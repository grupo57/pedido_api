package br.com.fiap.soat07.clean.infra.repository.mysql.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.fiap.soat07.clean.core.domain.entity.Cliente;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.ClienteModel;

@Mapper (componentModel = "spring")
public interface ClienteRepositoryMapper {
	
	static ClienteRepositoryMapper INSTANCE = Mappers.getMapper(ClienteRepositoryMapper.class);
	
	/**
	 * To domain mapper
	 * @param clienteModel
	 * @return {@link Cliente}
	 */
	Cliente toDomain(ClienteModel clienteModel);
	
	/**
	 * To model mapper
	 * @param cliente
	 * @return {@link ClienteModel}
	 */
	ClienteModel toModel(Cliente cliente);

}
