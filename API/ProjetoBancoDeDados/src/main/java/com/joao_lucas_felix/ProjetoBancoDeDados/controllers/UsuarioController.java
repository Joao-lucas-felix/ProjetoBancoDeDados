package com.joao_lucas_felix.ProjetoBancoDeDados.controllers;

import com.joao_lucas_felix.ProjetoBancoDeDados.domain.DataTransferObjects.UsuarioDto;
import com.joao_lucas_felix.ProjetoBancoDeDados.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/usuario/v1")
public class UsuarioController {
    private final UsuarioService service;
    @Autowired
    public UsuarioController(UsuarioService service){
        this.service = service;
    }

    //Create
    @PostMapping
    public ResponseEntity<UsuarioDto> createAutor(
            @RequestBody UsuarioDto usuarioDto
    ){
        return ResponseEntity.ok(this.service.create(usuarioDto));
    }

    //Reads
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<UsuarioDto>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(service.findAll(pageable));
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioDto> findById(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(service.findById(id));
    }
    @GetMapping(value = "/findUsuarioByName/{name}")
    public ResponseEntity<PagedModel<EntityModel<UsuarioDto>>> findByName
            (@PathVariable(name = "name") String nome,
             @RequestParam(value = "page", defaultValue = "0") Integer page,
             @RequestParam(value = "size", defaultValue = "12") Integer size,
             @RequestParam(value = "direction", defaultValue = "asc") String direction ){

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(service.findUsuarioByName(nome, pageable));
    }

    //Update
    @PutMapping
    public ResponseEntity<UsuarioDto>  updateAutor
    (@RequestBody UsuarioDto usuarioDto){
        return ResponseEntity.ok(service.update(usuarioDto));
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteAutor
            (@PathVariable(name = "id") Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
