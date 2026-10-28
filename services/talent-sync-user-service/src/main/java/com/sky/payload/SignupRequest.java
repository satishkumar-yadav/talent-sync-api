package com.sky.payload;

import com.sky.domain.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
 
@Data // for getter setter
public class SignupRequest {
    
    @NotBlank(message = "full name is mandatory")
    private String fullName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "email is mandatory")
    private String email;

    @NotBlank(message = "password is mandatory")
    private String password;

    private String phone;

    @NotNull(message = "role is mandatory")
    private UserRole role;

    // Temporarily add these manually: // as lombok not working
    // public String getEmail() {
    //     return email;
    // }

    // public void setEmail(String email) {
    //     this.email = email;
    // }

}
