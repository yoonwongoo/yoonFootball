package com.spring.yoon.football.converter;

import lombok.NoArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

@NoArgsConstructor
public class EnumConvertorFactory implements ConverterFactory<String, Enum<?>> {


    @Override
    public <T extends Enum<?>> Converter<String, T> getConverter(Class<T> targetType) {

        return new EnumConvertor(targetType);
    }

    public static class EnumConvertor<T extends Enum<T>>  implements Converter<String,T> {
        private Class<T> enumType;
        public EnumConvertor(Class<T> enumType){
            this.enumType=enumType;
        }

        @Override
        public T convert(String source) {
            return source.isEmpty() ? null : Enum.valueOf(this.enumType, source.trim().toUpperCase());
        }
    }

}
