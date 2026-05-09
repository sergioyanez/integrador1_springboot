package com.example.springbootexample.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
public class FacturaProducto {

    @EmbeddedId
    private FacturaProductoPK id;

    @ManyToOne( fetch = FetchType.LAZY )
    @MapsId("idFactura")
    @JoinColumn( name = "id_factura")
    private Factura factura;

    @ManyToOne( fetch = FetchType.LAZY )
    @MapsId("idProducto")
    @JoinColumn ( name = "id_producto")
    private Producto producto;

    private Integer cantidad;

    public FacturaProducto() {
    }
}
