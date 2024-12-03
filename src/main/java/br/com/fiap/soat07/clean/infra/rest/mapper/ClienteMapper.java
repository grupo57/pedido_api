package br.com.fiap.soat07.clean.infra.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.fiap.soat07.clean.core.domain.entity.Cliente;
import br.com.fiap.soat07.clean.infra.rest.dto.ClienteDTO;

@Mapper (componentModel = "spring")
public interface ClienteMapper {
	
	static ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);
	
	/**
	 * To domain mapper
	 * @param clienteDTO ClienteDTO
	 * @return {@link ClienteDTO}
	 */
	Cliente toDomain(ClienteDTO clienteDTO);
	
	/**
	 * To model mapper
	 * @param cliente Cliente
	 * @return {@link ClienteDTO}
	 */
	ClienteDTO toDTO(Cliente cliente);

}
