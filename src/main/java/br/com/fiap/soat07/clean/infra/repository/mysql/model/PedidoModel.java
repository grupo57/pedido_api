package br.com.fiap.soat07.clean.infra.repository.mysql.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.fiap.soat07.clean.core.domain.enumeration.MetodoPagamentoEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.ProvedorPagamentoEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "PEDIDOS")
public class PedidoModel {
	
	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String codigo;
	
	@Column
	private String nomeCliente;

	@OneToOne
	@JoinColumn(name = "combo_id", referencedColumnName = "id")
	private ComboModel combo;

	@Enumerated(EnumType.STRING)
	@Column
	private PedidoStatusEnum status;

	@Column(precision = 10, scale = 2)
    private BigDecimal valor;
	
	@ManyToMany(cascade = { CascadeType.MERGE})
    @JoinTable(name = "pedido_produtos",
    joinColumns = @JoinColumn(name = "pedidoid", nullable = true, updatable = true),
    inverseJoinColumns = @JoinColumn(name = "produtoid", nullable = true, updatable = true))
	private Set<ProdutoModel> produtos;
	
	@CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime ultimaModificacao;

	@Enumerated(EnumType.STRING)
	@Column(length = 30, nullable = true)
	private ProvedorPagamentoEnum provedor;

	@Enumerated(EnumType.STRING)
	@Column(length = 30, nullable = true)
	private MetodoPagamentoEnum metodo;

	@Enumerated(EnumType.STRING)
	@Column(length = 30, nullable = true)
	private PagamentoStatusEnum pagamentoStatus;

	@Column
	private String transactionCode;

	@Column
	private LocalDateTime transactionTime;


	public BigDecimal retrieveValor() {
		if (getProdutos() == null)
			return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);

		return getProdutos().stream()
				.map(ProdutoModel::getValor)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}


	@Override
	public String toString() {
		Gson GSON = new GsonBuilder() //
        .registerTypeAdapter(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE) //
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY) //
        .setPrettyPrinting() //
        .create();

		return GSON.toJson(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PedidoModel that)) return false;
        return getId() == that.getId() && Objects.equals(getCodigo(), that.getCodigo()) && Objects.equals(getNomeCliente(), that.getNomeCliente()) && getStatus() == that.getStatus();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getCodigo(), getNomeCliente(), getStatus());
	}
}
