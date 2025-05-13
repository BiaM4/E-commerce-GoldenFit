package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.business.*;
import br.com.fatec.goldenfit.dao.*;
import br.com.fatec.goldenfit.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fachada implements IFachada {
    private Map<String, IDAO> mapaDaos;
    private Map<String, List<IStrategy>> mapaAntesPesistencia;
    private Map<String, List<IStrategy>> mapaDepoisPesistencia;
    Result result = new Result();

    public Fachada() {

        // GerarLog gLog = new GerarLog();

        List<IStrategy> rnAntesCliente = new ArrayList<IStrategy>();
        List<IStrategy> rnAntesCartao = new ArrayList<IStrategy>();
        List<IStrategy> rnAntesEndereco = new ArrayList<IStrategy>();
        List<IStrategy> rnAntesUsuario = new ArrayList<IStrategy>();
        List<IStrategy> rnAntesPedido = new ArrayList<IStrategy>();
        List<IStrategy> rnAntesProduto = new ArrayList<>();

        List<IStrategy> rnDepois = new ArrayList<IStrategy>();

        mapaAntesPesistencia = new HashMap<String, List<IStrategy>>();
        mapaDepoisPesistencia = new HashMap<String, List<IStrategy>>();

        carregarMapaAntesPersistencia(rnAntesCliente, rnAntesCartao, rnAntesEndereco, rnAntesUsuario, rnAntesPedido, rnAntesProduto);

        mapaDaos = new HashMap<String, IDAO>();
        mapaDaos.put(Cartao.class.getName(), new CartaoDAO());
        mapaDaos.put(Usuario.class.getName(), new UsuarioDAO());
        mapaDaos.put(Cliente.class.getName(), new ClienteDAO());
        mapaDaos.put(Endereco.class.getName(), new EnderecoDAO());
        mapaDaos.put(Pedido.class.getName(), new PedidoDAO());
        mapaDaos.put(Cupom.class.getName(), new CupomDAO());
        mapaDaos.put(Categoria.class.getName(), new CategoriaDAO());
        mapaDaos.put(GrupoPrecificacao.class.getName(), new GrupoPrecificacaoDAO());
        mapaDaos.put(MovimentacaoEstoque.class.getName(), new MovimentacaoEstoqueDAO());
        mapaDaos.put(PedidoItem.class.getName(), new PedidoItemDAO());
        mapaDaos.put(PedidoItemTroca.class.getName(), new PedidoItemTrocaDAO());
        mapaDaos.put(FiltroPesquisaPeriodoGrafico.class.getName(), new GraficoVendasDAO());
        mapaDaos.put(Produto.class.getName(), new ProdutoDAO());
    }

    @Override
    public Result salvar(EntidadeDominio entidade) {
        String nmClass = entidade.getClass().getName();
        List<IStrategy> rnsAntes = mapaAntesPesistencia.get(nmClass);

        StringBuilder sb = executarStrategies(rnsAntes, entidade);

        if (sb.length() == 0) {
            IDAO dao = mapaDaos.get(nmClass);
            result.setResposta(dao.salvar(entidade));
        } else {
            result.setResposta(sb.toString());
        }

        return result;
    }

    @Override
    public Result alterar(EntidadeDominio entidade) {
        String nmClass = entidade.getClass().getName();
        List<IStrategy> rnsAntes = mapaAntesPesistencia.get(nmClass);
        StringBuilder sb = executarStrategies(rnsAntes, entidade);

        if (sb.length() == 0) {
            IDAO dao = mapaDaos.get(nmClass);
            if (dao != null) {
                dao.alterar(entidade);
            } else {
                sb.append("Implementação de IDAO não encontrada para a classe: ").append(nmClass);
            }
        } else {
            result.setResposta(sb.toString());
        }
        return result;
    }

    @Override
    public Result excluir(EntidadeDominio entidade) {
        String name = entidade.getClass().getName();
        String mensagem = mapaDaos.get(name).excluir(entidade);
        if (mensagem == null) {
            return result;
        }
        result.setResposta(mensagem);
        return result;
    }

    @Override
    public Result consultar(EntidadeDominio entidade) {
        String nomeClasse = entidade.getClass().getName();
        List<EntidadeDominio> lista = mapaDaos.get(nomeClasse).consultar(entidade);
        Result result = new Result();
        for (EntidadeDominio obj : lista) {
            result.addEntidades(obj);
        }
        return result;
    }

    private StringBuilder executarStrategies(List<IStrategy> strategies, EntidadeDominio entidade) {
        StringBuilder retorno = new StringBuilder();
        if (strategies != null) {
            for (IStrategy rn : strategies) {
                String msg = rn.processar(entidade);
                if (msg != null) {
                    retorno.append(msg);
                }
            }
        }
        return retorno;
    }

    public void carregarMapaAntesPersistencia(List<IStrategy> rnAntesCliente, List<IStrategy> rnAntesCartao,
                                              List<IStrategy> rnAntesEndereco, List<IStrategy> rnAntesUsuario,
                                              List<IStrategy> rnAntesPedido, List<IStrategy> rnAntesProduto) {

        // Regras antes da persistencia do cliente
        rnAntesCliente.add(new ValidadorCpf());
        rnAntesCliente.add(new ValidadorDadosCliente());
        rnAntesCliente.add(new ValidadorExistenciaCliente());

        // Regras antes da persistencia do cart�o
        rnAntesCartao.add(new ValidadorCartao());

        // Regras antes da persistencia do endere�o
        rnAntesEndereco.add(new ValidadorEndereco());

        // Regras antes da persistencia do usuario
        rnAntesUsuario.add(new ValidadorSenha());

        // Regras antes da persistencia do pedido
        rnAntesPedido.add(new ValidadorCadastroPedido());

        // Regras antes da persistencia do produto
        rnAntesProduto.add(new ValidadorProduto());

        mapaAntesPesistencia.put(Cliente.class.getName(), rnAntesCliente);
        mapaAntesPesistencia.put(Cartao.class.getName(), rnAntesCartao);
        mapaAntesPesistencia.put(Endereco.class.getName(), rnAntesEndereco);
        mapaAntesPesistencia.put(Usuario.class.getName(), rnAntesUsuario);
        mapaAntesPesistencia.put(Pedido.class.getName(), rnAntesPedido);
        mapaAntesPesistencia.put(Produto.class.getName(), rnAntesProduto);
    }
}
