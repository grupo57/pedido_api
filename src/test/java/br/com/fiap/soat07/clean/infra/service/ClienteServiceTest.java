package br.com.fiap.soat07.clean.infra.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.fiap.soat07.clean.core.usecase.cliente.CreateClienteUseCase;
import br.com.fiap.soat07.clean.core.usecase.cliente.DeleteClienteUseCase;
import br.com.fiap.soat07.clean.core.usecase.cliente.SearchClienteUseCase;
import br.com.fiap.soat07.clean.core.usecase.cliente.UpdateClienteUseCase;
import br.com.fiap.soat07.clean.infra.repository.mysql.ClienteRepository;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {
	
	@InjectMocks
	ClienteService clienteService;
	 
	@Mock
	ClienteRepository clienteGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		clienteService = new ClienteService(clienteGateway);
	}
	
	@Test
	void shouldTestGetCreateClienteUseCase() {		
		ReflectionTestUtils.setField(clienteService, "createClienteUseCase", new CreateClienteUseCase(clienteGateway));
				
		assertNotNull(clienteService.getCreateClienteUseCase());
	}
	
	@Test
	void shouldTestGetCreateClienteUseCaseWithNull() {		
				
		assertNotNull(clienteService.getCreateClienteUseCase());
	}
	
	@Test
	void shouldTestGetDeleteClienteUseCase() {		
		ReflectionTestUtils.setField(clienteService, "deleteClienteUseCase", new DeleteClienteUseCase(clienteGateway));
				
		assertNotNull(clienteService.getDeleteClienteUseCase());
	}
	
	@Test
	void shouldTestGetDeleteClienteUseCaseWithNull() {		
				
		assertNotNull(clienteService.getDeleteClienteUseCase());
	}
	
	@Test
	void shouldTestGetUpdateClienteUseCase() {		
		ReflectionTestUtils.setField(clienteService, "updateClienteUseCase", new UpdateClienteUseCase(clienteGateway));
				
		assertNotNull(clienteService.getUpdateClienteUseCase());
	}
	
	@Test
	void shouldTestGetUpdateClienteUseCaseWithNull() {		
				
		assertNotNull(clienteService.getUpdateClienteUseCase());
	}
	
	@Test
	void shouldTestGetSearchClienteUseCase() {		
		ReflectionTestUtils.setField(clienteService, "searchClienteUseCase", new SearchClienteUseCase(clienteGateway));
				
		assertNotNull(clienteService.getSearchClienteUseCase());
	}
	
	@Test
	void shouldTestGetSearchClienteUseCaseWithNull() {		
				
		assertNotNull(clienteService.getSearchClienteUseCase());
	}
	

}
