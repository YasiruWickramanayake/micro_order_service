package com.micro.orderService.infra.externalConnectorImpl.publisher.connectorImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.orderService.OrderServiceGrpc;
import com.micro.orderService.ProductReservationInitRequest;
import com.micro.orderService.ProductReservationInitResponse;
import com.micro.orderService.commons.dto.infra.externalConnector.output.ProductReleaseInitiationMessage;
import com.micro.orderService.commons.dto.infra.externalConnector.output.ProductionReservationInitiateMessage;
import com.micro.orderService.infra.externalConnector.publisher.ProductPublisherConnector;
import com.micro.productService.ProductReleaseInitRequest;
import com.micro.productService.ProductReleaseInitResponse;
import com.micro.productService.ProductServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
class ProductPublisherConnectorImpl implements ProductPublisherConnector {
    @GrpcClient("order-service")
    OrderServiceGrpc.OrderServiceBlockingStub orderServiceBlockingStub;

    @GrpcClient("product-release-initiate-service")
    ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void initiateProductReservation(ProductReservationInitRequest productionReservationInitiateMessage) {
        try {
            ProductReservationInitResponse productReservationInitResponse = orderServiceBlockingStub
                    .initiateProductReservation(productionReservationInitiateMessage);

        } catch (RuntimeException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void initiateProductRelease(ProductReleaseInitRequest productReleaseInitRequest) {
        try {
            ProductReleaseInitResponse productReleaseInitResponse = productServiceBlockingStub.releaseReservedProduct(productReleaseInitRequest);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
