package com.sky.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sky.domain.UserStatus;
import com.sky.dto.response.UserResponse;
import com.sky.mapper.UserMapper;
import com.sky.modal.User;
import com.sky.payload.UpdateUserRequest;
import com.sky.repository.UserRepository;
import com.sky.service.UserService;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository; 

    @Override
    public User getUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user==null){
            throw new Exception("user not found");
        }

        return user;
    }

    @Override
    public User getUserById(Long id) throws Exception {
       return userRepository.findById(id).orElseThrow(
             ()-> new Exception("user not found")
       );
    }

    @Override
    public List<User> getAllUsers() {
       return userRepository.findAll();
    }

    @Override
    public UserResponse updateProfile(String email, UpdateUserRequest req) throws Exception {
       User user = getUserByEmail(email);
       if(req.getFullName()!=null){user.setFullName(req.getFullName());}
       if(req.getPhone()!=null){user.setPhone(req.getPhone());}
       if(req.getProfileImage()!=null){user.setProfileImage(req.getProfileImage());}
       return UserMapper.toDTO(userRepository.save(user));
    }

    //admin service

    @Override
    public UserResponse suspendUser(Long id) throws Exception {
        User user = getUserById(id);
        user.setStatus(UserStatus.SUSPENDED);
        user.setSuspendedAt(LocalDateTime.now());
        return UserMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserResponse activateUser(Long id) throws Exception {
        User user = getUserById(id);
        user.setStatus(UserStatus.ACTIVE);
        user.setSuspendedAt(null);
        return UserMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserResponse deleteUser(Long id) throws Exception {
        User user = getUserById(id);
        user.setStatus(UserStatus.DELETED);
        user.setDeletedAt(LocalDateTime.now());
        return UserMapper.toDTO(userRepository.save(user));
    }
    
}
