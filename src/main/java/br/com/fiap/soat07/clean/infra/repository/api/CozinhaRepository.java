package br.com.fiap.soat07.clean.infra.repository.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import br.com.fiap.soat07.clean.core.domain.entity.Pedido;
import br.com.fiap.soat07.clean.core.gateway.CozinhaGateway;
import br.com.fiap.soat07.clean.infra.rest.dto.AtendimentoPedidoDTO;
import br.com.fiap.soat07.clean.infra.rest.mapper.PedidoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class CozinhaRepository implements CozinhaGateway {
	
	@Value("${application.atendimento.cozinha.url}")
	private String ATENDIMENTO_COZINHA_INCLUIR_PEDIDO_URL;
	
	private final RestTemplate restTemplate;
	
	private final PedidoMapper mapper;

	@Override
	@Retryable(retryFor = HttpClientErrorException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	public Boolean send(Pedido pedido) {
		
		AtendimentoPedidoDTO pedidoDTO = mapper.toAtendimentoPedidoDTO(pedido);
		
		
		HttpEntity<AtendimentoPedidoDTO> requestEntity = new HttpEntity<>(pedidoDTO);
		
		try {
		ResponseEntity<?> response =
				restTemplate.exchange(ATENDIMENTO_COZINHA_INCLUIR_PEDIDO_URL, HttpMethod.POST, requestEntity, Object.class);
		log.info("Pedido enviado com sucesso para a cozinha. \n Resposta: {}", new Gson().toJson(response));
		return Boolean.TRUE;
		}catch(RuntimeException e) {
			log.error("Não foi possível enviar o pedido: {} para a cozinha", pedido.getId(), e);
			return Boolean.FALSE;
		}

	}
	
	

}
