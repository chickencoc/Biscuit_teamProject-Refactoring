package com.project.biscuit.domain.book.controller;

import com.project.biscuit.domain.book.dto.BookResponseDto;
import com.project.biscuit.domain.book.service.BookService;
import com.project.biscuit.domain.user.service.UserBookclipService;
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

    // 도서 검색
    @GetMapping("/list")
    public String bookList(@RequestParam String title,
                           @RequestParam int display,
                           @RequestParam int start,
                           @RequestParam String sort) {
        return bookService.searchBooks(title, display, start, sort);
    }

    // 도서 조회
    @GetMapping("/info/{isbn}")
    public ResponseEntity<BookResponseDto> bookInfo(@PathVariable String isbn,
                                                    @RequestParam String userId) throws IOException, InterruptedException {
        return ResponseEntity.ok(bookService.searchBookInfo(isbn, userId));
    }

    // 북클립 추가
    @PostMapping("/user/{userNo}/clip/{isbn}")
    public String bookClip(@PathVariable long userNo,
                           @PathVariable String isbn) {
        return bookService.inClip(userNo, isbn);
    }

    // 북클립 삭제
    @DeleteMapping("/user/{userNo}/delclip/{bookNo}")
    public String delBookClip(@PathVariable long userNo,
                              @PathVariable long bookNo) {
        return userBookclipService.delMyClip(bookNo, userNo);
    }
}
