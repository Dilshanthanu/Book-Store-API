package com.example.BackEnd.Controller;

import com.example.BackEnd.Controller.OrderController;
import com.example.BackEnd.DTO.OrderDTO;
import com.example.BackEnd.DTO.ResponseDTO;
import com.example.BackEnd.Service.OrderService;
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
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    void testPlaceOrderSuccess() {
        // Arrange
        OrderDTO mockOrderDTO = new OrderDTO(1, "Test Book", "2022-02-03", "Shipping Address", 5, "test@gmail.com");
        when(orderService.place_order(any(OrderDTO.class))).thenReturn(VarList.RSP_SUCCESS);

        // Act
        ResponseEntity<ResponseDTO> responseEntity = orderController.place_order(mockOrderDTO);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertEquals("Success", responseEntity.getBody().getMessage());
        assertEquals(mockOrderDTO, responseEntity.getBody().getContent());
    }

    @Test
    void testPlaceOrderNoDataFound() {
        // Arrange
        OrderDTO mockOrderDTO = new OrderDTO(1, "Nonexistent Book", "2022-02-03", "Shipping Address", 5, "test@gmail.com");
        when(orderService.place_order(any(OrderDTO.class))).thenReturn(VarList.RSP_NO_DATA_FOUND);

        // Act
        ResponseEntity<ResponseDTO> responseEntity = orderController.place_order(mockOrderDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody().getMessage());  // You might want to handle this differently
        assertEquals(mockOrderDTO, responseEntity.getBody().getContent());
    }

    @Test
    void testPlaceOrderError() {
        // Arrange
        OrderDTO mockOrderDTO = new OrderDTO(1, "Test Book", "2022-02-03", "Shipping Address", 5, "test@gmail.com");
        when(orderService.place_order(any(OrderDTO.class))).thenReturn(VarList.RSP_FAIL);

        // Act
        ResponseEntity<ResponseDTO> responseEntity = orderController.place_order(mockOrderDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Error", responseEntity.getBody().getMessage());
        assertEquals(null, responseEntity.getBody().getContent());
    }
}
