package com.project.biscuit.domain.booklog.dto;

import com.project.biscuit.domain.booklog.entity.Booklog;
import com.project.biscuit.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddBooklogRequest {

    private String booklog_name;
    private String intro;
    private User user_no;

    public Booklog toEntity() {
        return Booklog.builder()
                .booklogName(booklog_name)
                .intro(intro)
                .user(user_no)
                .build();
    }
}
