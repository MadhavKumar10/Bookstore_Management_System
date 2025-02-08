package com.bookstore.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Integer version; 

    private String title;
    private String author;
    private String genre;
    private String isbn;
    private double price;
    private String description;
    private int stockQuantity;
    private String imageUrl;
}
