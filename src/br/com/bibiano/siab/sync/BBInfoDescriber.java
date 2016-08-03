package br.com.bibiano.siab.sync;

import litebase.LitebaseConnection;
import litebase.ResultSet;
import br.com.bibiano.siab.business.SIABBusiness;

/**
 * Classe Sync
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class BBInfoDescriber extends SIABBusiness{
	static LitebaseConnection driver ;
	
	public static BBInfoDescriber criarInstancia()
	{
		
		return new BBInfoDescriber();
	}
	
	public ResultSet getKeyTable(String nmTabela) throws Exception {
		driver = LitebaseConnection.getInstance(CREATORID);
		ResultSet rs = driver.executeQuery(" SELECT * FROM SIABDESCRIBER WHERE  NM_TABELA ='"+nmTabela+"' AND FL_CHAVE='S' ORDER BY NR_ORDEM");
		driver.closeAll();
		return rs;
	}
	
	public ResultSet getNotKeyTable(String nmTabela) throws Exception {
		driver = LitebaseConnection.getInstance(CREATORID);
		ResultSet rs = driver.executeQuery(" SELECT * FROM SIABDESCRIBER WHERE  NM_TABELA ='"+nmTabela+"' AND FL_CHAVE='N'  ORDER BY NR_ORDEM ");
		driver.closeAll();
		return rs;
	}
}
