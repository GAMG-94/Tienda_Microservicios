package com.tienda.proveedores.repository;

import com.tienda.proveedores.entity.Proveedores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedores, Long> {
}
