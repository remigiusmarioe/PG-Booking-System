package com.pgbooking.pg.repository;

import com.pgbooking.pg.entity.PgRoomType;
import com.pgbooking.pg.enums.OccupancyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PgRoomTypeRepository extends JpaRepository<PgRoomType, Long> {

    List<PgRoomType>findByRentBetween(Double minRent,Double maxRent);
    List<PgRoomType>findByOccupancyType(OccupancyType occupancy);
}
