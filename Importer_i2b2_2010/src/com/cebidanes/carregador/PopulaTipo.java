package com.cebidanes.carregador;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;

import com.cebidanes.entidades.Assertion;
import com.cebidanes.entidades.Tipo;

public class PopulaTipo {
	
	EntityManager em;	
	ArrayList<Tipo> tipos = new ArrayList<Tipo>();
	ArrayList<Assertion> assertions = new ArrayList<Assertion>();
	
	public PopulaTipo(EntityManager em) {
		this.em = em;
	}
	
	public void carregaArquivoAst(String path) {
		
		try {
			FileReader fr = new FileReader(path);
			Scanner scanner = new Scanner(fr);
			Scanner arquivo = scanner.useDelimiter("\\|\\||\n");

			while(arquivo.hasNext()){
				arquivo.next();
				String tipo = arquivo.next();
				String assertion = arquivo.next();
				
				em.persist(new Tipo(pegaValores(tipo)));
				em.persist(new Assertion(pegaValores(assertion)));
				
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
