package jp.cds.siri.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GoogleResponse {
    @JsonProperty("CompleteSuggestion")
    private CompleteSuggestion completeSuggestion;

    @Data
    public static class CompleteSuggestion {
        private Suggestion suggestion;
    }

    @Data
    public static class Suggestion {
        private String data;
    }

}
