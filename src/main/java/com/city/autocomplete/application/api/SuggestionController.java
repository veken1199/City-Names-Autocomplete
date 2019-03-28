package com.city.autocomplete.application.api;

import com.city.autocomplete.application.Constants;
import com.city.autocomplete.application.containers.GeonameContainer;

import com.city.autocomplete.application.models.ApiResponse;
import com.city.autocomplete.application.models.RequestInput;
import com.city.autocomplete.application.models.Suggestion;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(description = "Suggestion REST API endpoint that provides auto-complete suggestions for large cities")
public class SuggestionController {

    @Autowired
    GeonameContainer geonameContainer;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @ApiImplicitParams({
            @ApiImplicitParam(name = "q", value = Constants.QUERY_DEFAULT_MESSAGE, paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "limit", value = Constants.LIMIT_DEFAULT_ERROR_MESSAGE, paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "longitude", value = Constants.LONGITUDE_DEFAULT_ERROR_MESSAGE, paramType = "query", dataType="double"),
            @ApiImplicitParam(name = "latitude", value = Constants.LATITUDE_DEFAULT_ERROR_MESSAGE, paramType = "query", dataType="double"),
            @ApiImplicitParam(name = "minScore", value = Constants.MIN_SCORE_DEFAULT_ERROR_MESSAGE, paramType = "query", dataType="double"),
            @ApiImplicitParam(name = "similarityAlgo", value = Constants.SIMILARITY_ALGO_DEFAULT_MESSAGE, paramType = "query", dataType="string"),
    })
    @GetMapping(value="/suggestion")
    public ApiResponse computeSuggestions(@Valid @ApiParam(Constants.LIMIT_DEFAULT_ERROR_MESSAGE) RequestInput input) {
        logger.info("Request has been received: " + input.toString());
        List<Suggestion> suggestions = geonameContainer.findByNameLike(input);
        return ApiResponse.buildResponse(suggestions, HttpStatus.OK.toString(), null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleErrors(BindException exception) {
        List<String> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        return ApiResponse.buildResponse(null, HttpStatus.BAD_REQUEST.toString(), errors);

    }
}


