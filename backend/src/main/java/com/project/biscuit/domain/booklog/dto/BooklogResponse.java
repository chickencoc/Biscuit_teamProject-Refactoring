package com.project.biscuit.domain.booklog.dto;

import com.project.biscuit.domain.booklog.entity.Booklog;
import com.project.biscuit.domain.user.entity.User;
import lombok.Getter;

@Getter
public class BooklogResponse {


    private Long no;

    private String booklog_name;
    private Long today;
    private Long total;
    private Long suber;
    private String intro;
    private User user_no;

    public BooklogResponse(Booklog booklog){

        this.no = booklog.getNo();
        this.booklog_name = booklog.getBooklogName();
        this.today = booklog.getToday();
        this.total = booklog.getTotal();
        this.suber = booklog.getSuber();
        this.intro = booklog.getIntro();
        this.user_no = booklog.getUser();
    }
}
