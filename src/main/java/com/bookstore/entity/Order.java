package com.bookstore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Integer version;

    @ManyToOne(cascade = CascadeType.ALL) // Cascade save operation to User
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ElementCollection
    private List<OrderItem> items;

    private String status; // Pending, Shipped, Delivered
    private String paymentStatus; // Paid, Unpaid
    private double totalAmount;
}

@Data
@Embeddable
class OrderItem {
    private Long bookId;
    private String title;
    private int quantity;
    private double price;
}
