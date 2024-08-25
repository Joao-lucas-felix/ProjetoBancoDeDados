package com.joao_lucas_felix.ProjetoBancoDeDados.services;

import com.joao_lucas_felix.ProjetoBancoDeDados.controllers.EditoraController;
import com.joao_lucas_felix.ProjetoBancoDeDados.domain.DataTransferObjects.EditoraDto;
import com.joao_lucas_felix.ProjetoBancoDeDados.domain.Editora;
import com.joao_lucas_felix.ProjetoBancoDeDados.repositories.EditoraRepository;
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
public class EditoraService {
    private static final Logger logger = Logger.getLogger(AutorService.class.getName());
    private final EditoraRepository repository;
    private final PagedResourcesAssembler<EditoraDto> assembler;
    @Autowired
    public EditoraService(EditoraRepository repository,
                        PagedResourcesAssembler<EditoraDto> assembler){
        this.repository = repository;
        this.assembler = assembler;
    }


    public EditoraDto create(EditoraDto editoraDto) {
        if(editoraDto == null) throw new RuntimeException("Informações obrigatorias nulas");

        logger.info("Criando Editora");

        Editora editoraParaSerSalva = new Editora(editoraDto.getKey(), editoraDto.getNome());
        Editora EditoraSalvo = repository.save(editoraParaSerSalva);

        EditoraDto editoraDtoResponse = new EditoraDto(EditoraSalvo.getId(), EditoraSalvo.getNome());
        editoraDtoResponse
                .add(linkTo(methodOn(EditoraController.class)
                        .findById(editoraDtoResponse.getKey()))
                        .withSelfRel());
        return editoraDtoResponse;
    }
    public PagedModel<EntityModel<EditoraDto>> findAll(Pageable pageable) {

        logger.info("Encontrando todas as Editoras");

        Page<Editora> PaginaEditora = repository.findAll(pageable);

        Page<EditoraDto> mapDtos = PaginaEditora.map(editora -> new EditoraDto(editora.getId(), editora.getNome()));

        mapDtos.map(p -> p.add(linkTo(methodOn(EditoraController.class).findById(p.getKey())).withSelfRel()));

        Link findAllLink = linkTo(
                methodOn(EditoraController.class)
                        .findAll(pageable.getPageNumber(),
                                pageable.getPageSize(),
                                "asc")).withSelfRel();

        return assembler.toModel(mapDtos, findAllLink);
    }

    public EditoraDto findById(Long id) {
        logger.info("Encontrando uma editora");

        Editora entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não foi encontrado nehum autor com esse ID!"));
        EditoraDto dto = new EditoraDto(entity.getId(), entity.getNome());
        dto.add(
                linkTo(methodOn(EditoraController.class)
                        .findById(id)).withSelfRel());
        return dto;
    }

    public PagedModel<EntityModel<EditoraDto>> findEditoraByName(String nome, Pageable pageable) {

        logger.info("Buscando editoras com o nome: " + nome + "!");

        Page<Editora> editoraPage = repository.findEditoraByName(nome, pageable);

        Page<EditoraDto> mapDto = editoraPage.map(editora -> new EditoraDto(editora.getId(), editora.getNome()));
        mapDto.map(
                p -> p.add(
                        linkTo(methodOn(EditoraController.class)
                                .findById(p.getKey())).withSelfRel()));

        Link link = linkTo(
                methodOn(EditoraController.class)
                        .findAll(pageable.getPageNumber(),
                                pageable.getPageSize(),
                                "asc")).withSelfRel();

        return assembler.toModel(mapDto, link);
    }


    public EditoraDto update(EditoraDto editoraDto) {
        if (editoraDto == null) throw new RuntimeException("Editora com informações nulas!");
        logger.info("Atualizando os dados do editora com o id: "+editoraDto.getKey()+"!");
        Editora editoraASerAtualizado =
                repository.findById(editoraDto.getKey())
                        .orElseThrow(() -> new RuntimeException("A editora que você está tentando atulizar não existe"));

        editoraASerAtualizado.setId(editoraDto.getKey());
        editoraASerAtualizado.setNome(editoraDto.getNome());
        Editora saved = repository.save(editoraASerAtualizado);
        EditoraDto response = new EditoraDto(saved.getId(), saved.getNome());

        response
                .add(linkTo(methodOn(EditoraController.class)
                        .findById(response.getKey())).withSelfRel());
        return response;
    }

    public void delete(Long id) {
        Editora editoraParaDeletar = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não foi encontrado autor com esse ID!"));

        logger.info("Deletando o autor com id: "+id+"!");
        repository.delete(editoraParaDeletar);
    }


}
