package br.com.fiap.soat07.clean.core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PedidoSituacaoInvalidaExceptionTest {
	
	@Test
	void shouldTestPedidoSituacaoInvalidaException() {
		
		assertEquals(new PedidoSituacaoInvalidaException("teste").getMessage(), "teste");

	}


}
