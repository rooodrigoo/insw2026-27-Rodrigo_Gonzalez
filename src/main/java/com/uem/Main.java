package com.uem;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uem.controller.OrderController;
import com.uem.model.Order;
import com.uem.service.ExchangeRateService;
import com.uem.view.OrderView;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static final String ORDERS_RESOURCE = "/orders.json";

    public static void main(String[] args) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Order>>() {}.getType();

        // El JSON se lee desde dentro del JAR (classpath), no desde una ruta del disco
        List<Order> orders = new ArrayList<>();
        try (InputStream in = Main.class.getResourceAsStream(ORDERS_RESOURCE)) {
            if (in == null) {
                log.error("No se encontro el recurso {}", ORDERS_RESOURCE);
                return;
            }
            try (Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
                orders = gson.fromJson(reader, listType);
            }
        } catch (IOException e) {
            log.error("No se pudo leer el recurso {}", ORDERS_RESOURCE, e);
            return;
        }

        for (Order order : orders) {
            log.debug("Loaded order: {}", order.getId());
        }

        // Tipo de cambio: se consulta a la API cada vez que se ejecuta la app
        ExchangeRateService rates = new ExchangeRateService();
        rates.refresh();

        // Inicializar MVC
        OrderView view = new OrderView();
        new OrderController(view, orders, rates);
    }
}