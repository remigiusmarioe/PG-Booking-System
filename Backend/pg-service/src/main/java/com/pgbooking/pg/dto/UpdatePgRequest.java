package com.pgbooking.pg.dto;


import com.pgbooking.pg.enums.AmenityType;
import com.pgbooking.pg.enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePgRequest {

    private Long ownerId;

    private String pgName;

    private String description;

    private String city;

    private String locality;

    private String address;

    private GenderType genderType;

    private Set<AmenityType> amenities;
}
