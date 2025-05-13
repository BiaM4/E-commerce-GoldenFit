package model;

import br.com.fatec.goldenfit.controller.Fachada;
import br.com.fatec.goldenfit.model.*;
import br.com.fatec.goldenfit.model.enums.*;

import java.util.Date;

public class teste {
    public static void main(String[] args)throws Exception{
        Fachada fachada = new Fachada();

        Produto produto = new Produto();

//        Cliente cliente = new Cliente(
//                1,
//                "Maria",
//                "José",
//                "Feminino",
//                new Date(31-10-1999),
//                "064.508.050-05",
//                10,
//                new Telefone(1,
//                        "11",
//                        "4712-2244",
//                        TipoTelefone.RESIDENCIAL),
//                new Telefone(2,
//                        "11",
//                        "91234-5678",
//                        TipoTelefone.CELULAR),
//                new Endereco(1,
//                        "casa",
//                        TipoLogradouro.Rua,
//                        "Rua 1",
//                        "122",
//                        "casa",
//                        TipoResidencia.Casa,
//                        "moderno",
//                        "08440-123",
//                        new Cidade(1,
//                                "Anhembi",
//                                Estado.SAO_PAULO),
//                        "gufgitij",
//                        TipoEndereco.Residencial,
//                        1),
//                new Usuario(2,
//                        "teste1@gmail.com",
//                        "12345678",
//                        false)
//        );
//
//        fachada.salvar(cliente);

         fachada.consultar(produto);
    }
}
