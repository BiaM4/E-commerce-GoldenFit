package br.com.fatec.goldenfit.dto;

import br.com.fatec.goldenfit.model.EntidadeDominio;

import java.util.ArrayList;
import java.util.List;

public class GraficoVendasDTO extends EntidadeDominio {
    private static final long serialVersionUID = 1L;
    private String periodo; // categoria do gráfico
    private List<VendaPeriodoDTO> dadosVenda = new ArrayList<VendaPeriodoDTO>();

    public GraficoVendasDTO() {
    }

    public GraficoVendasDTO(VendaPeriodoDTO vendaDto) {
        if(vendaDto != null) {
            this.periodo = vendaDto.getPeriodo();
            this.getDadosVenda().add(vendaDto);
        }
    }

    public String getPeriodo() {
        return periodo;
    }
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    public List<VendaPeriodoDTO> getDadosVenda() {
        return dadosVenda;
    }
    public void setDadosFaturamentoProdutos(List<VendaPeriodoDTO> dadosVendaProdutos) {
        this.dadosVenda = dadosVendaProdutos;
    }

    public Double getValorTotalVendasPeriodo() {
        Double valorTotal = 0d;
        if(dadosVenda != null) {
            for(VendaPeriodoDTO dto : dadosVenda) {
                valorTotal += dto.getValorTotal();
            }
        }
        return valorTotal;
    }

    public Double getQuantidadeTotalVendasPeriodo() {
        Double qtdTotal = 0d;
        if(dadosVenda != null) {
            for(VendaPeriodoDTO dto : dadosVenda) {
                qtdTotal += dto.getQuantidadeTotal();
            }
        }
        return qtdTotal;
    }
}
