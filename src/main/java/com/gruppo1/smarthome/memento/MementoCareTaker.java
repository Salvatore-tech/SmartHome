package com.gruppo1.smarthome.memento;

import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.command.impl.AddOperationImpl;
import com.gruppo1.smarthome.command.impl.DeleteOperationImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class MementoCareTaker {

    private final List<MementoCommand> mementoCommandList = new ArrayList<>();

    private class MementoCommand {
        CrudOperation operation;
        Memento memento;

        public MementoCommand(CrudOperation operation, Memento memento) {
            this.operation = operation;
            this.memento = memento;
        }

        public CrudOperation getOperation() {
            return operation;
        }

        public Memento getMemento() {
            return memento;
        }
    }

    public void push(CrudOperation operation, Memento memento) {
        mementoCommandList.add(new MementoCommand(operation, memento));
    }


    public int undo() {
        CrudOperation undoOperation = null;
        MementoCommand lastMementoCommand = getUndo();
        if (Objects.nonNull(lastMementoCommand))
            undoOperation = executeUndoOperation(lastMementoCommand);
        if (Objects.nonNull(undoOperation))
            return 1;
        return 0;
    }

    public List<ImmutablePair<String, String>> getMementoCommandList() {
        List<ImmutablePair<String, String>> output = new ArrayList<>();
        mementoCommandList.forEach(
                element -> {
                    String pathOperation = element.getOperation().toString();
                    String operation = pathOperation.substring(pathOperation.lastIndexOf(".")+1, pathOperation.indexOf("@"));
                    String stringToStamp = StringUtils.join(
                            StringUtils.splitByCharacterTypeCamelCase(operation),
                            ' '
                    );
                    String pathClass = element.getOperation().getRepository().getClass().getInterfaces()[0].getName();
                    stringToStamp += " Of "+ pathClass.substring(pathClass.lastIndexOf(".")+1,pathClass.indexOf("Repo"));
                    if (Objects.nonNull(element.getMemento())) {
                        output.add(new ImmutablePair(element.getMemento().getName(), stringToStamp));
                    } else {
                        output.add(new ImmutablePair(StringUtils.EMPTY, stringToStamp));
                    }
                }
        );
        return output;
    }

    private MementoCommand getUndo() {
        if (mementoCommandList.isEmpty())
            return null;
        return mementoCommandList.get(mementoCommandList.size() - 1);
    }

    private CrudOperation executeUndoOperation(MementoCommand mementoCommand) {
        CrudOperation operationReverted;
        CrudOperation lastOperationPerformed = mementoCommand.getOperation();
        Memento lastMemento = mementoCommand.getMemento();
        if (lastOperationPerformed.getClass().equals(AddOperationImpl.class)) {
            operationReverted = new DeleteOperationImpl(lastOperationPerformed.getRepository());
            operationReverted.execute(lastMemento);
        } else if (lastOperationPerformed.getClass().equals(DeleteOperationImpl.class)) {
            operationReverted = new AddOperationImpl(lastOperationPerformed.getRepository());
            operationReverted.execute(lastMemento);
        } else
            operationReverted = null;
        return operationReverted;
    }

}
