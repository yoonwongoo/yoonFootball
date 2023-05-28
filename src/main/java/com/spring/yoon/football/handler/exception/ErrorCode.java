package com.spring.yoon.football.handler.exception;
public enum ErrorCode {

    // 공통적으로 쓰이는 error
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    ENTITY_NOT_FOUND(404, "C003", " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", "잘못된 타입의 값입니다"),
    ACCESS_DENIED(403, "C006", "Access is Denied"),


    //이메일 인증번호
    EMAIL_AlREADY_SEND(400,"E001","이미 전송한 이메일입니다"),
    EMAIL_INVAlID_INPUT(400,"E002","잘못된 인증번호입니다"),
    EMAIL_NOT_FOUND(404,"E003","존재하지않는 이메일,인증번호입니다"),

    //포인트 관련
    POINT_LACK(403, "P001","포인트가 부족합니다")
    ;
    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }


}
