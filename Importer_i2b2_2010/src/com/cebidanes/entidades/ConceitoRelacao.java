
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
	Relacao relacao;
	
	@ManyToOne
	Conceito conceitoB;

	public ConceitoRelacao(){}
	
	public ConceitoRelacao(Conceito conceitoA, Relacao relacao, Conceito conceitoB){
		this.conceitoA = conceitoA;
		this.relacao = relacao;
		this.conceitoB = conceitoB;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Conceito getConceitoA() {
		return conceitoA;
	}

	public void setConceitoA(Conceito conceitoA) {
		this.conceitoA = conceitoA;
	}

	public Relacao getRelacao() {
		return relacao;
	}

	public void setRelacao(Relacao relacao) {
		this.relacao = relacao;
	}

	public Conceito getConceitoB() {
		return conceitoB;
	}

	public void setConceitoB(Conceito conceitoB) {
		this.conceitoB = conceitoB;
	}
	
	
}
