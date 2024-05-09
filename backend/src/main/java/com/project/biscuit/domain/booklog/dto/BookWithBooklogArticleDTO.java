package com.project.biscuit.domain.booklog.dto;

import com.project.biscuit.domain.booklog.entity.BooklogArticle;
import com.project.biscuit.domain.book.entity.Book;
import lombok.Getter;

@Getter
public class BookWithBooklogArticleDTO {
    private Book book;
    private BooklogArticle booklogArticle;

    public BookWithBooklogArticleDTO(BooklogArticle booklogArticle , Book book) {
        this.booklogArticle = booklogArticle;
        this.book = book;
    }
}
