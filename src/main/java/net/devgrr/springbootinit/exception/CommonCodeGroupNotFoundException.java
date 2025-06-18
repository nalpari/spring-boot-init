package net.devgrr.springbootinit.exception;

public class CommonCodeGroupNotFoundException extends RuntimeException {
    public CommonCodeGroupNotFoundException(String groupCode) {
        super("CommonCodeGroup not found with groupCode: " + groupCode);
    }
}