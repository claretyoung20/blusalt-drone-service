package com.blusalt.droneservice.web.rest;


import com.blusalt.droneservice.web.rest.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DeafultAPI {

    private final Logger log = LoggerFactory.getLogger(DeafultAPI.class);


    @GetMapping()
    public ResponseEntity<ApiResponse<String>> getAllAddresses() {
        log.info("Default api");
        String msg =  "Go to /swagger-ui/#/ for API documentation. Thank you";
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Welcome", msg));
    }

}
