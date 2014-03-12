package com.cebidanes.entidades;

import javax.persistence.Entity;

@Entity
public class Conceito extends DominioBase {
	
	public Conceito(){}
	
	public Conceito(String valor){
		this.valor = valor;		
	}
}
