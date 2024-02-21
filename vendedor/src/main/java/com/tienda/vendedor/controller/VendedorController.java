package com.tienda.vendedor.controller;

import com.tienda.vendedor.dto.VendedorDTO;
import com.tienda.vendedor.entity.Vendedor;
import com.tienda.vendedor.service.VendedorService;
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
@RequestMapping("/vendedor")
@CrossOrigin
@AllArgsConstructor
public class VendedorController {

    @Autowired
    private final VendedorService vendedorService;

    @Autowired
    private final ModelMapper modelMapper;

    // ** Listar Todos Los Vendedores
    @GetMapping
    public ResponseEntity<Iterable<VendedorDTO>> listarVendedores(){
        Iterable<Vendedor> vendedores = vendedorService.listarVendedor().getBody();
        return ResponseEntity.ok(mapToDTOs(vendedores));
    }

    // ** Listar Vendedor Por Id
    @GetMapping("/{id}")
    public ResponseEntity<VendedorDTO> buscarPorId(@PathVariable(value = "id") Long id){
        Vendedor vendedor = vendedorService.buscarPorId(id).getBody();
        return ResponseEntity.ok(mapToDTO(vendedor));
    }

    // ** Crear nuevo Vendedor
    @PostMapping("/nuevoVendedor")
    public ResponseEntity<VendedorDTO> crearVendedor(@RequestBody VendedorDTO vendedorDTO){
        Vendedor vendedor = mapToEntity(vendedorDTO);
        Vendedor vendedorGuardado = vendedorService.crearVendedor(vendedor).getBody();
        return new ResponseEntity<>(mapToDTO(vendedorGuardado), HttpStatus.CREATED);
    }

    // ** Actualizar Vendedor
    @PutMapping("/{id}")
    public ResponseEntity<VendedorDTO> actualizarVendedor(@PathVariable(value = "id") Long id,@RequestBody VendedorDTO vendedorDTO) {
        if (vendedorDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        Vendedor vendedor = mapToEntity(vendedorDTO);
        if (vendedor == null) {
            return ResponseEntity.badRequest().build();
        }
        Vendedor vendedorActualizado = vendedorService.actualizarVendedor(id, vendedor).getBody();
        if (vendedorActualizado == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        VendedorDTO vendedorActualizadoDTO = mapToDTO(vendedorActualizado);
        return ResponseEntity.ok(vendedorActualizadoDTO);
    }

    // ** Eliminar Vendedor Por Id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVendedor(@PathVariable(value = "id") Long id) {
        vendedorService.eliminarVendedor(id);
        return ResponseEntity.noContent().build();
    }

    // ** ----------------------- MAPEO DE ENTIDAD A DTO Y VICEVERSA ------------------------------------------

    // ** Método privado para mapear una entidad Vendedor a un DTO VendedorDTO
    private VendedorDTO mapToDTO(Vendedor vendedor) {
        return modelMapper.map(vendedor, VendedorDTO.class);
    }

    // ** Método privado para mapear una lista de entidades Vendedores a una lista de DTOs VendedoresDTO
    private List<VendedorDTO> mapToDTOs(Iterable<Vendedor> vendedores) {
        return StreamSupport.stream(vendedores.spliterator(), false)
                .map(vendedor -> modelMapper.map(vendedor, VendedorDTO.class))
                .collect(Collectors.toList());
    }

    // ** Método privado para mapear un DTO VendedorDTO a una entidad Vendedor
    private Vendedor mapToEntity(VendedorDTO vendedorDTO) {
        return modelMapper.map(vendedorDTO, Vendedor.class);
    }
}
