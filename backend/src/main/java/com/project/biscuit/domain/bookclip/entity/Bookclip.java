package com.project.biscuit.domain.bookclip.entity;

import com.project.biscuit.global.jpaAuditing.BaseTimeEntity;
import com.project.biscuit.domain.book.entity.Book;
import com.project.biscuit.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Bookclip extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint comment '북클립번호'" )
    private Long no;

    @Builder.Default
    @Column(columnDefinition = "varchar(1) comment '삭제여부'" , nullable = false)
    private String delYn = "N";

    // join column
    @ManyToOne
    @JoinColumn(name = "Books_no")
    @ToString.Exclude
    private Book book;

    // join column
    @ManyToOne
    @JoinColumn(name = "User_no")
    @ToString.Exclude
    private User user;
}
