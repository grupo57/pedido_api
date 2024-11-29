package br.com.fiap.soat07.clean.infra.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.soat07.clean.infra.repository.mysql.ComboRepository;
import br.com.fiap.soat07.clean.infra.repository.mysql.PedidoRepository;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

	@InjectMocks
	PedidoService pedidoService;
	 
	@Mock
	ComboRepository comboGateway;
	
	@Mock
	PedidoRepository pedidoGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		pedidoService = new PedidoService(comboGateway, pedidoGateway);
	}
	
	@Test
	void shouldTestGetCreatePedidoUseCase() {		
				
		assertNotNull(pedidoService.getCreatePedidoUseCase());
	}

	@Test
	void shouldTestGetDeletePedidoUseCase() {		
				
		assertNotNull(pedidoService.getDeletePedidoUseCase());
	}
	
	@Test
	void shouldTestGetUpdatePedidoUseCase() {		
				
		assertNotNull(pedidoService.getUpdatePedidoUseCase());
	}

	@Test
	void shouldTestGetUpdateStatusPedidoUseCase() {		
				
		assertNotNull(pedidoService.getUpdateStatusPedidoUseCase());
	}
	
	@Test
	void shouldTestGetSearchPedidoUseCase() {		
				
		assertNotNull(pedidoService.getSearchPedidoUseCase());
	}
	
}