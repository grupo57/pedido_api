package br.com.fiap.soat07.clean.core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PagamentoNotFoundExceptionTest {
	
	@Test
	void shouldTestPagamentoNotFoundException() {
		
		assertEquals(new PagamentoNotFoundException("teste").getMessage(), "Não foi encontrado um pagamento com o Id:teste");

	}

}
