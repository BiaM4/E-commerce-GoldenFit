package br.com.fatec.goldenfit.business;

import br.com.fatec.goldenfit.business.IStrategy;
import br.com.fatec.goldenfit.model.Cliente;
import br.com.fatec.goldenfit.model.EntidadeDominio;

import java.util.Arrays;

public class ValidadorCpf implements IStrategy {

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
            if (entidadeDominio instanceof Cliente) {
                try {
                    Cliente cliente = (Cliente) entidadeDominio;
                    String cpf = cliente.getCpf().replace("-", "").replace(".", "");
                    String msgPadrao = "CPF inválido\n";

                    if (cpf == null || cpf.isEmpty()) {
                        return "É necessário informar o CPF\n";
                    }

                    if (cpf.length() != 11 || !cpf.matches("\\d{11}")) {
                        return msgPadrao;
                    }

                    if (Arrays.stream(cpf.split("")).allMatch(d -> d.equals(String.valueOf(cpf.charAt(0))))) {
                        return msgPadrao;
                    }

                    int[] cpfArray = cpf.chars().map(Character::getNumericValue).toArray();
                    if (!validarCpf(cpfArray)) {
                        return msgPadrao;
                    }

                } catch (ClassCastException e) {
                    return "Entidade inválida para validação de CPF\n";
                }
            }

            return null;
        }

        private boolean validarCpf(int[] cpfArray) {
            int sum;
            int remainder;

            // Verifica o primeiro dígito verificador
            sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += cpfArray[i] * (10 - i);
            }
            remainder = 11 - (sum % 11);
            if (remainder == 10 || remainder == 11) {
                remainder = 0;
            }
            if (remainder != cpfArray[9]) {
                return false;
            }

            // Verifica o segundo dígito verificador
            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += cpfArray[i] * (11 - i);
            }
            remainder = 11 - (sum % 11);
            if (remainder == 10 || remainder == 11) {
                remainder = 0;
            }
            return remainder == cpfArray[10];
        }
    }