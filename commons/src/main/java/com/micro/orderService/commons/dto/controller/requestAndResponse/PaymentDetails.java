package com.micro.orderService.commons.dto.controller.requestAndResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDetails {
    @JsonProperty("payMethod")
    private Integer paymentMethod;
    @JsonProperty("amount")
    private Double amount;
}
