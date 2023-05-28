package com.spring.yoon.football.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Converter
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime,String> {

    @Override
    public String convertToDatabaseColumn(LocalDateTime attribute) {
        return attribute.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String dbData) {
        return LocalDateTime.parse(dbData,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
