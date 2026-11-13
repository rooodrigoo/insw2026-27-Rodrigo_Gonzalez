package com.uem.controller;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uem.model.Order;
import com.uem.service.ExchangeRateService;
import com.uem.view.OrderView;

public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    // Atributo de clase OrderView
    OrderView view;
    // Lista de orders
    List<Order> orders;
    // Servicio que da el tipo de cambio
    ExchangeRateService rates;
    // Ultimo pedido mostrado (para redibujarlo si cambia la moneda)
    private Order currentOrder;

    public OrderController(OrderView view, List<Order> orders, ExchangeRateService rates) {
        this.view = view;
        this.orders = orders;
        this.rates = rates;

        view.getSearchButton().addActionListener(e -> searchOrder());
        // Al cambiar la moneda en el desplegable, se actualiza el pedido mostrado
        view.getCurrencyBox().addActionListener(e -> showCurrentOrder());
    }

    private void searchOrder() {
        String id = view.getSearchId().trim();
        log.debug("Searching order: {}", id);

        currentOrder = orders.stream()
                .filter(o -> o.getId() != null && o.getId().trim().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);

        if (currentOrder != null) {
            showCurrentOrder();
        } else {
            log.debug("Order not found: {}", id);
            view.displayMessage("Order not found.");
        }
    }

    private void showCurrentOrder() {
        if (currentOrder == null) {
            return;
        }
        String currency = view.getSelectedCurrency();
        double rate = rates.getRate(currency);
        String info = currency.equals("EUR") ? "" : String.format(Locale.US,
                "Exchange rate: 1 EUR = %.4f %s (%s)", rate, currency, rates.getSource());
        view.displayOrder(currentOrder, currency, rate, info);
    }
}