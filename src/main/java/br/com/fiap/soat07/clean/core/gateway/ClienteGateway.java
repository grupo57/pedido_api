package br.com.fiap.soat07.clean.core.gateway;

import java.util.Collection;
import java.util.Optional;

import br.com.fiap.soat07.clean.core.domain.entity.Cliente;

public interface ClienteGateway {

    /**
     * Get by id
     * @param id {@link Long}
     * @return {@link Cliente}
     */
    Optional<Cliente> findById(Long id);

    /**
     * Get by id
     * @param cpf {@link String}
     * @return {@link Cliente}
     */
    Optional<Cliente> findByCpf(String cpf);

    Optional<Cliente> findByCodigo(String codigo);

    Cliente save(Cliente cliente);

    void delete(Cliente cliente);

    Collection<Cliente> find(Integer page, Integer size);
}
