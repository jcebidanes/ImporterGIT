package com.cebidanes.carregador;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.cebidanes.entidades.Assertion;
import com.cebidanes.entidades.Conceito;
import com.cebidanes.entidades.ConceitoTipoAssertion;
import com.cebidanes.entidades.Tipo;

public class CarregaAst {
	
	EntityManager em;
	ArrayList<String> conceitos = new ArrayList<String>();
	ArrayList<String> tipos = new ArrayList<String>();
	ArrayList<String> assertions = new ArrayList<String>();

	public CarregaAst(EntityManager em) {
		this.em = em;
	}
	
	public void carregaArquivoAst(String path) throws NoResultException {
		
		try {
			FileReader fr = new FileReader(path);
			Scanner scanner = new Scanner(fr);
			Scanner arquivo = scanner.useDelimiter("\\|\\||\n");

			while(arquivo.hasNext()){
				String conceito = arquivo.next();
				String tipo = arquivo.next();
				String assertion = arquivo.next();
				
				String valorConceito = pegaValores(conceito);
				String valorTipo = pegaValores(tipo);
				String valorAssertion = pegaValores(assertion);
				
				ConceitoTipoAssertion conceitoTipoAssertionCadastrado = null;
				
				conceitoTipoAssertionCadastrado = em.createQuery(
						 " Select cta From ConceitoTipoAssertion cta " +
						 " left join cta.conceito c" +
						 " left join cta.tipo t" +
						 " left join cta.assertion a" +
						 " where c.valor=:conceito and t.valor=:tipo ", ConceitoTipoAssertion.class)
						.setParameter("conceito", valorConceito)
						.setParameter("tipo", valorTipo)
						.getSingleResult();
				
				if(conceitoTipoAssertionCadastrado != null){
					
					if(conceitoTipoAssertionCadastrado.getAssertion() == null){
					
						Assertion assertionCadastrado = em.createQuery("Select a From Assertion a Where a.valor=:assertion ", Assertion.class)
							.setParameter("assertion", valorAssertion)
							.getSingleResult();
						
						conceitoTipoAssertionCadastrado.setAssertion(assertionCadastrado);
					
						em.persist(conceitoTipoAssertionCadastrado);
					}
				}else{
					
					CarregaCon con = new CarregaCon(em);
					conceitoTipoAssertionCadastrado = con.cadastraConceitoAndTipo(new Conceito(valorConceito), new Tipo(valorTipo));
						
					Assertion assertionCadastrado = em.createQuery("Select a From Assertion a Where a.valor=:assertion ", Assertion.class)
							.setParameter("assertion", valorAssertion)
							.getSingleResult();
					
					conceitoTipoAssertionCadastrado.setAssertion(assertionCadastrado);
					
					em.persist(conceitoTipoAssertionCadastrado);
					
				}
				
			}
			scanner.close();
			fr.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public String pegaValores(String valor){
		Pattern pattern = Pattern.compile("\"(.*?)\"");
		Matcher matcher = pattern.matcher(valor);
		
		if (matcher.find()){
		    return matcher.group(1);
		}
		return null;
	}

}
