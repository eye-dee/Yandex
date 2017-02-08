package com.yandex.metrics;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientResultGetter {
    JsonCreator jsonCreator;
    ObjectMapper objectMapper = new ObjectMapper();
    private Double highFailureValue;
    private Integer highFailureLinksAmount;

    public void setHighFailureValue(Double highFailureValue) {
        this.highFailureValue = highFailureValue;
    }

    public void setHighFailureLinksAmount(Integer highFailureLinksAmount) {
        this.highFailureLinksAmount = highFailureLinksAmount;
    }

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
        } else if ("highFailure".equals(msg)) {
            try {
                JsonNode node = objectMapper.readValue(jsonCreator.getStringJson(), JsonNode.class);

                JsonNode header = node.get("query");
                String metrics1 = header.get("metrics").get(0).asText(),
                        metrics2 = header.get("metrics").get(1).asText();
                int totalRows = node.get("total_rows").asInt();

                node = node.get("data");

                List<Object[]> result = new ArrayList<>();

                for (int i = 0; i < totalRows; ++i){
                    JsonNode metrics = node.get(i).get("metrics");/*
                    System.out.println(metrics1 + " = " + metrics.get(0).asText() +
                                    " " +
                                        metrics2 + " = " + metrics.get(1).asText());*/
                    Object[] arr = new Object[3];
                    arr[0] = node.get(1).get("dimensions").get(0).get("name").asText();
                    arr[1] = metrics.get(0).asDouble();
                    arr[2] = metrics.get(1).asDouble();
                    if ((Double)arr[2] > highFailureValue){
                        result.add(arr);
                    }
                }
                ///result.sort(); ??

                List<String> res = new ArrayList<>();
                for (int i = 0; i < result.size() && i < highFailureLinksAmount; ++i){
                    res.add((String)((Object[])result.get(i))[0]);
                }

                return res;
            } catch (IOException ioe){
                ioe.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException();
        }
        return null;
    }
}
