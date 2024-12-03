package br.com.fiap.soat07.clean.infra.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.soat07.clean.infra.repository.mysql.ClienteRepository;
import br.com.fiap.soat07.clean.infra.repository.mysql.ComboRepository;
import br.com.fiap.soat07.clean.infra.repository.mysql.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
public class ComboServiceTest {
	
	@InjectMocks
	ComboService comboService;
	 
	@Mock
	ComboRepository comboGateway;
	
	@Mock
	ClienteRepository clienteGateway;
	
	@Mock
	ProdutoRepository produtoGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		comboService = new ComboService(comboGateway, clienteGateway, produtoGateway);
	}
		
	@Test
	void shouldTestGetCreateComboUseCase() {		
				
		assertNotNull(comboService.getCreateComboUseCase());
	}
	
	
	@Test
	void shouldTestGetDeleteComboUseCase() {		
				
		assertNotNull(comboService.getDeleteComboUseCase());
	}
	
	@Test
	void shouldTestGetUpdateComboUseCase() {		
				
		assertNotNull(comboService.getUpdateComboUseCase());
	}
	
	@Test
	void shouldTestGetSearchComboUseCase() {		
				
		assertNotNull(comboService.getSearchComboUseCase());
	}
	

}
