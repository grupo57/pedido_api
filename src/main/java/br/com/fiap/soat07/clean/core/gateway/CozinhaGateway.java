package br.com.fiap.soat07.clean.core.gateway;

import br.com.fiap.soat07.clean.core.domain.entity.Pedido;

public interface CozinhaGateway {
	
	Boolean send(Pedido pedido);

}
