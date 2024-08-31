package com.micro.orderService.commons.dto.controller.requestAndResponse;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderCreateRequest {
    @JsonProperty(value = "custId")
    private Integer customerId;
    @JsonProperty(value = "fullGrossAmnt")
    private Double fullGrossAmount;
    @JsonProperty(value = "fullNetAmnt")
    private Double fullNetAmount;
    @JsonProperty(value = "fullDiscountAmount")
    private Double fullDiscountAmount;
    @JsonProperty(value = "cart")
    private List<CartDetails> cartDetails;
    @JsonProperty(value = "payment")
    private List<PaymentDetails> paymentDetails;
}
