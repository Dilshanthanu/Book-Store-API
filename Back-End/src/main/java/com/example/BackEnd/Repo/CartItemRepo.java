package com.example.BackEnd.Repo;

import com.example.BackEnd.Entity.AppUser;
import com.example.BackEnd.Entity.Book;
import com.example.BackEnd.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CartItemRepo extends JpaRepository<CartItem,Integer> {


    boolean existsByBook(Book book);
    CartItem findByBook(Book book);

    CartItem  findAllByBook(Book book);

    Optional<CartItem> findByUserAndBook(AppUser user, Book book);
}
