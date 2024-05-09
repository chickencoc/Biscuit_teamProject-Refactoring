package com.project.biscuit.domain.bookclip.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.biscuit.domain.bookclip.entity.Bookclip;
import com.project.biscuit.domain.book.entity.Book;
import com.project.biscuit.domain.book.view.BookclipAndBookcnt;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BookClipResponseDto {
    private Long no;
    private String delYn;
    private Book book;
    private long bcnt;
    @JsonFormat(pattern = "yyyy.MM.dd hh:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy.MM.dd hh:mm", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    public BookClipResponseDto(Bookclip bkc, BookclipAndBookcnt bkcabc) {
        this.no = bkc.getNo();
        this.delYn = bkc.getDelYn();
        this.book = bkc.getBook();
        this.bcnt = bkcabc.getBcnt();
        this.createdAt = bkc.getCreatedAt();
        this.updatedAt = bkc.getUpdatedAt();
    }
    public BookClipResponseDto(Bookclip bkc) {
        this.no = bkc.getNo();
        this.delYn = bkc.getDelYn();
        this.book = bkc.getBook();
        this.createdAt = bkc.getCreatedAt();
        this.updatedAt = bkc.getUpdatedAt();
    }
}
