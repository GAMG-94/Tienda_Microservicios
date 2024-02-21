package com.tienda.productos.controller;

import com.tienda.productos.dto.ProductosDTO;
import com.tienda.productos.entity.Productos;
import com.tienda.productos.service.ProductosService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
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
@RequestMapping("/productos")
@CrossOrigin
@AllArgsConstructor
public class ProductosController {

    @Autowired
    private final ProductosService productosService;

    @Autowired
    private final ModelMapper modelMapper;

    // ** Listar Todos Los Productos
    @GetMapping
    public ResponseEntity<Iterable<ProductosDTO>> listarProductos(){
        Iterable<Productos> productos = productosService.listarProductos().getBody();
        assert productos != null;
        return ResponseEntity.ok(mapToDTOs(productos));
    }

    // ** Listar Producto Por Id
    @GetMapping("/{id}")
    public ResponseEntity<ProductosDTO> buscarPorId (@PathVariable(value = "id") Long id){
        Productos productos = productosService.buscarPorId(id).getBody();
        return ResponseEntity.ok(mapToDTO(productos));
    }

    // ** Crear Un Nuevo Producto
    @PostMapping("/nuevoProducto")
    public ResponseEntity<ProductosDTO> crearProducto(@RequestBody ProductosDTO productosDTO){
        Productos productos = mapToEntity(productosDTO);
        Productos productoGuardado = productosService.crearProducto(productos).getBody();
        return new ResponseEntity<>(mapToDTO(productoGuardado), HttpStatus.CREATED);
    }

    // ** Actualizar Producto
    @PutMapping("/{id}")
    public ResponseEntity<ProductosDTO> actualizarProducto(@PathVariable(value = "id") Long id, @RequestBody ProductosDTO productosDTO) {
        if (productosDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        Productos productos = mapToEntity(productosDTO);
        if (productos == null) {
            return ResponseEntity.badRequest().build();
        }
        Productos productoActualizado = productosService.actualizarProducto(id, productos).getBody();
        if (productoActualizado == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ProductosDTO productoActualizadoDTO = mapToDTO(productoActualizado);
        return ResponseEntity.ok(productoActualizadoDTO);
    }

    // ** Eliminar Cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto (@PathVariable(value = "id") Long id){
        productosService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    // ** ----------------------- MAPEO DE ENTIDAD A DTO Y VICEVERSA ------------------------------------------

    // ** Método privado para mapear una entidad Producto a un DTO ProductoDTO
    private ProductosDTO mapToDTO(Productos productos) {
        return modelMapper.map(productos, ProductosDTO.class);
    }

    // ** Método privado para mapear una lista de entidades Producto a una lista de DTOs ProductoDTO
    private List<ProductosDTO> mapToDTOs(Iterable<Productos> productos) {
        return StreamSupport.stream(productos.spliterator(), false)
                .map(producto -> modelMapper.map(producto, ProductosDTO.class))
                .collect(Collectors.toList());
    }

    // ** Método privado para mapear un DTO ProductoDTO a una entidad Producto
    private Productos mapToEntity(ProductosDTO productosDTO) {
        return modelMapper.map(productosDTO, Productos.class);
    }
}
