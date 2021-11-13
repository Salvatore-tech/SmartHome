package com.gruppo1.smarthome.adapter;

import com.gruppo1.smarthome.model.*;
import org.json.JSONException;
import org.json.JSONObject;

public class TelevisionAdapter extends AdapterInterface {


    @Override
    public void run(JSONObject deviceJson, Device device) throws JSONException {

        Television television = (Television) device;

        super.run(deviceJson, device);

        if (deviceJson.has("status")) {
            television.setStatus(Boolean.parseBoolean(deviceJson.get("status").toString()));
        }
        if (deviceJson.has("volume")) {
            television.setVolume((Integer) deviceJson.get("volume"));
        }
        if (deviceJson.has("channel")) {
            television.setChannel((Integer) deviceJson.get("channel"));
        }
    }
}

