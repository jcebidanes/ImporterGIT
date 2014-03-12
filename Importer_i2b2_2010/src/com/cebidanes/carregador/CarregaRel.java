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
import com.cebidanes.entidades.ConceitoRelacao;
import com.cebidanes.entidades.Relacao;

public class CarregaRel {
	
	Integer i = 0;
	EntityManager em;
	ArrayList<String> conceitos = new ArrayList<String>();
	ArrayList<String> tipos = new ArrayList<String>();

	public CarregaRel(EntityManager em) {
		this.em = em;
	}
	
	public void carregaArquivoRel(String path) throws NoResultException{
		
		try {
			FileReader fr = new FileReader(path);
			Scanner scanner = new Scanner(fr);
			Scanner arquivo = scanner.useDelimiter("\\|\\||\n");

			while(arquivo.hasNext()){
				String conceitoA = arquivo.next();
				String relacao = arquivo.next();
				String conceitoB = arquivo.next();
				
				String valorConceitoA = pegaValores(conceitoA);
				String valorRelacao = pegaValores(relacao);
				String valorConceitoB = pegaValores(conceitoB);
				
				Long conceitoRelacaoCadastrado = em.createQuery(
						 " Select count(cr) From ConceitoRelacao cr " +
						 " left join cr.conceitoA ca " +
						 " left join cr.relacao r" +
						 " left join cr.conceitoB cb" +
						 " where ca.valor=:conceitoA and r.valor=:relacao and cb.valor=:conceitoB  ", Long.class)
						.setParameter("conceitoA", valorConceitoA)
						.setParameter("relacao", valorRelacao)
						.setParameter("conceitoB", valorConceitoB)
						.getSingleResult();
				
				if(conceitoRelacaoCadastrado < 1){
				
					Conceito conceitoACadastrado = buscaConceito(valorConceitoA);
					
					Relacao relacaoCadastrada = em.createQuery("Select r From Relacao r where r.valor=:relacao ", Relacao.class)
							.setParameter("relacao", valorRelacao)
							.getSingleResult();
					
					Conceito conceitoBCadastrado = buscaConceito(valorConceitoB);
					
					em.persist(new ConceitoRelacao(conceitoACadastrado, relacaoCadastrada, conceitoBCadastrado));
					
					if(i++ % 20 == 0){
						em.flush();
						em.clear();
					}
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

	private Conceito buscaConceito(String valorConceito) {
		Conceito conceitoACadastrado = em.createQuery("Select c From Conceito c where c.valor=:conceito ", Conceito.class)
				.setParameter("conceito", valorConceito)
				.getSingleResult();
		return conceitoACadastrado;
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
