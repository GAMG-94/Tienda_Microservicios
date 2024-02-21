package com.tienda.productos.service;

import com.tienda.productos.dto.ProductosDTO;
import com.tienda.productos.entity.Productos;
import com.tienda.productos.repository.ProductosRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductosService {

    @Autowired
    private final ProductosRepository productosRepository;

    @Autowired
    private final ModelMapper modelMapper;

    // ** Listar Todos Los Productos
    public ResponseEntity<Iterable<Productos>> listarProductos() {
        Iterable<Productos> productos = productosRepository.findAll();
        return ResponseEntity.ok(productos);
    }

    // ** Listar Producto Por Id
    public ResponseEntity<Productos> buscarPorId(Long id){
        Optional<Productos> productos = productosRepository.findById(id);
        return productos.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ** Crear Nuevo Producto
    public ResponseEntity<Productos> crearProducto(Productos nuevoProducto){
        Productos productoGuardado = productosRepository.save(nuevoProducto);
        return ResponseEntity.ok(productoGuardado);
    }

    // ** Actualizar Producto
    public ResponseEntity<Productos> actualizarProducto(Long id, @Valid Productos productos) {
        Optional<Productos> productoOptional = productosRepository.findById(id);
        if (productoOptional.isPresent()) {
            Productos productoExistente = productoOptional.get();
            productoExistente.setNombre(productos.getNombre());
            productoExistente.setEmpresa(productos.getEmpresa());
            productoExistente.setFechaPedido(productos.getFechaPedido());
            productoExistente.setFechaEntrega(productos.getFechaEntrega());
            productoExistente.setPrecio(productos.getPrecio());
            productoExistente.setCantidad(productos.getCantidad());
            Productos productoActualizado = productosRepository.save(productoExistente);
            return ResponseEntity.ok(productoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ** Eliminar Producto Por Id
    public void eliminarProducto(Long id) {
        productosRepository.deleteById(id);
        ResponseEntity.noContent().build();
    }
}
