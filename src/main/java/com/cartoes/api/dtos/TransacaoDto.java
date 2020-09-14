package com.cartoes.api.dtos;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class TransacaoDto {
	private String id;
	@NotEmpty(message = "CNPJ não pode estar vazio. ")
	@Length(min = 14, max = 14, message = "CNPJ deve conter 14 caracteres. ")
	private String dataTransacao;
	private String cnpj;
	@NotEmpty(message = "O valor não pode estar vazio. ")
	@Length(min = 1, max = 10, message = "O valor deve conter 1 a 10 caracteres. ")
	private String valor;
	@NotEmpty(message = "A quantidade não pode estar vazia. ")
	@Length(min = 1, max = 2, message = "A quantidade deve conter 1 a 2 caracteres. ")
	private String qtdParcelas;
	@NotEmpty(message = "O campo juros não pode estar vazio. ")
	@Length(min = 1, max = 4, message = "O campo juros deve conter 1 a 4 caracteres. ")
	private String juros;
	 public String getId() {
		 return id;
		 }

		 public void setId(String id) {
		 this.id = id;
		 }
		 
		 public String getDataTransacao() {
			 return dataTransacao;
		 }
		 
		 public void setDataTransacao(String dataTransacao) {
			 this.dataTransacao = dataTransacao;
		 }

		 public String getCnpj() {
		 return cnpj;
		 }

		 public void setCnpj(String cnpj) {
		 this.cnpj = cnpj;
		 }

		 public String getValor() {
		 return valor;
		 }

		 public void setValor(String valor) {
		 this.valor = valor;
		 }

		 public String getQtdParcelas() {
		 return qtdParcelas;
		 }

		 public void setQtdParcelas(String qtdParcelas) {
		 this.qtdParcelas = qtdParcelas;
		 }

		 public String getJuros() {
		 return juros;
		 }

		 public void setJuros(String juros) {
		 this.juros = juros;
		 }
		 
		 

		 @Override
		 public String toString() {
			 return "Transacao[" + "id=" + id + ","
			 + "dataTransacao=" + dataTransacao + ","
			 + "cnpj=" + cnpj + ","
			 + "valor=" + valor + ","
			 + "qtdParcelas=" + qtdParcelas + ","
			 + "juros=" + juros + "]";
			 }
}
