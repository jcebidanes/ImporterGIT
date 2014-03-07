package com.cebidanes.base;

public class Elemento {
	
	String conceito;
	String tipo;
	String assertion;

	public Elemento(String conceito, String tipo, String assertion) {
		super();
		this.conceito = conceito;
		this.tipo = tipo;
		this.assertion = assertion;
	}

	public String getConceito() {
		return conceito;
	}

	public void setConceito(String conceito) {
		this.conceito = conceito;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getAssertion() {
		return assertion;
	}

	public void setAssertion(String assertion) {
		this.assertion = assertion;
	}
}
