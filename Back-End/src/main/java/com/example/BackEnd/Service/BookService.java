package com.example.BackEnd.Service;

import com.example.BackEnd.DTO.BookDTO;
import com.example.BackEnd.Entity.Book;
import com.example.BackEnd.Repo.BookRepo;
import com.example.BackEnd.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service

public class BookService {

    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private ModelMapper modelMapper;


    public String add_book(BookDTO bookDTO){
        try {
            if (bookRepo.existsById(bookDTO.getBook_Name())) {
                Book book = bookRepo.findById(bookDTO.getBook_Name()).orElseThrow(() -> new RuntimeException("Book not found"));
                book.setStock(book.getStock() + bookDTO.getStock());
                return VarList.RSP_DUPLICATED;
            } else {
                bookRepo.save(modelMapper.map(bookDTO, Book.class));
                return VarList.RSP_SUCCESS;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error adding book: " + e.getMessage());
        }
    }



    public String updateBook(BookDTO bookDTO) {
        try {
            if (bookRepo.existsById(bookDTO.getBook_Name())) {
                bookRepo.save(modelMapper.map(bookDTO, Book.class));
                return VarList.RSP_SUCCESS;
            } else {
                return VarList.RSP_NO_DATA_FOUND;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating book: " + e.getMessage());
        }
    }

    public List<BookDTO> GetBook() {
        try {
            List<Book> bookList = bookRepo.findAll();
            return modelMapper.map(bookList, new TypeToken<ArrayList<BookDTO>>() {
            }.getType());
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving books: " + e.getMessage());
        }
    }

    public BookDTO searchBook(String name) {
        try {
            if (bookRepo.existsById(name)) {
                Book book = bookRepo.findById(name).orElse(null);
                return modelMapper.map(book, BookDTO.class);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error searching for book: " + e.getMessage());
        }
    }
}
