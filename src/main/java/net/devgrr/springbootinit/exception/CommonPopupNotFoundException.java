package net.devgrr.springbootinit.exception;

public class CommonPopupNotFoundException extends RuntimeException {
    public CommonPopupNotFoundException(Long id) {
        super("CommonPopup not found with id: " + id);
    }
}