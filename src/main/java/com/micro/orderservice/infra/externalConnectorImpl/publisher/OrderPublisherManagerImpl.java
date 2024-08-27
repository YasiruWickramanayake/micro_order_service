package com.micro.orderservice.infra.externalConnectorImpl.publisher;

import com.micro.orderservice.common.dto.application.commonDto.OrderDto;
import com.micro.orderservice.common.dto.application.commonDto.OrderItemDto;
import com.micro.orderservice.common.dto.application.commonDto.OrderPaymentDto;
import com.micro.orderservice.common.dto.infra.externalConnector.output.PaymentInitiationMessage;
import com.micro.orderservice.common.dto.infra.externalConnector.output.ProductReleaseInitiationMessage;
import com.micro.orderservice.common.dto.infra.externalConnector.output.ProductionReservationInitiateMessage;
import com.micro.orderservice.common.dto.infra.externalConnector.output.ReservedOrderItem;
import com.micro.orderservice.common.utils.enums.OrderErrorCodes;
import com.micro.orderservice.common.utils.enums.OrderStatus;
import com.micro.orderservice.common.utils.enums.PaymentMethods;
import com.micro.orderservice.common.utils.exceptions.InvalidPaymentMethodException;
import com.micro.orderservice.infra.externalConnector.publisher.OrderPublisherManager;
import com.micro.orderservice.infra.externalConnector.publisher.connectorI.PaymentPublisherConnector;
import com.micro.orderservice.infra.externalConnector.publisher.connectorI.ProductPublisherConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderPublisherManagerImpl implements OrderPublisherManager {

    @Autowired
    private ProductPublisherConnector productPublisherConnector;

    @Autowired
    private PaymentPublisherConnector paymentPublisherConnector;
    @Override
    public void publishOrderMessage(OrderDto orderDto) {
        if(orderDto.getNextStatus().equals(OrderStatus.ORDER_ITEM_RESERVATION_INITIATE.getOrderStatusCode())){
            initiateProductReservationMessage(orderDto);
        } else if (orderDto.getNextStatus().equals(OrderStatus.ORDER_PAYMENT_INITIATED.getOrderStatusCode())) {
            initiateOrderPayment(orderDto);
        }else if(orderDto.getNextStatus().equals(OrderStatus.ORDER_ITEM_RELEASE_INITIATE.getOrderStatusCode())){
            initiateOrderItemRelease(orderDto);
        }
    }

    private void initiateProductReservationMessage(OrderDto orderDto){
        try{
            ProductionReservationInitiateMessage productionReservationInitiateMessage = ProductionReservationInitiateMessage.builder()
                    .sagaId(orderDto.getSagaId())
                    .orderItems(getReservedOrderItems(orderDto.getOrderItems()))
                    .build();
            productPublisherConnector.initiateProductReservation(productionReservationInitiateMessage);
        }catch (Exception ex){

        }
    }

    private void initiateOrderPayment(OrderDto orderDto){
        try{
            if(!orderDto.getNextStatus().equals(OrderStatus.ORDER_PAYMENT_INITIATED.getOrderStatusCode())){
                return ;
            }
            OrderPaymentDto orderPayment = orderDto.getOrderPayments().stream().filter(orderPaymentDto ->
                            orderPaymentDto.getPaymentMethod().equals(PaymentMethods.CARD_PAYMENT.getPaymentMethodId()))
                    .findFirst()
                    .orElseThrow(() -> new InvalidPaymentMethodException(OrderErrorCodes.INVALID_PAYMENT_METHOD.getErrorCode(),
                            OrderErrorCodes.INVALID_PAYMENT_METHOD.getMessage()));

            PaymentInitiationMessage paymentInitiationMessage = PaymentInitiationMessage.builder()
                    .customerId(orderDto.getCustomerId())
                    .payableAmount(orderPayment.getAmount())
                    .sagaId(orderDto.getSagaId())
                    .build();

            paymentPublisherConnector.initiatePayment(paymentInitiationMessage);

        }catch (Exception ex){

        }
    }

    private void initiateOrderItemRelease(OrderDto orderDto){
        try{
            ProductReleaseInitiationMessage productReleaseInitiationMessage = ProductReleaseInitiationMessage.builder()
                    .sagaId(orderDto.getSagaId())
                    .reservedOrderItems(getReservedOrderItems(orderDto.getOrderItems()))
                    .build();
            productPublisherConnector.initiateProductRelease(productReleaseInitiationMessage);
        }catch (Exception ex){

        }
    }


    private List<ReservedOrderItem> getReservedOrderItems(List<OrderItemDto> orderItems){
        return orderItems.stream().map(orderItemDto -> ReservedOrderItem.builder()
                .productId(orderItemDto.getProductId())
                .quantity(orderItemDto.getQuantity())
                .amount(orderItemDto.getNetPrice())
                .build()
        ).collect(Collectors.toList());

    }
}
