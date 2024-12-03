package br.com.fiap.soat07.clean.infra.service;

import org.springframework.stereotype.Component;

import br.com.fiap.soat07.clean.core.gateway.ClienteGateway;
import br.com.fiap.soat07.clean.core.gateway.ProdutoGateway;
import br.com.fiap.soat07.clean.core.usecase.combo.CreateComboUseCase;
import br.com.fiap.soat07.clean.core.usecase.combo.DeleteComboUseCase;
import br.com.fiap.soat07.clean.core.usecase.combo.SearchComboUseCase;
import br.com.fiap.soat07.clean.core.usecase.combo.UpdateComboUseCase;
import br.com.fiap.soat07.clean.infra.repository.mysql.ComboRepository;

@Component
public class ComboService {

    private final CreateComboUseCase createComboUseCase;
    private final DeleteComboUseCase deleteComboUseCase;
    private final UpdateComboUseCase updateComboUseCase;
    private final SearchComboUseCase searchComboUseCase;

    public ComboService(final ComboRepository comboGateway, final ClienteGateway clienteGateway, final ProdutoGateway produtoGateway) {
        this.createComboUseCase = new CreateComboUseCase(comboGateway, clienteGateway, produtoGateway);
        this.deleteComboUseCase = new DeleteComboUseCase(comboGateway);
        this.updateComboUseCase = new UpdateComboUseCase(comboGateway);
        this.searchComboUseCase = new SearchComboUseCase(comboGateway);
    }

    public CreateComboUseCase getCreateComboUseCase() {
        return createComboUseCase;
    }

    public DeleteComboUseCase getDeleteComboUseCase() {
        return deleteComboUseCase;
    }

    public UpdateComboUseCase getUpdateComboUseCase() {
        return updateComboUseCase;
    }

    public SearchComboUseCase getSearchComboUseCase() {
        return searchComboUseCase;
    }
}
