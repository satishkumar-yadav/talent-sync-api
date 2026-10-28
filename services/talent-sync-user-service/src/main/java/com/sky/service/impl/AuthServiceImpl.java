package com.sky.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.sky.domain.UserRole;
import com.sky.domain.UserStatus;
import com.sky.mapper.UserMapper;
import com.sky.modal.User;
import com.sky.payload.AuthResponse;
import com.sky.payload.LoginRequest;
import com.sky.payload.SignupRequest;
import com.sky.repository.UserRepository;
import com.sky.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class AuthServiceImpl implements AuthService {
    
     private final UserRepository userRepository;

    // AuthServiceImpl(UserRepository userRepository) {
    //     this.userRepository = userRepository;
    // }

     @Override 
     public AuthResponse signup(SignupRequest req) throws Exception {

        // if(userRepository.findByEmail(req.getEmail()) !=null){
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new Exception("Email Already Registered "+req.getEmail());
        }

        if(req.getRole()==UserRole.ROLE_ADMIN){
            throw new Exception("cannot self-register as role admin");
        }

        User user = User.builder()
                    .fullName(req.getFullName())
                    .email(req.getEmail())
                    .password(req.getPassword())
                    .role(req.getRole())
                    .phone(req.getPhone())
                    .lastLogin(LocalDateTime.now())
                    .status(UserStatus.ACTIVE)
                    .build();

        User savedUser = userRepository.save(user);
        
        AuthResponse res = new AuthResponse();
        res.setTitle("Welcome "+savedUser.getFullName());
        res.setMessage("Registered Successfully");
        res.setJwt("jwt");
        res.setUser(UserMapper.toDTO(savedUser));

       // userRepository
        return res;
     }

     @Override
     public AuthResponse login(LoginRequest req) {
         return null;
     }
}
