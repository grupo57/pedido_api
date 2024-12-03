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
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.domain.enumeration.TipoProdutoEnum;
import br.com.fiap.soat07.clean.core.usecase.combo.CreateComboUseCase;
import br.com.fiap.soat07.clean.core.usecase.combo.DeleteComboUseCase;
import br.com.fiap.soat07.clean.core.usecase.combo.SearchComboUseCase;
import br.com.fiap.soat07.clean.core.usecase.combo.UpdateComboUseCase;
import br.com.fiap.soat07.clean.core.usecase.produto.SearchProdutoUseCase;
import br.com.fiap.soat07.clean.infra.rest.dto.ClienteDTO;
import br.com.fiap.soat07.clean.infra.rest.dto.ComboDTO;
import br.com.fiap.soat07.clean.infra.rest.dto.CreateComboDTO;
import br.com.fiap.soat07.clean.infra.rest.mapper.ComboMapper;
import br.com.fiap.soat07.clean.infra.service.ComboService;
import br.com.fiap.soat07.clean.infra.service.ProdutoService;

public class ComboControllerTest {

	private MockMvc mockMvc;

	@RegisterExtension
	LogTrackerStub logTracker = LogTrackerStub.create().recordForLevel(LogTracker.LogLevel.INFO)
			.recordForType(ClienteController.class);

	@Mock
	private ComboService comboService;
	
	@Mock
	private ProdutoService produtoService;

	@Mock
	private CreateComboUseCase createComboUseCase;
	
	@Mock
    private DeleteComboUseCase deleteComboUseCase;
	
	@Mock
    private UpdateComboUseCase updateComboUseCase;
	
	@Mock
    private SearchComboUseCase searchComboUseCase; 
	
	@Mock
	private SearchProdutoUseCase searchProdutoUseCase;
	
	ComboMapper mapper = ComboMapper.INSTANCE;

	AutoCloseable openMocks;

	@BeforeEach
	void setUp() {
		openMocks = MockitoAnnotations.openMocks(this);
		ComboController comboController = new ComboController(comboService, produtoService, mapper);
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
	class CreateCombo {
		@Test
		void shouldTestCreateCombo() throws Exception {
			when(comboService.getCreateComboUseCase()).thenReturn(createComboUseCase);
			when(createComboUseCase.execute(any(CreateComboDTO.class))).thenReturn(mockCombo());
	
			mockMvc.perform(
					post("/combo")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(mockComboDTO())))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(comboService, times(1)).getCreateComboUseCase();
		}

	}
	

	@Nested
	class UpdateCombo {
		@Test
		void shouldTestUpdateCombo() throws Exception {
			when(comboService.getUpdateComboUseCase()).thenReturn(updateComboUseCase);
			when(comboService.getSearchComboUseCase()).thenReturn(searchComboUseCase);
			when(produtoService.getSearchProdutoUseCase()).thenReturn(searchProdutoUseCase);
			when(searchComboUseCase.findById(anyLong())).thenReturn(Optional.of(mockCombo()));
			when(searchProdutoUseCase.findById(anyLong())).thenReturn(Optional.of(mockProduto()));
			
			when(updateComboUseCase.execute(any(Combo.class), any())).thenReturn(mockCombo());
			
	
			mockMvc.perform(
					put("/combo/{id}", 1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(mockProdutoIds())))
	                //.andDo(print())
					.andExpect(status().isOk());
			verify(comboService, times(1)).getUpdateComboUseCase();
		}

		
	}
	
	@Nested
	class DeleteCombo {
		@Test
		void shouldTestDeleteCombo() throws Exception {
			when(comboService.getDeleteComboUseCase()).thenReturn(deleteComboUseCase);
			when(comboService.getSearchComboUseCase()).thenReturn(searchComboUseCase);
			when(comboService.getSearchComboUseCase().findById(anyLong())).thenReturn(Optional.of(mockCombo()));
			
			doNothing().when(deleteComboUseCase).execute(any(Combo.class));
	
			mockMvc.perform(
					delete("/combo/{id}", 1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(mockClienteDTO())))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(comboService, times(1)).getDeleteComboUseCase();
		}
		
		@Test
		void shouldTestDeleteComboNotFound() throws Exception {
			when(comboService.getDeleteComboUseCase()).thenReturn(deleteComboUseCase);
			when(comboService.getSearchComboUseCase()).thenReturn(searchComboUseCase);
			when(comboService.getSearchComboUseCase().findById(anyLong())).thenReturn(Optional.empty());
			
			doNothing().when(deleteComboUseCase).execute(any(Combo.class));
	
			mockMvc.perform(
					delete("/combo/{id}", 1L)
					.contentType(MediaType.APPLICATION_JSON))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(comboService, times(0)).getDeleteComboUseCase();
		}
		
		@Test
		void shouldTestDeleteComboWithIdNull() throws Exception {
			when(comboService.getDeleteComboUseCase()).thenReturn(deleteComboUseCase);
			when(comboService.getSearchComboUseCase()).thenReturn(searchComboUseCase);
			when(comboService.getSearchComboUseCase().findById(anyLong())).thenReturn(Optional.empty());
			
			doNothing().when(deleteComboUseCase).execute(any(Combo.class));
	
			Long id = null;
			mockMvc.perform(
					delete("/combo/{id}", id)
					.contentType(MediaType.APPLICATION_JSON))
	        //        .andDo(print())
					.andExpect(status().isNotFound());
			verify(comboService, times(0)).getDeleteComboUseCase();
		}
	}
	
	@Nested
	class FindCombo {
		@Test
		void shouldTestGetCliente() throws Exception {
			when(comboService.getSearchComboUseCase()).thenReturn(searchComboUseCase);
			when(searchComboUseCase.findById(anyLong())).thenReturn(Optional.of(mockCombo()));
	
			mockMvc.perform(
					get("/combo/{id}", 1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(mockComboDTO())))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(comboService, times(1)).getSearchComboUseCase();
		}
		
		@Test
		void shouldTestGetClienteNotFound() throws Exception {
			when(comboService.getSearchComboUseCase()).thenReturn(searchComboUseCase);
			when(searchComboUseCase.findById(anyLong())).thenReturn(Optional.empty());
	
			mockMvc.perform(
					get("/combo/{id}", 1L)
					.contentType(MediaType.APPLICATION_JSON))
	        //        .andDo(print())
					.andExpect(status().isNotFound());
			verify(comboService, times(1)).getSearchComboUseCase();
		}
		
		@Test
		void shouldTestGetClienteWithIdNull() throws Exception {
			when(comboService.getSearchComboUseCase()).thenReturn(searchComboUseCase);
			when(searchComboUseCase.findById(anyLong())).thenReturn(Optional.empty());
	
			Long id = null;
			mockMvc.perform(
					get("/combo/{id}", id)
					.contentType(MediaType.APPLICATION_JSON))
	        //        .andDo(print())
					.andExpect(status().isNotFound());
			verify(comboService, times(0)).getSearchComboUseCase();
		}
		
		@Test
		void shouldTestGetClientes() throws Exception {
			when(comboService.getSearchComboUseCase()).thenReturn(searchComboUseCase);
			when(searchComboUseCase.find(anyInt(), anyInt())).thenReturn(mockComboLista());
	
			mockMvc.perform(
					get("/combo")
					.contentType(MediaType.APPLICATION_JSON))
			//		.content(asJsonString(mockClienteDTO())))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(comboService, times(1)).getSearchComboUseCase();
		}
		
	}
	
	private Collection<Combo> mockComboLista() {
		List<Combo> combos = new ArrayList<>();
		combos.add(mockCombo());
		return combos;
	}
	
	private List<Long> mockProdutoIds() {
		List<Long> produtoIds = List.of(1L, 2L, 3L);
		return produtoIds;
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
