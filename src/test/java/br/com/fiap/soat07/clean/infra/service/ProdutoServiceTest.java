package br.com.fiap.soat07.clean.infra.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.soat07.clean.infra.repository.mysql.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {
	
	@InjectMocks
	ProdutoService produtoService;
	 
	@Mock
	ProdutoRepository produtoGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		produtoService = new ProdutoService(produtoGateway);
	}
	
	@Test
	void shouldTestGetCreateProdutoUseCase() {		
				
		assertNotNull(produtoService.getCreateProdutoUseCase());
	}

	@Test
	void shouldTestGetDeleteProdutoUseCase() {		
				
		assertNotNull(produtoService.getDeleteProdutoUseCase());
	}
	
	@Test
	void shouldTestGetUpdateProdutoUseCase() {		
				
		assertNotNull(produtoService.getUpdateProdutoUseCase());
	}

	@Test
	void shouldTestGetSearchProdutoUseCase() {		
				
		assertNotNull(produtoService.getSearchProdutoUseCase());
	}

}
