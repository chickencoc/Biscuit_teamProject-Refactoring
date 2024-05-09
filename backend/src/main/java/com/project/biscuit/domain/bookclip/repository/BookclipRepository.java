package com.project.biscuit.domain.bookclip.repository;

import com.project.biscuit.domain.bookclip.entity.Bookclip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookclipRepository extends JpaRepository<Bookclip, Long> {

    /**
     * 책 번호, 유저 번호, 삭제 여부로 클립 검색
    */
    boolean existsByBook_NoAndUser_NoAndDelYn(Long bookNo, Long userNo, String delYn);

    /**
     * 책 번호, 유저 번호로 클립 검색
     */
    Optional<Bookclip> findByBook_NoAndUser_No(Long bookNo, Long userNo);

    /**
     * 유저 아이디로 클립 검색
     */
    List<Bookclip> findByDelYnAndUser_NoOrderByUpdatedAtDesc(String delYn, long no);
    List<Bookclip> findByDelYnAndUser_NoOrderByBook_Price(String delYn, long no);
    List<Bookclip> findByDelYnAndUser_No(String delYn, long no);

}
