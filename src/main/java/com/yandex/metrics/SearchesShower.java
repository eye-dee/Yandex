package com.yandex.metrics;

import org.json.JSONException;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.io.IOException;

public class SearchesShower {
    public static void main(String[] args) throws IOException, JSONException {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("app-context-yandex-metrics.xml");
        ctx.refresh();

        ClientResultGetter clientResultGetter = (ClientResultGetter) ctx.getBean("clientResultGetter");

        System.out.println(clientResultGetter.getResult("highFailure").getInformation());
    }
}
