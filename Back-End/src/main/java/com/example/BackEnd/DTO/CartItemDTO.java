package com.example.BackEnd.DTO;

import com.example.BackEnd.Entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CartItemDTO {
    private int cartItemId;
    private int quantity;
    private String book_name;
    private String e_mail;


}
