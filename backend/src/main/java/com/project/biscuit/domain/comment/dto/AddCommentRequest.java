package com.project.biscuit.domain.comment.dto;

import com.project.biscuit.domain.booklog.entity.BooklogArticle;
import com.project.biscuit.domain.comment.entity.Comment;
import com.project.biscuit.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddCommentRequest {

    private Long user_no = 1L;
    private Long booklog_article_no;
    private String content;
    private String del_yn = "N";
    private Long upcomment_no;

    public Comment toEntity() {
        return Comment.builder()
                .user(User.builder().no(user_no).build())
                .booklogArticle(BooklogArticle.builder().no(booklog_article_no).build())
                .content(content)
                .delYn(del_yn)
                .upcommentNo(upcomment_no)
                .build();
    }

}
