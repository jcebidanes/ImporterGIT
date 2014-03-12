package com.cebidanes.base;

import java.io.File;
import java.nio.file.FileSystemNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.cebidanes.carregador.CarregaAst;
import com.cebidanes.carregador.CarregaCon;
import com.cebidanes.carregador.CarregaRel;
import com.cebidanes.entidades.Assertion;
import com.cebidanes.entidades.Relacao;
import com.cebidanes.entidades.Tipo;

public class Main {

	public static void main(String[] args) throws Exception {
		
		File localBase;
		File diretorioCasa = new File("D:/jcebidanes/git/ImporterGIT");
		File diretorioECO = new File("C:/Users/jhonnatan.santos/git/ImporterGIT");
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mestradoPU");
		EntityManager em = emf.createEntityManager();
		
		
		try {
			
			if(diretorioECO.exists()){
				localBase = diretorioECO;
			}else if(diretorioCasa.exists()){
				localBase = diretorioCasa;
			}else{
				throw new FileSystemNotFoundException("Local Base não encontrado");
			}
			
			em.getTransaction().begin();
			Tipo[] tipoList = {new Tipo("problem"), new Tipo("treatment"), new Tipo("test")};
			for(Tipo t: tipoList){
				Long tipoCadastrado = em.createQuery("Select count(t) From Tipo t where t.valor=:tipo ", Long.class)
						.setParameter("tipo", t.getValor())
						.getSingleResult();
				
				if(tipoCadastrado < 1)	
					em.persist(t);
			}
			em.getTransaction().commit();
			System.out.println("##############  Tipos cadastrados");
			
			em.getTransaction().begin();
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
			em.getTransaction().commit();
			System.out.println("##############  Assertions cadastrados");
			
			em.getTransaction().begin();
			Relacao[] relacaoList = {new Relacao("TrIP"), 
					 				 new Relacao("TrWP"),
					 				 new Relacao("TrCP"),
					 				 new Relacao("TrAP"),
					 				 new Relacao("TrNAP"),
					 				 new Relacao("PIP"),
  									 new Relacao("TeRP"),
 									 new Relacao("TeCP")};
			
			for(Relacao r: relacaoList){
				Long relacaoCadastrada = em.createQuery("Select count(r) From Relacao r Where r.valor=:relacao ", Long.class)
						.setParameter("relacao", r.getValor())
						.getSingleResult();
				if(relacaoCadastrada <1)
					em.persist(r);
			}
			em.getTransaction().commit();
			System.out.println("##############  Relacoes cadastradas");
			
			em.getTransaction().begin();
			CarregaCon con  =  new CarregaCon(em);
			File[] arquivosCon = carregaArquivos(localBase+"/Importer_i2b2_2010/arquivos/concepts");
			
			for(File f: arquivosCon){
				System.out.println(f.getPath());
				con.carregaArquivoCon(f.getPath());
			}
			em.getTransaction().commit();
			System.out.println("##############  Con cadastrados");
			
			em.getTransaction().begin();
			CarregaAst ast  =  new CarregaAst(em);
			File[] arquivosAst = carregaArquivos(localBase+"/Importer_i2b2_2010/arquivos/ast");
			
			for(File f: arquivosAst){
				System.out.println(f.getPath());
				ast.carregaArquivoAst(f.getPath());
			}
			em.getTransaction().commit();
			System.out.println("##############  Ast cadastrados");
			
			em.getTransaction().begin();
			CarregaRel rel  =  new CarregaRel(em);
			File[] arquivosRel = carregaArquivos(localBase+"/Importer_i2b2_2010/arquivos/rel");
			
			for(File f: arquivosRel){
				System.out.println(f.getPath());
				rel.carregaArquivoRel(f.getPath());
			}
			em.getTransaction().commit();
			System.out.println("##############  Rel cadastrados");
			
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
