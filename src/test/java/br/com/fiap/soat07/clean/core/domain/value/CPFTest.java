package br.com.fiap.soat07.clean.core.domain.value;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import br.com.fiap.soat07.clean.core.exception.CPFInvalidoException;

public class CPFTest {
	
	@Test
	void shouldTestValidNumber() {
		
		assertNotNull(CPF.of("77255446078"));

	}
	
	@Test
	void shouldTestParseNumber() {
		
		assertEquals(Optional.of("772.554.460-78").get().toString(), CPF.parse("77255446078").get().toString());

	}
	
	@Test
	void shouldTestHashCode() {
		
		assertEquals(Optional.of("77255446078").hashCode(), 1061289869);

	}
	
	@Test
	void shouldTestEquals() {
		
		assertTrue(Optional.of("77255446078").equals( Optional.of("77255446078")));

	}
	
	@Test
	void shouldTestNotEquals() {
		
		assertFalse(Optional.of("77255446078").equals(Objects.hash("51468663097")) );

	}
	
	@Test
	void shouldTestCompareToEquals() {
		
		assertEquals(CPF.of("77255446078").compareTo(CPF.of("77255446078")), 0);

	}
	
	@Test
	void shouldTestCompareToNotEquals() {
		
		assertEquals(CPF.of("77255446078").compareTo(CPF.of("51468663097")), 2);

	}
	
	@Test
	void shouldTestParseNumberException() {
		
		assertEquals(Optional.ofNullable(CPF.of(null)), CPF.parse(null));

	}
	
	@Test
	void shouldTestThrowsCPFInvalidoExceptionEqualNumbers() {
		
		assertThatThrownBy(() -> CPF.of("00000000000"))
        .isInstanceOf(CPFInvalidoException.class)
        .hasMessage("Números iguais");
		
		assertThatThrownBy(() -> CPF.of("11111111111"))
        .isInstanceOf(CPFInvalidoException.class)
        .hasMessage("Números iguais");
		
		assertThatThrownBy(() -> CPF.of("22222222222"))
        .isInstanceOf(CPFInvalidoException.class)
        .hasMessage("Números iguais");
		
		
		assertThatThrownBy(() -> CPF.of("33333333333"))
        .isInstanceOf(CPFInvalidoException.class)
        .hasMessage("Números iguais");
		
		assertThatThrownBy(() -> CPF.of("44444444444"))
        .isInstanceOf(CPFInvalidoException.class)
        .hasMessage("Números iguais");
		
		assertThatThrownBy(() -> CPF.of("55555555555"))
        .isInstanceOf(CPFInvalidoException.class)
        .hasMessage("Números iguais");
		
		assertThatThrownBy(() -> CPF.of("66666666666"))
        .isInstanceOf(CPFInvalidoException.class)
        .hasMessage("Números iguais");
		
		assertThatThrownBy(() -> CPF.of("77777777777"))
        .isInstanceOf(CPFInvalidoException.class)
        .hasMessage("Números iguais");
		
		assertThatThrownBy(() -> CPF.of("88888888888"))
        .isInstanceOf(CPFInvalidoException.class)
        .hasMessage("Números iguais");
		
		assertThatThrownBy(() -> CPF.of("99999999999"))
        .isInstanceOf(CPFInvalidoException.class)
        .hasMessage("Números iguais");
	}
	
	@Test
	void shouldTestThrowsCPFInvalidoExceptionDigit() {
		
		assertThatThrownBy(() -> CPF.of("77777777776"))
        .isInstanceOf(CPFInvalidoException.class)
        .hasMessage("dígito verificador inválido");
	}
	
	@Test
	void shouldTestThrowsCPFInvalidoExceptionInvalidNumbers() { 
		
		assertThatThrownBy(() -> CPF.of("777777777-77"))
        .isInstanceOf(CPFInvalidoException.class)
        .hasMessage("O número deve ter exatamente 11 dígitos");
	}
	

}
