package com.yandex.metrics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ClientResultGetter {
    JsonCreator jsonCreator;

    public void setJsonCreator(JsonCreator jsonCreator) {
        this.jsonCreator = jsonCreator;
    }

    List<String> getResult(String msg){
        if ("dimensions".equals(msg)){
            JSONObject json = jsonCreator.getJson();

            try {
                List<String> res = new ArrayList<>();
                JSONArray arr = json.getJSONArray("data");
                for (int i = 0; i < arr.length(); i++) {
                    res.add(arr.getJSONObject(i).getJSONArray("dimensions").getJSONObject(0).getString("name"));
                }
                return res;
            } catch (JSONException jsone){
                jsone.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException();
        }
        return null;
    }
}
