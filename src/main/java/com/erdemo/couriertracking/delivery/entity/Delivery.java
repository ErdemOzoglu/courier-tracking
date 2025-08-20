package com.erdemo.couriertracking.delivery.entity;

import com.erdemo.couriertracking.delivery.enums.EnumDeliveryStatus;
import com.erdemo.couriertracking.generic.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "DELIVERY")
@Getter
@Setter
public class Delivery extends BaseEntity {

    @GeneratedValue(generator = "Delivery")
    @SequenceGenerator(name = "Delivery", sequenceName = "DELIVERY_ID_SEQ")
    @Id
    private Long id;

    @Column(name = "ID_COURIER")
    private Long courierId;

    @Column(name = "STORE_NAME", length = 100)
    private String storeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "DELIVERY_STATUS", length = 30)
    private EnumDeliveryStatus deliveryStatus;

    @Column(name = "DISTANCE", precision = 19, scale = 2)
    private Double distance;



}
