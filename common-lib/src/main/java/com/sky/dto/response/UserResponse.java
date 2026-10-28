package com.sky.dto.response;

import java.time.LocalDateTime;

import com.sky.domain.UserRole;
import com.sky.domain.UserStatus;

import lombok.Data;

@Data 
public class UserResponse {
    
    private Long id;
    private String fullName;
    // private String password;
    private String email;
    private String phone;
    private String profileImage;
    private UserRole role;
    private UserStatus status ; 
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;

}
