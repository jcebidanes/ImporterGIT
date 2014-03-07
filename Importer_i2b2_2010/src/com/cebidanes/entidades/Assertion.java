package com.cebidanes.entidades;

import javax.persistence.Entity;

@Entity
public class Assertion extends DominioBase {

	public Assertion(){}
	
	public Assertion(String valor) {
		setValor(valor);
	}

}
