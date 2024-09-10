package com.dayliBlog.dtos;

import java.time.LocalDate;

public record NewPostDTO(String title, String imgUrl, String content, LocalDate date) {
}
