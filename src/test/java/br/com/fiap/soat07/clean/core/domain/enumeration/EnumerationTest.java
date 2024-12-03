package br.com.fiap.soat07.clean.core.domain.enumeration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EnumerationTest {
	
	
	@Test
	void shouldTestSimpleEnums() {
		assertEquals(MetodoPagamentoEnum.BOLETO, MetodoPagamentoEnum.BOLETO); 
		assertEquals(PagamentoStatusEnum.CANCELADO, PagamentoStatusEnum.CANCELADO); 
		assertEquals(PedidoStatusEnum.INICIADO, PedidoStatusEnum.INICIADO ); 
		assertEquals(ProvedorPagamentoEnum.MERCADO_PAGO, ProvedorPagamentoEnum.MERCADO_PAGO); 
		assertEquals(TipoProdutoEnum.LANCHE, TipoProdutoEnum.LANCHE); 
	}
	
	@Test
	void shouldTestPedidoStatusEnum() {

		assertEquals(PedidoStatusEnum.INICIADO.getStep(), PedidoStatusEnum.INICIADO.getStep() ); 
		assertEquals(PedidoStatusEnum.INICIADO.isCancelado(), false ); 
		assertEquals(PedidoStatusEnum.CANCELADO.isCancelado(), true ); 
		assertEquals(PedidoStatusEnum.CANCELADO.isFinalizado(), false );
		assertEquals(PedidoStatusEnum.FINALIZADO.isFinalizado(), true );

	}

}
