package com.spring.yoon.football.converter;

import com.spring.yoon.football.enums.paytype.PayType;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PayTypeConverter implements AttributeConverter<PayType,String> {
    @Override
    public String convertToDatabaseColumn(PayType attribute) {
        return (attribute !=null) ? attribute.getType().trim() : PayType.NULL.getType();
    }

    @Override
    public PayType convertToEntityAttribute(String dbData) {
        return PayType.convertPayType(dbData);
    }
}
