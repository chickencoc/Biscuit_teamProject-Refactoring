package com.project.biscuit.domain.gonggam.entity;

import com.project.biscuit.domain.booklog.entity.BooklogArticle;
import com.project.biscuit.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(callSuper=false)
public class Gonggam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint comment '공감번호'")
    private Long no;

    @ManyToOne
    @ToString.Exclude
    private User user;

    @ManyToOne
    @ToString.Exclude
    private BooklogArticle booklogArticle;
}
