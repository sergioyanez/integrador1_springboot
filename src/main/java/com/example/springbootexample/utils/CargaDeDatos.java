package com.example.springbootexample.utils;

import com.example.springbootexample.domain.*;
import com.example.springbootexample.repository.ClienteRepository;
import com.example.springbootexample.repository.FacturaProductoRepository;
import com.example.springbootexample.repository.FacturaRepository;
import com.example.springbootexample.repository.ProductoRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Component
public class CargaDeDatos {

    private final ClienteRepository clienteRepository;
    private final FacturaRepository facturaRepository;
    private final ProductoRepository productoRepository;
    private final FacturaProductoRepository facturaProductoRepository;

    @Autowired
    public CargaDeDatos(ClienteRepository clienteRepository,FacturaRepository facturaRepository,ProductoRepository productoRepository, FacturaProductoRepository facturaProductoRepository) {

        this.clienteRepository = clienteRepository;
        this.facturaRepository = facturaRepository;
        this.productoRepository = productoRepository;
        this.facturaProductoRepository = facturaProductoRepository;
    }

    public void cargarDatosDesdeCSV() throws IOException {
        File clientesCSV = ResourceUtils.getFile("src/main/java/com/example/springbootexample/csv/clientes-con-dni.csv");
        File productosCSV = ResourceUtils.getFile("src/main/java/com/example/springbootexample/csv/productos-con-rubro.csv");
        File facturasCSV = ResourceUtils.getFile("src/main/java/com/example/springbootexample/csv/facturas.csv");
        File facturasProductosCSV = ResourceUtils.getFile("src/main/java/com/example/springbootexample/csv/facturas-productos.csv");

        try (FileReader reader = new FileReader(clientesCSV);
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

            for (CSVRecord csvRecord : csvParser) {
                Cliente cliente = new Cliente();
                cliente.setNombre(csvRecord.get("nombre"));
                cliente.setEmail(csvRecord.get("email"));
                cliente.setDni(Integer.parseInt(csvRecord.get("dni")));
                clienteRepository.save(cliente); // Guarda el cliente en la base de datos
            }
        }

        try (FileReader reader = new FileReader(productosCSV);
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

            for (CSVRecord csvRecord : csvParser) {
                Producto producto = new Producto();
                producto.setNombre(csvRecord.get("nombre"));
                producto.setValor(Float.parseFloat(csvRecord.get("valor")));
                producto.setRubro(csvRecord.get("rubro"));
                productoRepository.save(producto); // Guarda el producto en la base de datos
            }
        }
        try (FileReader reader = new FileReader(facturasCSV);
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

            for (CSVRecord csvRecord : csvParser) {
                if (csvRecord.size() == 2) { // Asumiendo que hay dos columnas: id y cliente_id
                    Long idFactura = Long.valueOf(csvRecord.get("idFactura"));
                    Long idCliente = Long.valueOf(csvRecord.get("idCliente"));

                    // Crea una instancia de Factura
                    Factura factura = new Factura();
                    factura.setId(idFactura);

                    // Crea una instancia de Cliente y configura el ID del cliente
                    Cliente cliente = new Cliente();
                    cliente.setId(idCliente);

                    // Establece la relación entre Factura y Cliente
                    factura.setCliente(cliente);
                    facturaRepository.save(factura); // Guarda el producto en la base de datos
                }
            }
        }
        try (FileReader reader = new FileReader(facturasProductosCSV);
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

            for (CSVRecord csvRecord : csvParser) {
                if (csvRecord.size() == 3) { // Asumiendo que hay tres columnas: id_factura, id_producto, cantidad
                    Long idFactura = Long.valueOf(csvRecord.get("idFactura"));
                    Long idProducto = Long.valueOf(csvRecord.get("idProducto"));
                    Integer cantidad = Integer.valueOf(csvRecord.get("cantidad"));

                    // Crea una instancia de FacturaProductoPK
                    FacturaProductoPK facturaProductoPK = new FacturaProductoPK();
                    facturaProductoPK.setIdFactura(idFactura);
                    facturaProductoPK.setIdProducto(idProducto);

                    // Crea una instancia de FacturaProducto y configura sus relaciones
                    FacturaProducto facturaProducto = new FacturaProducto();
                    facturaProducto.setId(facturaProductoPK);
                    facturaProducto.setCantidad(cantidad);

                    // Agrega facturaProducto a la lista facturaProductos de Factura
                    Factura factura = new Factura();
                    factura.setId(idFactura);
                    facturaProducto.setFactura(factura);

                    // Configura la relación con Producto si es necesario
                     Producto producto = new Producto();
                     producto.setId(idProducto);
                     facturaProducto.setProducto(producto);

                    facturaProductoRepository.save(facturaProducto); // Guarda el producto en la base de datos

                }

            }
        }
    }

}

