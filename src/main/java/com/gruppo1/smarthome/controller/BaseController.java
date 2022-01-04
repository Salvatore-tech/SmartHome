package com.gruppo1.smarthome.controller;

import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.memento.Memento;
import com.gruppo1.smarthome.service.GenericService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public ResponseEntity.BodyBuilder undoLastOperation() {
        return genericService.undo() == 1 ? ResponseEntity.ok() : ResponseEntity.badRequest();
    }

    @GetMapping("/history")
    @ApiOperation(value = "List the previous operations performed", tags = {"Generic"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operations previously performed")})
    public ResponseEntity<String> showHistory() {

        List<ImmutablePair<CrudOperation, Memento>> history = genericService.getHistory();
        return new ResponseEntity(serializeList(history), HttpStatus.OK);

    }

    private String serializeList(List<ImmutablePair<CrudOperation, Memento>> mementoPairList) {
        List<JSONObject> output = new ArrayList<>();
        mementoPairList.forEach(

                element -> {

                    if (Objects.nonNull(element.getLeft()) && Objects.nonNull(element.getRight())) {
                        String pathOperation = element.getLeft().toString();
                        String operationCamelCase = pathOperation.substring(pathOperation.lastIndexOf(".") + 1, pathOperation.indexOf("@"));
                        String operation = StringUtils.join(
                                StringUtils.splitByCharacterTypeCamelCase(operationCamelCase),
                                ' '
                        );
                        operation = operation.replace("Impl", "") + "Of A " + element.getRight().getLabel();
                        JSONObject obj = new JSONObject();
                        try {
                            obj.put("Operation", operation);
                            obj.put("item", element.getRight().getName());
                            output.add(obj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    String pathOperation = element.getLeft().toString();
                    String operationCamelCase = pathOperation.substring(pathOperation.lastIndexOf(".") + 1, pathOperation.indexOf("@"));
                    String operation = StringUtils.join(
                            StringUtils.splitByCharacterTypeCamelCase(operationCamelCase),
                            ' '
                    );
                    operation = operation.replace("Impl", "") + "Of A " + element.getRight().getLabel();
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("Operation", operation);
                        obj.put("item", element.getRight().getName());
                        output.add(obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    }
                }
        );
        return output.toString();
    }
}
