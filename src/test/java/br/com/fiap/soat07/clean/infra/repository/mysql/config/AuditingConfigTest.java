package br.com.fiap.soat07.clean.infra.repository.mysql.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class AuditingConfigTest {
	
	@Test
	void shouldTestCreateProduto() {		

		AuditingConfig config = new AuditingConfig();
				
		assertNotNull(config.dateTimeProvider());

	}

}
