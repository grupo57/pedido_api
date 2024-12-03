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
import java.util.List;
import java.util.Optional;

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
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.domain.enumeration.TipoProdutoEnum;
import br.com.fiap.soat07.clean.core.usecase.produto.CreateProdutoUseCase;
import br.com.fiap.soat07.clean.core.usecase.produto.DeleteProdutoUseCase;
import br.com.fiap.soat07.clean.core.usecase.produto.SearchProdutoUseCase;
import br.com.fiap.soat07.clean.core.usecase.produto.UpdateProdutoUseCase;
import br.com.fiap.soat07.clean.infra.rest.dto.ClienteDTO;
import br.com.fiap.soat07.clean.infra.rest.dto.ProdutoDTO;
import br.com.fiap.soat07.clean.infra.rest.mapper.ProdutoMapper;
import br.com.fiap.soat07.clean.infra.service.ProdutoService;

public class ProdutoControllerTest {
	
	private MockMvc mockMvc;

	@RegisterExtension
	LogTrackerStub logTracker = LogTrackerStub.create().recordForLevel(LogTracker.LogLevel.INFO)
			.recordForType(ClienteController.class);
	
	@Mock
	private ProdutoService produtoService;

	@Mock
	private CreateProdutoUseCase createProdutoUseCase;
	
	@Mock
    private DeleteProdutoUseCase deleteProdutoUseCase;
	
	@Mock
    private UpdateProdutoUseCase updateProdutoUseCase;
	
	@Mock
	private SearchProdutoUseCase searchProdutoUseCase;
	
	ProdutoMapper mapper = ProdutoMapper.INSTANCE;

	AutoCloseable openMocks;

	@BeforeEach
	void setUp() {
		openMocks = MockitoAnnotations.openMocks(this);
		ProdutoController produtoController = new ProdutoController(produtoService, mapper);
		mockMvc = MockMvcBuilders.standaloneSetup(produtoController)
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
	class CreateProduto {
		@Test
		void shouldTestCreateProduto() throws Exception {
			when(produtoService.getCreateProdutoUseCase()).thenReturn(createProdutoUseCase);
			when(createProdutoUseCase.execute(any(ProdutoDTO.class))).thenReturn(mockProduto());
	
			mockMvc.perform(
					post("/produto")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(mockProdutoDTO())))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(produtoService, times(1)).getCreateProdutoUseCase();
		}

	}
	

	@Nested
	class UpdateProduto {
		@Test
		void shouldTestUpdateProduto() throws Exception {
			when(produtoService.getUpdateProdutoUseCase()).thenReturn(updateProdutoUseCase);
			when(produtoService.getSearchProdutoUseCase()).thenReturn(searchProdutoUseCase);
			when(searchProdutoUseCase.findById(anyLong())).thenReturn(Optional.of(mockProduto()));
	
			
			when(updateProdutoUseCase.execute(any(Produto.class), any())).thenReturn(mockProduto());
			
	
			mockMvc.perform(
					put("/produto/{id}", 1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(mockProdutoDTO())))
	                //.andDo(print())
					.andExpect(status().isOk());
			verify(produtoService, times(1)).getUpdateProdutoUseCase();
		}

		
	}
	
	@Nested
	class DeleteProduto {
		@Test
		void shouldTestDeleteProduto() throws Exception {
			when(produtoService.getDeleteProdutoUseCase()).thenReturn(deleteProdutoUseCase);
			when(produtoService.getSearchProdutoUseCase()).thenReturn(searchProdutoUseCase);
			when(produtoService.getSearchProdutoUseCase().findById(anyLong())).thenReturn(Optional.of(mockProduto()));
			
			doNothing().when(deleteProdutoUseCase).execute(any(Produto.class));
	
			mockMvc.perform(
					delete("/produto/{id}", 1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(mockClienteDTO())))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(produtoService, times(1)).getDeleteProdutoUseCase();
		}
		
		@Test
		void shouldTestDeleteProdutoNotFound() throws Exception {
			when(produtoService.getDeleteProdutoUseCase()).thenReturn(deleteProdutoUseCase);
			when(produtoService.getSearchProdutoUseCase()).thenReturn(searchProdutoUseCase);
			when(produtoService.getSearchProdutoUseCase().findById(anyLong())).thenReturn(Optional.empty());
			
			doNothing().when(deleteProdutoUseCase).execute(any(Produto.class));
	
			mockMvc.perform(
					delete("/produto/{id}", 1L)
					.contentType(MediaType.APPLICATION_JSON))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(produtoService, times(0)).getDeleteProdutoUseCase();
		}
		
		@Test
		void shouldTestDeleteProdutoWithIdNull() throws Exception {
			when(produtoService.getDeleteProdutoUseCase()).thenReturn(deleteProdutoUseCase);
			when(produtoService.getSearchProdutoUseCase()).thenReturn(searchProdutoUseCase);
			when(produtoService.getSearchProdutoUseCase().findById(anyLong())).thenReturn(Optional.empty());
			
			doNothing().when(deleteProdutoUseCase).execute(any(Produto.class));
	
			Long id = null;
			mockMvc.perform(
					delete("/produto/{id}", id)
					.contentType(MediaType.APPLICATION_JSON))
	        //        .andDo(print())
					.andExpect(status().isNotFound());
			verify(produtoService, times(0)).getDeleteProdutoUseCase();
		}
	}
	
	@Nested
	class FindProduto {
		@Test
		void shouldTestGetCliente() throws Exception {
			when(produtoService.getSearchProdutoUseCase()).thenReturn(searchProdutoUseCase);
			when(searchProdutoUseCase.findById(anyLong())).thenReturn(Optional.of(mockProduto()));
	
			mockMvc.perform(
					get("/produto/{id}", 1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(mockProdutoDTO())))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(produtoService, times(1)).getSearchProdutoUseCase();
		}
		
		@Test
		void shouldTestGetClienteNotFound() throws Exception {
			when(produtoService.getSearchProdutoUseCase()).thenReturn(searchProdutoUseCase);
			when(searchProdutoUseCase.findById(anyLong())).thenReturn(Optional.empty());
	
			mockMvc.perform(
					get("/produto/{id}", 1L)
					.contentType(MediaType.APPLICATION_JSON))
	        //        .andDo(print())
					.andExpect(status().isNotFound());
			verify(produtoService, times(1)).getSearchProdutoUseCase();
		}
		
		@Test
		void shouldTestGetClienteWithIdNull() throws Exception {
			when(produtoService.getSearchProdutoUseCase()).thenReturn(searchProdutoUseCase);
			when(searchProdutoUseCase.findById(anyLong())).thenReturn(Optional.empty());
	
			Long id = null;
			mockMvc.perform(
					get("/produto/{id}", id)
					.contentType(MediaType.APPLICATION_JSON))
	        //        .andDo(print())
					.andExpect(status().isNotFound());
			verify(produtoService, times(0)).getSearchProdutoUseCase();
		}
		
		@Test
		void shouldTestGetClientes() throws Exception {
			when(produtoService.getSearchProdutoUseCase()).thenReturn(searchProdutoUseCase);
			when(searchProdutoUseCase.find(anyInt(), anyInt())).thenReturn(mockProdutoLista());
	
			mockMvc.perform(
					get("/produto")
					.contentType(MediaType.APPLICATION_JSON))
			//		.content(asJsonString(mockClienteDTO())))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(produtoService, times(1)).getSearchProdutoUseCase();
		}
		
	}
	
	private Collection<Produto> mockProdutoLista() {
		List<Produto> produtos = new ArrayList<>();
		produtos.add(mockProduto());
		return produtos;
	}
	
	private List<Long> mockProdutoIds() {
		List<Long> produtoIds = List.of(1L, 2L, 3L);
		return produtoIds;
	}
	
	private ProdutoDTO mockProdutoDTO() {
		ProdutoDTO dto = new ProdutoDTO();
		dto.setId(1L);
		return dto;
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
