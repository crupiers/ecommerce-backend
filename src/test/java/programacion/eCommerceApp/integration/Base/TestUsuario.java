package programacion.eCommerceApp.integration.Base;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import programacion.eCommerceApp.eCommerceApplication;

@Sql("/scripts/base/reset_db.sql")
@Sql("/scripts/usuarios/crear_usuario.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = eCommerceApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class TestUsuario extends BaseIntegrationTest {


    @Test
    public void testearUsuario() throws Exception {
        // Given
    System.out.println("TestUsuario");
}
}