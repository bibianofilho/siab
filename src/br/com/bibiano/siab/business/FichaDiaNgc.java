package br.com.bibiano.siab.business;

import litebase.LitebaseConnection;
import litebase.ResultSet;
import waba.util.Date;
import waba.util.Hashtable;
import br.com.bibiano.siab.sync.BBPDBTemp;

/**
 * Classe Business
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */

public class FichaDiaNgc extends SIABBusiness{
	static LitebaseConnection driver ;
	
	public static FichaDiaNgc criarInstancia()
	{   
		driver = LitebaseConnection.getInstance(CREATORID);
		return new FichaDiaNgc();
	}
	

	public void InserteFichaDia(Hashtable voFichaDia) throws Exception{
		driver.executeUpdate(" INSERT INTO FICHADIA ( CD_SEGMENTO, CD_AREA, CD_MICROAREA, NR_FAMILIA, CD_PACIENTE, CD_ANO,  DS_OBSERVACAO,STATUS)" +
		                     " VALUES("+voFichaDia.get("CD_SEGMENTO")+","+voFichaDia.get("CD_AREA")+","+voFichaDia.get("CD_MICROAREA")+","+voFichaDia.get("NR_FAMILIA")+","+voFichaDia.get("CD_PACIENTE")+","+(new Date()).getYear()+",'"+voFichaDia.get("DS_OBSERVACAO")+"',"+BBPDBTemp.SSK_RECORD_INSERTED+")");

		driver.executeUpdate(" INSERT INTO FICHADIAACOMP ( CD_SEGMENTO, CD_AREA, CD_MICROAREA, NR_FAMILIA, CD_PACIENTE, CD_MES, CD_ANO, DT_VISITA, FL_DIETA, FL_EXERCICIO,FL_INSULINA, FL_HIPOGLICEMIANTE,DT_ULTCONSULTA,STATUS)" +
						     " VALUES("+voFichaDia.get("CD_SEGMENTO")+","+voFichaDia.get("CD_AREA")+","+voFichaDia.get("CD_MICROAREA")+","+voFichaDia.get("NR_FAMILIA")+","+voFichaDia.get("CD_PACIENTE")+","+voFichaDia.get("CD_MES")+","+voFichaDia.get("CD_ANO")+",'"+voFichaDia.get("DT_VISITA")+"','"+
						     voFichaDia.get("FL_DIETA")+"','"+voFichaDia.get("FL_EXERCICIO")+"','"+voFichaDia.get("FL_INSULINA")+"','"+voFichaDia.get("FL_HIPOGLICEMIANTE")+"','"+
						     voFichaDia.get("DT_ULTCONSULTA")+"',"+BBPDBTemp.SSK_RECORD_INSERTED+")");
	}
	
	public void DeleteFichaDia(long cdFamilia,long cdpaciente) throws Exception{
		driver.executeUpdate(" UPDATE FICHADIA SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia+" and  CD_PACIENTE = "+cdpaciente);
		driver.executeUpdate(" UPDATE FICHADIAACOMP SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia+" and  CD_PACIENTE = "+cdpaciente);
	}
	
	public void DeleteFichaDiaFamilia(long cdFamilia) throws Exception{		
		driver.executeUpdate(" UPDATE FICHADIA SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia);
		driver.executeUpdate(" UPDATE FICHADIAACOMP SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia);
	}
	
	public void UpdateFichaDia(Hashtable voFichaDia) throws Exception{
		// TODO EXEMPLO UPDATE
		ResultSet rs =driver.executeQuery(" SELECT STATUS FROM FICHADIA WHERE NR_FAMILIA ="+voFichaDia.get("NR_FAMILIA")+
				" and CD_PACIENTE = "+voFichaDia.get("CD_PACIENTE")+" and CD_ANO = "+voFichaDia.get("CD_ANO")+" AND   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		if(rs.next()){
			String flStatus=null;		
			if(rs.getInt("STATUS")==BBPDBTemp.SSK_RECORD_NULL){
				flStatus = ""+BBPDBTemp.SSK_RECORD_UPDATED;
			}else{
				flStatus = rs.getString("STATUS");
			}			
			driver.executeUpdate(" UPDATE FICHADIA SET  DS_OBSERVACAO='"+voFichaDia.get("DS_OBSERVACAO")+"', STATUS="+flStatus+
					 " WHERE NR_FAMILIA ="+voFichaDia.get("NR_FAMILIA")+" and CD_PACIENTE = "+voFichaDia.get("CD_PACIENTE")+" and "+
					 " STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		}
		
		
		ResultSet rsAcomp =driver.executeQuery(" SELECT STATUS FROM FICHADIAACOMP WHERE NR_FAMILIA ="+voFichaDia.get("NR_FAMILIA")+" and CD_PACIENTE = "+voFichaDia.get("CD_PACIENTE")+" and " +
										  " CD_MES="+voFichaDia.get("CD_MES")+" AND CD_ANO = "+voFichaDia.get("CD_ANO")+" AND   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		if(rsAcomp.next()){
			String flStatus=null;		
			if(rsAcomp.getInt("STATUS")==BBPDBTemp.SSK_RECORD_NULL){
				flStatus = ""+BBPDBTemp.SSK_RECORD_UPDATED;
			}else{
				flStatus = rsAcomp.getString("STATUS");
			}
			driver.executeUpdate(" UPDATE FICHADIAACOMP SET  DT_VISITA='"+voFichaDia.get("DT_VISITA")+"', FL_DIETA='"+voFichaDia.get("FL_DIETA")+"'," +
					" FL_EXERCICIO='"+voFichaDia.get("FL_EXERCICIO")+"', FL_INSULINA='"+voFichaDia.get("FL_INSULINA")+"'," +
				    " FL_HIPOGLICEMIANTE='"+voFichaDia.get("FL_HIPOGLICEMIANTE")+"',"+
					" DT_ULTCONSULTA='"+voFichaDia.get("DT_ULTCONSULTA")+"' AND STATUS="+flStatus+
					" WHERE NR_FAMILIA ="+voFichaDia.get("NR_FAMILIA")+" and CD_PACIENTE = "+voFichaDia.get("CD_PACIENTE")+" and" +
					" CD_MES="+voFichaDia.get("CD_MES")+"AND CD_ANO = "+voFichaDia.get("CD_ANO")+" AND   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		}else{
			driver.executeUpdate(" INSERT INTO FICHADIAACOMP ( CD_SEGMENTO, CD_AREA, CD_MICROAREA, NR_FAMILIA, CD_PACIENTE, CD_MES, CD_ANO, DT_VISITA, FL_DIETA, FL_EXERCICIO,FL_INSULINA, FL_HIPOGLICEMIANTE,DT_ULTCONSULTA,STATUS)" +
				     " VALUES("+voFichaDia.get("CD_SEGMENTO")+","+voFichaDia.get("CD_AREA")+","+voFichaDia.get("CD_MICROAREA")+","+voFichaDia.get("NR_FAMILIA")+","+voFichaDia.get("CD_PACIENTE")+","+voFichaDia.get("CD_MES")+","+voFichaDia.get("CD_ANO")+",'"+voFichaDia.get("DT_VISITA")+"','"+
				     voFichaDia.get("FL_DIETA")+"','"+voFichaDia.get("FL_EXERCICIO")+"','"+voFichaDia.get("FL_INSULINA")+"','"+voFichaDia.get("FL_HIPOGLICEMIANTE")+"','"+
				     voFichaDia.get("DT_ULTCONSULTA")+"',"+BBPDBTemp.SSK_RECORD_INSERTED+")");
		}		
	}
	
	public ResultSet getFichaDia(long cdFamilia, long cdpaciente) throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHADIA WHERE  NR_FAMILIA ="+cdFamilia+" and CD_PACIENTE = "+cdpaciente+" and   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		return rs;		
	}
	public ResultSet GetFichaDiaAcomp(long cdFamilia, long cdpaciente) throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHADIAACOMP WHERE NR_FAMILIA ="+cdFamilia+" and CD_PACIENTE ="+cdpaciente+" and   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		return rs;		
	}
	
	public ResultSet ListaFichaAll() throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHADIA WHERE  STATUS !="+BBPDBTemp.SSK_RECORD_DELETED );
		return rs;		
	}
	
	public boolean ExistsPacienteDia(long cdFamilia, long cdpaciente) throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHADIA WHERE  NR_FAMILIA ="+cdFamilia+" and CD_PACIENTE = "+cdpaciente+" AND  STATUS !="+BBPDBTemp.SSK_RECORD_DELETED );
		if(rs.next()){
			return true;	
		}else{
			return false;
		}
			
	}
}
