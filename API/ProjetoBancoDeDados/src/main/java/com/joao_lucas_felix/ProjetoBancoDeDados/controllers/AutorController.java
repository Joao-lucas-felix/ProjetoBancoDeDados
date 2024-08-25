package com.joao_lucas_felix.ProjetoBancoDeDados.controllers;

import com.joao_lucas_felix.ProjetoBancoDeDados.domain.DataTransferObjects.AutorDto;
import com.joao_lucas_felix.ProjetoBancoDeDados.services.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/autor/v1")
public class AutorController {
    private final AutorService service;
    @Autowired
    public AutorController(AutorService service){
       this.service = service;
    }

    //Create
    @PostMapping
    public ResponseEntity<AutorDto> createAutor(
            @RequestBody AutorDto autorDto
    ){
        return ResponseEntity.ok(this.service.create(autorDto));
    }

    //Reads
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<AutorDto>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(service.findAll(pageable));
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<AutorDto> findById(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(service.findById(id));
    }
    @GetMapping(value = "/findAutorByName/{name}")
    public ResponseEntity<PagedModel<EntityModel<AutorDto>>> findByName
            (@PathVariable(name = "name") String nome,
             @RequestParam(value = "page", defaultValue = "0") Integer page,
             @RequestParam(value = "size", defaultValue = "12") Integer size,
             @RequestParam(value = "direction", defaultValue = "asc") String direction ){

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(service.findAutorByName(nome, pageable));
    }

    //Update
    @PutMapping
    public ResponseEntity<AutorDto>  updateAutor
        (@RequestBody AutorDto autorDto){
        return ResponseEntity.ok(service.update(autorDto));
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteAutor
            (@PathVariable(name = "id") Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
