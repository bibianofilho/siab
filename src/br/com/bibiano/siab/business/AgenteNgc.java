package br.com.bibiano.siab.business;

import litebase.LitebaseConnection;
import litebase.ResultSet;

/**
 * Classe Business
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */


public class AgenteNgc extends SIABBusiness{
	
	
	public static AgenteNgc criarInstancia()
	{
		return new AgenteNgc();
	}	
	
	public ResultSet getSegAreaMicroa() throws Exception{
		LitebaseConnection driver = LitebaseConnection.getInstance(CREATORID);		
		ResultSet rs = driver.executeQuery(" SELECT * FROM AGENTE");
		return rs;
	}
	
	public ResultSet getAgente() throws Exception{
		LitebaseConnection driver = LitebaseConnection.getInstance(CREATORID);		
		ResultSet rs = driver.executeQuery(" SELECT * FROM AGENTE");
		return rs;
	}
}
