package com.pgbooking.pg.controller;


import com.pgbooking.pg.dto.CreatePgRequest;
import com.pgbooking.pg.dto.PgResponse;
import com.pgbooking.pg.dto.UpdatePgRequest;
import com.pgbooking.pg.entity.Pg;
import com.pgbooking.pg.enums.GenderType;
import com.pgbooking.pg.enums.OccupancyType;
import com.pgbooking.pg.service.PgService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "PG APIs",description = "Operation related to Pg Management")
@RestController
@RequestMapping("/pgs")
@RequiredArgsConstructor
@Slf4j
public class PgController {
    private final PgService pgService;

    @Operation(summary = "Create PG", description = "Create a new Pg with room types and images")
    @ApiResponses({@ApiResponse(responseCode = "201",description = "PG Successfully Created"),
            @ApiResponse(responseCode = "400",description = "Validation Failed")})
    @PostMapping
    public ResponseEntity<PgResponse> createPg(@Valid @RequestBody CreatePgRequest request){

        log.info("Received request for createPg endpoint");

       com.pgbooking.pg.dto.PgResponse response = pgService.createPg(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Get PG By Id",
            description = "Fetch PG details using PG Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "PG Found"),
            @ApiResponse(responseCode = "404", description = "PG Not Found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PgResponse> getPgById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                pgService.getPgById(id)
        );
    }

    @Operation(
            summary = "Get All PGs",
            description = "Returns paginated PG list"
    )
    @GetMapping
    public ResponseEntity<Page<PgResponse>> getAllPgsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                pgService.getAllPgs(page, size, sortBy, direction)
        );
    }

    @Operation(
            summary = "Update PG",
            description = "Updates existing PG details"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "PG Updated"),
            @ApiResponse(responseCode = "404", description = "PG Not Found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PgResponse> updatePg(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePgRequest request) {

        return ResponseEntity.ok(
                pgService.updatePg(id, request)
        );
    }

    @Operation(
            summary = "Delete PG",
            description = "Deletes PG using Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "PG Deleted"),
            @ApiResponse(responseCode = "404", description = "PG Not Found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePg(
            @PathVariable Long id) {

        pgService.deletePg(id);

        return ResponseEntity.ok("PG Deleted Successfully");
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<PgResponse>> getPgByCity(@PathVariable String city){
        log.info("Received request for getPgByCity endpoint");
        return ResponseEntity.ok(pgService.getPgByCity(city));
    }

    @GetMapping("/gender/{genderType}")
    public ResponseEntity<List<PgResponse>> getPgByGender(@PathVariable GenderType genderType){
        log.info("Received request for getPgByGender endpoint");
        return ResponseEntity.ok(pgService.getPgByGender(genderType));
    }

    @GetMapping("/locality/{locality}")
    public ResponseEntity<List<PgResponse>> getPgByLocality(@PathVariable String locality){
        log.info("Received request for getPgByLocality endpoint");
        return ResponseEntity.ok(pgService.getPgByLocality(locality));
    }

    @GetMapping("/rent")
    public ResponseEntity<List<PgResponse>> searchRentRange(@RequestParam Double minRent, @RequestParam Double maxRent){
        log.info("Received request for searchRentRange endpoint");
        return ResponseEntity.ok(pgService.searchByRentRange(minRent,maxRent));
    }

    @GetMapping("/occupancy/{occupancyType}")
    public ResponseEntity<List<PgResponse>> searchByOccupancy(@PathVariable OccupancyType occupancyType){
        log.info("Received request for searchByOccupancy endpoint");
        return ResponseEntity.ok(pgService.searchByOccupancy(occupancyType));
    }


    @Operation(
            summary = "Dynamic Search",
            description = """
                Search PGs using multiple filters:
                city,
                locality,
                gender,
                rent range,
                sorting,
                pagination
                """
    )
    @GetMapping("/search")
    public ResponseEntity<Page<PgResponse>> searchPgs(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) GenderType genderType,
            @RequestParam(required = false) String locality,
            @RequestParam(required = false) Double minRent,
            @RequestParam(required = false) Double maxRent,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                pgService.searchPgs(
                        city,
                        genderType,
                        locality,
                        minRent,
                        maxRent,
                        page,
                        size,
                        sortBy,
                        direction
                )
        );
    }



}
