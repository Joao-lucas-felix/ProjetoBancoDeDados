package com.joao_lucas_felix.ProjetoBancoDeDados.services;

import com.joao_lucas_felix.ProjetoBancoDeDados.controllers.LivroController;
import com.joao_lucas_felix.ProjetoBancoDeDados.domain.DataTransferObjects.LivroDto;
import com.joao_lucas_felix.ProjetoBancoDeDados.domain.Livro;
import com.joao_lucas_felix.ProjetoBancoDeDados.repositories.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class LivroService {
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    private static final Logger logger = Logger.getLogger(UsuarioService.class.getName());
    private final LivroRepository repository;
    private final PagedResourcesAssembler<LivroDto> assembler;
    @Autowired
    public LivroService(LivroRepository repository,
                          PagedResourcesAssembler<LivroDto> assembler){
        this.repository = repository;
        this.assembler = assembler;
    }

    public LivroDto create(LivroDto dto) {
        if(dto == null) throw new RuntimeException("Informações obrigatorias nulas");

        logger.info("Criando Livro" + dto.getDataLancamento());
        try {
           Date data = formatter.parse(dto.getDataLancamento());

            Livro livroParaSerSalvo = new Livro(dto.getKey() ,
                    dto.getTitulo(), dto.getDescription(),
                    dto.getPreco(), data, dto.getQtdEstoque());

            Livro saved = repository.save(livroParaSerSalvo);

            LivroDto livroDtoResponse = new LivroDto(saved.getId()
                    ,saved.getTitulo(), saved.getDescription(),
                    saved.getPreco(), formatter.format(saved.getDataLacamento()), saved.getQtdEstoque());

            livroDtoResponse
                    .add(linkTo(methodOn(LivroController.class)
                            .findById(livroDtoResponse.getKey()))
                            .withSelfRel());
            return livroDtoResponse;
        } catch (ParseException e) {
            throw new RuntimeException("Data no formato invalido!");
        }
    }

    public PagedModel<EntityModel<LivroDto>> findAll(Pageable pageable) {

        logger.info("Encontrando todos os Livros");

        Page<Livro> paginaLivro = repository.findAll(pageable);

        Page<LivroDto> mapDtos = paginaLivro.map(livro ->  new LivroDto(livro.getId()
                ,livro.getTitulo(), livro.getDescription(),
                livro.getPreco(), formatter.format(livro.getDataLacamento()),
                livro.getQtdEstoque()));

        mapDtos.map(p -> p.add(linkTo(methodOn(LivroController.class).findById(p.getKey())).withSelfRel()));

        Link findAllLink = linkTo(
                methodOn(LivroController.class)
                        .findAll(pageable.getPageNumber(),
                                pageable.getPageSize(),
                                "asc")).withSelfRel();

        return assembler.toModel(mapDtos, findAllLink);
    }

    public LivroDto findById(Long id) {

        logger.info("Encontrando um Livro");

        Livro entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não foi encontrado nehum Usuario com esse ID!"));
        LivroDto dto =  new LivroDto(entity.getId()
                ,entity.getTitulo(), entity.getDescription(),
                entity.getPreco(), formatter.format(entity.getDataLacamento()), entity.getQtdEstoque());

        dto.add(
                linkTo(methodOn(LivroController.class)
                        .findById(id)).withSelfRel());
        return dto;
    }



    public PagedModel<EntityModel<LivroDto>> findLivroByTitulo(String titulo, Pageable pageable) {

        logger.info("Buscando Usuario com o nome: " + titulo + "!");

        Page<Livro> livroPage = repository.findLivroByTitulo(titulo, pageable);

        Page<LivroDto> mapDto = livroPage.map(livro ->  new LivroDto(livro.getId()
                ,livro.getTitulo(), livro.getDescription(),
                livro.getPreco(), formatter.format(livro.getDataLacamento()),
                livro.getQtdEstoque()));
        mapDto.map(
                p -> p.add(
                        linkTo(methodOn(LivroController.class)
                                .findById(p.getKey())).withSelfRel()));

        Link link = linkTo(
                methodOn(LivroController .class)
                        .findAll(pageable.getPageNumber(),
                                pageable.getPageSize(),
                                "asc")).withSelfRel();

        return assembler.toModel(mapDto, link);
    }

    public LivroDto update(LivroDto dto) {
        if (dto == null) throw new RuntimeException("Livro com informações nulas!");
        logger.info("Atualizando os dados do livro com o id: "+dto.getKey()+"!");
        Livro livroASerAtualizado =
                repository.findById(dto.getKey())
                        .orElseThrow(() -> new RuntimeException("O Livro que você está tentando atulizar não existe"));

        try {
            Date data = formatter.parse(dto.getDataLancamento());

            livroASerAtualizado.setId(dto.getKey());
            livroASerAtualizado.setTitulo(dto.getTitulo());
            livroASerAtualizado.setDescription(dto.getDescription());
            livroASerAtualizado.setPreco(dto.getPreco());
            livroASerAtualizado.setDataLacamento(data);
            livroASerAtualizado.setQtdEstoque(dto.getQtdEstoque());
            Livro saved = repository.save(livroASerAtualizado);
            LivroDto livroDtoResponse = new LivroDto(saved.getId()
                    , saved.getTitulo(), saved.getDescription(),
                    saved.getPreco(), formatter.format(saved.getDataLacamento()), saved.getQtdEstoque());

            livroDtoResponse
                    .add(linkTo(methodOn(LivroController.class)
                            .findById(livroDtoResponse.getKey()))
                            .withSelfRel());
            return livroDtoResponse;
        }catch (ParseException e) {
                throw new RuntimeException("Data no formato invalido!");
            }
    }

    public void delete(Long id) {
        Livro autorParaDeletar = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não foi encontrado usuario com esse ID!"));

        logger.info("Deletando o Livro com id: "+id+"!");
        repository.delete(autorParaDeletar);

    }
}
