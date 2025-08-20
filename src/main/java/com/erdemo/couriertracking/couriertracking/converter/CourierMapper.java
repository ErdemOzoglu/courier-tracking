package com.erdemo.couriertracking.couriertracking.converter;

import com.erdemo.couriertracking.couriertracking.dto.CourierSaveRequestDto;
import com.erdemo.couriertracking.couriertracking.entity.Courier;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourierMapper {

    CourierMapper INSTANCE = Mappers.getMapper(CourierMapper.class);

    Courier convertToCourier(CourierSaveRequestDto courierSaveRequestDto);
}
