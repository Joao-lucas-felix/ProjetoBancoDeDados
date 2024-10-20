package com.joao_lucas_felix.ProjetoBancoDeDados.controllers;

import com.joao_lucas_felix.ProjetoBancoDeDados.domain.DataTransferObjects.FuncionarioDto;
import com.joao_lucas_felix.ProjetoBancoDeDados.domain.DataTransferObjects.UsuarioDto;
import com.joao_lucas_felix.ProjetoBancoDeDados.domain.Funcionario;
import com.joao_lucas_felix.ProjetoBancoDeDados.services.FuncionarioService;
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
@RequestMapping(value = "/api/funcionario/v1")
public class FuncionarioController {
    private final FuncionarioService service;
    @Autowired
    public FuncionarioController(FuncionarioService service){
        this.service = service;
    }

    //Create

    //Reads
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<FuncionarioDto>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(service.findAll(pageable));
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<FuncionarioDto> findById(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(service.findById(id));
    }
    @GetMapping(value = "/findFuncionarioByName/{name}")
    public ResponseEntity<PagedModel<EntityModel<FuncionarioDto>>> findByName
            (@PathVariable(name = "name") String nome,
             @RequestParam(value = "page", defaultValue = "0") Integer page,
             @RequestParam(value = "size", defaultValue = "12") Integer size,
             @RequestParam(value = "direction", defaultValue = "asc") String direction ){

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(service.findFuncionariosByName(nome, pageable));
    }
}
