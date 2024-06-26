package com.project.biscuit.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FindPwUpdateDto {
    private String password;

    @Builder
    public FindPwUpdateDto(String password){
        this.password = password;
    }
    public FindPwUpdateDto() {
        // 기본 생성자
    }
}
