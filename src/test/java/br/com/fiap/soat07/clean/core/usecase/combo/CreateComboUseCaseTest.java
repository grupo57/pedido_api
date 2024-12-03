package br.com.fiap.soat07.clean.core.usecase.combo;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.soat07.clean.core.domain.entity.Cliente;
import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.domain.enumeration.TipoProdutoEnum;
import br.com.fiap.soat07.clean.core.gateway.ClienteGateway;
import br.com.fiap.soat07.clean.core.gateway.ComboGateway;
import br.com.fiap.soat07.clean.core.gateway.ProdutoGateway;
import br.com.fiap.soat07.clean.infra.rest.dto.CreateComboDTO;
import br.com.fiap.soat07.clean.infra.rest.dto.IdentificacaoDoClienteDTO;
import br.com.fiap.soat07.clean.infra.rest.dto.ProdutoDTO;

@ExtendWith(MockitoExtension.class)
public class CreateComboUseCaseTest {
	
	@InjectMocks
	CreateComboUseCase useCase;
	
	@Mock
	ComboGateway comboGateway;
	
	@Mock
	ClienteGateway clienteGateway;
	 
	@Mock
	 ProdutoGateway produtoGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		useCase = new CreateComboUseCase(comboGateway, clienteGateway, produtoGateway);
	}
	
	@Test
	void shouldTestCreateCombo() {		
		when(comboGateway.save(any(Combo.class))).thenReturn(mockCombo());
		when(clienteGateway.save(any(Cliente.class))).thenReturn(mockCliente());
		when(clienteGateway.findById(anyLong())).thenReturn(Optional.of(mockCliente()));
		when(produtoGateway.findById(anyLong())).thenReturn(Optional.of(mockProduto()));

		assertNotNull(useCase.execute(mockCreateComboDTO(1L, "51468663097")));
		verify( comboGateway, times(1)).save(any(Combo.class));
	}
	
	@Test
	void shouldTestCreateComboClienteCpfNull() {		
		when(comboGateway.save(any(Combo.class))).thenReturn(mockCombo());
		when(clienteGateway.save(any(Cliente.class))).thenReturn(mockCliente());
		when(produtoGateway.findById(anyLong())).thenReturn(Optional.of(mockProduto()));
		CreateComboDTO createComboDTO = mockCreateComboDTO(0L, null);
		assertNotNull(useCase.execute(createComboDTO));
		verify( comboGateway, times(1)).save(any(Combo.class));
	}
	
	@Test
	void shouldTestCreateComboClienteIdZero() {		
		when(comboGateway.save(any(Combo.class))).thenReturn(mockCombo());
		when(clienteGateway.save(any(Cliente.class))).thenReturn(mockCliente());
		when(clienteGateway.findByCpf(anyString())).thenReturn(Optional.of(mockCliente()));
		when(produtoGateway.findById(anyLong())).thenReturn(Optional.of(mockProduto()));
		CreateComboDTO createComboDTO = mockCreateComboDTO(0L, "51468663097");
		
		assertNotNull(useCase.execute(createComboDTO));
		verify( comboGateway, times(1)).save(any(Combo.class));
	}
	
	@Test
	void shouldTestCreateComboFindClienteByCpfNull() {		
		when(comboGateway.save(any(Combo.class))).thenReturn(mockCombo());
		when(clienteGateway.save(any(Cliente.class))).thenReturn(mockCliente());
		when(clienteGateway.findByCpf(anyString())).thenReturn(Optional.ofNullable(null));
		when(produtoGateway.findById(anyLong())).thenReturn(Optional.of(mockProduto()));
		CreateComboDTO createComboDTO = mockCreateComboDTO(0L, "51468663097");
		
		assertNotNull(useCase.execute(createComboDTO));
		verify( comboGateway, times(1)).save(any(Combo.class));
	}
	
	@Test
	void shouldTestCreateComboNullIllegalArgumentException() {		
		
		assertThatThrownBy(() -> useCase.execute(null))
        .isInstanceOf(IllegalArgumentException.class);
	}
	
	@Test
	void shouldTestCreateComboDTOClienteNullIllegalArgumentException() {		
		CreateComboDTO createComboDTO = mockCreateComboDTO(0L, "51468663097");
		createComboDTO.setCliente(null);
		assertThatThrownBy(() -> useCase.execute(createComboDTO))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Cliente não informado");
	}
	
	
	private CreateComboDTO mockCreateComboDTO(Long clienteId, String cpf) {
		CreateComboDTO dto = CreateComboDTO.builder()
				.cliente(mockIdentificacaoDoCliente(clienteId, cpf))
				.produtos(mockPrudutoDTOLista())
				.build();
		
		return dto;
	}

	private List<ProdutoDTO> mockPrudutoDTOLista() {
		List<ProdutoDTO> produtos = new ArrayList<>();
		produtos.add(mockProdutoDTO());
		return produtos;
	}

	private IdentificacaoDoClienteDTO mockIdentificacaoDoCliente(Long clienteId, String cpf) {
		IdentificacaoDoClienteDTO id = new IdentificacaoDoClienteDTO();
		id.setCpf(cpf);
		id.setId(clienteId);
		id.setNome("test");
		return id;
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
	
	private ProdutoDTO mockProdutoDTO() {
		ProdutoDTO dto = new ProdutoDTO();
		dto.setCodigo("COD1");
		dto.setId(1l);
		dto.setNome("test");
		dto.setTipoProduto(TipoProdutoEnum.LANCHE);
		dto.setValor(new BigDecimal(10.20));
		return dto;
	}
	
	private Combo mockCombo() {
		Combo combo = Combo.builder()
				.cliente(mockCliente())
				.id(1L)
				.nome("test")
				.produtos(mockPrudutoSet())
				.dataCriacao(LocalDateTime.of(2024, 11, 25, 22, 0))
				.build();
		return combo;
	}
	
	private Set<Produto> mockPrudutoSet() {
		Set<Produto> produtos = new HashSet<>();
		produtos.add(mockProduto());
		return produtos;
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
