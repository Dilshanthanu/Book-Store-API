package com.example.BackEnd.Service;

import com.example.BackEnd.DTO.CartItemDTO;
import com.example.BackEnd.Entity.AppUser;
import com.example.BackEnd.Entity.Book;
import com.example.BackEnd.Entity.CartItem;

import com.example.BackEnd.ExceptionHandler.CartException;
import com.example.BackEnd.Repo.AppUserRepo;
import com.example.BackEnd.Repo.BookRepo;
import com.example.BackEnd.Repo.CartItemRepo;
import com.example.BackEnd.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartItemService {

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private AppUserRepo userRepo;

    public String deleteCartItem(int cartItemid) {
        try {
            if (cartItemRepo.existsById(cartItemid)) {
                cartItemRepo.deleteById(cartItemid);
                return VarList.RSP_SUCCESS;
            } else {
                return VarList.RSP_NO_DATA_FOUND;
            }
        } catch (Exception e) {
            throw new CartException("Error deleting cart item: " + e.getMessage());
        }
    }

    public String add_cart(CartItemDTO cartItemDTO) {
        try {
            if (bookRepo.findById(cartItemDTO.getBook_name()).get().getStock() != 0) {
                Optional<AppUser> userOptional = userRepo.findById(cartItemDTO.getE_mail());

                if (userRepo.existsById(cartItemDTO.getE_mail())) {
                    AppUser user = userOptional.get();

                    // Map CartItemDTO to CartItem
                    CartItem cartItem = modelMapper.map(cartItemDTO, CartItem.class);
                    cartItem.setUser(user);

                    // Check if the book exists
                    Book book = modelMapper.map(bookRepo.findById(modelMapper.map(cartItemDTO.getBook_name(), String.class)), Book.class);

                    // Check if the cart item exists
                    if (!cartItemRepo.existsByBook(book)) {
                        // Cart item doesn't exist, save it
                        cartItemRepo.save(cartItem);
                        return VarList.RSP_SUCCESS;
                    } else {
                        CartItem existItem = cartItemRepo.findAllByBook(book);
                        existItem.setQuantity(existItem.getQuantity() + cartItemDTO.getQuantity());
                        return VarList.RSP_DUPLICATED;
                    }
                } else {
                    return VarList.RSP_NO_DATA_FOUND;
                }
            } else {
                return VarList.RSP_STOCK_UNAVAILABLE;
            }
        } catch (Exception e) {
            throw new CartException("Error adding cart item: " + e.getMessage());
        }
    }

    public List<CartItemDTO> Getcartitem() {
        try {
            List<CartItem> cartitemList = cartItemRepo.findAll();
            return modelMapper.map(cartitemList, new TypeToken<ArrayList<CartItemDTO>>() {
            }.getType());
        } catch (Exception e) {
            throw new CartException("Error getting cart items: " + e.getMessage());
        }
    }

    public String UpdateCartItem(CartItemDTO cartItemDTO) {
        try {
            if (cartItemRepo.existsById(cartItemDTO.getCartItemId())) {
                cartItemRepo.save(modelMapper.map(cartItemDTO, CartItem.class));
                return VarList.RSP_SUCCESS;
            } else {
                return VarList.RSP_NO_DATA_FOUND;
            }
        } catch (Exception e) {
            throw new CartException("Error updating cart item: " + e.getMessage());
        }
    }


}
