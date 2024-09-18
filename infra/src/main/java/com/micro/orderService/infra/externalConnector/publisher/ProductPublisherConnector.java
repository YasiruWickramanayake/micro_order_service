package com.micro.orderService.infra.externalConnector.publisher;


import com.micro.orderService.ProductReservationInitRequest;
import com.micro.orderService.commons.dto.infra.externalConnector.output.ProductReleaseInitiationMessage;
import com.micro.productService.ProductReleaseInitRequest;

public interface ProductPublisherConnector {

    public void initiateProductReservation(ProductReservationInitRequest productReservationInitRequest);
    public void initiateProductRelease(ProductReleaseInitRequest productReleaseInitRequest);
}
