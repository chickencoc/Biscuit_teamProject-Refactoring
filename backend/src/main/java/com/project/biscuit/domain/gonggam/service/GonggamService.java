package com.project.biscuit.domain.gonggam.service;

import com.project.biscuit.domain.booklog.entity.BooklogArticle;
import com.project.biscuit.domain.gonggam.entity.Gonggam;
import com.project.biscuit.domain.user.entity.User;
import com.project.biscuit.domain.gonggam.repository.GonggamRepository;
import com.project.biscuit.domain.booklog.service.BooklogArticleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GonggamService {
    private final GonggamRepository gonggamRepository;
    private final BooklogArticleService booklogArticleService;

    // 좋아요 추가
    public Gonggam addGonggam(User user, BooklogArticle article) {
        Gonggam gonggam = Gonggam.builder()
                .user(user)
                .booklogArticle(article)
                .build();
        return gonggamRepository.save(gonggam);
    }

    // 좋아요 삭제
    @Transactional
    public void removeGonggam(User user, BooklogArticle article) {
        gonggamRepository.deleteByUserAndBooklogArticle(user, article);
    }

    // 좋아요 여부
    public boolean isGonggamed(User user, BooklogArticle article) {
        return gonggamRepository.existsByUserAndBooklogArticle(user, article);
    }
}
