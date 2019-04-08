package com.city.autocomplete.application.containers;

import com.city.autocomplete.application.Constants;
import com.city.autocomplete.application.helpers.BaseHelpers;
import com.city.autocomplete.application.helpers.CoordsCalculator;
import com.city.autocomplete.application.helpers.StringMatcher;
import com.city.autocomplete.application.models.Geoname;
import com.city.autocomplete.application.models.RequestInput;
import com.city.autocomplete.application.models.Suggestion;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class GeonameContainer {

    @Getter @Setter
    private List<Geoname> Geonames;

    /*
    This method is responsible for returning a list of suggestions based on user
    inputs: query string, limit, minScore, (long, lat), and similarity algo
     */
    public List<Suggestion> findByNameLike(RequestInput input) {
        List<Suggestion> suggestions = this.Geonames.stream()
                .map(geoname -> {
                    // we first check if the query matches alternative names
                    double score = StringMatcher.isAlternativeName(geoname.getAltNames(), input.getQ()) ?
                            Constants.MAX_STRING_SIMILARITY_SCORE : input.getSimilarityAlgo().apply(input.getQ(), geoname.getAscii());
                    Suggestion suggestion = Suggestion.build(geoname);
                    suggestion.score = score;
                    return suggestion;
                })
                .filter(suggestion -> suggestion.score >= input.getMinScore())
                .map(suggestion -> {
                    if(Objects.nonNull(input.getLongitude()) && Objects.nonNull(input.getLatitude())) {
                        double positionScore = CoordsCalculator.calculateHaversineDistanceScore(suggestion.latitude,
                                suggestion.longitude,
                                input.getLatitude(),
                                input.getLongitude());
                        suggestion.score = BaseHelpers.formatDecimals(Math.min(positionScore + suggestion.score, Constants.MAX_STRING_SIMILARITY_SCORE));
                    } return suggestion;
                })
                .sorted(Comparator.comparing(Suggestion::getScore, Comparator.reverseOrder()))
                .limit(input.getLimit())
                .collect(Collectors.toList());
        return suggestions;
    }
}
