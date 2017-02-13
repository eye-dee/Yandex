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

    public SearchPhraseYandexDirectDto getResult(String msg){
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

                JsonNode header = node.get("query");
                int totalRows = node.get("total_rows").asInt();

                node = node.get("data");

                List<SearchPhraseDto> searchPhraseDtos = new ArrayList<>();

                for (int i = 0; i < totalRows; ++i) {
                    JsonNode metrics = node.get(i).get("metrics");
                    String phrase = node.get(i).get("dimensions").get(0).get("name").asText();

                    searchPhraseDtos.add(new SearchPhraseDto(phrase, metrics.get(0).asInt(),
                            metrics.get(1).asDouble(),
                            metrics.get(2).asDouble(),
                            metrics.get(3).asDouble(),
                            metrics.get(4).asDouble()));
                }

                return new SearchPhraseYandexDirectDto("searches",searchPhraseDtos,null);
            } catch (IOException ioe){
                ioe.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException();
        }
        return null;
    }
}