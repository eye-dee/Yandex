package com.yandex.metrics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.io.IOException;

public class SearchesShower {
    public static void main(String[] args) throws IOException, JSONException {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("app-context-yandex-metrics.xml");
        ctx.refresh();

        JsonCreator jsonCreator = (SearchesShowJsonCreator) ctx.getBean("searchesShowJsonCreator");

        JSONObject json = jsonCreator.getJson();
        JSONArray arr = json.getJSONArray("data");
        for (int i = 0; i < arr.length(); i++) {
            System.out.println(arr.getJSONObject(i).getJSONArray("dimensions").getJSONObject(0).getString("name"));
        }
    }
}
