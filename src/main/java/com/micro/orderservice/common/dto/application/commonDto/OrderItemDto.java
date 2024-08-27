package com.micro.orderservice.common.dto.application.commonDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OrderItemDto {
    private final Integer productId;
    private final String productName;
    private final Integer quantity;
    private final Double grossPrice;
    private final Double netPrice;
    private final Double discount;
}
