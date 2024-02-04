package com.example.BackEnd.Service;

import com.example.BackEnd.DTO.UserDTO;
import com.example.BackEnd.Entity.AppUser;

import com.example.BackEnd.ExceptionHandler.UserException;
import com.example.BackEnd.Repo.AppUserRepo;

import com.example.BackEnd.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Transactional
@Service
public class UserService {
    @Autowired
    private AppUserRepo AppuserRepo;

    @Autowired
    private ModelMapper modelMapper;


    public String sign_up(UserDTO userDTO) {
        try {
            if (!(AppuserRepo.count() == 0)) {
                AppUser user = new AppUser();
                if (AppuserRepo.existsById(userDTO.getE_mail())) {
                    return VarList.RSP_DUPLICATED;
                } else {
                    AppuserRepo.save(modelMapper.map(userDTO, AppUser.class));
                    return VarList.RSP_SUCCESS;
                }
            }
            AppuserRepo.save(modelMapper.map(userDTO, AppUser.class));
            return VarList.RSP_SUCCESS;
        } catch (Exception e) {
            throw new UserException("Error signing up user: " + e.getMessage());
        }
    }



    public UserDTO login(String password,String email){
        try {
            if (AppuserRepo.existsById(email)) {
                AppUser user = AppuserRepo.findById(email).orElse(null);
                if (user.getE_mail().equals(email) && user.getPassword() != null && user.getPassword().equals(password)) {
                    return modelMapper.map(user, UserDTO.class);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new UserException("Error logging in: " + e.getMessage());
        }
    }
}
