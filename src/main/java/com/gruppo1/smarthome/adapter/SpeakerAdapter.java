package com.gruppo1.smarthome.adapter;

import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.Speaker;
import org.json.JSONException;
import org.json.JSONObject;

public class SpeakerAdapter extends AdapterInterface {

    @Override
    public void run(JSONObject deviceJson, Device device) throws JSONException {

        Speaker speaker = (Speaker) device;

        super.run(deviceJson, device);

        if (deviceJson.has("status")) {
            speaker.setStatus(Boolean.parseBoolean(deviceJson.get("status").toString()));
        }
        if (deviceJson.has("volume")) {
            speaker.setPower((Integer) deviceJson.get("volume"));
        }
    }
}
