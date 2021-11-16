package com.gruppo1.smarthome.adapter;

import com.gruppo1.smarthome.model.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Transactional
public class AdapterService {

    AdapterInterface adapterInterface;

    public AdapterService ()
    {}

    public void adapt(JSONObject deviceObject, Device device) throws JSONException {

        String typeDevice = deviceObject.get("type").toString().toLowerCase();
        if (typeDevice.equalsIgnoreCase("alarmclock")) {
            adapterInterface = new AlarmClockAdapter();

        } else if (typeDevice.equalsIgnoreCase("conditioner")) {
            adapterInterface = new ConditionerAdapter();
        } else if (typeDevice.equalsIgnoreCase("lightbulb")) {
            adapterInterface = new LightBulbAdapter();
        } else if (typeDevice.equalsIgnoreCase("speaker")) {
            adapterInterface = new SpeakerAdapter();
        } else if (typeDevice.equalsIgnoreCase("television")) {
            adapterInterface = new TelevisionAdapter();
        }
        adapterInterface.run(deviceObject, device);
    }

}
