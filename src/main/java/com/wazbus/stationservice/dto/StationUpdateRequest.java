package com.wazbus.stationservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class StationUpdateRequest {

    @NotBlank
    @Size(min = 1, max = 10)
    private String code;

    @NotBlank
    @Size(min = 1, max = 20)
    private String name;

    @NotBlank
    @Size(min = 1, max = 20)
    private String city;

    @NotBlank
    @Size(min = 1, max = 20)
    private String country;
}
