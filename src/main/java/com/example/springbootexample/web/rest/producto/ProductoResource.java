package com.example.springbootexample.web.rest.producto;

import com.example.springbootexample.domain.Producto;
import com.example.springbootexample.service.ProductoService;
import com.example.springbootexample.service.dto.cliente.request.ClienteRequestDTO;
import com.example.springbootexample.service.dto.cliente.response.ClienteResponseDTO;
import com.example.springbootexample.service.dto.producto.response.ProductoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.util.List;

@RestController
@RequestMapping("api/productos")
@RequiredArgsConstructor
public class ProductoResource {

    private final ProductoService productoService;

    @GetMapping("")
    public List<ProductoResponseDTO> findAll(){
        return this.productoService.findAll();
    }


    @GetMapping("/{id}")
    public ProductoResponseDTO findById( @PathVariable Long id ){
        return this.productoService.findById( id );
    }


    @GetMapping("/top")
    public ResponseEntity<ProductoResponseDTO> obtenerProductoTop() {
        Producto producto = productoService.obtenerProductoTop();

        if (producto != null) {
            ProductoResponseDTO productoResponseDTO = new ProductoResponseDTO(producto);
            return ResponseEntity.ok(productoResponseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/descuentos")
    public List<ProductoResponseDTO> getProductosConDescuentoBasadoEnDia() {
        // Llama al método del servicio que aplica descuentos basados en el día de la semana
        List<ProductoResponseDTO> productosConDescuento = productoService.applyDiscountsBasedOnDay();
        return productosConDescuento;
    }
}
