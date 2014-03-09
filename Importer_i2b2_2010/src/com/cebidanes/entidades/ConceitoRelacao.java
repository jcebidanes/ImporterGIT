
package com.cebidanes.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="conceito_relacao")
public class ConceitoRelacao {
	
	@Id
	@GeneratedValue
	Long id;
	
	@ManyToOne
	Conceito conceitoA;
	
	@ManyToOne
	Relacao relacoes;
	
	@ManyToOne
	Conceito conceitoB;

	public ConceitoRelacao(){}
	
	
}
