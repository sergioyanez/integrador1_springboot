package com.example.springbootexample.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Factura {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private Cliente cliente;

    @OneToMany( fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "factura")
    private Set<FacturaProducto> facturaProductos;
}
