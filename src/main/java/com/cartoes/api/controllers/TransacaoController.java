package com.cartoes.api.controllers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cartoes.api.entities.Transacao;
import com.cartoes.api.services.TransacaoService;
import com.cartoes.api.dtos.TransacaoDto;
import com.cartoes.api.utils.ConsistenciaException;
import com.cartoes.api.utils.ConversaoUtils;
import com.cartoes.api.response.Response;
@RestController
@RequestMapping("/api/transacao")
@CrossOrigin(origins = "*")
public class TransacaoController {
	@Autowired
	 private TransacaoService transacaoService;
	
	private static final Logger log = LoggerFactory.getLogger(TransacaoController.class);
	
	@GetMapping(value = "/cartao/{cartaoNumero}")
	public ResponseEntity<Response<List<Transacao>>>
	buscarPorNumeroCartao(@PathVariable("cartaoNumero") String cartaoNumero) {
		Response<List<Transacao>> response = new Response<List<Transacao>>();
		try {
			 log.info("Controller: buscando transacoes do cartao de numero: {}", cartaoNumero);
			 Optional<List<Transacao>> listaTransacoes = transacaoService.buscarPorNumeroCartao(cartaoNumero);
			 response.setDados(listaTransacoes.get());
			 return ResponseEntity.ok(response);
	} catch (ConsistenciaException e) {
		log.info("Controller: Inconsistencia de dados: {}" , e.getMessage());
		response.adicionarErro(e.getMensagem());
		 return ResponseEntity.badRequest().body(response);
	} catch (Exception e) {
		log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
		response.adicionarErro("Ocorreu um erro na aplicação: {}",
				e.getMessage());
				 return ResponseEntity.status(500).body(response);
	}
}
	@PostMapping
	 public ResponseEntity<Response<Transacao>> salvar(@RequestBody Transacao transacao) {
		Response<Transacao> response = new Response<Transacao>();
		try {
			 log.info("Controller: salvando a transacao: {}", transacao.toString());

			 response.setDados(this.transacaoService.salvar(transacao));
			 return ResponseEntity.ok(response);
			 } catch (ConsistenciaException e) {
			 log.info("Controller: Inconsistência de dados: {}", e.getMessage());
			 response.adicionarErro(e.getMensagem());
			 return ResponseEntity.badRequest().body(response);
			 } catch (Exception e) {
			 log.error("Controller: Ocorreu um erro na aplicação: {}",
			e.getMessage());
			 response.adicionarErro("Ocorreu um erro na aplicação: {}",
					 e.getMessage());
					  return ResponseEntity.status(500).body(response);
			 }
	}


}