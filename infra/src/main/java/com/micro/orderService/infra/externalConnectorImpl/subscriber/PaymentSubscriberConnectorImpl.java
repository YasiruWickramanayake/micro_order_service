package com.micro.orderService.infra.externalConnectorImpl.subscriber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.orderService.applicationInputPlugin.primary.OrderService;
import com.micro.orderService.commons.dto.infra.externalConnector.input.PaymentFailMessage;
import com.micro.orderService.commons.dto.infra.externalConnector.input.PaymentSuccessMessage;
import com.micro.orderService.infra.externalConnector.subscriber.PaymentSubscriberConnector;
import com.micro.paymentService.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;


@GrpcService
public class PaymentSubscriberConnectorImpl extends PaymentServiceGrpc.PaymentServiceImplBase
        implements PaymentSubscriberConnector {

    @Autowired
    private OrderService orderService;

    @Override
    public void paymentSuccess(PaymentSuccessRequest request, StreamObserver<PaymentSuccessResponse> responseObserver) {
        try {
            PaymentSuccessMessage paymentSuccessMessage = PaymentSuccessMessage.builder()
                    .sagaId(request.getSagaId())
                    .build();
            orderService.orderUpdateBasedOnPaymentSuccess(paymentSuccessMessage);
            responseObserver.onNext(PaymentSuccessResponse.newBuilder()
                    .setStatus(true)
                    .setMessage("message received successfully")
                    .build());
            responseObserver.onCompleted();
        } catch (Exception ex) {
            responseObserver.onNext(PaymentSuccessResponse.newBuilder()
                    .setStatus(false)
                    .setMessage("message received failed")
                    .build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void paymentFail(PaymentFailRequest request, StreamObserver<PaymentFailResponse> responseObserver) {
        try {
            PaymentFailMessage paymentFailMessage = PaymentFailMessage.builder()
                    .sagaId(request.getSagaId())
                    .build();

            orderService.orderUpdateBasedOnPaymentFail(paymentFailMessage);
            responseObserver.onNext(PaymentFailResponse.newBuilder()
                    .setStatus(true)
                    .setMessage("message received successfully")
                    .build());
            responseObserver.onCompleted();
        } catch (Exception ex) {
            responseObserver.onNext(PaymentFailResponse.newBuilder()
                    .setStatus(false)
                    .setMessage("message received failed")
                    .build());
            responseObserver.onCompleted();
        }
    }
}
