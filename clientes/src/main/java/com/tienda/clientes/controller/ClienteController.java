package com.tienda.clientes.controller;

import com.tienda.clientes.dto.ClienteDTO;
import com.tienda.clientes.dto.ProductosDTO;
import com.tienda.clientes.dto.ProveedoresDTO;
import com.tienda.clientes.dto.VendedorDTO;
import com.tienda.clientes.entity.Cliente;
import com.tienda.clientes.interfaces.ProductosFeignClient;
import com.tienda.clientes.interfaces.ProveedoresFeignClient;
import com.tienda.clientes.interfaces.VendedorFeignClient;
import com.tienda.clientes.service.ClienteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

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
    private final ModelMapper modelMapper;
    private final ProductosFeignClient productosFeignClient;
    private final ProveedoresFeignClient proveedoresFeignClient;
    private final VendedorFeignClient vendedorFeignClient;
    private final String productosServiceUrl = "http://localhost:8083/productos";
    private final WebClient.Builder webClientBuilder;

    // ** Listar Todos Los Clientes
    @GetMapping
    @Transactional
    public ResponseEntity<Iterable<ClienteDTO>> listarClientes(){
        Iterable<Cliente> clientes = clienteService.listarClientes().getBody();
        assert clientes != null;
        return ResponseEntity.ok(mapToDTOs(clientes));
    }

    // ** Listar Cliente Por Id
    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable(value = "id") Long id){
        Cliente cliente = clienteService.buscarPorId(id).getBody();
        return ResponseEntity.ok(mapToDTO(cliente));
    }

    // ** Crear Nuevo Cliente
    @PostMapping("/nuevoCliente")
    @Transactional
    @Validated
    public ResponseEntity<ClienteDTO> crearCliente(@RequestBody @Valid ClienteDTO clienteDTO){
        Cliente cliente = mapToEntity(clienteDTO);
        Cliente clienteGuardado = clienteService.crearCliente(cliente).getBody();
        return new ResponseEntity<>(mapToDTO(clienteGuardado), HttpStatus.CREATED);
    }

    // ** Actualizar Cliente
    @PutMapping("/{id}")
    @Transactional
    @Validated
    public ResponseEntity<ClienteDTO> actualizarCliente(@PathVariable(value = "id") Long id, @Valid @RequestBody ClienteDTO clienteDTO){
        if (clienteDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        Cliente cliente = mapToEntity(clienteDTO);
        if (cliente == null) {
            return ResponseEntity.badRequest().build();
        }
        Cliente clienteActualizado = clienteService.actualizarCliente(id, cliente).getBody();
        if (clienteActualizado == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ClienteDTO clienteActualizadoDTO = mapToDTO(clienteActualizado);
        return ResponseEntity.ok(clienteActualizadoDTO);
    }

    // ** Eliminar Cliente
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id){
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

    // ** ----------------------- COMUNICANDO LOS OTROS MICROSERVICIOS ----------------------------------------

    // ** Comunicando con el microservicio productos
    @GetMapping("/cliente/{id}/productos")
    public ResponseEntity<ProductosDTO> obtenerProductosDelCliente(@PathVariable Long id) {
        WebClient webClient = webClientBuilder.baseUrl(productosServiceUrl).build();

        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .toEntity(ProductosDTO.class)
                .block(); // Obtener el resultado de manera síncrona
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
