package com.matheus.clinic.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.Period;

@Data
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String sobrenome;
    private String sexo;
    private LocalDate nascimento;
    private Double idade;
    private Double altura;
    private Double peso;
    private String cpf;
    private Double imc;

    @Transient
    private final static double PESO_IDEAL_HOMEM = 72.7;
    @Transient
    private final static double PESO_IDEAL_MULHER = 62.1;

    public double calcularIMC() {
        return peso / (altura * altura);
    }

    public double calcularIdade() {
        return Period.between(nascimento, LocalDate.now()).getYears();
    }

    public double obterPesoIdeal() {
        if ("Male".equalsIgnoreCase(sexo)) {
            return (PESO_IDEAL_HOMEM * altura) - 58;
        } else {
            return (PESO_IDEAL_MULHER * altura) - 44.7;
        }
    }

    public String obterSituacaoIMC() {
        double imc = calcularIMC();
        if (imc < 17) {
            return "Muito abaixo do peso";
        } else if (imc < 18.5) {
            return "Abaixo do peso";
        } else if (imc < 25) {
            return "Peso normal";
        } else if (imc < 30) {
            return "Acima do peso";
        } else if (imc < 35) {
            return "Obesidade I";
        } else if (imc < 40) {
            return "Obesidade II (severa)";
        } else {
            return "Obesidade III (mórbida)";
        }
    }

    public String obterCpfOfuscado() {
        return "***." + cpf.substring(3, 6) + ".***-**";
    }

    public boolean validarCpf() {
        if (cpf == null || cpf.length() != 11) {
            return false;
        }

        try {
            int[] digits = new int[11];
            for (int i = 0; i < 11; i++) {
                digits[i] = Integer.parseInt(cpf.substring(i, i + 1));
            }

            int v1 = 0, v2 = 0;
            for (int i = 0; i < 9; i++) {
                v1 += digits[i] * (10 - i);
                v2 += digits[i] * (11 - i);
            }
            v1 = (v1 % 11) < 2 ? 0 : 11 - (v1 % 11);
            v2 += v1 * 2;
            v2 = (v2 % 11) < 2 ? 0 : 11 - (v2 % 11);

            return digits[9] == v1 && digits[10] == v2;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
