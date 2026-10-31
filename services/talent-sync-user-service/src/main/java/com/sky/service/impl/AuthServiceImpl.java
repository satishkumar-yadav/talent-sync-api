package com.sky.service.impl;

import java.time.LocalDateTime;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sky.domain.UserRole;
import com.sky.domain.UserStatus;
import com.sky.mapper.UserMapper;
import com.sky.modal.User;
import com.sky.payload.AuthResponse;
import com.sky.payload.LoginRequest;
import com.sky.payload.SignupRequest;
import com.sky.repository.UserRepository;
import com.sky.security.CustomUserDetailsService;
import com.sky.security.JwtProvider;
import com.sky.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class AuthServiceImpl implements AuthService {
    
    private final CustomUserDetailsService customUserDetailsService;
     private final JwtProvider jwtProvider;   //
     private final PasswordEncoder passwordEncoder;
     private final UserRepository userRepository;

    //  AuthServiceImpl(PasswordEncoder passwordEncoder) {
    //     this.passwordEncoder = passwordEncoder;
    //  }

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
                    .password(passwordEncoder.encode(req.getPassword()))
                    .role(req.getRole())
                    .phone(req.getPhone())
                    .lastLogin(LocalDateTime.now())
                    .status(UserStatus.ACTIVE)
                    .build();

        User savedUser = userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication, savedUser.getId());  // jwtProvider directly
        
        AuthResponse res = new AuthResponse();
        res.setTitle("Welcome "+savedUser.getFullName());
        res.setMessage("Registered Successfully");
        res.setJwt("jwt");
        res.setUser(UserMapper.toDTO(savedUser));

       // userRepository
        return res;
     }

     @Override
     public AuthResponse login(LoginRequest req) throws Exception {

         Authentication authentication = authenticate(req.getEmail(), req.getPassword());
         SecurityContextHolder.getContext().setAuthentication(authentication);

         User user = userRepository.findByEmail(req.getEmail());

         String jwt = jwtProvider.generateToken(authentication, user.getId());

         user.setLastLogin(LocalDateTime.now());
         userRepository.save(user);

         AuthResponse res = new AuthResponse();
         res.setTitle("Welcome " + user.getFullName());
         res.setMessage("Registered Successfully");
         res.setJwt("jwt");
         res.setUser(UserMapper.toDTO(user));

         return null;
     }

     private Authentication authenticate(String email, String password) throws Exception{
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        if(userDetails==null){
            throw new Exception("user not found with email " + email);
        }
        
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new Exception("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
     }
}
