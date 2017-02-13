package com.yandex.metrics;

import java.util.List;

public class SearchPhraseYandexDirectDto {
    private ReportTypeEnum type = ReportTypeEnum.SEARCH_PHRASE_YANDEX_DIRECT;
    private String reason;
    private List<SearchPhraseDto> successPhrases;
    private List<SearchPhraseDto> failurePhrases;

    public SearchPhraseYandexDirectDto(String reason, List<SearchPhraseDto> successPhrases, List<SearchPhraseDto> failurePhrases) {
        this.reason = reason;
        this.successPhrases = successPhrases;
        this.failurePhrases = failurePhrases;
    }

    public ReportTypeEnum getType() {
        return type;
    }

    public String getReason() {
        return reason;
    }

    public List<SearchPhraseDto> getSuccessPhrases() {
        return successPhrases;
    }

    public List<SearchPhraseDto> getFailurePhrases() {
        return failurePhrases;
    }
}
