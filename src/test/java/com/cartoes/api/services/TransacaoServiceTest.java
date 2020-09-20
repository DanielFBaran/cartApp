package com.cartoes.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.text.ParseException;
 
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cartoes.api.entities.Cartao;
import com.cartoes.api.repositories.CartaoRepository;
import com.cartoes.api.entities.Transacao;
import com.cartoes.api.repositories.TransacaoRepository;
import com.cartoes.api.utils.ConsistenciaException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TransacaoServiceTest {
	
	@MockBean
    private TransacaoRepository transacaoRepository;
   	
	@Autowired
	private CartaoRepository cartaoRepository;
	
   	@Autowired
   	private TransacaoService transacaoService;

	@Test
	public void testBuscarPorNumeroCartao() throws ConsistenciaException {
		List<Transacao> listaTrans = new ArrayList<Transacao>();
		listaTrans.add(new Transacao());
		BDDMockito.given(transacaoRepository.findByNumeroCartao(Mockito.anyString()))
		.willReturn(Optional.of(listaTrans));
		
		Optional<List<Transacao>> resultado = transacaoService.buscarPorNumeroCartao("1111111111111");
     	
     	assertTrue(resultado.isPresent());
	}

//	@Test
	//public void testSalvar() throws ConsistenciaException, ParseException {
		//CARTAO
	//	Cartao cartTeste = new Cartao();
	//	cartTeste.setNumero("1111111111111");
	//	cartTeste.setDataValidade(new SimpleDateFormat("dd/MM/yyyy").parse("03/03/2023"));
		
		//TRANSACAO
	//	Transacao transTeste = new Transacao();
	//	transTeste.setCartao(cartTeste);

	//	BDDMockito.given(cartaoRepository.findByNumero(Mockito.anyString())).willReturn(Optional.of(cartTeste));
	//	BDDMockito.given(transacaoRepository.save(Mockito.any(Transacao.class))).willReturn(new Transacao());

	//	Transacao resultado = transacaoService.salvar(transTeste);

	//	assertNotNull(resultado);
	//}

}
