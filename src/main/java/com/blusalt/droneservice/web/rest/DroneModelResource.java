package com.blusalt.droneservice.web.rest;


import com.blusalt.droneservice.models.DroneModel;
import com.blusalt.droneservice.models.dto.DroneModelDto;
import com.blusalt.droneservice.service.DroneModelService;
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
 * REST controller for managing {@link  com.blusalt.droneservice.models.DroneModel}.
 */
@RestController
@RequestMapping("/api")
public class DroneModelResource {

    private final Logger log = LoggerFactory.getLogger(DroneModelResource.class);

    private final DroneModelService droneModelService;

    public DroneModelResource(DroneModelService droneModelService) {
        this.droneModelService = droneModelService;
    }

    /**
     * {@code POST  /drone-models} : Create a new droneModel.
     *
     * @param droneModelDto the droneModel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new droneModel, or with status {@code 400 (Bad Request)} if the droneModel has already an ID.
     */
    @PostMapping("/drone-models")
    public ResponseEntity<ApiResponse<DroneModel>>  createDroneModel(@RequestBody DroneModelDto droneModelDto) {
        log.debug("REST request to save DroneModel : {}", droneModelDto);

        DroneModel result = droneModelService.save(droneModelDto);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Created a new drone model", result));
    }

    /**
     * {@code PUT  /drone-models/:id} : Updates an existing droneModel status.
     *
     * @param id the id of the droneModel to save.
     * @param droneModelDto the droneModel info to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated droneModel,
     * or with status {@code 400 (Bad Request)} if the droneModel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the droneModel couldn't be updated.
     */
    @PutMapping("/drone-models/{id}")
    public ResponseEntity<ApiResponse<DroneModel>> updateDroneModelStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DroneModelDto droneModelDto
    ) {
        log.debug("REST request to update DroneModel status id: {}, dto: {}", id, droneModelDto);

        DroneModel result = droneModelService.updateStatus(droneModelDto, id);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), String.format("Drone model with id %d", id), result));
    }

    /**
     * {@code GET  /all-active/drone-models} : get all active the droneModels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of droneModels in body.
     */
    @GetMapping("/all-active/drone-models")
    public ResponseEntity<ApiResponse<List<DroneModel>>> getAllActiveDroneModels() {
        log.debug("REST request to get all active DroneModels");
        List<DroneModel> entityList = droneModelService.findAllActive();
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "List of all active drone model", entityList));
    }

    /**
     * {@code GET  /all/drone-models} : get all the droneModels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of droneModels in body.
     */
    @GetMapping("/all/drone-models")
    public ResponseEntity<ApiResponse<List<DroneModel>>> getAllDroneModels() {
        log.debug("REST request to get all DroneModels");
        List<DroneModel> entityList = droneModelService.findAll();
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "List of all drone model", entityList));
    }

    /**
     * {@code GET  /drone-models/id/:id} : get the "id" droneModel.
     *
     * @param id the id of the droneModel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the droneModel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/drone-models/id/{id}")
    public ResponseEntity<ApiResponse<DroneModel>> getDroneModelById(@PathVariable Long id) {
        log.debug("REST request to get DroneModel : {}", id);
        Optional<DroneModel> droneModel = droneModelService.findById(id);

        if(droneModel.isEmpty()) {
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, String.format("Model with id: %d not found", id) );
        }
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Model Found", droneModel.get()));

    }

    /**
     * {@code GET  /drone-models/name/:name} : get the "id" droneModel.
     *
     * @param name the name of the droneModel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the droneModel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/drone-models/{name}")
    public ResponseEntity<ApiResponse<DroneModel>> getDroneModelByName(@PathVariable String name) {

        log.debug("REST request to get DroneModel : {}", name);

        Optional<DroneModel> droneModel = droneModelService.findByName(name);

        if(droneModel.isEmpty()) {

            throw new ErrorResponse(HttpStatus.BAD_REQUEST, String.format("Model with name: %s not found", name) );

        }

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Model Found", droneModel.get()));
    }

    /**
     * {@code DELETE  /drone-models/:id} : delete the "id" droneModel.
     *
     * @param id the id of the droneModel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/drone-models/{id}")
    public ResponseEntity<ApiResponse<?>> deleteDroneModel(@PathVariable Long id) {
        log.debug("REST request to delete DroneModel : {}", id);
        droneModelService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Model deleted"));
    }
}
