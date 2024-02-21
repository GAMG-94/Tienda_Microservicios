package com.tienda.clientes.controller;

import com.tienda.clientes.dto.ClienteDTO;
import com.tienda.clientes.entity.Cliente;
import com.tienda.clientes.service.ClienteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/cliente")
@CrossOrigin
@AllArgsConstructor
public class ClienteController {

    @Autowired
    private final ClienteService clienteService;

    @Autowired
    private final ModelMapper modelMapper;

    // ** Listar Todos Los Clientes
    @GetMapping
    public ResponseEntity<Iterable<ClienteDTO>> listarClientes(){
        Iterable<Cliente> clientes = clienteService.listarClientes().getBody();
        assert clientes != null;
        return ResponseEntity.ok(mapToDTOs(clientes));
    }

    // ** Listar Cliente Por Id
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable(value = "id") Long id){
        Cliente cliente = clienteService.buscarPorId(id).getBody();
        return ResponseEntity.ok(mapToDTO(cliente));
    }

    // ** Crear Nuevo Cliente
    @PostMapping("/nuevoCliente")
    @Validated
    public ResponseEntity<ClienteDTO> crearCliente(@RequestBody @Valid ClienteDTO clienteDTO){
        Cliente cliente = mapToEntity(clienteDTO);
        Cliente clienteGuardado = clienteService.crearCliente(cliente).getBody();
        return new ResponseEntity<>(mapToDTO(clienteGuardado), HttpStatus.CREATED);
    }

    // ** Actualizar Cliente
    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<ClienteDTO> actualizarCliente(@PathVariable(value = "id") @Valid Long id, @RequestBody ClienteDTO clienteDTO){
        Cliente cliente = mapToEntity(clienteDTO);
        Cliente clienteActualizado = clienteService.actualizarCliente(id, cliente).getBody();
        return ResponseEntity.ok(mapToDTO(clienteActualizado));
    }

    // ** Eliminar Cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id){
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

    // ** ----------------------- MAPEO DE ENTIDAD A DTO Y VICEVERSA ------------------------------------------

    // ** Método privado para mapear una entidad Cliente a un DTO ClienteDTO
    private ClienteDTO mapToDTO(Cliente cliente) {
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    // ** Método privado para mapear una lista de entidades Cliente a una lista de DTOs ClienteDTO
    private List<ClienteDTO> mapToDTOs(Iterable<Cliente> clientes) {
        return StreamSupport.stream(clientes.spliterator(), false)
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }

    // ** Método privado para mapear un DTO ClienteDTO a una entidad Cliente
    private Cliente mapToEntity(ClienteDTO clienteDTO) {
        return modelMapper.map(clienteDTO, Cliente.class);
    }
}
