package com.project.biscuit.domain.images.dto;

import com.project.biscuit.domain.event.entity.Event;
import com.project.biscuit.domain.goods.entity.Goods;
import com.project.biscuit.domain.images.entity.Images;
import lombok.Getter;

//import java.awt.*;
@Getter
public class ImagesResponse {
    private Long no;
    private String img_name;
    private String img_path;
    private String thumbnail_yn;
    private Event event_no;
    private Goods goods_no;

    public ImagesResponse (Images images) {
        this.no = images.getNo();
        this.img_name = images.getImgName();
        this.img_path = images.getImgPath();
        this.thumbnail_yn = images.getThumbnailYn();
        this.event_no = images.getEvent();
        this.goods_no = images.getGoods();
    }
}
