package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotBlank;

public record NewLoginRequest(

    @NotBlank
    String nombre,

    @NotBlank
    String contrasenia

) { }
