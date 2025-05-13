package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.Cartao;
import br.com.fatec.goldenfit.model.Cliente;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Result;
import br.com.fatec.goldenfit.model.enums.Bandeira;
import br.com.fatec.goldenfit.util.Conversao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CadastroCartãoVH implements IViewHelper{

    private static final String NUMERO = "numeroCartao";
    private static final String BANDEIRA = "bandeira";
    private static final String NOME = "nomeImpresso";
    private static final String CVV = "codigoSeguranca";
    private static final String PREFERENCIAL = "preferencial";
    private static final String CLIENTE = "clienteLogado";
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Cadastrando novo cartão");

        Cartao cartao = new Cartao();

        String numero = request.getParameter(NUMERO);
        Bandeira bandeira = Bandeira.valueOf( request.getParameter(BANDEIRA));
        String nome = request.getParameter(NOME);
        String cvv = request.getParameter(CVV);
        String preferencial = request.getParameter(PREFERENCIAL);
        Cliente cliente = (Cliente) request.getSession().getAttribute(CLIENTE);

        cartao = new Cartao(
                numero,
                bandeira,
                nome,
                cvv,
                Conversao.parseStringToBoolean(preferencial),
                cliente);
        return cartao;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/carregarDadosCartoes");
    }
}
