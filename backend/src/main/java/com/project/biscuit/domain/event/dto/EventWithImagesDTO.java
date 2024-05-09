package com.project.biscuit.domain.event.dto;

import com.project.biscuit.domain.event.entity.Event;
import com.project.biscuit.domain.images.entity.Images;
import lombok.Getter;

@Getter
public class EventWithImagesDTO {
    private Event event;
    private Images images;

    public EventWithImagesDTO(Event event , Images images ) {
        this.event = event;
        this.images = images;
    }
}
