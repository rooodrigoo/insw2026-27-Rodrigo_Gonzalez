package com.uem;

import com.uem.model.Searcher;
import com.uem.model.Calculator;
import com.uem.model.Article;
import com.uem.model.Order;

public class Main {
    public static void main(String[] args) {
        Order order = new Order("PEDIDO-001");
        order.addArticle(new Article("Ratón", 2, 25.0, 5.0));
        order.addArticle(new Article("Monitor", 1, 150.0, 15.0));
        System.out.println(order);
    }
}
