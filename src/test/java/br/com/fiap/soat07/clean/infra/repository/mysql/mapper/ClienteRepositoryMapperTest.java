package br.com.fiap.soat07.clean.infra.repository.mysql.mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.soat07.clean.core.domain.entity.Cliente;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.ClienteModel;

@ExtendWith(MockitoExtension.class)
public class ClienteRepositoryMapperTest {
	
	
	ClienteRepositoryMapper mapper = ClienteRepositoryMapper.INSTANCE;
	
	
	@Test
	void shouldTestToDomain() {
		Cliente cliente = mapper.toDomain(mockClienteModel());
		assertNotNull (cliente);
	}
	
	@Test
	void shouldTestToModel() {
		ClienteModel model = mapper.toModel(mockCliente());
		assertNotNull (model);
	}
	
	private Cliente mockCliente() {
		Cliente cliente = new Cliente();
		return cliente;
	}

	private ClienteModel mockClienteModel() {
		ClienteModel model = new ClienteModel();
		return model;
	}

}
