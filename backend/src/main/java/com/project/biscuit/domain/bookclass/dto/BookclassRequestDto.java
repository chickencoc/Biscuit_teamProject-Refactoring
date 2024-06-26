package com.project.biscuit.domain.bookclass.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.biscuit.domain.bookclass.entity.Bookclass;
import com.project.biscuit.domain.book.entity.Book;
import com.project.biscuit.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookclassRequestDto {
    private String title;
    private String intro;
    private String zipCode;
    private String location;
    private String locationDetail;
    private int memberCnt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime scheduleStart;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime scheduleEnd;
    private String userId;
    private String isbn;

    public Bookclass toEntity(User user, Book book) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return Bookclass.builder()
                .title(title)
                .intro(intro)
                .zipCode(zipCode)
                .location(location)
                .locationDetail(locationDetail)
                .memberCnt(memberCnt)
                .scheduleStart(scheduleStart)
                .scheduleEnd(scheduleEnd)
                .userNo(user)
                .bookNo(book)
                .build();
    }
}

