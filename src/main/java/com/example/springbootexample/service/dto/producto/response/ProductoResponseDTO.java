package com.example.springbootexample.service.dto.producto.response;

import com.example.springbootexample.domain.Producto;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProductoResponseDTO {

    private final Long id;
    private final String nombre;
    private final double valor;
    private final String rubro;


    public ProductoResponseDTO( Producto p ) {
        this.id = p.getId();
        this.nombre = p.getNombre();
        this.valor = p.getValor();
        this.rubro = p.getRubro();
    }
}
