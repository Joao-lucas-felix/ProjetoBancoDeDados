package com.joao_lucas_felix.ProjetoBancoDeDados.services;

import com.joao_lucas_felix.ProjetoBancoDeDados.controllers.UsuarioController;
import com.joao_lucas_felix.ProjetoBancoDeDados.domain.DataTransferObjects.FuncionarioDto;
import com.joao_lucas_felix.ProjetoBancoDeDados.domain.DataTransferObjects.UsuarioDto;
import com.joao_lucas_felix.ProjetoBancoDeDados.domain.Funcionario;
import com.joao_lucas_felix.ProjetoBancoDeDados.domain.Usuario;
import com.joao_lucas_felix.ProjetoBancoDeDados.repositories.FuncionarioRepository;
import com.joao_lucas_felix.ProjetoBancoDeDados.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class FuncionarioService {
    private static final Logger logger = Logger.getLogger(FuncionarioService.class.getName());

    private final FuncionarioRepository repository;
    private final PagedResourcesAssembler<FuncionarioDto> assembler;

    @Autowired
    public FuncionarioService(FuncionarioRepository repository,
                              PagedResourcesAssembler<FuncionarioDto> assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    public PagedModel<EntityModel<FuncionarioDto>> findAll(Pageable pageable) {

        logger.info("Encontrando todos os Funcionarios");

        Page<Funcionario> PaginaFuncionario = repository.findAll(pageable);

        Page<FuncionarioDto> mapDtos = PaginaFuncionario.map( f -> new FuncionarioDto(f.getId(), f.getNome(), f.getValorVendido()));

        mapDtos.map(p -> p.add(linkTo(methodOn(UsuarioController.class).findById(p.getKey())).withSelfRel()));

        Link findAllLink = linkTo(
                methodOn(UsuarioController.class)
                        .findAll(pageable.getPageNumber(),
                                pageable.getPageSize(),
                                "asc")).withSelfRel();

        return assembler.toModel(mapDtos, findAllLink);
    }

    public FuncionarioDto findById(Long id) {

        logger.info("Encontrando um Usuario");

        Funcionario entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não foi encontrado nehum Funcionario com esse ID!"));
        FuncionarioDto dto =  new FuncionarioDto(entity.getId(), entity.getNome(), entity.getValorVendido());
        dto.add(
                linkTo(methodOn(UsuarioController.class)
                        .findById(id)).withSelfRel());
        return dto;
    }


    public PagedModel<EntityModel<FuncionarioDto>> findFuncionariosByName(String name, Pageable pageable) {

        logger.info("Buscando os Funcionarios com o nome: " + name + "!");

        Page<Funcionario> funcionarioPage = repository.findAllFuncionariosByNome(name, pageable);

        Page<FuncionarioDto> mapDtos = funcionarioPage.map( f -> new FuncionarioDto(f.getId(), f.getNome(), f.getValorVendido()));
        mapDtos.map(
                p -> p.add(
                        linkTo(methodOn(UsuarioController.class)
                                .findById(p.getKey())).withSelfRel()));

        Link link = linkTo(
                methodOn(UsuarioController.class)
                        .findAll(pageable.getPageNumber(),
                                pageable.getPageSize(),
                                "asc")).withSelfRel();

        return assembler.toModel(mapDtos, link);
    }
}
