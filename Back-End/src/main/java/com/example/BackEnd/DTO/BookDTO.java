package com.example.BackEnd.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDTO {

    private String Book_Name;
    private Long Book_ID;
    private String Author;
    private double Price;
    private int Stock;
    private String Details;

}
