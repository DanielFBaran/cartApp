package com.cartoes.api.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import com.cartoes.api.dtos.CartaoDto;
import com.cartoes.api.dtos.ClienteDto;
import com.cartoes.api.entities.Cartao;
import com.cartoes.api.entities.Cliente;
import com.cartoes.api.entities.Transacao;
import com.cartoes.api.dtos.TransacaoDto;
import com.cartoes.api.dtos.RegraDto;
import com.cartoes.api.dtos.UsuarioDto;
import com.cartoes.api.entities.Regras;
import com.cartoes.api.entities.Usuario;

public class ConversaoUtils {
public static Transacao Converter(TransacaoDto transDto) throws ParseException {
	Transacao trans = new Transacao();
	if (transDto.getId() != null && transDto.getId() != "")
		trans.setId(Integer.parseInt(transDto.getId()));
		trans.setCnpj(transDto.getCnpj());
		trans.setValor(Double.parseDouble(transDto.getValor()));
		trans.setQtdParcelas(Integer.parseInt(transDto.getQtdParcelas()));
		trans.setJuros(Double.parseDouble(transDto.getJuros()));
		//Cartao cartao = new Cartao();
		//cartao.setNumero(transDto.getNumero());
	return trans;
}
public static TransacaoDto Converter(Transacao trans) {
	TransacaoDto transDto = new TransacaoDto();
	
	transDto.setId(String.valueOf(trans.getId()));
	transDto.setCnpj(trans.getCnpj());
	transDto.setValor(String.valueOf(trans.getValor()));
	transDto.setQtdParcelas(String.valueOf(trans.getQtdParcelas()));
	transDto.setJuros(String.valueOf(trans.getJuros()));
	//transDto.set ALGUMA COISA CARTAO
	return transDto;
}
public static List<TransacaoDto> ConverterListaTransacao(List<Transacao> lista){

	 List<TransacaoDto> lst = new ArrayList<TransacaoDto>(lista.size());
	 for (Transacao trans : lista) {
		 lst.add(Converter(trans));
		 }

		 return lst;

		 }
 public static Cartao Converter(CartaoDto cartaoDto) throws ParseException {
 Cartao cartao = new Cartao();
 if (cartaoDto.getId() != null && cartaoDto.getId() != "")
 cartao.setId(Integer.parseInt(cartaoDto.getId()));
 cartao.setNumero(cartaoDto.getNumero());
 cartao.setDataValidade(new
SimpleDateFormat("dd/MM/yyyy").parse(cartaoDto.getDataValidade()));
 cartao.setBloqueado(Boolean.parseBoolean(cartaoDto.getBloqueado()));
 Cliente cliente = new Cliente();
 cliente.setId(Integer.parseInt(cartaoDto.getClienteId()));
 cartao.setCliente(cliente);
 return cartao;
 }

 public static CartaoDto Converter(Cartao cartao) {
 CartaoDto cartaoDto = new CartaoDto();

 cartaoDto.setId(String.valueOf(cartao.getId()));
 cartaoDto.setNumero(cartao.getNumero());
 cartaoDto.setDataValidade(cartao.getDataValidade().toString());
 cartaoDto.setBloqueado(String.valueOf(cartao.getBloqueado()));
 cartaoDto.setClienteId(String.valueOf(cartao.getCliente().getId()));
 return cartaoDto;
 }

 public static List<CartaoDto> ConverterListaCartao(List<Cartao> lista){

 List<CartaoDto> lst = new ArrayList<CartaoDto>(lista.size());
 for (Cartao cartao : lista) {
	 lst.add(Converter(cartao));
	 }

	 return lst;

	 }
	 public static Cliente Converter(ClienteDto clienteDto) {
	 Cliente cliente = new Cliente();
	 if (clienteDto.getId() != null && clienteDto.getId() != "")
	 cliente.setId(Integer.parseInt(clienteDto.getId()));
	 cliente.setNome(clienteDto.getNome());
	 cliente.setCpf(clienteDto.getCpf());
	 cliente.setUf(clienteDto.getUf());
	 return cliente;
	 }

	 public static ClienteDto Converter(Cliente cliente) {
	 ClienteDto clienteDto = new ClienteDto();
	 clienteDto.setId(String.valueOf(cliente.getId()));
	 clienteDto.setNome(cliente.getNome());
	 clienteDto.setCpf(cliente.getCpf());
	 clienteDto.setUf(cliente.getUf());
	 return clienteDto;
	 }
	   	public static Usuario Converter(UsuarioDto usuarioDto) {
	   	 
         	Usuario usuario = new Usuario();
 
         	if (usuarioDto.getId() != null && usuarioDto.getId() != "")
                	usuario.setId(Integer.parseInt(usuarioDto.getId()));
 
         	usuario.setNome(usuarioDto.getNome());
         	usuario.setCpf(usuarioDto.getCpf());
         	usuario.setAtivo(Boolean.parseBoolean(usuarioDto.getAtivo()));
 
         	if (usuarioDto.getRegras() != null && usuarioDto.getRegras().size() > 0) {
 
                	usuario.setRegras(new ArrayList<Regras>());
                	for (RegraDto regraDto : usuarioDto.getRegras()) {
                		 
                       	Regras regra = new Regras();
                       	regra.setNome(regraDto.getNome());
 
                       	usuario.getRegras().add(regra);
 
                	}
 
         	}
 
         	return usuario;
 
   	}
 
   	public static UsuarioDto Converter(Usuario usuario) {
 
         	UsuarioDto usuarioDto = new UsuarioDto();
 
         	usuarioDto.setId(Integer.toString(usuario.getId()));
 
         	usuarioDto.setNome(usuario.getNome());
         	usuarioDto.setCpf(usuario.getCpf());
         	usuarioDto.setAtivo(Boolean.toString(usuario.getAtivo()));
 
         	if (usuario.getRegras() != null) {
                	
                	usuarioDto.setRegras(new ArrayList<RegraDto>());
 
                	for (int i = 0; i < usuario.getRegras().size(); i++) {
                       	
                       	RegraDto regraDto = new RegraDto();
                       	
                       	regraDto.setNome(usuario.getRegras().get(i).getNome());
                       	regraDto.setDescricao(usuario.getRegras().get(i).getDescricao());
                       	regraDto.setAtivo(usuario.getRegras().get(i).getAtivo());
                       	
                       	usuarioDto.getRegras().add(regraDto);
                       	
                	}
 
         	}
 
         	return usuarioDto;
 
   	}
 
   	public static RegraDto Converter(Regras regra) {
 
         	RegraDto regraDto = new RegraDto();
         	regraDto.setNome(regra.getNome());
         	regraDto.setDescricao(regra.getDescricao());
         	regraDto.setAtivo(regra.getAtivo());
 
         	return regraDto;
 
   	}
 
   	public static List<RegraDto> Converter(List<Regras> regras) {
 
         	List<RegraDto> regrasDto = new ArrayList<RegraDto>();
 
         	for (Regras regra : regras)
                	regrasDto.add(Converter(regra));
 
         	return regrasDto;
 
   	}

	}