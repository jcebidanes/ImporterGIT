package com.cebidanes.entidades;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Index;


@Table(indexes={@Index(columnList="valor")})
@Entity
public class Conceito extends DominioBase {
	
	public Conceito(){}
	
	public Conceito(String valor){
		this.valor = valor;		
	}
}
