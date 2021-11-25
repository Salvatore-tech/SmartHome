package com.gruppo1.smarthome.adapter;

import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.device.AlarmClock;
import org.json.JSONException;
import org.json.JSONObject;

public class AlarmClockAdapter extends DeviceAdapter {

    @Override
    public void run(JSONObject deviceJson, Device device) throws JSONException {

        AlarmClock alarmClock = (AlarmClock) device;

        super.run(deviceJson, device);

        if (deviceJson.has("time")) {
            alarmClock.setTime(deviceJson.get("time").toString());
        }
        if(deviceJson.has("frequency")){
            alarmClock.setFrequency(deviceJson.get("frequency").toString());
        }
        if(deviceJson.has("song")){
            alarmClock.setSong(deviceJson.get("song").toString());
        }
    }
}
