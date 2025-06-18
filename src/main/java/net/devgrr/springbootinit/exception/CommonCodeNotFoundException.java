package net.devgrr.springbootinit.exception;

public class CommonCodeNotFoundException extends RuntimeException {
    public CommonCodeNotFoundException(Long id) {
        super("CommonCode not found with id: " + id);
    }
    
    public CommonCodeNotFoundException(String groupCode, String code) {
        super("CommonCode not found with groupCode: " + groupCode + ", code: " + code);
    }
}