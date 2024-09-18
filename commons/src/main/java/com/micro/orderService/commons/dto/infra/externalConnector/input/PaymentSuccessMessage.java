package com.micro.orderService.commons.dto.infra.externalConnector.input;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentSuccessMessage {
    private String sagaId;
}
