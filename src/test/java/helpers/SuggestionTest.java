package helpers;

import com.city.autocomplete.application.Constants;
import com.city.autocomplete.application.helpers.CityNameBuilder;
import com.city.autocomplete.application.models.Geoname;
import com.city.autocomplete.application.models.Suggestion;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.TestCase.assertTrue;

@RunWith(JUnit4.class)
public class SuggestionTest {

    @Test
    public void TestBuildSuggestion() {
        String id = "1";
        String name = "City";
        String ascii = "City";
        String alt_name = "City,city";
        String latitude = "1.0";
        String longitude = "1.0";
        String country = "CA";
        String stateCode = "01";

        String[] geonameData = new String[11];
        geonameData[Constants.DATA_ID_INDEX] = id;
        geonameData[Constants.DATA_NAME_INDEX] = name;
        geonameData[Constants.DATA_ASCII_INDEX] = ascii;
        geonameData[Constants.DATA_ALT_NAME_INDEX] = alt_name;
        geonameData[Constants.DATA_LATITUDE_INDEX] = latitude;
        geonameData[Constants.DATA_LONGITUDE_INDEX] = longitude;
        geonameData[Constants.DATA_COUNTRY_INDEX] = country;
        geonameData[Constants.DATA_ADMINCODE_INDEX] = stateCode;

        Geoname geoname = Geoname.createGeonameFromArray(geonameData);

        Suggestion suggestion = Suggestion.build(geoname);
        assertTrue(suggestion.latitude == geoname.getLatitude());
        assertTrue( suggestion.longitude == geoname.getLongitude());
        assertTrue( suggestion.name.equals(CityNameBuilder.exec(geoname)));
    }
}
