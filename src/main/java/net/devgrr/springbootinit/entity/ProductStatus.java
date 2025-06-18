package net.devgrr.springbootinit.entity;

public enum ProductStatus {
    ACTIVE("활성"),
    INACTIVE("비활성"),
    OUT_OF_STOCK("품절"),
    DISCONTINUED("단종");

    private final String description;

    ProductStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}