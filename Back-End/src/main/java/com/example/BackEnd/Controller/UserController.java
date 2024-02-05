package com.example.BackEnd.Controller;

import com.example.BackEnd.DTO.ResponseDTO;
import com.example.BackEnd.DTO.UserDTO;
import com.example.BackEnd.ExceptionHandler.BookException;
import com.example.BackEnd.Service.UserService;
import com.example.BackEnd.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;


    private ResponseDTO responseDTO = new ResponseDTO();
    @Autowired
    public UserController(UserService userService, ResponseDTO responseDTO) {
        this.userService = userService;
        this.responseDTO = responseDTO;
    }

    @PostMapping("/sign up")
    public ResponseEntity sign_up_user(@RequestBody UserDTO userDTO){
        try {
            String res = userService.sign_up(userDTO);
            ResponseDTO responseDTO = new ResponseDTO();

            if ("00".equals(res)) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(userDTO);
                return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
            } else if ("06".equals(res)) {
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMessage("Email is already entered");
                responseDTO.setContent(userDTO);
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
            } else {
                responseDTO.setCode(VarList.RSP_ERROR);
                responseDTO.setMessage("Error");
                responseDTO.setContent(userDTO);
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception x) {
            throw new BookException("Error in UserController: " + x.getMessage());
        }
    }

   @GetMapping("/Login/{password}/{email}")
    public ResponseEntity login(@PathVariable String password, @PathVariable String email){
        try {
            UserDTO userDTO = userService.login(password, email);
            ResponseDTO responseDTO = new ResponseDTO();
            if (userDTO != null) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(userDTO);
                return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
            }else {
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMessage("E mail or password incorrect");
                responseDTO.setContent(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception x){
            throw new BookException("Error in UserController: " + x.getMessage());
        }

    }

}
