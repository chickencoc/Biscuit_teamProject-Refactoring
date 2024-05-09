package com.project.biscuit.domain.recycle.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SaleCampaignRequestDto {

    private String saleYn;

    @Builder
    public SaleCampaignRequestDto(String saleYn){
        this.saleYn = saleYn;
    }
    public SaleCampaignRequestDto() {
        // 기본 생성자
    }


}
