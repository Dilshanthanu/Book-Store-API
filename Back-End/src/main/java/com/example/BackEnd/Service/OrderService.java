package com.example.BackEnd.Service;

import com.example.BackEnd.DTO.OrderDTO;
import com.example.BackEnd.Entity.AppUser;
import com.example.BackEnd.Entity.Book;
import com.example.BackEnd.Entity.CartItem;
import com.example.BackEnd.Entity.Order;
import com.example.BackEnd.Repo.AppUserRepo;
import com.example.BackEnd.Repo.BookRepo;
import com.example.BackEnd.Repo.CartItemRepo;
import com.example.BackEnd.Repo.OrderRepo;
import com.example.BackEnd.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private AppUserRepo userRepo;
    @Autowired
    private CartItemRepo cartItemRepo;

    public String place_order(OrderDTO orderDTO) {
        try {
            if (bookRepo.existsById(orderDTO.getItem_name())) {
                Optional<AppUser> userOptional = userRepo.findById(orderDTO.getE_mail());

                if (userOptional.isPresent()) {
                    AppUser user = userOptional.get();
                    Book book = modelMapper.map(bookRepo.findById(orderDTO.getItem_name()).orElseThrow(), Book.class);

                    if (cartItemRepo.existsByBook(book)) {
                        CartItem cartItem = cartItemRepo.findByBook(book);

                        if (cartItem.getQuantity() > orderDTO.getQuantity()) {
                            cartItem.setQuantity(cartItem.getQuantity() - orderDTO.getQuantity());
                            book.setStock(book.getStock() - orderDTO.getQuantity());
                        } else {
                            book.setStock(book.getStock() - orderDTO.getQuantity());
                            cartItem.setQuantity(0);
                            bookRepo.save(book);
                        }

                        cartItemRepo.save(cartItem);
                    } else {
                        return VarList.RSP_NO_DATA_FOUND;
                    }

                    Order order = modelMapper.map(orderDTO, Order.class);
                    order.setUser(user);
                    Set<Book> books = new HashSet<>();
                    books.add(book);
                    order.setBooks(books);
                    order.setTotal_amount(book.getPrice() * orderDTO.getQuantity());
                    orderRepo.save(order);

                    orderRepo.save(order);

                    return VarList.RSP_SUCCESS;
                } else {
                    return VarList.RSP_NO_DATA_FOUND;
                }
            } else {
                return VarList.RSP_NO_DATA_FOUND;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error placing order: " + e.getMessage());
        }
    }

}

