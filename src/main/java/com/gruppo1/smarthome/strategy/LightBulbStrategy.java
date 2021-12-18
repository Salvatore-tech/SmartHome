package com.gruppo1.smarthome.strategy;

import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.LightBulb;
import org.json.JSONException;
import org.json.JSONObject;

public class LightBulbStrategy extends ConverterFromJsonToDevice {

    @Override
    public void convert(JSONObject deviceJson, Device device) throws JSONException {

        LightBulb lightBulb = (LightBulb) device;

        super.convert(deviceJson, device);

        if (deviceJson.has("brightness")) {
            lightBulb.setBrightness((Integer) deviceJson.get("brightness"));
        }
        if (deviceJson.has("colorTemp")) {
            lightBulb.setColorTemperature((String) deviceJson.get("colorTemp"));
        }
    }
}
