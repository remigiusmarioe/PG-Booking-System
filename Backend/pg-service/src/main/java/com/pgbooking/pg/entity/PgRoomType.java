package com.pgbooking.pg.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pgbooking.pg.enums.OccupancyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = {"pg"})
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pg_room_types")
public class PgRoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OccupancyType occupancyType;

    @Column(nullable = false)
    private Double rent;

    @Column(nullable = false)
    private Integer availableRooms;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pg_id", nullable = false)
    @JsonBackReference
    private Pg pg;
}
