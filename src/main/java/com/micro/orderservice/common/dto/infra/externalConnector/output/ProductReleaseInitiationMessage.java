package com.micro.orderservice.common.dto.infra.externalConnector.output;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ProductReleaseInitiationMessage {

    private String sagaId;
    private List<ReservedOrderItem> reservedOrderItems;


}
