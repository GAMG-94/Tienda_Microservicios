package com.tienda.proveedores.service;

import com.tienda.proveedores.entity.Proveedores;
import com.tienda.proveedores.repository.ProveedorRepository;
import feign.Response;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProveedorService {

    @Autowired
    private final ProveedorRepository proveedorRepository;

    @Autowired
    private final ModelMapper modelMapper;

    // ** Listar Todos Los Proveedores
    public ResponseEntity<Iterable<Proveedores>> listarProveedores(){
        Iterable<Proveedores> proveedores = proveedorRepository.findAll();
        return ResponseEntity.ok(proveedores);
    }

    // ** Listar Proveedores Por Id
    public ResponseEntity<Proveedores> buscarPorId(Long id){
        Optional<Proveedores> proveedores = proveedorRepository.findById(id);
        return proveedores.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    // ** Crear Nuevo Proveedor
    public ResponseEntity<Proveedores> crearProveedor(Proveedores nuevoProveedor) {
        Proveedores proveedorGuardado = proveedorRepository.save(nuevoProveedor);
        return ResponseEntity.ok(proveedorGuardado);
    }

    // ** Actualizar Proveedor
    public ResponseEntity<Proveedores> actualizarProveedor(Long id, @Valid Proveedores proveedor){
        Optional<Proveedores> proveedoresOptional = proveedorRepository.findById(id);
        if (proveedoresOptional.isPresent()){
            Proveedores proveedorExistente = proveedoresOptional.get();
            proveedorExistente.setNombreEmpresa(proveedor.getNombreEmpresa());
            proveedorExistente.setNombreEmpleado(proveedor.getNombreEmpleado());
            proveedorExistente.setDireccion(proveedor.getDireccion());
            proveedorExistente.setCorreo(proveedor.getCorreo());
            proveedorExistente.setTelefono(proveedor.getTelefono());
            proveedorExistente.setSitioWeb(proveedor.getSitioWeb());
            proveedorExistente.setAtencionCliente(proveedor.getAtencionCliente());
            proveedorExistente.setRedesSociales(proveedor.getRedesSociales());
            proveedorExistente.setContactoVentas(proveedor.getContactoVentas());
            Proveedores proveedorActualizado = proveedorRepository.save(proveedorExistente);
            return ResponseEntity.ok(proveedorActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ** Eliminar Proveedor Por Id
    public void eliminarProveedor(Long id){
        proveedorRepository.deleteById(id);
        ResponseEntity.noContent().build();
    }
}
