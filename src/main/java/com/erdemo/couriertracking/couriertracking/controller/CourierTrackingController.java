package com.erdemo.couriertracking.couriertracking.controller;

import com.erdemo.couriertracking.couriertracking.dto.CourierLocationInfoDto;
import com.erdemo.couriertracking.couriertracking.dto.CourierSaveRequestDto;
import com.erdemo.couriertracking.couriertracking.entity.Courier;
import com.erdemo.couriertracking.couriertracking.service.CourierTrackingService;
import com.erdemo.couriertracking.generic.dto.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/couriers")
public class CourierTrackingController {

    private final CourierTrackingService courierTrackingService;

    @PostMapping
    public ResponseEntity<RestResponse> saveCourier(
            @Valid @RequestBody CourierSaveRequestDto courierSaveRequestDto){

        Courier courier = courierTrackingService.saveCourier(courierSaveRequestDto);

        return ResponseEntity.ok(RestResponse.of(courier.getId()));
    }

    @PostMapping("/positions")
    public ResponseEntity<RestResponse> saveCourierPosition(
            @Valid @RequestBody CourierLocationInfoDto courierLocationInfoDto){

        courierTrackingService.saveCourierPosition(courierLocationInfoDto);

        return ResponseEntity.ok(RestResponse.empty());
    }

    @GetMapping("/total-distance/{courierPlate}")
    public ResponseEntity<RestResponse> getTotalDistanceOfCourier(@PathVariable String courierPlate){

        if (!courierPlate.matches("^[a-zA-Z0-9]+$")) {
            throw new IllegalArgumentException("Plaka sadece harf ve rakam içermelidir.");
        }

        Double totalDistance = courierTrackingService.getTotalDistanceOfCourier(courierPlate);

        if (Objects.isNull(totalDistance) || totalDistance.equals(0.0)) {
            return ResponseEntity.ok(RestResponse.error("Kuryenin tamamlanmış teslimatı bulunmamaktadır."));
        }
        return ResponseEntity.ok(RestResponse.of(totalDistance));

    }
}
