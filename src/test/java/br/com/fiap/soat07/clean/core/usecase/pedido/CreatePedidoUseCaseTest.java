package br.com.fiap.soat07.clean.core.usecase.pedido;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
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
import br.com.fiap.soat07.clean.core.exception.PedidoDuplicadoComboException;
import br.com.fiap.soat07.clean.core.exception.ProdutoDuplicadoComboException;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;

@ExtendWith(MockitoExtension.class)
public class CreatePedidoUseCaseTest {
	
	@InjectMocks
	CreatePedidoUseCase useCase;
	 
	@Mock
	 PedidoGateway pedidoGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		useCase = new CreatePedidoUseCase(pedidoGateway);
	}
	
	@Test
	void shouldTestCreatePedido() {		
		when(pedidoGateway.findByCombo(anyLong())).thenReturn(Optional.ofNullable(null));
		when(pedidoGateway.save(any(Pedido.class))).thenReturn(mockPedido());
		
		
		assertNotNull(useCase.execute(mockCombo()));
		verify( pedidoGateway, times(1)).save(any(Pedido.class));
	}
	
	@Test
	void shouldTestCreatePedidoDuplicadoIllegalArgumentException() {
		when(pedidoGateway.findByCombo(anyLong())).thenReturn(Optional.ofNullable(mockPedido()));
		
		assertThatThrownBy(() -> useCase.execute(mockCombo()))
        .isInstanceOf(PedidoDuplicadoComboException.class)
        .hasMessage("Não é permitido criar dois pedidos a partir do mesmo combo");
	}
	
	@Test
	void shouldTestCreatePedidoComboDuplicadoIllegalArgumentException() {				
		assertThatThrownBy(() -> useCase.execute(mockComboDuplicado()))
        .isInstanceOf(ProdutoDuplicadoComboException.class)
        .hasMessage("Não é permitido adicionar produtos do mesmo tipo no Combo");
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

	private Produto mockProduto(Long id, String cod) {
		Produto produto = new Produto();
		produto.setCodigo(cod);
		produto.setId(id);
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
	
	private Combo mockComboDuplicado() {
		Combo combo = Combo.builder()
				.cliente(mockCliente())
				.id(1L)
				.nome("test")
				.produtos(mockPrudutoSetDuplicado())
				.dataCriacao(LocalDateTime.of(2024, 11, 25, 22, 0))
				.build();
		return combo;
	}
	
	private Set<Produto> mockPrudutoSet() {
		Set<Produto> produtos = new HashSet<>();
		produtos.add(mockProduto(1L, "COD1"));
		return produtos;
	}
	
	private Set<Produto> mockPrudutoSetDuplicado() {
		Set<Produto> produtos = new HashSet<>();
		produtos.add(mockProduto(1L, "COD1"));
		produtos.add(mockProduto(2L, "COD2"));
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
