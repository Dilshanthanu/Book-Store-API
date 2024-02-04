package com.example.BackEnd.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int order_Id;
    private String item_name;
    private String order_date;
    private double total_amount;
    private String shipping_address;
    private int quantity;
    @ManyToOne
    private AppUser user;
    @ManyToMany
    @JoinTable(
            name = "Book_Order",
            joinColumns = @JoinColumn(name = "order_Id"),
            inverseJoinColumns = @JoinColumn(name = "Book_Name")
    )
    private Set<Book> books;

}
