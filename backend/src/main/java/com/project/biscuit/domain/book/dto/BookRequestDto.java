package com.project.biscuit.domain.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookRequestDto {
    private String title;
    private String isbn;
    private int display;
    private int start;
    private String sort;
    private String userId;
}
