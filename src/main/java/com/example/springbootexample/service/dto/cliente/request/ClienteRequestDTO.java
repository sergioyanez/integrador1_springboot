package com.example.springbootexample.service.dto.cliente.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties( ignoreUnknown = true )
public class ClienteRequestDTO {

    @NotNull( message = "El nombre es un campo obligatorio.")
    @NotEmpty( message = "El nombre es un campo obligatorio.")
    private String nombre;
    private String email;
    private int dni;

}
