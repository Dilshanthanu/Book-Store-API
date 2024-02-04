package com.example.BackEnd.Controller;

import com.example.BackEnd.DTO.BookDTO;
import com.example.BackEnd.DTO.ResponseDTO;
import com.example.BackEnd.ExceptionHandler.BookException;
import com.example.BackEnd.Service.BookService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Test
    void testAddBookSuccess() {
        // Arrange
        BookDTO mockBookDTO = new BookDTO("Test Book", 123L, "Author", 19.99, 50, "Book details");
        when(bookService.add_book(any(BookDTO.class))).thenReturn(VarList.RSP_SUCCESS);

        // Act
        ResponseEntity<ResponseDTO> responseEntity = bookController.add_book(mockBookDTO);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertEquals("Success", responseEntity.getBody().getMessage());
        assertEquals(mockBookDTO, responseEntity.getBody().getContent());
    }

    @Test
    void testAddBookDuplicate() {
        // Arrange
        BookDTO mockBookDTO = new BookDTO("Test Book", 123L, "Author", 19.99, 20, "Book details");
        when(bookService.add_book(any(BookDTO.class))).thenReturn(VarList.RSP_DUPLICATED);

        // Act
        ResponseEntity<ResponseDTO> responseEntity = bookController.add_book(mockBookDTO);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertEquals("Stock updated", responseEntity.getBody().getMessage());
        assertEquals(mockBookDTO, responseEntity.getBody().getContent());
    }

    @Test
    void testAddBookError() {
        // Arrange
        BookDTO mockBookDTO = new BookDTO("Test Book", 123L, "Author", 19.99, 30, "Book details");
        when(bookService.add_book(any(BookDTO.class))).thenThrow(new RuntimeException("Error adding book"));

        // Act & Assert
        BookException exception = org.junit.jupiter.api.Assertions.assertThrows(BookException.class,
                () -> bookController.add_book(mockBookDTO));
        assertEquals("Error in BookController: Error adding book", exception.getMessage());
    }
}
