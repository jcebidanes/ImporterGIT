
package com.cebidanes.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="conceito_tipo_assertion")
public class ConceitoTipoAssertion {
	
	@Id
	@GeneratedValue
	Long id;
	
	@ManyToOne
	Conceito conceito;
	
	@ManyToOne
	Tipo tipo;
	
	@ManyToOne
	Assertion assertion;

	public ConceitoTipoAssertion(){}
	
	public ConceitoTipoAssertion(Conceito conceito, Tipo tipo, Assertion assertion){
		this.conceito = conceito;
		this.tipo = tipo;
		this.assertion = assertion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Conceito getConceito() {
		return conceito;
	}

	public void setConceito(Conceito conceito) {
		this.conceito = conceito;
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
