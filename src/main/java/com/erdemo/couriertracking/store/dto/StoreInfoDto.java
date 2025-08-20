package com.erdemo.couriertracking.store.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreInfoDto {

    private String name;

    @JsonAlias("lat")
    private Double latitude;

    @JsonAlias("lng")
    private Double longitude;
}
