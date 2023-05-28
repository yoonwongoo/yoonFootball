package com.spring.yoon.football.domain;


import com.spring.yoon.football.converter.LocalDateTimeConverter;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {
    @Convert(converter = LocalDateTimeConverter.class)
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;


    @Convert(converter = LocalDateTimeConverter.class)
    @LastModifiedDate
    @Column
    private LocalDateTime modifiedDate;;

    public BaseTimeEntity() {
    }
}
