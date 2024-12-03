package br.com.fiap.soat07.clean.core.usecase.pedido;

import org.springframework.stereotype.Component;

import br.com.fiap.soat07.clean.Utils;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.clean.core.gateway.CozinhaGateway;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;

@Component
public class UpdatePedidoUseCase {

    private final PedidoGateway pedidoGateway;
    
    private final CozinhaGateway cozinhaGateway;

    public UpdatePedidoUseCase(PedidoGateway pedidoGateway, CozinhaGateway cozinhaGateway) {
        this.pedidoGateway = pedidoGateway;
        this.cozinhaGateway = cozinhaGateway;
    }

    public Pedido execute(Pedido pedido, Pedido atualizacoes) {

        pedido.setCodigo(atualizacoes.getCodigo());
        pedido.setNomeCliente(atualizacoes.getNomeCliente());
        pedido.setUltimaModificacao(Utils.now());
        pedido.setStatus(atualizacoes.getStatus());

        pedidoGateway.save(pedido);
        if(pedido.getStatus() == PedidoStatusEnum.PAGO) {
        	cozinhaGateway.send(pedido); // No caso de retorno false, implementar política de retentativas de envio
        }
        return pedido;
    }

}
