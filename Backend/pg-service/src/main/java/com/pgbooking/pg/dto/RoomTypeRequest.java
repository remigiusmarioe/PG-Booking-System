package com.pgbooking.pg.dto;

import com.pgbooking.pg.enums.OccupancyType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomTypeRequest {

    @NotNull(message = "Occupancy Type is required")
    private OccupancyType occupancyType;

    @NotNull(message = "Rent is required")
    @Positive(message = "Rent Must be greater then zero")
    private Double rent;

    @NotNull(message = "Available rooms is required")
    @PositiveOrZero(message = "Available rooms cannot be negative")
    private Integer availableRooms;


}
