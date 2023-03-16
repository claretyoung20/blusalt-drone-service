package com.blusalt.droneservice.web.rest;


import com.blusalt.droneservice.models.DroneState;
import com.blusalt.droneservice.models.dto.DroneStateDto;
import com.blusalt.droneservice.service.DroneStateService;
import com.blusalt.droneservice.web.rest.errors.ErrorResponse;
import com.blusalt.droneservice.web.rest.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.blusalt.droneservice.models.DroneState}.
 */
@RestController
@RequestMapping("/api")
public class DroneStateResource {

    private final Logger log = LoggerFactory.getLogger(DroneStateResource.class);

    private final DroneStateService droneStateService;

    public DroneStateResource(DroneStateService droneStateService) {
        this.droneStateService = droneStateService;
    }


    /**
     * {@code POST  /drone-states} : Create a new DroneState.
     *
     * @param droneStateDto the DroneState to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new DroneState, or with status {@code 400 (Bad Request)} if the DroneState has already an ID.
     */
    @PostMapping("/drone-states")
    public ResponseEntity<ApiResponse<DroneState>>  createDroneState(@RequestBody DroneStateDto droneStateDto) {
        log.debug("REST request to save DroneState : {}", droneStateDto);

        DroneState result = droneStateService.save(droneStateDto);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Created a new drone state", result));
    }

    /**
     * {@code PUT  /drone-states/:id} : Updates an existing DroneState status.
     *
     * @param id the id of the DroneState to save.
     * @param droneStateDto the DroneState info to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated DroneState,
     * or with status {@code 400 (Bad Request)} if the DroneState is not valid,
     * or with status {@code 500 (Internal Server Error)} if the DroneState couldn't be updated.
     */
    @PutMapping("/drone-states/{id}")
    public ResponseEntity<ApiResponse<DroneState>> updateDroneStateStatus(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody DroneStateDto droneStateDto
    ) {
        log.debug("REST request to update DroneState status id: {}, dto: {}", id, droneStateDto);

        DroneState result = droneStateService.updateStatus(droneStateDto, id);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), String.format("Drone state with id %d", id), result));
    }

    /**
     * {@code GET  /all-active/drone-states} : get all active the drone states.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of drone states in body.
     */
    @GetMapping("/all-active/drone-states")
    public ResponseEntity<ApiResponse<List<DroneState>>> getAllActiveDroneStates() {
        log.debug("REST request to get all active DroneStates");
        List<DroneState> entityList = droneStateService.findAllActive();
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "List of all active drone state", entityList));
    }

    /**
     * {@code GET  /all/drone-states} : get all the droneStates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of droneStates in body.
     */
    @GetMapping("/all/drone-states")
    public ResponseEntity<ApiResponse<List<DroneState>>> getAllDroneStates() {
        log.debug("REST request to get all DroneStates");
        List<DroneState> entityList = droneStateService.findAll();
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "List of all drone state", entityList));
    }

    /**
     * {@code GET  /drone-states/:id} : get the "id" DroneState.
     *
     * @param id the id of the DroneState to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the DroneState, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/drone-states/id/{id}")
    public ResponseEntity<ApiResponse<DroneState>> getDroneStateById(@PathVariable Long id) {
        log.debug("REST request to get DroneState : {}", id);
        Optional<DroneState> DroneState = droneStateService.findById(id);

        if(DroneState.isEmpty()) {
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, String.format("state with id: %d not found", id) );
        }
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "state Found", DroneState.get()));

    }

    /**
     * {@code GET  /drone-states/:name} : get the "id" DroneState.
     *
     * @param state the name of the DroneState to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the DroneState, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/drone-states/state/{state}")
    public ResponseEntity<ApiResponse<DroneState>> getDroneStateByName(@PathVariable String state) {

        log.debug("REST request to get DroneState : {}", state);

        Optional<DroneState> DroneState = droneStateService.findByState(state);

        if(DroneState.isEmpty()) {

            throw new ErrorResponse(HttpStatus.BAD_REQUEST, String.format("state with name: %s not found", state) );

        }

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "state Found", DroneState.get()));
    }

    /**
     * {@code DELETE  /drone-states/:id} : delete the "id" DroneState.
     *
     * @param id the id of the DroneState to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/drone-states/{id}")
    public ResponseEntity<ApiResponse<?>> deleteDroneState(@PathVariable Long id) {
        log.debug("REST request to delete DroneState : {}", id);
        droneStateService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "state deleted"));
    }
}
