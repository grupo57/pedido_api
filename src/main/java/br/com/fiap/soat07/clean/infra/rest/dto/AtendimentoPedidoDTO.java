package br.com.fiap.soat07.clean.infra.rest.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AtendimentoPedidoDTO {
	
	private Long id;
    private String cliente;
    private String codigo;
    private Set<ProdutoDTO> produtos;

}
