package br.com.fiap.soat07.clean.infra.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.callibrity.logging.test.LogTracker;
import com.callibrity.logging.test.LogTrackerStub;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.soat07.clean.core.domain.entity.Cliente;
import br.com.fiap.soat07.clean.core.usecase.cliente.CreateClienteUseCase;
import br.com.fiap.soat07.clean.core.usecase.cliente.DeleteClienteUseCase;
import br.com.fiap.soat07.clean.core.usecase.cliente.SearchClienteUseCase;
import br.com.fiap.soat07.clean.core.usecase.cliente.UpdateClienteUseCase;
import br.com.fiap.soat07.clean.infra.rest.dto.ClienteDTO;
import br.com.fiap.soat07.clean.infra.rest.mapper.ClienteMapper;
import br.com.fiap.soat07.clean.infra.service.ClienteService;

public class ClienteControllerTest {

	private MockMvc mockMvc;

	@RegisterExtension
	LogTrackerStub logTracker = LogTrackerStub.create().recordForLevel(LogTracker.LogLevel.INFO)
			.recordForType(ClienteController.class);

	@Mock
	private ClienteService clienteService;

	@Mock
	private CreateClienteUseCase createClienteUseCase;
	
	@Mock
    private DeleteClienteUseCase deleteClienteUseCase;
	
	@Mock
    private UpdateClienteUseCase updateClienteUseCase;
	
	@Mock
    private SearchClienteUseCase searchClienteUseCase;

	ClienteMapper mapper = ClienteMapper.INSTANCE;

	AutoCloseable openMocks;

	@BeforeEach
	void setUp() {
		openMocks = MockitoAnnotations.openMocks(this);
		ClienteController clienteController = new ClienteController(clienteService, mapper);
		mockMvc = MockMvcBuilders.standaloneSetup(clienteController)
				.setControllerAdvice(new RestResponseEntityExceptionHandler())
				.addFilter((request, response, chain) -> {
					response.setCharacterEncoding("UTF-8");
					chain.doFilter(request, response);
				}, "/*").build();
	}

	@AfterEach
	void tearDown() throws Exception {
		openMocks.close();
	}

	@Nested
	class CreateCliente {
		@Test
		void shouldTestCreateCliente() throws Exception {
			when(clienteService.getCreateClienteUseCase()).thenReturn(createClienteUseCase);
			when(clienteService.getCreateClienteUseCase().execute(any(ClienteDTO.class))).thenReturn(mockCliente());
	
			mockMvc.perform(
					post("/cliente")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(mockClienteDTO())))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(clienteService, times(2)).getCreateClienteUseCase();
		}
	}
	

	@Nested
	class UpdateCliente {
		@Test
		void shouldTestUpdateCliente() throws Exception {
			when(clienteService.getUpdateClienteUseCase()).thenReturn(updateClienteUseCase);
			when(clienteService.getSearchClienteUseCase()).thenReturn(searchClienteUseCase);
			when(clienteService.getSearchClienteUseCase().findById(anyLong())).thenReturn(Optional.of(mockCliente()));
			
			when(clienteService.getUpdateClienteUseCase().execute(any(Cliente.class), any(ClienteDTO.class))).thenReturn(mockCliente());
	
			mockMvc.perform(
					put("/cliente/{id}", 1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(mockClienteDTO())))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(clienteService, times(2)).getUpdateClienteUseCase();
		}
	}
	
	@Nested
	class DeleteCliente {
		@Test
		void shouldTestDeleteCliente() throws Exception {
			when(clienteService.getDeleteClienteUseCase()).thenReturn(deleteClienteUseCase);
			when(clienteService.getSearchClienteUseCase()).thenReturn(searchClienteUseCase);
			when(clienteService.getSearchClienteUseCase().findById(anyLong())).thenReturn(Optional.of(mockCliente()));
			
			doNothing().when(deleteClienteUseCase).execute(any(Cliente.class));
	
			mockMvc.perform(
					delete("/cliente/{id}", 1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(mockClienteDTO())))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(clienteService, times(1)).getDeleteClienteUseCase();
		}
		
		@Test
		void shouldTestDeleteClienteNotFound() throws Exception {
			when(clienteService.getDeleteClienteUseCase()).thenReturn(deleteClienteUseCase);
			when(clienteService.getSearchClienteUseCase()).thenReturn(searchClienteUseCase);
			when(clienteService.getSearchClienteUseCase().findById(anyLong())).thenReturn(Optional.empty());
			
			doNothing().when(deleteClienteUseCase).execute(any(Cliente.class));
	
			mockMvc.perform(
					delete("/cliente/{id}", 1L)
					.contentType(MediaType.APPLICATION_JSON))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(clienteService, times(0)).getDeleteClienteUseCase();
		}
		
		@Test
		void shouldTestDeleteClienteWithIdNull() throws Exception {
			when(clienteService.getDeleteClienteUseCase()).thenReturn(deleteClienteUseCase);
			when(clienteService.getSearchClienteUseCase()).thenReturn(searchClienteUseCase);
			when(clienteService.getSearchClienteUseCase().findById(anyLong())).thenReturn(Optional.empty());
			
			doNothing().when(deleteClienteUseCase).execute(any(Cliente.class));
	
			Long id = null;
			mockMvc.perform(
					delete("/cliente/{id}", id)
					.contentType(MediaType.APPLICATION_JSON))
	        //        .andDo(print())
					.andExpect(status().isNotFound());
			verify(clienteService, times(0)).getDeleteClienteUseCase();
		}
	}
	
	@Nested
	class FindCliente {
		@Test
		void shouldTestGetCliente() throws Exception {
			when(clienteService.getSearchClienteUseCase()).thenReturn(searchClienteUseCase);
			when(clienteService.getSearchClienteUseCase().findById(anyLong())).thenReturn(Optional.of(mockCliente()));
	
			mockMvc.perform(
					get("/cliente/{id}", 1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(mockClienteDTO())))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(clienteService, times(2)).getSearchClienteUseCase();
		}
		
		@Test
		void shouldTestGetClienteNotFound() throws Exception {
			when(clienteService.getSearchClienteUseCase()).thenReturn(searchClienteUseCase);
			when(clienteService.getSearchClienteUseCase().findById(anyLong())).thenReturn(Optional.empty());
	
			mockMvc.perform(
					get("/cliente/{id}", 1L)
					.contentType(MediaType.APPLICATION_JSON))
	        //        .andDo(print())
					.andExpect(status().isNotFound());
			verify(clienteService, times(2)).getSearchClienteUseCase();
		}
		
		@Test
		void shouldTestGetClienteWithIdNull() throws Exception {
			when(clienteService.getSearchClienteUseCase()).thenReturn(searchClienteUseCase);
			when(clienteService.getSearchClienteUseCase().findById(anyLong())).thenReturn(Optional.empty());
	
			Long id = null;
			mockMvc.perform(
					get("/cliente/{id}", id)
					.contentType(MediaType.APPLICATION_JSON))
	        //        .andDo(print())
					.andExpect(status().isNotFound());
			verify(clienteService, times(1)).getSearchClienteUseCase();
		}
		
		@Test
		void shouldTestGetClienteByCpf() throws Exception {
			when(clienteService.getSearchClienteUseCase()).thenReturn(searchClienteUseCase);
			when(clienteService.getSearchClienteUseCase().findByCpf(anyString())).thenReturn(Optional.of(mockCliente()));
	
			mockMvc.perform(
					get("/cliente/cpf/{cpf}", "51468663097")
					.contentType(MediaType.APPLICATION_JSON))
			//		.content(asJsonString(mockClienteDTO())))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(clienteService, times(2)).getSearchClienteUseCase();
		}
		
		@Test
		void shouldTestGetClientes() throws Exception {
			when(clienteService.getSearchClienteUseCase()).thenReturn(searchClienteUseCase);
			when(searchClienteUseCase.find(anyInt(), anyInt())).thenReturn(mockClienteLista());
	
			mockMvc.perform(
					get("/cliente")
					.contentType(MediaType.APPLICATION_JSON))
			//		.content(asJsonString(mockClienteDTO())))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(clienteService, times(1)).getSearchClienteUseCase();
		}
		
	}
	
	private Collection<Cliente> mockClienteLista() {
		List<Cliente> clientes = new ArrayList<>();
		clientes.add(mockCliente());
		return clientes;
	}
	
	private Cliente mockCliente() {
		Cliente cliente = Cliente.builder().cpf("77255446078").id(1L).nome("test").codigo("codigo")
				.dataCriacao(LocalDateTime.of(2024, 11, 25, 22, 0)).build();
		return cliente;
	}

	private ClienteDTO mockClienteDTO() {
		ClienteDTO dto = ClienteDTO.builder().cpf("77255446078").id(1L).nome("test").codigo("codigo").build();
		return dto;
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
