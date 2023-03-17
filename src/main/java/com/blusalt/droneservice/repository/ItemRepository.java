package com.blusalt.droneservice.repository;

import com.blusalt.droneservice.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the DroneState entity.
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByCode(String code);

    List<Item>findAllByCodeIn(List<String> code);
}
