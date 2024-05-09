package com.project.biscuit.domain.booklog.service;

import com.project.biscuit.domain.book.repository.BookRepository;
import com.project.biscuit.domain.booklog.entity.BooklogArticle;
import com.project.biscuit.domain.booklog.repository.BooklogArticleRepository;
import com.project.biscuit.domain.booklog.repository.BooklogRepository;
import com.project.biscuit.domain.comment.repository.CommentRepository;
import com.project.biscuit.domain.user.repository.UserRepository;
import com.project.biscuit.domain.book.entity.Book;
import com.project.biscuit.domain.user.entity.User;
import com.project.biscuit.domain.booklog.dto.AddBooklogArticleRequest;
import com.project.biscuit.domain.booklog.dto.UpdateBooklogArticleRequest;
import com.project.biscuit.domain.user.model.Grade;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BooklogArticleService {
    private final BooklogArticleRepository booklogArticleRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BooklogRepository booklogRepository;
    private final CommentRepository commentRepository;


    // 북로그 게시글 만들기
    public BooklogArticle save(AddBooklogArticleRequest request) throws NotFoundException {

        // Find the Books entity by its no
        Book book = bookRepository.findById(request.getBook_no())
                .orElseThrow(() -> new NotFoundException("Books not found"));

        // Find the User entity by its no
        User user = userRepository.findById(request.getUser_no())
                .orElseThrow(() -> new NotFoundException("User not found"));


        BooklogArticle article = request.toEntity();

        article.setBook(book);
        article.setUser(user);
        user.setPoint(user.getPoint() + 100);

        if(user.getPoint() >= 500) {
            user.setGrade(Grade.L);
        }
        
        return booklogArticleRepository.save(article);
    }

    // user_no 기준 게시글 가져오기
    public List<BooklogArticle> getArticlesByUserNo(Long userNo) {
        return booklogArticleRepository.findByUserNo(userNo);
    }

    // 북로그 게시글에 책 가져오기
    public List<Book> findBooksAll(){
        return bookRepository.findAll();
    }
    
    // 북로그 게시글에서 책 검색하기
    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContaining(title);
    }

    // 북로그 게시글 전체 리스트 뽑기
    public List<BooklogArticle> findAll() {
        return booklogArticleRepository.findAll();
    }
    
    // 북로그 게시글 하나만 가져오기 !!!! 이거 게시글 실제로 가져오는 메소드
    public BooklogArticle findById(long no) {
        BooklogArticle article = booklogArticleRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException(no + " : 를 찾을 수 없습니다."));

        article.incrementViewCount(); // 조회수 증가
        booklogArticleRepository.save(article); // 업데이트된 조회수 저장

        return article;
    }

    // 북로그 게시글 수정하기 (update)
    @Transactional
    public BooklogArticle update(long no , UpdateBooklogArticleRequest request) {
        BooklogArticle booklogArticle = booklogArticleRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException(no + " : 를 찾을수 없습니다."));

        // 수정할 때, books_no 대신에 Books 객체를 받아와서 업데이트 메소드 호출
        Book book = bookRepository.findById(request.getBook_no())
                .orElseThrow(() -> new IllegalArgumentException("해당 Books를 찾을 수 없습니다."));


        booklogArticle.update(
                request.getTitle(),
                request.getContent(),
                request.getGroups(),
                request.getKinds(),
                book
                );

        return booklogArticle;
    }

    // 북로그 게시글 리스트 페이지 나누기
    public Page<BooklogArticle> findAll(Pageable pageable) {
        Page<BooklogArticle> pageBooklogArticle = booklogArticleRepository.findAll(pageable);
        return pageBooklogArticle;
    }

    // 북로그 공감
    public BooklogArticle saveLikes(BooklogArticle article) {
        return booklogArticleRepository.save(article);
    }

    // groups 별 user_no 기준 북로그 게시글
    public List<BooklogArticle> getArticlesByGroupsAndUserNo(String groups, Long userNo) {
        return booklogArticleRepository.findByGroupsAndUserNo(groups, userNo);
    }

    // 북로그 게시글 검색 기능 ( 페이지 )
    public List<BooklogArticle> searchArticles(String keyword) {
        return booklogArticleRepository.findByTitleContainingOrContentContainingOrderByCreatedAtDesc(keyword, keyword);
    }

    // 북로그 게시글 삭제 기능 사용자용
    @Transactional
    public String delMyBookLog(Long booklogNo) {
        commentRepository.deleteAllByBooklogArticle_No(booklogNo);
        booklogArticleRepository.deleteById(booklogNo);
        return "success";
    }
}

