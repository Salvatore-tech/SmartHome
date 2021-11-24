package com.gruppo1.smarthome.adapter;

import com.gruppo1.smarthome.model.Device;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class DeviceAdapter {

    void run(JSONObject deviceJson, Device deviceToAdapt) throws JSONException {
        deviceToAdapt.setType(deviceJson.get("type").toString());
        deviceToAdapt.setName(deviceJson.get("name").toString());
        deviceToAdapt.setStatus(Boolean.parseBoolean(deviceJson.get("status").toString()));
    }
}
