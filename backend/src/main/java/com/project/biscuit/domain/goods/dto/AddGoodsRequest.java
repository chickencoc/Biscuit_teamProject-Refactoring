package com.project.biscuit.domain.goods.dto;

import com.project.biscuit.domain.goods.entity.Goods;
import com.project.biscuit.domain.images.entity.Images;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddGoodsRequest {
    private Long no;
    private String name;
    private Long price;
    private String content;
    private String sale_yn;
    private Long inventory;
    private Images images_no;

    public Goods toEntity() {
        return Goods.builder()
                .no(no)
                .name(name)
                .price(price)
                .content(content)
                .sale_yn(sale_yn)
                .inventory(inventory)
                .images(images_no)
                .build();

    }
}
