package com.project.biscuit.domain.booklog.dto;

import com.project.biscuit.domain.booklog.entity.Booklog;
import com.project.biscuit.domain.booklog.entity.BooklogArticle;
import com.project.biscuit.domain.book.entity.Book;
import com.project.biscuit.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddBooklogArticleRequest {

    private Long no;
    private String title;
    private String content;
    private Long likes = 0L;
    private Long cnt = 0L;
    private String groups;
    private String kinds;
    private Long booklog_no;
    private Long book_no;
    private Long user_no;

    public void setUser_no(Long user_no) {
        this.user_no = user_no;
    }
    public BooklogArticle toEntity() {
        BooklogArticle article = BooklogArticle.builder()
                .no(no)
                .title(title)
                .content(content)
                .likes(likes)
                .cnt(cnt)
                .groups(groups)
                .kinds(kinds)
                .book(Book.builder().no(book_no).build()) // book_no 넘겨주기
                .user(User.builder().no(user_no).build())   // user_no 넘겨주기
                .build();

        // Booklog 객체 생성과 연결
        if (booklog_no != null) {
            Booklog booklog = new Booklog();
            booklog.setNo(booklog_no);
            article.setBooklog(booklog);
        }

        return article;
    }
}
