package com.project.biscuit.domain.images.repository;

import com.project.biscuit.domain.images.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImagesRepository extends JpaRepository<Images, Long> {
    Optional<Images> findByImgName(String imgName);

    Optional<Images> findByGoods_NoAndThumbnailYn(Long no, String yn);
}
