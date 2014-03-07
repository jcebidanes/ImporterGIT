package com.cebidanes.base;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.cebidanes.carregador.PopulaTipo;

public class Main {

	public static void main(String[] args) throws Exception {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mestradoPU");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		try {
			
			//CarregaAst ast  =  new CarregaAst();
			PopulaTipo pt = new PopulaTipo(em);
			
			pt.carregaArquivoAst("../Importer_i2b2_2010/arquivos/0001.ast");
			
			//ast.carregaArquivoAst("../Importer_i2b2_2010/arquivos/0001.ast");
			
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		
		
		
		
		
		
		
	}

}
