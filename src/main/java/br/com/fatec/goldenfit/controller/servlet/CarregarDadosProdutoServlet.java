package br.com.fatec.goldenfit.controller.servlet;

import br.com.fatec.goldenfit.command.ConsultarCommand;
import br.com.fatec.goldenfit.model.Categoria;
import br.com.fatec.goldenfit.model.GrupoPrecificacao;
import br.com.fatec.goldenfit.model.Result;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/view/carregarDadosProduto")
public class CarregarDadosProdutoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ConsultarCommand command = new ConsultarCommand();
    private Result resultadoCategorias = new Result();
    private Result resultadoGruposPrecificacao = new Result();
    private Categoria categoria = new Categoria();
    private GrupoPrecificacao grupoPrecificacao = new GrupoPrecificacao();

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        categoria.setPesquisa("");
        resultadoCategorias = command.executar(categoria);

        grupoPrecificacao.setPesquisa("");
        resultadoGruposPrecificacao = command.executar(grupoPrecificacao);

        if(resultadoCategorias.getEntidades() != null && !resultadoCategorias.getEntidades().isEmpty()) {
            request.setAttribute("categorias", resultadoCategorias.getEntidades());
        }
        if(resultadoGruposPrecificacao.getEntidades() != null && !resultadoGruposPrecificacao.getEntidades().isEmpty()) {
            request.setAttribute("gruposPrecificacao", resultadoGruposPrecificacao.getEntidades());
        }

        RequestDispatcher rd = request.getRequestDispatcher("/view/cadastrarProduto.jsp");
        rd.forward(request, response);
    }
}
