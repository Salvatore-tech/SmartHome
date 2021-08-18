package com.gruppo1.smarthome.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ConditionId implements Serializable {
    @Column(name = "device_id")
    private String deviceId;
    @Column(name = "scene_id")
    private String sceneId;

    public ConditionId() {}

    public ConditionId(String idDevice, String idScene) {
        this.deviceId = idDevice;
        this.sceneId = idScene;
    }

    public String getIdDevice() {
        return deviceId;
    }

    public void setIdDevice(String idDevice) {
        this.deviceId = idDevice;
    }

    public String getIdScene() {
        return sceneId;
    }

    public void setIdScene(String idScene) {
        this.sceneId = idScene;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConditionId that = (ConditionId) o;
        return deviceId.equals(that.deviceId) && sceneId.equals(that.sceneId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceId, sceneId);
    }

}
