package helpers;

import com.city.autocomplete.application.helpers.CoordsCalculator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;


@RunWith(JUnit4.class)
public class CoordsDistanceCalculatorTest {

    @Test
    public void TestCoordDistanceCalculator() {
        double lat = 45.82318;
        double _long = -73.4294;

        double lat2 = 45.81681;
        double _long2 = -77.11616;

        double radius = CoordsCalculator.calculateHaversineDistanceScore(lat, _long, lat2, _long2);
        assertEquals(0, (long) radius, 0.001);
    }

    @Test
    public void TestCoordDistanceCalculatorWithMaxValues() {
        double lat, _long, lat2, _long2;
        lat = _long = lat2 = _long2 = Double.MAX_VALUE;

        double radius = CoordsCalculator.calculateHaversineDistanceScore(lat, _long, lat2, _long2);
        assertEquals(0, (long) radius, 0.001);
    }
}

