package br.com.fiap.soat07.clean.infra.repository.mysql.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "COMBOS")
public class ComboModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String nome;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = true, updatable = true)
	private ClienteModel cliente;

    @OneToOne(mappedBy = "combo")
    private PedidoModel combo;

    @ManyToMany(cascade = { CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "combo_produtos",
            joinColumns = @JoinColumn(name = "comboid", nullable = true, updatable = true),
            inverseJoinColumns = @JoinColumn(name = "produtoid", nullable = true, updatable = true))
	private Set<ProdutoModel> produtos;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime ultimaModificacao;



    public BigDecimal retrieveValor() {
        if (getProdutos() == null)
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);

        return getProdutos().stream()
                .map(ProdutoModel::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public String toString() {
        return "ComboModel{" +
                "cliente=" + cliente +
                ", nome='" + nome + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComboModel that)) return false;
        return Objects.equals(getNome(), that.getNome()) && Objects.equals(getCliente(), that.getCliente()) && Objects.equals(getDataCriacao(), that.getDataCriacao());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome(), getCliente(), getDataCriacao());
    }
}
