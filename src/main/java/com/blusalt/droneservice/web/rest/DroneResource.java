package com.blusalt.droneservice.web.rest;

import com.blusalt.droneservice.models.Delivery;
import com.blusalt.droneservice.models.dto.DroneDto;
import com.blusalt.droneservice.models.dto.DroneUpdateDto;
import com.blusalt.droneservice.models.dto.ItemListDto;
import com.blusalt.droneservice.models.enums.DroneStateConstant;
import com.blusalt.droneservice.models.pojos.DronePojo;
import com.blusalt.droneservice.service.DeliveryService;
import com.blusalt.droneservice.service.DroneService;
import com.blusalt.droneservice.service.ItemService;
import com.blusalt.droneservice.web.rest.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing {@link com.blusalt.droneservice.models.Drone}.
 */
@RestController
@RequestMapping("/api")
public class DroneResource {

    private final Logger log = LoggerFactory.getLogger(DroneResource.class);

    @Inject
    private DroneService droneService;

    @Inject
    protected DeliveryService deliveryService;

    @Inject
    private ItemService itemService;
    /**
     * {@code POST  /drones} : Create a new droneDto.
     *
     * @param droneDto the droneDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new droneDto, or with status {@code 400 (Bad Request)} if the droneDto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/drones")
    public ResponseEntity<ApiResponse<DronePojo>> createDrone(@Valid @RequestBody DroneDto droneDto) throws URISyntaxException {
        log.debug("REST request to save Drone : {}", droneDto);

        DronePojo result = droneService.save(droneDto);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "Created a new drone", result));
    }

    /**
     * {@code PUT  /drones/:id} : Updates an existing droneUpdateDto.
     *
     * @param id the id of the drone to update.
     * @param droneUpdateDto the droneUpdateDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated droneUpdateDto,
     * or with status {@code 400 (Bad Request)} if the droneUpdateDto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the droneUpdateDto couldn't be updated.
     */
    @PutMapping("/drones/{id}")
    public ResponseEntity<ApiResponse<DronePojo>> updateDroneById(@PathVariable(value = "id") final Long id, @Valid @RequestBody DroneUpdateDto droneUpdateDto) {
        log.debug("REST request to update Drone : {}, {}", id, droneUpdateDto);

        DronePojo result = droneService.update(droneUpdateDto, id);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Drone updated..", result));
    }

    /**
     * {@code GET  /drones} : get all the drones.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of drones in body.
     */
    @GetMapping("/active/drones")
    public ResponseEntity<ApiResponse<List<DronePojo>>> getAllActiveDrones() {
        log.debug("REST request to get all Drones");
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "List of drones.", droneService.findAllActiveDrone()));
    }

    @GetMapping("/all/drones")
    public ResponseEntity<ApiResponse<List<DronePojo>>> getAllDrones() {
        log.debug("REST request to get all Drones");
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "List of drones.", droneService.findAllDrone()));
    }

    /**
     * {@code GET  /drones/:id} : get the "id" drone.
     *
     * @param id the id of the drone to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the drone, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/drones/{id}")
    public ResponseEntity<ApiResponse<DronePojo>> getDroneById(@PathVariable Long id) {
        log.debug("REST request to get Drone : {}", id);
        DronePojo drone = droneService.findDroneById(id);

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Drone details", drone));
    }

    /**
     * {@code DELETE  /drones/:id} : delete the "id" drone.
     *
     * @param id the id of the drone to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/drones/{id}")
    public ResponseEntity<ApiResponse<?>> deleteDroneById(@PathVariable Long id) {
        log.debug("REST request to delete Drone : {}", id);
        droneService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Drone deleted"));
    }

    /**
     * {@code GET  /drone/:id/loaded/items} : Get loaded drone items with the "id" drone.
     *
     * @param id the id of the drone to get loaded items.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
     */
    @GetMapping("/drone/{id}/loaded/items")
    public ResponseEntity<ApiResponse<Delivery>> getLoadedDroneItems(@PathVariable Long id) {
        log.debug("REST request to get all Drones");
        Delivery delivery = deliveryService.getLoadedDeliveryByDrone(id);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "List of drone item", delivery));
    }

    /**
     * {@code GET  /available/drones} : get all the active available drones.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of drones in body.
     */
    @GetMapping("/available/drones")
    public ResponseEntity<ApiResponse<List<DronePojo>>> getAllAvailableActiveDrones() {
        log.debug("REST request to get all Drones");
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "List of drones.", droneService.findAllActiveAvailableDrone()));
    }


    /**
     * {@code POST  /drones/load} : load drone
     *
     * @param itemListDto the item info to load to a drone.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and without body, or with status {@code 400 (Bad Request)} if the there is validation error.
     */
    @PostMapping("/drones/load")
    public ResponseEntity<ApiResponse<?>> loadDrone(@Valid @RequestBody ItemListDto itemListDto) {
        log.debug("REST request to load Drone load dto: {}", itemListDto);

        itemService.save(itemListDto);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "loaded drone"));
    }

    @PutMapping("/drone/{id}/state/{state}")
    public ResponseEntity<ApiResponse<DronePojo>> updateDroneState(@PathVariable() Long id, @PathVariable() DroneStateConstant state) {
        log.debug("REST request to change Drone state to: {}", state);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "loaded status changed", droneService.updateDroneState(id, state)));
    }


}
