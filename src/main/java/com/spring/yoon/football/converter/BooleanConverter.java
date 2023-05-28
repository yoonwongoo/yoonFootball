package com.spring.yoon.football.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/*모든 boolean타입은 Y ,N 으로 관리*/
@Converter
public class BooleanConverter implements AttributeConverter<Boolean,String> {
    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return (attribute!=null && attribute) ? "Y" : "N" ;
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return "Y".equals(dbData);
    }
}
