
package com.cebidanes.entidades;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Conceito extends DominioBase {
	
	@ManyToOne
	Tipo tipo;
	
	@ManyToOne
	Assertion assertion;
	
	@ManyToMany
	List<Relacao> relacoes;

	public Conceito(){}
	
	public Conceito(Tipo tipo, Assertion aseAssertion, String valor){
		this.tipo = tipo;
		this.assertion = aseAssertion;
		this.valor = valor;
	}
	
	
	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Assertion getAssertion() {
		return assertion;
	}

	public void setAssertion(Assertion assertion) {
		this.assertion = assertion;
	}

	public List<Relacao> getRelacoes() {
		return relacoes;
	}

	public void setRelacoes(List<Relacao> relacoes) {
		this.relacoes = relacoes;
	}
	
}
