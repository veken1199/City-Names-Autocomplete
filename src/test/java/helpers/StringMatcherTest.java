package helpers;

import com.city.autocomplete.application.helpers.StringMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.city.autocomplete.application.helpers.StringMatcher.isAlternativeName;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(JUnit4.class)
public class StringMatcherTest {

    @Test
    public void TestLevenshteinDistance() {
        String testQueryString = "Quebec";
        String testTarget = "Québec";
        double distance = StringMatcher.levenshteinNormalizedDistanceScore(testQueryString, testTarget);
        assertEquals(0.833, distance, 0.001);

        distance = StringMatcher.levenshteinNormalizedDistanceScore("New york", "New city town");
        assertEquals(0.462, distance, 0.001);
    }

    @Test
    public void TestJaroMatcher() {
        String testQueryString = "Quebec";
        String testTarget = "Québec";
        double distance = StringMatcher.JaroWinklerDistance(testQueryString, testTarget);
        assertEquals(0.822, distance, 0.001);

        distance = StringMatcher.JaroWinklerDistance("rain", "shine");
        assertEquals(0.633, distance, 0.001);

    }

    @Test
    public void TestStingMatchersForIdenticalWords() {
        String testQueryString = "Quebec";
        String testTarget = "Quebec";
        double levenDistance = StringMatcher.levenshteinNormalizedDistanceScore(testQueryString, testTarget);
        double jaroDistance = StringMatcher.JaroWinklerDistance(testQueryString, testTarget);

        assertEquals(1, levenDistance, 0.001);
        assertEquals(1, jaroDistance, 0.001);
    }

    @Test
    public void TestStingMatchersDistanceReturnsCorrectDifference() {
        String testQueryString = "Que";
        String testTarget = "Quebec";
        double levenDistance = StringMatcher.levenshteinDistance(testQueryString, testTarget);

        assertEquals(3, levenDistance, 0.001);

        testQueryString = "Que";
        testTarget = "QuebecTEST";
        levenDistance = StringMatcher.levenshteinDistance(testQueryString, testTarget);
        assertEquals(7, levenDistance, 0.001);


        testQueryString = "new york city";
        testTarget = "New yourCiTY";
        levenDistance = StringMatcher.levenshteinDistance(testQueryString, testTarget);
        assertEquals(3, levenDistance, 0.001);

        testQueryString = "Gumbo            ";
        testTarget = "Gambol";
        levenDistance = StringMatcher.levenshteinDistance(testQueryString, testTarget);
        assertEquals(2, levenDistance, 0.001);

    }


    @Test
    public void TestStingMatchersForCompleteMismatchTerms() {
        String testQueryString = "Quebec";
        String testTarget = "no at all";
        double levenDistance = StringMatcher.levenshteinNormalizedDistanceScore(testQueryString, testTarget);
        double jaroDistance = StringMatcher.JaroWinklerDistance(testQueryString, testTarget);

        assertEquals(0, levenDistance, 0.001);
        assertEquals(0, jaroDistance, 0.001);
    }

    @Test
    public void TestStingMatchersForEmptyStrings() {
        String testQueryString = "Quebec";
        String testTarget = "";
        double levenDistance = StringMatcher.levenshteinNormalizedDistanceScore(testQueryString, testTarget);
        double jaroDistance = StringMatcher.JaroWinklerDistance(testQueryString, testTarget);

        assertEquals(0, levenDistance, 0.001);
        assertEquals(0, jaroDistance, 0.001);

        testQueryString = "";
        testTarget = "";
        levenDistance = StringMatcher.levenshteinNormalizedDistanceScore(testQueryString, testTarget);
        jaroDistance = StringMatcher.JaroWinklerDistance(testQueryString, testTarget);

        assertEquals(1, levenDistance, 0.001);
        assertEquals(1, jaroDistance, 0.001);

    }

    @Test
    public void TestStingMatchersForNulls() {
        String testTarget = null;
        String testQuery = null;
        double levenDistance = StringMatcher.levenshteinNormalizedDistanceScore(testQuery, testTarget);
        double jaroDistance = StringMatcher.JaroWinklerDistance(testQuery, testTarget);

        assertEquals(0, levenDistance, 0.001);
        assertEquals(0, jaroDistance, 0.001);
    }

    @Test
    public void TestIsAlternativeName() {
        String altnames = "Erdri,ai er de li,ayrdray,ayrdry,eeodeuli,Ердри,إيردراي,ائرڈرائ,ایردری,艾尔德里,에어드리";

        assertTrue(isAlternativeName(altnames, "ایردری"));
        assertTrue(isAlternativeName(altnames, "艾尔德里"));
        assertTrue(isAlternativeName(altnames, "에어드리"));

        assertFalse(isAlternativeName(altnames, "ردری"));
        assertFalse(isAlternativeName(altnames, "에어드"));
    }


}
