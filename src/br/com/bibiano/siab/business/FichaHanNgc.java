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

public class FichaHanNgc extends SIABBusiness{

	static LitebaseConnection driver ;
	
	public static FichaHanNgc criarInstancia()
	{
		driver = LitebaseConnection.getInstance(CREATORID);
		return new FichaHanNgc();
	}
	
	public void InserteFichaHan(Hashtable voFichaHan) throws Exception{		
		driver.executeUpdate(" INSERT INTO FICHAHAN ( CD_SEGMENTO, CD_AREA, CD_MICROAREA, NR_FAMILIA,CD_PACIENTE,CD_ANO,NUM_COMUNICANTES, DS_OBSERVACAO,STATUS)" +
		                     " VALUES("+voFichaHan.get("CD_SEGMENTO")+","+voFichaHan.get("CD_AREA")+","+voFichaHan.get("CD_MICROAREA")+","+voFichaHan.get("NR_FAMILIA")+","+
		                     voFichaHan.get("CD_PACIENTE")+","+(new Date()).getYear()+","+voFichaHan.get("NUM_COMUNICANTES")+",'"+
		                     voFichaHan.get("DS_OBSERVACAO")+"',"+BBPDBTemp.SSK_RECORD_INSERTED+")");
				
		driver.executeUpdate(" INSERT INTO FICHAHANACOMP ( CD_SEGMENTO, CD_AREA, CD_MICROAREA, NR_FAMILIA, CD_PACIENTE, CD_MES,CD_ANO, DT_VISITA, FL_MEDICACAO, DT_ULTDOSE,FL_AUTOCUIDADOS, DT_CONSULTA,NUM_EXAMINADOS, NUM_COMBCG,STATUS)" +
						     " VALUES("+voFichaHan.get("CD_SEGMENTO")+","+voFichaHan.get("CD_AREA")+","+voFichaHan.get("CD_MICROAREA")+","+voFichaHan.get("NR_FAMILIA")+","+
						     voFichaHan.get("CD_PACIENTE")+","+voFichaHan.get("CD_MES")+","+voFichaHan.get("CD_ANO")+",'"+voFichaHan.get("DT_VISITA")+"','"+
						     voFichaHan.get("FL_MEDICACAO")+"','"+voFichaHan.get("DT_ULTDOSE")+"',"+" '"+voFichaHan.get("FL_AUTOCUIDADOS")+
						     "','"+voFichaHan.get("DT_CONSULTA")+"',"+voFichaHan.get("NUM_EXAMINADOS")+","+voFichaHan.get("NUM_COMBCG")+","+BBPDBTemp.SSK_RECORD_INSERTED+" )");
			
	}
	
	public void DeleteFichaHan(int cdFamilia,int cdpaciente) throws Exception{		
		driver.executeUpdate(" UPDATE FICHAHAN SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia+" and  CD_PACIENTE = "+cdpaciente);
		driver.executeUpdate(" UPDATE FICHAHANACOMP SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia+" and  CD_PACIENTE = "+cdpaciente);
		
	}
	
	public void DeleteFichaHanFamilia(long cdFamilia) throws Exception{
		driver.executeUpdate(" UPDATE FICHAHAN SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia);
		driver.executeUpdate(" UPDATE FICHAHANACOMP SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia);
		
	}
	
	public void UpdateFichaHan(Hashtable voFichaHan) throws Exception{
		ResultSet rs =driver.executeQuery(" SELECT STATUS FROM FICHAHAN WHERE NR_FAMILIA ="+voFichaHan.get("NR_FAMILIA")+
				" and CD_PACIENTE = "+voFichaHan.get("CD_PACIENTE")+" and CD_ANO = "+voFichaHan.get("CD_ANO")+" AND   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		if(rs.next()){
			String flStatus=null;		
			if(rs.getInt("STATUS")==BBPDBTemp.SSK_RECORD_NULL){
				flStatus = ""+BBPDBTemp.SSK_RECORD_UPDATED;
			}else{
				flStatus = rs.getString("STATUS");
			}	
			driver.executeUpdate(" UPDATE FICHAHAN SET NUM_COMUNICANTES="+voFichaHan.get("NUM_COMUNICANTES")+", DS_OBSERVACAO='"+voFichaHan.get("DS_OBSERVACAO")+"'," +
								 " STATUS="+flStatus+
								 " WHERE NR_FAMILIA ="+voFichaHan.get("NR_FAMILIA")+" and CD_PACIENTE = "+voFichaHan.get("CD_PACIENTE")+" AND" +
								 " STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		}
		ResultSet rsAcomp =driver.executeQuery(" SELECT STATUS FROM FICHAHANACOMP WHERE NR_FAMILIA ="+voFichaHan.get("NR_FAMILIA")+" and CD_PACIENTE = "+voFichaHan.get("CD_PACIENTE")+" and " +
				  " CD_MES="+voFichaHan.get("CD_MES")+" AND CD_ANO = "+voFichaHan.get("CD_ANO")+" AND   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		if(rsAcomp.next()){
			String flStatus=null;		
			if(rsAcomp.getInt("STATUS")==BBPDBTemp.SSK_RECORD_NULL){
				flStatus = ""+BBPDBTemp.SSK_RECORD_UPDATED;
			}else{
				flStatus = rsAcomp.getString("STATUS");
			}
			driver.executeUpdate(" UPDATE FICHAHANACOMP SET  DT_VISITA='"+voFichaHan.get("DT_VISITA")+"', FL_MEDICACAO='"+voFichaHan.get("FL_MEDICACAO")+"'," +
					" DT_ULTDOSE='"+voFichaHan.get("DT_ULTDOSE")+"', FL_AUTOCUIDADOS='"+voFichaHan.get("FL_AUTOCUIDADOS")+"', DT_CONSULTA='"+voFichaHan.get("DT_CONSULTA")+"',"+
					" NUM_EXAMINADOS="+voFichaHan.get("NUM_EXAMINADOS")+", NUM_COMBCG="+voFichaHan.get("NUM_COMBCG")+
					" STATUS="+flStatus+
					" WHERE NR_FAMILIA ="+voFichaHan.get("NR_FAMILIA")+" and CD_PACIENTE = "+voFichaHan.get("CD_PACIENTE")+" and CD_MES="+voFichaHan.get("CD_MES")+" AND "+
					" CD_ANO = "+voFichaHan.get("CD_ANO")+" AND  STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		}else{
			driver.executeUpdate(" INSERT INTO FICHAHANACOMP ( CD_SEGMENTO, CD_AREA, CD_MICROAREA, NR_FAMILIA, CD_PACIENTE, CD_MES,CD_ANO, DT_VISITA, FL_MEDICACAO, DT_ULTDOSE,FL_AUTOCUIDADOS, DT_CONSULTA,NUM_EXAMINADOS, NUM_COMBCG,STATUS)" +
				     " VALUES("+voFichaHan.get("CD_SEGMENTO")+","+voFichaHan.get("CD_AREA")+","+voFichaHan.get("CD_MICROAREA")+","+voFichaHan.get("NR_FAMILIA")+","+
				     voFichaHan.get("CD_PACIENTE")+","+voFichaHan.get("CD_MES")+","+voFichaHan.get("CD_ANO")+",'"+voFichaHan.get("DT_VISITA")+"','"+
				     voFichaHan.get("FL_MEDICACAO")+"','"+voFichaHan.get("DT_ULTDOSE")+"',"+" '"+voFichaHan.get("FL_AUTOCUIDADOS")+
				     "','"+voFichaHan.get("DT_CONSULTA")+"',"+voFichaHan.get("NUM_EXAMINADOS")+","+voFichaHan.get("NUM_COMBCG")+","+BBPDBTemp.SSK_RECORD_INSERTED+" )");
		}
	}
	
	public ResultSet getFichaHan(long cdFamilia, long cdpaciente) throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHAHAN WHERE  NR_FAMILIA ="+cdFamilia+" and CD_PACIENTE = "+cdpaciente+"  and   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		return rs;		
	}
	public ResultSet GetFichaHanAcomp(long cdFamilia, long cdpaciente) throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHAHANACOMP WHERE NR_FAMILIA ="+cdFamilia+" and CD_PACIENTE ="+cdpaciente+"  and   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		return rs;		
	}
	
	public ResultSet ListaFichaAll() throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHAHAN WHERE  STATUS !="+BBPDBTemp.SSK_RECORD_DELETED );
		return rs;		
	}
	
	public boolean ExistsPacienteHan(long cdFamilia, long cdpaciente) throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHAHAN WHERE  NR_FAMILIA ="+cdFamilia+" and CD_PACIENTE = "+cdpaciente+" AND  STATUS !="+BBPDBTemp.SSK_RECORD_DELETED );
		if(rs.next()){
			return true;	
		}else{
			return false;
		}
			
	}
	
	

}
