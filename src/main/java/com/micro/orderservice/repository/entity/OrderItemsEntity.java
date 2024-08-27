package com.micro.orderservice.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "gross_amount")
    private Double grossAmount;
    @Column(name = "net_amount")
    private Double netAmount;
    @Column(name = "discount_amount")
    private Double discountAmount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private OrderEntity orderEntity;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name="created_user")
    private String createdUser;
    @Column(name = "last_update_date")
    private Date lastUpdateDate;
    @Column(name="last_updated_user")
    private String lastUpdatedUser;
}
