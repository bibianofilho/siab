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

public class LoginNgc extends SIABBusiness{
	static LitebaseConnection driver ;
	
	public static LoginNgc criarInstancia()
	{
		driver = LitebaseConnection.getInstance(CREATORID);
		return new LoginNgc();
	}	
	
	public boolean ValidaSenha(String login,String senha) throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM AGENTE WHERE DS_LOGIN = '"+login+"' AND DS_SENHA='"+senha+"'" );
		if(rs.next()){
			return true;
		}else{
			return false;
		}
	}
	
	public int getAnoUltSinc() throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM AGENTE " );
		if(rs.next()){
			return rs.getInt("CD_ANO");
		}else{
			return 0;
		}
	}
}
