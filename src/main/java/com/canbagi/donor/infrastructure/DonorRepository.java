package com.canbagi.donor.infrastructure;

import com.canbagi.donor.domain.DonorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DonorRepository extends JpaRepository<DonorProfile, UUID> {

    Optional<DonorProfile> findByEmail(String email);

    List<DonorProfile> findByBloodType(String bloodType);

    @Query("from DonorProfile d where  d.address.city = :city")
    List<DonorProfile> findByAddress_City(@Param("city") String city);

    boolean existByEmail(String email);
}
