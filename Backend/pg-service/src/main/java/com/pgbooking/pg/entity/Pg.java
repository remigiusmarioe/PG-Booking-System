package com.pgbooking.pg.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pgbooking.pg.enums.AmenityType;
import com.pgbooking.pg.enums.GenderType;
import com.pgbooking.pg.enums.OccupancyType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "pgs")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = {"roomTypes","images"})
@Builder
public class Pg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
    @Column(nullable = false)
   private Long ownerId;
    @Column(nullable = false)
   private String pgName;
    @Column(columnDefinition = "TEXT")
   private String description;
    @Column(nullable = false)
   private String city;
    @Column(nullable = false)
   private String locality;
    @Column(nullable = false)
   private String address;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   private GenderType genderType;

   @ElementCollection(targetClass = AmenityType.class)
   @Enumerated(EnumType.STRING)
   @CollectionTable(name = "pg_amenities",joinColumns = @JoinColumn(name = "pg_id"))
    @Column(name = "amenity")
   private Set<AmenityType> amenities;

   @OneToMany(mappedBy = "pg",cascade = CascadeType.ALL, orphanRemoval = true)
   @JsonManagedReference
   @Builder.Default
   private List<PgRoomType> roomTypes = new ArrayList<>();

    @OneToMany(mappedBy = "pg",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Builder.Default
   private List<PgImage> images = new ArrayList<>();


   @Builder.Default
   @Column(nullable = false)
   private Boolean active = true;

   @Builder.Default
   @Column(nullable = false)
   private LocalDateTime createdAt = LocalDateTime.now();





}
