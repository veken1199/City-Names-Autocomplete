package com.city.autocomplete.application.models;

import com.city.autocomplete.application.Constants;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApiResponse {
    private List<Suggestion> suggestions;
    private String status;
    private String message;
    private List<String> errors;

    public static ApiResponse buildResponse(List<Suggestion> suggestions, String status, List<String> errors) {
        ApiResponse resp = new ApiResponse();
        resp.suggestions = suggestions;
        resp.status = status;
        resp.message = getResponseMessage(errors);
        resp.errors = errors;
        return resp;
    }

    private static String getResponseMessage(List<String> errors) {
        if (errors == null) return Constants.RESPONSE_SUCCESS_MESSAGE;
        return Constants.RESPONSE_ERROR_MESSAGE;
    }
}

