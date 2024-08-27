package com.micro.orderservice.common.dto.controller.requestAndResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDetails {
    @JsonProperty(value = "prodId")
    private Integer productId;
    @JsonProperty(value = "productName")
    private String productName;
    @JsonProperty(value = "quantity")
    private Integer quantity;
    @JsonProperty(value = "grossAmnt")
    private Double grossPrice;
    @JsonProperty(value = "netAmnt")
    private Double netPrice;
    @JsonProperty(value = "discnt")
    private Double discount;
}
