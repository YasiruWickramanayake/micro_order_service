package com.micro.orderservice.common.dto.infra.externalConnector.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductReservationSuccessMessage {
    @JsonProperty(value = "sagaId")
    private String sagaId;
}


