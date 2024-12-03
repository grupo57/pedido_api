package br.com.fiap.soat07.clean.infra.rest.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.soat07.clean.infra.repository.mysql.ClienteRepository;

@ExtendWith(MockitoExtension.class)
public class UseCaseConfigTest {
	
	@Mock
	ClienteRepository repository;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void shouldTestGetUserUseCase() {
		
		UseCaseConfig config = new UseCaseConfig();
		assertNotNull(config.getUserUseCase(repository));
		
	}

}
