package br.com.fiap.soat07.clean.infra.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.TipoProdutoEnum;
import br.com.fiap.soat07.clean.core.usecase.combo.SearchComboUseCase;
import br.com.fiap.soat07.clean.core.usecase.pedido.CreatePedidoUseCase;
import br.com.fiap.soat07.clean.core.usecase.pedido.DeletePedidoUseCase;
import br.com.fiap.soat07.clean.core.usecase.pedido.SearchPedidoUseCase;
import br.com.fiap.soat07.clean.core.usecase.pedido.UpdatePedidoUseCase;
import br.com.fiap.soat07.clean.core.usecase.pedido.UpdateStatusPedidoUseCase;
import br.com.fiap.soat07.clean.infra.rest.dto.ComboDTO;
import br.com.fiap.soat07.clean.infra.rest.dto.PedidoDTO;
import br.com.fiap.soat07.clean.infra.rest.mapper.PedidoMapper;
import br.com.fiap.soat07.clean.infra.service.ComboService;
import br.com.fiap.soat07.clean.infra.service.PedidoService;

public class PedidoControllerTest {
	
	private MockMvc mockMvc;

	@RegisterExtension
	LogTrackerStub logTracker = LogTrackerStub.create().recordForLevel(LogTracker.LogLevel.INFO)
			.recordForType(ClienteController.class);

	@Mock
	private ComboService comboService;
	
	@Mock
	private PedidoService pedidoService;

	@Mock
	private CreatePedidoUseCase createPedidoUseCase;
	
	@Mock
    private DeletePedidoUseCase deletePedidoUseCase;
	
	@Mock
    private UpdatePedidoUseCase updatePedidoUseCase;
	
	@Mock
    private SearchPedidoUseCase searchPedidoUseCase; 
	
	@Mock
    private SearchComboUseCase searchComboUseCase;
	
	@Mock
	private UpdateStatusPedidoUseCase updateStatusPedidoUseCase;
	
	PedidoMapper mapper = PedidoMapper.INSTANCE;

	AutoCloseable openMocks;

	@BeforeEach
	void setUp() {
		openMocks = MockitoAnnotations.openMocks(this);
		PedidoController comboController = new PedidoController(pedidoService, comboService, mapper);
		mockMvc = MockMvcBuilders.standaloneSetup(comboController)
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
	class CreatePedido {
		@Test
		void shouldTestCreatePedido() throws Exception {
			when(pedidoService.getCreatePedidoUseCase()).thenReturn(createPedidoUseCase);
			when(comboService.getSearchComboUseCase()).thenReturn(searchComboUseCase);
			when(searchComboUseCase.findById(anyLong())).thenReturn(Optional.of( mockCombo()));
			when(createPedidoUseCase.execute(any(Combo.class))).thenReturn(mockPedido());
	
			Long id = 1L;
			mockMvc.perform(
					post("/pedido/{id}", id)
					.contentType(MediaType.APPLICATION_JSON))
			//		.content(asJsonString(mockPedidoDTO())))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(pedidoService, times(1)).getCreatePedidoUseCase();
		}

	}
	

	@Nested
	class UpdatePedido {
		@Test
		void shouldTestUpdatePedido() throws Exception {
			when(pedidoService.getSearchPedidoUseCase()).thenReturn(searchPedidoUseCase);
			when(searchPedidoUseCase.findById(anyLong())).thenReturn(Optional.of(mockPedido()));
			when(pedidoService.getUpdatePedidoUseCase()).thenReturn(updatePedidoUseCase);
			when(updatePedidoUseCase.execute(any(Pedido.class), any(Pedido.class))).thenReturn(mockPedido());			
	
			mockMvc.perform(
					put("/pedido/{id}", 1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(mockPedidoDTO())))
	                //.andDo(print())
					.andExpect(status().isOk());
			verify(pedidoService, times(1)).getUpdatePedidoUseCase();
		}
		
		@Test
		void shouldTestUpdatePedidoEmpty() throws Exception {
			when(pedidoService.getSearchPedidoUseCase()).thenReturn(searchPedidoUseCase);
			when(searchPedidoUseCase.findById(anyLong())).thenReturn(Optional.empty());		
	
			mockMvc.perform(
					put("/pedido/{id}", 1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(mockPedidoDTO())))
	                //.andDo(print())
					.andExpect(status().isBadRequest());
			verify(pedidoService, times(1)).getSearchPedidoUseCase();
		}

		@Test
		void shouldTestUpdatePedidoStatus() throws Exception {
			when(pedidoService.getSearchPedidoUseCase()).thenReturn(searchPedidoUseCase);
			when(searchPedidoUseCase.findById(anyLong())).thenReturn(Optional.of(mockPedido()));
			when(pedidoService.getUpdateStatusPedidoUseCase()).thenReturn(updateStatusPedidoUseCase);
			when(updateStatusPedidoUseCase.execute(any(Pedido.class), any(PedidoStatusEnum.class))).thenReturn(mockPedido());	
			
	
			mockMvc.perform(
					patch("/pedido/{id}", 1L)
					.contentType(MediaType.APPLICATION_JSON)
					.queryParam("status", PedidoStatusEnum.PAGO.name()))
					//.content(asJsonString(mockPedidoDTO())))
	                //.andDo(print())
					.andExpect(status().isOk());
			verify(pedidoService, times(1)).getUpdateStatusPedidoUseCase();
		}
		
		@Test
		void shouldTestUpdatePedidoStatusWithIdNull() throws Exception {
			
			Long id = null;
			mockMvc.perform(
					patch("/pedido/{id}", id)
					.contentType(MediaType.APPLICATION_JSON)
					.queryParam("status", PedidoStatusEnum.PAGO.name()))
					//.content(asJsonString(mockPedidoDTO())))
	                //.andDo(print())
					.andExpect(status().isNotFound());
			
		}
		
		@Test
		void shouldTestUpdatePedidoStatusWithStatusNull() throws Exception {
			
			Long id = 1L;
			mockMvc.perform(
					patch("/pedido/{id}", id)
					.contentType(MediaType.APPLICATION_JSON))
					//.queryParam("status", PedidoStatusEnum.PAGO.name()))
					//.content(asJsonString(mockPedidoDTO())))
	                //.andDo(print())
					.andExpect(status().isBadRequest());
			
		}
		
	}
	
	@Nested
	class DeletePedido {
		@Test
		void shouldTestDeletePedido() throws Exception {
			when(pedidoService.getDeletePedidoUseCase()).thenReturn(deletePedidoUseCase);
			when(pedidoService.getSearchPedidoUseCase()).thenReturn(searchPedidoUseCase);
			when(searchPedidoUseCase.findById(anyLong())).thenReturn(Optional.of(mockPedido()));
			
			doNothing().when(deletePedidoUseCase).execute(any(Pedido.class));
	
			mockMvc.perform(
					delete("/pedido/{id}", 1L)
					.contentType(MediaType.APPLICATION_JSON))
					//.content(asJsonString(mockClienteDTO())))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(pedidoService, times(1)).getDeletePedidoUseCase();
		}
		
		@Test
		void shouldTestDeletePedidoWithIdNull() throws Exception {
			Long id = null;
			mockMvc.perform(
					delete("/pedido/{id}", id)
					.contentType(MediaType.APPLICATION_JSON))
	        //        .andDo(print())
					.andExpect(status().isNotFound());
			verify(comboService, times(0)).getDeleteComboUseCase();
		}
		
		@Test
		void shouldTestDeletePedidoNotFound() throws Exception {
			
			when(pedidoService.getSearchPedidoUseCase()).thenReturn(searchPedidoUseCase);
			when(searchPedidoUseCase.findById(anyLong())).thenReturn(Optional.empty());
			
	
			Long id = null;
			mockMvc.perform(
					delete("/pedido/{id}", id)
					.contentType(MediaType.APPLICATION_JSON))
	        //        .andDo(print())
					.andExpect(status().isNotFound());
			verify(pedidoService, times(0)).getSearchPedidoUseCase();
		}
	}
	
	@Nested
	class FindPedido {
		@Test
		void shouldTestGetPedido() throws Exception {
			when(pedidoService.getSearchPedidoUseCase()).thenReturn(searchPedidoUseCase);
			when(searchPedidoUseCase.findById(anyLong())).thenReturn(Optional.of(mockPedido()));
	
			mockMvc.perform(
					get("/pedido/{id}", 1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(mockComboDTO())))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(pedidoService, times(1)).getSearchPedidoUseCase();
		}
		
		@Test
		void shouldTestGetPedidoNotFound() throws Exception {
			when(pedidoService.getSearchPedidoUseCase()).thenReturn(searchPedidoUseCase);
			when(searchPedidoUseCase.findById(anyLong())).thenReturn(Optional.empty());
	
			mockMvc.perform(
					get("/pedido/{id}", 1L)
					.contentType(MediaType.APPLICATION_JSON))
	        //        .andDo(print())
					.andExpect(status().isNotFound());
			verify(pedidoService, times(1)).getSearchPedidoUseCase();
		}
		
		@Test
		void shouldTestGetPedidoWithIdNull() throws Exception {
			when(pedidoService.getSearchPedidoUseCase()).thenReturn(searchPedidoUseCase);
			when(searchPedidoUseCase.findById(anyLong())).thenReturn(Optional.empty());
	
			Long id = null;
			mockMvc.perform(
					get("/pedido/{id}", id)
					.contentType(MediaType.APPLICATION_JSON))
	        //        .andDo(print())
					.andExpect(status().isNotFound());
			verify(pedidoService, times(0)).getSearchPedidoUseCase();
		}
		
		@Test
		void shouldTestGetPedidos() throws Exception {
			when(pedidoService.getSearchPedidoUseCase()).thenReturn(searchPedidoUseCase);
			when(searchPedidoUseCase.find(anyInt(), anyInt())).thenReturn(mockPedidoLista());
	
			mockMvc.perform(
					get("/pedido")
					.contentType(MediaType.APPLICATION_JSON))
			//		.content(asJsonString(mockClienteDTO())))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(pedidoService, times(1)).getSearchPedidoUseCase();
		}
		
	}
	
	private Collection<Pedido> mockPedidoLista() {
		List<Pedido> pedidos = new ArrayList<>();
		pedidos.add(mockPedido());
		return pedidos;
	}
	
	private Object mockPedidoDTO() {
		PedidoDTO dto = PedidoDTO.builder()
				.id(1L)
				.build();
		return dto;
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
	
	private ComboDTO mockComboDTO() {
		ComboDTO dto = new ComboDTO();
		dto.setId(1L);
		return dto;
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
	
	private Cliente mockCliente() {
		Cliente cliente = Cliente.builder().cpf("77255446078").id(1L).nome("test").codigo("codigo")
				.dataCriacao(LocalDateTime.of(2024, 11, 25, 22, 0)).build();
		return cliente;
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
