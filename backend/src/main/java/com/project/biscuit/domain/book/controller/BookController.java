package com.project.biscuit.domain.book.controller;

import com.project.biscuit.domain.bookclip.dto.BookClipRequestDto;
import com.project.biscuit.domain.book.dto.BookRequestDto;
import com.project.biscuit.domain.book.dto.BookResponseDto;
import com.project.biscuit.domain.user.service.UserBookclipService;
import com.project.biscuit.domain.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final UserBookclipService userBookclipService;

    @PostMapping("/list")
    public String bookList(@RequestBody BookRequestDto req) {
        return bookService.searchBooks(req);
    }

    @PostMapping("/info")
    public ResponseEntity<BookResponseDto> bookInfo(@RequestBody BookRequestDto req) throws IOException, InterruptedException {
        return ResponseEntity.ok(bookService.searchBookInfo(req));
    }

    @PostMapping("/clip")
    public String bookClip(@RequestBody BookRequestDto req) {
        return bookService.inClip(req);
    }

    @PutMapping("/delclip")
    public String delBookClip(@RequestBody BookClipRequestDto req) {
        return userBookclipService.delMyClip(req);
    }
}
