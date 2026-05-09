package com.example.springbootexample.service.dto.producto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties( ignoreUnknown = true )
public class ProductoRequestDTO {

    @NotNull( message = "El nombre es un campo obligatorio.")
    @NotEmpty( message = "El nombre es un campo obligatorio.")
    private String nombre;
    private float valor;
    private String rubro;

}
