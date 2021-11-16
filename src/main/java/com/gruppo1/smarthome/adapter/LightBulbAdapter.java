package com.gruppo1.smarthome.adapter;

import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.LightBulb;
import org.json.JSONException;
import org.json.JSONObject;

public class LightBulbAdapter extends AdapterInterface {

    @Override
    public void run(JSONObject deviceJson, Device device) throws JSONException {

        LightBulb lightBulb = (LightBulb) device;

        super.run(deviceJson, device);

        if (deviceJson.has("brightness")) {
            lightBulb.setBrightness((Integer) deviceJson.get("brightness"));
        }
        if (deviceJson.has("colorTemp")) {
            lightBulb.setColorTemp((String) deviceJson.get("colorTemp"));
        }
    }
}
