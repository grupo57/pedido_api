package br.com.fiap.soat07.clean.core.gateway;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;

import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;

public interface ComboGateway {

    /**
     * Get by id
     * @param id {@link Long}
     * @return {@link Optional<Combo>}
     * @see Optional
     */
    Optional<Combo> findById(long id);

    Combo save(Combo combo);

    void delete(Combo combo);

    void deleteProdutosOfCombo(Combo combo, Produto produto);


    /**
     * Get pageable
     * @param pageNumber
     * @param pageSize
     * @return {@link Page < Combo >}
     */
    Collection<Combo> find(int pageNumber, int pageSize);
}
