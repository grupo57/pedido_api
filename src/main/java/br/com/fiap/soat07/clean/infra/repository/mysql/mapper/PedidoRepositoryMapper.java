package br.com.fiap.soat07.clean.infra.repository.mysql.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.PedidoModel;

@Mapper (componentModel = "spring")
public interface PedidoRepositoryMapper {
	
	static PedidoRepositoryMapper INSTANCE = Mappers.getMapper(PedidoRepositoryMapper.class);
	
	/**
	 * To domain mapper
	 * @param pedidoModel
	 * @return {@link Pedido}
	 */
	Pedido toDomain(PedidoModel pedidoModel);

	/**
	 * To model mapper
	 * @param pedido
	 * @return {@link PedidoModel}
	 */
	PedidoModel toModel(Pedido pedido);

}
