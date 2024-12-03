package br.com.fiap.soat07.clean.core.usecase.combo;

import org.springframework.stereotype.Component;

import br.com.fiap.soat07.clean.core.domain.entity.Combo;
import br.com.fiap.soat07.clean.core.domain.entity.Produto;
import br.com.fiap.soat07.clean.core.gateway.ComboGateway;

@Component
public class DeleteProdutoComboUseCase {

    private final ComboGateway comboGateway;

    public DeleteProdutoComboUseCase(final ComboGateway comboGateway) {
        this.comboGateway = comboGateway;
    }

    public void execute(final Combo combo, final Produto produto) {
        if (combo == null)
            throw new IllegalArgumentException();
        if (produto == null)
            throw new IllegalArgumentException();

        combo.getProdutos().removeIf(p -> p.getId() == produto.getId());

        comboGateway.save(combo);
    }

}
