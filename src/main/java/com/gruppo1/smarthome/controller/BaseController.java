package com.gruppo1.smarthome.controller;

import com.gruppo1.smarthome.crud.api.CrudOperation;
import com.gruppo1.smarthome.crud.memento.Memento;
import com.gruppo1.smarthome.service.GenericService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@Api(value = "Generic", description = "Rest API for handle base requests", tags = {"Generic"})
@RequestMapping("/home")
public class BaseController {

    private GenericService genericService;

    @Autowired
    public BaseController(GenericService genericService) {
        this.genericService = genericService;
    }

    @GetMapping("/undo")
    public ResponseEntity undoLastOperation(){
        CrudOperation undoOperation = genericService.undo();
        return Objects.nonNull(undoOperation) ?  new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/history")
    public ResponseEntity<List<Memento>> showHistory(){
        return new ResponseEntity<>(genericService.getHistory(), HttpStatus.OK);
    }

}
