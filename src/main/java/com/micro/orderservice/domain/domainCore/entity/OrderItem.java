package com.micro.orderservice.domain.domainCore.entity;

import com.micro.orderservice.common.dto.controller.requestAndResponse.CartDetails;
import lombok.Getter;

@Getter
public class OrderItem {
    private final Integer productId;
    private final String productName;
    private final Integer quantity;
    private final Double grossPrice;
    private final Double netPrice;
    private final Double discount;

    public OrderItem(CartDetails cartDetails) {
        this.productId = cartDetails.getProductId();
        this.productName = cartDetails.getProductName();
        this.quantity = cartDetails.getQuantity();
        this.grossPrice = cartDetails.getGrossPrice();
        this.netPrice = cartDetails.getNetPrice();
        this.discount = cartDetails.getDiscount();

    }

    public OrderItem(Integer productId, String productName, Integer quantity, Double grossPrice, Double netPrice, Double discount) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.grossPrice = grossPrice;
        this.netPrice = netPrice;
        this.discount = discount;
    }
}
