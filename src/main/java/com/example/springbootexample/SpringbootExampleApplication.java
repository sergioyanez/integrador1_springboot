package com.example.springbootexample;

import com.example.springbootexample.utils.CargaDeDatos;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class SpringbootExampleApplication {

    @Autowired
    CargaDeDatos cargaDeDatos;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootExampleApplication.class, args);
    }

    @PostConstruct
    public void init() throws IOException {
        cargaDeDatos.cargarDatosDesdeCSV();
    }
}
