package com.cebidanes.base;

import java.io.File;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.cebidanes.carregador.CarregaAst;
import com.cebidanes.entidades.Assertion;
import com.cebidanes.entidades.Relacao;
import com.cebidanes.entidades.Tipo;

public class Main {

	public static void main(String[] args) throws Exception {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mestradoPU");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		try {

			Tipo[] tipoList = {new Tipo("problem"), new Tipo("treatment"), new Tipo("test")};
			for(Tipo t: tipoList){
				Long tipoCadastrado = em.createQuery("Select count(t) From Tipo t where t.valor=:tipo ", Long.class)
						.setParameter("tipo", t.getValor())
						.getSingleResult();
				
				if(tipoCadastrado < 1)	
					em.persist(t);
			}
			
			Assertion[] assertionList = {new Assertion("present"),
										 new Assertion("absent"),
										 new Assertion("possible"),
										 new Assertion("conditional"),
										 new Assertion("hypothetical"),
										 new Assertion("associated_with_someone_else")};			
			
			for(Assertion a: assertionList){
				Long assertionCadastrado = em.createQuery("Select count(a) From Assertion a Where a.valor=:assertion ", Long.class)
						.setParameter("assertion", a.getValor())
						.getSingleResult();
					
				if(assertionCadastrado < 1)	
					em.persist(a);
			}
			
			Relacao[] relacaoList = {new Relacao("TrIP"), 
					 				 new Relacao("TrWP"),
					 				 new Relacao("TrCP"),
					 				 new Relacao("TrAP"),
					 				 new Relacao("TrNAP "),
					 				 new Relacao("PIP"),
  									 new Relacao("TeRP"),
 									 new Relacao("TeCP ")};
			
			for(Relacao r: relacaoList){
				Long relacaoCadastrada = em.createQuery("Select count(r) From Relacao r Where r.valor=:relacao ", Long.class)
						.setParameter("relacao", r.getValor())
						.getSingleResult();
				if(relacaoCadastrada <1)
					em.persist(r);
			}
			
			
			CarregaAst ast  =  new CarregaAst(em);
			File[] arquivosAst = carregaArquivos("../Importer_i2b2_2010/arquivos/ast");
			
			for(File f: arquivosAst){
				System.out.println(f.getPath());
				ast.carregaArquivoAst(f.getPath());
			}
			
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		
	}
	
	public static File[] carregaArquivos(String path) {
		File diretorio = new File(path);  
		return diretorio.listFiles();  
	}
	

}
