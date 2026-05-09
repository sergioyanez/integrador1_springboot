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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class CargaDeDatos {

    private final ClienteRepository clienteRepository;
    private final FacturaRepository facturaRepository;
    private final ProductoRepository productoRepository;
    private final FacturaProductoRepository facturaProductoRepository;

    @Autowired
    public CargaDeDatos(ClienteRepository clienteRepository,
                        FacturaRepository facturaRepository,
                        ProductoRepository productoRepository,
                        FacturaProductoRepository facturaProductoRepository) {

        this.clienteRepository = clienteRepository;
        this.facturaRepository = facturaRepository;
        this.productoRepository = productoRepository;
        this.facturaProductoRepository = facturaProductoRepository;
    }

    public void cargarDatosDesdeCSV() throws IOException {

        InputStream clientesStream = getClass().getClassLoader()
                .getResourceAsStream("csv/clientes-con-dni.csv");

        InputStream productosStream = getClass().getClassLoader()
                .getResourceAsStream("csv/productos-con-rubro.csv");

        InputStream facturasStream = getClass().getClassLoader()
                .getResourceAsStream("csv/facturas.csv");

        InputStream facturasProductosStream = getClass().getClassLoader()
                .getResourceAsStream("csv/facturas-productos.csv");

        if (clientesStream == null ||
                productosStream == null ||
                facturasStream == null ||
                facturasProductosStream == null) {

            throw new RuntimeException("No se encontraron los archivos CSV");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientesStream));
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

            for (CSVRecord csvRecord : csvParser) {
                Cliente cliente = new Cliente();
                cliente.setNombre(csvRecord.get("nombre"));
                cliente.setEmail(csvRecord.get("email"));
                cliente.setDni(Integer.parseInt(csvRecord.get("dni")));

                clienteRepository.save(cliente);
            }
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(productosStream));
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

            for (CSVRecord csvRecord : csvParser) {
                Producto producto = new Producto();
                producto.setNombre(csvRecord.get("nombre"));
                producto.setValor(Float.parseFloat(csvRecord.get("valor")));
                producto.setRubro(csvRecord.get("rubro"));

                productoRepository.save(producto);
            }
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(facturasStream));
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

            for (CSVRecord csvRecord : csvParser) {

                Long idFactura = Long.valueOf(csvRecord.get("idFactura"));
                Long idCliente = Long.valueOf(csvRecord.get("idCliente"));

                Factura factura = new Factura();
                factura.setId(idFactura);

                Cliente cliente = new Cliente();
                cliente.setId(idCliente);

                factura.setCliente(cliente);

                facturaRepository.save(factura);
            }
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(facturasProductosStream));
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

            for (CSVRecord csvRecord : csvParser) {

                Long idFactura = Long.valueOf(csvRecord.get("idFactura"));
                Long idProducto = Long.valueOf(csvRecord.get("idProducto"));
                Integer cantidad = Integer.valueOf(csvRecord.get("cantidad"));

                FacturaProductoPK facturaProductoPK = new FacturaProductoPK();
                facturaProductoPK.setIdFactura(idFactura);
                facturaProductoPK.setIdProducto(idProducto);

                FacturaProducto facturaProducto = new FacturaProducto();
                facturaProducto.setId(facturaProductoPK);
                facturaProducto.setCantidad(cantidad);

                Factura factura = new Factura();
                factura.setId(idFactura);
                facturaProducto.setFactura(factura);

                Producto producto = new Producto();
                producto.setId(idProducto);
                facturaProducto.setProducto(producto);

                facturaProductoRepository.save(facturaProducto);
            }
        }
    }
}