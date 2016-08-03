package br.com.bibiano.siab.sync;

import br.com.bibiano.siab.business.SIABBusiness;
import br.com.bibiano.siab.window.ReplicationWin;
import litebase.DriverException;
import litebase.LitebaseConnection;
import litebase.ResultSet;
import litebase.ResultSetMetaData;
import superwaba.ext.xplat.webservice.SOAP;
import superwaba.ext.xplat.webservice.XmlRpcException;
import waba.sys.Convert;
import waba.sys.Vm;
import waba.ui.MessageBox;

/**
 * Classe Sync
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */

public class SyncUpManager extends SIABBusiness{
	static LitebaseConnection driver ;
	static String urlWebService = "http://192.168.0.108:8080/wssiab/Handler.jws";
	//static String urlWebService = "http://200.253.233.10:8080/wssiab/Handler.jws";
	//static String urlWebService = "http://192.168.0.194:8080/axis/TestHandler.jws";
	//static String urlWebService = "http://183.245.5.136:9090/axis/TestHandler.jws";
	public static SyncUpManager criarInstancia()
	{
		
		return new SyncUpManager();
	}	
	public void setIp(String ip){
		urlWebService = "http://"+ip+":8080/wssiab/Handler.jws";
		////urlWebService = "http://"+ip+":8000/wssiab/Handler.jws";
	}
	
	public void setRegistroSincronizado(int cdAgente, String nmTabela, String tpOperacao,long cdSincUsuario) throws Exception{
		if(cdSincUsuario>0){			
			SOAP s = new SOAP("setSinc",urlWebService);
	        s.setParam(cdAgente,"cdAgente");
	        s.setParam(nmTabela,"nmTabela");
	        s.setParam(tpOperacao,"tpOperacao");
	        s.setParam(""+cdSincUsuario,"cdSincUsuario");
	        s.execute();  
		}
	}	
	
	public void setDelFicha(int cdAgente,String nmTabela) throws Exception{			
		String[] rsDel =null; 
		String[] retornoTemp=null;
		int contador=0;		
		try {			
			driver = LitebaseConnection.getInstance(CREATORID);  
	        ResultSet rs = driver.executeQuery(" SELECT * FROM "+nmTabela+" WHERE  STATUS ="+BBPDBTemp.SSK_RECORD_DELETED);
	        retornoTemp = new String[1004];			  
			StringBuffer regTemp = new StringBuffer(1024);
			while(rs.next()){
				 regTemp.setLength(0);				
				 regTemp.append(rs.getString("CD_SEGMENTO")).append("|").append(rs.getString("CD_AREA")).append("|");
				 regTemp.append(rs.getString("CD_MICROAREA")).append("|").append(rs.getString("NR_FAMILIA")).append("|");
				 regTemp.append(rs.getString("CD_ANO")).append("|").append(rs.getString("CD_PACIENTE"));   
			     retornoTemp[contador]=regTemp.toString();
			     contador++;				
			}
			if(contador>0){
				rsDel = new String[contador];
				Vm.copyArray(retornoTemp,0,rsDel,0,contador);
				SOAP s = new SOAP("deleteFicha",urlWebService);	       
		        s.setParam(cdAgente,"cdAgente");
		        s.setParam(rsDel,"rsDel");   
		        s.setParam(nmTabela,"nmTabela");
		        s.execute();
			}
		} catch (XmlRpcException e) {
			throw new Exception("Webserver: |"+e.getMessage());
		}catch(DriverException e){
			throw new Exception("Litebase: |"+e.getMessage());
		}finally{
			driver.closeAll();	
		}		
	}
	
	public void setDelFichaAcomp(int cdAgente,String nmTabela) throws Exception{			
		String[] rsDel =null; 
		String[] retornoTemp=null;
		int contador=0;		
		try {			
			driver = LitebaseConnection.getInstance(CREATORID);  
	        ResultSet rs = driver.executeQuery(" SELECT * FROM "+nmTabela+" WHERE  STATUS ="+BBPDBTemp.SSK_RECORD_DELETED);
	        retornoTemp = new String[1004];			  
			StringBuffer regTemp = new StringBuffer(1024);
			while(rs.next()){
				 regTemp.setLength(0);				
				 regTemp.append(rs.getString("CD_SEGMENTO")).append("|").append(rs.getString("CD_AREA")).append("|");
				 regTemp.append(rs.getString("CD_MICROAREA")).append("|").append(rs.getString("NR_FAMILIA")).append("|");
				 regTemp.append(rs.getString("CD_ANO")).append("|").append(rs.getString("CD_PACIENTE")).append("|");
				 regTemp.append(rs.getString("CD_PACIENTE")); 
			     retornoTemp[contador]=regTemp.toString();
			     contador++;				
			}
			if(contador>0){
				rsDel = new String[contador];
				Vm.copyArray(retornoTemp,0,rsDel,0,contador);	
				SOAP s = new SOAP("deleteFichaAcomp",urlWebService);	       
		        s.setParam(cdAgente,"cdAgente");
		        s.setParam(rsDel,"rsDel");   
		        s.setParam(nmTabela,"nmTabela");
		        s.execute();
		        
		        driver.executeUpdate(" delete FROM "+nmTabela+" WHERE  STATUS ="+BBPDBTemp.SSK_RECORD_DELETED);
			}
	        
		} catch (XmlRpcException e) {
			throw new Exception("Webserver: |"+e.getMessage());
		}catch(DriverException e){
			throw new Exception("Litebase: |"+e.getMessage());
		}finally{
			driver.closeAll();	
		}		
	}
	
	public void UpdateFicha(int cdAgente,String nmTabela) throws Exception{			
		String[] rsUpKey =null; 
		String[] rsUpNotKey =null;
		String[] retornoTempKey=null;
		String[] retornoTempNotKey=null;		
		int contador=0;			
		int incColNotKey=0;
		int incColKey=0;		
		try {			
			driver = LitebaseConnection.getInstance(CREATORID);  
	        ResultSet rs = driver.executeQuery(" SELECT * FROM "+nmTabela+" WHERE  STATUS ="+BBPDBTemp.SSK_RECORD_UPDATED);
	     
	       
	        retornoTempKey = new String[1004];	
	        retornoTempNotKey = new String[1004];	
			StringBuffer regTempNotKey = new StringBuffer(1024);
			StringBuffer regTempKey = new StringBuffer(1024);
			ResultSet rsDescKey = BBInfoDescriber.criarInstancia().getKeyTable(nmTabela);
			ResultSet rsDescNotKey = BBInfoDescriber.criarInstancia().getNotKeyTable(nmTabela);
			while(rs.next()){
				 regTempKey.setLength(0);	
				 regTempNotKey.setLength(0);
				 incColNotKey=0;
				 incColKey=0;
				 
				 while(rsDescNotKey.next()){					 
					 if(incColNotKey==0){
						 regTempNotKey.append(rs.getString(rsDescNotKey.getString("NM_COLUNA"))); 
					 }else{
						 regTempNotKey.append("|").append(rs.getString(rsDescNotKey.getString("NM_COLUNA")));	 
					 }
					  
					 incColNotKey++; 
				 }
				 while(rsDescKey.next()){					 
					 if(incColKey==0){
						 regTempKey.append(rs.getString(rsDescKey.getString("NM_COLUNA"))); 
					 }else{
						 regTempKey.append("|").append(rs.getString(rsDescKey.getString("NM_COLUNA")));
					 }
					 incColKey++; 
				 }
				 rsDescNotKey.beforeFirst();
				 rsDescKey.beforeFirst();
				 retornoTempNotKey[contador]=regTempNotKey.toString();
				 retornoTempKey[contador]=regTempKey.toString();
			     contador++;				
			}
			if(contador>0){
				rsUpKey = new String[contador];
				rsUpNotKey = new String[contador];
				Vm.copyArray(retornoTempKey,0,rsUpKey,0,contador);
				Vm.copyArray(retornoTempNotKey,0,rsUpNotKey,0,contador);
				String nmMetodo=null;
				if("FICHACADFAM".equalsIgnoreCase(nmTabela)){
					nmMetodo="updateFichaCadFam";					
				}else if("PACFAMILIA".equalsIgnoreCase(nmTabela)){
					nmMetodo="updatePacFamilia";					
				}else if("FICHATB".equalsIgnoreCase(nmTabela)){
					nmMetodo="updateFichaTb";					
				}else if("FICHATBACOMP".equalsIgnoreCase(nmTabela)){
					nmMetodo="updateFichaTbAcomp";	
				}else if("FICHAHAN".equalsIgnoreCase(nmTabela)){
					nmMetodo="updateFichaHan";	
				}else if("FICHAHANACOMP".equalsIgnoreCase(nmTabela)){
					nmMetodo="updateFichaHanAcomp";	
				}else if("FICHAHA".equalsIgnoreCase(nmTabela)){
					nmMetodo="updateFichaHa";	
				}else if("FICHAHAACOMP".equalsIgnoreCase(nmTabela)){
					nmMetodo="updateFichaHaAcomp";	
				}else if("FICHADIA".equalsIgnoreCase(nmTabela)){
					nmMetodo="updateFichaDia";	
				}else if("FICHADIAACOMP".equalsIgnoreCase(nmTabela)){
					nmMetodo="updateFichaDiaAcomp";	
				}else if("FICHAGES".equalsIgnoreCase(nmTabela)){
					nmMetodo="updateFichaGes";	
				}else if("FICHAGESACOMP".equalsIgnoreCase(nmTabela)){
					nmMetodo="updateFichaGesAcomp";	
				}else if("FICHAIDOSO".equalsIgnoreCase(nmTabela)){
					nmMetodo="updateFichaIdoso";	
				}else if("FICHAIDOSOACOMP".equalsIgnoreCase(nmTabela)){
					nmMetodo="updateFichaIdosoAcomp";	
				}else if("FICHACRIANCA".equalsIgnoreCase(nmTabela)){
					nmMetodo="updateFichaCrianca";	
				}else if("FICHACRIANCAACOMP".equalsIgnoreCase(nmTabela)){
					nmMetodo="updateFichaCriancaAcomp";	
				}
				
				SOAP s = new SOAP(nmMetodo,urlWebService);
		        s.setParam(cdAgente,"cdAgente");
		        s.setParam(nmTabela,"nmTabela");
		        s.setParam(rsUpKey,"rsUpKey");
		        s.setParam(rsUpNotKey,"rsUpNotKey");		        
		       
		        s.execute();
		        driver = LitebaseConnection.getInstance(CREATORID); 
		        driver.executeUpdate(" Update "+nmTabela+" set STATUS="+BBPDBTemp.SSK_RECORD_NULL+" WHERE  STATUS ="+BBPDBTemp.SSK_RECORD_UPDATED);
			}	        
		} catch (XmlRpcException e) {
			throw new Exception("Webserver: |"+e.getMessage());
		}catch(DriverException e){
			throw new Exception("Litebase: |"+e.getMessage());
		}	
	}
	
	public void InsertFicha(int cdAgente,String nmTabela) throws Exception{			
		String[] rsUpKey =null; 
		String[] rsUpNotKey =null;
		String[] retornoTempKey=null;
		String[] retornoTempNotKey=null;		
		int contador=0;			
		int incColNotKey=0;
		int incColKey=0;		
		try {			
			driver = LitebaseConnection.getInstance(CREATORID);  
	        ResultSet rs = driver.executeQuery(" SELECT * FROM "+nmTabela+" WHERE  STATUS ="+BBPDBTemp.SSK_RECORD_INSERTED);
	     
	       
	        retornoTempKey = new String[1004];	
	        retornoTempNotKey = new String[1004];	
			StringBuffer regTempNotKey = new StringBuffer(1024);
			StringBuffer regTempKey = new StringBuffer(1024);
			ResultSet rsDescKey = BBInfoDescriber.criarInstancia().getKeyTable(nmTabela);
			ResultSet rsDescNotKey = BBInfoDescriber.criarInstancia().getNotKeyTable(nmTabela);
			while(rs.next()){
				 regTempKey.setLength(0);	
				 regTempNotKey.setLength(0);
				 incColNotKey=0;
				 incColKey=0;
				 
				 while(rsDescNotKey.next()){					 
					 if(incColNotKey==0){
						 regTempNotKey.append(rs.getString(rsDescNotKey.getString("NM_COLUNA"))); 
					 }else{
						 regTempNotKey.append("|").append(rs.getString(rsDescNotKey.getString("NM_COLUNA")));	 
					 }
					  
					 incColNotKey++; 
				 }
				 while(rsDescKey.next()){					 
					 if(incColKey==0){
						 regTempKey.append(rs.getString(rsDescKey.getString("NM_COLUNA"))); 
					 }else{
						 regTempKey.append("|").append(rs.getString(rsDescKey.getString("NM_COLUNA")));
					 }
					 incColKey++; 
				 }
				 rsDescNotKey.beforeFirst();
				 rsDescKey.beforeFirst();
				 retornoTempNotKey[contador]=regTempNotKey.toString();
				 retornoTempKey[contador]=regTempKey.toString();
			     contador++;				
			}
			if(contador>0){
				rsUpKey = new String[contador];
				rsUpNotKey = new String[contador];
				Vm.copyArray(retornoTempKey,0,rsUpKey,0,contador);
				Vm.copyArray(retornoTempNotKey,0,rsUpNotKey,0,contador);
				String nmMetodo=null;
				if("FICHACADFAM".equalsIgnoreCase(nmTabela)){
					nmMetodo="insertFichaCadFam";					
				}else if("PACFAMILIA".equalsIgnoreCase(nmTabela)){
					nmMetodo="insertPacFamilia";					
				}else if("FICHATB".equalsIgnoreCase(nmTabela)){
					nmMetodo="insertFichaTb";					
				}else if("FICHATBACOMP".equalsIgnoreCase(nmTabela)){
					nmMetodo="insertFichaTbAcomp";	
				}else if("FICHAHAN".equalsIgnoreCase(nmTabela)){
					nmMetodo="insertFichaHan";	
				}else if("FICHAHANACOMP".equalsIgnoreCase(nmTabela)){
					nmMetodo="insertFichaHanAcomp";	
				}else if("FICHAHA".equalsIgnoreCase(nmTabela)){
					nmMetodo="insertFichaHa";	
				}else if("FICHAHAACOMP".equalsIgnoreCase(nmTabela)){
					nmMetodo="insertFichaHaAcomp";	
				}else if("FICHADIA".equalsIgnoreCase(nmTabela)){
					nmMetodo="insertFichaDia";	
				}else if("FICHADIAACOMP".equalsIgnoreCase(nmTabela)){
					nmMetodo="insertFichaDiaAcomp";	
				}else if("FICHAGES".equalsIgnoreCase(nmTabela)){
					nmMetodo="insertFichaGes";	
				}else if("FICHAGESACOMP".equalsIgnoreCase(nmTabela)){
					nmMetodo="insertFichaGesAcomp";	
				}else if("FICHAIDOSO".equalsIgnoreCase(nmTabela)){
					nmMetodo="insertFichaIdoso";	
				}else if("FICHAIDOSOACOMP".equalsIgnoreCase(nmTabela)){
					nmMetodo="insertFichaIdosoAcomp";	
				}else if("FICHACRIANCA".equalsIgnoreCase(nmTabela)){
					nmMetodo="insertFichaCrianca";	
				}else if("FICHACRIANCAACOMP".equalsIgnoreCase(nmTabela)){
					nmMetodo="insertFichaCriancaAcomp";	
				}
				
				SOAP s = new SOAP(nmMetodo,urlWebService);
		        s.setParam(cdAgente,"cdAgente");
		        s.setParam(nmTabela,"nmTabela");
		        s.setParam(rsUpKey,"rsUpKey");
		        s.setParam(rsUpNotKey,"rsUpNotKey");		        
		       
		        s.execute();
		        driver = LitebaseConnection.getInstance(CREATORID); 
		        driver.executeUpdate(" Update "+nmTabela+" set STATUS="+BBPDBTemp.SSK_RECORD_NULL+" WHERE  STATUS ="+BBPDBTemp.SSK_RECORD_INSERTED);
			}	        
		} catch (XmlRpcException e) {
			throw new Exception("Webserver: |"+e.getMessage());
		}catch(DriverException e){
			throw new Exception("Litebase: |"+e.getMessage());
		}	
	}
	
	
}
