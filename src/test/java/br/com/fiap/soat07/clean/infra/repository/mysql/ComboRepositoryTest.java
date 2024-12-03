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
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.domain.enumeration.TipoProdutoEnum;
import br.com.fiap.soat07.clean.infra.repository.mysql.mapper.ComboRepositoryMapper;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.ClienteModel;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.ComboModel;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.ProdutoModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

@ExtendWith(MockitoExtension.class)
public class ComboRepositoryTest {

	@InjectMocks
	ComboRepository comboRepository;
	 
	@Mock
    EntityManager entityManager;
	
	@Mock
	private ClienteRepository clienteRepository;

	@Mock
	private ProdutoRepository produtoRepository;

	ComboRepositoryMapper comboMapper = ComboRepositoryMapper.INSTANCE;
	
	@Mock
	TypedQuery<ComboModel> typedQueryComboModel;
	
	@Mock
	TypedQuery<ClienteModel> typedQueryClienteModel;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		ReflectionTestUtils.setField(comboRepository, "comboMapper", comboMapper);
	}
	
	@Test
	void shouldTest_FindById() {		

		when(entityManager.createQuery(anyString(), eq(ComboModel.class))).thenReturn(typedQueryComboModel);
		when(typedQueryComboModel.setParameter(anyString(), anyLong())).thenReturn(typedQueryComboModel);
		when(typedQueryComboModel.getSingleResult()).thenReturn(mockComboModel());
		
		
		assertEquals(Optional.of(mockComboModel()), comboRepository._findById(1L));
	}
	
	@Test
	void shouldTest_FindByIdNoResultException() {		

		when(entityManager.createQuery(anyString(), eq(ComboModel.class))).thenReturn(typedQueryComboModel);
		when(typedQueryComboModel.setParameter(anyString(), anyLong())).thenReturn(typedQueryComboModel);
		when(typedQueryComboModel.getSingleResult()).thenThrow(NoResultException.class);
		
		
		assertEquals(Optional.empty(), comboRepository._findById(1L));
	}
	
	@Test
	void shouldTestFindById() {		

		when(entityManager.createQuery(anyString(), eq(ComboModel.class))).thenReturn(typedQueryComboModel);
		when(typedQueryComboModel.setParameter(anyString(), anyLong())).thenReturn(typedQueryComboModel);
		when(typedQueryComboModel.getSingleResult()).thenReturn(mockComboModel());
		
		
		assertNotNull(comboRepository.findById(1L));
	}
	
	@Test
	void shouldTestFind() {		

		when(entityManager.createQuery(anyString(), eq(ComboModel.class))).thenReturn(typedQueryComboModel);
		when(typedQueryComboModel.setFirstResult(anyInt())).thenReturn(typedQueryComboModel);
		when(typedQueryComboModel.setMaxResults(anyInt())).thenReturn(typedQueryComboModel);		
		when(typedQueryComboModel.getResultList()).thenReturn(mockComboModelLista());		
		
		assertNotNull(comboRepository.find(1, 10));
	}
	
	@Test
	void shouldTestSaveClientNotFound() {		
		
		when(clienteRepository._findById(anyLong())).thenReturn(Optional.of(mockClienteModel()));
		when(produtoRepository._find(any())).thenReturn(mockProdutoModelSet());
		
		doNothing().when(entityManager).persist(any(ComboModel.class));
		
		Combo combo = mockCombo();
		combo.setId(null);
		comboRepository.save(combo);
		verify( entityManager, times(1)).persist(any(ComboModel.class));
	}
	
	@Test
	void shouldTestSaveClientFound() {		

		when(clienteRepository._findById(anyLong())).thenReturn(Optional.of(mockClienteModel()));
		when(produtoRepository._find(any())).thenReturn(mockProdutoModelSet());
		when(entityManager.createQuery(anyString(), eq(ComboModel.class))).thenReturn(typedQueryComboModel);
		when(typedQueryComboModel.setParameter(anyString(), anyLong())).thenReturn(typedQueryComboModel);
		when(typedQueryComboModel.getSingleResult()).thenReturn(mockComboModel());
		
		when(entityManager.merge(any(ComboModel.class))).thenReturn(mockComboModel());
		
		comboRepository.save(mockCombo());
		verify( entityManager, times(1)).merge(any(ComboModel.class));
	}
	
	private Set<ProdutoModel> mockProdutoModelSet() {
		Set<ProdutoModel> sets = new HashSet<>();
		sets.add(mockProdutoModel());
		return sets;
	}

	private ProdutoModel mockProdutoModel() {
		ProdutoModel model = ProdutoModel.builder()
				.codigo("COD1")
				.id(1L)
				.tipoProduto(TipoProdutoEnum.LANCHE)
				.build();
		return model;
	}

	@Test
	void shouldTestDelete() {		

		when(entityManager.createQuery(anyString(), eq(ComboModel.class))).thenReturn(typedQueryComboModel);
		when(typedQueryComboModel.setParameter(anyString(), any())).thenReturn(typedQueryComboModel);
		when(typedQueryComboModel.getSingleResult()).thenReturn(mockComboModel());
		doNothing().when(entityManager).remove(any(ComboModel.class));
		
		comboRepository.delete(mockCombo());
		verify( entityManager, times(1)).remove(any(ComboModel.class));
	}
	
	@Test
	void shouldTestDeleteProdutosOfCombo() {		

		when(entityManager.createNativeQuery(anyString())).thenReturn(typedQueryComboModel);
		when(typedQueryComboModel.setParameter(anyString(), any())).thenReturn(typedQueryComboModel);
		when(typedQueryComboModel.executeUpdate()).thenReturn(1);
		doNothing().when(entityManager).flush();
		
		comboRepository.deleteProdutosOfCombo(mockCombo(), mockProduto());
		verify( entityManager, times(1)).flush();
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
	
	private List<ComboModel> mockComboModelLista() {
		List<ComboModel> list =  new ArrayList<>();
		list.add(mockComboModel());
		return list;
	}
	
	private ComboModel mockComboModel() {
		ComboModel model = new ComboModel();
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
	private ClienteModel mockClienteModel() {
		ClienteModel model = new ClienteModel();
		return model;
	}
	
}
