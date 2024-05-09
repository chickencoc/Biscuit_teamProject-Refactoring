package com.project.biscuit.domain.images.dto;

import com.project.biscuit.domain.images.entity.Images;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddImagesRequest {
    private String img_name;
    private String img_path;
    private String thumbnail_yn;

    public Images toEntity() {
        return Images.builder()
                .imgName(img_name)
                .imgPath(img_path)
                .thumbnailYn(thumbnail_yn)
                .build();
    }
}
