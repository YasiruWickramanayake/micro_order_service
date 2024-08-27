package com.micro.orderservice.infra.externalConnectorImpl.subscriber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.orderservice.application.service.OrderService;
import com.micro.orderservice.common.dto.infra.externalConnector.input.ProductReleaseSuccessMessage;
import com.micro.orderservice.common.dto.infra.externalConnector.input.ProductReservationFailMessage;
import com.micro.orderservice.common.dto.infra.externalConnector.input.ProductReservationSuccessMessage;
import com.micro.orderservice.infra.externalConnector.subscriber.InventorySubscriberConnector;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventorySubscriberConnectorImpl implements InventorySubscriberConnector {

    @Autowired
    private OrderService orderService;

    @KafkaListener(topics = "item-reservation-success")
    public void itemReservationSuccessMessage(ConsumerRecord<String, String> record) {
        System.out.println("Received message: " + record.value());
        ObjectMapper ob = new ObjectMapper();
        try {
            ProductReservationSuccessMessage productReservationSuccessMessage = ob.readValue(record.value(), ProductReservationSuccessMessage.class);
            orderService.orderUpdateBasedOnProductReservationSuccess(productReservationSuccessMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "item-reservation-fail")
    public void itemReservationFailMessage(ConsumerRecord<String, String> record) {
        System.out.println("Received message: " + record.value());
        ObjectMapper ob = new ObjectMapper();
        try {
            ProductReservationFailMessage productReservationFailMessage = ob.readValue(record.value(), ProductReservationFailMessage.class);
            orderService.orderUpdateBasedOnProductReservationFail(productReservationFailMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "item-release-success")
    public void itemReleaseSuccessMessage(ConsumerRecord<String, String> record) {
        System.out.println("Received message: " + record.value());
        ObjectMapper ob = new ObjectMapper();
        try {
            ProductReleaseSuccessMessage productReleaseSuccessMessage = ob.readValue(record.value(), ProductReleaseSuccessMessage.class);
            orderService.orderUpdateBasedOnProductReleaseSuccess(productReleaseSuccessMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
