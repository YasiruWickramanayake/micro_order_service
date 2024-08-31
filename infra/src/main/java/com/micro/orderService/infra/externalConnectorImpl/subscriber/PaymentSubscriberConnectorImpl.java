package com.micro.orderService.infra.externalConnectorImpl.subscriber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.orderService.applicationInputPlugin.primary.OrderService;
import com.micro.orderService.commons.dto.infra.externalConnector.input.PaymentFailMessage;
import com.micro.orderService.commons.dto.infra.externalConnector.input.PaymentSuccessMessage;
import com.micro.orderService.infra.externalConnector.subscriber.PaymentSubscriberConnector;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentSubscriberConnectorImpl implements PaymentSubscriberConnector {

    @Autowired
    private OrderService orderService;

    @KafkaListener(topics = "payment-success")
    public void paymentSuccessMessage(ConsumerRecord<String, String> record) {
        System.out.println("Received message: " + record.value());
        ObjectMapper ob = new ObjectMapper();
        try {
            PaymentSuccessMessage paymentSuccessMessage = ob.readValue(record.value(), PaymentSuccessMessage.class);
            orderService.orderUpdateBasedOnPaymentSuccess(paymentSuccessMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "payment-fail")
    public void paymentFailMessage(ConsumerRecord<String, String> record) {
        System.out.println("Received message: " + record.value());
        ObjectMapper ob = new ObjectMapper();
        try {
            PaymentFailMessage paymentFailMessage = ob.readValue(record.value(), PaymentFailMessage.class);
            orderService.orderUpdateBasedOnPaymentFail(paymentFailMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
