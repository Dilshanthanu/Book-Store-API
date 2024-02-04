package com.example.BackEnd.Controller;

import com.example.BackEnd.Controller.UserController;
import com.example.BackEnd.DTO.ResponseDTO;
import com.example.BackEnd.DTO.UserDTO;
import com.example.BackEnd.Service.UserService;
import com.example.BackEnd.util.VarList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;


    @Test
    void testSignUpUser_Success() {
        UserDTO userDTO = new UserDTO("test@gmail.com", "password", "FirstName", "LastName");

        when(userService.sign_up(any(UserDTO.class))).thenReturn(VarList.RSP_SUCCESS);

        ResponseEntity<ResponseDTO> responseEntity = userController.sign_up_user(userDTO);

        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertEquals(VarList.RSP_SUCCESS, responseEntity.getBody().getCode());
        assertEquals("Success", responseEntity.getBody().getMessage());
        assertEquals(userDTO, responseEntity.getBody().getContent());

        verify(userService, times(1)).sign_up(eq(userDTO));
    }

    @Test
    void testSignUpUser_Error() {

        UserDTO userDTO = new UserDTO("test@gmail.com", "password", "FirstName", "LastName");

        when(userService.sign_up(userDTO)).thenReturn("SomeError");

        ResponseEntity<ResponseDTO> responseEntity = userController.sign_up_user(userDTO);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(VarList.RSP_ERROR, responseEntity.getBody().getCode());

        verify(userService, times(1)).sign_up(eq(userDTO));
    }




    @Test
    public void testLoginSuccess() {
        // Mock data
        UserDTO mockedUserDTO = new UserDTO();
        mockedUserDTO.setE_mail("test@example.com");
        mockedUserDTO.setPassword("password123");

        // Mock UserService behavior
        when(userService.login(anyString(), anyString())).thenReturn(mockedUserDTO);

        // Perform the login
        ResponseEntity<ResponseDTO> responseEntity = userController.login("password123", "test@example.com");

        // Verify the interactions
        verify(userService, times(1)).login("password123", "test@example.com");

        // Assertions
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertEquals("Success", responseEntity.getBody().getMessage());
        assertEquals(mockedUserDTO, responseEntity.getBody().getContent());
    }





    @Test
    void testLogin_Fail() {
        // Arrange
        Mockito.when(userService.login(anyString(), anyString())).thenReturn(null);

        // Act
        ResponseEntity<ResponseDTO> responseEntity = userController.login("invalidPassword", "invalid@example.com");

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("E mail or password incorrect", responseEntity.getBody().getMessage());
        assertEquals(null, responseEntity.getBody().getContent());
    }

}