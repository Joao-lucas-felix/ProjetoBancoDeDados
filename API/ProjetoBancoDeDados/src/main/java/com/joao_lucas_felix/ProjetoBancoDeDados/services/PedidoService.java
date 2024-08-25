package com.joao_lucas_felix.ProjetoBancoDeDados.services;

import com.joao_lucas_felix.ProjetoBancoDeDados.controllers.PedidoController;
import com.joao_lucas_felix.ProjetoBancoDeDados.domain.DataTransferObjects.PedidoDto;
import com.joao_lucas_felix.ProjetoBancoDeDados.domain.Pedido;
import com.joao_lucas_felix.ProjetoBancoDeDados.repositories.PedidoRepository;
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
public class PedidoService {
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    private static final Logger logger = Logger.getLogger(UsuarioService.class.getName());
    private final PedidoRepository repository;
    private final PagedResourcesAssembler<PedidoDto> assembler;
    @Autowired
    public PedidoService(PedidoRepository repository,
                        PagedResourcesAssembler<PedidoDto> assembler){
        this.repository = repository;
        this.assembler = assembler;
    }
    public PedidoDto create(PedidoDto dto) {
        if(dto == null) throw new RuntimeException("Informações obrigatorias nulas");

        logger.info("Criando Pedido" );
        try {
            Date data = formatter.parse(dto.getData());


            Pedido pedidoParaSerSalvo = new Pedido(dto.getKey(), dto.getIdUser() ,
                    dto.getIdLivro(), data, dto.getQuantidade());

            Pedido saved = repository.save(pedidoParaSerSalvo);

            PedidoDto pedidoDtoResponse = new PedidoDto( saved.getId(),
                    saved.getIdUser(), saved.getIdLivro(),
                    formatter.format(saved.getData()), saved.getQuantidade());

           pedidoDtoResponse
                .add(linkTo(methodOn(PedidoController.class)
                        .findById(pedidoDtoResponse.getKey()))
                        .withSelfRel());
            return pedidoDtoResponse;
        } catch (ParseException e) {
            throw new RuntimeException("Data no formato invalido!");
        }
    }

    public PagedModel<EntityModel<PedidoDto>> findAll(Pageable pageable) {
        logger.info("Encontrando todos os Pedidos");

        Page<Pedido> paginaPedido = repository.findAll(pageable);

        Page<PedidoDto> mapDtos = paginaPedido.map(pedido ->  new PedidoDto( pedido.getId(),
                pedido.getIdUser(), pedido.getIdLivro(),
                formatter.format(pedido.getData()), pedido.getQuantidade()));

        mapDtos.map(p -> p.add(linkTo(methodOn(PedidoController.class)
                .findById(p.getKey())).withSelfRel()));

        Link findAllLink = linkTo(
                methodOn(PedidoController.class)
                        .findAll(pageable.getPageNumber(),
                                pageable.getPageSize(),
                                "asc")).withSelfRel();

        return assembler.toModel(mapDtos, findAllLink);
    }


    public PedidoDto findById(Long id) {
        logger.info("Encontrando Pedido");
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nehum pedido com esse id foi encontrado!"));

        PedidoDto pedidoDtoResponse = new PedidoDto( pedido.getId(),
                pedido.getIdUser(), pedido.getIdLivro(),
            formatter.format(pedido.getData()), pedido.getQuantidade());

        pedidoDtoResponse
                .add(linkTo(methodOn(PedidoController.class)
                        .findById(pedidoDtoResponse.getKey()))
                        .withSelfRel());
        return pedidoDtoResponse;

    }

    public PedidoDto update(PedidoDto dto) {
        if(dto == null) throw new RuntimeException("Informações obrigatorias nulas");

        logger.info("Atualizando Pedido" );
        try {
            Date data = formatter.parse(dto.getData());

            Pedido pedidoAntigo = repository.findById(dto.getKey())
                    .orElseThrow(() -> new RuntimeException("Nehum pedido com esse id foi encontrado!"));
            Pedido pedidoParaSerAtualizado = new Pedido(dto.getKey(), dto.getIdUser() ,
                    dto.getIdLivro(), data, dto.getQuantidade());

            pedidoAntigo.setId(dto.getKey());
            pedidoAntigo.setIdUser(dto.getIdUser());
            pedidoAntigo.setIdLivro(dto.getIdLivro());
            pedidoAntigo.setData(data);
            pedidoAntigo.setQuantidade(pedidoParaSerAtualizado.getQuantidade());

            Pedido pedidoAtualizado = repository.save(pedidoAntigo);

            PedidoDto pedidoDtoResponse = new PedidoDto( pedidoAtualizado.getId(),
                    pedidoAtualizado.getIdUser(), pedidoAtualizado.getIdLivro(),
                    formatter.format(pedidoAtualizado.getData()), pedidoAtualizado.getQuantidade());


            pedidoDtoResponse
                    .add(linkTo(methodOn(PedidoController.class)
                            .findById(pedidoDtoResponse.getKey()))
                            .withSelfRel());
            return pedidoDtoResponse;
        } catch (ParseException e) {
            throw new RuntimeException("Data no formato invalido!");
        }
    }

    public void delete(Long id) {
        logger.info("Deletando o pedido!" );
            Pedido pedido = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Nehum pedido com esse id foi encontrado!"));
            repository.delete(pedido);


    }
}
