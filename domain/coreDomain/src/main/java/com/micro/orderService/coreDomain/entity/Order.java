package com.micro.orderService.coreDomain.entity;


import com.micro.orderService.commons.dto.application.commonDto.OrderDto;
import com.micro.orderService.commons.dto.application.commonDto.OrderItemDto;
import com.micro.orderService.commons.dto.application.commonDto.OrderPaymentDto;
import com.micro.orderService.commons.dto.controller.requestAndResponse.CartDetails;
import com.micro.orderService.commons.dto.controller.requestAndResponse.OrderCreateRequest;
import com.micro.orderService.commons.dto.controller.requestAndResponse.PaymentDetails;
import com.micro.orderService.commons.utils.enums.OrderErrorCodes;
import com.micro.orderService.commons.utils.enums.OrderStatus;
import com.micro.orderService.commons.utils.enums.PaymentMethods;
import com.micro.orderService.commons.utils.exceptions.InvalidPaymentMethodException;
import com.micro.orderService.commons.utils.exceptions.OrderUpdateException;
import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class Order {
    private final Integer orderId;
    private final Integer customerId;
    private final Double fullGrossAmount;
    private final Double fullNetAmount;
    private final Double fullDiscountAmount;
    private final String trackingId;
    private final String sagaId;
    private final Integer orderStatus;
    private final String orderNarration;
    private final Integer orderNextStatus;
    private final String orderNextStatusNarration;
    private final List<OrderItem> orderItems;
    private final List<OrderPayment> orderPayments;

    public Order(OrderCreateRequest orderCreateRequest){
        this.orderId = null;
        this.customerId = orderCreateRequest.getCustomerId();
        this.fullGrossAmount = orderCreateRequest.getFullGrossAmount();
        this.fullNetAmount = orderCreateRequest.getFullNetAmount();
        this.fullDiscountAmount = orderCreateRequest.getFullDiscountAmount();
        this.orderItems = this.getOrderItems(orderCreateRequest.getCartDetails());
        this.orderPayments = this.getOrderPayment(orderCreateRequest.getPaymentDetails());
        this.orderStatus = OrderStatus.ORDER_INITIATED.getOrderStatusCode();
        this.orderNarration = OrderStatus.ORDER_INITIATED.getOrderStatusMessage();
        this.orderNextStatus = OrderStatus.ORDER_ITEM_RESERVATION_INITIATE.getOrderStatusCode();
        this.orderNextStatusNarration =OrderStatus.ORDER_ITEM_RESERVATION_INITIATE.getOrderStatusMessage();
        this.trackingId = this.generateTrackingId();
        this.sagaId = this.generateSagaId();
    }



    public Order updateOrderForProductReservationSuccess(){
        if(isEligibleToUpdateOrderStatusForProductReservationSuccess()){
            OrderStatus orderNextStatus = updateNextStatusAfterProductReservedSuccess();
            return new Order(this.orderId,
                    this.customerId,
                    this.fullGrossAmount,
                    this.fullNetAmount,
                    this.fullDiscountAmount,
                    this.trackingId,
                    this.sagaId,
                    OrderStatus.ORDER_ITEM_RESERVED.getOrderStatusCode(),
                    OrderStatus.ORDER_ITEM_RESERVED.getOrderStatusMessage(),
                    orderNextStatus.getOrderStatusCode(),
                    orderNextStatus.getOrderStatusMessage(),
                    this.orderItems,
                    this.orderPayments);
        }else{
            throw new OrderUpdateException(OrderErrorCodes.ORDER_UPDATE_FAILED.getErrorCode(),
                    OrderErrorCodes.ORDER_UPDATE_FAILED.getMessage());
        }
    }

    public Order updateOrderForProductReservationFail(){
        if(isEligibleToUpdateOrderStatusForProductReservationFail()){
            OrderStatus orderNextStatus = updateNextStatusAfterProductReservedFail();
            return new Order(this.orderId,
                    this.customerId,
                    this.fullGrossAmount,
                    this.fullNetAmount,
                    this.fullDiscountAmount,
                    this.trackingId,
                    this.sagaId,
                    OrderStatus.ORDER_ITEM_RESERVATION_FAILED.getOrderStatusCode(),
                    OrderStatus.ORDER_ITEM_RESERVATION_FAILED.getOrderStatusMessage(),
                    orderNextStatus.getOrderStatusCode(),
                    orderNextStatus.getOrderStatusMessage(),
                    this.orderItems,
                    this.updateOrderPaymentForProductReservationCancel());
        }else{
            throw new OrderUpdateException(OrderErrorCodes.ORDER_UPDATE_FAILED.getErrorCode(),
                    OrderErrorCodes.ORDER_UPDATE_FAILED.getMessage());
        }
    }

    public Order updateOrderForPaymentSuccess(){
        if(isEligibleToUpdateOrderStatusForPaymentSuccess()){
            OrderStatus orderNextStatus = updateNextStatusAfterPaymentSuccess();
            return new Order(this.orderId,
                    this.customerId,
                    this.fullGrossAmount,
                    this.fullNetAmount,
                    this.fullDiscountAmount,
                    this.trackingId,
                    this.sagaId,
                    OrderStatus.ORDER_PAYMENT_SUCCESS.getOrderStatusCode(),
                    OrderStatus.ORDER_PAYMENT_SUCCESS.getOrderStatusMessage(),
                    orderNextStatus.getOrderStatusCode(),
                    orderNextStatus.getOrderStatusMessage(),
                    this.orderItems,
                    this.updateOrderPaymentForPaymentSuccess());
        }else{
            throw new OrderUpdateException(OrderErrorCodes.ORDER_UPDATE_FAILED.getErrorCode(),
                    OrderErrorCodes.ORDER_UPDATE_FAILED.getMessage());
        }
    }

    public Order updateOrderForPaymentFail(){
        if(isEligibleToUpdateOrderStatusForPaymentFail()){
            OrderStatus orderNextStatus = updateNextStatusAfterPaymentFail();
            return new Order(this.orderId,
                    this.customerId,
                    this.fullGrossAmount,
                    this.fullNetAmount,
                    this.fullDiscountAmount,
                    this.trackingId,
                    this.sagaId,
                    OrderStatus.ORDER_PAYMENT_REJECT.getOrderStatusCode(),
                    OrderStatus.ORDER_PAYMENT_REJECT.getOrderStatusMessage(),
                    orderNextStatus.getOrderStatusCode(),
                    orderNextStatus.getOrderStatusMessage(),
                    this.orderItems,
                    this.updateOrderPaymentForPaymentFail());
        }else{
            throw new OrderUpdateException(OrderErrorCodes.ORDER_UPDATE_FAILED.getErrorCode(),
                    OrderErrorCodes.ORDER_UPDATE_FAILED.getMessage());
        }

    }

    public Order updateOrderForProductRelease(){
        if(isEligibleToUpdateOrderStatusForProductRelease()){
            OrderStatus orderNextStatus = updateNextStatusAfterProductReleaseSuccess();
            return new Order(this.orderId,
                    this.customerId,
                    this.fullGrossAmount,
                    this.fullNetAmount,
                    this.fullDiscountAmount,
                    this.trackingId,
                    this.sagaId,
                    this.orderStatus,
                    this.orderNarration,
                    orderNextStatus.getOrderStatusCode(),
                    orderNextStatus.getOrderStatusMessage(),
                    this.orderItems,
                    this.orderPayments);
        }else{
            throw new OrderUpdateException(OrderErrorCodes.ORDER_UPDATE_FAILED.getErrorCode(),
                    OrderErrorCodes.ORDER_UPDATE_FAILED.getMessage());
        }

    }


    public Order(OrderDto orderDto){
        this.orderId = orderDto.getOrderId();
        this.customerId = orderDto.getCustomerId();
        this.fullGrossAmount = orderDto.getFullGrossAmount();
        this.fullNetAmount = orderDto.getFullNetAmount();
        this.fullDiscountAmount = orderDto.getFullDiscountAmount();
        this.orderItems = this.getOrderItemsByOrderDto(orderDto.getOrderItems());
        this.orderPayments = this.getOrderPaymentByOrderDto(orderDto.getOrderPayments());
        this.orderStatus = orderDto.getStatus();
        this.orderNarration = orderDto.getNarration();
        this.orderNextStatus = orderDto.getNextStatus();
        this.orderNextStatusNarration = orderDto.getNextStatusNarration();
        this.trackingId = orderDto.getTrackingId();
        this.sagaId = orderDto.getSagaId();
    }

    private boolean isEligibleToUpdateOrderStatusForProductReservationSuccess(){
        // check the order is correct status to update
        if(!this.orderStatus.equals(OrderStatus.ORDER_INITIATED.getOrderStatusCode())){
            throw new OrderUpdateException(OrderErrorCodes.ORDER_IS_NOT_CORRECT_STATUS_TO_UPDATE.getErrorCode(),
                    OrderErrorCodes.ORDER_IS_NOT_CORRECT_STATUS_TO_UPDATE.getMessage());
        }
        return true;
    }

    private boolean isEligibleToUpdateOrderStatusForProductReservationFail(){
        // check the order is correct status to update
        if(!this.orderStatus.equals(OrderStatus.ORDER_INITIATED.getOrderStatusCode())){
            throw new OrderUpdateException(OrderErrorCodes.ORDER_IS_NOT_CORRECT_STATUS_TO_UPDATE.getErrorCode(),
                    OrderErrorCodes.ORDER_IS_NOT_CORRECT_STATUS_TO_UPDATE.getMessage());
        }
        return true;
    }

    private boolean isEligibleToUpdateOrderStatusForPaymentSuccess(){
        // check the order is correct status to update
        if(!this.orderStatus.equals(OrderStatus.ORDER_ITEM_RESERVED.getOrderStatusCode())){
            throw new OrderUpdateException(OrderErrorCodes.ORDER_IS_NOT_CORRECT_STATUS_TO_UPDATE.getErrorCode(),
                    OrderErrorCodes.ORDER_IS_NOT_CORRECT_STATUS_TO_UPDATE.getMessage());
        }
        return true;
    }

    private boolean isEligibleToUpdateOrderStatusForPaymentFail(){
        if(!this.orderStatus.equals(OrderStatus.ORDER_ITEM_RESERVED.getOrderStatusCode())){
            throw new OrderUpdateException(OrderErrorCodes.ORDER_IS_NOT_CORRECT_STATUS_TO_UPDATE.getErrorCode(),
                    OrderErrorCodes.ORDER_IS_NOT_CORRECT_STATUS_TO_UPDATE.getMessage());
        }
        return true;
    }

    private boolean isEligibleToUpdateOrderStatusForProductRelease(){
        if(!this.orderStatus.equals(OrderStatus.ORDER_PAYMENT_REJECT.getOrderStatusCode())){
            throw new OrderUpdateException(OrderErrorCodes.ORDER_IS_NOT_CORRECT_STATUS_TO_UPDATE.getErrorCode(),
                    OrderErrorCodes.ORDER_IS_NOT_CORRECT_STATUS_TO_UPDATE.getMessage());
        }
        return true;
    }

    private  OrderStatus updateNextStatusAfterProductReservedSuccess(){
        boolean hasCardPayment = this.orderPayments.stream()
                .anyMatch(payment -> payment.getPaymentMethod()
                        .equals(PaymentMethods.CARD_PAYMENT.getPaymentMethodId()));

        boolean hasCashPayment = this.orderPayments.stream()
                .anyMatch(payment -> payment.getPaymentMethod()
                        .equals(PaymentMethods.CASH_PAYMENT.getPaymentMethodId()));

        boolean hasVoucher = this.orderPayments.stream()
                .anyMatch(payment -> payment.getPaymentMethod()
                        .equals(PaymentMethods.GIFT_VOUCHER.getPaymentMethodId()));

        if(hasCardPayment && hasCashPayment){
            throw new InvalidPaymentMethodException(OrderErrorCodes.INVALID_PAYMENT_METHOD.getErrorCode(),
                    OrderErrorCodes.INVALID_PAYMENT_METHOD.getMessage());
        }

        if (hasCardPayment){
            return  OrderStatus.ORDER_PAYMENT_INITIATED;
        }
        else if (hasCashPayment) {
            return OrderStatus.ORDER_PAYMENT_AFTER_DELIVERY;
        }else if(hasVoucher){
            Optional<OrderPayment> giftVoucherPayment = this.orderPayments.stream().filter(payment ->
                            payment.getPaymentMethod()
                                    .equals(PaymentMethods.GIFT_VOUCHER.getPaymentMethodId()))
                    .findFirst();
            if(giftVoucherPayment.isEmpty()){
                throw new InvalidPaymentMethodException(OrderErrorCodes.PAYMENT_METHOD_IS_NOT_INCLUDED.getErrorCode(),
                        OrderErrorCodes.PAYMENT_METHOD_IS_NOT_INCLUDED.getMessage());
            }
            if(!this.fullNetAmount.equals(giftVoucherPayment.get().getAmount())){
                throw new InvalidPaymentMethodException(OrderErrorCodes.FULL_PAYMENT_IS_NOT_COVER_BY_PAYMENT_METHOD.getErrorCode(),
                        OrderErrorCodes.FULL_PAYMENT_IS_NOT_COVER_BY_PAYMENT_METHOD.getMessage());
            }
            return OrderStatus.ORDER_READY_FOR_DELIVERY;
        }else{
            throw new InvalidPaymentMethodException(OrderErrorCodes.INVALID_PAYMENT_METHOD.getErrorCode(),
                    OrderErrorCodes.INVALID_PAYMENT_METHOD.getMessage());
        }

    }

    private OrderStatus updateNextStatusAfterProductReservedFail(){
        boolean hasVoucher = this.orderPayments.stream()
                .anyMatch(payment -> payment.getPaymentMethod()
                        .equals(PaymentMethods.GIFT_VOUCHER.getPaymentMethodId()));

        return hasVoucher ? OrderStatus.ORDER_RELEASE_VOUCHER: OrderStatus.ORDER_NO_LONGER_PROCESS;
    }

    private OrderStatus updateNextStatusAfterPaymentSuccess(){
        return OrderStatus.ORDER_READY_FOR_DELIVERY;
    }

    private List<OrderPayment> updateOrderPaymentForProductReservationCancel(){
        return this.orderPayments.stream()
                .map(OrderPayment::invalidateOrderPayment)
                .collect(Collectors.toList());
    }

    private List<OrderPayment> updateOrderPaymentForPaymentFail(){
        return this.orderPayments.stream()
                .map(OrderPayment::rejectOrderPayment)
                .collect(Collectors.toList());
    }

    private OrderStatus updateNextStatusAfterPaymentFail(){
        return OrderStatus.ORDER_ITEM_RELEASE_INITIATE;
    }

    private OrderStatus updateNextStatusAfterProductReleaseSuccess(){
        return OrderStatus.ORDER_NO_LONGER_PROCESS_AFTER_PRODUCT_RELEASE;
    }

    private List<OrderPayment> updateOrderPaymentForPaymentSuccess(){
        return this.orderPayments.stream()
                .map(OrderPayment::updatePaymentStatusForPaymentSuccess)
                .collect(Collectors.toList());
    }

    private Order(Integer orderId,
                 Integer customerId,
                 Double fullGrossAmount,
                 Double fullNetAmount,
                 Double fullDiscountAmount,
                 String trackingId,
                 String sagaId,
                 Integer orderStatus,
                 String orderNarration,
                 Integer orderNextStatus,
                 String orderNextStatusNarration,
                 List<OrderItem> orderItems,
                 List<OrderPayment> orderPayments) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.fullGrossAmount = fullGrossAmount;
        this.fullNetAmount = fullNetAmount;
        this.fullDiscountAmount = fullDiscountAmount;
        this.trackingId = trackingId;
        this.sagaId = sagaId;
        this.orderStatus = orderStatus;
        this.orderNarration = orderNarration;
        this.orderNextStatus = orderNextStatus;
        this.orderNextStatusNarration = orderNextStatusNarration;
        this.orderItems = orderItems;
        this.orderPayments = orderPayments;
    }

    private List<OrderItem> getOrderItems(List<CartDetails> cartDetails){
            return cartDetails.stream()
                    .map(OrderItem::new)
                    .collect(Collectors.toList());
    }

    private List<OrderPayment> getOrderPayment(List<PaymentDetails> paymentDetails){
        return paymentDetails.stream()
                .map(OrderPayment::new)
                .collect(Collectors.toList());
    }

    private List<OrderItem> getOrderItemsByOrderDto(List<OrderItemDto> orderItemDtos){
        return orderItemDtos.stream()
                .map(orderItemDto ->
                        new OrderItem(orderItemDto.getProductId(),
                                orderItemDto.getProductName(),
                                orderItemDto.getQuantity(),
                                orderItemDto.getGrossPrice(),
                                orderItemDto.getNetPrice(),
                                orderItemDto.getDiscount()
                                ))
                .collect(Collectors.toList());
    }

    private List<OrderPayment> getOrderPaymentByOrderDto(List<OrderPaymentDto> paymentDtos){
        return paymentDtos.stream()
                .map(orderPaymentDto -> new OrderPayment(orderPaymentDto.getPaymentMethod(),
                        orderPaymentDto.getAmount(),
                        orderPaymentDto.getPaymentStatus()))
                .collect(Collectors.toList());
    }

    private String generateTrackingId(){
        return UUID.randomUUID().toString();
    }
    private String generateSagaId(){
        return UUID.randomUUID().toString();
    }


}
