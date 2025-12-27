package com.wazbus.stationservice.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Station {
    @Id
    private String id;
    private String code;
    private String name;
    private String city;
    private String country;
}
