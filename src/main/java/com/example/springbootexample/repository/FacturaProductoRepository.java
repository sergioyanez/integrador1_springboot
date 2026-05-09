package com.example.springbootexample.repository;

import com.example.springbootexample.domain.FacturaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaProductoRepository extends JpaRepository<FacturaProducto, Long> {
}
