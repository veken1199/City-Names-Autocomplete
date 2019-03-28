package controllers;

import com.city.autocomplete.application.Application;
import com.city.autocomplete.application.models.ApiResponse;
import com.city.autocomplete.application.models.RequestInput;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class BaseContorllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    protected MockMvc mvc;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity<RequestInput> entity;
    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Before
    public void setup() {
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        entity = new HttpEntity<RequestInput>(null, headers);

        // We need to mock the data

    }


    protected ResponseEntity<ApiResponse> submitRequest(String path) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                createURLWithPort(path), HttpMethod.GET, entity, ApiResponse.class);
        return response;
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
