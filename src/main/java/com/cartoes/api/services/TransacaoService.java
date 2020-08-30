package com.cartoes.api.services;

import java.util.List;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cartoes.api.entities.Transacao;
import com.cartoes.api.entities.Cartao;
import com.cartoes.api.repositories.TransacaoRepository;
import com.cartoes.api.repositories.CartaoRepository;
import com.cartoes.api.utils.ConsistenciaException;

@Service
public class TransacaoService {
	private static final Logger Log = LoggerFactory.getLogger(TransacaoService.class);
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	@Autowired
	private CartaoRepository cartaoRepository;
	
	public Optional<List<Transacao>> buscarPorNumeroCartao(String cartaoNumero) throws ConsistenciaException {
		Log.info("Service: buscando as transacoes do cartao de numero: {}", cartaoNumero);
		
		Optional<List<Transacao>> transacoes = transacaoRepository.findByNumeroCartao(cartaoNumero);
		
		if(!transacoes.isPresent() || transacoes.get().size() < 1) {
			Log.info("Service: Nenhuma transacao encontrada no cartao de numero: {}", cartaoNumero);
			throw new ConsistenciaException("Nenhuma transacao encontrada no cartao de numero: {}", cartaoNumero);
		}
		
		return transacoes;
	}
	
	public Transacao salvar(Transacao transacao) throws ConsistenciaException {
		Log.info("Service: salvando transacoes: {}", transacao);
		
		Optional<Cartao> cartao = cartaoRepository.findByNumero(transacao.getCartao().getNumero());
		
		if(!cartaoRepository.findById(transacao.getCartao().getId()).isPresent()) {
			
			Log.info("Service: Nenhum cartao com id: {} foi encontrado", transacao.getCartao().getId());
			throw new ConsistenciaException("Nenhum cartao com id: {} foi encontrado", transacao.getCartao().getId());
		}
		
		if (transacao.getCartao().getBloqueado() == true) {
			Log.info("Service: “Não é possível incluir transações para este cartão, pois o mesmo se encontra bloqueado");
			throw new ConsistenciaException("Não é possível incluir transações para este cartão, pois o mesmo se encontra bloqueado");
		}
		
		if (transacao.getId() > 0) {
			Log.info("Service: Transações não podem ser alteradas, apenas incluídas.");
			throw new ConsistenciaException("Transações não podem ser alteradas, apenas incluídas.");
		}
		
		if (cartao.get().getDataValidade().before(new Date())) {
			Log.info("Service: Não é possível incluir transações para este cartão, pois o mesmo encontra-se vencido");
			throw new ConsistenciaException("Service: Não é possível incluir transações para este cartão, pois o mesmo encontra-se vencido");
		}
		
	     transacao.setCartao(cartao.get());
	     
	     return transacaoRepository.save(transacao);
	}
}
