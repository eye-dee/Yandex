package com.yandex.metrics;

import org.json.JSONException;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.io.IOException;
import java.util.List;

public class SearchesShower {
    public static void main(String[] args) throws IOException, JSONException {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("app-context-yandex-metrics.xml");
        ctx.refresh();

        ClientResultGetter clientResultGetter = (ClientResultGetter) ctx.getBean("clientResultGetter");

        List<String> res = clientResultGetter.getResult("highFailure");

        if (res != null) {
            for (String i : res) {
                System.out.println(i);
            }
        }
    }
}
