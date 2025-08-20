package com.erdemo.couriertracking.couriertracking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierLocationInfoDto {

    @NotNull
    @PastOrPresent
    private LocalDateTime time;

    @NotNull
    @Size(max = 10)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Plaka Bilgisi Sadece Harf Ve Rakam İçerebilir.")
    private String courierPlate;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;


}
