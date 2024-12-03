package br.com.fiap.soat07.clean.core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ProdutoNotFoundExceptionTest {
	
	@Test
	void shouldTestProdutoNotFoundException() {
		
		assertEquals(new ProdutoNotFoundException(1L).getMessage(), "Não foi encontrado um produto com o Id:1");

	}


}
