package com.yandex.metrics;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class SearchesShowJsonCreator implements JsonCreator {
    UrlCreator urlCreator;

    public SearchesShowJsonCreator(UrlCreator urlCreator){
        this.urlCreator = urlCreator;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public JSONObject getJson(){
        InputStream is;
        try {
            is = new URL(urlCreator.getUrl()).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } catch (IOException io){
            io.printStackTrace();
        } catch (JSONException json) {
            json.printStackTrace();
        } finally {
            //is.close();
        }
        return null;
    }
}
