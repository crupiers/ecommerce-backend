package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotBlank;

public record NewRegisterRequest(

    @NotBlank
    String nombre,

    @NotBlank
    String contrasenia

) { }
