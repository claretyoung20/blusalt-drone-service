package com.blusalt.droneservice.service;


import com.blusalt.droneservice.models.Item;
import com.blusalt.droneservice.models.dto.ItemListDto;

/**
 * Service Interface for managing {@link Item}.
 */
public interface ItemService {

    /**
     * Save a drone.
     *
     * @param itemDListDto the entity to save.
     * @return the persisted entity.
     */
    void save(ItemListDto itemDListDto);

}
