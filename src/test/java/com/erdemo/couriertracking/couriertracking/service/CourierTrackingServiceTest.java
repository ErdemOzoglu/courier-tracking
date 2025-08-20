package com.erdemo.couriertracking.couriertracking.service;

import com.erdemo.couriertracking.couriertracking.dto.CourierLocationInfoDto;
import com.erdemo.couriertracking.couriertracking.entity.Courier;
import com.erdemo.couriertracking.couriertracking.service.entityservice.CourierEntityService;
import com.erdemo.couriertracking.delivery.entity.Delivery;
import com.erdemo.couriertracking.delivery.entity.DeliveryMovement;
import com.erdemo.couriertracking.delivery.service.entityservice.DeliveryEntityService;
import com.erdemo.couriertracking.delivery.service.entityservice.DeliveryMovementEntityService;
import com.erdemo.couriertracking.store.dto.StoreInfoDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourierTrackingServiceTest {

    @Mock
    private CourierEntityService courierEntityService;

    @Mock
    private DeliveryEntityService deliveryEntityService;

    @Mock
    private DeliveryMovementEntityService deliveryMovementEntityService;

    @Mock
    private LocationControlService locationControlService;

    @InjectMocks
    private CourierTrackingService courierTrackingService;

    @Test
    void shouldSaveCourier() {

        Courier courier = Mockito.mock(Courier.class);
        when(courier.getId()).thenReturn(1L);
        when(courier.getName()).thenReturn("Erdem");
        when(courierEntityService.save(any())).thenReturn(courier);

        Courier result = courierTrackingService.saveCourier(any());

        assertEquals(1L, result.getId());
        assertEquals("Erdem", result.getName());
    }

    @Test
    void shouldSaveCourierPosition_whenThereIsNoActiveDelivery() {

        CourierLocationInfoDto courierLocationInfoDto = mock(CourierLocationInfoDto.class);
        Courier courier = Mockito.mock(Courier.class);
        when(courier.getId()).thenReturn(1L);
        Optional<Courier> optionalCourier = Optional.of(courier);

        when(courierEntityService.findByCourierPlate(any())).thenReturn(optionalCourier);

        Delivery delivery = mock(Delivery.class);
        when(delivery.getId()).thenReturn(1L);

        DeliveryMovement deliveryMovement = mock(DeliveryMovement.class);

        when(deliveryEntityService.findActiveDeliveryByCourierId(any())).thenReturn(null);
        when(deliveryEntityService.save(any())).thenReturn(delivery);
        when(deliveryMovementEntityService.save(any())).thenReturn(deliveryMovement);

        courierTrackingService.saveCourierPosition(courierLocationInfoDto);
    }

    @Test
    void shouldSaveCourierPosition_whenReenteredIsTrue() {

        LocalDateTime lastMovementDate = LocalDateTime.of(2025, 4, 5, 12, 0, 0);

        CourierLocationInfoDto courierLocationInfoDto = mock(CourierLocationInfoDto.class);
        when(courierLocationInfoDto.getCourierPlate()).thenReturn("61ASD114");
        when(courierLocationInfoDto.getTime()).thenReturn(lastMovementDate);

        Courier courier = Mockito.mock(Courier.class);
        when(courier.getId()).thenReturn(1L);
        Optional<Courier> optionalCourier = Optional.of(courier);

        when(courierEntityService.findByCourierPlate(any())).thenReturn(optionalCourier);

        Delivery delivery = mock(Delivery.class);
        when(delivery.getId()).thenReturn(1L);

        DeliveryMovement deliveryMovement = mock(DeliveryMovement.class);
        when(deliveryMovement.getMovementDate()).thenReturn(lastMovementDate);

        when(deliveryEntityService.findActiveDeliveryByCourierId(any())).thenReturn(delivery);
        when(deliveryMovementEntityService.findLastDeliveryMovementByDeliveryId(any())).thenReturn(deliveryMovement);
        when(locationControlService.isReentered(any(), any())).thenReturn(true);

        courierTrackingService.saveCourierPosition(courierLocationInfoDto);
    }

    @Test
    void shouldSaveCourierPosition_whenReenteredFalseAndNotInRangeOfStores() {

        LocalDateTime lastMovementDate = LocalDateTime.of(2025, 4, 5, 12, 0, 0);
        LocalDateTime time = LocalDateTime.of(2025, 4, 5, 12, 2, 0);

        Courier courier = Mockito.mock(Courier.class);
        when(courier.getId()).thenReturn(1L);
        Optional<Courier> optionalCourier = Optional.of(courier);

        when(courierEntityService.findByCourierPlate(any())).thenReturn(optionalCourier);

        CourierLocationInfoDto courierLocationInfoDto = mock(CourierLocationInfoDto.class);
        when(courierLocationInfoDto.getCourierPlate()).thenReturn("61ASD114");
        when(courierLocationInfoDto.getTime()).thenReturn(time);

        Delivery delivery = mock(Delivery.class);
        when(delivery.getId()).thenReturn(1L);

        DeliveryMovement deliveryMovement = mock(DeliveryMovement.class);
        when(deliveryMovement.getMovementDate()).thenReturn(lastMovementDate);

        when(deliveryEntityService.findActiveDeliveryByCourierId(any())).thenReturn(delivery);
        when(deliveryMovementEntityService.findLastDeliveryMovementByDeliveryId(any())).thenReturn(deliveryMovement);
        when(locationControlService.getClosestStoreToTheLocation(anyDouble(), anyDouble())).thenReturn(Optional.empty());

        courierTrackingService.saveCourierPosition(courierLocationInfoDto);
    }

    @Test
    void shouldSaveCourierPosition_whenReenteredFalseAndInRangeOfStores() {

        LocalDateTime lastMovementDate = LocalDateTime.of(2025, 4, 5, 12, 0, 0);
        LocalDateTime time = LocalDateTime.of(2025, 4, 5, 12, 2, 0);

        Courier courier = Mockito.mock(Courier.class);
        when(courier.getId()).thenReturn(1L);
        Optional<Courier> optionalCourier = Optional.of(courier);

        when(courierEntityService.findByCourierPlate(any())).thenReturn(optionalCourier);

        CourierLocationInfoDto courierLocationInfoDto = mock(CourierLocationInfoDto.class);
        when(courierLocationInfoDto.getCourierPlate()).thenReturn("61ASD114");
        when(courierLocationInfoDto.getTime()).thenReturn(time);
        when(courierLocationInfoDto.getLatitude()).thenReturn(40.986106);
        when(courierLocationInfoDto.getLongitude()).thenReturn(29.1161293);

        Delivery delivery = mock(Delivery.class);
        when(delivery.getId()).thenReturn(1L);

        DeliveryMovement deliveryMovement = mock(DeliveryMovement.class);
        when(deliveryMovement.getMovementDate()).thenReturn(lastMovementDate);

        DeliveryMovement movement1 = mock(DeliveryMovement.class);
        when(movement1.getLatitude()).thenReturn(40.986106);
        when(movement1.getLongitude()).thenReturn(29.1161293);

        DeliveryMovement movement2 = mock(DeliveryMovement.class);
        when(movement2.getLatitude()).thenReturn(41.986106);
        when(movement2.getLongitude()).thenReturn(28.1161293);

        DeliveryMovement movement3 = mock(DeliveryMovement.class);
        when(movement3.getLatitude()).thenReturn(42.986106);
        when(movement3.getLongitude()).thenReturn(27.1161293);

        List<DeliveryMovement> deliveryMovementList = new ArrayList<>();
        deliveryMovementList.add(movement1);
        deliveryMovementList.add(movement2);
        deliveryMovementList.add(movement3);

        StoreInfoDto storeInfoDto = mock(StoreInfoDto.class);
        Optional<StoreInfoDto> storeInfoDtoOptional = Optional.of(storeInfoDto);

        when(deliveryEntityService.findActiveDeliveryByCourierId(any())).thenReturn(delivery);
        when(deliveryMovementEntityService.findLastDeliveryMovementByDeliveryId(any())).thenReturn(deliveryMovement);
        when(deliveryMovementEntityService.findAllByDeliveryIdOrderByMovementDate(any())).thenReturn(deliveryMovementList);
        when(deliveryEntityService.save(any())).thenReturn(null);
        when(locationControlService.getClosestStoreToTheLocation(anyDouble(), anyDouble())).thenReturn(storeInfoDtoOptional);

        courierTrackingService.saveCourierPosition(courierLocationInfoDto);
    }

    @Test
    void shouldGetTotalDistanceOfCourier() {

        Courier courier = Mockito.mock(Courier.class);
        when(courier.getId()).thenReturn(1L);
        Optional<Courier> optionalCourier = Optional.of(courier);

        when(courierEntityService.findByCourierPlate(any())).thenReturn(optionalCourier);

        Delivery delivery1 = mock(Delivery.class);
        when(delivery1.getDistance()).thenReturn(2.0);

        Delivery delivery2 = mock(Delivery.class);
        when(delivery2.getDistance()).thenReturn(3.0);

        List<Delivery> deliveryList = new ArrayList<>();
        deliveryList.add(delivery1);
        deliveryList.add(delivery2);

        when(deliveryEntityService.findAllCompletedDeliveryListByCourierId(any())).thenReturn(deliveryList);

        Double result = courierTrackingService.getTotalDistanceOfCourier(any());

        assertEquals(5.0, result);
    }

    @Test
    void shouldGetTotalDistanceOfCourier_WhenThereIsNoCompletedDelivery() {

        Courier courier = Mockito.mock(Courier.class);
        when(courier.getId()).thenReturn(1L);
        Optional<Courier> optionalCourier = Optional.of(courier);

        when(courierEntityService.findByCourierPlate(any())).thenReturn(optionalCourier);

        List<Delivery> deliveryList = new ArrayList<>();

        when(deliveryEntityService.findAllCompletedDeliveryListByCourierId(any())).thenReturn(deliveryList);

        Double result = courierTrackingService.getTotalDistanceOfCourier(any());

        assertEquals(0.0, result);
    }

    @Test
    void shouldGetTotalDistanceOfCourier_EvenDistanceIsNull() {

        Courier courier = Mockito.mock(Courier.class);
        when(courier.getId()).thenReturn(1L);
        Optional<Courier> optionalCourier = Optional.of(courier);

        when(courierEntityService.findByCourierPlate(any())).thenReturn(optionalCourier);

        Delivery delivery1 = mock(Delivery.class);
        when(delivery1.getDistance()).thenReturn(2.0);

        Delivery delivery2 = mock(Delivery.class);
        when(delivery2.getDistance()).thenReturn(null);

        List<Delivery> deliveryList = new ArrayList<>();
        deliveryList.add(delivery1);
        deliveryList.add(delivery2);

        when(deliveryEntityService.findAllCompletedDeliveryListByCourierId(any())).thenReturn(deliveryList);

        Double result = courierTrackingService.getTotalDistanceOfCourier(any());

        assertEquals(2.0, result);
    }


}