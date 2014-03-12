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

import com.cebidanes.entidades.Conceito;
import com.cebidanes.entidades.ConceitoTipoAssertion;
import com.cebidanes.entidades.Tipo;

public class CarregaCon {
	
	Integer i = 0;
	EntityManager em;
	ArrayList<String> conceitos = new ArrayList<String>();
	ArrayList<String> tipos = new ArrayList<String>();

	public CarregaCon(EntityManager em) {
		this.em = em;
	}
	
	public void carregaArquivoCon(String path) throws NoResultException {
		
		try {
			FileReader fr = new FileReader(path);
			Scanner scanner = new Scanner(fr);
			Scanner arquivo = scanner.useDelimiter("\\|\\||\n");

			while(arquivo.hasNext()){
				String conceito = arquivo.next();
				String tipo = arquivo.next();
				
				String valorConceito = pegaValores(conceito);
				String valorTipo = pegaValores(tipo);
				
				cadastraConceitoAndTipo(new Conceito(valorConceito), new Tipo(valorTipo));
			}
			scanner.close();
			fr.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

	public ConceitoTipoAssertion cadastraConceitoAndTipo(Conceito conceitoCadastrado, Tipo tipoCadastrado) {
		
		if(i++ % 20 == 0){
			em.flush();
			em.clear();
		}
		
		Conceito conceitoBusca = null;
		ConceitoTipoAssertion cta = null;
		
		if(conceitoCadastrado.getId() == null){
			conceitoBusca = buscaConceito(conceitoCadastrado);
		}
		
		if(conceitoBusca == null){
			
			em.persist(conceitoCadastrado);
			
			
			if(tipoCadastrado.getId() == null)
				tipoCadastrado = buscaTipo(tipoCadastrado);
			
			cta = new ConceitoTipoAssertion(conceitoCadastrado, tipoCadastrado, null);
			
			em.persist(cta);
			
			
			return cta; 
			
		}else{
			
			ConceitoTipoAssertion conceitoTipoAssertionCadastrado = null;
			
			try{
				conceitoTipoAssertionCadastrado = em.createQuery(
						 " Select cta From ConceitoTipoAssertion cta " +
						 " left join cta.conceito c" +
						 " left join cta.tipo t" +
						 " where c.valor=:conceito and t.valor=:tipo", ConceitoTipoAssertion.class)
						.setParameter("conceito", conceitoCadastrado.getValor())
						.setParameter("tipo", tipoCadastrado.getValor())
						.getSingleResult();
				
			}catch(NoResultException e){
					
				if(tipoCadastrado.getId() == null)
					tipoCadastrado = buscaTipo(tipoCadastrado);
				
				cta = new ConceitoTipoAssertion(conceitoBusca, tipoCadastrado, null);
				em.persist(cta);
				
				return cta;
			}
			return conceitoTipoAssertionCadastrado;
		}
	}

	private Tipo buscaTipo(Tipo tipo) {
		try{
			Tipo tipoCadastrado = em.createQuery("Select t From Tipo t where t.valor=:tipo ", Tipo.class)
					.setParameter("tipo", tipo.getValor())
					.getSingleResult();
			return tipoCadastrado;
		}catch(NoResultException e){
			return null;
		}
	}

	private Conceito buscaConceito(Conceito conceito) {
		try{
			Conceito conceitoCadastrado = em.createQuery(
					 " Select c From Conceito c where c.valor=:conceito ", Conceito.class)
					.setParameter("conceito", conceito.getValor())
					.getSingleResult();
			return conceitoCadastrado;
		}catch(NoResultException e){
			return null;
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
