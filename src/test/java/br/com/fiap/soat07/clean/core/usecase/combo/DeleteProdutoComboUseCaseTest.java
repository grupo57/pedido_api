package br.com.fiap.soat07.clean.core.usecase.combo;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.soat07.clean.core.domain.entity.Cliente;
import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.domain.enumeration.TipoProdutoEnum;
import br.com.fiap.soat07.clean.core.gateway.ComboGateway;

@ExtendWith(MockitoExtension.class)
public class DeleteProdutoComboUseCaseTest {
	
	@InjectMocks
	DeleteProdutoComboUseCase useCase;
	
	@Mock
	ComboGateway comboGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		useCase = new DeleteProdutoComboUseCase(comboGateway);
	}
	
	@Test
	void shouldTestDeleteProdutoCombo() {		
		when(comboGateway.save(any(Combo.class))).thenReturn(mockCombo());

		useCase.execute(mockCombo(), mockProduto());
		verify( comboGateway, times(1)).save(any(Combo.class));
	}
		
	@Test
	void shouldTestDeleteProdutoComboNullIllegalArgumentException() {		
		
		assertThatThrownBy(() -> useCase.execute(null, mockProduto()))
        .isInstanceOf(IllegalArgumentException.class);
	}
	
	@Test
	void shouldTestDeleteProdutoNullComboIllegalArgumentException() {		
		
		assertThatThrownBy(() -> useCase.execute(mockCombo(), null))
        .isInstanceOf(IllegalArgumentException.class);
	}
	
	private Produto mockProduto() {
		Produto produto = new Produto();
		produto.setCodigo("COD1");
		produto.setId(1l);
		produto.setNome("test");
		produto.setTipoProduto(TipoProdutoEnum.LANCHE);
		produto.setValor(new BigDecimal(10.20));
		return produto;
	}
	
	private Combo mockCombo() {
		Combo combo = Combo.builder()
				.cliente(mockCliente())
				.id(1L)
				.nome("test")
				.produtos(mockPrudutoSet())
				.dataCriacao(LocalDateTime.of(2024, 11, 25, 22, 0))
				.build();
		return combo;
	}
	
	private Set<Produto> mockPrudutoSet() {
		Set<Produto> produtos = new HashSet<>();
		produtos.add(mockProduto());
		return produtos;
	}

	private Cliente mockCliente() {
		Cliente cliente = Cliente.builder()
				.cpf("77255446078")
				.id(1L)
				.nome("test")
				.codigo("codigo")
				.dataCriacao(LocalDateTime.of(2024, 11, 25, 22, 0))
				.build();
		return cliente;
	}

}
