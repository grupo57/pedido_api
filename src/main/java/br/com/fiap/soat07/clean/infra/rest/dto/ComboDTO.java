package br.com.fiap.soat07.clean.infra.rest.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

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
public class ComboDTO {
	
	private Long id;
//	private String nome;
	private ClienteDTO cliente;	
	private Set<ProdutoDTO> produtos;


	public BigDecimal retrieveValor() {
		if (getProdutos() == null)
			return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);

		return getProdutos().stream()
				.map(ProdutoDTO::retrieveValor)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
