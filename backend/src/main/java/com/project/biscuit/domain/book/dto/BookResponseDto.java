package com.project.biscuit.domain.book.dto;

import com.project.biscuit.domain.book.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponseDto {

    private Book book;
    private boolean cliped;


    public static BookResponseDto of(Book book, boolean clip) {

        return BookResponseDto.builder()
                .book(book)
                .cliped(clip)
                .build();
    }
}
