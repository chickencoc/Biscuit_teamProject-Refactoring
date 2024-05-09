package com.project.biscuit.domain.event.dto;

import com.project.biscuit.domain.images.entity.Images;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequest {
    private String title;
    private String content;
    private String del_yn;
    private Long cnt;
    private Long likes;
    private Images images_no;
    private String event_type;
    private LocalDateTime event_start;
    private LocalDateTime event_end;
}
