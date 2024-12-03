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
public class CreateProdutoUseCaseTest {

	@InjectMocks
	CreateProdutoUseCase useCase;
	 
	@Mock
	 ProdutoGateway produtoGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		useCase = new CreateProdutoUseCase(produtoGateway);
	}
	
	@Test
	void shouldTestCreateProduto() {		

		when(produtoGateway.save(any(Produto.class))).thenReturn(mockProduto());
		
		
		assertNotNull(useCase.execute(mockProdutoDTO()));
		verify( produtoGateway, times(1)).save(any(Produto.class));
	}
	
	@Test
	void shouldTestCreateProdutoNullIllegalArgumentException() {
	
		assertThatThrownBy(() -> useCase.execute(null))
        .isInstanceOf(IllegalArgumentException.class);
	}
	
	@Test
	void shouldTestCreateProdutoCodigoNullIllegalArgumentException() {	
		ProdutoDTO dto = mockProdutoDTO();
		dto.setCodigo(null);
		assertThatThrownBy(() -> useCase.execute(dto))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Obrigatório código");
	}
	
	@Test
	void shouldTestCreateProdutoNomeNullIllegalArgumentException() {	
		ProdutoDTO dto = mockProdutoDTO();
		dto.setNome(null);
		assertThatThrownBy(() -> useCase.execute(dto))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Obrigatório nome");
	}
	
	private Produto mockProduto() {
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
