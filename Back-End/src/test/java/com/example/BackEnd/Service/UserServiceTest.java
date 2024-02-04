package com.example.BackEnd.Service;

import com.example.BackEnd.DTO.UserDTO;
import com.example.BackEnd.Entity.AppUser;
import com.example.BackEnd.Repo.AppUserRepo;
import com.example.BackEnd.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private AppUserRepo userRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sign_up() {
        UserDTO userDTO = new UserDTO("Test@gmil.com", "1234", "F_name", "L_name");

        when(userRepo.count()).thenReturn(0L);
        when(userRepo.existsById(any())).thenReturn(false);

        when(modelMapper.map(userDTO, AppUser.class)).thenReturn(new AppUser());

        String result = underTest.sign_up(userDTO);

        ArgumentCaptor<AppUser> userArgumentCaptor = ArgumentCaptor.forClass(AppUser.class);
        verify(userRepo).save(userArgumentCaptor.capture());

        AppUser capturedUser = userArgumentCaptor.getValue();

        assertThat(result).isEqualTo("00");
        assertThat(capturedUser).isNotNull(); // Adjust based on your expectations
    }


    @Test
    void login() {
        String email = "test@gmail.com";
        String password = "password123";

        AppUser user = new AppUser(email, password, "First", "Last");
        when(userRepo.existsById(email)).thenReturn(true);
        when(userRepo.findById(email)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDTO.class)).thenReturn(new UserDTO(email, password, "First", "Last"));

        UserDTO result = underTest.login(password, email);

        assertThat(result).isNotNull();
        assertThat(result.getE_mail()).isEqualTo(email);
        assertThat(result.getPassword()).isEqualTo(password);
        assertThat(result.getUser_FName()).isEqualTo("First");
        assertThat(result.getUser_LName()).isEqualTo("Last");
    }
}