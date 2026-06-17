package com.pgbooking.pg.dto;

import com.pgbooking.pg.enums.AmenityType;
import com.pgbooking.pg.enums.GenderType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePgRequest {

    @NotNull(message = "Owner Id is required")
    private Long ownerId;
    @NotBlank(message = "Pg Name cannot be empty")
    @Size(min = 3,max = 100,message = "Pg Name must be between 3-100 characters")
    private String pgName;

    private String description;
    @NotBlank(message = "city cannot be empty")
    private String city;
    @NotBlank(message = "locality cannot be empty")
    private String locality;
    @NotBlank(message = "Address cannot be empty")
    private String address;
    @NotNull(message = "Gender type is required")
    private GenderType genderType;

    @NotEmpty(message = "Amenities cannot be empty")
    private Set<AmenityType> amenities;

    @NotEmpty(message = "Room types are required")
    @Valid
    private List<RoomTypeRequest> roomTypeRequests;

    private List<String> imageUrls;


}
