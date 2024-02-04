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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepo orderRepo;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private BookRepo bookRepo;

    @Mock
    private AppUserRepo userRepo;

    @Mock
    private CartItemRepo cartItemRepo;

    @InjectMocks
    private OrderService underTest;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void place_order_success() {
        OrderDTO orderDTO = new OrderDTO(1, "testItem", "2024.1.1", "kandy", 10, "test@gmail.com");
        AppUser user = new AppUser("test@gmaol.com", "password", "FirstName", "LastName");
        Book book = new Book("testItem", 1L, "sampleAuthor", 29.99, 10, "testDetails");
        CartItem cartItem = new CartItem(1, 5, book, user);

        // Mocking repository methods
        when(bookRepo.existsById("testItem")).thenReturn(true);
        when(userRepo.findById("test@gmaol.com")).thenReturn(Optional.of(user));
        when(modelMapper.map(bookRepo.findById("testItem").orElseThrow(), Book.class)).thenReturn(book);
        when(cartItemRepo.existsByBook(book)).thenReturn(true);
        when(cartItemRepo.findByBook(book)).thenReturn(cartItem);

        // Calling the method under test
        String result = underTest.place_order(orderDTO);

        // Verifying the interactions
        verify(bookRepo).save(book);
        verify(cartItemRepo).save(cartItem);
        verify(orderRepo, times(2)).save(any(Order.class)); // Two calls are expected in the method

        // Asserting the result
        assertEquals(VarList.RSP_SUCCESS, result);
    }

    @Test
    void place_order_no_user_found() {
        // Mock data
        //OrderDTO orderDTO = new OrderDTO("testItem", "nonexistentuser@gmaol.com", 2);

        // Mocking repository methods
        when(bookRepo.existsById("testItem")).thenReturn(true);
        when(userRepo.findById("nonexistentuser@gmaol.com")).thenReturn(Optional.empty());

        // Calling the method under test
        //String result = underTest.place_order(orderDTO);

        // Asserting the result
        //assertEquals(VarList.RSP_NO_DATA_FOUND, result);
    }

    // Add more test cases for different scenarios as needed
}
