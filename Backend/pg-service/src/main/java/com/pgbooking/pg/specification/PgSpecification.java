package com.pgbooking.pg.specification;

import com.pgbooking.pg.entity.Pg;
import com.pgbooking.pg.enums.GenderType;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class PgSpecification {
    public static Specification<Pg> hasCity(String city) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("city"), city);
    }

    public static Specification<Pg> hasGender(GenderType genderType) {
        return (root, query, cb) ->
                cb.equal(root.get("genderType"), genderType);
    }

    public static Specification<Pg> hasLocality(String locality) {
        return (root, query, cb) ->
                cb.equal(root.get("locality"), locality);
    }
    public static Specification<Pg> hasMinRent(Double minRent) {
        return (root, query, criteriaBuilder) -> {

            Join<Object, Object> roomTypeJoin = root.join("roomTypes");

            return criteriaBuilder.greaterThanOrEqualTo(
                    roomTypeJoin.get("rent"),
                    minRent
            );
        };
    }

    public static Specification<Pg> hasMaxRent(Double maxRent) {
        return (root, query, criteriaBuilder) -> {

            Join<Object, Object> roomTypeJoin = root.join("roomTypes");

            return criteriaBuilder.lessThanOrEqualTo(
                    roomTypeJoin.get("rent"),
                    maxRent
            );
        };
    }

}
