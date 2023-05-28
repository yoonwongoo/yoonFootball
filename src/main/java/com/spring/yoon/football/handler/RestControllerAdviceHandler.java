package com.spring.yoon.football.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.spring.yoon.football.dto.ResponseDto;
import com.spring.yoon.football.handler.exception.ErrorCode;
import com.spring.yoon.football.handler.exception.ErrorResponseDto;
import com.spring.yoon.football.handler.exception.customserviceexception.CustomServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class RestControllerAdviceHandler{


    private final ObjectMapper ob;



    /*@pathvariable TypeMissMatch*/
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public  ModelAndView handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request, HttpServletResponse response){
        log.info("getName:{},getParameter:{}",e.getName(),e.getParameter());
        ErrorResponseDto errorResponseDto = ErrorResponseDto.of(ErrorCode.INVALID_TYPE_VALUE);

        if(acceptCheck(request)) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(errorResponseDto.getStatus());
            try {
                response.getWriter().write(ob.writeValueAsString(errorResponseDto));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return new ModelAndView();
        }
        return new ModelAndView("/error/"+errorResponseDto.getStatus());
    }


    /*혹시 모를 예외를 대비하여 runtime 전역처리*/
    @SuppressWarnings("unchecked")
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView exception(RuntimeException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("RuntimeException:::{}", e.toString());
        log.info("Cause:::{}",e.getCause());
        log.info("fillInStackTrace:::{}",e.fillInStackTrace());
        log.info("Message:::{}", e.getMessage());
        if(acceptCheck(request)){
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ResponseEntity responseEntity = new ResponseEntity<>(new ResponseDto<>(null,"일시적인 오류",HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
            response.getWriter().write(ob.writeValueAsString(responseEntity));
            return new ModelAndView();
        }
        return new ModelAndView("/error/500");
    }


    /*HTTP JSON 바인딩중 exception*/
   @ExceptionHandler(HttpMessageNotReadableException.class)
   public ResponseEntity<ResponseDto> httpMessageNotReadableException(HttpMessageNotReadableException e){
       log.info("HttpMessageNotReadableException:{}",e.toString());
        Throwable cause = e.getCause();
       Map<String,String> errorMap = new HashMap<>();
        if(cause instanceof InvalidFormatException ){
            String field =((InvalidFormatException) cause).getPath().get(0).getFieldName();
            System.out.println(((InvalidFormatException) cause).getValue().toString());
            errorMap.put(field,"잘못된 형식입니다");
        }


       return new ResponseEntity<>(new ResponseDto(errorMap,"잘못된 JSON 형식입니다",HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
   }




    /*HttpMessageConvertor 바인딩 후 validate 후 exception
    * 이건 api만 요청처리를 할 수있다 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> methodArgumentNotValidException(MethodArgumentNotValidException e){
        ErrorResponseDto errorResponseDto = ErrorResponseDto.of(ErrorCode.INVALID_INPUT_VALUE,e.getBindingResult());

           return new ResponseEntity<>(errorResponseDto,HttpStatus.BAD_REQUEST);
       }

    /*주로 @ModelAttribute 바인딩시 exception 이나 validate 후 exception*/
    @ExceptionHandler(BindException.class)
    protected ModelAndView handleBindException(BindException e, HttpServletRequest request,HttpServletResponse response) throws IOException {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.of(ErrorCode.INVALID_INPUT_VALUE,e.getBindingResult());

        if(acceptCheck(request)){
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(errorResponseDto.getStatus());
            response.getWriter().write(ob.writeValueAsString(errorResponseDto));
            return new ModelAndView();
        }
        return new ModelAndView("/error/"+errorResponseDto.getStatus());
    }

    /*Custom exception*/
    @ExceptionHandler(CustomServiceException.class)
    public ModelAndView handleCustomValidException(CustomServiceException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ErrorResponseDto errorResponseDto =ErrorResponseDto.of(e.getErrorCode());

        if(acceptCheck(request)){
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(errorResponseDto.getStatus());
            response.getWriter().write(ob.writeValueAsString(errorResponseDto));
            return new ModelAndView();

        }
        return new ModelAndView("/error/"+errorResponseDto.getStatus());
    }


    /*해당 요청 request header Accept를 체크를 하여서 분기 ? response Json : ModelAndView */
    private boolean acceptCheck(HttpServletRequest request){
        if(request.getHeader("Accept").equals(MediaType.APPLICATION_JSON_VALUE)){
            return true;
        }
        return false;

    }
}
