package com.joao_lucas_felix.ProjetoBancoDeDados.controllers;

import com.joao_lucas_felix.ProjetoBancoDeDados.domain.DataTransferObjects.RelatorioDto;
import com.joao_lucas_felix.ProjetoBancoDeDados.services.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/relatorio/v1")
public class RelatorioController {
    private static RelatorioService service;
    @Autowired
    public RelatorioController(RelatorioService service){
        RelatorioController.service = service;
    }
    @GetMapping
    public ResponseEntity<RelatorioDto> getRelatorioSistema(){
        return ResponseEntity.ok(service.getRelatorio());

    }
}
