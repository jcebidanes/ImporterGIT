package com.cebidanes.entidades;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Table(indexes={@Index(columnList="valor")})
@Entity
public class Tipo extends DominioBase {

	public Tipo(){}
	
	public Tipo(String valor){
		setValor(valor);
	}
	
}
