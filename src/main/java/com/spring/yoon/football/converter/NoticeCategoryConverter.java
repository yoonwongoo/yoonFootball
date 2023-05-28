package com.spring.yoon.football.converter;

import com.spring.yoon.football.enums.noticecategory.NoticeCategory;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class NoticeCategoryConverter implements AttributeConverter<NoticeCategory,String> {
    @Override
    public String convertToDatabaseColumn(NoticeCategory attribute) {
        return attribute.getValue();
    }

    @Override
    public NoticeCategory convertToEntityAttribute(String dbData) {
        return NoticeCategory.convertNoticeCategory(dbData);
    }
}
