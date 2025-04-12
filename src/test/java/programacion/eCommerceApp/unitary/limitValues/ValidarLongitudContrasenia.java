package programacion.eCommerceApp.unitary.limitValues;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validator;
import jakarta.validation.Validator;
import org.springframework.test.web.servlet.MockMvc;
import programacion.eCommerceApp.controller.request.NewRegisterRequest;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ValidarLongitudContrasenia {
    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void LongitudMinimaValida() { // 8 caracteres
        NewRegisterRequest request = new NewRegisterRequest("agustin", "Abc12345", LocalDate.of(2000, 1, 1));

        Set<ConstraintViolation<NewRegisterRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void LongitudMaximaValida() { //64 caracteres
        NewRegisterRequest request = new NewRegisterRequest("agustin", "Abc1234567111111111111111111111111111111111111111111111111111111", LocalDate.of(2000, 1, 1));

        Set<ConstraintViolation<NewRegisterRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void LongitudMinimaInvalida() { //7 caracteres
        NewRegisterRequest request = new NewRegisterRequest("agustin", "Abc1234",LocalDate.of(2000, 1, 1));

        Set<ConstraintViolation<NewRegisterRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void LonigudMaximaInvalida() { //65 caracteres
        NewRegisterRequest request = new NewRegisterRequest("agustin", "1Abc1234567111111111111111111111111111111111111111111111111111111",LocalDate.of(2000, 1, 1));

        Set<ConstraintViolation<NewRegisterRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
    }
}
