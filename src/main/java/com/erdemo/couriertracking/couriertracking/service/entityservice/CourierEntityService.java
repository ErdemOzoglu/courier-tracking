package com.erdemo.couriertracking.couriertracking.service.entityservice;

import com.erdemo.couriertracking.couriertracking.dao.CourierDao;
import com.erdemo.couriertracking.couriertracking.entity.Courier;
import com.erdemo.couriertracking.delivery.entity.Delivery;
import com.erdemo.couriertracking.delivery.enums.EnumDeliveryStatus;
import com.erdemo.couriertracking.generic.service.BaseEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CourierEntityService extends BaseEntityService<Courier, CourierDao> {

    public CourierEntityService(CourierDao dao) {
        super(dao);
    }

    public Optional<Courier> findByCourierPlate(String courierPlate){
        return getDao().findByCourierPlate(courierPlate);
    }

}
