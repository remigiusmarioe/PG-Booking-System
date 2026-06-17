package com.pgbooking.pg.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PgResponse {

    private Long id;
    private String pgName;
    private String city;
    private List<PgRoomTypeResponse> roomTypes;
    private List<String> imageUrls;
}
