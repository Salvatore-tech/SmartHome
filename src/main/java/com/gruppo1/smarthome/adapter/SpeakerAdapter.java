package com.gruppo1.smarthome.adapter;

import com.gruppo1.smarthome.model.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class SpeakerAdapter extends AdapterInterface {


    @Override
    public void run(JSONObject deviceJson, Device device) throws JSONException {

        Speaker speaker = (Speaker) device;

        super.run(deviceJson, device);

        if (deviceJson.has("status")) {
            speaker.setStatus(Boolean.parseBoolean(deviceJson.get("status").toString()));
        }
        if (deviceJson.has("volume")) {
            speaker.setVolume((Integer) deviceJson.get("volume"));
        }
    }
}
