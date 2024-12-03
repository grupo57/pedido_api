package br.com.fiap.soat07.clean.core.usecase.cliente;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.soat07.clean.core.domain.entity.Cliente;
import br.com.fiap.soat07.clean.core.gateway.ClienteGateway;
import br.com.fiap.soat07.clean.infra.rest.dto.ClienteDTO;

@ExtendWith(MockitoExtension.class)
public class UpdateClienteUseCaseTest {
	
	@InjectMocks
	UpdateClienteUseCase useCase;
	
	@Mock
	ClienteGateway clienteGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		useCase = new UpdateClienteUseCase(clienteGateway);
	}
	
	@Test
	void shouldTestExecuteUpdate() {		
		when(clienteGateway.save(any(Cliente.class))).thenReturn(mockCliente());
		
		assertEquals(mockCliente() , useCase.execute(mockCliente(), mockClienteDTO()));
	}

	
	@Test
	void shouldTestExecuteIllegalArgumentExceptionWithMessageForNameNull() {	
		ClienteDTO dto =mockClienteDTO();
		dto.setId(0L);
		assertThatThrownBy(() -> useCase.execute(mockCliente(), dto))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Obrigatório informar o código do cliente");
	}
	
	private ClienteDTO mockClienteDTO() {
		ClienteDTO dto = ClienteDTO.builder()
				.cpf("77255446078")
				.id(1L)
				.nome("test")
				.codigo("codigo")
				.build();
		return dto;
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
