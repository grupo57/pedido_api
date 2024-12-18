package br.com.fiap.soat07.clean.infra.rest.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateComboDTO {
	
	private IdentificacaoDoClienteDTO cliente;
	private List<ProdutoDTO> produtos;

}
