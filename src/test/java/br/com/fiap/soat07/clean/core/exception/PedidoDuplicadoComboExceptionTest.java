package br.com.fiap.soat07.clean.core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PedidoDuplicadoComboExceptionTest {
	
	@Test
	void shouldTestPedidoDuplicadoComboException() {
		
		assertEquals(new PedidoDuplicadoComboException().getMessage(), "Não é permitido criar dois pedidos a partir do mesmo combo");

	}


}
