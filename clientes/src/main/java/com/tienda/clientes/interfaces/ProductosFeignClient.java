package com.tienda.clientes.interfaces;

import com.tienda.clientes.dto.ProductosDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "productos")
public interface ProductosFeignClient {

    @RequestMapping(method = RequestMethod.POST, value = "/{id}")
    ResponseEntity<ProductosDTO> buscarPorId(@PathVariable(value = "id") Long id);

}
