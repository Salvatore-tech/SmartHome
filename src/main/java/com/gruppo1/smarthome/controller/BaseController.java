package com.gruppo1.smarthome.controller;

import com.gruppo1.smarthome.service.GenericService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value = "Generic", description = "Rest API to manage generic requests", tags = {"Generic"})
@RequestMapping("/home")
public class BaseController {

    private final GenericService genericService;

    @Autowired
    public BaseController(GenericService genericService) {
        this.genericService = genericService;
    }

    @GetMapping("/undo")
    @ApiOperation(value = "Undo the last operation performed", tags = {"Generic"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Undo performed correctly"),
            @ApiResponse(code = 400, message = "The previous operation is not undoable")
    })
    public ResponseEntity undoLastOperation() {
        return genericService.undo() == 1 ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/history")
    @ApiOperation(value = "List the previous operations performed", tags = {"Generic"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operations previously performed")})
    public ResponseEntity<List<ImmutablePair<String, String>>> showHistory() {
        return new ResponseEntity<>(genericService.getHistory(), HttpStatus.OK);

    }
}
