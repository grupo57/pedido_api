package br.com.fiap.soat07.clean.core.usecase.pedido;

import static br.com.fiap.soat07.clean.Utils.hasProdutoDuplicates;

import java.util.UUID;

import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.clean.core.exception.PedidoDuplicadoComboException;
import br.com.fiap.soat07.clean.core.exception.ProdutoDuplicadoComboException;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;

public class CreatePedidoUseCase {
	private final PedidoGateway pedidoGateway;

	public CreatePedidoUseCase(PedidoGateway pedidoGateway) {
		this.pedidoGateway = pedidoGateway;
	}


	/**
	 * Create new Pedido com base num Combo
	 * @param combo Combo
	 * @return {@link Combo}
	 */
	public Pedido execute(Combo combo) {
		if (pedidoGateway.findByCombo(combo.getId()).isPresent())
			throw new PedidoDuplicadoComboException();

		if (hasProdutoDuplicates(combo.getProdutos()))
			throw new ProdutoDuplicadoComboException();

		Pedido pedido = new Pedido();
		pedido.setCombo(combo);
		pedido.setCodigo(getCodigoPedido());
		pedido.setNomeCliente(combo.getCliente().getNome());
		pedido.setStatus(PedidoStatusEnum.INICIADO);
		for (Produto produto : combo.getProdutos())
			pedido.getProdutos().add(produto);

		pedido = pedidoGateway.save(pedido);

		return pedido;
	}


	private String getCodigoPedido() {
		return String.format("%s%s","COD",UUID.randomUUID().toString().substring(0, 4));
	}

}
