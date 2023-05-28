package com.spring.yoon.football.controller.validator.enumpattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<EnumPattern,Enum<?>> {
    private EnumPattern constraintAnnotation;

    @Override
    public void initialize(EnumPattern constraintAnnotation) {
        this.constraintAnnotation=constraintAnnotation;
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        /*해당 Enum<?>  value에는 입력한 value가 들어온다*/

        if(value==null){
            return false;
            /*검증 실패*/
        }
        Object[] enumContent = this.constraintAnnotation.enumClass().getEnumConstants();
        for(Object enums : enumContent){
            if(value.name()==String.valueOf(enums)){
                return true;
            }
        }

        return false;
    }
}
