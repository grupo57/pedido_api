package br.com.fiap.soat07.clean.infra.rest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.context.request.WebRequest;

@ExtendWith(MockitoExtension.class)
public class RestResponseEntityExceptionHandlerTest {
	
	@InjectMocks
	private RestResponseEntityExceptionHandler handler;
	
	@Mock
	private RuntimeException ex;
	
	@Mock
	private WebRequest request;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void shouldTestHandleConflict() {
		assertNotNull(handler.handleConflict(ex, request));
	}
	
	@Test
	void shouldTestHandleNotFound() {
		assertNotNull(handler.handleNotFound(ex, request));
	}
	
	@Test
	void shouldTestHandleBusinessException() {
		assertNotNull(handler.handleBusinessException(ex, request));
	}
	
	@Test
	void shouldTestHandleGenericException() {
		assertNotNull(handler.handleGenericException(ex, request));
	}

}
