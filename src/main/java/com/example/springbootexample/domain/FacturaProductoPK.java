package com.example.springbootexample.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode
public class FacturaProductoPK implements Serializable {

    @Column( name = "id_factura")
    private Long idFactura;
    @Column( name = "id_producto")
    private Long idProducto;

}
