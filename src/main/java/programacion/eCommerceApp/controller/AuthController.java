package programacion.eCommerceApp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import programacion.eCommerceApp.service.IAuthService;
import programacion.eCommerceApp.controller.request.NewLoginRequest;
import programacion.eCommerceApp.controller.request.NewRegisterRequest;
import programacion.eCommerceApp.controller.response.AuthResponse;

@RestController
@RequestMapping("/ecommerce")
@CrossOrigin(value = " http://localhost:8080")
public class AuthController {

    @Autowired
    private IAuthService service;

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid final NewLoginRequest newLoginRequest) {
        return ResponseEntity.ok(service.login(newLoginRequest));
    }

    @PostMapping("/auth/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid final NewRegisterRequest newRegisterRequest) {
        return ResponseEntity.ok(service.register(newRegisterRequest));
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<AuthResponse> actualizar(@RequestBody @Valid final NewRegisterRequest newRegisterRequest, @PathVariable final Integer id) {
        return ResponseEntity.ok(service.actualizar(newRegisterRequest, id));
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable final Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok().build();
    }
}
