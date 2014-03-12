package com.cebidanes.entidades;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Table(indexes={@Index(columnList="valor")})
@Entity
public class Relacao extends DominioBase {
	
	public Relacao(){}
	
	public Relacao(String valor) {
		setValor(valor);
	}

}
