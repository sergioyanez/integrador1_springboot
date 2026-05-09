package com.example.springbootexample.repository;

import com.example.springbootexample.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Long> {
    @Query("SELECT p " +
            "FROM Producto p " +
            "JOIN FacturaProducto fp ON p.id = fp.producto.id " +
            "GROUP BY fp.producto.id " +
            "ORDER BY SUM(p.valor * fp.cantidad) DESC " +
            "LIMIT 1")
    Producto productTop();
}
