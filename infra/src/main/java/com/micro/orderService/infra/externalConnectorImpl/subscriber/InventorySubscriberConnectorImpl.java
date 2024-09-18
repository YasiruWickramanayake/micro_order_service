package com.micro.orderService.infra.externalConnectorImpl.subscriber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.orderService.applicationInputPlugin.primary.OrderService;
import com.micro.orderService.commons.dto.infra.externalConnector.input.ProductReleaseSuccessMessage;
import com.micro.orderService.commons.dto.infra.externalConnector.input.ProductReservationFailMessage;
import com.micro.orderService.commons.dto.infra.externalConnector.input.ProductReservationSuccessMessage;
import com.micro.productService.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;


@GrpcService
public class InventorySubscriberConnectorImpl extends ProductServiceGrpc.ProductServiceImplBase {

    @Autowired
    private OrderService orderService;

    @Override
    public void productReserveSuccess(ProductReserveSuccessRequest request, StreamObserver<ProductReserveSuccessResponse> responseObserver) {
        try{
        ProductReservationSuccessMessage productReservationSuccessMessage =ProductReservationSuccessMessage.builder().sagaId(request.getSagaId()).build();
        orderService.orderUpdateBasedOnProductReservationSuccess(productReservationSuccessMessage);
        responseObserver.onNext(ProductReserveSuccessResponse.newBuilder()
                .setStatus(true)
                .setMessage("reserved successfully")
                .build());
        responseObserver.onCompleted();
        }catch (RuntimeException ex){
            responseObserver.onNext(ProductReserveSuccessResponse.newBuilder()
                    .setStatus(false)
                    .setMessage("reservation failed")
                    .build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void productReserveFail(ProductReserveFailRequest request, StreamObserver<ProductReserveFailResponse> responseObserver) {
        System.out.println("Received reservation fail message: " + request);
        try {
            ProductReservationFailMessage productReservationFailMessage = ProductReservationFailMessage.builder()
                    .sagaId(request.getSagaId())
                    .build();
            orderService.orderUpdateBasedOnProductReservationFail(productReservationFailMessage);
            responseObserver.onNext(ProductReserveFailResponse.newBuilder()
                    .setStatus(true)
                    .setMessage("message received successfully")
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onNext(ProductReserveFailResponse.newBuilder()
                    .setStatus(false)
                    .setMessage("message received failed")
                    .build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void productReleaseSuccess(ProductReleaseSuccessRequest request, StreamObserver<ProductReleaseSuccessResponse> responseObserver) {
        try{
            ProductReleaseSuccessMessage productReleaseSuccessMessage = ProductReleaseSuccessMessage.builder()
                    .sagaId(request.getSagaId())
                    .build();
            orderService.orderUpdateBasedOnProductReleaseSuccess(productReleaseSuccessMessage);
            responseObserver.onNext(ProductReleaseSuccessResponse.newBuilder()
                    .setStatus(true)
                    .setMessage("message received successfully")
                    .build());
            responseObserver.onCompleted();
        }catch(RuntimeException ex){
            responseObserver.onNext(ProductReleaseSuccessResponse.newBuilder()
                    .setStatus(false)
                    .setMessage("message received failed")
                    .build());
            responseObserver.onCompleted();
        }
    }

    //    @KafkaListener(topics = "item-release-success")
//    public void itemReleaseSuccessMessage(ConsumerRecord<String, String> record) {
//        System.out.println("Received message: " + record.value());
//        ObjectMapper ob = new ObjectMapper();
//        try {
//            ProductReleaseSuccessMessage productReleaseSuccessMessage = ob.readValue(record.value(), ProductReleaseSuccessMessage.class);
//            orderService.orderUpdateBasedOnProductReleaseSuccess(productReleaseSuccessMessage);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
