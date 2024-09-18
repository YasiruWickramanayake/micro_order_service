package com.micro.orderService.infra.externalConnectorImpl.publisher;

import com.micro.orderService.ProductReservationInitRequest;
import com.micro.orderService.ReservedOrderItem;
import com.micro.orderService.applicationOutputPlugin.primary.OrderPublisherManager;
import com.micro.orderService.commons.dto.application.commonDto.OrderDto;
import com.micro.orderService.commons.dto.application.commonDto.OrderItemDto;
import com.micro.orderService.commons.dto.application.commonDto.OrderPaymentDto;
import com.micro.orderService.commons.dto.infra.externalConnector.output.PaymentInitiationMessage;
import com.micro.orderService.commons.dto.infra.externalConnector.output.ProductReleaseInitiationMessage;
import com.micro.orderService.commons.dto.infra.externalConnector.output.ProductionReservationInitiateMessage;
import com.micro.orderService.commons.utils.enums.OrderErrorCodes;
import com.micro.orderService.commons.utils.enums.OrderStatus;
import com.micro.orderService.commons.utils.enums.PaymentMethods;
import com.micro.orderService.commons.utils.exceptions.InvalidPaymentMethodException;
import com.micro.orderService.infra.externalConnector.publisher.PaymentPublisherConnector;
import com.micro.orderService.infra.externalConnector.publisher.ProductPublisherConnector;
import com.micro.paymentService.PaymentInitiateRequest;
import com.micro.productService.ProductReleaseInitRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
class OrderPublisherManagerImpl implements OrderPublisherManager {

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
//            ProductionReservationInitiateMessage productionReservationInitiateMessage = ProductionReservationInitiateMessage.builder()
//                    .sagaId(orderDto.getSagaId())
//                    .orderItems(getReservedOrderItems(orderDto.getOrderItems()))
//                    .build();

            ProductReservationInitRequest productReservationInitRequest = ProductReservationInitRequest.newBuilder()
                    .setSagaId(orderDto.getSagaId())
                    .addAllOrderItems(getReservedOrderItems(orderDto.getOrderItems()))
                    .build();
            productPublisherConnector.initiateProductReservation(productReservationInitRequest);
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

            PaymentInitiateRequest paymentInitiateRequest = PaymentInitiateRequest.newBuilder()
                    .setCustomerId(orderDto.getCustomerId())
                    .setPayableAmount(orderPayment.getAmount())
                    .setSagaId(orderDto.getSagaId())
                    .build();

            paymentPublisherConnector.initiatePayment(paymentInitiateRequest);

        }catch (Exception ex){

        }
    }

    private void initiateOrderItemRelease(OrderDto orderDto){
        try{
            ProductReleaseInitRequest productReleaseInitRequest = ProductReleaseInitRequest.newBuilder()
                    .setSagaId(orderDto.getSagaId())
                    .build();
            productPublisherConnector.initiateProductRelease(productReleaseInitRequest);
        }catch (Exception ex){

        }
    }




    private List<ReservedOrderItem> getReservedOrderItems(List<OrderItemDto> orderItems){
        return orderItems.stream().map(orderItemDto -> ReservedOrderItem.newBuilder()
                .setProductId(orderItemDto.getProductId())
                .setQuantity(orderItemDto.getQuantity())
                .setAmount(orderItemDto.getNetPrice())
                .build()
        ).collect(Collectors.toList());

    }
}
