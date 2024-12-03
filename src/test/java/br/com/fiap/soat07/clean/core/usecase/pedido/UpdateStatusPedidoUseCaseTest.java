package br.com.fiap.soat07.clean.core.usecase.pedido;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.clean.core.exception.PedidoSituacaoInvalidaException;
import br.com.fiap.soat07.clean.core.gateway.CozinhaGateway;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;

@ExtendWith(MockitoExtension.class)
public class UpdateStatusPedidoUseCaseTest {

	@InjectMocks
	UpdateStatusPedidoUseCase useCase;
	 
	@Mock
	PedidoGateway pedidoGateway;
	
	@Mock
	CozinhaGateway cozinhaGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		useCase = new UpdateStatusPedidoUseCase(pedidoGateway, cozinhaGateway);
	}
	
	@Test
	void shouldTestUpdateStatusPedido() {		

		when(pedidoGateway.save(any(Pedido.class))).thenReturn(mockPedido(PedidoStatusEnum.PAGO));
				
		assertNotNull(useCase.execute(mockPedido(PedidoStatusEnum.PAGO), PedidoStatusEnum.PAGO));
		verify( pedidoGateway, times(1)).save(any(Pedido.class));
	}
	
	@Test
	void shouldTestUpdateStatusPedidoCanceladoIllegalArgumentException() {
		
		assertThatThrownBy(() -> useCase.execute(mockPedido(PedidoStatusEnum.CANCELADO), PedidoStatusEnum.CANCELADO))
        .isInstanceOf(PedidoSituacaoInvalidaException.class)
        .hasMessage("Pedido está cancelado");
	}
	
	@Test
	void shouldTestUpdateStatusPedidoFinalizadoIllegalArgumentException() {
		
		assertThatThrownBy(() -> useCase.execute(mockPedido(PedidoStatusEnum.FINALIZADO), PedidoStatusEnum.FINALIZADO))
        .isInstanceOf(PedidoSituacaoInvalidaException.class)
        .hasMessage("Pedido está finalizado");
	}
	
	@Test
	void shouldTestUpdateStatusPedidoInferiorIllegalArgumentException() {
		
		assertThatThrownBy(() -> useCase.execute(mockPedido(PedidoStatusEnum.PAGO), PedidoStatusEnum.INICIADO))
        .isInstanceOf(PedidoSituacaoInvalidaException.class)
        .hasMessage("Pedido está na situação PAGO e não pode ser atualizado para INICIADO");
	}
		
	private Pedido mockPedido(PedidoStatusEnum status) {
		Pedido pedido = Pedido.builder()
				.codigo("COD1")
				.id(1L)
				.nomeCliente("test")
				.status(status)
				.build();
		return pedido;
	}

}
