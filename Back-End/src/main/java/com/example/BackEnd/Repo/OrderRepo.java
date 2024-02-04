package com.example.BackEnd.Repo;

import com.example.BackEnd.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Integer> {
}
