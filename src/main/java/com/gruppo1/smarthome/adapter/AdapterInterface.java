package com.gruppo1.smarthome.adapter;

import com.gruppo1.smarthome.model.*;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class AdapterInterface {



    void run(JSONObject deviceJson, Device deviceToAdapt) throws JSONException
    {
        deviceToAdapt.setType(deviceJson.get("type").toString());
        deviceToAdapt.setName(deviceJson.get("name").toString());
        deviceToAdapt.setStatus(Boolean.parseBoolean(deviceJson.get("status").toString()));
    }
}
