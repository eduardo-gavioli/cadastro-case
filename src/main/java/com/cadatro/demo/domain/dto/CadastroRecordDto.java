package com.cadatro.demo.domain.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroRecordDto (@NotBlank String nome, @NotBlank String sobrenome, @NotNull int idade,@NotBlank String pais) {
}
