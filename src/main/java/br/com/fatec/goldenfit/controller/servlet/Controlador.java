package br.com.fatec.goldenfit.controller.servlet;

import br.com.fatec.goldenfit.command.*;
import br.com.fatec.goldenfit.controller.IViewHelper;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/controlador")
public class Controlador extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Map<String,ICommand> commandMap = new HashMap<>();

    public Controlador() {
        commandMap.put("salvar", new SalvarCommand());
        commandMap.put("alterar", new AlterarCommand());
        commandMap.put("excluir", new ExcluirCommand());
        commandMap.put("consultar", new ConsultarCommand());
    }

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String parametroAcao = request.getParameter("acao");
        String nomeViewHelper = request.getParameter("viewHelper");

        if(parametroAcao.equals("Logout")){
            request.getSession().invalidate();
            response.sendRedirect(request.getContextPath() + "/view/index");
        } else {
            try {
                String viewHelper = "br.com.fatec.goldenfit.controller." + nomeViewHelper;
                Class classe = Class.forName(viewHelper);
                IViewHelper iViewHelper = (IViewHelper) classe.newInstance();

                ICommand command = commandMap.get(parametroAcao);
                EntidadeDominio entidade = iViewHelper.getEntidade(request, response);

                Result resultado = command.executar(entidade);
                iViewHelper.setView(resultado, request, response);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new ServletException(e);
            }
        }
    }
}
