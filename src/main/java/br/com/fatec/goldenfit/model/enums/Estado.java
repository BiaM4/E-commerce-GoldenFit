package br.com.fatec.goldenfit.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum Estado {
    AMAZONAS(3, "Amazonas", "AM", "Brasil"),
    ALAGOAS(2, "Alagoas", "AL", "Brasil"),
    ACRE(1, "Acre", "AC", "Brasil"),
    AMAPA(4, "Amapá", "AP", "Brasil"),
    BAHIA(5, "Bahia", "BA", "Brasil"),
    PARA(14, "Pará", "PA", "Brasil"),
    MATO_GROSSO(13, "Mato Grosso", "MT", "Brasil"),
    MINAS_GERAIS(11, "Minas Gerais", "MG", "Brasil"),
    MATO_GROSSO_DO_SUL(12, "Mato Grosso do Sul", "MS", "Brasil"),
    GOIAS(9, "Goiás", "GO", "Brasil"),
    MARANHAO(10, "Maranhão", "MA", "Brasil"),
    RIO_GRANDE_DO_SUL(23, "Rio Grande do Sul", "RS", "Brasil"),
    TOCANTINS(27, "Tocantins", "TO", "Brasil"),
    PIAUI(17, "Piauí", "PI", "Brasil"),
    SAO_PAULO(26, "São Paulo", "SP", "Brasil"),
    RONDONIA(21, "Rondônia", "RO", "Brasil"),
    RORAIMA(22, "Roraima", "RR", "Brasil"),
    PARANA(18, "Paraná", "PR", "Brasil"),
    CEARA(6, "Ceará", "CE", "Brasil"),
    PERNAMBUCO(16, "Pernambuco", "PE", "Brasil"),
    SANTA_CATARINA(24, "Santa Catarina", "SC", "Brasil"),
    PARAIBA(15, "Paraíba", "PB", "Brasil"),
    RIO_GRANDE_DO_NORTE(20, "Rio Grande do Norte", "RN", "Brasil"),
    ESPIRITO_SANTO(8, "Espírito Santo", "ES", "Brasil"),
    RIO_DE_JANEIRO(19, "Rio de Janeiro", "RJ", "Brasil"),
    SERGIPE(25, "Sergipe", "SE", "Brasil"),
    DISTRITO_FEDERAL(7, "Distrito Federal", "DF", "Brasil");

    private int codigo;
    private String nome;
    private String sigla;
    private String pais;

    Estado(int codigo, String nome, String sigla, String pais) {
        this.codigo = codigo;
        this.nome = nome;
        this.sigla = sigla;
        this.pais = pais;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }
    public String getSigla() {
        return sigla;
    }

    public String getPais() {
        return pais;
    }

    public static Estado getByCodigo(int id) {
        for (Estado estado : values()) {
            if (estado.getCodigo() == id) {
                return estado;
            }
        }
        return null;
    }

    public static Estado getBySigla(String sigla) {
        for (Estado estado : values()) {
            if (estado.getSigla().equals(sigla)) {
                return estado;
            }
        }
        return null;
    }

    public static List<Estado> getEstadosOrdenados() {
        List <Estado> estados =  Arrays.asList(Estado.values());;

        Collections.sort(estados, new Comparator<Estado>() {
            @Override
            public int compare(Estado o1, Estado o2) {
                return o1.getNome().compareTo(o2.getNome());
            }
        });
        return estados;
    }
}
