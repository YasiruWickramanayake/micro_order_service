package com.micro.orderService.commons.dto.infra.externalConnector.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductReservationSuccessMessage {
    @JsonProperty(value = "sagaId")
    private String sagaId;
}


