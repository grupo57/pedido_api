package br.com.fiap.soat07.clean.core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PedidoNotFoundExceptionTest {
	
	@Test
	void shouldTestPedidoNotFoundException() {
		
		assertEquals(new PedidoNotFoundException(1L).getMessage(), "Não foi encontrado um pedido com o Id:1");

	}


}
