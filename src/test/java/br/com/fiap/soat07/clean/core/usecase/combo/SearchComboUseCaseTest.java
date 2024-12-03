package br.com.fiap.soat07.clean.core.usecase.combo;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
public class SearchComboUseCaseTest {
	
	@InjectMocks
	SearchComboUseCase useCase;
	
	@Mock
	ComboGateway comboGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		useCase = new SearchComboUseCase(comboGateway);
	}
	
	@Test
	void shouldTestFindById() {		
		when(comboGateway.findById(anyLong())).thenReturn(Optional.of(mockCombo()));


		assertEquals(Optional.of(mockCombo()), useCase.findById(1L));
		verify( comboGateway, times(1)).findById(anyLong());
	}
	
	@Test
	void shouldTestFindByIdlIllegalArgumentException() {				
		assertThatThrownBy(() -> useCase.findById(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Obrigatório informar código do produto");
	}
	
	@Test
	void shouldTestFind() {		
		when(comboGateway.find(anyInt(), anyInt())).thenReturn(mockComboList());


		assertEquals(mockComboList(), useCase.find(1, 10));
		verify( comboGateway, times(1)).find(anyInt(), anyInt());
	}	
	
	private Collection<Combo> mockComboList() {
		List<Combo> combos = new ArrayList<>();
		combos.add(mockCombo());
		return combos;
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
