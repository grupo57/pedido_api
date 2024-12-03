package br.com.fiap.soat07.clean.infra.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.infra.rest.dto.ComboDTO;

@Mapper (componentModel = "spring")
public interface ComboMapper {
	
	static ComboMapper INSTANCE = Mappers.getMapper(ComboMapper.class);
	
	/**
	 * To domain mapper
	 * @param comboModel
	 * @return {@link Combo}
	 */
	Combo toDomain(ComboDTO comboModel);
	
	/**
	 * To model mapper
	 * @param combo
	 * @return {@link ComboDTO}
	 */
	ComboDTO toDTO(Combo combo);

}
