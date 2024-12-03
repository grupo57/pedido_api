package br.com.fiap.soat07.clean.core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CPFInvalidoExceptionTest {
	
	@Test
	void shouldTestCPFInvalidoException() {
		
		assertEquals(new CPFInvalidoException("teste").getMessage(), "teste");
		assertEquals(new CPFInvalidoException("teste").getValor(), "teste");
	}

}
