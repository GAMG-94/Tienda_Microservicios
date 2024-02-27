package com.tienda.clientes.interfaces;

import com.tienda.clientes.dto.ProveedoresDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="proveedores")
public interface ProveedoresFeignClient {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<ProveedoresDTO> buscarPorId(@PathVariable(value = "id") Long id);

}
