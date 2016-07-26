package com.haikong.exception;

/**
 * @author Niu Li
 * @date 2016/7/26
 */
public class DeviceException extends Exception {
    private int code;
    private String message;

    /**
     * 构造异常类
     * @param code
     * @param message
     */
    public DeviceException( int code,String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据枚举类构造异常结果
     * @param errorCode
     */
    public DeviceException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
