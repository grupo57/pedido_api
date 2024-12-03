package br.com.fiap.soat07.clean.infra.repository.mysql.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.code.beanmatchers.BeanMatchers;
import com.google.code.beanmatchers.ValueGenerator;

public class ModelTest {

	@BeforeAll
	static void setup(){
		BeanMatchers.registerValueGenerator(new ValueGenerator<LocalDateTime>() {
			@Override
			public LocalDateTime generate() {
				long startEpochDay = LocalDate.of(1950, 1, 1).toEpochDay();
				long endEpochDay = LocalDate.of(2050, 12, 31).toEpochDay();
				long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);
				return LocalDateTime.ofEpochSecond(randomDay, 1, ZoneOffset.ofHours(1));
			}
		}, LocalDateTime.class);

		BeanMatchers.registerValueGenerator(new ValueGenerator<LocalDate>() {
			@Override
			public LocalDate generate() {
				// Generate a random LocalDate between 1950 and 2050
				long startEpochDay = LocalDate.of(1950, 1, 1).toEpochDay();
				long endEpochDay = LocalDate.of(2050, 12, 31).toEpochDay();
				long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);
				return LocalDate.ofEpochDay(randomDay);
			}
		}, LocalDate.class);

	}
	
	
	@Test
	void shouldTestHasValidGettersAndSetters() {

		assertThat(ComboModel.class, hasValidGettersAndSetters());
		assertThat(PedidoModel.class, hasValidGettersAndSetters());
		assertThat(ProdutoModel.class, hasValidGettersAndSetters());
		assertThat(ClienteModel.class, hasValidGettersAndSetters());
	}
	

	@Test
	void shouldTestHasValidToString() {

		assertEquals(new ComboModel().toString(), new ComboModel().toString());
		assertEquals(new ProdutoModel().toString(), new ProdutoModel().toString());
		assertEquals(new ClienteModel().toString(), new ClienteModel().toString());
	}

	
}
