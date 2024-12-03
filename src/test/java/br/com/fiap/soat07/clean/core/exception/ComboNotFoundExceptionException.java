package br.com.fiap.soat07.clean.core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ComboNotFoundExceptionException {
	
	@Test
	void shouldTestComboNotFoundException() {
				
		assertEquals(new ComboNotFoundException(1L).getMessage(), "Não foi encontrado um combo com o Id:1");

	}

}
