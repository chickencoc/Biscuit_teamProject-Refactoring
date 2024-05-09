package com.project.biscuit.domain.booklog.dto;

import com.project.biscuit.domain.booklog.entity.Booklog;
import com.project.biscuit.domain.booklog.entity.BooklogArticle;
import lombok.Getter;
// Booklog 와 BooklogArticle Join 시켜주는 DTO
@Getter
public class BooklogArticleWithBooklogInfo {

    private BooklogArticle article;
    private Booklog booklog;

    public BooklogArticleWithBooklogInfo(BooklogArticle article, Booklog booklog) {
        this.article = article;
        this.booklog = booklog;
    }
}