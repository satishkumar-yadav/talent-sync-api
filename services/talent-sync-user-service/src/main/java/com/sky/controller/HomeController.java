package com.sky.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.domain.UserRole;


@RestController
public class HomeController {
    @GetMapping
    public String HomeController() {
        return "TalentSync User Service"+UserRole.ROLE_JOB_SEEKER+" ....";
    }
    
}
  