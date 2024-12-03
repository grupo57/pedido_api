package br.com.fiap.soat07.clean.core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ProdutoDuplicadoComboExceptionTest {
	
	@Test
	void shouldTestProdutoDuplicadoComboException() {
		
		assertEquals(new ProdutoDuplicadoComboException().getMessage(), "Não é permitido adicionar produtos do mesmo tipo no Combo");

	}

}
