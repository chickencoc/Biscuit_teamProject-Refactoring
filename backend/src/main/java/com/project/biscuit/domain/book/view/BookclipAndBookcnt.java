package com.project.biscuit.domain.book.view;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Entity
@Immutable
// @Immutable 또는 @Subselect(JPQL)
@Getter
public class BookclipAndBookcnt {

    @Id
    private Long no;
    private String delYn;
    private long bookNo;
    private long userNo;
    private long bcnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
