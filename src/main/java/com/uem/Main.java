package com.uem;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uem.model.Order;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static final String ORDERS_PATH = "Software/universidad/src/main/resources/orders.json";

    public static void main(String[] args) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Order>>() {}.getType();

        List<Order> orders = new ArrayList<>();
        try (FileReader reader = new FileReader(ORDERS_PATH)) {
            orders = gson.fromJson(reader, listType);
        } catch (IOException e) {
            log.error("No se pudo leer el fichero {}", ORDERS_PATH, e);
            return;
        }

        for (Order order : orders) {
            log.debug("Loaded order: {}", order.getId());
        }
    }
}