package com.uem.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uem.model.Order;
import com.uem.view.OrderView;

public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    // Tipo de cambio fijo EUR -> USD
    private static final double USD_RATE = 1.162976;

    // Atributo de clase OrderView
    OrderView view;
    // Lista de orders
    List<Order> orders;

    
    public OrderController(OrderView view, List<Order> orders) {
        this.view = view;
        this.orders = orders;

        view.getSearchButton().addActionListener(e -> searchOrder());
    }

    private void searchOrder() {
        String id = view.getSearchId().trim();
        log.debug("Searching order: {}", id);

        Order found = orders.stream()
                .filter(o -> o.getId() != null && o.getId().trim().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);

        if (found != null) {
            view.displayOrder(found, USD_RATE);
        } else {
            log.debug("Order not found: {}", id);
            view.displayMessage("Order not found.");
        }
    }
}