package br.com.fiap.soat07.clean.core.usecase.pedido;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;

@ExtendWith(MockitoExtension.class)
public class DeletePedidoUseCaseTest {
	
	@InjectMocks
	DeletePedidoUseCase useCase;
	 
	@Mock
	 PedidoGateway pedidoGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		useCase = new DeletePedidoUseCase(pedidoGateway);
	}
	
	@Test
	void shouldTestDeletePedido() {		
		doNothing().when(pedidoGateway).delete(any(Pedido.class));
		
		useCase.execute(mockPedido());
		verify( pedidoGateway, times(1)).delete(any(Pedido.class));
	}
	
	@Test
	void shouldTestDeletePedidoIllegalArgumentException() {
		
		assertThatThrownBy(() -> useCase.execute(null))
        .isInstanceOf(IllegalArgumentException.class);
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
