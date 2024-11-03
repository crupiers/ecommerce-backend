package programacion.eCommerceApp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import programacion.eCommerceApp.service.IAuthService;
import programacion.eCommerceApp.controller.request.NewLoginRequest;
import programacion.eCommerceApp.controller.request.NewRegisterRequest;
import programacion.eCommerceApp.controller.response.AuthResponse;

@RestController
@RequestMapping("/ecommerce")
@CrossOrigin(value=" http://localhost:8080")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid NewLoginRequest newLoginRequest) {
        return ResponseEntity.ok(authService.login(newLoginRequest));
    }

    @PostMapping("/auth/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid NewRegisterRequest newRegisterRequest) {
        return ResponseEntity.ok(authService.register(newRegisterRequest));
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<AuthResponse> actualizar(@RequestBody @Valid NewRegisterRequest newRegisterRequest, @PathVariable Integer id) {
        return ResponseEntity.ok(authService.actualizar(newRegisterRequest, id));
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        authService.eliminar(id);
        return ResponseEntity.ok().build();
    }
}
