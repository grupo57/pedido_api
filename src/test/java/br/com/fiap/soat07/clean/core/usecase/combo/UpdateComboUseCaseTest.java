package br.com.fiap.soat07.clean.core.usecase.combo;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import br.com.fiap.soat07.clean.core.exception.ProdutoDuplicadoComboException;
import br.com.fiap.soat07.clean.core.gateway.ComboGateway;

@ExtendWith(MockitoExtension.class)
public class UpdateComboUseCaseTest {
	
	@InjectMocks
	UpdateComboUseCase useCase;
	
	@Mock
	ComboGateway comboGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		useCase = new UpdateComboUseCase(comboGateway);
	}
	
	@Test
	void shouldTestFindById() {		
		when(comboGateway.save(any(Combo.class))).thenReturn(mockCombo());


		assertNotNull(useCase.execute(mockCombo(), mockProdutoLista()));
		verify( comboGateway, times(1)).save(any(Combo.class));
	}
	
	@Test
	void shouldTestFindByIdlIllegalArgumentException() {				
		assertThatThrownBy(() -> useCase.execute(mockCombo(), mockProdutoListaDuplicada()))
        .isInstanceOf(ProdutoDuplicadoComboException.class)
        .hasMessage("Não é permitido adicionar produtos do mesmo tipo no Combo");
	}
	
	private List<Produto> mockProdutoLista() {
		List<Produto> produtos = new ArrayList<>();
		produtos.add(mockProduto());
		return produtos;
	}
	private List<Produto> mockProdutoListaDuplicada() {
		List<Produto> produtos = new ArrayList<>();
		produtos.add(mockProduto());
		produtos.add(mockProduto());
		return produtos;
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
