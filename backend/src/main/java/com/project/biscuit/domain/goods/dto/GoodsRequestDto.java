package com.project.biscuit.domain.goods.dto;

import com.project.biscuit.domain.goods.entity.Goods;
import com.project.biscuit.domain.images.entity.Images;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsRequestDto {
    private Long no;
    private String content;
    private Long likes;
    private String name;
    private Long price;
    private String sale_yn;
    private Long inventory;
    private Images images;



    public Goods toEntity() {
        return Goods.builder()
                .no(no)
                .content(content)
                .likes(likes)
                .name(name)
                .price(price)
                .sale_yn(sale_yn)
                .inventory(inventory)
                .images(images)
                .build();
    }


}
