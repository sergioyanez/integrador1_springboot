package com.example.springbootexample.service.dto.cliente.response;

import com.example.springbootexample.domain.Cliente;
import com.example.springbootexample.domain.Producto;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ClienteResponseDTO {

    private final Long id;
    private final String nombre;
    private final String email;
    private final int dni;

    public ClienteResponseDTO( Cliente c ) {
        this.id = c.getId();
        this.nombre = c.getNombre();
        this.email = c.getEmail();
        this.dni = c.getDni();
    }

}
