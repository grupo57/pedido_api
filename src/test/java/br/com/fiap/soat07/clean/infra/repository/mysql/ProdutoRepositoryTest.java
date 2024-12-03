package br.com.fiap.soat07.clean.infra.repository.mysql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.fiap.soat07.clean.core.domain.entity.Cliente;
import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.TipoProdutoEnum;
import br.com.fiap.soat07.clean.infra.repository.mysql.mapper.ProdutoRepositoryMapper;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.ProdutoModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

@ExtendWith(MockitoExtension.class)
public class ProdutoRepositoryTest {
	
	@InjectMocks
	ProdutoRepository repository;
	 
	@Mock
    EntityManager entityManager;

	ProdutoRepositoryMapper produtoMapper = ProdutoRepositoryMapper.INSTANCE;
	
	@Mock
	TypedQuery<ProdutoModel> typedQuery;
	
	@Mock
	Query<ProdutoModel> query;
	
	@Mock
	Session session;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		ReflectionTestUtils.setField(repository, "produtoMapper", produtoMapper);
	}
	
	@Test
	void shouldTestFindByIdNoResultException() {		

		when(entityManager.createQuery(anyString(), eq(ProdutoModel.class))).thenReturn(typedQuery);
		when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
		when(typedQuery.getSingleResult()).thenThrow(NoResultException.class);
		
		
		assertEquals(Optional.empty(), repository.findById(1L));
	}
	
	@Test
	void shouldTestFindById() {		

		when(entityManager.createQuery(anyString(), eq(ProdutoModel.class))).thenReturn(typedQuery);
		when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
		when(typedQuery.getSingleResult()).thenReturn(mockProdutoModel());
		
		
		assertNotNull(repository.findById(1L));
	}
	
	@Test
	void shouldTestSaveProdutoNotFound() {		

		doNothing().when(entityManager).persist(any(ProdutoModel.class));
		
		Produto produto = mockProduto();
		produto.setId(null);
		repository.save(produto);
		verify( entityManager, times(1)).persist(any(ProdutoModel.class));
	}
	
	@Test
	void shouldTestSaveProdutoFound() {		

		when(entityManager.createQuery(anyString(), eq(ProdutoModel.class))).thenReturn(typedQuery);
		when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
		when(typedQuery.getSingleResult()).thenReturn(mockProdutoModel());
		when(entityManager.merge(any(ProdutoModel.class))).thenReturn(mockProdutoModel());
		
		repository.save(mockProduto());
		verify( entityManager, times(1)).merge(any(ProdutoModel.class));
	}
	
	@Test
	void shouldTestDelete() {		

		when(entityManager.createQuery(anyString(), eq(ProdutoModel.class))).thenReturn(typedQuery);
		when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
		when(typedQuery.getSingleResult()).thenReturn(mockProdutoModel());
		when(entityManager.merge(any(ProdutoModel.class))).thenReturn(mockProdutoModel());
		
		repository.delete(mockProduto());
		verify( entityManager, times(1)).merge(any(ProdutoModel.class));
	}
	
	@Test
	void shouldTestFind() {		

		when(entityManager.createQuery(anyString(), eq(ProdutoModel.class))).thenReturn(typedQuery);
		when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);	        
		when(typedQuery.getResultList()).thenReturn(mockProdutoModelLista());
		
		assertNotNull(repository.find(TipoProdutoEnum.LANCHE, 1, 10));
		
	}
	
	@Test
	void shouldTestFindByCombo() {		
		
		
		when(entityManager.unwrap(eq(Session.class))).thenReturn(session);
		when(session.createQuery(anyString(), eq(ProdutoModel.class))).thenReturn(query);
		when(query.setParameter(anyString(), any())).thenReturn(query);	        
		when(query.getResultList()).thenReturn(mockProdutoModelLista());
		
		assertNotNull(repository.find(mockCombo()));
		
	}
	
	@Test
	void shouldTestFindByPedido() {		
		
		
		when(entityManager.unwrap(eq(Session.class))).thenReturn(session);
		when(session.createQuery(anyString(), eq(ProdutoModel.class))).thenReturn(query);
		when(query.setParameter(anyString(), any())).thenReturn(query);	        
		when(query.getResultList()).thenReturn(mockProdutoModelLista());
		
		assertNotNull(repository.find(mockPedido()));
		
	}
	
	@Test
	void shouldTestFindByPage() {		
		
		
		when(entityManager.unwrap(eq(Session.class))).thenReturn(session);
		when(session.createQuery(anyString(), eq(ProdutoModel.class))).thenReturn(query); 
		when(query.setFirstResult(anyInt())).thenReturn(query);
		when(query.setMaxResults(anyInt())).thenReturn(query);	
		when(query.getResultList()).thenReturn(mockProdutoModelLista());
		
		assertNotNull(repository.find(1, 10));
		
	}
	
	@Test
	void shouldTestGetProdutoModelTransformer() {		
				
		assertNotNull(ProdutoRepository.getProdutoModelTransformer());		
	}
	
	private Pedido mockPedido() {
		Pedido pedido = Pedido.builder()
				.codigo("COD1")
				.id(1L)
				.nomeCliente("test")
				.status(PedidoStatusEnum.INICIADO)
				.build();
		return pedido;
	}
	
	private Combo mockCombo() {
		Combo combo = Combo.builder()
				.cliente(mockCliente())
				.id(1L)
				.nome("test")
				.produtos(mockProdutoLista())
				.dataCriacao(LocalDateTime.of(2024, 11, 25, 22, 0))
				.build();
		return combo;
	}
	
	private Set<Produto> mockProdutoLista() {
		Set<Produto> produtos = new HashSet<>();
		produtos.add(mockProduto());
		return produtos;
	}
	
	private List<ProdutoModel> mockProdutoModelLista() {
		List<ProdutoModel> produtos = new ArrayList<>();
		produtos.add(mockProdutoModel());
		return produtos;
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
	
	private ProdutoModel mockProdutoModel() {
		ProdutoModel model = ProdutoModel.builder()
				.codigo("COD1")
				.id(1L)
				.tipoProduto(TipoProdutoEnum.LANCHE)
				.build();
		return model;
	}
	
	private Cliente mockCliente() {
		Cliente cliente = Cliente.builder()
				.cpf("77255446078")
				.id(1L)
				.nome("test")
				.codigo("codigo")
				.dataCriacao(LocalDateTime.of(2024, 11, 25, 22, 0))
				.build();
		return cliente;
	}
	
}
