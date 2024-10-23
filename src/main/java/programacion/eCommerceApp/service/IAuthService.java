package programacion.eCommerceApp.service;

import programacion.eCommerceApp.controller.request.NewLoginRequest;
import programacion.eCommerceApp.controller.request.NewRegisterRequest;
import programacion.eCommerceApp.controller.response.AuthResponse;

public interface IAuthService {

    AuthResponse login(NewLoginRequest newLoginRequest);

    AuthResponse register(NewRegisterRequest newRegisterRequest);

}
