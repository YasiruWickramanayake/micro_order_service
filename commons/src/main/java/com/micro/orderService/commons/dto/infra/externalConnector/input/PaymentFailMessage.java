package com.micro.orderService.commons.dto.infra.externalConnector.input;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentFailMessage {
    private  String sagaId;
}
