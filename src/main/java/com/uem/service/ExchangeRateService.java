package com.uem.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Obtiene el tipo de cambio EUR -> USD / GBP desde una API externa (Frankfurter).
 *
 * Funcionamiento:
 *  1. Al arrancar la app se llama a refresh(), que consulta la API.
 *  2. Si la consulta funciona, se usan esos valores y se guardan en un fichero local.
 *  3. Si falla (sin internet, API caida...), se usa el ultimo valor conocido guardado.
 *  4. Si nunca se ha podido consultar, se usan unos valores por defecto.
 */
public class ExchangeRateService {

    private static final Logger log = LoggerFactory.getLogger(ExchangeRateService.class);

    private static final String API_URL =
            "https://api.frankfurter.dev/v1/latest?base=EUR&symbols=USD,GBP";
    private static final Path CACHE_FILE =
            Path.of(System.getProperty("user.home"), ".orders-app", "rates.properties");

    private final Map<String, Double> rates = new HashMap<>();
    private String source;

    public ExchangeRateService() {
        // Valores por defecto (solo se usan si nunca ha habido conexion)
        rates.put("EUR", 1.0);
        rates.put("USD", 1.162976);
        rates.put("GBP", 0.87);
        source = "default value";
    }

    /** Consulta la API; si falla, recupera el ultimo valor conocido. */
    public void refresh() {
        try {
            fetchFromApi();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Consulta del tipo de cambio interrumpida", e);
            loadFromCache();
        } catch (Exception e) {
            log.warn("No se pudo consultar el tipo de cambio: {}", e.getMessage());
            loadFromCache();
        }
    }

    private void fetchFromApi() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
        HttpRequest request = HttpRequest.newBuilder(URI.create(API_URL))
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("HTTP " + response.statusCode());
        }

        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonObject apiRates = json.getAsJsonObject("rates");
        double usd = apiRates.get("USD").getAsDouble();
        double gbp = apiRates.get("GBP").getAsDouble();
        String date = json.get("date").getAsString();

        rates.put("USD", usd);
        rates.put("GBP", gbp);
        source = "live rate, " + date;
        log.debug("Tipo de cambio actualizado: USD={} GBP={} ({})", usd, gbp, date);

        saveToCache(usd, gbp, date);
    }

    private void saveToCache(double usd, double gbp, String date) {
        try {
            Files.createDirectories(CACHE_FILE.getParent());
            Properties p = new Properties();
            p.setProperty("USD", String.valueOf(usd));
            p.setProperty("GBP", String.valueOf(gbp));
            p.setProperty("date", date);
            try (OutputStream out = Files.newOutputStream(CACHE_FILE)) {
                p.store(out, "Ultimo tipo de cambio conocido (base EUR)");
            }
        } catch (IOException e) {
            log.warn("No se pudo guardar el tipo de cambio: {}", e.getMessage());
        }
    }

    private void loadFromCache() {
        if (!Files.exists(CACHE_FILE)) {
            log.warn("Sin conexion y sin valor guardado: se usan los valores por defecto");
            return;
        }
        try (InputStream in = Files.newInputStream(CACHE_FILE)) {
            Properties p = new Properties();
            p.load(in);
            rates.put("USD", Double.parseDouble(p.getProperty("USD")));
            rates.put("GBP", Double.parseDouble(p.getProperty("GBP")));
            source = "last known rate, " + p.getProperty("date", "unknown date");
            log.debug("Usando ultimo tipo de cambio conocido ({})", source);
        } catch (IOException | RuntimeException e) {
            log.warn("No se pudo leer el ultimo tipo de cambio guardado: {}", e.getMessage());
        }
    }

    /** Cuantas unidades de la moneda se obtienen por 1 EUR. */
    public double getRate(String currency) {
        return rates.getOrDefault(currency, 1.0);
    }

    /** Origen del valor: live, last known o default. */
    public String getSource() {
        return source;
    }
}