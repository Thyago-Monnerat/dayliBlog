package com.dayliBlog.controllers;

import com.dayliBlog.dtos.NewPicDTO;
import com.dayliBlog.dtos.UserChangePasswordDTO;
import com.dayliBlog.models.UserModel;
import com.dayliBlog.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/getMyAccount")
    public ResponseEntity<UserModel> getMyAccount(@RequestHeader(value = "Authorization") String auth) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getMyAccount(auth));
    }

    @DeleteMapping("/deleteMyAccount")
    public ResponseEntity<String> deleteMyAccount(@RequestHeader(value = "Authorization") String auth) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.deleteMyAccount(auth));

    }

    @PutMapping("/changeMyPassword")
    public ResponseEntity<String> changePassword(@RequestHeader(value = "Authorization") String auth, @RequestBody UserChangePasswordDTO userChangePasswordDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.changeMyPassword(auth, userChangePasswordDTO));
    }

    @PutMapping("/changeMyPic")
    public ResponseEntity<String> changePassword(@RequestHeader(value = "Authorization") String auth, @RequestBody NewPicDTO newPic) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.changeMyPic(auth, newPic));
    }
}