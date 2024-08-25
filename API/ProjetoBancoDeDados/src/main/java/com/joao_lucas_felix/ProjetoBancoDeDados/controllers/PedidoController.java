package com.joao_lucas_felix.ProjetoBancoDeDados.controllers;

import com.joao_lucas_felix.ProjetoBancoDeDados.domain.DataTransferObjects.PedidoDto;
import com.joao_lucas_felix.ProjetoBancoDeDados.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/pedido/v1")
public class PedidoController {
    private final PedidoService service;
    @Autowired
    public PedidoController(PedidoService service){
        this.service = service;
    }

    //Create
    @PostMapping
    public ResponseEntity<PedidoDto> create(
            @RequestBody PedidoDto pedidoDto
    ){
        return ResponseEntity.ok(this.service.create(pedidoDto));
    }

    //Reads
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<PedidoDto>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "quantidade"));
        return ResponseEntity.ok(service.findAll(pageable));
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<PedidoDto> findById
            (@PathVariable(name = "id") Long id){

        return ResponseEntity.ok(service.findById(id));
    }

    //Update
    @PutMapping
    public ResponseEntity<PedidoDto>  updateAutor
    (@RequestBody PedidoDto pedidoDto){
        return ResponseEntity.ok(service.update(pedidoDto));
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteAutor
            (@PathVariable(name = "id") Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
