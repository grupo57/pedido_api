package br.com.fiap.soat07.clean.infra.repository.api;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.clean.infra.rest.mapper.PedidoMapper;


@ExtendWith(MockitoExtension.class)
public class CozinhaRepositoryTest {
	
	
	@Mock
	private RestTemplate restTemplate;
	
	private PedidoMapper mapper = PedidoMapper.INSTANCE;
	
	@InjectMocks
	private CozinhaRepository repository;
	
	private Object response = new Object();
	
	private String ATENDIMENTO_COZINHA_INCLUIR_PEDIDO_URL = "http://localhost";
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		repository = new CozinhaRepository(restTemplate, mapper);
		ReflectionTestUtils.setField(repository, "ATENDIMENTO_COZINHA_INCLUIR_PEDIDO_URL", ATENDIMENTO_COZINHA_INCLUIR_PEDIDO_URL);
	}
	
	@Test
	void shouldTestSendSuccess() {
		
		when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), eq(Object.class))).thenReturn(mockResponse());

		assertTrue(repository.send(mockPedido()));
		
	}
	
	@Test
	void shouldTestSendException() {
		
		when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), eq(Object.class))).thenThrow(RuntimeException.class);

		assertFalse(repository.send(mockPedido()));
		
	}

	private ResponseEntity<Object> mockResponse() {
		return ResponseEntity.ok(response);
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


}
