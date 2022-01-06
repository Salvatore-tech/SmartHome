package com.gruppo1.smarthome.strategy;

import com.gruppo1.smarthome.model.AlarmClock;
import com.gruppo1.smarthome.model.Device;
import org.json.JSONException;
import org.json.JSONObject;

public class AlarmClockStrategy extends ConverterFromJsonToDevice {

    @Override
    public void convert(JSONObject deviceJson, Device device) throws JSONException {

        AlarmClock alarmClock = (AlarmClock) device;
        super.convert(deviceJson, device);
        if (deviceJson.has("time")) {
            alarmClock.setTime(deviceJson.get("time").toString());
        }
        if(deviceJson.has("song")){
            alarmClock.setSong(deviceJson.get("song").toString());
        }
    }
}
