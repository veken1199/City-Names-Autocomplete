package helpers;

import com.city.autocomplete.application.Constants;
import com.city.autocomplete.application.helpers.CoordsCalculator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;


@RunWith(JUnit4.class)
public class CoordsDistanceCalculatorTest {

    @Test
    public void TestCoordDistanceCalculatorScoreOutOfRange() {
        // 60.84154 KM
        double score = CoordsCalculator.calculateHaversineDistanceScore(47.0788206, -122.3271205, 47.6788206, -122.5271205);
        assertEquals(0, score, 0.001);
    }

    @Test
    public void TestCoordDistanceCalculatorScoreWithin60KM() {
        // 57 KM
        double score = CoordsCalculator.calculateHaversineDistanceScore(47.1788206, -122.3271205, 47.6788206, -122.5271205);
        assertEquals(Constants.WITHIN_60_KM_SCORE, score, 0.001);
    }
  @Test
    public void TestCoordDistanceCalculatorScoreWithin30KM() {
        // 29.6 KM
      double score = CoordsCalculator.calculateHaversineDistanceScore(47.4488206, -122.3271205, 47.6788206, -122.5271205);
      assertEquals(Constants.WITHIN_30_KM_SCORE, score, 0.001);
    }


    @Test
    public void TestCoordDistanceCalculatorScoreWithMaxValues() {
        double lat, _long, lat2, _long2;
        lat = _long = lat2 = _long2 = Double.MAX_VALUE;

        double score = CoordsCalculator.calculateHaversineDistanceScore(lat, _long, lat2, _long2);
        assertEquals(0, score, 0.001);
    }

    @Test
    public void TestHaversineDistance() {
        double radius = CoordsCalculator.calculateHaversineDistance(47.1488206, -122.3271205, 47.6788206, -122.5271205);
        assertEquals(60.84154, radius, 0.001);

        radius = CoordsCalculator.calculateHaversineDistance(36.12, -86.67, 33.94, -118.40);
        assertEquals(2887.2599, radius, 0.001);
    }
}

