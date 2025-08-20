package com.erdemo.couriertracking.couriertracking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierSaveRequestDto {

    @NotNull
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Sadece harf girilebilir")
    private String name;

    @NotNull
    @Size(max = 10)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Plaka Bilgisi Sadece Harf Ve Rakam İçerebilir.")
    private String courierPlate;

}
