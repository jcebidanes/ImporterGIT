package com.cebidanes.carregador;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cebidanes.base.Elemento;

public class CarregaAst {
	
	ArrayList<Elemento> elementos = new ArrayList<Elemento>();
	
	
	ArrayList<String> conceitos = new ArrayList<String>();
	ArrayList<String> tipos = new ArrayList<String>();
	ArrayList<String> assertions = new ArrayList<String>();
	
	public void carregaArquivoAst(String path) {
		
		try {
			FileReader fr = new FileReader(path);
			Scanner scanner = new Scanner(fr);
			Scanner arquivo = scanner.useDelimiter("\\|\\||\n");

			while(arquivo.hasNext()){
				String conceito = arquivo.next();
				String tipo = arquivo.next();
				String assertion = arquivo.next();
				
				Elemento e = new Elemento(pegaValores(conceito), pegaValores(tipo), pegaValores(assertion));
				elementos.add(e);
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
