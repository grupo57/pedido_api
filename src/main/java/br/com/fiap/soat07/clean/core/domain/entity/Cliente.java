package br.com.fiap.soat07.clean.core.domain.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
	
	private Long id;
	private String nome;	
	private String cpf;	
	private String codigo;
	private LocalDateTime dataCriacao;
	private LocalDateTime ultimaModificacao;
	private LocalDateTime dataExclusao;

}
