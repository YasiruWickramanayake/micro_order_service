package com.micro.orderService.applicationCommon;


import com.micro.orderService.applicationDomain.OrderDomainApplication;
import com.micro.orderService.applicationInputPlugin.primary.OrderService;
import com.micro.orderService.applicationOutputPlugin.primary.OrderPublisherManager;
import com.micro.orderService.commons.dto.application.commonDto.OrderDto;
import com.micro.orderService.commons.dto.controller.requestAndResponse.OrderCreateRequest;
import com.micro.orderService.commons.dto.infra.externalConnector.input.*;
import com.micro.orderService.commons.utils.enums.OrderErrorCodes;
import com.micro.orderService.commons.utils.enums.OrderStatus;
import com.micro.orderService.commons.utils.exceptions.OrderCreateException;
import com.micro.orderService.commons.utils.exceptions.OrderUpdateException;
import com.micro.orderService.repository.dao.OrderRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
class OrderServiceImpl implements OrderService {
    Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDomainApplication orderDomainApplication;

    @Autowired
    private OrderPublisherManager orderPublisherManager;


    @Override
    @Transactional(rollbackOn = Exception.class)
    public String createOrder(OrderCreateRequest orderCreateRequest) {
        try {
            //Create Order entity
            OrderDto orderDto = orderDomainApplication.createOrder(orderCreateRequest);
            orderRepository.saveOrder(orderDto);

            //publish order creation request to event
            orderPublisherManager.publishOrderMessage(orderDto);
            return orderDto.getTrackingId();
        }catch (RuntimeException ex){
            throw new OrderCreateException(OrderStatus.ORDER_INITIATION_FAILED.getOrderStatusCode(),
                    OrderStatus.ORDER_INITIATION_FAILED.getOrderStatusMessage() + ": exception : " + ex.getMessage());
        }
    }

    @Override
    public void orderUpdateBasedOnProductReservationSuccess(ProductReservationSuccessMessage productReservationSuccessMessage) {
        try{
            OrderDto orderDto = orderRepository.orderFindBySagaId(productReservationSuccessMessage.getSagaId());
            OrderDto updatedOrderDto = orderDomainApplication.updateOrderForTheProductReservationSuccess(orderDto);
            orderRepository.saveOrder(updatedOrderDto);
            orderPublisherManager.publishOrderMessage(updatedOrderDto);
        }catch (RuntimeException ex){
            throw new OrderUpdateException(OrderErrorCodes.ORDER_UPDATE_FAILED.getErrorCode(), OrderErrorCodes.ORDER_UPDATE_FAILED.getMessage());
        }

    }

    @Override
    public void orderUpdateBasedOnProductReservationFail(ProductReservationFailMessage productReservationFailMessage) {
        try{
            OrderDto orderDto = orderRepository.orderFindBySagaId(productReservationFailMessage.getSagaId());
            OrderDto updatedOrderDto = orderDomainApplication.updateOrderForTheProductReservationFail(orderDto);
            orderRepository.saveOrder(updatedOrderDto);
        }catch (RuntimeException ex){
            throw new OrderUpdateException(OrderErrorCodes.ORDER_UPDATE_FAILED.getErrorCode(), OrderErrorCodes.ORDER_UPDATE_FAILED.getMessage());

        }
    }

    @Override
    public void orderUpdateBasedOnPaymentSuccess(PaymentSuccessMessage paymentSuccessMessage) {
       try{
           OrderDto orderDto = orderRepository.orderFindBySagaId(paymentSuccessMessage.getSagaId());
           OrderDto updatedOrderDto = orderDomainApplication.updateOrderForThePaymentSuccess(orderDto);
           orderRepository.saveOrder(updatedOrderDto);
       }catch (RuntimeException ex){
           throw new OrderUpdateException(OrderErrorCodes.ORDER_UPDATE_FAILED.getErrorCode(),
                   OrderErrorCodes.ORDER_UPDATE_FAILED.getMessage());

       }
    }

    @Override
    public void orderUpdateBasedOnPaymentFail(PaymentFailMessage paymentFailMessage) {
        try{
            OrderDto orderDto = orderRepository.orderFindBySagaId(paymentFailMessage.getSagaId());
            OrderDto updatedOrderDto = orderDomainApplication.updateOrderForThePaymentFail(orderDto);
            orderRepository.saveOrder(updatedOrderDto);
            orderPublisherManager.publishOrderMessage(updatedOrderDto);
        }catch (RuntimeException ex){
            throw new OrderUpdateException(OrderErrorCodes.ORDER_UPDATE_FAILED.getErrorCode(),
                    OrderErrorCodes.ORDER_UPDATE_FAILED.getMessage());
        }
    }

    @Override
    public void orderUpdateBasedOnProductReleaseSuccess(ProductReleaseSuccessMessage productReleaseSuccessMessage) {
        OrderDto orderDto = orderRepository.orderFindBySagaId(productReleaseSuccessMessage.getSagaId());
        OrderDto updatedOrderDto = orderDomainApplication.updateOrderForProductReleaseSuccess(orderDto);
        orderRepository.saveOrder(updatedOrderDto);
    }




}
