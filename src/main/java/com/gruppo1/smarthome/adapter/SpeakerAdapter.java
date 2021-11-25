package com.gruppo1.smarthome.adapter;

import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.device.Speaker;
import org.json.JSONException;
import org.json.JSONObject;

public class SpeakerAdapter extends DeviceAdapter {

    @Override
    public void run(JSONObject deviceJson, Device device) throws JSONException {

        Speaker speaker = (Speaker) device;

        super.run(deviceJson, device);

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
