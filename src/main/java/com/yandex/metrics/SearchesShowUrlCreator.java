package com.yandex.metrics;

import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class SearchesShowUrlCreator implements UrlCreator {
    //https://api-metrika.yandex.ru/stat/v1/data?
    // metrics=ym:s:visits,ym:s:pageviews&
    // dimensions=ym:s:firstSearchPhrase&
    // date1=6daysAgo&
    // date2=yesterday&
    // limit=10000&
    // offset=1&
    // ids=34322390&
    // oauth_token=AQAAAAAUQvf-AAK_6er7lEvV5ka6p6cZ1oetBaU
    private String version;
    private List<String> metrics;
    private List<String> dimensions;
    private String date1;
    private String date2;
    private String limit;
    private String offset;
    private List<String> ids;
    private String oauth_token;

    public String getUrl(){
        String res = new String("https://api-metrika.yandex.ru/stat/" + version + "/data?");

        if (!metrics.isEmpty()){
            res += "metrics=";
            res += StringUtils.join(metrics.toArray(),',');
        }

        if (!dimensions.isEmpty()){
            res += "&dimensions=";
            res += StringUtils.join(dimensions.toArray(),',');
        }

        res += "&date1=" + date1 + "&date2=" + date2 + "&limit=" + limit + "&offset=" + offset;

        res += "&ids=";
        res += StringUtils.join(ids.toArray(),',');

        res += "&oauth_token=" + oauth_token;

        return res;
    }

    public void setDate1(@NotNull String date1) {
        this.date1 = date1;
    }

    public void setDate2(@NotNull String date2) {
        this.date2 = date2;
    }

    public void setDimensions(List<String> dimensions) {
        this.dimensions = dimensions;
    }

    public void setIds(@NotNull List<String> ids) {
        this.ids = ids;
    }

    public void setLimit(@NotNull String limit) {
        this.limit = limit;
    }

    public void setMetrics(List<String> metrics) {
        this.metrics = metrics;
    }

    public void setOauth_token(@NotNull String oauth_token) {
        this.oauth_token = oauth_token;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setOffset(@NotNull String offset) {
        this.offset = offset;
    }
}
