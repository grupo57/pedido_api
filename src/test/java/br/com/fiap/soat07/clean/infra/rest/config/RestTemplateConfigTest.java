package br.com.fiap.soat07.clean.infra.rest.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class RestTemplateConfigTest {
	
	@Mock
	private RestTemplateBuilder restTemplateBuilder;
	
	@Mock
	private RestTemplate restTemplate;
	
	@InjectMocks
	private RestTemplateConfig restTemplateConfig;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void shouldTestRestTemplate() {
		
		when(restTemplateBuilder.build()).thenReturn(restTemplate);

		assertNotNull(restTemplateConfig.restTemplate(restTemplateBuilder));
		
	}

}
