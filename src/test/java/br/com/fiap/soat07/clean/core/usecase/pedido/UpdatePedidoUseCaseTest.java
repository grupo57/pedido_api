package br.com.fiap.soat07.clean.core.usecase.pedido;

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
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;

@ExtendWith(MockitoExtension.class)
public class UpdatePedidoUseCaseTest {
	
	@InjectMocks
	UpdatePedidoUseCase useCase;
	 
	@Mock
	 PedidoGateway pedidoGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		useCase = new UpdatePedidoUseCase(pedidoGateway);
	}
	
	@Test
	void shouldTestUpdatePedidoUseCase() {		
		when(pedidoGateway.save(any(Pedido.class))).thenReturn(mockPedido());		
		
		assertNotNull(useCase.execute(mockPedido(), mockPedido()));
		verify( pedidoGateway, times(1)).save(any(Pedido.class));
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
