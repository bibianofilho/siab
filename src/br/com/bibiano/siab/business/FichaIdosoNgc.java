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

public class FichaIdosoNgc extends SIABBusiness{
	static LitebaseConnection driver ;
	
	public static FichaIdosoNgc criarInstancia()
	{
		driver = LitebaseConnection.getInstance(CREATORID);
		return new FichaIdosoNgc();
	}
	
	public void InserteFichaIdoso(Hashtable vo) throws Exception{		
		driver.executeUpdate(" INSERT INTO FICHAIDOSO (CD_SEGMENTO, CD_AREA, CD_MICROAREA, NR_FAMILIA, CD_PACIENTE, CD_ANO, FL_FUMANTE, DT_DTP,DT_INFLUENZA,DT_PNEUMONOCOCOS,FL_HIPERTENSAO," +
							 " FL_TUBERCULOSE,FL_ALZHEIMER,FL_DEFFISICA,FL_HANSENIASE,FL_MALPARKSON,FL_CANCER,FL_ACAMADO,FL_INTERNAMENTOS," +
							 " FL_SEQUELASAVC,FL_ALCOLATRA,STATUS)" +
		                     " VALUES("+vo.get("CD_SEGMENTO")+","+vo.get("CD_AREA")+","+vo.get("CD_MICROAREA")+","+vo.get("NR_FAMILIA")+","+vo.get("CD_PACIENTE")+","+(new Date()).getYear()+",'"+vo.get("FL_FUMANTE")+"','"+vo.get("DT_DTP")+"','"+
		                     vo.get("DT_INFLUENZA")+"','"+vo.get("DT_PNEUMONOCOCOS")+"','"+vo.get("FL_HIPERTENSAO")+"','"+vo.get("FL_TUBERCULOSE")+"','"+
		                     vo.get("FL_ALZHEIMER") +"','"+vo.get("FL_DEFFISICA")+"','"+vo.get("FL_HANSENIASE")+"','"+vo.get("FL_MALPARKSON")+"','"+
		                     vo.get("FL_CANCER") +"','"+vo.get("FL_ACAMADO")+"','"+vo.get("FL_INTERNAMENTOS")+"','"+
		                     vo.get("FL_SEQUELASAVC") +"','"+vo.get("FL_ALCOLATRA")+"',"+BBPDBTemp.SSK_RECORD_INSERTED+")");

		driver.executeUpdate(" INSERT INTO FICHAIDOSOACOMP (CD_SEGMENTO, CD_AREA, CD_MICROAREA, NR_FAMILIA, CD_PACIENTE, CD_MES,CD_ANO, FL_ESTADONUTRI, DT_VISITA, DT_ACOMPPSF,STATUS)" +
						     " VALUES("+vo.get("CD_SEGMENTO")+","+vo.get("CD_AREA")+","+vo.get("CD_MICROAREA")+","+vo.get("NR_FAMILIA")+","+vo.get("CD_PACIENTE")+","+vo.get("CD_MES")+","+vo.get("CD_ANO")+",'"+vo.get("FL_ESTADONUTRI")+"','"+
						     vo.get("DT_VISITA")+"','"+vo.get("DT_ACOMPPSF")+"',"+BBPDBTemp.SSK_RECORD_INSERTED+")");			
	}
	
	public void DeleteFichaIdoso(int cdFamilia,int cdpaciente) throws Exception{
		driver.executeUpdate(" UPDATE FICHAIDOSO SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia+" and  CD_PACIENTE = "+cdpaciente);
		driver.executeUpdate(" UPDATE FICHAIDOSOACOMP SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia+" and  CD_PACIENTE = "+cdpaciente);
		
	}
	
	public void DeleteFichaIdosoFamilia(int cdFamilia) throws Exception{	
		driver.executeUpdate(" UPDATE FICHAIDOSO SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia);
		driver.executeUpdate(" UPDATE FICHAIDOSOACOMP SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia);
	}
	
	public void UpdateFichaIdoso(Hashtable vo) throws Exception{
		ResultSet rs =driver.executeQuery(" SELECT STATUS FROM FICHAIDOSO WHERE NR_FAMILIA ="+vo.get("NR_FAMILIA")+
				" and CD_PACIENTE = "+vo.get("CD_PACIENTE")+" and CD_ANO = "+vo.get("CD_ANO")+" AND   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		if(rs.next()){
			String flStatus=null;		
			if(rs.getInt("STATUS")==BBPDBTemp.SSK_RECORD_NULL){
				flStatus = ""+BBPDBTemp.SSK_RECORD_UPDATED;
			}else{
				flStatus = rs.getString("STATUS");
			}	
			driver.executeUpdate(" UPDATE FICHAIDOSO SET FL_FUMANTE='"+vo.get("FL_FUMANTE")+"', DT_DTP='"+vo.get("DT_DTP")+"'," +
								 " DT_INFLUENZA='"+vo.get("DT_INFLUENZA")+"',DT_PNEUMONOCOCOS='"+vo.get("DT_PNEUMONOCOCOS")+"',"+
								 " FL_HIPERTENSAO='"+vo.get("FL_HIPERTENSAO")+"',FL_TUBERCULOSE='"+vo.get("FL_TUBERCULOSE")+"',"+
								 " FL_ALZHEIMER='"+vo.get("FL_ALZHEIMER")+"',FL_DEFFISICA='"+vo.get("FL_DEFFISICA")+"',"+
								 " FL_HANSENIASE='"+vo.get("FL_HANSENIASE")+"',FL_MALPARKSON='"+vo.get("FL_MALPARKSON")+"',"+
								 " FL_CANCER='"+vo.get("FL_CANCER")+"',FL_ACAMADO='"+vo.get("FL_ACAMADO")+"',"+
								 " FL_INTERNAMENTOS='"+vo.get("FL_INTERNAMENTOS")+"',FL_SEQUELASAVC='"+vo.get("FL_SEQUELASAVC")+"',"+
								 " FL_ALCOLATRA='"+vo.get("FL_ALCOLATRA")+"',STATUS="+flStatus+
								 " WHERE NR_FAMILIA ="+vo.get("NR_FAMILIA")+" and CD_PACIENTE = "+vo.get("CD_PACIENTE")+" AND " +
	 					 		 " STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		}	
		ResultSet rsAcomp =driver.executeQuery(" SELECT STATUS FROM FICHAIDOSOACOMP WHERE NR_FAMILIA ="+vo.get("NR_FAMILIA")+" and CD_PACIENTE = "+vo.get("CD_PACIENTE")+" and " +
				  " CD_MES="+vo.get("CD_MES")+" AND CD_ANO = "+vo.get("CD_ANO")+" AND   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		if(rsAcomp.next()){
			String flStatus=null;		
			if(rsAcomp.getInt("STATUS")==BBPDBTemp.SSK_RECORD_NULL){
				flStatus = ""+BBPDBTemp.SSK_RECORD_UPDATED;
			}else{
				flStatus = rsAcomp.getString("STATUS");
			}
			driver.executeUpdate(" UPDATE FICHAIDOSOACOMP SET FL_ESTADONUTRI='"+vo.get("FL_ESTADONUTRI")+"', DT_VISITA='"+vo.get("DT_VISITA")+"'," +
								 " DT_ACOMPPSF='"+vo.get("DT_ACOMPPSF")+"', STATUS="+flStatus+
								 " WHERE NR_FAMILIA ="+vo.get("NR_FAMILIA")+" and CD_PACIENTE = "+vo.get("CD_PACIENTE")+" and CD_MES="+vo.get("CD_MES")+" AND "+
								 " CD_ANO = "+vo.get("CD_ANO")+" AND STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);	
		}else{
			driver.executeUpdate(" INSERT INTO FICHAIDOSOACOMP (CD_SEGMENTO, CD_AREA, CD_MICROAREA, NR_FAMILIA, CD_PACIENTE, CD_MES,CD_ANO, FL_ESTADONUTRI, DT_VISITA, DT_ACOMPPSF,STATUS)" +
				     " VALUES("+vo.get("CD_SEGMENTO")+","+vo.get("CD_AREA")+","+vo.get("CD_MICROAREA")+","+vo.get("NR_FAMILIA")+","+vo.get("CD_PACIENTE")+","+vo.get("CD_MES")+","+vo.get("CD_ANO")+",'"+vo.get("FL_ESTADONUTRI")+"','"+
				     vo.get("DT_VISITA")+"','"+vo.get("DT_ACOMPPSF")+"',"+BBPDBTemp.SSK_RECORD_INSERTED+")");
		}
	}
	
	public ResultSet getFichaIdoso(int cdFamilia, int cdpaciente) throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHAIDOSO WHERE  NR_FAMILIA ="+cdFamilia+" and CD_PACIENTE = "+cdpaciente+" and   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		return rs;		
	}
	public ResultSet GetFichaIdosoAcomp(int cdFamilia, int cdpaciente) throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHAIDOSOACOMP WHERE NR_FAMILIA ="+cdFamilia+" and CD_PACIENTE ="+cdpaciente+" and   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		return rs;		
	}
	
	public ResultSet ListaFichaAll() throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHAIDOSO WHERE  STATUS !="+BBPDBTemp.SSK_RECORD_DELETED );
		return rs;		
	}
	
	public boolean ExistsPacienteIdoso(int cdFamilia, int cdpaciente) throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHAIDOSO WHERE  NR_FAMILIA ="+cdFamilia+" and CD_PACIENTE = "+cdpaciente+" AND  STATUS !="+BBPDBTemp.SSK_RECORD_DELETED );
		if(rs.next()){
			return true;	
		}else{
			return false;
		}			
	}
	
	

}
