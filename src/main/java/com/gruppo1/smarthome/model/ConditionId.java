package com.gruppo1.smarthome.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ConditionId implements Serializable {
    private Long idDevice;
    private Long idScene;

    public ConditionId() {}

    public ConditionId(Long idDevice, Long idScene) {
        this.idDevice = idDevice;
        this.idScene = idScene;
    }

    public Long getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(Long idDevice) {
        this.idDevice = idDevice;
    }

    public Long getIdScene() {
        return idScene;
    }

    public void setIdScene(Long idScene) {
        this.idScene = idScene;
    }
}
