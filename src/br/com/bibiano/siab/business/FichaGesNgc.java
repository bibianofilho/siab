package br.com.bibiano.siab.business;

import litebase.LitebaseConnection;
import litebase.ResultSet;
import waba.sys.Convert;
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

public class FichaGesNgc extends SIABBusiness{
	static LitebaseConnection driver ;
	
	public static FichaGesNgc criarInstancia()
	{   
		driver = LitebaseConnection.getInstance(CREATORID);
		return new FichaGesNgc();
	}
	
	public void InserteFichaGes(Hashtable voFichaGes) throws Exception{	
		driver.executeUpdate(" INSERT INTO FICHAGES ( CD_SEGMENTO, CD_AREA, CD_MICROAREA, NR_FAMILIA, CD_PACIENTE,CD_ANO, DT_ULTREGRA, DT_PARTO,DT_VACINA1,DT_VACINA2,DT_VACINA3,DT_VACINAR," +
							 " FL_SEISGESTA,FL_NATIMORTO,FL_36ANOSMAIS,FL_MENOS20,FL_SANGRAMENTO,FL_EDEMA,FL_DIABETES,FL_PRESSAOALTA,DT_NASCVIVO,DT_NASCMORTO," +
							 " DT_ABORTO,DT_PUERPERIO1,DT_PUERPERIO2, DS_OBSERVACAO,STATUS)" +
		                     " VALUES("+voFichaGes.get("CD_SEGMENTO")+","+voFichaGes.get("CD_AREA")+","+voFichaGes.get("CD_MICROAREA")+","+voFichaGes.get("NR_FAMILIA")+","+voFichaGes.get("CD_PACIENTE")+","+(new Date()).getYear()+",'"+voFichaGes.get("DT_ULTREGRA")+"','"+
		                     voFichaGes.get("DT_PARTO")+"','"+voFichaGes.get("DT_VACINA1")+"','"+voFichaGes.get("DT_VACINA2")+"','"+voFichaGes.get("DT_VACINA3")+"','"+
		                     voFichaGes.get("DT_VACINAR")+"','"+voFichaGes.get("FL_SEISGESTA")+"','"+voFichaGes.get("FL_NATIMORTO")+"','"+
		                     voFichaGes.get("FL_36ANOSMAIS")+"','"+voFichaGes.get("FL_MENOS20")+"','"+voFichaGes.get("FL_SANGRAMENTO")+"','"+voFichaGes.get("FL_EDEMA")+"','"+
		                     voFichaGes.get("FL_DIABETES")+"','"+voFichaGes.get("FL_PRESSAOALTA")+"','"+voFichaGes.get("DT_NASCVIVO")+"','"+
		                     voFichaGes.get("DT_NASCMORTO")+"','"+voFichaGes.get("DT_ABORTO")+"','"+voFichaGes.get("DT_PUERPERIO1")+"','"+
		                     voFichaGes.get("DT_PUERPERIO2")+"','"+voFichaGes.get("DS_OBSERVACAO")+"',"+BBPDBTemp.SSK_RECORD_INSERTED+")");
		
		
		String[]ArrayEstadoNutri =(String[])voFichaGes.get("FL_ESTADONUTRI");
		String[]ArrayConsPreNatal =(String[])voFichaGes.get("DT_CONSPRENATAL");
		String[]ArrayDtVisita =(String[])voFichaGes.get("DT_VISITA");
		String[]ArrayCdAno =(String[])voFichaGes.get("CD_ANO");
		for(int i=0;i<9;i++){
			if(!"".equals(ArrayEstadoNutri[i]==null?"":ArrayEstadoNutri[i]) || !"".equals(ArrayConsPreNatal[i]==null?"":ArrayConsPreNatal[i]) || !"".equals(ArrayDtVisita[i]==null?"":ArrayDtVisita[i])){				
				driver.executeUpdate("INSERT INTO FICHAGESACOMP (CD_SEGMENTO, CD_AREA, CD_MICROAREA, NR_FAMILIA, CD_PACIENTE, CD_MES,CD_ANO, FL_ESTADONUTRI, DT_CONSPRENATAL, DT_VISITA,STATUS)" +
					     " VALUES("+voFichaGes.get("CD_SEGMENTO")+","+voFichaGes.get("CD_AREA")+","+voFichaGes.get("CD_MICROAREA")+","+voFichaGes.get("NR_FAMILIA")+","+voFichaGes.get("CD_PACIENTE")+","+(i+1)+","+ArrayCdAno[i]+",'"+
					     ArrayEstadoNutri[i]+"','"+ArrayConsPreNatal[i]+"','"+ArrayDtVisita[i]+"',"+BBPDBTemp.SSK_RECORD_INSERTED+")");
			}
		}		
	}
	
	public void DeleteFichaGes(long cdFamilia,long cdpaciente) throws Exception{
		/*driver.executeUpdate(" DELETE FROM FICHAGES WHERE  CD_FAMILIA ="+cdFamilia+"L and  CD_PACIENTE = "+cdpaciente+"L");
		driver.executeUpdate(" DELETE FROM FICHAGESACOMP WHERE  CD_FAMILIA ="+cdFamilia+"L and  CD_PACIENTE = "+cdpaciente+"L");*/
		driver.executeUpdate(" UPDATE FICHAGES SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia+" and  CD_PACIENTE = "+cdpaciente);
		driver.executeUpdate(" UPDATE FICHAGESACOMP SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia+" and  CD_PACIENTE = "+cdpaciente);
	}
	
	public void DeleteFichaGesFamilia(long cdFamilia) throws Exception{
		/*driver.executeUpdate(" DELETE FROM FICHAGES WHERE  CD_FAMILIA ="+cdFamilia+"L");
		driver.executeUpdate(" DELETE FROM FICHAGESACOMP WHERE  CD_FAMILIA ="+cdFamilia+"L");*/
		driver.executeUpdate(" UPDATE FICHAGES SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia);
		driver.executeUpdate(" UPDATE FICHAGESACOMP SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia);
	}
	
	public void UpdateFichaGes(Hashtable voFichaGes) throws Exception{
		ResultSet rs =driver.executeQuery(" SELECT STATUS FROM FICHAGES WHERE NR_FAMILIA ="+voFichaGes.get("NR_FAMILIA")+
				" and CD_PACIENTE = "+voFichaGes.get("CD_PACIENTE")+" and CD_ANO = "+voFichaGes.get("CD_ANO")+" AND   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		if(rs.next()){
			String flStatus=null;		
			if(rs.getInt("STATUS")==BBPDBTemp.SSK_RECORD_NULL){
				flStatus = ""+BBPDBTemp.SSK_RECORD_UPDATED;
			}else{
				flStatus = rs.getString("STATUS");
			}
			driver.executeUpdate(" UPDATE FICHAGES SET DT_ULTREGRA='"+voFichaGes.get("DT_ULTREGRA")+"', DT_PARTO='"+voFichaGes.get("DT_PARTO")+"', " +
								 " DT_VACINA1='"+voFichaGes.get("DT_VACINA1")+"', DT_VACINA2='"+voFichaGes.get("DT_VACINA2")+"', " +
								 " DT_VACINA3='"+voFichaGes.get("DT_VACINA3")+"', DT_VACINAR='"+voFichaGes.get("DT_VACINAR")+"', " +
								 " FL_SEISGESTA='"+voFichaGes.get("FL_SEISGESTA")+"', FL_NATIMORTO='"+voFichaGes.get("FL_NATIMORTO")+"', " +
								 " FL_36ANOSMAIS='"+voFichaGes.get("FL_36ANOSMAIS")+"', FL_SANGRAMENTO='"+voFichaGes.get("FL_SANGRAMENTO")+"', " +
								 " FL_EDEMA='"+voFichaGes.get("FL_EDEMA")+"', FL_DIABETES='"+voFichaGes.get("FL_DIABETES")+"', " +
								 " FL_PRESSAOALTA='"+voFichaGes.get("FL_PRESSAOALTA")+"', DT_NASCVIVO='"+voFichaGes.get("DT_NASCVIVO")+"', " +
								 " DT_NASCMORTO='"+voFichaGes.get("DT_NASCMORTO")+"', DT_ABORTO='"+voFichaGes.get("DT_ABORTO")+"', " +
								 " FL_MENOS20='"+voFichaGes.get("FL_MENOS20")+"', DT_PUERPERIO1='"+voFichaGes.get("DT_PUERPERIO1")+"',"+
								 " DT_PUERPERIO2='"+voFichaGes.get("DT_PUERPERIO2")+"', STATUS="+flStatus+
								 " WHERE NR_FAMILIA ="+voFichaGes.get("NR_FAMILIA")+" and CD_PACIENTE = "+voFichaGes.get("CD_PACIENTE")+" and "+
								 " STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		}	
		
		
		String[]ArrayEstadoNutri =(String[])voFichaGes.get("FL_ESTADONUTRI");
		String[]ArrayConsPreNatal =(String[])voFichaGes.get("DT_CONSPRENATAL");
		String[]ArrayDtVisita =(String[])voFichaGes.get("DT_VISITA");
		String[]ArrayCdAno =(String[])voFichaGes.get("CD_ANO");
		for(int i=0;i<9;i++){
			if(!"".equals(ArrayEstadoNutri[i]==null?"":ArrayEstadoNutri[i]) || !"".equals(ArrayConsPreNatal[i]==null?"":ArrayConsPreNatal[i]) || !"".equals(ArrayDtVisita[i]==null?"":ArrayDtVisita[i]==null?"":ArrayEstadoNutri[i])){
				ResultSet rsAcomp= GetFichaGesAcompMes(Convert.toLong((String)voFichaGes.get("NR_FAMILIA")),Convert.toLong((String)voFichaGes.get("CD_PACIENTE")),i+1);
				if(rsAcomp.next()){
					String flStatus=null;		
					if(rsAcomp.getInt("STATUS")==BBPDBTemp.SSK_RECORD_NULL){
						flStatus = ""+BBPDBTemp.SSK_RECORD_UPDATED;
					}else{
						flStatus = rsAcomp.getString("STATUS");
					}
					driver.executeUpdate(" UPDATE FICHAGESACOMP SET  FL_ESTADONUTRI='"+ArrayEstadoNutri[i]+"', DT_CONSPRENATAL='"+ArrayConsPreNatal[i]+"'," +
							 " DT_VISITA='"+ArrayDtVisita[i]+"', STATUS="+flStatus+
							 " WHERE NR_FAMILIA ="+voFichaGes.get("NR_FAMILIA")+" and CD_PACIENTE = "+voFichaGes.get("CD_PACIENTE")+
							 " and CD_MES="+(i+1)+" AND STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
				}else{
					driver.executeUpdate("INSERT INTO FICHAGESACOMP ( CD_SEGMENTO,CD_AREA,CD_MICROAREA,NR_FAMILIA, CD_PACIENTE, CD_MES,CD_ANO, FL_ESTADONUTRI, DT_CONSPRENATAL, DT_VISITA,STATUS)" +
						     " VALUES("+voFichaGes.get("CD_SEGMENTO")+","+voFichaGes.get("CD_AREA")+","+voFichaGes.get("CD_MICROAREA")+","+voFichaGes.get("NR_FAMILIA")+","+voFichaGes.get("CD_PACIENTE")+","+(i+1)+","+ArrayCdAno[i]+",'"+
						     ArrayEstadoNutri[i]+"','"+ArrayConsPreNatal[i]+"','"+ArrayDtVisita[i]+"',"+BBPDBTemp.SSK_RECORD_INSERTED+")");
				}		
			}
		}	
	}
	
	public ResultSet getFichaGes(long cdFamilia, long cdpaciente) throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHAGES WHERE  NR_FAMILIA ="+cdFamilia+" and CD_PACIENTE = "+cdpaciente+" and   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		return rs;		
	}
	public ResultSet GetFichaGesAcomp(long cdFamilia, long cdpaciente) throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHAGESACOMP WHERE NR_FAMILIA ="+cdFamilia+" and CD_PACIENTE ="+cdpaciente+" and STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		return rs;		
	}
	public ResultSet GetFichaGesAcompMes(long cdFamilia, long cdpaciente,int cdMes) throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHAGESACOMP WHERE NR_FAMILIA ="+cdFamilia+" and CD_PACIENTE ="+cdpaciente+" and " +
				" CD_MES="+cdMes+ " AND STATUS !="+BBPDBTemp.SSK_RECORD_DELETED +" ORDER BY CD_MES");
		return rs;		
	}
	
	public ResultSet ListaFichaAll() throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHAGES WHERE  STATUS !="+BBPDBTemp.SSK_RECORD_DELETED );
		return rs;		
	}
	
	public boolean ExistsPacienteGes(long cdFamilia, long cdpaciente) throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHAGES WHERE  NR_FAMILIA ="+cdFamilia+" and CD_PACIENTE = "+cdpaciente+" AND  STATUS !="+BBPDBTemp.SSK_RECORD_DELETED );
		if(rs.next()){
			return true;	
		}else{
			return false;
		}
			
	}
	
	

}
