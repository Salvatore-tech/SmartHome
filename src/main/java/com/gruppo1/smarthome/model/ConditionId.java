package com.gruppo1.smarthome.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ConditionId implements Serializable {
    @Column(name = "device_id")
    private Long deviceId;
    @Column(name = "scene_id")
    private Long sceneId;

    public ConditionId() {}

    public ConditionId(Long idDevice, Long idScene) {
        this.deviceId = idDevice;
        this.sceneId = idScene;
    }

    public Long getIdDevice() {
        return deviceId;
    }

    public void setIdDevice(Long idDevice) {
        this.deviceId = idDevice;
    }

    public Long getIdScene() {
        return sceneId;
    }

    public void setIdScene(Long idScene) {
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
