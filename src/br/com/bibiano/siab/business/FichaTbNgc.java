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

public class FichaTbNgc extends SIABBusiness{
	static LitebaseConnection driver ;
	
	public static FichaTbNgc criarInstancia()
	{
		driver = LitebaseConnection.getInstance(CREATORID);
		return new FichaTbNgc();
	}
	
	public void InserteFichaTb(Hashtable voFichaTb) throws Exception{		
		driver.executeUpdate(" INSERT INTO FICHATB ( CD_SEGMENTO, CD_AREA, CD_MICROAREA, NR_FAMILIA, CD_PACIENTE, CD_ANO, NUM_COMUNICANTES,NUM_COMUNICANTES5, DS_OBSERVACAO,STATUS)" +
		                     " VALUES("+voFichaTb.get("CD_SEGMENTO")+","+voFichaTb.get("CD_AREA")+","+voFichaTb.get("CD_MICROAREA")+","+voFichaTb.get("NR_FAMILIA")+","+voFichaTb.get("CD_PACIENTE")+","+(new Date()).getYear()+","+voFichaTb.get("NUM_COMUNICANTES")+","+voFichaTb.get("NUM_COMUNICANTES5")+",'"+
		                     voFichaTb.get("DS_OBSERVACAO")+"',"+BBPDBTemp.SSK_RECORD_INSERTED+")");

		driver.executeUpdate(" INSERT INTO FICHATBACOMP ( CD_SEGMENTO, CD_AREA, CD_MICROAREA, NR_FAMILIA, CD_PACIENTE, CD_MES, CD_ANO, DT_VISITA, FL_MEDICACAO, FL_REAINDESE, DT_CONSULTA, FL_ESCARRO, NUM_EXAMINADOS, NUM_COMBCG,STATUS)" +
						     " VALUES("+voFichaTb.get("CD_SEGMENTO")+","+voFichaTb.get("CD_AREA")+","+voFichaTb.get("CD_MICROAREA")+","+voFichaTb.get("NR_FAMILIA")+","+voFichaTb.get("CD_PACIENTE")+
						     ","+voFichaTb.get("CD_MES")+","+voFichaTb.get("CD_ANO")+",'"+voFichaTb.get("DT_VISITA")+"','"+voFichaTb.get("FL_MEDICACAO")+"','"+voFichaTb.get("FL_REAINDESE")+"',"+
						     " '"+voFichaTb.get("DT_CONSULTA")+"','"+voFichaTb.get("FL_ESCARRO")+"',"+voFichaTb.get("NUM_EXAMINADOS")+","+voFichaTb.get("NUM_COMBCG")+","+BBPDBTemp.SSK_RECORD_INSERTED+")");
			
	}
	
	public void DeleteFichaTb(long cdFamilia,long cdpaciente) throws Exception{	
		driver = LitebaseConnection.getInstance(CREATORID);
		driver.executeUpdate(" UPDATE FICHATB SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia+" and  CD_PACIENTE = "+cdpaciente);
		driver.executeUpdate(" UPDATE FICHATBACOMP SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia+" and  CD_PACIENTE = "+cdpaciente);
	}
	
	public void DeleteFichaTbFamilia(long cdFamilia) throws Exception{	
		driver = LitebaseConnection.getInstance(CREATORID);
		driver.executeUpdate(" UPDATE FICHATB SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia);
		driver.executeUpdate(" UPDATE FICHATBACOMP SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia);
	}
	
	public void UpdateFichaTb(Hashtable voFichaTb) throws Exception{
		driver = LitebaseConnection.getInstance(CREATORID);
		ResultSet rs =driver.executeQuery(" SELECT STATUS FROM FICHATB WHERE NR_FAMILIA ="+voFichaTb.get("NR_FAMILIA")+
				" and CD_PACIENTE = "+voFichaTb.get("CD_PACIENTE")+" and CD_ANO = "+voFichaTb.get("CD_ANO")+" AND   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		if(rs.next()){
			String flStatus=null;		
			if(rs.getInt("STATUS")==BBPDBTemp.SSK_RECORD_NULL){
				flStatus = ""+BBPDBTemp.SSK_RECORD_UPDATED;
			}else{
				flStatus = rs.getString("STATUS");
			}	
			driver.executeUpdate(" UPDATE FICHATB SET NUM_COMUNICANTES="+voFichaTb.get("NUM_COMUNICANTES")+", NUM_COMUNICANTES5="+voFichaTb.get("NUM_COMUNICANTES5")+"," +
								 " DS_OBSERVACAO='"+voFichaTb.get("DS_OBSERVACAO")+"', STATUS="+flStatus+
								 " WHERE NR_FAMILIA ="+voFichaTb.get("NR_FAMILIA")+" and CD_PACIENTE = "+voFichaTb.get("CD_PACIENTE")+" AND "+
								 " STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		}	
		ResultSet rsAcomp =driver.executeQuery(" SELECT STATUS FROM FICHATBACOMP WHERE NR_FAMILIA ="+voFichaTb.get("NR_FAMILIA")+" and CD_PACIENTE = "+voFichaTb.get("CD_PACIENTE")+" and " +
				  " CD_MES="+voFichaTb.get("CD_MES")+" AND CD_ANO = "+voFichaTb.get("CD_ANO")+" AND   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		if(rsAcomp.next()){
			String flStatus=null;		
			if(rsAcomp.getInt("STATUS")==BBPDBTemp.SSK_RECORD_NULL){
				flStatus = ""+BBPDBTemp.SSK_RECORD_UPDATED;
			}else{
				flStatus = rsAcomp.getString("STATUS");
			}	
			driver.executeUpdate(" UPDATE FICHATBACOMP SET  DT_VISITA='"+voFichaTb.get("DT_VISITA")+"', FL_MEDICACAO='"+voFichaTb.get("FL_MEDICACAO")+"'," +
					" FL_REAINDESE='"+voFichaTb.get("FL_REAINDESE")+"', DT_CONSULTA='"+voFichaTb.get("DT_CONSULTA")+"', FL_ESCARRO," +
					" NUM_EXAMINADOS="+voFichaTb.get("NUM_EXAMINADOS")+", NUM_COMBCG="+voFichaTb.get("NUM_COMBCG")+", STATUS="+flStatus+ 
					" WHERE NR_FAMILIA ="+voFichaTb.get("NR_FAMILIA")+" and CD_PACIENTE = "+voFichaTb.get("CD_PACIENTE")+" and CD_MES="+voFichaTb.get("CD_MES")+" AND "+
					" CD_ANO = "+voFichaTb.get("CD_ANO")+" AND STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		}else{
			driver.executeUpdate(" INSERT INTO FICHATBACOMP ( CD_SEGMENTO, CD_AREA, CD_MICROAREA, NR_FAMILIA, CD_PACIENTE, CD_MES, CD_ANO, DT_VISITA, FL_MEDICACAO, FL_REAINDESE, DT_CONSULTA, FL_ESCARRO, NUM_EXAMINADOS, NUM_COMBCG,STATUS)" +
					     " VALUES("+voFichaTb.get("CD_SEGMENTO")+","+voFichaTb.get("CD_AREA")+","+voFichaTb.get("CD_MICROAREA")+","+voFichaTb.get("NR_FAMILIA")+","+voFichaTb.get("CD_PACIENTE")+
					     ","+voFichaTb.get("CD_MES")+","+voFichaTb.get("CD_ANO")+",'"+voFichaTb.get("DT_VISITA")+"','"+voFichaTb.get("FL_MEDICACAO")+"','"+voFichaTb.get("FL_REAINDESE")+"',"+
					     " '"+voFichaTb.get("DT_CONSULTA")+"','"+voFichaTb.get("FL_ESCARRO")+"',"+voFichaTb.get("NUM_EXAMINADOS")+","+voFichaTb.get("NUM_COMBCG")+","+BBPDBTemp.SSK_RECORD_INSERTED+")");
		}
		
	}
	
	public ResultSet ListaFichaTb(long cdFamilia, long cdpaciente) throws Exception{
		driver = LitebaseConnection.getInstance(CREATORID);
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHATB WHERE  NR_FAMILIA ="+cdFamilia+"L and CD_PACIENTE = "+cdpaciente+"  and   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		return rs;		
	}
	public ResultSet ListaFichaTbAcomp(long cdFamilia, long cdpaciente) throws Exception{
		driver = LitebaseConnection.getInstance(CREATORID);
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHATBACOMP WHERE NR_FAMILIA ="+cdFamilia+" and CD_PACIENTE ="+cdpaciente+"  and   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		return rs;		
	}
	
	public ResultSet ListaFichaAll() throws Exception{
		driver = LitebaseConnection.getInstance(CREATORID);
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHATB WHERE  STATUS !="+BBPDBTemp.SSK_RECORD_DELETED );
		return rs;		
	}
	public boolean ExistsPacienteTb(long cdFamilia, long cdpaciente) throws Exception{
		driver = LitebaseConnection.getInstance(CREATORID);
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHATB WHERE  NR_FAMILIA ="+cdFamilia+" and CD_PACIENTE = "+cdpaciente+" AND  STATUS !="+BBPDBTemp.SSK_RECORD_DELETED );
		if(rs.next()){
			return true;	
		}else{
			return false;
		}			
	}
}
