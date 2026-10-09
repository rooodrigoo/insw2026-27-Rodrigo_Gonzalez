package com.uem.model;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private final Calculator calculator = new Calculator();

    private String id;
    private List<Article> articles;

    public Order() {
        this.articles = new ArrayList<>();
    }

    public Order(String id) {
        this.id = id;
        this.articles = new ArrayList<>();
    }

    public Order(String id, List<Article> articles) {
        this.id = id;
        this.articles = articles != null ? articles : new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public List<Article> getArticles() { return articles; }
    public void setArticles(List<Article> articles) {
        this.articles = articles != null ? articles : new ArrayList<>();
    }

    public void addArticle(Article article) {
        articles.add(article);
    }

    // Total de todos los artículos sin descuento
    public double getGrossTotal() {
        List<Double> amounts = new ArrayList<>();
        for (Article a : articles) {
            amounts.add(a.getGrossAmount());
        }
        return calculator.calculateTotal(amounts);
    }

    // Total de todos los artículos con su descuento aplicado
    public double getDiscountedTotal() {
        List<Double> amounts = new ArrayList<>();
        for (Article a : articles) {
            amounts.add(a.getDiscountedAmount());
        }
        return calculator.calculateTotal(amounts);
    }

    @Override
    public String toString() {
        return "Order{id='" + id + "', articles=" + articles + ", grossTotal=" + getGrossTotal() + ", discountedTotal=" + getDiscountedTotal() + "}";
    }
}
