package com.project.biscuit.domain.comment.dto;

import com.project.biscuit.domain.booklog.entity.BooklogArticle;
import com.project.biscuit.domain.comment.entity.Comment;
import com.project.biscuit.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {
    private Long no;

    private User user_no;
    private BooklogArticle booklog_article;
    private String content;
    private String del_yn;
    private Long upcomment_no;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public CommentResponse (Comment comment) {
        this.no = comment.getNo();

        this.user_no = comment.getUser();
        this.booklog_article = comment.getBooklogArticle();
        this.content = comment.getContent();
        this.del_yn = comment.getDelYn();
        this.upcomment_no = comment.getUpcommentNo();

        this.created_at = comment.getCreatedAt();
        this.updated_at = comment.getUpdatedAt();
    }
}
