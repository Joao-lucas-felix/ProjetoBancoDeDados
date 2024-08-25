package com.joao_lucas_felix.ProjetoBancoDeDados.services;

import com.joao_lucas_felix.ProjetoBancoDeDados.controllers.UsuarioController;
import com.joao_lucas_felix.ProjetoBancoDeDados.domain.DataTransferObjects.UsuarioDto;
import com.joao_lucas_felix.ProjetoBancoDeDados.domain.Usuario;
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
public class UsuarioService {
    private static final Logger logger = Logger.getLogger(UsuarioService.class.getName());
    private static final Pattern cpfPattern =
            Pattern.compile("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
    private final UsuarioRepository repository;
    private final PagedResourcesAssembler<UsuarioDto> assembler;
    @Autowired
    public UsuarioService(UsuarioRepository repository,
                        PagedResourcesAssembler<UsuarioDto> assembler){
        this.repository = repository;
        this.assembler = assembler;
    }

    public static boolean validarFormatoDoCpf(String input) {
        Matcher matcher = cpfPattern.matcher(input);
        return matcher.matches();
    }


    public UsuarioDto create(UsuarioDto usuarioDto) {
        if(usuarioDto == null) throw new RuntimeException("Informações obrigatorias nulas");

        logger.info("Criando Usuario");
        if (!validarFormatoDoCpf(usuarioDto.getCpf()))
            throw new RuntimeException("Cpf no formato incorreto por favor " +
                    "informe um cpf no formato \"xxx.xxx.xxx-xx\" onde x é um digito de 0 a 9");

        Usuario usuarioParaSerSalvo = new Usuario(usuarioDto.getKey(), 
                usuarioDto.getNome(), usuarioDto.getCpf(), usuarioDto.getEnd());

        Usuario saved = repository.save(usuarioParaSerSalvo);

        UsuarioDto usuarioDtoResponse = new UsuarioDto(saved.getId()
                , saved.getNome(), saved.getCpf(), saved.getEnd());
        usuarioDtoResponse
                .add(linkTo(methodOn(UsuarioController.class)
                        .findById(usuarioDtoResponse.getKey()))
                        .withSelfRel());
        return usuarioDtoResponse;
    }

    public PagedModel<EntityModel<UsuarioDto>> findAll(Pageable pageable) {

        logger.info("Encontrando todos os Usuario");

        Page<Usuario> PaginaUsuario = repository.findAll(pageable);

        Page<UsuarioDto> mapDtos = PaginaUsuario.map(usuario -> new UsuarioDto(usuario.getId(), usuario.getNome()
                ,usuario.getCpf(), usuario.getEnd()));

        mapDtos.map(p -> p.add(linkTo(methodOn(UsuarioController.class).findById(p.getKey())).withSelfRel()));

        Link findAllLink = linkTo(
                methodOn(UsuarioController.class)
                        .findAll(pageable.getPageNumber(),
                                pageable.getPageSize(),
                                "asc")).withSelfRel();

        return assembler.toModel(mapDtos, findAllLink);
    }

    public UsuarioDto findById(Long id) {

        logger.info("Encontrando um Usuario");

        Usuario entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não foi encontrado nehum Usuario com esse ID!"));
        UsuarioDto dto = new UsuarioDto(entity.getId(), entity.getNome(), entity.getCpf(), entity.getEnd());
        dto.add(
                linkTo(methodOn(UsuarioController.class)
                        .findById(id)).withSelfRel());
        return dto;
    }



    public PagedModel<EntityModel<UsuarioDto>> findUsuarioByName(String name, Pageable pageable) {

        logger.info("Buscando Usuario com o nome: " + name + "!");

        Page<Usuario> autorPage = repository.findUsuarioByNome(name, pageable);

        Page<UsuarioDto> mapDto = autorPage.map(usuario -> new UsuarioDto(usuario.getId(), usuario.getNome(),
                usuario.getCpf(), usuario.getEnd()));
        mapDto.map(
                p -> p.add(
                        linkTo(methodOn(UsuarioController.class)
                                .findById(p.getKey())).withSelfRel()));

        Link link = linkTo(
                methodOn(UsuarioController.class)
                        .findAll(pageable.getPageNumber(),
                                pageable.getPageSize(),
                                "asc")).withSelfRel();

        return assembler.toModel(mapDto, link);
    }

    public UsuarioDto update(UsuarioDto dto) {
        if (dto == null) throw new RuntimeException("Usuario com informações nulas!");
        logger.info("Atualizando os dados do Usuario com o id: "+dto.getKey()+"!");
        if (!validarFormatoDoCpf(dto.getCpf()))
            throw new RuntimeException("Cpf no formato incorreto por favor " +
                    "informe um cpf no formato \"xxx.xxx.xxx-xx\" onde x é um digito de 0 a 9");

        Usuario usuarioASerAtualizado =
                repository.findById(dto.getKey())
                        .orElseThrow(() -> new RuntimeException("O autor que você está tentando atulizar não existe"));

        usuarioASerAtualizado.setId(dto.getKey());
        usuarioASerAtualizado.setNome(dto.getNome());
        usuarioASerAtualizado.setCpf(dto.getCpf());
        usuarioASerAtualizado.setEnd(dto.getEnd());

        Usuario saved = repository.save(usuarioASerAtualizado);
        UsuarioDto response = new UsuarioDto(saved.getId(), saved.getNome(), saved.getCpf(), saved.getEnd());
        return response
                .add(linkTo(methodOn(UsuarioController.class)
                        .findById(response.getKey())).withSelfRel());
    }

    public void delete(Long id) {
        Usuario autorParaDeletar = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não foi encontrado usuario com esse ID!"));

        logger.info("Deletando o Usuario com id: "+id+"!");
        repository.delete(autorParaDeletar);

    }

}
