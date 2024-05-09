package com.project.biscuit.domain.book.repository;

import com.project.biscuit.domain.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);

    // 북로그 게시글 에서 책 검색하기
    List<Book> findByTitleContaining(String title);
}
