package com.dayliBlog.controllers;


import com.dayliBlog.dtos.UserLoginDTO;
import com.dayliBlog.dtos.UserRegisterDTO;

import com.dayliBlog.exceptions.UserAlreadyExistsException;
import com.dayliBlog.services.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.userService.login(userLoginDTO));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.register(userRegisterDTO));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


}
