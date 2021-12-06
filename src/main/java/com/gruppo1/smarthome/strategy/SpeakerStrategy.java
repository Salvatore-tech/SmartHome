package com.gruppo1.smarthome.strategy;

import com.gruppo1.smarthome.model.device.Device;
import com.gruppo1.smarthome.model.device.Speaker;
import org.json.JSONException;
import org.json.JSONObject;

public class SpeakerStrategy extends ConverterFromJsonToDevice {

    @Override
    public void convert(JSONObject deviceJson, Device device) throws JSONException {

        Speaker speaker = (Speaker) device;

        super.convert(deviceJson, device);

        if (deviceJson.has("power")) {
            speaker.setPower((Integer) deviceJson.get("power"));
        }
        if(deviceJson.has("brand")){
            speaker.setBrand(deviceJson.get("brand").toString());
        }
        if(deviceJson.has("model")){
            speaker.setModel(deviceJson.get("model").toString());
        }
    }
}
