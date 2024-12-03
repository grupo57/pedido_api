package br.com.fiap.soat07.clean.core.usecase.pedido;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
public class SearchPedidoUseCaseTest {
	
	@InjectMocks
	SearchPedidoUseCase useCase;
	 
	@Mock
	 PedidoGateway pedidoGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		useCase = new SearchPedidoUseCase(pedidoGateway);
	}
	
	@Test
	void shouldTestFindById() {		
		when(pedidoGateway.findById(anyLong())).thenReturn(Optional.ofNullable(mockPedido()));
				
		assertEquals(Optional.ofNullable(mockPedido()), useCase.findById(1L));
		verify( pedidoGateway, times(1)).findById(anyLong());
	}
	
	@Test
	void shouldTestFindByIdIllegalArgumentException() {
		
		assertThatThrownBy(() -> useCase.findById(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Obrigatório informar código do pedido");
	}
	
	@Test
	void shouldTestFind() {		
		when(pedidoGateway.find(anyInt(), anyInt())).thenReturn(mockPedidoLista());
				
		assertEquals(mockPedidoLista(), useCase.find(1, 10));
		verify( pedidoGateway, times(1)).find(anyInt(), anyInt());
	}
		
	private Collection<Pedido> mockPedidoLista() {
		List<Pedido> pedidos = new ArrayList<>();
		pedidos.add(mockPedido());
		return pedidos;
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
