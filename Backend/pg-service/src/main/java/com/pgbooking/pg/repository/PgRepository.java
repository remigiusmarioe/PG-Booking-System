package com.pgbooking.pg.repository;

import com.pgbooking.pg.entity.Pg;
import com.pgbooking.pg.enums.GenderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PgRepository extends JpaRepository<Pg,Long>, JpaSpecificationExecutor<Pg> {


    List<Pg> findByCityIgnoreCase(String city);


    List<Pg> findByGenderType(GenderType genderType);

    List<Pg> findByLocalityIgnoreCase(String locality);

    List<Pg> findByCityAndLocalityIgnoreCase(String city, String locality);



}
