package com.gruppo1.smarthome.adapter;

import com.gruppo1.smarthome.model.Device;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class FactoryDeviceAdapter {

    DeviceAdapter deviceAdapter;

    public void adapt(JSONObject deviceObject, Device device) throws JSONException {
        String typeDevice = deviceObject.get("type").toString().toLowerCase();

        if (typeDevice.equalsIgnoreCase("alarmclock")) {
            deviceAdapter = new AlarmClockAdapter();
        } else if (typeDevice.equalsIgnoreCase("conditioner")) {
            deviceAdapter = new ConditionerAdapter();
        } else if (typeDevice.equalsIgnoreCase("lightbulb")) {
            deviceAdapter = new LightBulbAdapter();
        } else if (typeDevice.equalsIgnoreCase("speaker")) {
            deviceAdapter = new SpeakerAdapter();
        } else if (typeDevice.equalsIgnoreCase("television")) {
            deviceAdapter = new TelevisionAdapter();
        }
        deviceAdapter.run(deviceObject, device);
    }
}
