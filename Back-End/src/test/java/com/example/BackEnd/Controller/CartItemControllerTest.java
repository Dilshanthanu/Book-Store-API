package com.example.BackEnd.Controller;

import com.example.BackEnd.DTO.CartItemDTO;
import com.example.BackEnd.DTO.ResponseDTO;
import com.example.BackEnd.ExceptionHandler.CartException;
import com.example.BackEnd.Service.CartItemService;
import com.example.BackEnd.util.VarList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class CartItemControllerTest {

    @Mock
    private CartItemService cartItemService;

    @InjectMocks
    private CartItemController cartItemController;

    @Test
    void testAddCartItemSuccess() {
        // Arrange
        CartItemDTO mockCartItemDTO = new CartItemDTO(1, 5, "Test Book", "test@gmail.com");
        when(cartItemService.add_cart(any(CartItemDTO.class))).thenReturn("00");

        // Act
        ResponseEntity<ResponseDTO> responseEntity = cartItemController.add_cart(mockCartItemDTO);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertEquals("Success", responseEntity.getBody().getMessage());
        assertEquals(mockCartItemDTO, responseEntity.getBody().getContent());
    }

    @Test
    void testAddCartItemNoUserFound() {
        // Arrange
        CartItemDTO mockCartItemDTO = new CartItemDTO(1, 5, "Test Book", "test@gmail.com");
        when(cartItemService.add_cart(any(CartItemDTO.class))).thenReturn("01");

        // Act
        ResponseEntity<ResponseDTO> responseEntity = cartItemController.add_cart(mockCartItemDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("No user found", responseEntity.getBody().getMessage());
        assertEquals(mockCartItemDTO, responseEntity.getBody().getContent());
    }

    @Test
    void testAddCartItemQuantityUpdated() {
        // Arrange
        CartItemDTO mockCartItemDTO = new CartItemDTO(1, 5, "Test Book", "test@gmail.com");
        when(cartItemService.add_cart(any(CartItemDTO.class))).thenReturn("06");

        // Act
        ResponseEntity<ResponseDTO> responseEntity = cartItemController.add_cart(mockCartItemDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Quantity updated", responseEntity.getBody().getMessage());
        assertEquals(mockCartItemDTO, responseEntity.getBody().getContent());
    }

    @Test
    void testAddCartItemStockUnavailable() {
        // Arrange
        CartItemDTO mockCartItemDTO = new CartItemDTO(1, 5, "Test Book", "test@gmail.com");
        when(cartItemService.add_cart(any(CartItemDTO.class))).thenReturn("02");

        // Act
        ResponseEntity<ResponseDTO> responseEntity = cartItemController.add_cart(mockCartItemDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Stock is unavailable", responseEntity.getBody().getMessage());
        assertEquals(mockCartItemDTO, responseEntity.getBody().getContent());
    }

    @Test
    void testAddCartItemError() {
        // Arrange
        CartItemDTO mockCartItemDTO = new CartItemDTO(1, 5, "Test Book", "test@gmail.com");
        when(cartItemService.add_cart(any(CartItemDTO.class))).thenThrow(new CartException("Error"));

        // Act
        ResponseEntity<ResponseDTO> responseEntity = cartItemController.add_cart(mockCartItemDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Error", responseEntity.getBody().getMessage());
        assertEquals(mockCartItemDTO, responseEntity.getBody().getContent());
    }
}
