package com.micro.orderservice.common.dto.infra.externalConnector.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PaymentInitiationMessage {
    private String sagaId;
    private Integer customerId;
    private Double payableAmount;


}
