package com.cartoes.api.repositories;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import java.util.Optional;

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
import com.cartoes.api.entities.Cliente;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TransacaoRepositoryTest {
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	@Autowired
	private CartaoRepository cartaoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	private Transacao transTeste;
	
	private Cartao cartTeste;
	
	private Cliente clTeste;
	
	private void CriarTransacaoTeste() throws ParseException {
		
		//CLIENTE
		clTeste = new Cliente();
		
		clTeste.setNome("Daniel");
		clTeste.setCpf("09027472904");
		clTeste.setUf("PR");
		clTeste.setDataAtualizacao(new SimpleDateFormat("dd/MM/yyy").parse("01/01/2020"));
		
		//CARTAO
		cartTeste = new Cartao();
		
		cartTeste.setNumero("1111111111111111");
		cartTeste.setDataValidade(new SimpleDateFormat("dd/MM/yyyy").parse("03/03/2023"));
		cartTeste.setBloqueado(false);
		cartTeste.setDataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("02/02/2022"));
		cartTeste.setCliente(clTeste);
		
		//TRANSACAO
		transTeste = new Transacao();
		
		transTeste.setDataTransacao(new SimpleDateFormat("dd/MM;yyyy").parse("28/06/2020"));
		transTeste.setCnpj("93828684000103");
		transTeste.setValor(51.96);
		transTeste.setQtdParcelas(1);
		transTeste.setJuros(1);
		transTeste.setCartao(cartTeste);
	}
	
	@Before
	public void setUp() throws Exception {
		CriarTransacaoTeste();
		clienteRepository.save(clTeste);
		cartaoRepository.save(cartTeste);
		transacaoRepository.save(transTeste);
	}
	@After
	public void tearDown() throws Exception {
	   clienteRepository.deleteAll();
	   cartaoRepository.deleteAll();
	   transacaoRepository.deleteAll();
	}
	@Test
	public void testFindByNumeroCartao() {
		Optional<List<Transacao>> trans = transacaoRepository.findByNumeroCartao(transTeste.getCartao().getNumero());
		List<Transacao> assertTrans = trans.get();
		
		assertEquals(transTeste.getCartao().getNumero(), assertTrans.get(0).getCartao().getNumero());
	}
}
