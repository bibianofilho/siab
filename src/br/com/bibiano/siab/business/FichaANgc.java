package br.com.bibiano.siab.business;

import superwaba.ext.palm.io.builtin.ToDo;
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

public class FichaANgc extends SIABBusiness{
	
	
	public static FichaANgc criarInstancia()
	{
		return new FichaANgc();
	}
	
	public void InsertePaciente(Hashtable voPaciente) throws Exception{		
		LitebaseConnection driver = LitebaseConnection.getInstance(CREATORID);		
		driver.executeUpdate(" INSERT INTO PACFAMILIA( CD_SEGMENTO,CD_AREA,CD_MICROAREA,NR_FAMILIA, CD_PACIENTE,CD_ANO, NM_PACIENTE, DT_NASCIMENTO, NUM_IDADE, FL_SEXO, FL_MENORQUINZE, " +
							 " FL_ALFABETIZADO, FL_FREQESCOLA, CD_OCUPACAO, FL_ALC, FL_CHA, FL_DEF, FL_DIA, FL_DME, FL_EPI, FL_GES, FL_HAN," +
							 " FL_HA, FL_MAL, FL_TBC,FL_MAIOR, STATUS)" +
		                     " VALUES("+voPaciente.get("CD_SEGMENTO")+","+voPaciente.get("CD_AREA")+","+voPaciente.get("CD_MICROAREA")+","+voPaciente.get("NR_FAMILIA")+","+
		                     voPaciente.get("CD_PACIENTE")+","+(new Date()).getYear()+",'"+voPaciente.get("NM_PACIENTE")+"', '"+voPaciente.get("DT_NASCIMENTO")+"',"+
		                     voPaciente.get("NUM_IDADE")+",'"+voPaciente.get("FL_SEXO")+"','"+voPaciente.get("FL_MENORQUINZE")+"','"+voPaciente.get("FL_ALFABETIZADO")+"','"+
		                     voPaciente.get("FL_FREQESCOLA")+"',"+voPaciente.get("CD_OCUPACAO")+",'"+voPaciente.get("FL_ALC")+"','"+voPaciente.get("FL_CHA")+"','"+
		                     voPaciente.get("FL_DEF")+"','"+voPaciente.get("FL_DIA")+"','"+voPaciente.get("FL_DME")+"','"+voPaciente.get("FL_EPI")+"','"+
		                     voPaciente.get("FL_GES")+"','"+voPaciente.get("FL_HAN")+"','"+voPaciente.get("FL_HA")+"','"+voPaciente.get("FL_MAL")+"','"+
		                     voPaciente.get("FL_TBC")+"','"+voPaciente.get("FL_MAIOR")+"',"+BBPDBTemp.SSK_RECORD_INSERTED+")");
		driver.closeAll();
		// TODO: Verificar FL_MAIOR
		
	}
	
	public void UpdatePaciente(Hashtable voPaciente) throws Exception{		
		LitebaseConnection driver = LitebaseConnection.getInstance(CREATORID);
		ResultSet rs =driver.executeQuery(" SELECT STATUS FROM PACFAMILIA WHERE NR_FAMILIA ="+voPaciente.get("NR_FAMILIA")+
				" and CD_PACIENTE = "+voPaciente.get("CD_PACIENTE")+ " AND   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		if(rs.next()){
			String flStatus=null;		
			if(rs.getInt("STATUS")==BBPDBTemp.SSK_RECORD_NULL){
				flStatus = ""+BBPDBTemp.SSK_RECORD_UPDATED;
			}else{
				flStatus = rs.getString("STATUS");
			}
			driver.executeUpdate(" Update  PACFAMILIA SET NM_PACIENTE = '"+voPaciente.get("NM_PACIENTE")+"', DT_NASCIMENTO='"+voPaciente.get("DT_NASCIMENTO")+"', "+
								 " NUM_IDADE ="+voPaciente.get("NUM_IDADE")+", FL_SEXO='"+voPaciente.get("FL_SEXO")+"', "+
								 " FL_MENORQUINZE='"+voPaciente.get("FL_MENORQUINZE")+"', FL_ALFABETIZADO='"+voPaciente.get("FL_ALFABETIZADO")+"', "+
								 " FL_FREQESCOLA='"+voPaciente.get("FL_FREQESCOLA")+"', CD_OCUPACAO="+voPaciente.get("CD_OCUPACAO")+", "+
								 " FL_ALC='"+voPaciente.get("FL_ALC")+"', FL_CHA='"+voPaciente.get("FL_CHA")+"', "+
								 " FL_DEF='"+voPaciente.get("FL_DEF")+"', FL_DIA='"+voPaciente.get("FL_DIA")+"', "+
								 " FL_DME='"+voPaciente.get("FL_DME")+"', FL_EPI='"+voPaciente.get("FL_EPI")+"', "+
								 " FL_GES='"+voPaciente.get("FL_GES")+"', FL_HAN='"+voPaciente.get("FL_HAN")+"', "+
								 " FL_HA='"+ voPaciente.get("FL_HA") +"', FL_MAL='"+voPaciente.get("FL_MAL")+"', "+
								 " FL_TBC='"+voPaciente.get("FL_TBC")+"', AND STATUS="+flStatus+ 
								 " WHERE NR_FAMILIA="+voPaciente.get("NR_FAMILIA")+" AND CD_PACIENTE="+voPaciente.get("CD_PACIENTE") );
		}	
		driver.closeAll();
	}
	
	public void InserteFichaA(Hashtable voPaciente) throws Exception{		
		LitebaseConnection driver = LitebaseConnection.getInstance(CREATORID);
		
		driver.executeUpdate(" INSERT INTO FICHACADFAM(  CD_SEGMENTO, CD_AREA, CD_MICROAREA, NR_FAMILIA,CD_ANO, DS_ENDERECO, NUM_ENDERECO, DS_BAIRRO, DS_CEP, CD_MUNICIPIO," +
							 " DT_CADASTRO, CD_UF, CD_TPCASA,DS_TPCASAESP ,NUM_COMODOS,FL_ENERGIA ,CD_DESTLIXO,CD_TRATAAGUA,CD_ABASTEAGUA, CD_FEZESURINA,"+
							 "CD_DOENCAPROCU,DS_DOENCAPROCUESP, CD_GRUPOCOMU,DS_GRUPOCOMUESP, CD_MEIOSCOMUNI, DS_MEIOSCOMUNIESP,CD_MEIOSTRANS,DS_MEIOSTRANSESP,"+
							 "FL_POSSUIPLAN,NUM_PESPLAN,NM_PLANO ,DS_OBSERVACAO,STATUS)"+
		                     " VALUES("+voPaciente.get("CD_SEGMENTO")+","+voPaciente.get("CD_AREA")+","+voPaciente.get("CD_MICROAREA")+","+voPaciente.get("NR_FAMILIA")+","
		                     +(new Date()).getYear()+",'"+voPaciente.get("DS_ENDERECO")+"',"+voPaciente.get("NUM_ENDERECO")+",'"+voPaciente.get("DS_BAIRRO")+"','"+
		                     voPaciente.get("DS_CEP")+"',"+voPaciente.get("CD_MUNICIPIO")+",'"+voPaciente.get("DT_CADASTRO")+"','"+voPaciente.get("CD_UF")+"',"+voPaciente.get("CD_TPCASA")+",'"+
		                     voPaciente.get("DS_TPCASAESP")+"',"+voPaciente.get("NUM_COMODOS")+",'"+voPaciente.get("FL_ENERGIA")+"',"+voPaciente.get("CD_DESTLIXO")+"," +
		                     voPaciente.get("CD_TRATAAGUA")+","+voPaciente.get("CD_ABASTEAGUA")+","+voPaciente.get("CD_FEZESURINA")+","+voPaciente.get("CD_DOENCAPROCU")+",'"+
		                     voPaciente.get("DS_DOENCAPROCUESP")+"',"+voPaciente.get("CD_GRUPOCOMU")+",'"+voPaciente.get("DS_GRUPOCOMUESP")+"',"+
		                     voPaciente.get("CD_MEIOSCOMUNI")+",'"+voPaciente.get("DS_MEIOSCOMUNIESP")+"',"+voPaciente.get("CD_MEIOSTRANS")+",'"+
		                     voPaciente.get("DS_MEIOSTRANSESP")+"','"+voPaciente.get("FL_POSSUIPLAN")+"',"+voPaciente.get("NUM_PESPLAN")+",'"+
		                     voPaciente.get("NM_PLANO")+"','"+voPaciente.get("DS_OBSERVACAO")+"',"+BBPDBTemp.SSK_RECORD_INSERTED+")");
		driver.closeAll();
	}
	
	public void UpdateFichaA(Hashtable voPaciente) throws Exception{		
		LitebaseConnection driver = LitebaseConnection.getInstance(CREATORID);
		ResultSet rs =driver.executeQuery(" SELECT STATUS FROM FICHACADFAM WHERE NR_FAMILIA ="+voPaciente.get("NR_FAMILIA")+
				"  AND   STATUS !="+BBPDBTemp.SSK_RECORD_DELETED);
		if(rs.next()){
			String flStatus=null;		
			if(rs.getInt("STATUS")==BBPDBTemp.SSK_RECORD_NULL){
				flStatus = ""+BBPDBTemp.SSK_RECORD_UPDATED;
			}else{
				flStatus = rs.getString("STATUS");
			}
			driver.executeUpdate(" UPDATE FICHACADFAM SET "+
								 " DS_ENDERECO='"+voPaciente.get("DS_ENDERECO")+"' , NUM_ENDERECO="+voPaciente.get("NUM_ENDERECO")+" , DS_BAIRRO='"+voPaciente.get("DS_BAIRRO")+"'"+
								 " , DS_CEP='"+voPaciente.get("DS_CEP")+"' , CD_MUNICIPIO="+voPaciente.get("CD_MUNICIPIO")+" , DT_CADASTRO='"+voPaciente.get("DT_CADASTRO")+"'"+
								 " , CD_UF='"+voPaciente.get("CD_UF")+"' , CD_TPCASA="+voPaciente.get("CD_TPCASA")+" , DS_TPCASAESP='"+voPaciente.get("DS_TPCASAESP")+"'"+
								 " , NUM_COMODOS="+voPaciente.get("NUM_COMODOS")+" , FL_ENERGIA='"+voPaciente.get("FL_ENERGIA")+"' , CD_DESTLIXO="+voPaciente.get("CD_DESTLIXO")+
								 " , CD_TRATAAGUA="+voPaciente.get("CD_TRATAAGUA")+" , CD_ABASTEAGUA="+voPaciente.get("CD_ABASTEAGUA")+" , CD_FEZESURINA="+voPaciente.get("CD_FEZESURINA")+
								 " , CD_DOENCAPROCU="+voPaciente.get("CD_DOENCAPROCU")+" , DS_DOENCAPROCUESP='"+voPaciente.get("DS_DOENCAPROCUESP")+"' , CD_GRUPOCOMU="+voPaciente.get("CD_GRUPOCOMU")+
								 " , DS_GRUPOCOMUESP='"+voPaciente.get("DS_GRUPOCOMUESP")+"' , CD_MEIOSCOMUNI="+voPaciente.get("CD_MEIOSCOMUNI")+" , DS_MEIOSCOMUNIESP='"+voPaciente.get("DS_MEIOSCOMUNIESP")+"'"+
								 " , CD_MEIOSTRANS="+voPaciente.get("CD_MEIOSTRANS")+" , DS_MEIOSTRANSESP='"+voPaciente.get("DS_MEIOSTRANSESP")+"' , FL_POSSUIPLAN='"+voPaciente.get("FL_POSSUIPLAN")+"'"+
								 " , NUM_PESPLAN="+voPaciente.get("NUM_PESPLAN")+" , NM_PLANO='"+voPaciente.get("NM_PLANO")+"' , DS_OBSERVACAO='"+voPaciente.get("DS_OBSERVACAO")+"' , STATUS="+flStatus+
			                     " WHERE NR_FAMILIA="+voPaciente.get("NR_FAMILIA"));
		}	
		driver.closeAll();
	}
	
	public ResultSet ListaPacienteAll(int cdFamilia) throws Exception{
		LitebaseConnection driver = LitebaseConnection.getInstance(CREATORID);
		ResultSet rs = driver.executeQuery(" SELECT * FROM PACFAMILIA WHERE NR_FAMILIA = "+cdFamilia+" AND STATUS != "+BBPDBTemp.SSK_RECORD_DELETED+" ORDER BY CD_PACIENTE"  );
		driver.closeAll();
		return rs;		
	}
	public ResultSet ListaFamiliasAll() throws Exception{
		LitebaseConnection driver = LitebaseConnection.getInstance(CREATORID);
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHACADFAM where  STATUS !="+BBPDBTemp.SSK_RECORD_DELETED+" ORDER BY NR_FAMILIA"  );
		driver.closeAll();
		return rs;		
	}
	public ResultSet ListaFamilia(int cdFamilia) throws Exception{
		LitebaseConnection driver = LitebaseConnection.getInstance(CREATORID);
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHACADFAM  WHERE NR_FAMILIA = "+cdFamilia+" AND STATUS != "+BBPDBTemp.SSK_RECORD_DELETED+" ORDER BY NR_FAMILIA" );
		driver.closeAll();
		return rs;		
	}
	public int getCdFamiliaNew() throws Exception{
		LitebaseConnection driver = LitebaseConnection.getInstance(CREATORID);		
		ResultSet rs = driver.executeQuery(" SELECT MAX(NR_FAMILIA) as NR_FAMILIA FROM FICHACADFAM WHERE NR_FAMILIA>=500" );
		driver.closeAll();
		if(rs.next()){
			return	rs.getInt(1)+1;
		}else{	
			return 500;
		}	
	}
	public int getCdPacienteNew(int cdFamilia) throws Exception{
		LitebaseConnection driver = LitebaseConnection.getInstance(CREATORID);
		ResultSet rs = driver.executeQuery(" SELECT MAX(CD_PACIENTE) as NOVO FROM PACFAMILIA WHERE NR_FAMILIA = "+cdFamilia+" GROUP BY NR_FAMILIA");
		driver.closeAll();
		if(rs.next()){
			return	rs.getInt("NOVO")+1;
		}else return 1;		
	}
	public void DeletePaciente(int cdFamilia,int cdPaciente){
		LitebaseConnection driver = LitebaseConnection.getInstance(CREATORID);		
		driver.executeUpdate(" UPDATE PACFAMILIA SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia+" AND CD_PACIENTE = "+cdPaciente);
		driver.closeAll();
	}
	
	public void DeleteFamilia(int cdFamilia) throws Exception{
		LitebaseConnection driver = LitebaseConnection.getInstance(CREATORID);		
		
		driver.executeUpdate(" UPDATE FICHACADFAM SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia);
		driver.executeUpdate(" UPDATE PACFAMILIA SET STATUS = "+BBPDBTemp.SSK_RECORD_DELETED +" WHERE NR_FAMILIA = "+cdFamilia);
		FichaTbNgc.criarInstancia().DeleteFichaTbFamilia(cdFamilia);
		FichaHanNgc.criarInstancia().DeleteFichaHanFamilia(cdFamilia);
		FichaHaNgc.criarInstancia().DeleteFichaHaFamilia(cdFamilia);
		FichaDiaNgc.criarInstancia().DeleteFichaDiaFamilia(cdFamilia);
		FichaGesNgc.criarInstancia().DeleteFichaGesFamilia(cdFamilia);
		FichaIdosoNgc.criarInstancia().DeleteFichaIdosoFamilia(cdFamilia);
		FichaCriancaNgc.criarInstancia().DeleteFichaCriancaFamilia(cdFamilia);
		driver.closeAll();
		
	}
	public ResultSet GetPaciente(int cdFamilia,int cdPaciente){
		LitebaseConnection driver = LitebaseConnection.getInstance(CREATORID);
		ResultSet rs = driver.executeQuery(" SELECT * FROM PACFAMILIA WHERE NR_FAMILIA = "+cdFamilia+" AND CD_PACIENTE = "+cdPaciente);
		driver.closeAll();
		return rs;
	}
	
	public ResultSet GetFamiliaPaciente(int cdFamilia){
		LitebaseConnection driver = LitebaseConnection.getInstance(CREATORID);
		ResultSet rs = driver.executeQuery(" SELECT * FROM FICHACADFAM WHERE NR_FAMILIA = "+cdFamilia);
		driver.closeAll();
		return rs;
	}
	
	public ResultSet GetOcupacaoAll(){
		LitebaseConnection driver = LitebaseConnection.getInstance(CREATORID);
		ResultSet rs =driver.executeQuery(" SELECT * FROM OCUPACAO order by DS_OCUPACAO ");
		return rs;
	}
}
