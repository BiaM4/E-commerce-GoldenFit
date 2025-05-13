package br.com.fatec.goldenfit.model;

import br.com.fatec.goldenfit.model.enums.StatusPedido;
import br.com.fatec.goldenfit.model.enums.StatusPedidoItem;
import br.com.fatec.goldenfit.model.enums.TipoCupom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pedido extends EntidadeDominio{
    private Cliente cliente;
    private StatusPedido status;
    private Endereco enderecoEntrega;
    private Endereco enderecoCobranca;
    private Double valorTotal;
    private Double valorFrete;
    private List<FormaPagamento> formasPagamento = new ArrayList<FormaPagamento>();
    private List<PedidoItem> itens;

    public Pedido() {

    }

    public Pedido(Cliente cliente, StatusPedido status, Endereco enderecoEntrega, Endereco enderecoCobranca, List<PedidoItem> itens) {
        if (cliente != null) {
            this.cliente = cliente;
        }
        if (status != null) {
            this.status = status;
        }
        if (enderecoEntrega != null) {
            this.enderecoEntrega = enderecoEntrega;
        }
        if (enderecoCobranca != null) {
            this.enderecoCobranca = enderecoCobranca;
        }
        if (itens != null) {
            this.itens = itens;
        }
    }

    public Pedido(Integer id, Date dtCadastro, Cliente cliente, StatusPedido status, Endereco enderecoEntrega, Endereco enderecoCobranca,
                  Double valorTotal, Double valorFrete, List<FormaPagamento> formasPagamento, List<PedidoItem> itens) {
        super();
        if(id != null) {
            this.setId(id);
        }
        if(dtCadastro != null) {
            this.setDtCadastro(dtCadastro);
        }
        if (cliente != null) {
            this.cliente = cliente;
        }
        if (status != null) {
            this.status = status;
        }
        if (enderecoEntrega != null) {
            this.enderecoEntrega = enderecoEntrega;
        }
        if ( enderecoCobranca != null) {
            this.enderecoCobranca = enderecoCobranca;
        }
        if (valorTotal != null) {
            this.valorTotal = valorTotal;
        }
        if (valorFrete != null) {
            this.valorFrete = valorFrete;
        }
        if (formasPagamento != null) {
            this.formasPagamento = formasPagamento;
        }
        if (itens != null) {
            this.itens = itens;
        }
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public Endereco getEnderecoCobranca() {
        return enderecoCobranca;
    }
    public void setEnderecoCobranca(Endereco enderecoCobranca) {
        this.enderecoCobranca = enderecoCobranca;
    }

    public List<FormaPagamento> getFormasPagamento() {
        return formasPagamento;
    }

    public void setFormasPagamento(List<FormaPagamento> formasPagamento) {
        this.formasPagamento = formasPagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<PedidoItem> getItens() {
        return itens;
    }

    public void setItens(List<PedidoItem> itens) {
        this.itens = itens;
    }

    public Double getValorTotal() {
        if(valorTotal == null) {
            Double total = getValorTotalItens() + getValorFrete();
            total -= getValorTotalDescontos();
            return total;
        }
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getValorFrete() {
        if (valorFrete == null) {
            Double frete = 10d;
            if (itens != null && !itens.isEmpty()) {
                for (PedidoItem item : itens) {
                    frete += item.getQuantidade();
                }
            }
            return frete;
        }
        return valorFrete;
    }

    public void setValorFrete(Double valorFrete) {
        this.valorFrete = valorFrete;
    }

    public Double getValorTotalItens() {
        Double totalItens = 0d;
        if(itens != null && !itens.isEmpty()) {
            for(PedidoItem item : itens) {
                totalItens += item.getValorTotal();
            }
        }
        return totalItens;
    }

    public Double getValorTotalDescontos() {
        Double totalDescontos = 0d;

        if(formasPagamento != null) {
            for(FormaPagamento formaPagamento: formasPagamento) {
                if(formaPagamento.getCupom() != null) {
                    totalDescontos += formaPagamento.getCupom().getValor();
                }
            }
        }
        return totalDescontos;
    }

    public Double getQuantidadeCartoesUsados() {
        Double totalCartoes = 0d;
        if(formasPagamento != null) {
            for(FormaPagamento formaPagamento: formasPagamento) {
                if(formaPagamento.getCartao() != null && formaPagamento.getCartao().getId() > 0) {
                    totalCartoes++;
                }
            }
        }
        return totalCartoes;
    }

    public Double getQuantidadeMaxCartoes() {
        Double quantidadeCartoes = 0d;
        if (getValorTotal() > 0) {
            quantidadeCartoes = Math.floor(getValorTotal() / 10);
        }
        if(quantidadeCartoes == 0 && isUtilizouCupom() && getValorTotal() > 0 ) {
            quantidadeCartoes = 1d;
        }
        return quantidadeCartoes;
    }

    public boolean isUtilizouCartao() {
        if(formasPagamento != null && !formasPagamento.isEmpty()) {
            for(FormaPagamento forma : formasPagamento) {
                if(forma.getCartao() != null && forma.getCartao().getId() > 0 ) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isUtilizouCupom() {
        if(formasPagamento != null && !formasPagamento.isEmpty()) {
            for(FormaPagamento forma : formasPagamento) {
                if(forma.getCupom() != null && forma.getCupom().getId() > 0 ) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isUtilizouCupomPromocional() {
        if(formasPagamento != null && !formasPagamento.isEmpty()) {
            for(FormaPagamento forma : formasPagamento) {
                if(forma.getCupom() != null && forma.getCupom().getTipo().equals(TipoCupom.PROMOCIONAL)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isPossuiTrocaParcial() {
        if(itens != null && !itens.isEmpty()) {
            for(PedidoItem item : itens) {
                if(item.getQuantidadeTroca() < item.getQuantidade()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isPossuiTrocaParcialSolicitada() {
        if(itens != null && !itens.isEmpty()) {
            for(PedidoItem item : itens) {
                if(item.getStatus() != null && item.getStatus().equals(StatusPedidoItem.TROCA_SOLICITADA)
                        && isPossuiTrocaParcial()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isPossuiTrocaParcialAutorizada() {
        if(itens != null && !itens.isEmpty()) {
            for(PedidoItem item : itens) {
                if(item.getStatus() != null && item.getStatus().equals(StatusPedidoItem.TROCA_AUTORIZADA)
                        && isPossuiTrocaParcial()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isPossuiTrocaParcialRealizada() {
        if(itens != null && !itens.isEmpty()) {
            for(PedidoItem item : itens) {
                if(item.getStatus() != null && item.getStatus().equals(StatusPedidoItem.TROCA_REALIZADA)) {
                    return true;
                }
            }
        }
        return false;
    }
}
