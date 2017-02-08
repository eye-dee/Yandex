package com.yandex.metrics;

import org.json.JSONObject;

public interface JsonCreator {
    JSONObject getJson();
    String getStringJson();
}
