package com.blusalt.droneservice.service;


import com.blusalt.droneservice.models.PackageInfo;
import com.blusalt.droneservice.models.dto.PackageInfoListDto;

/**
 * Service Interface for managing {@link PackageInfo}.
 */
public interface PackageInfoService {

    /**
     * Save a drone.
     *
     * @param packageInfoDto the entity to save.
     * @return the persisted entity.
     */
    void save(PackageInfoListDto packageInfoDto);

}
