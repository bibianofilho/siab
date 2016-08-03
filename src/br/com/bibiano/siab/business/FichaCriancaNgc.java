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

public class FichaCriancaNgc extends SIABBusiness{
	static LitebaseConnection driver ;
	
	public static FichaCriancaNgc criarInstancia()
	{    
		driver = LitebaseConnection.getInstance(CREATORID);
		return new FichaCriancaNgc();
	}
	
	public void InserteFichaCrianca(Hashtable vo) throws Exception{	
		driver.executeUpdate(" INSERT INTO FICHACRIANCA (CD_SEGMENTO, CD_AREA, CD_MICROAREA, NR_FAMILIA, CD_PACIENTE,CD_ANO, CD_AVALIACAOALIMEN, DS_TIPOALIMENTACAO,FL_IMUNIZADO,FL_COMPLETARESQ," +
							 " FL_DIARREIA, FL_USARAMTRO,FL_INTERNAMENTOS,FL_IRA,FL_CAXUMBA,FL_CATAPORA,FL_BAIXOPESONASC,FL_DEFICIENCIAS,STATUS)" +
							 " VALUES("+vo.get("CD_SEGMENTO")+","+vo.get("CD_AREA")+","+vo.get("CD_MICROAREA")+","+vo.get("NR_FAMILIA")+","+vo.get("CD_PACIENTE")+","+(new Date()).getYear()+","+vo.get("CD_AVALIACAOALIMEN")+",'"+vo.get("DS_TIPOALIMENTACAO")+"','"+
		                     vo.get("FL_IMUNIZADO")+"','"+vo.get("FL_COMPLETARESQ")+"','"+vo.get("FL_DIARREIA")+"','"+vo.get("FL_USARAMTRO")+"','"+		                     
		                     vo.get("FL_INTERNAMENTOS") +"','"+vo.get("FL_IRA")+"','"+vo.get("FL_CAXUMBA")+"','"+
		                     vo.get("FL_CATAPORA") +"','"+vo.get("FL_BAIXOPESONASC")+"','"+vo.get("FL_DEFICIENCIAS")+"',"+BBPDBTemp.SSK_RECORD_INSERTED+")");

		driver.executeUpdate(" INSERT INTO FICHACRIANCAACOMP (CD_SEGMENTO, CD_AREA, CD_MICROAREA, NR_FAMILIA, CD_PACIENTE, CD_MES,CD_ANO, DT_QUADRONUTRI, NUM_PESO, NUM_ALTURA,STATUS)" +
						     " VALUES("+vo.get("CD_SEGMENTO")+","+vo.get("CD_AREA")+","+vo.get("CD_MICROAREA")+","+vo.get("NR_FAMILIA")+","+vo.get("CD_PACIENTE")+","+vo.get("CD_MES")+","+vo.get("CD_ANO")+",'"+vo.get("DT_QUADRONUTRI")+"',"+
						     vo.get("NUM_PESO")+","+vo.get("NUM_ALTURA")+","+BBPDBTemp.SSK_RECORD_INSERTED+")");			
	}
	
	public void DeleteFichaCrianca(int cdFamilia,int cdpaciente) throws Exception{
		/*driver.executeUpdate(" DELETE FROM FICHACRIANCA WHERE  CD_FAMILIA ="+cdFamilia+"L and  CD_PACIENTE = "+cdpaciente+"L");
		driver.executeUpdate(" DELETE FROM FICHACRIANCAACOMP WHERE  CD_FAMILIA ="+cdFamilia+"L and  CD_PACIENTE = "+cdpaciente+"L");*/
		driver.executeUpdate(" UPDATE FICHACRIANCA SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia+" and  CD_PACIENTE = "+cdpaciente);
		driver.executeUpdate(" UPDATE FICHACRIANCAACOMP SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia+" and  CD_PACIENTE = "+cdpaciente);
	}
	
	public void DeleteFichaCriancaFamilia(int cdFamilia) throws Exception{
		/*driver.executeUpdate(" DELETE FROM FICHACRIANCA WHERE  CD_FAMILIA ="+cdFamilia+"L");
		driver.executeUpdate(" DELETE FROM FICHACRIANCAACOMP WHERE  CD_FAMILIA ="+cdFamilia+"L");*/
		driver.executeUpdate(" UPDATE FICHACRIANCA SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia);
		driver.executeUpdate(" UPDATE FICHACRIANCAACOMP SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia);
	}
	
	public void UpdateFichaCrianca(Hashtable vo) throws Exception{
		
		ResultSet rs =driver.executeQuery(" SELECT STATUS FROM FICHACRIANCA WHERE NR_FAMILIA ="+vo.get("NR_FAMILIA")+
				" and CD_PACIENTE = "+vo.get("CD_PACIENTE")+" and CD_ANO = "+vo.get("CD_ANO")+" AND   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		if(rs.next()){
			String flStatus=null;		
			if(rs.getInt("STATUS")==BBPDBTemp.SSK_RECORD_NULL){
				flStatus = ""+BBPDBTemp.SSK_RECORD_UPDATED;
			}else{
				flStatus = rs.getString("STATUS");
			}	
			driver.executeUpdate(" UPDATE FICHACRIANCA SET CD_AVALIACAOALIMEN="+vo.get("CD_AVALIACAOALIMEN")+", DS_TIPOALIMENTACAO='"+vo.get("DS_TIPOALIMENTACAO")+"'," +
								 " FL_IMUNIZADO='"+vo.get("FL_IMUNIZADO")+"',FL_COMPLETARESQ='"+vo.get("FL_COMPLETARESQ")+"',"+
								 " FL_DIARREIA='"+vo.get("FL_DIARREIA")+"',FL_USARAMTRO='"+vo.get("FL_USARAMTRO")+"',"+
								 " FL_INTERNAMENTOS='"+vo.get("FL_INTERNAMENTOS")+"',FL_IRA='"+vo.get("FL_IRA")+"',"+
								 " FL_CAXUMBA='"+vo.get("FL_CAXUMBA")+"',FL_CATAPORA='"+vo.get("FL_CATAPORA")+"',"+
								 " FL_BAIXOPESONASC='"+vo.get("FL_BAIXOPESONASC")+"',FL_DEFICIENCIAS='"+vo.get("FL_DEFICIENCIAS")+"',"+
								 " STATUS="+flStatus+
								 " WHERE NR_FAMILIA ="+vo.get("NR_FAMILIA")+" and CD_PACIENTE = "+vo.get("CD_PACIENTE")+" AND "+
								 " STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		}
		ResultSet rsAcomp =driver.executeQuery(" SELECT STATUS FROM FICHACRIANCAACOMP WHERE NR_FAMILIA ="+vo.get("NR_FAMILIA")+" and CD_PACIENTE = "+vo.get("CD_PACIENTE")+" and " +
				  " CD_MES="+vo.get("CD_MES")+" AND CD_ANO = "+vo.get("CD_ANO")+" AND   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		if(rsAcomp.next()){
			String flStatus=null;		
			if(rsAcomp.getInt("STATUS")==BBPDBTemp.SSK_RECORD_NULL){
				flStatus = ""+BBPDBTemp.SSK_RECORD_UPDATED;
			}else{
				flStatus = rsAcomp.getString("STATUS");
			}
			driver.executeUpdate(" UPDATE FICHACRIANCAACOMP SET DT_QUADRONUTRI='"+vo.get("DT_QUADRONUTRI")+"', NUM_PESO="+vo.get("NUM_PESO")+"," +
								 " NUM_ALTURA="+vo.get("NUM_ALTURA")+ " STATUS="+flStatus+
								 " WHERE NR_FAMILIA ="+vo.get("NR_FAMILIA")+" and CD_PACIENTE = "+vo.get("CD_PACIENTE")+" and CD_MES="+vo.get("CD_MES")+" AND "+
								 " CD_ANO = "+vo.get("CD_ANO")+" AND STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);	
		}else{
			driver.executeUpdate(" INSERT INTO FICHACRIANCAACOMP (CD_SEGMENTO, CD_AREA, CD_MICROAREA, NR_FAMILIA, CD_PACIENTE, CD_MES,CD_ANO, DT_QUADRONUTRI, NUM_PESO, NUM_ALTURA,STATUS)" +
				     " VALUES("+vo.get("CD_SEGMENTO")+","+vo.get("CD_AREA")+","+vo.get("CD_MICROAREA")+","+vo.get("NR_FAMILIA")+","+vo.get("CD_PACIENTE")+","+vo.get("CD_MES")+","+vo.get("CD_ANO")+",'"+vo.get("DT_QUADRONUTRI")+"',"+
				     vo.get("NUM_PESO")+","+vo.get("NUM_ALTURA")+","+BBPDBTemp.SSK_RECORD_INSERTED+")");
		}
	}
	
	public ResultSet getFichaCrianca(int cdFamilia, int cdpaciente) throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHACRIANCA WHERE  NR_FAMILIA ="+cdFamilia+" and CD_PACIENTE = "+cdpaciente+"  and   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED );
		return rs;		
	}
	public ResultSet GetFichaCriancaAcomp(int cdFamilia, int cdpaciente) throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHACRIANCAACOMP WHERE NR_FAMILIA ="+cdFamilia+" and CD_PACIENTE ="+cdpaciente+"  and   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		return rs;		
	}
	
	public ResultSet ListaFichaAll() throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHACRIANCA WHERE  STATUS !="+BBPDBTemp.SSK_RECORD_DELETED );;
		return rs;		
	}
	
	public boolean ExistsPacienteCrianca(int cdFamilia, int cdpaciente) throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHACRIANCA WHERE  NR_FAMILIA ="+cdFamilia+" and CD_PACIENTE = "+cdpaciente+" and STATUS !="+BBPDBTemp.SSK_RECORD_DELETED );
		if(rs.next()){
			return true;	
		}else{
			return false;
		}			
	}
	
	

}
