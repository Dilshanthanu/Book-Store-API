package com.example.BackEnd.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {
    private int order_Id;
    private String item_name;
    private String order_date;
    private double total_amount;
    private String shipping_address;
    private int quantity;
    private String e_mail;

    public OrderDTO(int order_Id, String item_name, String order_date, String shipping_address, int quantity, String e_mail) {
        this.order_Id = order_Id;
        this.item_name = item_name;
        this.order_date = order_date;
        this.shipping_address = shipping_address;
        this.quantity = quantity;
        this.e_mail = e_mail;
    }
}
