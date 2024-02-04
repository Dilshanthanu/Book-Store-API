package com.example.BackEnd.Controller;

import com.example.BackEnd.DTO.BookDTO;
import com.example.BackEnd.DTO.ResponseDTO;
import com.example.BackEnd.ExceptionHandler.BookException;
import com.example.BackEnd.Service.BookService;
import com.example.BackEnd.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/book")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private ResponseDTO responseDTO;


    @PostMapping(value = "/addBook")
    public ResponseEntity add_book(@RequestBody BookDTO bookDTO) {
        try {
            String res = bookService.add_book(bookDTO);
            ResponseDTO responseDTO = new ResponseDTO();
            if (res.equals(VarList.RSP_DUPLICATED)) {
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMessage("Stock updated");
                responseDTO.setContent(bookDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            } else if (res.equals(VarList.RSP_SUCCESS)) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(bookDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            } else {
                throw new BookException("Error");
            }
        } catch (Exception e) {
            throw new BookException("Error in BookController: " + e.getMessage());
        }
    }

    @PutMapping(value = "/UpdateBook")
    public ResponseEntity UpdateBookStore(@RequestBody BookDTO bookDTO) {
        try {
            String resp = bookService.updateBook(bookDTO);
            ResponseDTO responseDTO = new ResponseDTO();
            if (resp.equals(VarList.RSP_SUCCESS)) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Update Success");
                responseDTO.setContent(bookDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            } else if (resp.equals(VarList.RSP_NO_DATA_FOUND)) {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("Not a registered employee");
                responseDTO.setContent(bookDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            } else {
                throw new BookException("Error");
            }
        } catch (Exception x) {
            throw new BookException("Error in BookController: " + x.getMessage());
        }
    }

    @GetMapping(value = "/GetAllBook")
    public ResponseEntity GetAllBook() {
        try {
            List<BookDTO> employeeDTOS = bookService.GetBook();
            responseDTO.setCode(VarList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(employeeDTOS);
            return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
        } catch (Exception x) {
            throw new BookException("Error in BookController: " + x.getMessage());
        }
    }

    @GetMapping(value = "/searchBook/{BookName}")
    public ResponseEntity searchEmployee(@PathVariable String bookName) {
        try {
            BookDTO employeeDTO = bookService.searchBook(bookName);
            if (employeeDTO != null) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(employeeDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            } else {
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMessage("No employee available for this EmpId");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception x) {
            throw new BookException("Error in BookController: " + x.getMessage());
        }
    }

}
