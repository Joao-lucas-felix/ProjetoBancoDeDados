package com.joao_lucas_felix.ProjetoBancoDeDados.controllers;

import com.joao_lucas_felix.ProjetoBancoDeDados.domain.DataTransferObjects.EditoraDto;
import com.joao_lucas_felix.ProjetoBancoDeDados.services.EditoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/editora/v1")
public class EditoraController {
    private final EditoraService service;
    @Autowired
    public EditoraController(EditoraService service){
        this.service = service;
    }

    //Create
    @PostMapping
    public ResponseEntity<EditoraDto> createAutor(
            @RequestBody EditoraDto editoraDto
    ){
        return ResponseEntity.ok(this.service.create(editoraDto));
    }

    //Reads
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<EditoraDto>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(service.findAll(pageable));
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<EditoraDto> findById(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(service.findById(id));
    }
    @GetMapping(value = "/findEditoraByName/{name}")
    public ResponseEntity<PagedModel<EntityModel<EditoraDto>>> findByName
            (@PathVariable(name = "name") String nome,
             @RequestParam(value = "page", defaultValue = "0") Integer page,
             @RequestParam(value = "size", defaultValue = "12") Integer size,
             @RequestParam(value = "direction", defaultValue = "asc") String direction ){

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(service.findEditoraByName(nome, pageable));
    }

    //Update
    @PutMapping
    public ResponseEntity<EditoraDto>  updateEditora
    (@RequestBody EditoraDto editoraDto){
        return ResponseEntity.ok(service.update(editoraDto));
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteEditora
            (@PathVariable(name = "id") Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
