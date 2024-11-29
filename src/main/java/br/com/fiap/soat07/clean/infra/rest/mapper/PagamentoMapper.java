package br.com.fiap.soat07.clean.infra.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.fiap.soat07.clean.infra.rest.dto.PagamentoDTO;
import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;

@Mapper (componentModel = "spring")
public interface PagamentoMapper {
	
	PagamentoMapper INSTANCE = Mappers.getMapper(PagamentoMapper.class);
	
	/**
	 * To domain mapper
	 * @param pagamentoDTO PagamentoDTO
	 * @return {@link Pagamento}
	 */
	Pagamento toDomain(PagamentoDTO pagamentoDTO);
	
	/**
	 * To model mapper
	 * @param pagamento Pagamento
	 * @return {@link PagamentoDTO}
	 */
	PagamentoDTO toDTO(Pagamento pagamento);

}
