package com.micro.orderService.infra.externalConnector.publisher;


import com.micro.orderService.commons.dto.infra.externalConnector.output.ProductReleaseInitiationMessage;
import com.micro.orderService.commons.dto.infra.externalConnector.output.ProductionReservationInitiateMessage;

public interface ProductPublisherConnector {

    public void initiateProductReservation(ProductionReservationInitiateMessage productionReservationInitiateMessage);
    public void initiateProductRelease(ProductReleaseInitiationMessage productReleaseInitiationMessage);
}
