package com.cebidanes.entidades;

import javax.persistence.Entity;

@Entity
public class Tipo extends DominioBase {

	public Tipo() {}
	
	public Tipo(String valor){
		setValor(valor);
	}
	
}
