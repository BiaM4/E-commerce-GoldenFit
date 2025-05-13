package br.com.fatec.goldenfit.controller.filter;

import javax.servlet.*;

import java.io.IOException;

public class TempoExecucaoFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter: TempoExecucaoFilter");
        Long antesExecucaoCodigo = System.currentTimeMillis();
        String acao = servletRequest.getParameter("acao");

        filterChain.doFilter(servletRequest, servletResponse);
        Long depoisExecucaoCodigo = System.currentTimeMillis();
        System.out.println("Tempo de execução da ação: " + acao + "-> " + (depoisExecucaoCodigo - antesExecucaoCodigo));
    }
}
