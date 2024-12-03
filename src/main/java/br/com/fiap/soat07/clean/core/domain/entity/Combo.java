package br.com.fiap.soat07.clean.core.domain.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Combo {
	
	private Long id;
	private String nome;	
	private Cliente cliente;	
	private Set<Produto> produtos;
	private LocalDateTime dataCriacao;
	private LocalDateTime ultimaModificacao;

	public Combo() {
		this.produtos = new HashSet<>();
	}

	public Set<Produto> getProdutos() {
		if (produtos == null)
			this.produtos = new HashSet<>();
		return produtos;
	}

	public BigDecimal retrieveValor() {
		if (getProdutos() == null)
			return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);

		return getProdutos().stream()
				.map(Produto::getValor)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
