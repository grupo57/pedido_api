package br.com.fiap.soat07.clean.core.usecase.produto;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.TipoProdutoEnum;
import br.com.fiap.soat07.clean.core.gateway.ProdutoGateway;

@ExtendWith(MockitoExtension.class)
public class SearchProdutoUseCaseTest {

	@InjectMocks
	SearchProdutoUseCase useCase;
	 
	@Mock
	ProdutoGateway produtoGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		useCase = new SearchProdutoUseCase(produtoGateway);
	}
	
	@Test
	void shouldTestSearchProdutoFindById() {		

		when(produtoGateway.findById(anyLong())).thenReturn(Optional.of(mockProduto()));		
		
		assertEquals(Optional.of(mockProduto()), useCase.findById(1L));
		verify( produtoGateway, times(1)).findById(anyLong());
	}
	
	@Test
	void shouldTestSearchProdutoFindByIdNullIllegalArgumentException() {
	
		assertThatThrownBy(() -> useCase.findById(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Obrigatório informar código do produto");
	}
	
	@Test
	void shouldTestSearchProdutoFind() {		

		when(produtoGateway.find(anyInt(), anyInt())).thenReturn(mockProdutoLista());		
		
		assertEquals(mockProdutoLista(), useCase.find(1, 10));
		verify( produtoGateway, times(1)).find(anyInt(), anyInt());
	}
	
	
	@Test
	void shouldTestSearchProdutoFindByPedido() {		

		when(produtoGateway.find(any(Pedido.class))).thenReturn(mockProdutoLista());		
		
		assertEquals(mockProdutoLista(), useCase.find(mockPedido()));
		verify( produtoGateway, times(1)).find(any(Pedido.class));
	}
	
	@Test
	void shouldTestSearchProdutoFindByPedidoNullIllegalArgumentException() {
		Pedido pedido = null;
		assertThatThrownBy(() -> useCase.find(pedido))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Obrigatório informar o pedido");
	}
	
	@Test
	void shouldTestSearchProdutoFindByCombo() {		

		when(produtoGateway.find(any(Combo.class))).thenReturn(mockProdutoLista());		
		
		assertEquals(mockProdutoLista(), useCase.find(mockCombo()));
		verify( produtoGateway, times(1)).find(any(Combo.class));
	}
	
	@Test
	void shouldTestSearchProdutoFindByComboNullIllegalArgumentException() {
		Combo combo = null;
		assertThatThrownBy(() -> useCase.find(combo))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Obrigatório informar o combo");
	}
	
	@Test
	void shouldTestSearchProdutoFindByTipo() {		

		when(produtoGateway.find(any(TipoProdutoEnum.class), anyInt(), anyInt())).thenReturn(mockProdutoLista());		
		
		assertEquals(mockProdutoLista(), useCase.find(TipoProdutoEnum.LANCHE, 1, 10));
		verify( produtoGateway, times(1)).find(any(TipoProdutoEnum.class), anyInt(), anyInt());
	}
	
	@Test
	void shouldTestSearchProdutoFindByTipoNullIllegalArgumentException() {

		assertThatThrownBy(() -> useCase.find(null, 1, 10))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Obrigatório informar o tipo do pedido");
	}
	
	private Combo mockCombo() {
		Combo combo = Combo.builder()
				.cliente(mockCliente())
				.id(1L)
				.nome("test")
				.produtos(mockProdutoSet())
				.dataCriacao(LocalDateTime.of(2024, 11, 25, 22, 0))
				.build();
		return combo;
	}
	
	private Set<Produto> mockProdutoSet() {
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
	
	private List<Produto> mockProdutoLista() {
		List<Produto> produtos = new ArrayList<>();
		produtos.add(mockProduto());
		return produtos;
	}
	
	private Pedido mockPedido() {
		Pedido pedido = Pedido.builder()
				.codigo("COD1")
				.id(1L)
				.nomeCliente("test")
				.status(PedidoStatusEnum.INICIADO)
				.build();
		return pedido;
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
	
}
