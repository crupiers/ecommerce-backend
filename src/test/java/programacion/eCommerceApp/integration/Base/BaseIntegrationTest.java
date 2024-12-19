package programacion.eCommerceApp.integration.Base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import programacion.eCommerceApp.eCommerceApplication;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = eCommerceApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BaseIntegrationTest {
    @Autowired
    public MockMvc mockMvc;
    @Autowired
    public ObjectMapper objectMapper;

    public String toJson(Object request) throws JsonProcessingException {
        return objectMapper.writeValueAsString(request);
    }

    public void assertContains(String response, String propertyName, String value) {
        String valueToCheck = buildValueToCheck(propertyName, value);
        Assertions.assertTrue(response.contains(valueToCheck));
    }

    public void assertNotContains(String response, String propertyName, String value) {
        String valueToCheck = buildValueToCheck(propertyName, value);
        Assertions.assertFalse(response.contains(valueToCheck));
    }

    public String buildValueToCheck(String propertyName, String value) {
        return "\"" + propertyName + "\"" + ":" + "\"" + value + "\"";
    }
}