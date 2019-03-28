package helpers;

import com.city.autocomplete.application.helpers.CityNameBuilder;
import com.city.autocomplete.application.models.Geoname;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.city.autocomplete.application.Constants.*;

@RunWith(JUnit4.class)
public class CityNameBuilderTest {
    String[] testDataArray = new String[11];
    String defaultCityName = "TEST CITY";
    String defaultCountryName = "CA";

    @Before
    public void setup() {
        testDataArray[DATA_ID_INDEX] = "0";
        testDataArray[DATA_NAME_INDEX] = defaultCityName;
        testDataArray[DATA_COUNTRY_INDEX] = defaultCountryName;
        testDataArray[DATA_LATITUDE_INDEX] = "0";
        testDataArray[DATA_LONGITUDE_INDEX] = "0";
        testDataArray[DATA_ADMINCODE_INDEX] = "15";
    }

    @Test
    public void TestParseCorrectProvinces() {
        String generatedCityName = generateCityNameForAdminCode("01");
        Assert.assertEquals("TEST CITY, AB, Canada", generatedCityName);

        generatedCityName = generateCityNameForAdminCode("02");
        Assert.assertEquals("TEST CITY, BC, Canada", generatedCityName);

        generatedCityName = generateCityNameForAdminCode("03");
        Assert.assertEquals("TEST CITY, MB, Canada", generatedCityName);

        generatedCityName = generateCityNameForAdminCode("04");
        Assert.assertEquals("TEST CITY, NB, Canada", generatedCityName);

        generatedCityName = generateCityNameForAdminCode("05");
        Assert.assertEquals("TEST CITY, NL, Canada", generatedCityName);

        generatedCityName = generateCityNameForAdminCode("07");
        Assert.assertEquals("TEST CITY, NS, Canada", generatedCityName);

        generatedCityName = generateCityNameForAdminCode("08");
        Assert.assertEquals("TEST CITY, ON, Canada", generatedCityName);

        generatedCityName = generateCityNameForAdminCode("09");
        Assert.assertEquals("TEST CITY, PE, Canada", generatedCityName);

        generatedCityName = generateCityNameForAdminCode("10");
        Assert.assertEquals("TEST CITY, QC, Canada", generatedCityName);

        generatedCityName = generateCityNameForAdminCode("11");
        Assert.assertEquals("TEST CITY, SK, Canada", generatedCityName);

        generatedCityName = generateCityNameForAdminCode("12");
        Assert.assertEquals("TEST CITY, YT, Canada", generatedCityName);

        generatedCityName = generateCityNameForAdminCode("13");
        Assert.assertEquals("TEST CITY, NT, Canada", generatedCityName);

        generatedCityName = generateCityNameForAdminCode("14");
        Assert.assertEquals("TEST CITY, NU, Canada", generatedCityName);
    }

    @Test
    public void TestCanadaProvinceNamesWithUnknownAdminCodes() {
        String generatedCityName = generateCityNameForAdminCode("155");
        Assert.assertEquals("TEST CITY, 155, Canada", generatedCityName);
    }

    @Test
    public void TestCountryCodeNames() {
        testDataArray[DATA_COUNTRY_INDEX] = "US";
        String generatedCityName = generateCityNameForAdminCode("155");
        Assert.assertEquals("TEST CITY, 155, USA", generatedCityName);
    }

    @Test
    public void TestWithNullAndValidStrings() {
        testDataArray[DATA_COUNTRY_INDEX] = "Invalid";
        testDataArray[DATA_COUNTRY_INDEX] = "TEST";

        String generatedCityName = generateCityNameForAdminCode("155");
        Assert.assertEquals("TEST CITY, 155, Unknown", generatedCityName);
    }


    private String generateCityNameForAdminCode(String cityCode) {
        testDataArray[DATA_ADMINCODE_INDEX] = cityCode;
        Geoname geoname = Geoname.createGeonameFromArray(testDataArray);
        return CityNameBuilder.exec(geoname);
    }
}
