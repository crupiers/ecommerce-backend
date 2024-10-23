package programacion.eCommerceApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import programacion.eCommerceApp.service.IAuthService;
import programacion.eCommerceApp.controller.request.NewLoginRequest;
import programacion.eCommerceApp.controller.request.NewRegisterRequest;
import programacion.eCommerceApp.controller.response.AuthResponse;

@RestController
@RequestMapping("/eCommerce/auth")
@CrossOrigin(value=" http://localhost:8080")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody NewLoginRequest newLoginRequest) {
        return ResponseEntity.ok(authService.login(newLoginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody NewRegisterRequest newRegisterRequest) {
        return ResponseEntity.ok(authService.register(newRegisterRequest));
    }
}
