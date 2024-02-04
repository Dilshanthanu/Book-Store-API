package com.example.BackEnd.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Book")
public class Book {
    @Id
    private String Book_Name;
    private Long Book_ID;
    private String Author;
    private double Price;
    private int Stock;
    private String Details;

    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Order> orders;

    public Book(String book_Name, Long book_ID, String author, double price, int stock, String details) {
        Book_Name = book_Name;
        Book_ID = book_ID;
        Author = author;
        Price = price;
        Stock = stock;
        Details = details;
    }
}
