package helpers;

import com.city.autocomplete.application.helpers.BaseHelpers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class BaseHelpersTest {

    public static final double DELTA = 0.001;
    @Test
    public void TestFormatter() {
        double formatted = BaseHelpers.formatDecimals(0.212333);
        assertEquals(formatted, 0.212, DELTA);

        formatted = BaseHelpers.formatDecimals(0.218887333);
        assertEquals(formatted, 0.219, DELTA);

        formatted = BaseHelpers.formatDecimals(0.218887333, 6);
        assertEquals(formatted, 0.218887, DELTA);
    }

    @Test
    public void TestFormatterWithInvalidDecimalPoints() {
        double formatted = BaseHelpers.formatDecimals(0.218887333, Integer.MAX_VALUE);
        assertEquals(formatted, 0, DELTA);

        formatted = BaseHelpers.formatDecimals(0.218887333, Integer.MIN_VALUE);
        assertEquals(formatted, 0, DELTA);
    }

    @Test
    public void TestMinNumberFromArray() {
        int min = BaseHelpers.findMinOfNumber(new int[]{6, 3, 2, -22, 23, 23423, 46, 234, 123, 43546, 21, 5});
        assertTrue(min == -22);

        min = BaseHelpers.findMinOfNumber(new int[]{});
        assertTrue(min == Integer.MAX_VALUE);
    }
}
