package com.example.springsecurity.Service;

import com.example.springsecurity.Model.User;
import com.example.springsecurity.Repo.UserRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public String saveUser(User user){
        try {
            userRepo.save(user);
            return null;
        }catch (Exception e){
            return e.getMessage();
        }
    }
}
