package br.com.fatec.goldenfit.dao;

import br.com.fatec.goldenfit.business.FiltroPesquisaPeriodoGrafico;
import br.com.fatec.goldenfit.dao.db.Db;
import br.com.fatec.goldenfit.dto.GraficoVendasDTO;
import br.com.fatec.goldenfit.dto.VendaPeriodoDTO;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.util.Calculadora;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GraficoVendasDAO extends AbstractDAO {
    @Override
    public String salvar(EntidadeDominio entidade) {
        return null;
    }

    @Override
    public String alterar(EntidadeDominio entidade) {
        return null;
    }

    @Override
    public String excluir(EntidadeDominio entidade) {
        return null;
    }


    @Override
    public List<GraficoVendasDTO> consultar(EntidadeDominio entidade) {
        List<VendaPeriodoDTO> listaDto = new ArrayList<VendaPeriodoDTO>();
        List<GraficoVendasDTO> listaGraficoDto = new ArrayList<GraficoVendasDTO>();
        GraficoVendasDTO graficoDto;
        FiltroPesquisaPeriodoGrafico filtroPesquisa =(FiltroPesquisaPeriodoGrafico) entidade;
        boolean adicionado;
        inicializarConexao();

        try {
            conn = Db.getConnection();
            StringBuilder sql = new StringBuilder("select pro_nome as nome, DATE_FORMAT(CAST(ped_dtCadastro AS DATE), '%m/%Y') "+
                    "as periodo, sum(pei_quantidade) as quantidadeTotal, sum(pei_quantidade * pei_valorunitario) as valorTotal " +
                    "from pedido_item join produto on pro_id = pei_pro_id " +
                    "join pedido on ped_id = pei_ped_id ");
            sql.append("and ped_status like 'ENTREGUE' ");

            if (filtroPesquisa.getDataInicial() != null) {
                sql.append("and CAST(ped_dtCadastro AS DATE) >= ? ");
            }

            if (filtroPesquisa.getDataFinal() != null) {
                sql.append("and CAST(ped_dtCadastro AS DATE) <= ? ");
            }
            sql.append("group by pro_nome, periodo order by YEAR (CAST(ped_dtCadastro AS DATE)), MONTH (CAST(ped_dtCadastro AS DATE))," +
                    "CAST(ped_dtCadastro AS DATE) asc");

            st = conn.prepareStatement(sql.toString()) ;
            setaParametrosQuery(st, filtroPesquisa.getDataInicial(), filtroPesquisa.getDataFinal());
            Long inicioExecucao = System.currentTimeMillis();
            ResultSet rs = st.executeQuery();
            Long terminoExecucao = System.currentTimeMillis();

            System.out.println("Tempo de execução da consulta: "
                    + Calculadora.calculaIntervaloTempo(inicioExecucao, terminoExecucao) + " segundos");

            while (rs.next()) {
                VendaPeriodoDTO dto = new VendaPeriodoDTO(rs.getString("nome"), rs.getString("periodo") ,
                        rs.getDouble("quantidadeTotal"), rs.getDouble("valorTotal"));
                listaDto.add(dto);
            }

            for(VendaPeriodoDTO vendaProdutoDto : listaDto){
                adicionado = false;
                graficoDto = new GraficoVendasDTO();
                if(listaGraficoDto.size() == 0){
                    graficoDto = new GraficoVendasDTO(vendaProdutoDto);
                    listaGraficoDto.add(graficoDto);
                }else{
                    for(GraficoVendasDTO itemGrafico : listaGraficoDto){
                        if(itemGrafico.getPeriodo().equals(vendaProdutoDto.getPeriodo())){
                            itemGrafico.getDadosVenda().add(vendaProdutoDto);
                            adicionado = true;
                            break;
                        }
                    }
                    if(!adicionado){
                        graficoDto.getDadosVenda().add(vendaProdutoDto);
                        graficoDto.setPeriodo(vendaProdutoDto.getPeriodo());
                        listaGraficoDto.add(graficoDto);
                    }
                }
            }

            return listaGraficoDto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            finalizarConexao();
        }
    }
}
