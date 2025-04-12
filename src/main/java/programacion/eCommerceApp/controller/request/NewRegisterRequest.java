package programacion.eCommerceApp.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;


public record NewRegisterRequest(

        @NotNull
        @Size(min = 2, max = 32, message = "EL NOMBRE DEBE IR DE ENTRE 2 Y 32 CARACTERES")
        @Pattern(regexp = "^([a-z]+|[0-9]+)(-([a-z]|[0-9])+)*$", message = "EL NOMBRE DEBE ESTAR CONFORMADO POR PALABRAS DE SÓLO LETRAS MINÚSCULAS O DE SÓLO NÚMEROS, LAS PALABRAS DEBEN ESTAR SEPARADAS CON GUIÓN COMÚN (-)")
        String nombre,

        @NotNull
        @Size(min = 8, max = 64, message = "LA CONTRASEÑA DEBE IR DE ENTRE 8 Y 64 CARACTERES- REQUEST")
        @Pattern(regexp = "^((([^\\s]*[a-z]+[^\\s]*[A-Z]+[^\\s]*[0-9]+[^\\s]*)|([^\\s]*[a-z]+[^\\s]*[0-9]+[^\\s]*[A-Z]+[^\\s]*))|(([^\\s]*[A-Z]+[^\\s]*[a-z]+[^\\s]*[0-9]+[^\\s]*)|([^\\s]*[A-Z]+[^\\s]*[0-9]+[^\\s]*[a-z]+[^\\s]*))|(([^\\s]*[0-9]+[^\\s]*[a-z]+[^\\s]*[A-Z]+[^\\s]*)|([^\\s]*[0-9]+[^\\s]*[A-Z]+[^\\s]*[a-z]+[^\\s]*)))$", message = "LA CONTRASEÑA DEBE CONTENER AL MENOS UNA MINÚSCULA, UNA MAYÚSCULA Y UN NÚMERO, NO PUEDE CONTENER ESPACIOS")
        String contrasenia,

        @NotNull
        LocalDate fechaNacimiento
) { }
