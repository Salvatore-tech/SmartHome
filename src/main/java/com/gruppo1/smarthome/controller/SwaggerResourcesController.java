package com.gruppo1.smarthome.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
public class SwaggerResourcesController {

    @ApiIgnore
    @GetMapping("/null/swagger-resources")
    public ResponseEntity swaggerResources() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiIgnore
    @GetMapping("/null/swagger-resources/configuration/ui")
    public ResponseEntity swaggerConfigUI() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
