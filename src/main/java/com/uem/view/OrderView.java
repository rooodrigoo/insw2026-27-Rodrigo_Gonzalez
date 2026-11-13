package com.uem.view;

import java.awt.FlowLayout;
import java.net.URL;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.uem.model.Article;
import com.uem.model.Order;

public class OrderView extends JFrame {

    public static final String[] CURRENCIES = {"EUR", "USD", "GBP"};

    private JTextField searchField = new JTextField(10);
    private JButton searchButton = new JButton("Search");
    private JComboBox<String> currencyBox = new JComboBox<>(CURRENCIES);
    private JTextArea resultArea = new JTextArea(10, 40);

    public OrderView() {
        setTitle("Order Management");

        // El icono se carga desde el classpath, para que funcione tambien dentro del JAR
        URL iconUrl = OrderView.class.getResource("/app.png");
        if (iconUrl != null) {
            setIconImage(new ImageIcon(iconUrl).getImage());
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Order ID:"));
        add(searchField);
        add(searchButton);
        add(currencyBox);
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea));

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public String getSearchId() {
        return searchField.getText();
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JComboBox<String> getCurrencyBox() {
        return currencyBox;
    }

    public String getSelectedCurrency() {
        return (String) currencyBox.getSelectedItem();
    }

    private static String symbolOf(String currency) {
        switch (currency) {
            case "USD": return "$";
            case "GBP": return "£";
            default: return "€";
        }
    }

    /** Muestra el pedido con todos los importes convertidos a la moneda elegida. */
    public void displayOrder(Order order, String currency, double rate, String rateInfo) {
        String s = symbolOf(currency);
        StringBuilder sb = new StringBuilder();
        sb.append("Order ").append(order.getId()).append(":\n");
        for (Article a : order.getArticles()) {
            sb.append(a.getNombre())
              .append(" x").append(a.getCantidad())
              .append(" @ ").append(money(a.getPrecio() * rate, s))
              .append(" (discount ").append(a.getDescuento()).append("%)")
              .append(" = ").append(money(a.getDiscountedAmount() * rate, s)).append("\n");
        }
        sb.append("Gross total: ").append(money(order.getGrossTotal() * rate, s)).append("\n");
        sb.append("Discounted total: ").append(money(order.getDiscountedTotal() * rate, s)).append("\n");
        if (rateInfo != null && !rateInfo.isEmpty()) {
            sb.append("\n").append(rateInfo).append("\n");
        }
        resultArea.setText(sb.toString());
    }

    private static String money(double value, String symbol) {
        return String.format(Locale.US, "%.2f%s", value, symbol);
    }

    public void displayMessage(String message) {
        resultArea.setText(message);
    }
}