package com.tienda.clientes.interfaces;

import com.tienda.clientes.dto.VendedorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "vendedor")
public interface VendedorFeignClient {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<VendedorDTO> buscarPorId(@PathVariable(value = "id") Long id);

}
