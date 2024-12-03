package br.com.fiap.soat07.clean.core.usecase.cliente;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

@ExtendWith(MockitoExtension.class)
public class DeleteClienteUseCaseTest {
	
	@InjectMocks
	DeleteClienteUseCase useCase;
	
	@Mock
	ClienteGateway clienteGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		useCase = new DeleteClienteUseCase(clienteGateway);
	}
	
	@Test
	void shouldTestExecuteDelete() {		
		doNothing().when(clienteGateway).delete(any(Cliente.class));
		
		useCase.execute(mockCliente());
		
		verify(clienteGateway, times(1)).delete(any(Cliente.class));
	}
	
	@Test
	void shouldTestExecuteDeleteIllegalArgumentException() {
		
		assertThatThrownBy(() -> useCase.execute(null))
        .isInstanceOf(IllegalArgumentException.class);
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
