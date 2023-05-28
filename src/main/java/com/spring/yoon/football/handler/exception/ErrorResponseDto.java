package com.spring.yoon.football.handler.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Getter
public class ErrorResponseDto {

    private String message;
    private int status;
    private String code;
    private List<FieldError> errors;

    /*필드 에러 메세지 있을때*/

    private ErrorResponseDto(ErrorCode errorCode, List<FieldError> errors ){
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.errors=errors;
    }

    private ErrorResponseDto(ErrorCode errorCode){
          this.message = errorCode.getMessage();
          this.status = errorCode.getStatus();
          this.code = errorCode.getCode();
          this.errors= new ArrayList<>();
      }

    /*필드 에러 메세지 있을때*/
    public static ErrorResponseDto of(ErrorCode errorCode, Errors errors){

        return new ErrorResponseDto(errorCode,FieldError.of(errors));
    }
    /*필드 에러 메세지 없을 경우 error부분 빈객체 나간다*/
    public static ErrorResponseDto of(ErrorCode errorCode){
    
        return new ErrorResponseDto(errorCode);
    }
    /*내가 필드 에러메세지 만들때*/
    public static ErrorResponseDto of(ErrorCode errorCode,String errorField, String errorValue, String errorMessage){
        List<FieldError> errors = FieldError.of(errorField,errorValue,errorMessage);
        return new ErrorResponseDto(errorCode,errors);
    }




    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {


      private String errorField;
      private String errorValue;
      private String errorMessage;

      private FieldError(String errorField, String errorValue, String errorMessage) {
          this.errorField = errorField;
          this.errorValue = errorValue;
          this.errorMessage = errorMessage;
      }
      /*내가 에러를 직접 만들어 줄 수 있다*/
      public static List<FieldError> of(String errorField,  String errorValue,  String errorMessage) {
          List<FieldError> fieldErrors = new ArrayList<>();
          fieldErrors.add(new FieldError(errorField, errorValue, errorMessage));
          return fieldErrors;
      }
        /*validation bindResult을 의존하여 error*/
      private static List<FieldError> of(Errors errors) {
          final List<org.springframework.validation.FieldError> fieldErrors = errors.getFieldErrors();
          return fieldErrors.stream()
                  .map(error -> new FieldError(
                          error.getField(),
                          error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                          error.getDefaultMessage()))
                  .distinct()
                  .collect(Collectors.toList());
      }

        @Override
        public boolean equals(Object o) {
            if(o instanceof FieldError){
                return this.errorField.equals(((FieldError) o).getErrorField());
            } else if (this == o) {
                return true;
            }else if (o == null || getClass() != o.getClass()){
                return false;
            }

            return false;
        }

        @Override
        public int hashCode() {
            return this.errorField.hashCode();
        }
    }




}
