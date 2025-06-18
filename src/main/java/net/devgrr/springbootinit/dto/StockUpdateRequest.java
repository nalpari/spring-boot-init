package net.devgrr.springbootinit.dto;

import lombok.Data;

@Data
public class StockUpdateRequest {
    private Integer quantity;
    private String reason;
}