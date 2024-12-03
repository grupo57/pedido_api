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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.fiap.soat07.clean.core.domain.entity.Cliente;
import br.com.fiap.soat07.clean.infra.repository.mysql.mapper.ClienteRepositoryMapper;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.ClienteModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

@ExtendWith(MockitoExtension.class)
public class ClienteRepositoryTest {
	
	@InjectMocks
	ClienteRepository repository;
	 
	@Mock
    EntityManager entityManager;

    ClienteRepositoryMapper mapper = ClienteRepositoryMapper.INSTANCE;
	
	@Mock
	TypedQuery<ClienteModel> typedQuery;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		ReflectionTestUtils.setField(repository, "mapper", mapper);
	}
	
	@Test
	void shouldTest_FindById() {		

		when(entityManager.createQuery(anyString(), eq(ClienteModel.class))).thenReturn(typedQuery);
		when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
		when(typedQuery.getSingleResult()).thenReturn(mockClienteModel());
		
		
		assertNotNull(repository._findById(1L));
	}
	
	@Test
	void shouldTest_FindByIdNoResultException() {		

		when(entityManager.createQuery(anyString(), eq(ClienteModel.class))).thenReturn(typedQuery);
		when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
		when(typedQuery.getSingleResult()).thenThrow(NoResultException.class);
		
		
		assertEquals(Optional.empty(), repository._findById(1L));
	}
	
	@Test
	void shouldTestFindById() {		

		when(entityManager.createQuery(anyString(), eq(ClienteModel.class))).thenReturn(typedQuery);
		when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
		when(typedQuery.getSingleResult()).thenReturn(mockClienteModel());
		
		
		assertNotNull(repository.findById(1L));
	}
	
	@Test
	void shouldTestFindByCpf() {		

		when(entityManager.createQuery(anyString(), eq(ClienteModel.class))).thenReturn(typedQuery);
		when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
		when(typedQuery.getSingleResult()).thenReturn(mockClienteModel());
		
		
		assertNotNull(repository.findByCpf("51468663097"));
	}
	
	@Test
	void shouldTestFindByCpfNoResultException() {		

		when(entityManager.createQuery(anyString(), eq(ClienteModel.class))).thenReturn(typedQuery);
		when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
		when(typedQuery.getSingleResult()).thenThrow(NoResultException.class);
		
		
		assertEquals(Optional.empty(), repository.findByCpf("51468663097"));
	}
	
	@Test
	void shouldTestFindByCodigo() {		

		when(entityManager.createQuery(anyString(), eq(ClienteModel.class))).thenReturn(typedQuery);
		when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
		when(typedQuery.getSingleResult()).thenReturn(mockClienteModel());
		
		
		assertNotNull(repository.findByCodigo("COD1"));
		//verify( produtoGateway, times(1)).save(any(Produto.class));
	}
	
	@Test
	void shouldTestFindByCodigoNoResultException() {		

		when(entityManager.createQuery(anyString(), eq(ClienteModel.class))).thenReturn(typedQuery);
		when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
		when(typedQuery.getSingleResult()).thenThrow(NoResultException.class);
		
		
		assertEquals(Optional.empty(), repository.findByCodigo("COD1"));
		//verify( produtoGateway, times(1)).save(any(Produto.class));
	}
	
	@Test
	void shouldTestSaveClientNotFound() {		

		when(entityManager.createQuery(anyString(), eq(ClienteModel.class))).thenReturn(typedQuery);
		when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
		when(typedQuery.getSingleResult()).thenThrow(NoResultException.class);
		doNothing().when(entityManager).persist(any(ClienteModel.class));
		
		repository.save(mockCliente());
		verify( entityManager, times(1)).persist(any(ClienteModel.class));
	}
	
	@Test
	void shouldTestSaveClientFound() {		

		when(entityManager.createQuery(anyString(), eq(ClienteModel.class))).thenReturn(typedQuery);
		when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
		when(typedQuery.getSingleResult()).thenReturn(mockClienteModel());
		when(entityManager.merge(any(ClienteModel.class))).thenReturn(mockClienteModel());
		
		repository.save(mockCliente());
		verify( entityManager, times(1)).merge(any(ClienteModel.class));
	}
	
	@Test
	void shouldTestDelete() {		

		when(entityManager.createQuery(anyString(), eq(ClienteModel.class))).thenReturn(typedQuery);
		when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
		when(typedQuery.getSingleResult()).thenReturn(mockClienteModel());
		doNothing().when(entityManager).remove(any(ClienteModel.class));
		
		repository.delete(mockCliente());
		verify( entityManager, times(1)).remove(any(ClienteModel.class));
	}
	
	@Test
	void shouldTestFind() {		

		when(entityManager.createQuery(anyString())).thenReturn(typedQuery);
		when(typedQuery.setFirstResult(anyInt())).thenReturn(typedQuery);
		when(typedQuery.setMaxResults(anyInt())).thenReturn(typedQuery);		        
		when(typedQuery.getResultList()).thenReturn(mockClienteModelLista());
		
		assertNotNull(repository.find(1, 10));
		
	}
	
	private List<ClienteModel> mockClienteModelLista() {
		List<ClienteModel> list = new ArrayList<>();
		list.add(mockClienteModel());
		return list;
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
