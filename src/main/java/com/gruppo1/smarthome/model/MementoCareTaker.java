package com.gruppo1.smarthome.model;

import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.command.impl.AddOperationImpl;
import com.gruppo1.smarthome.command.impl.DeleteOperationImpl;
import com.gruppo1.smarthome.command.impl.UpdateOperationImpl;
import com.gruppo1.smarthome.memento.Memento;
import com.gruppo1.smarthome.repository.BaseSmartHomeRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MementoCareTaker {

    private final Map<String, SmartHomeItem> mappings = new HashMap<>();

    private final List<MementoCommand> mementoCommandList = new ArrayList<>();

    private static class MementoCommand {
        final CrudOperation operation;
        final Memento memento;

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

    public MementoCareTaker() {
        mappings.put(AlarmClock.MementoAlarmClock.class.toString(), new AlarmClock());
        mappings.put(Condition.MementoCondition.class.toString(), new Condition());
        mappings.put(Conditioner.MementoConditioner.class.toString(), new Conditioner()); // TODO
        mappings.put(LightBulb.MementoLightBulb.class.toString(), new LightBulb());
        mappings.put(Profile.MementoProfile.class.toString(), new Profile());
        mappings.put(Room.MementoRoom.class.toString(), new Room());
        mappings.put(Scene.MementoScene.class.toString(), new Scene());
        mappings.put(Speaker.MementoSpeaker.class.toString(), new Speaker());
        mappings.put(Television.MementoTelevision.class.toString(), new Television());
    }

    public void push(CrudOperation operation, Memento memento) {
        mementoCommandList.add(new MementoCommand(operation, memento));
    }

    public int undo() {
        MementoCommand lastMementoCommand = getUndo();
        if (Objects.nonNull(lastMementoCommand))
            if (Objects.nonNull(executeUndoOperation(lastMementoCommand)))
                return 1;
        return 0;
    }

    public List<ImmutablePair<String, String>> getMementoCommandList() {
        List<ImmutablePair<String, String>> output = new ArrayList<>();
        mementoCommandList.forEach(
                element -> {
                    String pathOperation = element.getOperation().toString();
                    String operation = pathOperation.substring(pathOperation.lastIndexOf(".") + 1, pathOperation.indexOf("@"));
                    String stringToStamp = StringUtils.join(
                            StringUtils.splitByCharacterTypeCamelCase(operation),
                            ' '
                    );
                    String pathClass = element.getOperation().getRepository().getClass().getInterfaces()[0].getName();
                    stringToStamp += " Of " + pathClass.substring(pathClass.lastIndexOf(".") + 1, pathClass.indexOf("Repo"));
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

    private <Any> Any executeUndoOperation(MementoCommand mementoCommand) {
        CrudOperation operationReverted;
        CrudOperation lastOperationPerformed = mementoCommand.getOperation();
        Memento lastMemento = mementoCommand.getMemento();
        BaseSmartHomeRepository repository = lastOperationPerformed.getRepository();

        if (lastOperationPerformed instanceof AddOperationImpl) {
            operationReverted = new DeleteOperationImpl(repository);
        } else if (lastOperationPerformed instanceof DeleteOperationImpl) {
            operationReverted = new AddOperationImpl(repository);
        } else if (lastOperationPerformed instanceof UpdateOperationImpl) {
            operationReverted = new UpdateOperationImpl(repository);
        } else
            return null;

        SmartHomeItem originator = mappings.get(lastMemento.getClass().toString());
        mementoCommandList.add(new MementoCommand(null, null));
        return operationReverted.execute(originator.restore(lastMemento));
    }
}
