package com.gruppo1.smarthome.crud.beans;

import com.gruppo1.smarthome.crud.api.CrudOperation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CrudOperationExecutor {

    private final List<CrudOperation> operations = new ArrayList<>();

    public Object execute(CrudOperation operation, Object s) {
        operations.add(operation);
        return operation.execute(s);
    }

    public Object execute(CrudOperation operation, String itemName, Object s) {
        operations.add(operation);
        return operation.execute(s, itemName);
    }
}
