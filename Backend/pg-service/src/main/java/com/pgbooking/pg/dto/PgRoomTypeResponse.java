package com.pgbooking.pg.dto;

import com.pgbooking.pg.enums.OccupancyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PgRoomTypeResponse {

    private Long id;

    private OccupancyType occupancyType;

    private Double rent;
    private Integer availableRooms;
}
