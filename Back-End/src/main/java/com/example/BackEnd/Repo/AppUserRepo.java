package com.example.BackEnd.Repo;

import com.example.BackEnd.Entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepo extends JpaRepository<AppUser,String> {
}
