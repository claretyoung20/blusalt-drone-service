package com.blusalt.droneservice.web.rest;


import com.blusalt.droneservice.models.Address;
import com.blusalt.droneservice.service.AddressService;
import com.blusalt.droneservice.web.rest.errors.ErrorResponse;
import com.blusalt.droneservice.web.rest.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.blusalt.droneservice.models.Address}.
 */
@RestController
@RequestMapping("/api")
public class AddressResource {

    private final Logger log = LoggerFactory.getLogger(AddressResource.class);

    private final AddressService addressService;


    public AddressResource(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * {@code GET  /addresses} : get all the addresses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of addresses in body.
     */
    @GetMapping("/addresses")
    public ResponseEntity<ApiResponse<List<Address>>> getAllAddresses() {
        log.debug("REST request to get all Addresses");

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "List of Addresses", addressService.findAll()));
    }

    /**
     * {@code GET  /addresses/:id} : get the "id" address.
     *
     * @param id the id of the address to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the address, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/addresses/{id}")
    public ResponseEntity<ApiResponse<Address>> getAddress(@PathVariable Long id) {

        log.debug("REST request to get Address : {}", id);

        Optional<Address> address = addressService.findOne(id);

        if(address.isEmpty()) {

            throw new ErrorResponse(HttpStatus.BAD_REQUEST, String.format("Address with id: %d not found", id) );

        }

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Address Found", address.get()));
    }
}
