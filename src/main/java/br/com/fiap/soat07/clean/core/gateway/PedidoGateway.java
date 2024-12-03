package br.com.fiap.soat07.clean.core.gateway;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;

import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;

public interface PedidoGateway {

    /**
     * Get by id
     * @param id {@link Long}
     * @return {@link Combo}
     */
    Optional<Pedido> findById(long id);

    Optional<Pedido> findByCombo(long id);

    Pedido save(Pedido pedido);

    void delete(Pedido pedido);

    void deleteProduto(Pedido pedido, Produto produto);


    /**
     * Get pageable
     * @param pageNumber
     * @param pageSize
     * @return {@link Page < Pedido >}
     */
    Collection<Pedido> find(int pageNumber, int pageSize);

}
