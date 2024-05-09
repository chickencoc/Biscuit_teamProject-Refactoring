package com.project.biscuit.domain.booklog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateBooklogRequest {
    private String booklog_name;
    private String intro;
}
