package com.joao_lucas_felix.ProjetoBancoDeDados.services;

import com.joao_lucas_felix.ProjetoBancoDeDados.controllers.PedidoController;
import com.joao_lucas_felix.ProjetoBancoDeDados.domain.DataTransferObjects.FrontEndPedidoDto;
import com.joao_lucas_felix.ProjetoBancoDeDados.domain.DataTransferObjects.PedidoDto;
import com.joao_lucas_felix.ProjetoBancoDeDados.domain.Funcionario;
import com.joao_lucas_felix.ProjetoBancoDeDados.domain.Livro;
import com.joao_lucas_felix.ProjetoBancoDeDados.domain.Pedido;
import com.joao_lucas_felix.ProjetoBancoDeDados.domain.Usuario;
import com.joao_lucas_felix.ProjetoBancoDeDados.repositories.FuncionarioRepository;
import com.joao_lucas_felix.ProjetoBancoDeDados.repositories.LivroRepository;
import com.joao_lucas_felix.ProjetoBancoDeDados.repositories.PedidoRepository;
import com.joao_lucas_felix.ProjetoBancoDeDados.repositories.UsuarioRepository;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Service
public class PedidoService {
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    private final SimpleDateFormat formatterFront = new SimpleDateFormat("yyyy-MM-dd");
    private static final Logger logger = Logger.getLogger(UsuarioService.class.getName());
    private final PedidoRepository repository;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final PagedResourcesAssembler<PedidoDto> assembler;
    private static final Pattern cpfPattern =
            Pattern.compile("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");


    public static boolean validarFormatoDoCpf(String input) {
        Matcher matcher = cpfPattern.matcher(input);
        return matcher.matches();
    }

    @Autowired
    public PedidoService(PedidoRepository repository,
                        PagedResourcesAssembler<PedidoDto> assembler,
                         UsuarioRepository usuarioRepository,
                         LivroRepository livroRepository,
                         FuncionarioRepository funcionarioRepository){
        this.repository = repository;
        this.assembler = assembler;
        this.usuarioRepository = usuarioRepository;
        this.livroRepository = livroRepository;
        this.funcionarioRepository = funcionarioRepository;

    }
    public PedidoDto create(FrontEndPedidoDto dto) {
        if(dto == null) throw new RuntimeException("Informações obrigatorias nulas");

        logger.info("Criando Pedido" );
        if (!validarFormatoDoCpf(dto.getCpf()))
            throw new RuntimeException("Cpf no formato incorreto por favor " +
                    "informe um cpf no formato \"xxx.xxx.xxx-xx\" onde x é um digito de 0 a 9");
        try {
            logger.info(dto.getData());
            Date data = formatterFront.parse(dto.getData());
            logger.info(data.toString());
            Livro livroPedido = livroRepository
                    .findById(dto.getIdLivro())
                    .orElseThrow(() -> new RuntimeException("Nehum livro com esse ID encontrado no sistema"));

            Usuario usuario = usuarioRepository
                    .findUsuarioByCpf(dto.getCpf())
                    .orElseGet(() -> {
                        Usuario novoUsuario = new Usuario(null,dto.getUserName(), dto.getCpf(), dto.getEnd());
                        return usuarioRepository.save(novoUsuario);
                    });

            Funcionario funcionario =  funcionarioRepository
                    .findFuncionarioByNome(dto.getFuncionarioName())
                    .orElseGet(() -> {
                        Funcionario f = new Funcionario(null,
                                dto.getFuncionarioName(),
                                livroPedido.getPreco() * dto.getQuantidade().doubleValue() );
                         return funcionarioRepository.save(f);
                    });

            //Atualizando a quantidade em estoque
            livroPedido.setQtdEstoque( livroPedido.getQtdEstoque() - dto.getQuantidade());
            livroRepository.save(livroPedido);

            Pedido pedidoParaSerSalvo = new Pedido(null, usuario.getId() ,
                    dto.getIdLivro(),funcionario.getId(), data, dto.getQuantidade());

            Pedido saved = repository.save(pedidoParaSerSalvo);
            String nomeUsuario = usuario.getNome();
            String nomeFuncinario = funcionario.getNome();
            String tituloLivro = livroPedido.getTitulo();
            PedidoDto pedidoDtoResponse = new PedidoDto( saved.getId(),
                    saved.getIdUser(), saved.getIdLivro(),
                    formatter.format(saved.getData()), saved.getQuantidade(),
                    nomeUsuario, tituloLivro, nomeFuncinario);

           pedidoDtoResponse
                .add(linkTo(methodOn(PedidoController.class)
                        .findById(pedidoDtoResponse.getKey()))
                        .withSelfRel());
            return pedidoDtoResponse;
        } catch (ParseException e){
            throw new RuntimeException("Data no formato invalido!");
        }
    }

    public PagedModel<EntityModel<PedidoDto>> findAll(Pageable pageable) {
        logger.info("Encontrando todos os Pedidos");

        Page<Pedido> paginaPedido = repository.findAll(pageable);

        Page<PedidoDto> mapDtos = paginaPedido.map(pedido ->  new PedidoDto( pedido.getId(),
                pedido.getIdUser(), pedido.getIdLivro(),
                formatter.format(pedido.getData()), pedido.getQuantidade(),
                usuarioRepository
                        .findById(pedido.getIdUser()).orElseThrow(() -> new RuntimeException("Usuario não encontrado"))
                        .getNome()
                ,
                livroRepository
                        .findById(pedido.getIdLivro()).orElseThrow(() -> new RuntimeException("Livro não encontrado"))
                        .getTitulo(),
                funcionarioRepository
                        .findById(pedido.getIdFuncionario()).orElseThrow(() -> new RuntimeException("Funcionario não encontrado"))
                        .getNome()
                ));

        mapDtos.map(p -> p.add(linkTo(methodOn(PedidoController.class)
                .findById(p.getKey())).withSelfRel()));

        Link findAllLink = linkTo(
                methodOn(PedidoController.class)
                        .findAll(pageable.getPageNumber(),
                                pageable.getPageSize(),
                                "asc")).withSelfRel();

        return assembler.toModel(mapDtos, findAllLink);
    }

    public PagedModel<EntityModel<PedidoDto>> findPedidosByUser(Long id, Pageable pageable){
        logger.info("Encontrando todos os Pedidos do Usuario "+ id);
        Page<Pedido> paginaPedido = repository.findPedidosDoUsuario(id, pageable);


        Page<PedidoDto> mapDtos = paginaPedido.map(pedido ->  new PedidoDto( pedido.getId(),
                pedido.getIdUser(), pedido.getIdLivro(),
                formatter.format(pedido.getData()), pedido.getQuantidade(),
                usuarioRepository
                        .findById(pedido.getIdUser()).orElseThrow(() -> new RuntimeException("Usuario não encontrado"))
                        .getNome()
                ,
                livroRepository
                        .findById(pedido.getIdLivro()).orElseThrow(() -> new RuntimeException("Livro não encontrado"))
                        .getTitulo(),
                funcionarioRepository
                        .findById(pedido.getIdFuncionario()).orElseThrow(() -> new RuntimeException("Funcionario não encontrado"))
                        .getNome()
        ));

        mapDtos.map(p -> p.add(linkTo(methodOn(PedidoController.class)
                .findById(p.getKey())).withSelfRel()));

        Link findAllLink = linkTo(
                methodOn(PedidoController.class)
                        .findAll(pageable.getPageNumber(),
                                pageable.getPageSize(),
                                "asc")).withSelfRel();

        return assembler.toModel(mapDtos, findAllLink);
    }

    public PagedModel<EntityModel<PedidoDto>> findPedidosByFuncionario(Long id, Pageable pageable){
        logger.info("Encontrando todos os Pedidos do Usuario "+ id);
        Page<Pedido> paginaPedido = repository.findPedidosDoFuncionario(id, pageable);


        Page<PedidoDto> mapDtos = paginaPedido.map(pedido ->  new PedidoDto( pedido.getId(),
                pedido.getIdUser(), pedido.getIdLivro(),
                formatter.format(pedido.getData()), pedido.getQuantidade(),
                usuarioRepository
                        .findById(pedido.getIdUser()).orElseThrow(() -> new RuntimeException("Usuario não encontrado"))
                        .getNome()
                ,
                livroRepository
                        .findById(pedido.getIdLivro()).orElseThrow(() -> new RuntimeException("Livro não encontrado"))
                        .getTitulo(),
                funcionarioRepository
                        .findById(pedido.getIdFuncionario()).orElseThrow(() -> new RuntimeException("Funcionario não encontrado"))
                        .getNome()
        ));

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

        PedidoDto pedidoDtoResponse =   new PedidoDto( pedido.getId(),
                pedido.getIdUser(), pedido.getIdLivro(),
                formatter.format(pedido.getData()), pedido.getQuantidade(),
                usuarioRepository
                        .findById(pedido.getIdUser()).orElseThrow(() -> new RuntimeException("Usuario não encontrado"))
                        .getNome()
                ,
                livroRepository
                        .findById(pedido.getIdLivro()).orElseThrow(() -> new RuntimeException("Livro não encontrado"))
                        .getTitulo(),
                funcionarioRepository
                        .findById(pedido.getIdFuncionario()).orElseThrow(() -> new RuntimeException("Funcionario não encontrado"))
                        .getNome()
        );

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
            Livro livroPedido = livroRepository
                    .findById(dto.getIdLivro())
                    .orElseThrow(() -> new RuntimeException("Nehum livro com esse ID encontrado no sistema"));


            Funcionario funcionario =  funcionarioRepository
                    .findFuncionarioByNome(dto.getFuncionarioName())
                    .orElseGet(() -> {
                        Funcionario f = new Funcionario(null,
                                dto.getFuncionarioName(),
                                livroPedido.getPreco() * dto.getQuantidade().doubleValue() );
                        return funcionarioRepository.save(f);
                    });
            Pedido pedidoAntigo = repository.findById(dto.getKey())
                    .orElseThrow(() -> new RuntimeException("Nehum pedido com esse id foi encontrado!"));
            Pedido pedidoParaSerAtualizado = new Pedido(dto.getKey(), dto.getIdUser() ,
                    dto.getIdLivro(), funcionario.getId() , data, dto.getQuantidade() );

            pedidoAntigo.setId(dto.getKey());
            pedidoAntigo.setIdUser(dto.getIdUser());
            pedidoAntigo.setIdLivro(dto.getIdLivro());
            pedidoAntigo.setData(data);
            pedidoAntigo.setQuantidade(pedidoParaSerAtualizado.getQuantidade());

            Pedido pedidoAtualizado = repository.save(pedidoAntigo);

            PedidoDto pedidoDtoResponse =   new PedidoDto( pedidoAtualizado.getId(),
                    pedidoAtualizado.getIdUser(), pedidoAtualizado.getIdLivro(),
                    formatter.format(pedidoAtualizado.getData()), pedidoAtualizado.getQuantidade(),
                    usuarioRepository
                            .findById(pedidoAtualizado.getIdUser()).orElseThrow(() -> new RuntimeException("Usuario não encontrado"))
                            .getNome()
                    ,
                    livroRepository
                            .findById(pedidoAtualizado.getIdLivro()).orElseThrow(() -> new RuntimeException("Livro não encontrado"))
                            .getTitulo(),
                    funcionarioRepository
                            .findById(pedidoAtualizado.getIdFuncionario()).orElseThrow(() -> new RuntimeException("Funcionario não encontrado"))
                            .getNome()
            );


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
