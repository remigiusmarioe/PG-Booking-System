package com.pgbooking.pg.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Entity
@Data
@ToString(exclude = {"pg"})
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Table(name = "pg_images")
public class PgImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pg_id")
    @JsonBackReference
    private Pg pg;
}
