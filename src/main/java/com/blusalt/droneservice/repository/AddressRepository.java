package com.blusalt.droneservice.repository;

import com.blusalt.droneservice.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Address entity.
 */

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {}
