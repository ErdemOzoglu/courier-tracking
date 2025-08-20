package com.erdemo.couriertracking.couriertracking.dao;

import com.erdemo.couriertracking.couriertracking.entity.Courier;
import com.erdemo.couriertracking.delivery.entity.Delivery;
import com.erdemo.couriertracking.delivery.enums.EnumDeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourierDao extends JpaRepository<Courier, Long> {

    Optional<Courier> findByCourierPlate(String courierPlate);

}
