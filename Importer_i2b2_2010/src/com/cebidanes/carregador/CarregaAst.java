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
import com.cebidanes.entidades.Tipo;

public class CarregaAst {
	
	EntityManager em;
	ArrayList<String> conceitos = new ArrayList<String>();
	ArrayList<String> tipos = new ArrayList<String>();
	ArrayList<String> assertions = new ArrayList<String>();

	public CarregaAst(EntityManager em) {
		this.em = em;
	}
	
	public void carregaArquivoAst(String path) {
		
		try {
			FileReader fr = new FileReader(path);
			Scanner scanner = new Scanner(fr);
			Scanner arquivo = scanner.useDelimiter("\\|\\||\n");

			while(arquivo.hasNext()){
				String conceito = arquivo.next();
				String tipo = arquivo.next();
				String assertion = arquivo.next();
				
				String valorTipo = pegaValores(tipo);
				String valorAssertion = pegaValores(assertion);
				
				try{
					Tipo tipoCadastrado = em.createQuery("Select t From Tipo t where t.valor=:tipo ", Tipo.class)
							.setParameter("tipo", valorTipo)
							.getSingleResult();
					
					Assertion assertionCadastrado = em.createQuery("Select a From Assertion a Where a.valor=:assertion ", Assertion.class)
							.setParameter("assertion", valorAssertion)
							.getSingleResult();
					
					em.persist(new Conceito(tipoCadastrado, assertionCadastrado, pegaValores(conceito)));
				
				}catch(NoResultException e){
					System.out.println(arquivo.nextLine());
					e.printStackTrace();
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
