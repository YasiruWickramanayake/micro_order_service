package com.micro.orderService.commons.dto.infra.externalConnector.output;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ProductionReservationInitiateMessage {
    private String sagaId;
    private List<ReservedOrderItem> orderItems;

}
