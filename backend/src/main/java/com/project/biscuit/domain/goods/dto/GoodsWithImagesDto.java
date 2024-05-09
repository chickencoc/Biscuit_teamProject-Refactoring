package com.project.biscuit.domain.goods.dto;

import com.project.biscuit.domain.goods.entity.Goods;
import com.project.biscuit.domain.images.entity.Images;
import lombok.Getter;

@Getter
public class GoodsWithImagesDto {
    private Goods goods;
    private Images images;

    public GoodsWithImagesDto(Goods goods, Images images ) {
        this.goods = goods;
        this.images = images;
    }
}
