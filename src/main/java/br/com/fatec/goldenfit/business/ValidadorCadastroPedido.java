package br.com.fatec.goldenfit.business;

import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Pedido;

import java.text.DecimalFormat;

public class ValidadorCadastroPedido implements IStrategy {

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
        Pedido pedido = (Pedido) entidadeDominio;
        StringBuilder erros = new StringBuilder();

        if (!pedido.isUtilizouCartao() && !pedido.isUtilizouCupom()) {
            erros.append("É necessário informar uma forma de pagamento");
        }

        if (pedido.getValorTotal() > 0 && !pedido.isUtilizouCartao()) {
            DecimalFormat fmt = new DecimalFormat("0.00");
            erros.append("É necessário adicionar um cartão ao pedido. Valor pendente de R$"
                    + fmt.format(pedido.getValorTotal()));
        }

        if (pedido.getEnderecoCobranca() == null) {
            erros.append("É necessário informar o endereço de cobrança");
        }

        if (pedido.getEnderecoEntrega() == null) {
            erros.append("É necessário o endereço de entrega");
        }

        if (pedido.getQuantidadeCartoesUsados() > pedido.getQuantidadeMaxCartoes()) {
            erros.append("O pagamento mínimo permitido para cada cartão aplicado é de R$10,00");
        }

        if (erros != null && erros.length() > 0) {
            return erros.toString();
        } else {
            return null;
        }
    }
}
