package com.gruppo1.smarthome.strategy;

import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.device.Television;
import org.json.JSONException;
import org.json.JSONObject;

public class TelevisionStrategy extends ConverterFromJsonToDevice {

    @Override
    public void convert(JSONObject deviceJson, Device device) throws JSONException {

        Television television = (Television) device;

        super.convert(deviceJson, device);

        if (deviceJson.has("volume")) {
            television.setVolume((Integer) deviceJson.get("volume"));
        }
        if(deviceJson.has("channel")) {
            television.setChannel((Integer) deviceJson.get("channel"));
        }
        if(deviceJson.has("brand")){
            television.setBrand(deviceJson.get("brand").toString());
        }
        if(deviceJson.has("model")){
            television.setModel(deviceJson.get("model").toString());
        }
    }
}

