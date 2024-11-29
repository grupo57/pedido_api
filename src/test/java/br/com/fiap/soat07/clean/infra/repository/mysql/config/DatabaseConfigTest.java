package br.com.fiap.soat07.clean.infra.repository.mysql.config;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class DatabaseConfigTest {
	
	@InjectMocks
	DatabaseConfig config = new DatabaseConfig();
	
	private String url = "http://localhost";	
	private String driverClassName = "com.mysql.cj.jdbc.Driver";		
	private String userName = "src/test/resources/database/secret-db-username";
	private String password = "src/test/resources/database/secret-db-password";;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		ReflectionTestUtils.setField(config, "url", url);
		ReflectionTestUtils.setField(config, "driverClassName", driverClassName);
		ReflectionTestUtils.setField(config, "userName", userName);
		ReflectionTestUtils.setField(config, "password", password);
	}

	@Test
	void shouldTestCreateProduto() {		
			
		assertThrows(RuntimeException.class, () -> config.dataSource());

	}
	
	
	

}
