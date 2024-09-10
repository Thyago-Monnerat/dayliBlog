package com.dayliBlog.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class IsAdmin {
    @Value("${api.admin}")
    private String admin;

    public boolean checkAdmin(String name) {
        return name.equals(this.admin);
    }
}
