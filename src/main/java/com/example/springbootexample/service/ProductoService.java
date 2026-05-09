package com.example.springbootexample.service;

import com.example.springbootexample.domain.Producto;
import com.example.springbootexample.repository.ProductoRepository;
import com.example.springbootexample.service.dto.cliente.request.ClienteRequestDTO;
import com.example.springbootexample.service.dto.cliente.response.ClienteResponseDTO;
import com.example.springbootexample.service.dto.producto.request.ProductoRequestDTO;
import com.example.springbootexample.service.dto.producto.response.ProductoResponseDTO;
import com.example.springbootexample.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Transactional
    public ProductoResponseDTO save(ProductoRequestDTO request ){
        final var producto = new Producto( request );
        final var result = this.productoRepository.save( producto );
        return new ProductoResponseDTO( result.getId(), result.getNombre(), result.getValor(),result.getRubro() );
    }

    @Transactional( readOnly = true )
    public List<ProductoResponseDTO> findAll(){
        return this.productoRepository.findAll().stream().map( ProductoResponseDTO::new ).toList();
    }

    @Transactional( readOnly = true )
    public Producto obtenerProductoTop() {
        return productoRepository.productTop();
    }

    public ProductoResponseDTO findById(Long id) {
        return this.productoRepository.findById( id )
                .map(ProductoResponseDTO::new)
                .orElseThrow( () -> new NotFoundException("Producto", id ) );
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> applyDiscountsBasedOnDay() {

        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        double discountPercentage;

        // Determina el porcentaje de descuento en función del día de la semana
        if (dayOfWeek == DayOfWeek.MONDAY) {
            discountPercentage = 0.35; // 30% de descuento para almacen
        } else if (dayOfWeek == DayOfWeek.TUESDAY) {
            discountPercentage = 0.30; // 30% de descuento para ferretería
        } else if (dayOfWeek == DayOfWeek.SATURDAY) {
            discountPercentage = 0.25; // 25% de descuento para electrodomésticos
        } else if (dayOfWeek == DayOfWeek.WEDNESDAY) {
            discountPercentage = 0.50; // 50% de descuento para ropa
         }else {
            discountPercentage = 0.0;
        }
        // Aplica el descuento a los productos correspondientes
        List<Producto> productosConDescuento = this.productoRepository.findAll().stream()
                .filter(producto -> {
                    String tipo = producto.getRubro();
                    return (dayOfWeek == DayOfWeek.MONDAY && "almacen".equals(tipo))
                            || (dayOfWeek == DayOfWeek.TUESDAY && "ferreteria".equals(tipo))
                            || (dayOfWeek == DayOfWeek.WEDNESDAY && "ropa".equals(tipo))
                            || (dayOfWeek == DayOfWeek.SATURDAY && "electrodomesticos".equals(tipo));
                })
                .map(producto -> {
                    // Aplica el descuento al precio del producto
                    double precio = producto.getValor();
                    double precioConDescuento = precio - (precio * discountPercentage);
                    producto.setValor(precioConDescuento);
                    return producto;
                })
                .collect(Collectors.toList());

        // Convierte los productos con descuento en DTO y devuelve la lista
        return productosConDescuento.stream()
                .map(ProductoResponseDTO::new)
                .collect(Collectors.toList());
    }
}

