package com.dayliBlog.controllers;

import com.dayliBlog.models.UserModel;
import com.dayliBlog.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getAllUsers());
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<UserModel> getUser(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getUser(id));
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.deleteUser(id));
    }

    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<String> deletePost(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.deletePost(id));
    }
}
