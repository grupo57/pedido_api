package br.com.fiap.soat07.clean.core.usecase.produto;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

@ExtendWith(MockitoExtension.class)
public class DeleteProdutoUseCaseTest {

	@InjectMocks
	DeleteProdutoUseCase useCase;
	 
	@Mock
	 ProdutoGateway produtoGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		useCase = new DeleteProdutoUseCase(produtoGateway);
	}
	
	@Test
	void shouldTestDeleteProduto() {		

		when(produtoGateway.save(any(Produto.class))).thenReturn(mockProduto());
				
		useCase.execute(mockProduto());
		verify( produtoGateway, times(1)).save(any(Produto.class));
	}
	
	@Test
	void shouldTestDeleteProdutoNullIllegalArgumentException() {
	
		assertThatThrownBy(() -> useCase.execute(null))
        .isInstanceOf(IllegalArgumentException.class);
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
	
}
