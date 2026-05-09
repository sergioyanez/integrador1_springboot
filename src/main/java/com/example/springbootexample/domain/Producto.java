package com.example.springbootexample.domain;

import com.example.springbootexample.service.dto.cliente.request.ClienteRequestDTO;
import com.example.springbootexample.service.dto.producto.request.ProductoRequestDTO;
import com.example.springbootexample.service.dto.producto.response.ProductoResponseDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    private String nombre;
    private double valor;
    private String rubro;

    @OneToMany( fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "producto")
    private Set<FacturaProducto> facturaProductos;

    public Producto( ProductoRequestDTO request ) {
        this.nombre = request.getNombre();
        this.valor = request.getValor();
        this.rubro = request.getRubro();
    }

}
