package com.cartoes.api.entities;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonBackReference;
@Entity
@Table(name = "transacao")
public class Transacao implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "data_Transacao" , nullable = false)
	private Date dataTransacao;
	
	@Column(name = "cnpj", nullable = false, length = 14)
	private String cnpj;
	
	@Column(name = "valor", nullable = false)
	private double valor;
	
	@Column(name = "qdt_Parcelas", nullable = false)
	private int qtdParcelas;
	
	@Column(name = "juros", nullable = false)
	private double juros;
	
	 @JsonBackReference
	 @ManyToOne(fetch = FetchType.EAGER)
	 private Cartao cartao;
	 
public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public Date getDataTransacao() {
	return dataTransacao;
}

public void setDataTransacao(Date dataTransacao) {
	this.dataTransacao = dataTransacao;
}

public String getCnpj() {
	return cnpj;
}

public void setCnpj(String cnpj) {
	this.cnpj = cnpj;
}

public double getValor() {
	return valor;
}

public void setValor(double valor) {
	this.valor = valor;
}

public int getQtdParcelas() {
	return qtdParcelas;
}

public void setQtdParcelas(int qtdParcelas) {
	this.qtdParcelas = qtdParcelas;
}

public double getJuros() {
	return juros;
}

public void setJuros(double juros) {
	this.juros = juros;
}

public Cartao getCartao() {
	return cartao;
}

public void setCartao(Cartao cartao) {
	this.cartao = cartao;
}
@Override
public String toString() {
return "Transacao[" + "id=" + id + ","
+ "dataTransacao=" + dataTransacao + ","
+ "cnpj=" + cnpj + ","
+ "valor=" + valor + ","
+ "qtdParcelas=" + qtdParcelas + ","
+ "juros=" + juros + ","
+ "cartao=" + cartao + "]";
}
}