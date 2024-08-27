package com.micro.orderservice.infra.externalConnector.publisher.connectorI;

import com.micro.orderservice.common.dto.infra.externalConnector.output.ProductReleaseInitiationMessage;
import com.micro.orderservice.common.dto.infra.externalConnector.output.ProductionReservationInitiateMessage;

public interface ProductPublisherConnector {

    public void initiateProductReservation(ProductionReservationInitiateMessage productionReservationInitiateMessage);
    public void initiateProductRelease(ProductReleaseInitiationMessage productReleaseInitiationMessage);
}
