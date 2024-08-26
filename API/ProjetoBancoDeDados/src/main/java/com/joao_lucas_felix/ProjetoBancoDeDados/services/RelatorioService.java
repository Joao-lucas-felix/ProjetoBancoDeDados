package com.joao_lucas_felix.ProjetoBancoDeDados.services;

import com.joao_lucas_felix.ProjetoBancoDeDados.domain.DataTransferObjects.RelatorioDto;
import com.joao_lucas_felix.ProjetoBancoDeDados.domain.Livro;
import com.joao_lucas_felix.ProjetoBancoDeDados.domain.Pedido;
import com.joao_lucas_felix.ProjetoBancoDeDados.domain.Usuario;
import com.joao_lucas_felix.ProjetoBancoDeDados.repositories.LivroRepository;
import com.joao_lucas_felix.ProjetoBancoDeDados.repositories.PedidoRepository;
import com.joao_lucas_felix.ProjetoBancoDeDados.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RelatorioService {
    private static class PedidoLivro{
        public Livro livro;
        public Integer quantidade;

        public PedidoLivro(Livro livro, Integer quantidade) {
            this.livro = livro;
            this.quantidade = quantidade;
        }
    }

    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;
    private final PedidoRepository pedidoRepository;

    @Autowired
    public RelatorioService(LivroRepository livroRepository,
                            UsuarioRepository usuarioRepository,
                            PedidoRepository pedidoRepository){
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
        this.pedidoRepository = pedidoRepository;

    }
    public RelatorioDto getRelatorio() {
        List<Livro> allLivros = livroRepository.findAll();
        List<Usuario> allUsuarios = usuarioRepository.findAll();
        List<Pedido> allPeididos = pedidoRepository.findAll();

        RelatorioDto relatorioDto = new RelatorioDto();

        //Numeros cadastrados no banco
        relatorioDto.setNumeroLivrosCadastrados(allLivros.size());
        relatorioDto.setNumeroClientesCadastrados(allUsuarios.size());
        relatorioDto.setNumeroPedidosFeitos(allPeididos.size());

        relatorioDto.setNumeroLivrosEstoque(
                allLivros
                .stream()
                .map(Livro::getQtdEstoque)
                .reduce(0, Integer::sum)
        );

        Double faturamentoTotal = allPeididos.stream()
                .map(pedido ->
                        new PedidoLivro(livroRepository.findById(pedido.getIdLivro())
                                .orElseThrow(() -> new RuntimeException("Houve um error ao encontrar o Livro com ID " + pedido.getIdLivro()))
                                , pedido.getQuantidade()))
                .reduce(0.0,
                        (x, y) -> x + (y.livro.getPreco() * y.quantidade)
                        , Double::sum);

        relatorioDto.setFaturamentoTotal(faturamentoTotal);
        return relatorioDto;
    }
}
