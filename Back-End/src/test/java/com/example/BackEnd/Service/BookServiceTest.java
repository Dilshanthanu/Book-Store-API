package com.example.BackEnd.Service;

import com.example.BackEnd.DTO.BookDTO;
import com.example.BackEnd.Entity.Book;
import com.example.BackEnd.Repo.BookRepo;
import com.example.BackEnd.util.VarList;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookServiceTest {

    @Mock
    private BookRepo bookRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BookService underTest;

    @Test
    void testAddBook() {
        BookDTO bookDTO = new BookDTO("test",1l,"test_author",0.0,1,"test_details");

        when(bookRepo.existsById(any())).thenReturn(false);
        when(bookRepo.save(any())).thenReturn(new Book());

        String result = underTest.add_book(bookDTO);

        assertThat(result).isEqualTo(VarList.RSP_SUCCESS);


    }


    @Test
    void updateBook() {
        BookDTO bookDTO = new BookDTO("test",1l,"test_author",0.0,1,"test_details");

        when(bookRepo.existsById(any())).thenReturn(true);
        when(bookRepo.save(any())).thenReturn(new Book());

        String result = underTest.updateBook(bookDTO);

        assertThat(result).isEqualTo(VarList.RSP_SUCCESS);
    }

    @Test
    void getBook() {
        Book book1 = new Book("test1", 1L, "sampleAuthor1", 29.99, 10, "testDetails1");
        Book book2 = new Book("test2", 2L, "sampleAuthor2", 29.99, 10, "testDetails2");

        List<Book> bookList = Arrays.asList(book1, book2);

        when(bookRepo.findAll()).thenReturn(bookList);
        List<BookDTO> expectedBookDTOList = Arrays.asList(
                new BookDTO("test1", 1L, "sampleAuthor1", 29.99, 10, "testDetails1"),
                new BookDTO("test2", 2L, "sampleAuthor2", 29.99, 10, "testDetails2")
        );
        when(modelMapper.map(any(), eq(new TypeToken<ArrayList<BookDTO>>() {}.getType())))
                .thenReturn(expectedBookDTOList);

        List<BookDTO> result = underTest.GetBook();

        assertThat(result).isNotNull();

    }

    @Test
    void searchBook() {
        String bookName = "ExampleBook"; // Provide a valid book name

        Book expectedBook = new Book();
        expectedBook.setBook_Name(bookName);
        expectedBook.setBook_ID(1L);
        expectedBook.setAuthor("SampleAuthor");
        expectedBook.setStock(10);
        expectedBook.setDetails("testDetails");
        expectedBook.setPrice(29.99);

        when(bookRepo.existsById(expectedBook.getBook_Name())).thenReturn(true);
        when(bookRepo.findById(expectedBook.getBook_Name())).thenReturn(Optional.of(expectedBook));
        when(modelMapper.map(expectedBook, BookDTO.class)).thenReturn(new BookDTO(bookName, 1L, "SampleAuthor", 29.99, 10, "testDetails"));

        System.out.println("Input parameters for searchBook: " + bookName);

        BookDTO result = underTest.searchBook(bookName);

        System.out.println("Result from searchBook: " + result);

        assertThat(result).isNotNull();
        assertThat(result.getBook_Name()).isEqualTo("ExampleBook");
        assertThat(result.getBook_ID()).isEqualTo(1L);
        assertThat(result.getAuthor()).isEqualTo("SampleAuthor");
        assertThat(result.getStock()).isEqualTo(10);
        assertThat(result.getDetails()).isEqualTo("testDetails");
        assertThat(result.getPrice()).isEqualTo(29.99);
    }
}