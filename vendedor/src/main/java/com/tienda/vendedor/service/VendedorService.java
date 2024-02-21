package com.tienda.vendedor.service;

import com.tienda.vendedor.entity.Vendedor;
import com.tienda.vendedor.repository.VendedorRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VendedorService {

    @Autowired
    private final VendedorRepository vendedorRepository;

    // ** Listar Todos Los Vendedores
    public ResponseEntity<Iterable<Vendedor>> listarVendedor(){
        Iterable<Vendedor> vendedor = vendedorRepository.findAll();
        return ResponseEntity.ok(vendedor);
    }

    // ** Listar Vendedor Por Id
    public ResponseEntity<Vendedor> buscarPorId (Long id){
        Optional<Vendedor> vendedor = vendedorRepository.findById(id);
        return vendedor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ** Crear nuevo Vendedor
    public ResponseEntity<Vendedor> crearVendedor(Vendedor vendedor){
        Vendedor vendedorGuardado = vendedorRepository.save(vendedor);
        return ResponseEntity.ok(vendedorGuardado);
    }

    // ** Actualizar Vendedor
    public ResponseEntity<Vendedor> actualizarVendedor(Long id, @Valid Vendedor vendedor) {
        Optional<Vendedor> vendedorOptional = vendedorRepository.findById(id);
        if (vendedorOptional.isPresent()) {
            Vendedor vendedorExistente = vendedorOptional.get();
            vendedorExistente.setNombre(vendedor.getNombre());
            vendedorExistente.setApellido(vendedor.getApellido());
            vendedorExistente.setCorreo(vendedor.getCorreo());
            vendedorExistente.setTelefono(vendedor.getTelefono());
            vendedorExistente.setDireccion(vendedor.getDireccion());
            vendedorExistente.setCargo(vendedor.getCargo());
            vendedorExistente.setFechaIngreso(vendedor.getFechaIngreso());
            vendedorExistente.setFechaSalida(vendedor.getFechaSalida());
            vendedorExistente.setComision(vendedor.getComision());
            Vendedor vendedorActualizado = vendedorRepository.save(vendedorExistente);
            return ResponseEntity.ok(vendedorActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ** Eliminar Vendedor Por Id
    public void eliminarVendedor(Long id) {
        vendedorRepository.deleteById(id);
        ResponseEntity.noContent().build();
    }
}
