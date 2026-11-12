package com.uem.view;

import javax.swing.*;
import com.uem.model.Article;
import com.uem.model.Order;
import java.awt.*;

public class OrderView extends JFrame {
    private JTextField searchField = new JTextField(10);
    private JButton searchButton = new JButton("Search");
    private JTextArea resultArea = new JTextArea(10, 40);

    public OrderView() {
        setTitle("Order Management");
        setIconImage(new ImageIcon("Software\\universidad\\src\\main\\resources\\app.png").getImage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Order ID:"));
        add(searchField);
        add(searchButton);
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea));

        pack();
        setVisible(true);
    }

    public String getSearchId() {
        return searchField.getText();
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public void displayOrder(Order order, double rate) {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ").append(order.getId()).append(":\n");
        for (Article a : order.getArticles()) {
            sb.append(a.getNombre())
              .append(" x").append(a.getCantidad())
              .append(" ").append(a.getPrecio()).append("€")
              .append(" (discount ").append(a.getDescuento()).append("%)")
              .append(" = ").append(a.getDiscountedAmount()).append("€\n");
        }
        sb.append("Gross total: ").append(order.getGrossTotal()).append("€\n");
        sb.append("Discounted total: ").append(order.getDiscountedTotal()).append("€\n");
        sb.append("Gross total (USD): ").append(order.getGrossTotal() * rate).append("€\n");
        sb.append("Discounted total (USD): ").append(order.getDiscountedTotal() * rate).append("€\n");
        resultArea.setText(sb.toString());
    }

    public void displayMessage(String message) {
        resultArea.setText(message);
    }
}