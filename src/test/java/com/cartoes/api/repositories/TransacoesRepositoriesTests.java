package com.cartoes.api.repositories;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cartoes.api.entities.Transacao;
import com.cartoes.api.entities.Cartao;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TransacoesRepositoriesTests {
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	private Transacao transTeste;
	
	private Cartao cartaoTeste;
	
	private void CriarTransacaoTeste() throws ParseException {
		transTeste = new Transacao();
		
		transTeste.setDataTransacao(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020"));
		transTeste.setCnpj("80968413000101");
		transTeste.setValor(1500);
		transTeste.setQtdParcelas(3);
		transTeste.setJuros(1);
		cartaoTeste.setNumero("1111111111111");
	}
	
	@Before
	public void setUp() throws Exception {
		CriarTransacaoTeste();
		transacaoRepository.save(transTeste);
	}
	@After
	public void tearDown() throws Exception {
		transacaoRepository.deleteAll();
	}
	@Test
	public void testFindByNumeroCartao() {
		List<Transacao> transacao = transacaoRepository.findByNumeroCartao(transTeste.getCartao().getNumero()).get();
		assertEquals(transTeste.getCartao().getNumero(), ((Transacao) transacao).getCartao().getNumero());
	}
}
