package com.tienda.clientes.service;

import com.tienda.clientes.entity.Cliente;
import com.tienda.clientes.repository.ClienteRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClienteService {

    @Autowired
    private final ClienteRepository clienteRepository;

    // ** Listar Todos Los Clientes
    public ResponseEntity<Iterable<Cliente>> listarClientes(){
        Iterable<Cliente> cliente = clienteRepository.findAll();
        return ResponseEntity.ok(cliente);
    }

    // ** Listar Cliente Por Id
    public ResponseEntity<Cliente> buscarPorId(Long id){
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ** Crear Nuevo Cliente
    public ResponseEntity<Cliente> crearCliente(Cliente nuevoCliente){
        Cliente clienteGuardado = clienteRepository.save(nuevoCliente);
        return ResponseEntity.ok(clienteGuardado);
    }

    // ** Actualizar Cliente
    public ResponseEntity<Cliente> actualizarCliente(Long id,@Valid Cliente clienteActualizado){
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()) {
            Cliente clienteExistente = clienteOptional.get();
            clienteExistente.setNombre(clienteActualizado.getNombre());
            clienteExistente.setCorreo(clienteActualizado.getCorreo());
            clienteExistente.setTelefono(clienteActualizado.getTelefono());
            Cliente clienteGuardado = clienteRepository.save(clienteExistente);
            return ResponseEntity.ok(clienteGuardado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ** Eliminar Cliente Por Id
    public void eliminarCliente(Long id){
        clienteRepository.deleteById(id);
        ResponseEntity.noContent().build();
    }
}
