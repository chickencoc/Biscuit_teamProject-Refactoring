package com.project.biscuit.domain.book.view;

import com.project.biscuit.domain.book.view.BookclipAndBookcnt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookclipAndBookcntRepository extends JpaRepository<BookclipAndBookcnt, Long> {

    List<BookclipAndBookcnt> findByUserNoOrderByBcntDescUpdatedAtDesc(long no);
}
