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

    public ConditionId(String idDevice, String idScene) {
        this.deviceId = idDevice;
        this.sceneId = idScene;
    }

    public ConditionId() {

    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
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
