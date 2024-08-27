package com.micro.orderservice.common.dto.infra.externalConnector.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentSuccessMessage {
    private String sagaId;
}
