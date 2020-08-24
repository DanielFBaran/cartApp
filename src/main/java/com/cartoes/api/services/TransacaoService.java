package com.cartoes.api.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cartoes.api.entities.Transacao;
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
	
	public Optional<List<Transacao>> buscarPorNumeroCartao(int cartaoNumero) throws ConsistenciaException {
		Log.info("Service: buscando as transacoes do cartao de numero: {}", cartaoNumero);
		
		Optional<List<Transacao>> transacoes = Optional.ofNullable(transacaoRepository.findByNumeroCartao(cartaoNumero));
		
		if(!transacoes.isPresent() || transacoes.get().size() < 1) {
			Log.info("Service: Nenhuma transacao encontrada no cartao de numero: {}", cartaoNumero);
			throw new ConsistenciaException("Nenhuma transacao encontrada no cartao de numero: {}", cartaoNumero);
		}
		
		return transacoes;
	}
	
	public Transacao salvar(Transacao transacao) throws ConsistenciaException {
		Log.info("Service: salvando transacoes: {}", transacao);
		
		if(!cartaoRepository.findById(transacao.getCartao().getId()).isPresent()) {
			
			Log.info("Service: Nenhum cartao com id: {} foi encontrado", transacao.getCartao().getId());
			throw new ConsistenciaException("Nenhum cartao com id: {} foi encontrado", transacao.getCartao().getId());
		}
		
		if (!transacao.getCartao().getBloqueado() == false) {
			Log.info("Service: “Não é possível incluir transações para este cartão, pois o mesmo se encontra bloqueado");
			throw new ConsistenciaException("Não é possível incluir transações para este cartão, pois o mesmo se encontra bloqueado");
		}
		
		if (transacao.getId() > 0) {
			buscarPorNumeroCartao(transacao.getId());
		}
		
		if (transacao.getCartao().getDataValidade().before(transacao.getCartao().getDataAtualizacao())) {
			Log.info("Service: Não é possível incluir transações para este cartão, pois o mesmo encontra-se vencido");
			throw new ConsistenciaException("Service: Não é possível incluir transações para este cartão, pois o mesmo encontra-se vencido");
		}
		
	try {
		return transacaoRepository.save(transacao);
	} catch (DataIntegrityViolationException e) {
		
		Log.info("Service: Já existe uma transacao de id {} cadastrado", transacao.getId());
		throw new ConsistenciaException("Já existe uma transacao de id {} cadastrado", transacao.getId());
	}
	}
	
	public void excluirPorId(int id) throws ConsistenciaException {
		
		Log.info("Service: excluindo a transacao de id: {}", id);
		
		buscarPorNumeroCartao(id);
		
		transacaoRepository.deleteById(id);
	}
}
