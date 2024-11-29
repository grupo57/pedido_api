package br.com.fiap.soat07.clean.core.usecase.pagamento;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.core.gateway.PagamentoGateway;

public class RetornoGatewayPagamentoUseCase {

	private final PagamentoGateway pagamentoGateway;

	public RetornoGatewayPagamentoUseCase(PagamentoGateway pagamentoGateway) {
		this.pagamentoGateway = pagamentoGateway;
	}

	public PagamentoStatusEnum executar(Pagamento pagamento) {
		return pagamentoGateway.getSituacao(pagamento);
	}

	public String qrcode(Pagamento pagamento) {
		return pagamentoGateway.getQRCode(pagamento);
	}

}
