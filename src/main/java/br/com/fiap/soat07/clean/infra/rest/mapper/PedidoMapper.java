package br.com.fiap.soat07.clean.infra.rest.mapper;

import org.mapstruct.Mapper;

import br.com.fiap.soat07.clean.infra.rest.dto.PedidoDTO;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper (componentModel = "spring")
public interface PedidoMapper {
	
	static PedidoMapper INSTANCE = Mappers.getMapper(PedidoMapper.class);
	
	/**
	 * To domain mapper
	 * @param pedidoModel PedidoDTO
	 * @return {@link Pedido}
	 */
	Pedido toDomain(PedidoDTO pedidoModel);
	
	/**
	 * To model mapper
	 * @param pedido Pedido
	 * @return {@link PedidoDTO}
	 */
	@Mapping(target = "comboId", source = "pedido.combo.id")
	@Mapping(target = "cliente", source = "pedido.combo.cliente")
	PedidoDTO toDTO(Pedido pedido);

}
