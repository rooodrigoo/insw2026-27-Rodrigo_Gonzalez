package com.uem.model;

import java.util.List;

public class Calculator {

    public static final String EMPTY = "empty";

    // Multiplica dos enteros
    public int multiply(int a, int b) {
        return a * b;
    }

    // Multiplica dos double
    public double multiply(double a, double b) {
        return a * b;
    }

    // Concatena dos cadenas; si alguna es null devuelve "empty"
    public String concat(String a, String b) {
        if (a != null && b != null) {
            return a + b;
        }
        return EMPTY;
    }

    // Suma dos valores
    public double sum(double a, double b) {
        return a + b;
    }

    // Aplica un descuento porcentual (0-100) y devuelve el importe final
    public static double discount(double amount, double percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100");
        }
        return amount - (amount * percent / 100.0);
    }

    // Suma una lista de importes
    public double calculateTotal(List<Double> amounts) {
        return amounts.stream().mapToDouble(Double::doubleValue).sum();
    }
}
