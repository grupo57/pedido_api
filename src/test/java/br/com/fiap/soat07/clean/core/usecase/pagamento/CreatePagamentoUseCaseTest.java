package br.com.fiap.soat07.clean.core.usecase.pagamento;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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
import br.com.fiap.soat07.clean.core.domain.enumeration.MetodoPagamentoEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.ProvedorPagamentoEnum;
import br.com.fiap.soat07.clean.core.gateway.PagamentoGateway;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;

@ExtendWith(MockitoExtension.class)
public class CreatePagamentoUseCaseTest {
	
	@InjectMocks
	CreatePagamentoUseCase useCase;
	 
	@Mock
	PedidoGateway pedidoGateway;
	
	@Mock
	PagamentoGateway pagamentoGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		useCase = new CreatePagamentoUseCase(pedidoGateway, pagamentoGateway);
	}
	
	@Test
	void shouldTestExecutarPagamento() {		

		when(pedidoGateway.findPagamento(any(Pedido.class))).thenReturn(Optional.of(mockPagamento()));
		when(pagamentoGateway.getSituacao(any(Pagamento.class))).thenReturn(PagamentoStatusEnum.PAGO);
		when(pedidoGateway.save(any(Pedido.class), any(Pagamento.class))).thenReturn(mockPagamento());

		assertNotNull(useCase.executar(mockPedido(), ProvedorPagamentoEnum.MERCADO_PAGO, MetodoPagamentoEnum.CARTAO_CREDITO));
		verify( pedidoGateway, times(1)).save(any(Pedido.class), any(Pagamento.class));
	}
	
	@Test
	void shouldTestExecutarPagamentoPedidoNotFound() {		

		when(pedidoGateway.findPagamento(any(Pedido.class))).thenReturn(Optional.empty());
		when(pedidoGateway.save(any(Pedido.class), any(Pagamento.class))).thenReturn(mockPagamento());
		when(pagamentoGateway.create(any(Pedido.class))).thenReturn(mockPagamento());
		
		assertNotNull(useCase.executar(mockPedido(), ProvedorPagamentoEnum.MERCADO_PAGO, MetodoPagamentoEnum.CARTAO_CREDITO));
		verify( pedidoGateway, times(1)).save(any(Pedido.class), any(Pagamento.class));
	}
	
	private Pagamento mockPagamento() {
		Pagamento pagamento = new Pagamento();
		return pagamento;
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
		
}
