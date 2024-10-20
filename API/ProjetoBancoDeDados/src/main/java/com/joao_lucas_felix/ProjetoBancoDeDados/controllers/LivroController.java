package com.joao_lucas_felix.ProjetoBancoDeDados.controllers;

import com.joao_lucas_felix.ProjetoBancoDeDados.domain.DataTransferObjects.LivroDto;
import com.joao_lucas_felix.ProjetoBancoDeDados.services.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/livro/v1")
public class LivroController {
    private final LivroService service;
    @Autowired
    public LivroController(LivroService service){
        this.service = service;
    }

    //Create
    @PostMapping
    public ResponseEntity<LivroDto> create(
            @RequestBody LivroDto dto
    ){
        return ResponseEntity.ok(this.service.create(dto));
    }

    //Reads
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<LivroDto>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "titulo"));
        return ResponseEntity.ok(service.findAll(pageable));
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<LivroDto> findById(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(service.findById(id));
    }
    @GetMapping(value = "/findLivroByTitulo/{titulo}")
    public ResponseEntity<PagedModel<EntityModel<LivroDto>>> findByTitulo
            (@PathVariable(name = "titulo") String titulo,
             @RequestParam(value = "page", defaultValue = "0") Integer page,
             @RequestParam(value = "size", defaultValue = "50") Integer size,
             @RequestParam(value = "direction", defaultValue = "asc") String direction ){

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "titulo"));
        return ResponseEntity.ok(service.findLivroByTitulo(titulo, pageable));
    }

    //Update
    @PutMapping
    public ResponseEntity<LivroDto>  update
    (@RequestBody LivroDto dto){
        return ResponseEntity.ok(service.update(dto));
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete
            (@PathVariable(name = "id") Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
