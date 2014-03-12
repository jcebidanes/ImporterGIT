package com.cebidanes.entidades;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Table(indexes={@Index(columnList="valor")})
@Entity
public class Assertion extends DominioBase {

	public Assertion(){}
	
	public Assertion(String valor) {
		setValor(valor);
	}

}
