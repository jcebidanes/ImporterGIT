
package com.cebidanes.entidades;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Conceito extends DominioBase {
	
	@ManyToOne
	Tipo tipo;
	
	@ManyToOne
	Assertion assertion;

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
	
}
