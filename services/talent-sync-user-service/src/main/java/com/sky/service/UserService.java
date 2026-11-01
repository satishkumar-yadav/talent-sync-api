package com.sky.service;

import java.util.List;

import com.sky.dto.response.UserResponse;
import com.sky.modal.User;
import com.sky.payload.UpdateUserRequest;

public interface UserService {
    
    User getUserByEmail(String email) throws Exception;

    User getUserById(Long id) throws Exception;

    List<User> getAllUsers();

    UserResponse updateProfile(String email, UpdateUserRequest req) throws Exception;

    //admin action
    UserResponse suspendUser(Long id) throws Exception;
    UserResponse activateUser(Long id) throws Exception;
    UserResponse deleteUser(Long id) throws Exception;

}
