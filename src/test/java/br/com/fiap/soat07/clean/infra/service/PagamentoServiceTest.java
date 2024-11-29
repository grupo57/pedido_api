package br.com.fiap.soat07.clean.infra.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.soat07.clean.core.gateway.PagamentoGateway;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;

@ExtendWith(MockitoExtension.class)
public class PagamentoServiceTest {
	
	@InjectMocks
	PagamentoService pagamentoService;
	 
	@Mock
	PedidoGateway pedidoGateway;
	
	@Mock
	PagamentoGateway pagamentoGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		pagamentoService = new PagamentoService(pedidoGateway, pagamentoGateway);
	}
	
	@Test
	void shouldTestGetCreatePagamentoUseCase() {		
				
		assertNotNull(pagamentoService.getCreatePagamentoUseCase());
	}

	@Test
	void shouldTestGetRetornoGatewayPagamentoUseCase() {		
				
		assertNotNull(pagamentoService.getRetornoGatewayPagamentoUseCase());
	}
	
	@Test
	void shouldTestGetSearchPagamentoUseCase() {		
				
		assertNotNull(pagamentoService.getSearchPagamentoUseCase());
	}

	@Test
	void shouldTestGetUpdatePagamentoUseCase() {		
				
		assertNotNull(pagamentoService.getUpdatePagamentoUseCase());
	}
	

}
