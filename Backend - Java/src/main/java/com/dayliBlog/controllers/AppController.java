package com.dayliBlog.controllers;

import com.dayliBlog.services.AppService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AppController {
    private final AppService appService;

    @GetMapping("/get")
    public ResponseEntity<List<String>> getInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(this.appService.getInfo());
    }
}
