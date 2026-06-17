package com.pgbooking.pg.service;

import com.pgbooking.pg.dto.CreatePgRequest;
import com.pgbooking.pg.dto.PgResponse;
import com.pgbooking.pg.dto.UpdatePgRequest;
import com.pgbooking.pg.entity.Pg;
import com.pgbooking.pg.enums.GenderType;
import com.pgbooking.pg.enums.OccupancyType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PgService {

    PgResponse createPg(CreatePgRequest request);
    PgResponse getPgById(Long id);
    List<PgResponse> getAllPgs();
    PgResponse updatePg(Long id,UpdatePgRequest request);
    String deletePg(Long id);

    List<PgResponse> getPgByCity(String city);
    List<PgResponse> getPgByGender(GenderType genderType);
    List<PgResponse> getPgByLocality(String locality);

    List<PgResponse> searchPgByCityAndLocality(String city, String locality);

    List<PgResponse> searchByRentRange(Double minRent,Double maxRent);
    List<PgResponse> searchByOccupancy(OccupancyType occupancy);

    Page<PgResponse> getAllPgs(int page, int size,String sortBy, String direction);

    List<PgResponse> searchBySpecification(String city);

    Page<PgResponse> searchPgs(
            String city,
            GenderType genderType,
            String locality,
            Double minRent,
            Double maxRent,
            int page,
            int size,
            String sortBy,
            String direction
    );
}
