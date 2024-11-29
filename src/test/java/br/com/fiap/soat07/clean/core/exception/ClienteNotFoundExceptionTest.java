package br.com.fiap.soat07.clean.core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ClienteNotFoundExceptionTest {
	
	@Test
	void shouldTestClienteNotFoundException() {
		
		assertEquals(new ClienteNotFoundException("teste").getMessage(), "Não foi encontrado um cliente com o CPF:teste");
		assertEquals(new ClienteNotFoundException(1L).getMessage(), "Não foi encontrado um cliente com o Id:1");

	}

}
