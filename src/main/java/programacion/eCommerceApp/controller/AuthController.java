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
public class AuthController {

    @Autowired
    private IAuthService service;

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid NewLoginRequest newLoginRequest) {
        return ResponseEntity.ok(service.login(newLoginRequest));
    }

    @PostMapping("/auth/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid NewRegisterRequest newRegisterRequest) {
        return ResponseEntity.ok(service.register(newRegisterRequest));
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<AuthResponse> actualizar(@RequestBody @Valid NewRegisterRequest newRegisterRequest, @PathVariable Integer id) {
        return ResponseEntity.ok(service.actualizar(newRegisterRequest, id));
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<AuthResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
}
