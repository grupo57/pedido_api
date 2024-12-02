package br.com.fiap.soat07.clean.infra.repository.mysql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
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
import br.com.fiap.soat07.clean.core.domain.enumeration.ProvedorPagamentoEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.TipoProdutoEnum;
import br.com.fiap.soat07.clean.infra.repository.mysql.mapper.PedidoRepositoryMapper;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.ComboModel;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.PedidoModel;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.ProdutoModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

@ExtendWith(MockitoExtension.class)
public class PedidoRepositoryTest {
	
	@InjectMocks
	PedidoRepository repository;
	 
	@Mock
    EntityManager entityManager;
	
	@Mock
    ComboRepository comboRepository;
	
	@Mock
    ProdutoRepository produtoRepository;

	PedidoRepositoryMapper pedidoMapper = PedidoRepositoryMapper.INSTANCE;
	
	@Mock
	TypedQuery<PedidoModel> typedQuery;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		ReflectionTestUtils.setField(repository, "pedidoMapper", pedidoMapper);
	}
	
	@Test
	void shouldTestFindById() {		

		when(entityManager.createQuery(anyString(), eq(PedidoModel.class))).thenReturn(typedQuery);
		when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
		when(typedQuery.getSingleResult()).thenReturn(mockPedidoModel());
		
		
		assertNotNull(repository.findById(1L));
	}
	
	@Test
	void shouldTestFindByIdNoResultException() {		

		when(entityManager.createQuery(anyString(), eq(PedidoModel.class))).thenReturn(typedQuery);
		when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
		when(typedQuery.getSingleResult()).thenThrow(NoResultException.class);
		
		
		assertEquals(Optional.empty(), repository.findById(1L));
	}
	
	@Test
	void shouldTestFindPagamentoByCombo() {		

		when(entityManager.createQuery(anyString(), eq(PedidoModel.class))).thenReturn(typedQuery);
		when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
		when(typedQuery.getSingleResult()).thenReturn(mockPedidoModel());		
		
		assertNotNull(repository.findByCombo(1L));
	}
	
	@Test
	void shouldTestFindPagamentoByComboNoResultException() {		

		when(entityManager.createQuery(anyString(), eq(PedidoModel.class))).thenReturn(typedQuery);
		when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
		when(typedQuery.getSingleResult()).thenThrow(NoResultException.class);		
		
		assertEquals(Optional.empty(), repository.findByCombo(1L));
	}
	
	@Test
	void shouldTestSaveClientNotFound() {	
		
		
		when(comboRepository._findById(anyLong())).thenReturn(Optional.of(mockComboModel()));
		when(produtoRepository._find(any())).thenReturn(mockProdutoModelSet());

		doNothing().when(entityManager).persist(any(PedidoModel.class));
		
		Pedido pedido = mockPedido();
		pedido.setId(null);
		repository.save(pedido);
		verify( entityManager, times(1)).persist(any(PedidoModel.class));
	}
	
	@Test
	void shouldTestSaveClientFound() {	
		
		when(comboRepository._findById(anyLong())).thenReturn(Optional.of(mockComboModel()));
		when(produtoRepository._find(any())).thenReturn(mockProdutoModelSet());

		when(entityManager.createQuery(anyString(), eq(PedidoModel.class))).thenReturn(typedQuery);
		when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
		when(typedQuery.getSingleResult()).thenReturn(mockPedidoModel());
		when(entityManager.merge(any(PedidoModel.class))).thenReturn(mockPedidoModel());
		
		repository.save(mockPedido());
		verify( entityManager, times(1)).merge(any(PedidoModel.class));
	}
	
	@Test
	void shouldTestDelete() {		

		when(entityManager.createQuery(anyString(), eq(PedidoModel.class))).thenReturn(typedQuery);
		when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
		when(typedQuery.getSingleResult()).thenReturn(mockPedidoModel());
		doNothing().when(entityManager).remove(any(PedidoModel.class));
		
		repository.delete(mockPedido());
		verify( entityManager, times(1)).remove(any(PedidoModel.class));
	}
	
	@Test
	void shouldTestDeleteProduto() {		

		when(entityManager.createNativeQuery(anyString())).thenReturn(typedQuery);
		when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
		when(typedQuery.executeUpdate()).thenReturn(1);
		doNothing().when(entityManager).flush();
		
		repository.deleteProduto(mockPedido(), mockProduto());
		verify( entityManager, times(1)).flush();
	}
	
	@Test
	void shouldTestFind() {		

		when(entityManager.createQuery(anyString(), eq(PedidoModel.class))).thenReturn(typedQuery);
		when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
		when(typedQuery.setFirstResult(anyInt())).thenReturn(typedQuery);
		when(typedQuery.setMaxResults(anyInt())).thenReturn(typedQuery);		        
		when(typedQuery.getResultList()).thenReturn(mockPedidoModelLista());
		
		assertNotNull(repository.find(1, 10));
		
	}

	private List<PedidoModel> mockPedidoModelLista() {
		List<PedidoModel> lista = new ArrayList<>();
		lista.add(mockPedidoModel());
		return lista;
	}

	private Set<ProdutoModel> mockProdutoModelSet() {
		Set<ProdutoModel> sets = new HashSet<>();
		sets.add(mockProdutoModel());
		return sets;
	}

	private ProdutoModel mockProdutoModel() {
		ProdutoModel model = new ProdutoModel();
		return model;
	}
	
	private ComboModel mockComboModel() {
		ComboModel model = new ComboModel();
		
		return model;
	}
	
	private Pedido mockPedido() {
		Pedido pedido = Pedido.builder()
				.codigo("COD1")
				.id(1L)
				.nomeCliente("test")
				.status(PedidoStatusEnum.INICIADO)
				.combo(mockCombo())
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
	
	private Produto mockProduto() {
		Produto produto = new Produto();
		produto.setCodigo("COD1");
		produto.setId(1l);
		produto.setNome("test");
		produto.setTipoProduto(TipoProdutoEnum.LANCHE);
		produto.setValor(new BigDecimal(10.20));
		return produto;
	}

	private PedidoModel mockPedidoModel() {
		PedidoModel model = PedidoModel.builder()
				.id(1L)
				.nomeCliente("test")
				.status(PedidoStatusEnum.INICIADO)
				.provedor(ProvedorPagamentoEnum.MERCADO_PAGO)
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
