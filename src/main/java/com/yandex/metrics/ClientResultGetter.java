package com.yandex.metrics;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ClientResultGetter {
    JsonCreator jsonCreator;
    ObjectMapper objectMapper = new ObjectMapper();
    private Double highFailureVisits;
    private Integer highFailureLinksAmount;

    public void setHighFailureVisits(Double highFailureVisits) {
        this.highFailureVisits = highFailureVisits;
    }

    public void setHighFailureLinksAmount(Integer highFailureLinksAmount) {
        this.highFailureLinksAmount = highFailureLinksAmount;
    }

    public void setJsonCreator(JsonCreator jsonCreator) {
        this.jsonCreator = jsonCreator;
    }

    public ResultAbstraction getResult(String msg){
        if ("dimensions".equals(msg)){
            JSONObject json = jsonCreator.getJson();

            try {
                List<String> res = new ArrayList<>();
                JSONArray arr = json.getJSONArray("data");
                for (int i = 0; i < arr.length(); i++) {
                    res.add(arr.getJSONObject(i).getJSONArray("dimensions").getJSONObject(0).getString("name"));
                }
                return null;
            } catch (JSONException jsone){
                jsone.printStackTrace();
            }
        } else if ("highFailure".equals(msg)) {
            try {
                JsonNode node = objectMapper.readValue(jsonCreator.getStringJson(), JsonNode.class);

                int total_rows = node.get("total_rows").asInt();
                node = node.get("data");

                List<HighFailureResult> highFailureResults = new ArrayList<>();

                for (int i = 0; i < total_rows; ++i){
                    JsonNode tempNode = node.get(i);
                    //HighFailureResult highFailureResult = objectMapper.readValue(node.get(i).toString(),HighFailureResult.class);

                    HighFailureResult highFailureResult = new HighFailureResult(tempNode.get("dimensions").get(0).get("name").asText(),
                            tempNode.get("metrics").get(0).asInt(),
                            tempNode.get("metrics").get(1).asDouble());
                    
                    highFailureResults.add(highFailureResult);
                }

                highFailureResults = highFailureResults.stream().
                        filter(highFailureResult -> (highFailureResult.getVisits() > highFailureVisits))
                        .sorted(new Comparator<HighFailureResult>() {
                            @Override
                            public int compare(HighFailureResult highFailureResult, HighFailureResult t1) {
                                return (highFailureResult.getBounceRate() > t1.getBounceRate())? -1
                                        : (highFailureResult.getBounceRate() < t1.getBounceRate()? 1
                                        : 0);
                            }
                        })
                        .collect(Collectors.toList());

                HighFailureResultDto highFailureResultDto = new HighFailureResultDto();
                highFailureResultDto.setHighFailureResults(highFailureResults);

                return highFailureResultDto;
            } catch (IOException ioe){
                ioe.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException();
        }
        return null;
    }
}