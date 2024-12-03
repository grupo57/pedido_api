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
public class CreateClienteUseCaseTest {
	
	
	@InjectMocks
	CreateClienteUseCase useCase;
	
	@Mock
	ClienteGateway clienteGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		useCase = new CreateClienteUseCase(clienteGateway);
	}
	
	@Test
	void shouldTestExecute() {		
		when(clienteGateway.save(any(Cliente.class))).thenReturn(mockCliente());
		
		assertEquals(mockCliente() , useCase.execute(mockClienteDTO()));
	}
	
	@Test
	void shouldTestExecuteIllegalArgumentException() {
		
		assertThatThrownBy(() -> useCase.execute(null))
        .isInstanceOf(IllegalArgumentException.class);
	}
	
	@Test
	void shouldTestExecuteIllegalArgumentExceptionWithMessageForNameNull() {	
		ClienteDTO dto =mockClienteDTO();
		dto.setNome(null);
		assertThatThrownBy(() -> useCase.execute(dto))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Obrigatório o nome");
	}
	
	@Test
	void shouldTestExecuteIllegalArgumentExceptionWithMessageForNameEmpty() {		
		ClienteDTO dto =mockClienteDTO();
		dto.setNome("");
		assertThatThrownBy(() -> useCase.execute(dto))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Obrigatório o nome");
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
