package com.cartoes.api.controllers;
 
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
 
import java.util.Optional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
 
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
 
import com.cartoes.api.dtos.TransacaoDto;
import com.cartoes.api.entities.Transacao;
import com.cartoes.api.services.TransacaoService;
import com.cartoes.api.entities.Cartao;
import com.cartoes.api.services.CartaoService;
import com.cartoes.api.entities.Cliente;
import com.cartoes.api.services.ClienteService;
import com.cartoes.api.utils.ConversaoUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
 
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TransacaoControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private TransacaoService transacaoService;
	
	@MockBean
	private CartaoService cartaoService;
	
	@MockBean
	private ClienteService clienteService;
	
	private Transacao CriarTransacaoTeste() throws ParseException {
		Cliente clTeste = new Cliente();
		
		clTeste.setId(1);
		clTeste.setNome("Daniel");
		clTeste.setCpf("09027472904");
		clTeste.setUf("PR");
		
		Cartao cartTeste = new Cartao();
		
		cartTeste.setId(1);
		cartTeste.setNumero("1111111111111111");
		cartTeste.setBloqueado(false);
		cartTeste.setCliente(clTeste);
		
		Transacao transTeste = new Transacao();
		
		transTeste.setId(1);
		transTeste.setDataTransacao(new SimpleDateFormat("dd/MM/yyyy").parse("28/06/2020"));
		transTeste.setCnpj("93828684000103");
		transTeste.setValor(51.96);
		transTeste.setQtdParcelas(1);
		transTeste.setJuros(1);
		transTeste.setCartao(cartTeste);
		
		return transTeste;
	}

	@Test
	@WithMockUser
	public void testBuscarPorNumeroCartao() throws Exception {
		List<Transacao> listaTrans = new ArrayList<Transacao>();
		Transacao trans = CriarTransacaoTeste();
		listaTrans.add(trans);
		
		BDDMockito.given(transacaoService.buscarPorNumeroCartao(Mockito.anyString()))
		.willReturn(Optional.of(listaTrans));
		
		mvc.perform(MockMvcRequestBuilders.get("/api/transacao/cartao/1111111111111111")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.dados.[0].id").value(trans.getId()))
				.andExpect(jsonPath("$.dados.[0].dataTransacao").value(trans.getDataTransacao()))
				.andExpect(jsonPath("$.dados.[0].cnpj").value(trans.getCnpj()))
				.andExpect(jsonPath("$.dados.[0].valor").value(trans.getValor()))
				.andExpect(jsonPath("$.dados.[0].qdtParcelas").value(trans.getQtdParcelas()))
				.andExpect(jsonPath("$.dados.[0].juros").value(trans.getJuros()))
				.andExpect(jsonPath("$.dados.[0].cartaoNumero").value(trans.getCartao().getNumero()))
				.andExpect(jsonPath("$.erros").isEmpty());
	}

	@Test
	@WithMockUser
	public void testSalvar() throws Exception{
		Transacao trans = CriarTransacaoTeste();
		TransacaoDto transDto = ConversaoUtils.Converter(trans);
		transDto.setDataTransacao("28/06/2020");
		
		String json = new ObjectMapper().writeValueAsString(transDto);
		 BDDMockito.given(transacaoService.salvar(Mockito.any(Transacao.class)))
         .willReturn(trans);
		 
		 mvc.perform(MockMvcRequestBuilders.post("/api/transacao")
		            .content(json)
		            .contentType(MediaType.APPLICATION_JSON)
		            .accept(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk())
		            .andExpect(jsonPath("$.dados.id").value(trans.getId()))
		            .andExpect(jsonPath("$.dados.cnpj").value(trans.getCnpj()))
		            .andExpect(jsonPath("$.dados.valor").value(trans.getValor()))
		            .andExpect(jsonPath("$.dados.qdtParcelas").value(trans.getQtdParcelas()))
		            .andExpect(jsonPath("$.dados.juros").value(trans.getJuros()))
		            .andExpect(jsonPath("$.dados.cartaoNumero").value(trans.getCartao().getNumero()))
		            .andExpect(jsonPath("$.erros").isEmpty());
	}

}
