package br.com.fiap.soat07.clean.core.domain.entity;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.code.beanmatchers.BeanMatchers;
import com.google.code.beanmatchers.ValueGenerator;

public class EntityTest {
	
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

		assertThat(Combo.class, hasValidGettersAndSetters());
		assertThat(Pagamento.class, hasValidGettersAndSetters());
		assertThat(Pedido.class, hasValidGettersAndSetters());
		assertThat(Produto.class, hasValidGettersAndSetters());
		assertThat(Cliente.class, hasValidGettersAndSetters());
	}

	@Test
	void shouldTestHasValidHashCode() {

		assertThat(Combo.class, hasValidBeanHashCode());
		assertThat(Pagamento.class, hasValidBeanHashCode());
		assertThat(Pedido.class, hasValidBeanHashCode());
		//assertThat(Produto.class, hasValidBeanHashCode());
		assertThat(Cliente.class, hasValidBeanHashCode());
	}

	@Test
	void shouldTestHasValidBeanEquals() {

		assertThat(Combo.class, hasValidBeanEquals());
		assertThat(Pagamento.class, hasValidBeanEquals());
		//assertThat(Pedido.class, hasValidBeanEquals());
		//assertThat(Produto.class, hasValidBeanEquals());
		assertThat(Cliente.class, hasValidBeanEquals());
	}

	@Test
	void shouldTestHasValidToString() {

		assertEquals(new Combo().toString(), new Combo().toString());
		assertEquals(new Pagamento().toString(), new Pagamento().toString());
		//assertEquals(new Pedido().toString(), new Pedido().toString());
		//assertEquals(new Produto().toString(), new Produto().toString());
		assertEquals(new Cliente().toString(), new Cliente().toString());
	}


}
