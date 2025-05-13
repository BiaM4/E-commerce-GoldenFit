package br.com.fatec.goldenfit.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

public class AutorizacaoFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter: AutorizacaoFilter");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String parametroAcao = request.getParameter("acao");

        HttpSession sessao = request.getSession();
        boolean usuarioNaoLogado = (sessao.getAttribute("clienteLogado") == null);
        boolean acaoProtegida = !(parametroAcao.equals("login") || parametroAcao.equals("LoginFormulario") || parametroAcao.equals("IndexFormulario")
                || parametroAcao.equals("CadastraClienteFormulario")
        );

        if (acaoProtegida && usuarioNaoLogado) {
            response.sendRedirect("controlador?acao=LoginFormulario");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
