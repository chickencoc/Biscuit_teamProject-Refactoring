package com.project.biscuit.domain.recycle.dto;

import com.project.biscuit.domain.recycle.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CampaignRequestDto {
    private Long recycleNo;
    private String acceptYn;
    private Status status;
    private Long discountRate;
    private Long salePrice;
}
