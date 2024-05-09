package com.project.biscuit.domain.event.entity;

import com.project.biscuit.domain.event.entity.Event;
import com.project.biscuit.global.jpaAuditing.BaseTimeEntity;
import com.project.biscuit.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

//  이벤트 참여 목록
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(callSuper=false)
public class Participation extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint comment '명단번호'")
    private Long no;

    @ManyToOne
    @ToString.Exclude
    private User user;

    @ManyToOne
    @ToString.Exclude
    private Event event;

}
