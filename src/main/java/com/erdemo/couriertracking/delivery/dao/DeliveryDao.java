package com.erdemo.couriertracking.delivery.dao;

import com.erdemo.couriertracking.delivery.entity.Delivery;
import com.erdemo.couriertracking.delivery.enums.EnumDeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryDao extends JpaRepository<Delivery, Long> {

    Delivery findByCourierIdAndDeliveryStatus(Long courierId, EnumDeliveryStatus deliveryStatus);

    List<Delivery> findAllByCourierIdAndDeliveryStatus(Long courierId, EnumDeliveryStatus deliveryStatus);

    List<Delivery> findAllByCourierId(Long courierId);
}
