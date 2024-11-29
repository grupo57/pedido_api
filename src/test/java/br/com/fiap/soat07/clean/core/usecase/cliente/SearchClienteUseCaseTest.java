package br.com.fiap.soat07.clean.core.usecase.cliente;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
public class SearchClienteUseCaseTest {
	
	@InjectMocks
	SearchClienteUseCase useCase;
	
	@Mock
	ClienteGateway clienteGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		useCase = new SearchClienteUseCase(clienteGateway);
	}
	
	@Test
	void shouldTestFindById() {		
		when(clienteGateway.findById(anyLong())).thenReturn( Optional.of(mockCliente()));
		
		assertEquals(Optional.of(mockCliente()) , useCase.findById(1L));
	}
	
	@Test
	void shouldTestFindByIdIllegalArgumentException() {
		
		assertThatThrownBy(() -> useCase.findById(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Obrigatório informar código do cliente");
	}
	
	@Test
	void shouldTestFindByCpf() {		
		when(clienteGateway.findByCpf(anyString())).thenReturn( Optional.of(mockCliente()));
		
		assertEquals(Optional.of(mockCliente()) , useCase.findByCpf("77255446078"));
	}
	
	@Test
	void shouldTestFindByCpfNullIllegalArgumentException() {
		
		assertThatThrownBy(() -> useCase.findByCpf(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Obrigatório informar o cpf do cliente");
	}
	
	@Test
	void shouldTestFindByCpfEmptyIllegalArgumentException() {
		
		assertThatThrownBy(() -> useCase.findByCpf(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Obrigatório informar o cpf do cliente");
	}
	
	@Test
	void shouldTestFindByCodigo() {		
		when(clienteGateway.findByCodigo(anyString())).thenReturn( Optional.of(mockCliente()));
		
		assertEquals(Optional.of(mockCliente()) , useCase.findByCodigo("COD1"));
	}
	
	@Test
	void shouldTestFindByCodigoNullIllegalArgumentException() {
		
		assertThatThrownBy(() -> useCase.findByCodigo(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Obrigatório informar o código do cliente");
	}
	
	@Test
	void shouldTestFindByCodigoEmptyIllegalArgumentException() {
		
		assertThatThrownBy(() -> useCase.findByCodigo(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Obrigatório informar o código do cliente");
	}
	
	@Test
	void shouldTestFind() {		
		when(clienteGateway.find(anyInt(), anyInt())).thenReturn(mockClienteLista());
		
		assertEquals(mockClienteLista().size() , useCase.find(1, 10).size());
	}
	
	

	@Test
	void shouldTestFindIllegalArgumentException() {
		
		assertThatThrownBy(() -> useCase.findByCodigo(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Obrigatório informar o código do cliente");
	}
	
	
	private Collection<Cliente> mockClienteLista() {
		List<Cliente> clientes = new ArrayList<>();
		clientes.add(mockCliente());
		return clientes;
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
