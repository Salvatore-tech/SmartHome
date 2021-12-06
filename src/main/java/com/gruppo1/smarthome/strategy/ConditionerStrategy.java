package com.gruppo1.smarthome.strategy;

import com.gruppo1.smarthome.model.device.Conditioner;
import com.gruppo1.smarthome.model.device.Device;
import org.json.JSONException;
import org.json.JSONObject;

public class ConditionerStrategy extends ConverterFromJsonToDevice {

    @Override
    public void convert(JSONObject deviceJson, Device device) throws JSONException {
        Conditioner conditioner = (Conditioner) device;

        super.convert(deviceJson, device);

        if (deviceJson.has("settings")) {
            conditioner.setSettings(deviceJson.get("settings").toString());
        }
        if (deviceJson.has("temperature")) {
            conditioner.setTemperature((Integer) deviceJson.get("temperature"));
        }
    }
}
