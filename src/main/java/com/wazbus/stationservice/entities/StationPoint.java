package com.wazbus.stationservice.entities;

import com.wazbus.stationservice.enums.PointType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Document
@Getter
@Setter
public class StationPoint {

    private String code;
    private String stationCode;
    private String tripCode;
    private String name;
    private String address;
    private String landmark;
    private PointType type;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private Set<Long> mobile;

}
