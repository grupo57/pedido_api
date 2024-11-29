package br.com.fiap.soat07.clean.core.usecase.pagamento;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.ProvedorPagamentoEnum;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;

@ExtendWith(MockitoExtension.class)
public class SearchPagamentoUseCaseTest {

	@InjectMocks
	SearchPagamentoUseCase useCase;
	 
	@Mock
	PedidoGateway pedidoGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		useCase = new SearchPagamentoUseCase(pedidoGateway);
	}
	
	@Test
	void shouldTestFindById() {		

		when(pedidoGateway.findPagamento(any(Pedido.class))).thenReturn(Optional.of(mockPagamento()));
		when(pedidoGateway.findById(anyLong())).thenReturn(Optional.of(mockPedido()));

		assertNotNull(useCase.findById(1L));
		verify( pedidoGateway, times(1)).findPagamento(any(Pedido.class));
	}
	
	@Test
	void shouldTestFindByIdPedidoNotFoundException() {		
		
		assertThatThrownBy(() -> useCase.findById(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Obrigatório informar código do pagamento");

	}
	
	@Test
	void shouldTestFindByPedido() {		

		when(pedidoGateway.findPagamento(any(Pedido.class))).thenReturn(Optional.of(mockPagamento()));

		assertNotNull(useCase.findByPedido(mockPedido()));
		verify( pedidoGateway, times(1)).findPagamento(any(Pedido.class));
	}
	
	@Test
	void shouldTestFindByPedidoNotFoundException() {		
		
		assertThatThrownBy(() -> useCase.findByPedido(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Obrigatório informar o pedido");

	}
	
	@Test
	void shouldTestFindByProvedor() {		

		when(pedidoGateway.findPagamento(any(ProvedorPagamentoEnum.class), anyString())).thenReturn(Optional.of(mockPagamento()));

		assertNotNull(useCase.findByProvedor(ProvedorPagamentoEnum.MERCADO_PAGO, "COD1"));
		verify( pedidoGateway, times(1)).findPagamento(any(ProvedorPagamentoEnum.class), anyString());
	}
	
	@Test
	void shouldTestFindByProvedorNotFoundException() {		
		
		assertThatThrownBy(() -> useCase.findByProvedor(null, "COD1"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Obrigatório informar o provedor");

	}
	
	@Test
	void shouldTestFindByProvedorPagameantoIdNullNotFoundException() {		
		
		assertThatThrownBy(() -> useCase.findByProvedor(ProvedorPagamentoEnum.MERCADO_PAGO, null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Obrigatório informar o código de transação de pagamento");

	}
	
	@Test
	void shouldTestFindByProvedorPagameantoIdEmptyNotFoundException() {		
		
		assertThatThrownBy(() -> useCase.findByProvedor(ProvedorPagamentoEnum.MERCADO_PAGO, ""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Obrigatório informar o código de transação de pagamento");

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
	
	private Pagamento mockPagamento() {
		Pagamento pagamento = new Pagamento();
		return pagamento;
	}
		
}
