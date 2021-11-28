package com.gruppo1.smarthome.beans;

import com.gruppo1.smarthome.command.api.CrudOperation;
import org.springframework.stereotype.Component;

@Component
public class CrudOperationExecutor {

    public Object execute(CrudOperation operation, Object homeItemPlaceholder) {
        return operation.execute(homeItemPlaceholder);
    }

    public Object execute(CrudOperation operation, String itemName, Object homeItemPlaceholder) {
        return operation.execute(homeItemPlaceholder, itemName);
    }
}
