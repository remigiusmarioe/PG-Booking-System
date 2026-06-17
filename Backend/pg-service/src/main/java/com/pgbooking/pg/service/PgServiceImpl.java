package com.pgbooking.pg.service;

import com.pgbooking.pg.dto.CreatePgRequest;
import com.pgbooking.pg.dto.PgResponse;
import com.pgbooking.pg.dto.PgRoomTypeResponse;
import com.pgbooking.pg.dto.UpdatePgRequest;
import com.pgbooking.pg.entity.Pg;
import com.pgbooking.pg.entity.PgImage;
import com.pgbooking.pg.entity.PgRoomType;
import com.pgbooking.pg.enums.GenderType;
import com.pgbooking.pg.enums.OccupancyType;
import com.pgbooking.pg.exception.PgNotFoundException;
import com.pgbooking.pg.repository.PgImageRepository;
import com.pgbooking.pg.repository.PgRepository;
import com.pgbooking.pg.repository.PgRoomTypeRepository;
import com.pgbooking.pg.specification.PgSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PgServiceImpl  implements PgService {
    private final PgRepository pgRepository;
    private final PgRoomTypeRepository pgRoomTypeRepository;
    private final PgImageRepository pgImageRepository;

    @Override
    public PgResponse createPg(CreatePgRequest request) {

        log.info("Received request to Create Pg");

        Pg pg = Pg.builder()
                .ownerId(request.getOwnerId())
                .pgName(request.getPgName())
                .description(request.getDescription())
                .city(request.getCity())
                .locality(request.getLocality())
                .address(request.getAddress())
                .genderType(request.getGenderType())
                .amenities(request.getAmenities())
                .build();
        request.getRoomTypeRequests().forEach(roomRequest -> {

            PgRoomType roomType = new PgRoomType();

            roomType.setOccupancyType(roomRequest.getOccupancyType());
            roomType.setRent(roomRequest.getRent());
            roomType.setAvailableRooms(roomRequest.getAvailableRooms());
            roomType.setPg(pg);

            pg.getRoomTypes().add(roomType);
        });
        Pg savedPg = pgRepository.save(pg);

        List<String> imageUrl = Optional.ofNullable(request.getImageUrls()).orElse(Collections.emptyList());

        List<PgImage> images = imageUrl
                .stream()
                .map(url -> {PgImage image = new PgImage();
                    image.setImageUrl(url);
                    image.setPg(savedPg);
                return image;
                })
                .toList();
        pgImageRepository.saveAll(images);

        log.info(" Pg Created and saved - Response: {}",convertToResponse(savedPg));

        return convertToResponse(savedPg);
    }

    @Override
    public PgResponse getPgById(Long id) {
        log.info("Received request to Get Pg with id: {}", id);

        Pg pg = pgRepository.findById(id).orElseThrow(()->new PgNotFoundException("PG Not Found"));

        log.info("Pg Found - Response: {}",convertToResponse(pg));

        return  convertToResponse(pg);

    }
    @Override
    public List<PgResponse> getAllPgs() {
        List<Pg> pgs = pgRepository.findAll();
        return pgs.stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public PgResponse updatePg(Long id, UpdatePgRequest request){
        log.info("Received request to Update Pg with id: {}", id);
        Pg pg = pgRepository.findById(id).orElseThrow(()->new PgNotFoundException("PG Not Found"));

        pg.setOwnerId(request.getOwnerId());
        pg.setPgName(request.getPgName());
        pg.setDescription(request.getDescription());
        pg.setCity(request.getCity());
        pg.setLocality(request.getLocality());
        pg.setAddress(request.getAddress());
        pg.setGenderType(request.getGenderType());
        pg.setAmenities(request.getAmenities());

        Pg updatedPg = pgRepository.save(pg);
        log.info("Updated Pg with id: {},{}",updatedPg.getId(),updatedPg);

        return  convertToResponse(updatedPg);
    }

    @Override
    public String deletePg(Long id) {
        log.info("Received request to delete Pg with id: {}", id);

       Pg pg = pgRepository.findById(id).orElseThrow(()->new PgNotFoundException("PG Not Found"));

       pgRepository.delete(pg);

       log.info("Deleted Pg with id: {}",id);
       return "PG Deleted Successfully";

    }
    private PgResponse convertToResponse(Pg pg) {

        List<PgRoomTypeResponse> roomTypes =
                pg.getRoomTypes()
                        .stream()
                        .map(room -> new PgRoomTypeResponse(
                                room.getId(),
                                room.getOccupancyType(),
                                room.getRent(),
                                room.getAvailableRooms()
                        ))
                        .toList();

        List<String> imageUrls= pg.getImages()
                .stream()
                .map(PgImage::getImageUrl)
                .toList();

        PgResponse response = new PgResponse();

        response.setId(pg.getId());
        response.setPgName(pg.getPgName());
        response.setCity(pg.getCity());
        response.setRoomTypes(roomTypes);
        response.setImageUrls(imageUrls);

        return response;
    }

    @Override
    public List<PgResponse> getPgByCity(String city){
        List<Pg> pgs = pgRepository.findByCityIgnoreCase(city);
        log.info("Fetched Pgs In the City :{}",city);

        return pgs.stream()
                .map(this::convertToResponse)
                .toList();
    }
    @Override
    public List<PgResponse> getPgByGender(GenderType genderType){
        List<Pg> pgs = pgRepository.findByGenderType(genderType);
        log.info("Fetched Pgs In the Gender :{}",genderType);
        return pgs.stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public  List<PgResponse> getPgByLocality(String locality){
        List<Pg> pgs = pgRepository.findByLocalityIgnoreCase(locality);
        log.info("Fetched Pgs In the Locality :{}",locality);
        return pgs.stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public List<PgResponse> searchPgByCityAndLocality(
            String city,
            String locality) {

        List<Pg> pgs =
                pgRepository.findByCityAndLocalityIgnoreCase(
                        city,
                        locality);

        log.info("Fetched Pgs In the City & Locality : {} & {}",
                city,
                locality);

        return pgs.stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public List<PgResponse>searchByRentRange(Double minRent,Double maxRent){
        log.info("Received request for searchByRentRange betweeen {} ",minRent,maxRent);
        List<PgRoomType> roomTypes = pgRoomTypeRepository.findByRentBetween(minRent,maxRent);

        return roomTypes.stream()
                .map(PgRoomType::getPg)
                .distinct()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public List<PgResponse> searchByOccupancy(OccupancyType occupancyType){
        log.info("Received request for  searchByOccupancy  Occupancy Type: {}",occupancyType);
        List<PgRoomType> roomTypes = pgRoomTypeRepository.findByOccupancyType(occupancyType);

        return roomTypes.stream()
                .map(PgRoomType::getPg)
                .distinct()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public Page<PgResponse> getAllPgs(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Pg> pgPage = pgRepository.findAll(pageable);

        return pgPage.map(this::convertToResponse);
    }

    @Override
    public List<PgResponse> searchBySpecification(String city) {

        Specification<Pg> spec =
                PgSpecification.hasCity(city);

        return pgRepository.findAll(spec)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
   public Page<PgResponse> searchPgs(
            String city,
            GenderType genderType,
            String locality,
            Double minRent,
            Double maxRent,
            int page,
            int size,
            String sortBy,
            String direction
    ) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable =
                PageRequest.of(page, size, sort);

        Specification<Pg> spec = null;

        if (city != null) {
            spec = Specification.allOf(
                    spec,
                    PgSpecification.hasCity(city)
            );
        }

        if (genderType != null) {
            spec = Specification.allOf(
                    spec,
                    PgSpecification.hasGender(genderType)
            );
        }
        if (minRent != null) {
            spec = Specification.allOf(
                    spec,
                    PgSpecification.hasMinRent(minRent));
        }

        if (maxRent != null) {
            spec =  Specification.allOf(
                    spec,
                    PgSpecification.hasMaxRent(minRent)
            );;
        }

        if (locality != null) {
            spec = Specification.allOf(
                    spec,
                    PgSpecification.hasLocality(locality)
            );
        }

        Page<Pg> pgPage =
                pgRepository.findAll(spec, pageable);

        return pgPage.map(this::convertToResponse);
    }


}
