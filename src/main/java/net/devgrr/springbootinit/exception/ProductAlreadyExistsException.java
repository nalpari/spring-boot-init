package net.devgrr.springbootinit.exception;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String sku) {
        super("Product already exists with sku: " + sku);
    }
}