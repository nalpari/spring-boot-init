package net.devgrr.springbootinit.exception;

public class CommonCodeAlreadyExistsException extends RuntimeException {
    public CommonCodeAlreadyExistsException(String groupCode) {
        super("CommonCodeGroup already exists with groupCode: " + groupCode);
    }
    
    public CommonCodeAlreadyExistsException(String groupCode, String code) {
        super("CommonCode already exists with groupCode: " + groupCode + ", code: " + code);
    }
}