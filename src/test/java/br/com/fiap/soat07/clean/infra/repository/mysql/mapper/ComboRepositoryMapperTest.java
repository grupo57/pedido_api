package br.com.fiap.soat07.clean.infra.repository.mysql.mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.infra.repository.mysql.model.ComboModel;

@ExtendWith(MockitoExtension.class)
public class ComboRepositoryMapperTest {
	
	ComboRepositoryMapper mapper = Mappers.getMapper(ComboRepositoryMapper.class);
		
	//@Test
	void shouldTestToDomain() {
		Combo combo = mapper.toDomain(mockComboModel());
		assertNotNull (combo);
	}
	
	//@Test
	void shouldTestToModel() {
		ComboModel model = mapper.toModel(mockCombo());
		assertNotNull (model);
	}
	
	private Combo mockCombo() {
		Combo combo = new Combo();
		return combo;
	}

	private ComboModel mockComboModel() {
		ComboModel model = new ComboModel();
		return model;
	}


}
