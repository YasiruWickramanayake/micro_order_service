package com.micro.orderService.commons.dto.infra.externalConnector.input;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductReservationFailMessage {
    private String sagaId;
}
