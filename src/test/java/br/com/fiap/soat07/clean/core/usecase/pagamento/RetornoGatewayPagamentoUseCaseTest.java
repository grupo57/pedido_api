package br.com.fiap.soat07.clean.core.usecase.pagamento;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.core.gateway.PagamentoGateway;

@ExtendWith(MockitoExtension.class)
public class RetornoGatewayPagamentoUseCaseTest {

	@InjectMocks
	RetornoGatewayPagamentoUseCase useCase;
	
	@Mock
	PagamentoGateway pagamentoGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		useCase = new RetornoGatewayPagamentoUseCase(pagamentoGateway);
	}
	
	@Test
	void shouldTestExecutarPagamento() {		

		when(pagamentoGateway.getSituacao(any(Pagamento.class))).thenReturn(PagamentoStatusEnum.PAGO);

		assertEquals(PagamentoStatusEnum.PAGO, useCase.executar(mockPagamento()));
		verify( pagamentoGateway, times(1)).getSituacao(any(Pagamento.class));
	}
	
	@Test
	void shouldTestQrcode() {		

		when(pagamentoGateway.getQRCode(any(Pagamento.class))).thenReturn("QR123");

		assertEquals("QR123", useCase.qrcode(mockPagamento()));

	}
	
	
	private Pagamento mockPagamento() {
		Pagamento pagamento = new Pagamento();
		return pagamento;
	}

}
