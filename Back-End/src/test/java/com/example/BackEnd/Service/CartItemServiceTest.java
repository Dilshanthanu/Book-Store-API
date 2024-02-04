package com.example.BackEnd.Service;

import com.example.BackEnd.DTO.CartItemDTO;
import com.example.BackEnd.Entity.AppUser;
import com.example.BackEnd.Entity.Book;
import com.example.BackEnd.Entity.CartItem;
import com.example.BackEnd.Repo.AppUserRepo;
import com.example.BackEnd.Repo.BookRepo;
import com.example.BackEnd.Repo.CartItemRepo;
import com.example.BackEnd.util.VarList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CartItemServiceTest {
    @Mock
    private CartItemRepo cartItemRepo;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private CartItemService underTest;
    @Mock
    private AppUserRepo userRepo;
    @Mock
    private BookRepo bookRepo;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Disabled
    void deleteCartItem() {
        int cartitemid = 0;
        when(cartItemRepo.existsById(any())).thenReturn(true);
        String result = underTest.deleteCartItem(cartitemid);

        assertThat(result).isEqualTo("00");


    }


    @Test
    @Disabled
    void add_cart() {
        CartItemDTO cartItemDTO = new CartItemDTO(0,1,"test","test@gmaol.com");

        AppUser user = new AppUser("test@gmail.com", "password", "FirstName", "LastName");
        when(userRepo.findById("test@gmail.com")).thenReturn(Optional.of(user));
        when(userRepo.existsById("test@gmail.com")).thenReturn(true);
        Book book = new Book("test",0L,"testAuthor",0.0,1,"testDetails");
        when(bookRepo.findById("test")).thenReturn(Optional.of(book));

        String result = underTest.add_cart(cartItemDTO);

        CartItem cartItem = new CartItem(0,1,modelMapper.map("test",Book.class),modelMapper.map("test@gmail.com",AppUser.class));
        when(modelMapper.map(cartItemDTO, CartItem.class)).thenReturn(cartItem);

        when(cartItemRepo.existsByBook(book)).thenReturn(false);


        assertEquals(VarList.RSP_SUCCESS, result);
        //verify(cartItemRepo).save(cartItem);
    }

    @Test
    @Disabled
    void getcartitem() {
        List<CartItem> cartItemList = new ArrayList<>();
        CartItem cartItem1 = new CartItem();
        CartItem cartItem2 = new CartItem();
        cartItemList.add(cartItem1);
        cartItemList.add(cartItem2);

        List<CartItemDTO> expectedCartItemDTOList = new ArrayList<>();
        CartItemDTO cartItemDTO1 = new CartItemDTO();
        CartItemDTO cartItemDTO2 = new CartItemDTO();
        expectedCartItemDTOList.add(cartItemDTO1);
        expectedCartItemDTOList.add(cartItemDTO2);

        when(cartItemRepo.findAll()).thenReturn(cartItemList);

        when(modelMapper.map(cartItemList, new TypeToken<ArrayList<CartItemDTO>>() {}.getType()))
                .thenReturn(expectedCartItemDTOList);

        List<CartItemDTO> result = underTest.Getcartitem();

        assertEquals(expectedCartItemDTOList.size(), result.size());
    }

    @Test
    @Disabled
    void testUpdateCartItem() {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setCartItemId(1);

        CartItem existingCartItem = new CartItem();
        existingCartItem.setCartItemId(1);
        when(cartItemRepo.existsById(cartItemDTO.getCartItemId())).thenReturn(true);
        when(cartItemRepo.save(any())).thenReturn(existingCartItem);

        String result = underTest.UpdateCartItem(cartItemDTO);

        assertEquals(VarList.RSP_SUCCESS, result);
        verify(cartItemRepo, times(1)).save(any());
    }
    @Test
    void testUpdateCartItemNoDataFound() {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setCartItemId(1);

        when(cartItemRepo.existsById(cartItemDTO.getCartItemId())).thenReturn(false);

        String result = underTest.UpdateCartItem(cartItemDTO);

        assertEquals(VarList.RSP_NO_DATA_FOUND, result);
        verify(cartItemRepo, never()).save(any());
    }
}
