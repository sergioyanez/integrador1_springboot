package com.example.springbootexample.domain;

import com.example.springbootexample.service.dto.cliente.request.ClienteRequestDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    private String nombre;
    private String email;
    private int dni;

    public Cliente( ClienteRequestDTO request ) {
        this.nombre = request.getNombre();
        this.email = request.getEmail();
    }

}
