package controllers;

import com.city.autocomplete.application.Constants;
import com.city.autocomplete.application.models.ApiResponse;
import com.city.autocomplete.application.models.Suggestion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class SuggestionControllersIntegrationTests extends BaseContorllerIntegrationTest {

    public static final double DELTA = 0.001;

    @Test
    public void TesSuggestionControllerReturns200() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/suggestion?q=Quebec")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void TestSuggestionControllerRecoversFromBadLimitInput() {
        ResponseEntity<ApiResponse> resp = submitRequest("/suggestion?q=Quebec&limit=invalid");
        assertEquals(400, resp.getStatusCodeValue());
        assertEquals(Constants.RESPONSE_ERROR_MESSAGE, resp.getBody().getMessage());
        assertTrue(resp.getBody().getErrors().get(0).toLowerCase().contains("limit: failed to convert property value of type"));
    }

    @Test
    public void TestSuggestionControllerRecoversFromBadLongitudeInput() {
        ResponseEntity<ApiResponse> resp = submitRequest("/suggestion?q=Quebec&longitude=invalid");
        assertEquals(400, resp.getStatusCodeValue());
        assertEquals(Constants.RESPONSE_ERROR_MESSAGE, resp.getBody().getMessage());
        assertTrue(resp.getBody().getErrors().get(0).toLowerCase().contains("longitude: failed to convert property value of type"));
    }

    @Test
    public void TestSuggestionControllerRecoversFromBadLatitudeInput() {
        ResponseEntity<ApiResponse> resp = submitRequest("/suggestion?q=Quebec&latitude=invalid");
        assertEquals(400, resp.getStatusCodeValue());
        assertEquals(Constants.RESPONSE_ERROR_MESSAGE, resp.getBody().getMessage());
        assertTrue(resp.getBody().getErrors().get(0).toLowerCase().contains("latitude: failed to convert property value of type"));
    }

    @Test
    public void TestSuggestionControllerRecoversFromBadScoreInput() {
        ResponseEntity<ApiResponse> resp = submitRequest("/suggestion?q=Quebec&minScore=invalid");
        assertEquals(400, resp.getStatusCodeValue());
        assertEquals(Constants.RESPONSE_ERROR_MESSAGE, resp.getBody().getMessage());
        assertTrue(resp.getBody().getErrors().get(0).toLowerCase().contains("minscore: failed to convert property value of type"));
    }

    @Test
    public void TestSuggestionControllerRecoversFromUnknownParam() {
        ResponseEntity<ApiResponse> resp = submitRequest("/suggestion?q=Quebec&Test=test&unknown=unknown");
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
        assertEquals(Constants.RESPONSE_SUCCESS_MESSAGE, resp.getBody().getMessage());
    }

    @Test
    public void TestSuggestionControllerValidatesLimitParam() {
        ResponseEntity<ApiResponse> resp = submitRequest("/suggestion?q=Quebec&limit=2001");
        assertEquals(HttpStatus.BAD_REQUEST.value(), resp.getStatusCodeValue());
        assertEquals("limit: " + Constants.LIMIT_DEFAULT_ERROR_MESSAGE, resp.getBody().getErrors().get(0));

        resp = submitRequest("/suggestion?q=Quebec&limit=0");
        assertEquals(HttpStatus.BAD_REQUEST.value(), resp.getStatusCodeValue());
        assertEquals("limit: " + Constants.LIMIT_DEFAULT_ERROR_MESSAGE, resp.getBody().getErrors().get(0));
    }

    @Test
    public void TestSuggestionControllerValidatesLongitudeParam() {
        ResponseEntity<ApiResponse> resp = submitRequest("/suggestion?q=Quebec&longitude=-180.01");
        assertEquals(HttpStatus.BAD_REQUEST.value(), resp.getStatusCodeValue());
        assertEquals("longitude: " + Constants.LONGITUDE_DEFAULT_ERROR_MESSAGE, resp.getBody().getErrors().get(0));

        resp = submitRequest("/suggestion?q=Quebec&longitude=180.01");
        assertEquals(HttpStatus.BAD_REQUEST.value(), resp.getStatusCodeValue());
        assertEquals("longitude: " + Constants.LONGITUDE_DEFAULT_ERROR_MESSAGE, resp.getBody().getErrors().get(0));
    }

    @Test
    public void TestSuggestionControllerValidatesMinScoreParam() {
        ResponseEntity<ApiResponse> resp = submitRequest("/suggestion?q=Quebec&minScore=-1");
        assertEquals(HttpStatus.BAD_REQUEST.value(), resp.getStatusCodeValue());
        assertEquals("minScore: " + Constants.MIN_SCORE_DEFAULT_ERROR_MESSAGE, resp.getBody().getErrors().get(0));

        resp = submitRequest("/suggestion?q=Quebec&minScore=1.01");
        assertEquals(HttpStatus.BAD_REQUEST.value(), resp.getStatusCodeValue());
        assertEquals("minScore: " + Constants.MIN_SCORE_DEFAULT_ERROR_MESSAGE, resp.getBody().getErrors().get(0));
    }

    @Test
    public void TestSuggestionControllerComputesTheRightLimit() {
        ResponseEntity<ApiResponse> resp = submitRequest("/suggestion?q=Quebec&limit=232");
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
        assertEquals(232, resp.getBody().getSuggestions().size());

        resp = submitRequest("/suggestion?q=Quebec&limit=14");
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
        assertEquals(14, resp.getBody().getSuggestions().size());
    }

    @Test
    public void TestSuggestionControllerCorrectlySortsTheResponseInDescendingOrder() {
        ResponseEntity<ApiResponse> resp = submitRequest("/suggestion?q=Quebec");
        List<Suggestion> suggestions = resp.getBody().getSuggestions();
        for (int front = 1; front < suggestions.size(); front++) {
            assertTrue(suggestions.get(front - 1).score >= suggestions.get(front).score);
        }
    }

    @Test
    public void TestSuggestionControllerCanUseDifferentSimilarityAlgos() {
        // using JaroWinkler algo
        ResponseEntity<ApiResponse> resp = submitRequest("/suggestion?q=Queb&similarityAlgo=Jaro");
        List<Suggestion> suggestions = resp.getBody().getSuggestions();
        assertEquals(0.933, suggestions.get(0).score, DELTA);

        // using Levenshtein
        resp = submitRequest("/suggestion?q=Queb");
        suggestions = resp.getBody().getSuggestions();
        assertEquals(0.667, suggestions.get(0).score, DELTA);

        // This shows how JaroWinkler have better similarity matching score
        // for strings that have longer matching prefix

        // using JaroWinkler
        resp = submitRequest("/suggestion?q=ebe&similarityAlgo=Jaroc");
        suggestions = resp.getBody().getSuggestions();
        assertEquals(0.6, suggestions.get(0).score, DELTA);

        // using Levenshtein
        resp = submitRequest("/suggestion?q=ebec");
        suggestions = resp.getBody().getSuggestions();
        assertEquals(0.667, suggestions.get(0).score, DELTA);
    }

    @Test
    public void TestSuggestionControllerMissingQuery() {
        ResponseEntity<ApiResponse> resp = submitRequest("/suggestion");
        assertEquals(HttpStatus.BAD_REQUEST.value(), resp.getStatusCodeValue());
        assertEquals("q: " + Constants.QUERY_DEFAULT_MESSAGE, resp.getBody().getErrors().get(0));
    }

    @Test
    public void TestSuggestionControllerQueryStringLegth() {
        ResponseEntity<ApiResponse> resp = submitRequest("/suggestion?q=123456789101112121415161718192021");
        assertEquals(HttpStatus.BAD_REQUEST.value(), resp.getStatusCodeValue());
        assertEquals("q: " + Constants.QUERY_DEFAULT_LENGTH_MESSAGE, resp.getBody().getErrors().get(0));
    }

    @Test
    public void TestSuggestionControllerCouldReturnMultipleErrors() {
        ResponseEntity<ApiResponse> resp = submitRequest("/suggestion?longitude=23333&limit=-123123&minScore=-22");
        assertEquals(HttpStatus.BAD_REQUEST.value(), resp.getStatusCodeValue());
        assertEquals(4, resp.getBody().getErrors().size());
    }

    @Test
    public void TestSuggestionControllerWithAltNames() {
        ResponseEntity<ApiResponse> resp = submitRequest("/suggestion?q=Амос");
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());
        assertTrue(resp.getBody().getSuggestions().get(0).name.contains("Amos"));
        assertTrue(resp.getBody().getSuggestions().get(0).score == 1);
    }

    @Test
    public void TestSuggestionControllerAppliesMinScoreCorrectly() {
        ResponseEntity<ApiResponse> resp = submitRequest("/suggestion?q=quebec&minScore=0.65");
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());

        for(Suggestion suggestion: resp.getBody().getSuggestions()) {
            if (suggestion.name.contains("Ingersoll")) {
                assertTrue(suggestion.score >= 0.65);
            }
        }
    }

    @Test
    public void TestSuggestionControllerUsesLocationToImproveResults() {
        // 43.03339	-80.88302 Ingersoll CA
        // without location info
        ResponseEntity<ApiResponse> resp = submitRequest("/suggestion?q=Ingers");
        assertEquals(HttpStatus.OK.value(), resp.getStatusCodeValue());

        Suggestion suggestionResWithoutLocation = null;
        for(Suggestion suggestion: resp.getBody().getSuggestions()) {
            if (suggestion.name.contains("Ingersoll")) {
                suggestionResWithoutLocation = suggestion;
                break;
            }
        }

        // within 30 kn
        ResponseEntity<ApiResponse> respWithLocation = submitRequest("/suggestion?q=Ingers&latitude=43.03339&longitude=-80.88302");
        Suggestion suggestionResWithLocation = null;
        for(Suggestion suggestion: respWithLocation.getBody().getSuggestions()) {
            if (suggestion.name.contains("Ingersoll")) {
                suggestionResWithLocation = suggestion;
                break;
            }
        }
        assertEquals(suggestionResWithLocation.score,(suggestionResWithoutLocation.score + Constants.WITHIN_30_KM_SCORE), DELTA);

        // within 60km
        respWithLocation = submitRequest("/suggestion?q=Ingers&latitude=43.53339&longitude=-80.88302");
        suggestionResWithLocation = null;
        for(Suggestion suggestion: respWithLocation.getBody().getSuggestions()) {
            if (suggestion.name.contains("Ingersoll")) {
                suggestionResWithLocation = suggestion;
                break;
            }
        }
        assertEquals(suggestionResWithLocation.score,(suggestionResWithoutLocation.score + Constants.WITHIN_60_KM_SCORE), DELTA);
    }
}
