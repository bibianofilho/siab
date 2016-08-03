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

public class FichaHaNgc extends SIABBusiness{
	static LitebaseConnection driver ;
	
	
	public static FichaHaNgc criarInstancia()
	{driver = LitebaseConnection.getInstance(CREATORID);
		return new FichaHaNgc();
	}
	
	public void InserteFichaHa(Hashtable voFichaHa) throws Exception{
		driver.executeUpdate(" INSERT INTO FICHAHA ( CD_SEGMENTO,CD_AREA,CD_MICROAREA,NR_FAMILIA, CD_PACIENTE,CD_ANO, FL_FUMANTE, DS_OBSERVACAO,STATUS)" +
		                     " VALUES("+voFichaHa.get("CD_SEGMENTO")+","+voFichaHa.get("CD_AREA")+","+voFichaHa.get("CD_MICROAREA")+","+voFichaHa.get("NR_FAMILIA")+","+
		                     voFichaHa.get("CD_PACIENTE")+","+(new Date()).getYear()+",'"+voFichaHa.get("FL_FUMANTE")+"','"+
		                     voFichaHa.get("DS_OBSERVACAO")+"',"+BBPDBTemp.SSK_RECORD_INSERTED+")");

		driver.executeUpdate(" INSERT INTO FICHAHAACOMP (CD_SEGMENTO,CD_AREA,CD_MICROAREA,NR_FAMILIA, CD_PACIENTE, CD_MES,CD_ANO, DT_VISITA, FL_DIETA, FL_MEDICACAO,FL_EXERCICIO, DS_PRESSAO,DT_ULTCONSULTA,STATUS)" +
						     " VALUES("+voFichaHa.get("CD_SEGMENTO")+","+voFichaHa.get("CD_AREA")+","+voFichaHa.get("CD_MICROAREA")+","+voFichaHa.get("NR_FAMILIA")+","+voFichaHa.get("CD_PACIENTE")+","+voFichaHa.get("CD_MES")+","+voFichaHa.get("CD_ANO")+",'"+voFichaHa.get("DT_VISITA")+"','"+
						     voFichaHa.get("FL_DIETA")+"','"+voFichaHa.get("FL_MEDICACAO")+"','"+voFichaHa.get("FL_EXERCICIO")+"','"+
						     voFichaHa.get("DS_PRESSAO")+"','"+voFichaHa.get("DT_ULTCONSULTA")+"',"+BBPDBTemp.SSK_RECORD_INSERTED+")");
			
	}
	
	public void DeleteFichaHa(long cdFamilia,long cdpaciente) throws Exception{		
		driver.executeUpdate(" UPDATE FICHAHA SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia+" and  CD_PACIENTE = "+cdpaciente);
		driver.executeUpdate(" UPDATE FICHAHAACOMP SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia+" and  CD_PACIENTE = "+cdpaciente);
	}
	
	public void DeleteFichaHaFamilia(long cdFamilia) throws Exception{		
		driver.executeUpdate(" UPDATE FICHAHA SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia);
		driver.executeUpdate(" UPDATE FICHAHAACOMP SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia);
	}
	
	public void UpdateFichaHa(Hashtable voFichaHa) throws Exception{
		ResultSet rs =driver.executeQuery(" SELECT STATUS FROM FICHAHA WHERE NR_FAMILIA ="+voFichaHa.get("NR_FAMILIA")+
				" and CD_PACIENTE = "+voFichaHa.get("CD_PACIENTE")+" and CD_ANO = "+voFichaHa.get("CD_ANO")+" AND   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		if(rs.next()){
			String flStatus=null;		
			if(rs.getInt("STATUS")==BBPDBTemp.SSK_RECORD_NULL){
				flStatus = ""+BBPDBTemp.SSK_RECORD_UPDATED;
			}else{
				flStatus = rs.getString("STATUS");
			}	
			driver.executeUpdate(" UPDATE FICHAHA SET FL_FUMANTE='"+voFichaHa.get("FL_FUMANTE")+"', DS_OBSERVACAO='"+voFichaHa.get("DS_OBSERVACAO")+"'," +
								 " STATUS="+flStatus+
								 " WHERE NR_FAMILIA ="+voFichaHa.get("NR_FAMILIA")+" and CD_PACIENTE = "+voFichaHa.get("CD_PACIENTE")+" AND" +
								 " STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		}	
		ResultSet rsAcomp =driver.executeQuery(" SELECT STATUS FROM FICHAHAACOMP WHERE NR_FAMILIA ="+voFichaHa.get("NR_FAMILIA")+" and CD_PACIENTE = "+voFichaHa.get("CD_PACIENTE")+" and " +
				  " CD_MES="+voFichaHa.get("CD_MES")+" AND CD_ANO = "+voFichaHa.get("CD_ANO")+" AND   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		if(rsAcomp.next()){
			String flStatus=null;		
			if(rsAcomp.getInt("STATUS")==BBPDBTemp.SSK_RECORD_NULL){
				flStatus = ""+BBPDBTemp.SSK_RECORD_UPDATED;
			}else{
				flStatus = rsAcomp.getString("STATUS");
			}
			driver.executeUpdate(" UPDATE FICHAHAACOMP SET  DT_VISITA='"+voFichaHa.get("DT_VISITA")+"', FL_DIETA='"+voFichaHa.get("FL_DIETA")+"'," +
								" FL_MEDICACAO='"+voFichaHa.get("FL_MEDICACAO")+"', FL_EXERCICIO='"+voFichaHa.get("FL_EXERCICIO")+"', DS_PRESSAO='"+voFichaHa.get("DS_PRESSAO")+"',"+
								" DT_ULTCONSULTA='"+voFichaHa.get("DT_ULTCONSULTA")+"', STATUS="+flStatus+
								" WHERE NR_FAMILIA ="+voFichaHa.get("NR_FAMILIA")+" and CD_PACIENTE = "+voFichaHa.get("CD_PACIENTE")+" and CD_MES="+voFichaHa.get("CD_MES")+" AND "+
								" CD_ANO = "+voFichaHa.get("CD_ANO")+" AND   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		}else{
			driver.executeUpdate(" INSERT INTO FICHAHAACOMP (CD_SEGMENTO,CD_AREA,CD_MICROAREA,NR_FAMILIA, CD_PACIENTE, CD_MES,CD_ANO, DT_VISITA, FL_DIETA, FL_MEDICACAO,FL_EXERCICIO, DS_PRESSAO,DT_ULTCONSULTA,STATUS)" +
				     " VALUES("+voFichaHa.get("CD_SEGMENTO")+","+voFichaHa.get("CD_AREA")+","+voFichaHa.get("CD_MICROAREA")+","+voFichaHa.get("NR_FAMILIA")+","+voFichaHa.get("CD_PACIENTE")+","+voFichaHa.get("CD_MES")+","+voFichaHa.get("CD_ANO")+",'"+voFichaHa.get("DT_VISITA")+"','"+
				     voFichaHa.get("FL_DIETA")+"','"+voFichaHa.get("FL_MEDICACAO")+"','"+voFichaHa.get("FL_EXERCICIO")+"','"+
				     voFichaHa.get("DS_PRESSAO")+"','"+voFichaHa.get("DT_ULTCONSULTA")+"',"+BBPDBTemp.SSK_RECORD_INSERTED+")");
		}
	
	}
	
	public ResultSet getFichaHa(long cdFamilia, long cdpaciente) throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHAHA WHERE  NR_FAMILIA ="+cdFamilia+" and CD_PACIENTE = "+cdpaciente+" and   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		return rs;		
	}
	public ResultSet GetFichaHaAcomp(long cdFamilia, long cdpaciente) throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHAHAACOMP WHERE NR_FAMILIA ="+cdFamilia+" and CD_PACIENTE ="+cdpaciente+" and   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		return rs;		
	}
	
	public ResultSet ListaFichaAll() throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHAHA WHERE  STATUS !="+BBPDBTemp.SSK_RECORD_DELETED );
		return rs;		
	}
	
	public boolean ExistsPacienteHa(long cdFamilia, long cdpaciente) throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHAHA WHERE  NR_FAMILIA ="+cdFamilia+" and CD_PACIENTE = "+cdpaciente+" AND  STATUS !="+BBPDBTemp.SSK_RECORD_DELETED );
		if(rs.next()){
			return true;	
		}else{
			return false;
		}			
	}
}
