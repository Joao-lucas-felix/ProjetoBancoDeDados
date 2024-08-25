package com.joao_lucas_felix.ProjetoBancoDeDados.services;

import com.joao_lucas_felix.ProjetoBancoDeDados.controllers.AutorController;
import com.joao_lucas_felix.ProjetoBancoDeDados.domain.Autor;
import com.joao_lucas_felix.ProjetoBancoDeDados.domain.DataTransferObjects.AutorDto;
import com.joao_lucas_felix.ProjetoBancoDeDados.repositories.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Service
public class AutorService {
    private static final Logger logger = Logger.getLogger(AutorService.class.getName());
    private final AutorRepository repository;
    private final PagedResourcesAssembler<AutorDto> assembler;
    @Autowired
    public AutorService(AutorRepository repository,
                        PagedResourcesAssembler<AutorDto> assembler){
        this.repository = repository;
        this.assembler = assembler;
    }

    public AutorDto create(AutorDto autorDto) {
        if(autorDto == null) throw new RuntimeException("Informações obrigatorias nulas");

        logger.info("Criando Autor");

        Autor autorParaSerSalvo = new Autor(autorDto.getKey(), autorDto.getNome());
        Autor autorSalvo = repository.save(autorParaSerSalvo);

        AutorDto autorDtoResponse = new AutorDto(autorSalvo.getId(), autorSalvo.getNome());
        autorDtoResponse
                .add(linkTo(methodOn(AutorController.class)
                        .findById(autorDtoResponse.getKey()))
                        .withSelfRel());
        return autorDtoResponse;
    }

    public PagedModel<EntityModel<AutorDto>> findAll(Pageable pageable) {

        logger.info("Encontrando todos os Autores");

        Page<Autor> PaginaAutores = repository.findAll(pageable);

        Page<AutorDto> mapDtos = PaginaAutores.map(autor -> new AutorDto(autor.getId(), autor.getNome()));

        mapDtos.map(p -> p.add(linkTo(methodOn(AutorController.class).findById(p.getKey())).withSelfRel()));

        Link findAllLink = linkTo(
                methodOn(AutorController.class)
                        .findAll(pageable.getPageNumber(),
                                pageable.getPageSize(),
                                "asc")).withSelfRel();

        return assembler.toModel(mapDtos, findAllLink);
    }

    public AutorDto findById(Long id) {

        logger.info("Encontrando um autor");

        Autor entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não foi encontrado nehum autor com esse ID!"));
        AutorDto dto = new AutorDto(entity.getId(), entity.getNome());
        dto.add(
                linkTo(methodOn(AutorController.class)
                        .findById(id)).withSelfRel());
        return dto;
    }



    public PagedModel<EntityModel<AutorDto>> findAutorByName(String name, Pageable pageable) {

        logger.info("Buscando autores com o nome: " + name + "!");

        Page<Autor> autorPage = repository.findAutorByName(name, pageable);

        Page<AutorDto> mapDto = autorPage.map(autor -> new AutorDto(autor.getId(), autor.getNome()));
        mapDto.map(
                p -> p.add(
                        linkTo(methodOn(AutorController.class)
                                .findById(p.getKey())).withSelfRel()));

        Link link = linkTo(
                methodOn(AutorController.class)
                        .findAll(pageable.getPageNumber(),
                                pageable.getPageSize(),
                                "asc")).withSelfRel();

        return assembler.toModel(mapDto, link);
    }

    public AutorDto update(AutorDto autorDto) {
        if (autorDto == null) throw new RuntimeException("Autor com informações nulas!");
        logger.info("Atualizando os dados do autor com o id: "+autorDto.getKey()+"!");
        Autor autorASerAtualizado =
                repository.findById(autorDto.getKey())
                        .orElseThrow(() -> new RuntimeException("O autor que você está tentando atulizar não existe"));

        autorASerAtualizado.setId(autorDto.getKey());
        autorASerAtualizado.setNome(autorDto.getNome());
        Autor saved = repository.save(autorASerAtualizado);
        AutorDto response = new AutorDto(saved.getId(), saved.getNome());
        return response
                .add(linkTo(methodOn(AutorController.class)
                        .findById(response.getKey())).withSelfRel());
    }

    public void delete(Long id) {
        Autor autorParaDeletar = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não foi encontrado autor com esse ID!"));

        logger.info("Deletando o autor com id: "+id+"!");
        repository.delete(autorParaDeletar);

    }
}
