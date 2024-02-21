package com.tienda.proveedores.controller;

import com.tienda.proveedores.dto.ProveedoresDTO;
import com.tienda.proveedores.entity.Proveedores;
import com.tienda.proveedores.service.ProveedorService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/proveedores")
@CrossOrigin
@AllArgsConstructor
public class ProveedorController {

    @Autowired
    private final ProveedorService proveedorService;

    @Autowired
    private final ModelMapper modelMapper;

    // ** Listar Todos Los Proveedores
    @GetMapping
    public ResponseEntity<Iterable<ProveedoresDTO>> listarProveedores(){
        Iterable<Proveedores> proveedores = proveedorService.listarProveedores().getBody();
        assert proveedores != null;
        return ResponseEntity.ok(mapToDTOs(proveedores));
    }

    // ** Listar Proveedor Por Id
    @GetMapping("/{id}")
    public ResponseEntity<ProveedoresDTO> buscarPorId(@PathVariable(value = "id") Long id) {
        Proveedores proveedores = proveedorService.buscarPorId(id).getBody();
        return ResponseEntity.ok(mapToDTO(proveedores));
    }

    // ** Crear Nuevo Proveedor
    @PostMapping("/nuevoProveedor")
    public ResponseEntity<ProveedoresDTO> crearProveedor(@RequestBody ProveedoresDTO proveedoresDTO){
        Proveedores proveedores = mapToEntity(proveedoresDTO);
        Proveedores proveedorGuardado = proveedorService.crearProveedor(proveedores).getBody();
        return new ResponseEntity<>(mapToDTO(proveedorGuardado), HttpStatus.CREATED);
    }

    // ** Actualizar Proveedor
    @PutMapping("/{id}")
    public ResponseEntity<ProveedoresDTO> actualizarProveedor(@PathVariable(value = "id") Long id, @RequestBody ProveedoresDTO proveedoresDTO){
        if (proveedoresDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        Proveedores proveedores = mapToEntity(proveedoresDTO);
        if (proveedores == null) {
            return ResponseEntity.badRequest().build();
        }
        Proveedores proveedorActualizado = proveedorService.actualizarProveedor(id, proveedores).getBody();
        if (proveedorActualizado == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ProveedoresDTO proveedorActualizadoDTO = mapToDTO(proveedorActualizado);
        return ResponseEntity.ok(proveedorActualizadoDTO);
    }

    // ** Eliminar Proveedor Por Id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedor(@PathVariable(value = "id") Long id){
        proveedorService.eliminarProveedor(id);
        return ResponseEntity.noContent().build();
    }

    // ** ----------------------- MAPEO DE ENTIDAD A DTO Y VICEVERSA ------------------------------------------

    // ** Método privado para mapear una entidad Proveedor a un DTO ProveedorDTO
    private ProveedoresDTO mapToDTO(Proveedores proveedores) {
        return modelMapper.map(proveedores, ProveedoresDTO.class);
    }

    // ** Método privado para mapear una lista de entidades Proveedores a una lista de DTOs ProveedoresDTO
    private List<ProveedoresDTO> mapToDTOs(Iterable<Proveedores> proveedores) {
        return StreamSupport.stream(proveedores.spliterator(), false)
                .map(proveedor -> modelMapper.map(proveedor, ProveedoresDTO.class))
                .collect(Collectors.toList());
    }

    // ** Método privado para mapear un DTO ProveedoresDTO a una entidad Proveedores
    private Proveedores mapToEntity(ProveedoresDTO proveedoresDTO) {
        return modelMapper.map(proveedoresDTO, Proveedores.class);
    }
}
