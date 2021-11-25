package com.gruppo1.smarthome.adapter;

import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.device.Conditioner;
import org.json.JSONException;
import org.json.JSONObject;

public class ConditionerAdapter extends DeviceAdapter {

    @Override
    public void run(JSONObject deviceJson, Device device) throws JSONException {
        Conditioner conditioner = (Conditioner) device;

        super.run(deviceJson, device);

        if (deviceJson.has("settings")) {
            conditioner.setSettings(deviceJson.get("settings").toString());
        }
        if (deviceJson.has("temperature")) {
            conditioner.setTemperature((Integer) deviceJson.get("temperature"));
        }
    }
}
