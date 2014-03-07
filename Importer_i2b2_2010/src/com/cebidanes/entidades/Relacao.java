package com.cebidanes.entidades;

import javax.persistence.Entity;

@Entity
public class Relacao extends DominioBase {
	
	public Relacao(){}
	
	public Relacao(String valor) {
		setValor(valor);
	}

}
