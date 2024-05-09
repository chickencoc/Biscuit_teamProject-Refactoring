package com.project.biscuit.domain.recycle.dto;

import com.project.biscuit.domain.book.entity.Book;
import com.project.biscuit.domain.recycle.entity.Recycle;
import com.project.biscuit.domain.user.entity.User;
import com.project.biscuit.domain.recycle.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AddRecycleRequestDto {

    private String title;  // 게시글 제목
    private String content; //게시글
    private String pickupAddress; //픽업 장소
    private String isbn; // 책 정보
    private String userId; // 게시글 작성자 아이디
    private Status status; //도서 상태

    public Recycle toEntity(User user, Book book) {

        return Recycle.builder()
                .title(title)
                .content(content)
                .pickupAddress(pickupAddress)
                .userNo(user)
                .bookNo(book)
                .status(status)
                .build();
    }
}
