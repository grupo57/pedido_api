package br.com.fiap.soat07.clean.core.usecase.pagamento;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;

@ExtendWith(MockitoExtension.class)
public class UpdatePagamentoUseCaseTest {
	
	@InjectMocks
	UpdatePagamentoUseCase useCase;
	 
	@Mock
	PedidoGateway pedidoGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		useCase = new UpdatePagamentoUseCase(pedidoGateway);
	}
	
	@Test
	void shouldTestExecutarStatusPago() {		

		when(pedidoGateway.save(any(Pedido.class))).thenReturn(mockPedido());
		when(pedidoGateway.save(any(Pedido.class), any(Pagamento.class))).thenReturn(mockPagamento());

		assertNotNull(useCase.executar(mockPedido(), mockPagamento(), PagamentoStatusEnum.PAGO));
		verify( pedidoGateway, times(1)).save(any(Pedido.class));
		verify( pedidoGateway, times(1)).save(any(Pedido.class), any(Pagamento.class));
	}
	
	@Test
	void shouldTestExecutarOutroStatus() {		

		when(pedidoGateway.save(any(Pedido.class))).thenReturn(mockPedido());
		when(pedidoGateway.save(any(Pedido.class), any(Pagamento.class))).thenReturn(mockPagamento());

		assertNotNull(useCase.executar(mockPedido(), mockPagamento(), PagamentoStatusEnum.RECUSADO));
		verify( pedidoGateway, times(1)).save(any(Pedido.class));
		verify( pedidoGateway, times(1)).save(any(Pedido.class), any(Pagamento.class));
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
