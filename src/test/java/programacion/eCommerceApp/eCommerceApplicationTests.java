package programacion.eCommerceApp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test") // Indica que se use application-test.yml
class eCommerceApplicationTests {

    @Test
    void contextLoads() {
    }

}
