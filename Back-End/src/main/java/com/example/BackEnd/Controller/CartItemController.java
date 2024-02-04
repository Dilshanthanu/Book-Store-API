package com.example.BackEnd.Controller;

import com.example.BackEnd.DTO.CartItemDTO;
import com.example.BackEnd.DTO.ResponseDTO;
import com.example.BackEnd.ExceptionHandler.CartException;
import com.example.BackEnd.Repo.CartItemRepo;
import com.example.BackEnd.Service.CartItemService;
import com.example.BackEnd.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/cart")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CartItemRepo cartItemRepo;
    @PostMapping(value = "/addToCart")
    public ResponseEntity add_cart(@RequestBody CartItemDTO cartItemDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            String res = cartItemService.add_cart(cartItemDTO);
            if (res.equals("00")) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(cartItemDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            } else if (res.equals("01")) {
                throw new CartException("No user found");
            } else if (res.equals("06")) {
                throw new CartException("Quantity updated");
            } else if (res.equals("02")) {
                throw new CartException("Stock is unavailable");
            } else {
                throw new CartException("Error");
            }
        } catch (CartException e) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(cartItemDTO);
            return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(cartItemDTO);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/GetCartItem")
    public ResponseEntity GetAllCartItem(){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<CartItemDTO> cartItemDTOS = cartItemService.Getcartitem();
            responseDTO.setCode(VarList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(cartItemDTOS);
            return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
        }catch (Exception x){
            responseDTO.setCode(null);
            responseDTO.setMessage(x.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping(value = "/DeleteCartItem/{cartItemId}")
    public ResponseEntity deleteCartItem(@PathVariable int cartItemId) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            String resp = cartItemService.deleteCartItem(cartItemId);
            if (resp.equals("00")) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            } else {
                throw new CartException("No Item available for this CartItemId");
            }
        } catch (CartException e) {
            responseDTO.setCode(VarList.RSP_FAIL);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            responseDTO.setCode(null);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/UpdateCartItem")
    public ResponseEntity UpdateEmployee(@RequestBody CartItemDTO cartItemDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            String resp = cartItemService.UpdateCartItem(cartItemDTO);
            if (resp.equals("00")) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(cartItemDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            } else if (resp.equals("01")) {
                throw new CartException("No Item available for this CartItemId");
            } else {
                throw new CartException("Error");
            }
        } catch (CartException e) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(cartItemDTO);
            return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(cartItemDTO);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
