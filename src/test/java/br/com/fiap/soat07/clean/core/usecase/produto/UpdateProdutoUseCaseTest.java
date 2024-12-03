package br.com.fiap.soat07.clean.core.usecase.produto;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.domain.enumeration.TipoProdutoEnum;
import br.com.fiap.soat07.clean.core.gateway.ProdutoGateway;
import br.com.fiap.soat07.clean.infra.rest.dto.ProdutoDTO;

@ExtendWith(MockitoExtension.class)
public class UpdateProdutoUseCaseTest {

	@InjectMocks
	UpdateProdutoUseCase useCase;
	 
	@Mock
	 ProdutoGateway produtoGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		useCase = new UpdateProdutoUseCase(produtoGateway);
	}
	
	@Test
	void shouldTestCreateProduto() {		

		when(produtoGateway.save(any(Produto.class))).thenReturn(mockProduto());		
		
		assertNotNull(useCase.execute(mockProduto(), mockProdutoDTO()));
		verify( produtoGateway, times(1)).save(any(Produto.class));
	}
	
	private
	Produto mockProduto() {
		Produto produto = new Produto();
		produto.setCodigo("COD1");
		produto.setId(1l);
		produto.setNome("test");
		produto.setTipoProduto(TipoProdutoEnum.LANCHE);
		produto.setValor(new BigDecimal(10.20));
		return produto;
	}
	
	private ProdutoDTO mockProdutoDTO() {
		ProdutoDTO dto = new ProdutoDTO();
		dto.setCodigo("COD1");
		dto.setId(1l);
		dto.setNome("test");
		dto.setTipoProduto(TipoProdutoEnum.LANCHE);
		dto.setValor(new BigDecimal(10.20));
		return dto;
	}
}
