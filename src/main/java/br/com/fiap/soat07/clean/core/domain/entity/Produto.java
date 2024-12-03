package br.com.fiap.soat07.clean.core.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import com.google.gson.Gson;

import br.com.fiap.soat07.clean.Utils;
import br.com.fiap.soat07.clean.core.domain.enumeration.TipoProdutoEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Produto {

	private Long id;
	private String codigo;
	private String nome;
	private BigDecimal valor = BigDecimal.ZERO;
	private TipoProdutoEnum tipoProduto;
	private LocalDateTime dataCriacao;
	private LocalDateTime ultimaModificacao;
	private LocalDateTime dataExclusao;

	public Produto() {
		this.dataCriacao = Utils.now();
	}

	public boolean checkExcluido() {
		return getDataExclusao() != null;
	}

	public String toString() {		
		return new Gson().toJson(this);		
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Produto produto)) return false;
        return Objects.equals(getCodigo(), produto.getCodigo()) && Objects.equals(getNome(), produto.getNome()) && Objects.equals(getValor(), produto.getValor()) && getTipoProduto() == produto.getTipoProduto();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCodigo(), getNome(), getValor(), getTipoProduto());
	}

}
