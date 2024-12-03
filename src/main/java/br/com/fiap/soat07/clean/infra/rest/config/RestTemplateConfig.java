package br.com.fiap.soat07.clean.infra.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Class responsible to create a RestTemplate Object
 * 
 * @author Marcello de Almeida Lima
 * @since 20-11-2024
 */
@Configuration
public class RestTemplateConfig {
	
	/**
	 * Method responsible to create a RestTemplate Object
	 * @param restTemplateBuilder {@link RestTemplateBuilder}
	 * @return {@link RestTemplate}
	 */
	@Bean
	public RestTemplate restTemplate(@Autowired RestTemplateBuilder restTemplateBuilder) {
		RestTemplate restTemplate = restTemplateBuilder.build();
		return restTemplate;
	}

}
