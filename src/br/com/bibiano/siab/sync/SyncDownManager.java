package br.com.bibiano.siab.sync;

import br.com.bibiano.siab.business.SIABBusiness;
import br.com.bibiano.siab.window.ReplicationWin;
import litebase.DriverException;
import litebase.LitebaseConnection;
import litebase.PreparedStatement;
import litebase.ResultSet;
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

public class SyncDownManager extends SIABBusiness{
	static LitebaseConnection driver ;
	static String urlWebService = "http://192.168.0.108:8080/wssiab/Handler.jws";
	//static String urlWebService = "http://200.253.233.10:8080/wssiab/Handler.jws";
	//static String urlWebService = "http://192.168.0.194:8080/axis/TestHandler.jws";
	//static String urlWebService = "http://183.245.5.136:9090/axis/TestHandler.jws";
	static int vTimeOut=120000;
	
	public static SyncDownManager criarInstancia()
	{
		
		return new SyncDownManager();
	}
	
	public void setIp(String ip){
		urlWebService = "http://"+ip+":8080/wssiab/Handler.jws";
		//urlWebService = "http://"+ip+":8000/wssiab/Handler.jws";
	}
	public void limpaTabelas(boolean tmp) throws Exception{
		 driver = LitebaseConnection.getInstance(CREATORID); 
		 String sql =null;
		 String vTmp="";
		 if(tmp){
			 vTmp="temp";
		 }
		 
		 sql = "delete from ocupacao"+vTmp;
		 driver.executeUpdate(sql);	
		 sql = "delete from fichacadfam"+vTmp;
		 driver.executeUpdate(sql);
		 sql = "delete from pacfamilia"+vTmp;
		 driver.executeUpdate(sql);
		 sql = "delete from fichatb"+vTmp;
		 driver.executeUpdate(sql);		 
		 sql = "delete from fichatbacomp"+vTmp;
		 driver.executeUpdate(sql);		 
		 sql = "delete from fichahan"+vTmp;
		 driver.executeUpdate(sql);
		 sql = "delete from fichahanacomp"+vTmp;
		 driver.executeUpdate(sql);
		 sql = "delete from fichaha"+vTmp;
		 driver.executeUpdate(sql);
		 sql = "delete from fichahaacomp"+vTmp;
		 driver.executeUpdate(sql);
		 sql = "delete from fichadia"+vTmp;
		 driver.executeUpdate(sql);
		 sql = "delete from fichadiaacomp"+vTmp;
		 driver.executeUpdate(sql);
		 sql = "delete from fichages"+vTmp;
		 driver.executeUpdate(sql);
		 sql = "delete from fichagesacomp"+vTmp;
		 driver.executeUpdate(sql);		
		 sql = "delete from fichacrianca"+vTmp;
		 driver.executeUpdate(sql);
		 sql = "delete from fichacriancaacomp"+vTmp;
		 driver.executeUpdate(sql);	
		 sql = "delete from fichaidoso"+vTmp;
		 driver.executeUpdate(sql);
		 sql = "delete from fichaidosoacomp"+vTmp;
		 driver.executeUpdate(sql);	
		 driver.closeAll();
	}
	/*public void creatDescriber() throws Exception{
		LitebaseConnection driver = LitebaseConnection.getInstance(CREATORID);
		String sql="";
		if(!driver.exists("SIABDESCRIBER"))
			driver.execute("CREATE TABLE SIABDESCRIBER (NM_TABELA CHAR(20),NM_COLUNA CHAR(20),FL_CHAVE char(1))");
		
		driver.executeUpdate(" DELETE FROM SIABDESCRIBER");
		int i =0;	
		
		/////////////////// FICHACADFAM /////////////////////////////////////////////////////
		String[] nmColuna= new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_ANO","DS_ENDERECO","NUM_ENDERECO","DS_BAIRRO",
				   "DS_CEP","CD_MUNICIPIO","DT_CADASTRO","CD_UF",
				   "CD_TPCASA","DS_TPCASAESP","NUM_COMODOS","FL_ENERGIA","CD_DESTLIXO","CD_TRATAAGUA",
				   "CD_ABASTEAGUA","CD_FEZESURINA","CD_DOENCAPROCU","DS_DOENCAPROCUESP","CD_GRUPOCOMU",
				   "DS_GRUPOCOMUESP","CD_MEIOSCOMUNI","DS_MEIOSCOMUNIESP","CD_MEIOSTRANS","DS_MEIOSTRANSESP",
				   "FL_POSSUIPLAN","NUM_PESPLAN","NM_PLANO","DS_OBSERVACAO"};
		String[] keyCol= new String[]{"S","S","S","S","S","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N",
							 "N","N","N","N","N","N","N","N","N","N"};
		for(i=0;i<nmColuna.length;i++){
			sql="INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE) VALUES('FICHACADFAM','"+nmColuna[i]+"','"+keyCol[i]+"')";
			driver.executeUpdate(sql);
			Vm.debug(sql);			
		}
		
		nmColuna= new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_ANO","NM_PACIENTE","DT_NASC","NUM_IDADE","FL_SEXO",
							   "FL_MENORQUINZE","FL_ALFABETIZADO","FL_FREQESCOLA","CD_OCUPACAO",
							   "FL_ALC","FL_CHA","FL_DEF","FL_DIA","FL_DME","FL_EPI","FL_GES","FL_HAN","FL_HA","FL_MAL",
							   "FL_TBC","FL_MAIOR"};
		keyCol= new String[]{"S","S","S","S","S","S","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N"};
		for(i=0;i<nmColuna.length;i++){
			sql="INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE) VALUES('PACFAMILIA','"+nmColuna[i]+"','"+keyCol[i]+"')";
			driver.executeUpdate(sql);
			Vm.debug(sql);			
		}
		
		/////////////////// FICHACRIANCA /////////////////////////////////////////////////////		
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_ANO","CD_AVALIACAOALIMEN","DS_TIPOALIMENTACAO","FL_IMUNIZADO",
				   "FL_COMPLETARESQ","FL_DIARREIA","FL_USARAMTRO","FL_INTERNAMENTOS","FL_IRA","FL_CAXUMBA",
				   "FL_CATAPORA","FL_BAIXOPESONASC","FL_DEFICIENCIAS"};
		keyCol = new String[]{"S","S","S","S","S","S","N","N","N","N","N","N","N","N","N","N","N","N"};				 
		for(i=0;i<nmColuna.length;i++){
			sql="INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE) VALUES('FICHACRIANCA','"+nmColuna[i]+"','"+keyCol[i]+"')";
			driver.executeUpdate(sql);
			Vm.debug(sql);
		}	
		
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_MES","CD_ANO","DT_QUADRONUTRI","NUM_PESO","NUM_ALTURA"};
		keyCol = new String[]{"S","S","S","S","S","S","S","N","N","N"};				 
		for(i=0;i<nmColuna.length;i++){
			sql="INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE) VALUES('FICHACRIANCAACOMP','"+nmColuna[i]+"','"+keyCol[i]+"')";
			driver.executeUpdate(sql);
			Vm.debug(sql);
		}
		/////////////////// FICHATB /////////////////////////////////////////////////////
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_ANO","NUM_COMUNICANTES","NUM_COMUNICANTES5","DS_OBSERVACAO"		};
		keyCol = new String[]{"S","S","S","S","S","S","N","N","N"};				 
		for(i=0;i<nmColuna.length;i++){
			sql="INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE) VALUES('FICHATB','"+nmColuna[i]+"','"+keyCol[i]+"')";
			driver.executeUpdate(sql);
			Vm.debug(sql);
		}
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_MES","CD_ANO","DT_VISITA","FL_MEDICACAO","FL_REAINDESE",
				"DT_CONSULTA","FL_ESCARRO","NUM_EXAMINADOS","NUM_COMBCG"};
		keyCol = new String[]{"S","S","S","S","S","S","S","N","N","N","N","N","N","N"};				 
		for(i=0;i<nmColuna.length;i++){
			sql="INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE) VALUES('FICHATBACOMP','"+nmColuna[i]+"','"+keyCol[i]+"')";
			driver.executeUpdate(sql);
			Vm.debug(sql);
		}
		/////////////////// FICHADIA /////////////////////////////////////////////////////		
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_ANO","DS_OBSERVACAO"};
		keyCol = new String[]{"S","S","S","S","S","S","N"};				 
		for(i=0;i<nmColuna.length;i++){
			sql="INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE) VALUES('FICHADIA','"+nmColuna[i]+"','"+keyCol[i]+"')";
			driver.executeUpdate(sql);
			Vm.debug(sql);
		}
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_MES","CD_ANO","DT_VISITA","FL_DIETA","FL_EXERCICIO","FL_INSULINA",
				"FL_HIPOGLICEMIANTE","DT_ULTCONSULTA"};
		keyCol = new String[]{"S","S","S","S","S","S","S","N","N","N","N","N","N","N"};				 
		for(i=0;i<nmColuna.length;i++){
			sql="INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE) VALUES('FICHADIAACOMP','"+nmColuna[i]+"','"+keyCol[i]+"')";
			driver.executeUpdate(sql);
			Vm.debug(sql);
		}
		/////////////////// FICHAHA /////////////////////////////////////////////////////				
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_ANO","FL_FUMANTE","DS_OBSERVACAO"};
		keyCol = new String[]{"S","S","S","S","S","S","N","N"};				 
		for(i=0;i<nmColuna.length;i++){
			sql="INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE) VALUES('FICHAHA','"+nmColuna[i]+"','"+keyCol[i]+"')";
			driver.executeUpdate(sql);
			Vm.debug(sql);
		}
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_MES","CD_ANO","DT_VISITA","FL_DIETA","FL_MEDICACAO","FL_EXERCICIO",
				"DS_PRESSAO","DT_ULTCONSULTA"};
		keyCol = new String[]{"S","S","S","S","S","S","S","N","N","N","N","N","N"};				 
		for(i=0;i<nmColuna.length;i++){
			sql="INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE) VALUES('FICHAHAACOMP','"+nmColuna[i]+"','"+keyCol[i]+"')";
			driver.executeUpdate(sql);
			Vm.debug(sql);
		}
		/////////////////// FICHAGES /////////////////////////////////////////////////////	
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_ANO","DT_ULTREGRA","DT_PARTO","DT_VACINA1","DT_VACINA2",
				"DT_VACINA3","DT_VACINAR","FL_SEISGESTA","FL_NATIMORTO","FL_36ANOSMAIS","FL_MENOS20","FL_SANGRAMENTO",
				"FL_EDEMA","FL_DIABETES","FL_PRESSAOALTA","DT_NASCVIVO","DT_NASCMORTO","DT_ABORTO","DT_PUERPERIO1","DT_PUERPERIO2","DS_ONSERVACAO"};
		keyCol = new String[]{"S","S","S","S","S","S","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N"};				 
		for(i=0;i<nmColuna.length;i++){
			sql="INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE) VALUES('FICHAGES','"+nmColuna[i]+"','"+keyCol[i]+"')";
			driver.executeUpdate(sql);
			Vm.debug(sql);
		}
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_MES","CD_ANO","FL_ESTADONUTRI","DT_CONSPRENATAL","DT_VISITA"};
		keyCol = new String[]{"S","S","S","S","S","S","S","N","N","N"};				 
		for(i=0;i<nmColuna.length;i++){
			sql="INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE) VALUES('FICHAGESACOMP','"+nmColuna[i]+"','"+keyCol[i]+"')";
			driver.executeUpdate(sql);
			Vm.debug(sql);
		}
		/////////////////// FICHAHAN /////////////////////////////////////////////////////			
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_ANO","NUM_COMUNICANTES","DS_OBSERVACAO"};
		keyCol = new String[]{"S","S","S","S","S","S","N","N"};				 
		for(i=0;i<nmColuna.length;i++){
			sql="INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE) VALUES('FICHAHAN','"+nmColuna[i]+"','"+keyCol[i]+"')";
			driver.executeUpdate(sql);
			Vm.debug(sql);
		}
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_MES","CD_ANO","DT_VISITA","FL_MEDICACAO","DT_ULTDOSE","FL_AUTOCUIDADOS",
				"DT_CONSULTA","NUM_EXAMINADOS","NUM_COMBCG"};
		keyCol = new String[]{"S","S","S","S","S","S","S","N","N","N","N","N","N","N"};				 
		for(i=0;i<nmColuna.length;i++){
			sql="INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE) VALUES('FICHAHANACOMP','"+nmColuna[i]+"','"+keyCol[i]+"')";
			driver.executeUpdate(sql);
			Vm.debug(sql);
		}
		/////////////////// FICHAIDOSO /////////////////////////////////////////////////////		
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_ANO","FL_FUMANTE","DT_DTP","DT_INFLUENZA","DT_PNEUMONOCOCOS",
				"FL_HIPERTENSAO","FL_TUBERCULOSE","FL_ALZHEIMER","FL_DEFFISICA","FL_HANSENIASE","FL_MALPARKSON",
				"FL_CANCER","FL_ACAMADO","FL_INTERNAMENTOS","FL_SEQUELASAVC","FL_ALCOLATRA"};
		keyCol = new String[]{"S","S","S","S","S","S","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N"};				 
		for(i=0;i<nmColuna.length;i++){
			sql="INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE) VALUES('FICHAIDOSO','"+nmColuna[i]+"','"+keyCol[i]+"')";
			driver.executeUpdate(sql);
			Vm.debug(sql);
		}
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_MES","CD_ANO","FL_ESTADONUTRI","DT_VISITA","DT_ACOMPPSF"};
		keyCol = new String[]{"S","S","S","S","S","S","S","N","N","N"};				 
		for(i=0;i<nmColuna.length;i++){
			sql="INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE) VALUES('FICHAIDOSOACOMP','"+nmColuna[i]+"','"+keyCol[i]+"')";
			driver.executeUpdate(sql);
			Vm.debug(sql);
		}
		
		nmColuna = new String[]{"CD_OCUPACAO","DS_OCUPACAO"};
		keyCol = new String[]{"S","N"};				 
		for(i=0;i<nmColuna.length;i++){
			sql="INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE) VALUES('OCUPACAO','"+nmColuna[i]+"','"+keyCol[i]+"')";
			driver.executeUpdate(sql);
			Vm.debug(sql);
		}
		Vm.debug("Pdbs Gerado com Sucesso!");
	}*/

	public void creatDescriber() throws Exception{
		LitebaseConnection driver = LitebaseConnection.getInstance(CREATORID);
		//StringBuffer sql=new StringBuffer(1024);	
		
		
		if(!driver.exists("SIABDESCRIBER"))
			driver.execute("CREATE TABLE SIABDESCRIBER (NM_TABELA CHAR(20),NM_COLUNA CHAR(20),FL_CHAVE char(1),NR_ORDEM INT)");
		
		driver.executeUpdate(" DELETE FROM SIABDESCRIBER");
		int i =0;	
		
		/////////////////// FICHACADFAM /////////////////////////////////////////////////////
		String[]nmColuna = {"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_ANO","DS_ENDERECO","NUM_ENDERECO","DS_BAIRRO","DS_CEP","CD_MUNICIPIO",
		"DT_CADASTRO","CD_UF","CD_TPCASA","DS_TPCASAESP","NUM_COMODOS","FL_ENERGIA","CD_DESTLIXO","CD_TRATAAGUA","CD_ABASTEAGUA", 
		"CD_FEZESURINA","CD_DOENCAPROCU","DS_DOENCAPROCUESP","CD_GRUPOCOMU","DS_GRUPOCOMUESP","CD_MEIOSCOMUNI","DS_MEIOSCOMUNIESP",
		"CD_MEIOSTRANS","DS_MEIOSTRANSESP","FL_POSSUIPLAN","NUM_PESPLAN","NM_PLANO","DS_OBSERVACAO"};
		String[] keyCol= new String[]{"S","S","S","S","S","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N",
							 "N","N","N","N","N","N","N","N","N","N"};
		
		PreparedStatement ps = driver.prepareStatement("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHACADFAM',?,?,?)");
		driver.setRowInc("SIABDESCRIBER",nmColuna.length);
		for(i=0;i<nmColuna.length;i++){
			/*sql.setLength(0);
			sql.append("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHACADFAM','").append(nmColuna[i]);
			sql.append("','").append(keyCol[i]).append("',").append(i).append(")");
			driver.executeUpdate(sql.toString());*/
			//Vm.debug(sql.toString());
			ps.setString(0,nmColuna[i]);
			ps.setString(1,keyCol[i]);
			ps.setInt(2,i);
			ps.executeUpdate();
		}
		
		
		nmColuna= new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_ANO","NM_PACIENTE","DT_NASCIMENTO","NUM_IDADE","FL_SEXO",
							   "FL_MENORQUINZE","FL_ALFABETIZADO","FL_FREQESCOLA","CD_OCUPACAO",
							   "FL_ALC","FL_CHA","FL_DEF","FL_DIA","FL_DME","FL_EPI","FL_GES","FL_HAN","FL_HA","FL_MAL",
							   "FL_TBC","FL_MAIOR"};
		keyCol= new String[]{"S","S","S","S","S","S","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N"};
		ps = driver.prepareStatement("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('PACFAMILIA',?,?,?)");
		driver.setRowInc("SIABDESCRIBER",nmColuna.length);
		for(i=0;i<nmColuna.length;i++){
			/*sql.setLength(0);
			sql.append("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('PACFAMILIA','").append(nmColuna[i]);
			sql.append("','").append(keyCol[i]).append("',").append(i).append(")");		
			driver.executeUpdate(sql.toString());*/		
			ps.setString(0,nmColuna[i]);
			ps.setString(1,keyCol[i]);
			ps.setInt(2,i);
			ps.executeUpdate();
		}
		
		/////////////////// FICHACRIANCA /////////////////////////////////////////////////////		
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_ANO","CD_AVALIACAOALIMEN","DS_TIPOALIMENTACAO","FL_IMUNIZADO",
				   "FL_COMPLETARESQ","FL_DIARREIA","FL_USARAMTRO","FL_INTERNAMENTOS","FL_IRA","FL_CAXUMBA",
				   "FL_CATAPORA","FL_BAIXOPESONASC","FL_DEFICIENCIAS"};
		keyCol = new String[]{"S","S","S","S","S","S","N","N","N","N","N","N","N","N","N","N","N","N"};		
		ps = driver.prepareStatement("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHACRIANCA',?,?,?)");
		driver.setRowInc("SIABDESCRIBER",nmColuna.length);
		for(i=0;i<nmColuna.length;i++){
			/*sql.setLength(0);
			sql.append("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHACRIANCA','").append(nmColuna[i]);
			sql.append("','").append(keyCol[i]).append("',").append(i).append(")");		
			driver.executeUpdate(sql.toString());*/
			ps.setString(0,nmColuna[i]);
			ps.setString(1,keyCol[i]);
			ps.setInt(2,i);
			ps.executeUpdate();
		}	
		
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_MES","CD_ANO","DT_QUADRONUTRI","NUM_PESO","NUM_ALTURA"};
		keyCol = new String[]{"S","S","S","S","S","S","S","N","N","N"};	
		ps = driver.prepareStatement("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHACRIANCAACOMP',?,?,?)");
		driver.setRowInc("SIABDESCRIBER",nmColuna.length);
		for(i=0;i<nmColuna.length;i++){
			/*sql.setLength(0);
			sql.append("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHACRIANCAACOMP','").append(nmColuna[i]);
			sql.append("','").append(keyCol[i]).append("',").append(i).append(")");		
			driver.executeUpdate(sql.toString());		
			*/
			ps.setString(0,nmColuna[i]);
			ps.setString(1,keyCol[i]);
			ps.setInt(2,i);
			ps.executeUpdate();
		}
		/////////////////// FICHATB /////////////////////////////////////////////////////
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_ANO","NUM_COMUNICANTES","NUM_COMUNICANTES5","DS_OBSERVACAO"		};
		keyCol = new String[]{"S","S","S","S","S","S","N","N","N"};		
		ps = driver.prepareStatement("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHATB',?,?,?)");
		driver.setRowInc("SIABDESCRIBER",nmColuna.length);
		for(i=0;i<nmColuna.length;i++){
			/*sql.setLength(0);
			sql.append("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHATB','").append(nmColuna[i]);
			sql.append("','").append(keyCol[i]).append("',").append(i).append(")");		
			driver.executeUpdate(sql.toString());*/
			ps.setString(0,nmColuna[i]);
			ps.setString(1,keyCol[i]);
			ps.setInt(2,i);
			ps.executeUpdate();
		}
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_MES","CD_ANO","DT_VISITA","FL_MEDICACAO","FL_REAINDESE",
				"DT_CONSULTA","FL_ESCARRO","NUM_EXAMINADOS","NUM_COMBCG"};
		keyCol = new String[]{"S","S","S","S","S","S","S","N","N","N","N","N","N","N"};		
		ps = driver.prepareStatement("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHATBACOMP',?,?,?)");
		driver.setRowInc("SIABDESCRIBER",nmColuna.length);
		for(i=0;i<nmColuna.length;i++){
			/*sql.setLength(0);
			sql.append("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHATBACOMP','").append(nmColuna[i]);
			sql.append("','").append(keyCol[i]).append("',").append(i).append(")");		
			driver.executeUpdate(sql.toString());	*/
			ps.setString(0,nmColuna[i]);
			ps.setString(1,keyCol[i]);
			ps.setInt(2,i);
			ps.executeUpdate();
		}
		/////////////////// FICHADIA /////////////////////////////////////////////////////		
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_ANO","DS_OBSERVACAO"};
		keyCol = new String[]{"S","S","S","S","S","S","N"};	
		ps = driver.prepareStatement("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHADIA',?,?,?)");
		driver.setRowInc("SIABDESCRIBER",nmColuna.length);
		for(i=0;i<nmColuna.length;i++){
			/*sql.setLength(0);
			sql.append("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHADIA','").append(nmColuna[i]);
			sql.append("','").append(keyCol[i]).append("',").append(i).append(")");		
			driver.executeUpdate(sql.toString());*/
			ps.setString(0,nmColuna[i]);
			ps.setString(1,keyCol[i]);
			ps.setInt(2,i);
			ps.executeUpdate();
		}
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_MES","CD_ANO","DT_VISITA","FL_DIETA","FL_EXERCICIO","FL_INSULINA",
				"FL_HIPOGLICEMIANTE","DT_ULTCONSULTA"};
		keyCol = new String[]{"S","S","S","S","S","S","S","N","N","N","N","N","N","N"};		
		ps = driver.prepareStatement("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHADIAACOMP',?,?,?)");
		driver.setRowInc("SIABDESCRIBER",nmColuna.length);
		for(i=0;i<nmColuna.length;i++){
			/*sql.setLength(0);
			sql.append("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHADIAACOMP','").append(nmColuna[i]);
			sql.append("','").append(keyCol[i]).append("',").append(i).append(")");			
			driver.executeUpdate(sql.toString());*/
			ps.setString(0,nmColuna[i]);
			ps.setString(1,keyCol[i]);
			ps.setInt(2,i);
			ps.executeUpdate();
		}
		/////////////////// FICHAHA /////////////////////////////////////////////////////				
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_ANO","FL_FUMANTE","DS_OBSERVACAO"};
		keyCol = new String[]{"S","S","S","S","S","S","N","N"};			
		ps = driver.prepareStatement("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHAHA',?,?,?)");
		driver.setRowInc("SIABDESCRIBER",nmColuna.length);
		for(i=0;i<nmColuna.length;i++){
			/*sql.setLength(0);
			sql.append("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHAHA','").append(nmColuna[i]);
			sql.append("','").append(keyCol[i]).append("',").append(i).append(")");
			driver.executeUpdate(sql.toString());*/
			ps.setString(0,nmColuna[i]);
			ps.setString(1,keyCol[i]);
			ps.setInt(2,i);
			ps.executeUpdate();
		}
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_MES","CD_ANO","DT_VISITA","FL_DIETA","FL_MEDICACAO","FL_EXERCICIO",
				"DS_PRESSAO","DT_ULTCONSULTA"};
		keyCol = new String[]{"S","S","S","S","S","S","S","N","N","N","N","N","N"};	
		ps = driver.prepareStatement("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHAHAACOMP',?,?,?)");
		driver.setRowInc("SIABDESCRIBER",nmColuna.length);
		for(i=0;i<nmColuna.length;i++){
			/*sql.setLength(0);
			sql.append("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHAHAACOMP','").append(nmColuna[i]);
			sql.append("','").append(keyCol[i]).append("',").append(i).append(")");			
			driver.executeUpdate(sql.toString());*/
			ps.setString(0,nmColuna[i]);
			ps.setString(1,keyCol[i]);
			ps.setInt(2,i);
			ps.executeUpdate();
		}
		/////////////////// FICHAGES /////////////////////////////////////////////////////	
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_ANO","DT_ULTREGRA","DT_PARTO","DT_VACINA1","DT_VACINA2",
				"DT_VACINA3","DT_VACINAR","FL_SEISGESTA","FL_NATIMORTO","FL_36ANOSMAIS","FL_MENOS20","FL_SANGRAMENTO",
				"FL_EDEMA","FL_DIABETES","FL_PRESSAOALTA","DT_NASCVIVO","DT_NASCMORTO","DT_ABORTO","DT_PUERPERIO1","DT_PUERPERIO2","DS_OBSERVACAO"};
		keyCol = new String[]{"S","S","S","S","S","S","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N"};
		ps = driver.prepareStatement("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHAGES',?,?,?)");
		driver.setRowInc("SIABDESCRIBER",nmColuna.length);
		for(i=0;i<nmColuna.length;i++){
			/*sql.setLength(0);
			sql.append("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHAGES','").append(nmColuna[i]);
			sql.append("','").append(keyCol[i]).append("',").append(i).append(")");			
			driver.executeUpdate(sql.toString());*/
			ps.setString(0,nmColuna[i]);
			ps.setString(1,keyCol[i]);
			ps.setInt(2,i);
			ps.executeUpdate();
		}
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_MES","CD_ANO","FL_ESTADONUTRI","DT_CONSPRENATAL","DT_VISITA"};
		keyCol = new String[]{"S","S","S","S","S","S","S","N","N","N"};		
		ps = driver.prepareStatement("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHAGESACOMP',?,?,?)");
		driver.setRowInc("SIABDESCRIBER",nmColuna.length);
		for(i=0;i<nmColuna.length;i++){
			/*sql.setLength(0);
			sql.append("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHAGESACOMP','").append(nmColuna[i]);
			sql.append("','").append(keyCol[i]).append("',").append(i).append(")");			
			driver.executeUpdate(sql.toString());*/
			ps.setString(0,nmColuna[i]);
			ps.setString(1,keyCol[i]);
			ps.setInt(2,i);
			ps.executeUpdate();
		}
		/////////////////// FICHAHAN /////////////////////////////////////////////////////			
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_ANO","NUM_COMUNICANTES","DS_OBSERVACAO"};
		keyCol = new String[]{"S","S","S","S","S","S","N","N"};	
		ps = driver.prepareStatement("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHAHAN',?,?,?)");
		driver.setRowInc("SIABDESCRIBER",nmColuna.length);
		for(i=0;i<nmColuna.length;i++){
			/*sql.setLength(0);
			sql.append("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHAHAN','").append(nmColuna[i]);
			sql.append("','").append(keyCol[i]).append("',").append(i).append(")");
			driver.executeUpdate(sql.toString());	*/
			ps.setString(0,nmColuna[i]);
			ps.setString(1,keyCol[i]);
			ps.setInt(2,i);
			ps.executeUpdate();
		}
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_MES","CD_ANO","DT_VISITA","FL_MEDICACAO","DT_ULTDOSE","FL_AUTOCUIDADOS",
				"DT_CONSULTA","NUM_EXAMINADOS","NUM_COMBCG"};
		keyCol = new String[]{"S","S","S","S","S","S","S","N","N","N","N","N","N","N"};	
		ps = driver.prepareStatement("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHAHANACOMP',?,?,?)");
		driver.setRowInc("SIABDESCRIBER",nmColuna.length);
		for(i=0;i<nmColuna.length;i++){
			/*sql.setLength(0);
			sql.append("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHAHANACOMP','").append(nmColuna[i]);
			sql.append("','").append(keyCol[i]).append("',").append(i).append(")");		
			driver.executeUpdate(sql.toString());	*/
			ps.setString(0,nmColuna[i]);
			ps.setString(1,keyCol[i]);
			ps.setInt(2,i);
			ps.executeUpdate();
		}
		/////////////////// FICHAIDOSO /////////////////////////////////////////////////////		
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_ANO","FL_FUMANTE","DT_DTP","DT_INFLUENZA","DT_PNEUMONOCOCOS",
				"FL_HIPERTENSAO","FL_TUBERCULOSE","FL_ALZHEIMER","FL_DEFFISICA","FL_HANSENIASE","FL_MALPARKSON",
				"FL_CANCER","FL_ACAMADO","FL_INTERNAMENTOS","FL_SEQUELASAVC","FL_ALCOLATRA"};
		keyCol = new String[]{"S","S","S","S","S","S","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N"};
		ps = driver.prepareStatement("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHAIDOSO',?,?,?)");
		driver.setRowInc("SIABDESCRIBER",nmColuna.length);
		for(i=0;i<nmColuna.length;i++){
			/*sql.setLength(0);
			sql.append("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHAIDOSO','").append(nmColuna[i]);
			sql.append("','").append(keyCol[i]).append("',").append(i).append(")");		
			driver.executeUpdate(sql.toString());*/
			ps.setString(0,nmColuna[i]);
			ps.setString(1,keyCol[i]);
			ps.setInt(2,i);
			ps.executeUpdate();
		}
		nmColuna = new String[]{"CD_SEGMENTO","CD_AREA","CD_MICROAREA","NR_FAMILIA","CD_PACIENTE","CD_MES","CD_ANO","FL_ESTADONUTRI","DT_VISITA","DT_ACOMPPSF"};
		keyCol = new String[]{"S","S","S","S","S","S","S","N","N","N"};		
		ps = driver.prepareStatement("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHAIDOSOACOMP',?,?,?)");
		driver.setRowInc("SIABDESCRIBER",nmColuna.length);
		for(i=0;i<nmColuna.length;i++){
			/*sql.setLength(0);
			sql.append("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('FICHAIDOSOACOMP','").append(nmColuna[i]);
			sql.append("','").append(keyCol[i]).append("',").append(i).append(")");	
			driver.executeUpdate(sql.toString());*/
			ps.setString(0,nmColuna[i]);
			ps.setString(1,keyCol[i]);
			ps.setInt(2,i);
			ps.executeUpdate();
		}
		////////////////// OCUPACAO /////////////////////////////////////////////////////
		nmColuna = new String[]{"CD_OCUPACAO","DS_OCUPACAO"};
		keyCol = new String[]{"S","N"};		
		ps = driver.prepareStatement("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('OCUPACAO',?,?,?)");
		driver.setRowInc("SIABDESCRIBER",nmColuna.length);
		for(i=0;i<nmColuna.length;i++){
			/*sql.setLength(0);
			sql.append("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE, NR_ORDEM) VALUES('OCUPACAO','").append(nmColuna[i]);
			sql.append("','").append(keyCol[i]).append("',").append(i).append(")");
			driver.executeUpdate(sql.toString());*/
			ps.setString(0,nmColuna[i]);
			ps.setString(1,keyCol[i]);
			ps.setInt(2,i);
			ps.executeUpdate();
		}
		//////////////////AGENTE /////////////////////////////////////////////////////
		nmColuna = new String[]{"CD_AGENTE","NM_AGENTE","DS_LOGIN","DS_SENHA","CD_SEG","CD_AREA","CD_MICROA","CD_ANO"};
		keyCol = new String[]{"S","N","N","N","N","N","N","N"};		
		ps = driver.prepareStatement("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE,NR_ORDEM) VALUES('AGENTE',?,?,?)");
		driver.setRowInc("SIABDESCRIBER",nmColuna.length);
		for(i=0;i<nmColuna.length;i++){
			/*sql.setLength(0);
			sql.append("INSERT INTO SIABDESCRIBER (NM_TABELA, NM_COLUNA,FL_CHAVE, NR_ORDEM) VALUES('AGENTE','").append(nmColuna[i]);
			sql.append("','").append(keyCol[i]).append("',").append(i).append(")");
			driver.executeUpdate(sql.toString());*/
			ps.setString(0,nmColuna[i]);
			ps.setString(1,keyCol[i]);
			ps.setInt(2,i);
			ps.executeUpdate();
		}
		driver.setRowInc("SIABDESCRIBER",-1);
		
		Vm.debug("Describer Gerado com Sucesso!");
		driver.closeAll();
	}
	
	public long getDownOcupacao(int cdAgente,String tpOperacao) throws Exception{
		String[] cols=null;
		long ultCdSincUsuario=0;
		try {
			SOAP s = new SOAP("getOcupacao",urlWebService);
		    s.openTimeout=vTimeOut;
		    s.readTimeout=vTimeOut;
	        s.setParam(cdAgente,"cdAgente");
	        s.setParam(tpOperacao,"tpOperacao");       
				s.execute();
	        String sql =null;
	        driver = LitebaseConnection.getInstance(CREATORID);  
			
	        Object objRetorno = s.getAnswer();
	        if(objRetorno!=null){
	        	String [] retorno=null;
		        if(objRetorno instanceof String[]){
		        	retorno =(String[]) objRetorno;
		        }else{
		        	retorno =  new String[1];
		        	retorno[0] =(String) objRetorno;
		        }	
		        String status=null;
		        if("A".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_DELETEALL;
		        }else if("D".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_DELETED;
		        }else if("U".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_UPDATED;
		        }else if("I".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_INSERTED;
		        }
		        
		        
		        driver.executeUpdate("delete from ocupacaotemp where STATUS="+status);
		        int numRegistros = retorno.length;
				int pointerRs=0;
				
				ReplicationWin.progBarReplication.setValue(0);
				for(int i=0;i<retorno.length;i++){
					cols=Convert.tokenizeString(retorno[i],'|');			
					sql = "INSERT INTO ocupacaotemp (CD_OCUPACAO,DS_OCUPACAO,STATUS) VALUES("+cols[0]+",'"+cols[1]+"','"+cols[2]+"')";
					driver.executeUpdate(sql);
					pointerRs++;
					if( Math.ceil(((float)pointerRs/numRegistros)*100) % 5==0){
						ReplicationWin.progBarReplication.setValue(Convert.toInt(""+Math.round(Math.ceil(((float)pointerRs/numRegistros)*100))));				
					}
				}
				
				ReplicationWin.progBarReplication.setValue(100);
				ultCdSincUsuario =Convert.toLong(cols[4]);			
	        }	
	        driver.closeAll();
			
		
		} catch (XmlRpcException e) {
			throw new Exception("Webserver: |"+e.getMessage());
		}catch(DriverException e){
			throw new Exception("Litebase: |"+e.getMessage());
		}
		
		return ultCdSincUsuario;
	}	
	
	public long getDownFichaCadFam(int cdAgente,String tpOperacao) throws Exception{
			String[] cols=null;
			long ultCdSincUsuario=0;
		try{
			SOAP s = new SOAP("getFichaCadFam",urlWebService);			
			s.openTimeout=vTimeOut;
		    s.readTimeout=vTimeOut;
	        s.setParam(cdAgente,"cdAgente");
	        s.setParam(tpOperacao,"tpOperacao");	        
	        s.execute();      
	        String sql =null;
	        driver = LitebaseConnection.getInstance(CREATORID);  
	        
	        Object objRetorno = s.getAnswer();
	        if(objRetorno!=null){
	        	String [] retorno=null;
		        if(objRetorno instanceof String[]){
		        	retorno =(String[]) objRetorno;
		        }else{
		        	retorno =  new String[1];
		        	retorno[0] =(String) objRetorno;
		        }
		        String status=null;
		        if("A".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_DELETEALL;
		        }else if("D".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_DELETED;
		        }else if("U".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_UPDATED;
		        }else if("I".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_INSERTED;
		        }
		        
		        driver.executeUpdate("delete from fichacadfamtemp  where STATUS="+status);
		        int numRegistros = retorno.length;
				int pointerRs=0;				
				for(int i=0;i<retorno.length;i++){
					cols=Convert.tokenizeString(retorno[i],'|');				
					 sql = "INSERT INTO fichacadfamtemp (CD_SEGMENTO, CD_AREA, CD_MICROAREA, NR_FAMILIA, CD_ANO, DS_ENDERECO, "+
						 " NUM_ENDERECO, DS_BAIRRO, DS_CEP, CD_MUNICIPIO, DT_CADASTRO, CD_UF, CD_TPCASA, DS_TPCASAESP, NUM_COMODOS,"+
					     " FL_ENERGIA, CD_DESTLIXO, CD_TRATAAGUA, CD_ABASTEAGUA, CD_FEZESURINA,  CD_DOENCAPROCU, DS_DOENCAPROCUESP, "+
					     " CD_GRUPOCOMU, DS_GRUPOCOMUESP, CD_MEIOSCOMUNI, DS_MEIOSCOMUNIESP, CD_MEIOSTRANS, DS_MEIOSTRANSESP, "+
					     " FL_POSSUIPLAN, NUM_PESPLAN, NM_PLANO, DS_OBSERVACAO ,STATUS ) VALUES("+cols[0]+","+cols[1]+","+cols[2]+
					     ","+cols[3]+","+cols[4]+",'"+cols[5]+"',"+cols[6]+ ",'"+cols[7]+"','"+cols[8]+"',"+cols[9]+",'"+cols[10]+"','"+cols[11]+
						 "',"+cols[12]+",'"+cols[13]+"',"+cols[14]+",'"+cols[15]+"',"+cols[16]+","+cols[17]+","+cols[18]+","+cols[19]+","+cols[20]+
						 ",'"+cols[21]+"',"+cols[22]+",'"+cols[23]+"',"+cols[24]+",'"+cols[25]+"',"+cols[26]+",'"+cols[27]+"','"+cols[28]+"',"+cols[29]+
						 ",'"+cols[30]+"','"+cols[31]+"',"+cols[32]+")";
					driver.executeUpdate(sql);
					pointerRs++;
					if( Math.ceil(((float)pointerRs/numRegistros)*100) % 5==0){
						ReplicationWin.progBarReplication.setValue(Convert.toInt(""+Math.round(Math.ceil(((float)pointerRs/numRegistros)*100))));				
					}
				}				
				ultCdSincUsuario =Convert.toLong(cols[34]);
			}
	        driver.closeAll();
		} catch (XmlRpcException e) {
			throw new Exception("Erro Webserver:|"+e.getMessage());
		}catch(DriverException e){
			throw new Exception("Erro Litebase:|"+e.getMessage());
		}
			return ultCdSincUsuario;
	}
	public long getDownPacFamilia(int cdAgente,String tpOperacao) throws Exception{
		String[] cols=null;
		long ultCdSincUsuario=0;
		try{
			SOAP s = new SOAP("getPacFamilia",urlWebService);
			s.openTimeout=vTimeOut;
		    s.readTimeout=vTimeOut;
	        s.setParam(cdAgente,"cdAgente");
	        s.setParam(tpOperacao,"tpOperacao");        
	        s.execute();      
	       
	        driver = LitebaseConnection.getInstance(CREATORID);
			
			StringBuffer sql = new StringBuffer(1024);
			Object objRetorno = s.getAnswer();
		    if(objRetorno!=null){
	        	String [] retorno=null;
		        if(objRetorno instanceof String[]){
		        	retorno =(String[]) objRetorno;
		        }else{
		        	retorno =  new String[1];
		        	retorno[0] =(String) objRetorno;
		        }	
		        String status=null;
		        if("A".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_DELETEALL;
		        }else if("D".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_DELETED;
		        }else if("U".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_UPDATED;
		        }else if("I".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_INSERTED;
		        }
		        driver.executeUpdate("delete from pacfamiliatemp where STATUS="+status);
		        int numRegistros = retorno.length;
				int pointerRs=0;				
				for(int i=0;i<retorno.length;i++){
					cols=Convert.tokenizeString(retorno[i],'|');
					sql.setLength(0);
					sql.append("INSERT INTO pacfamiliatemp(CD_SEGMENTO, CD_AREA, CD_MICROAREA, NR_FAMILIA, CD_PACIENTE, CD_ANO, NM_PACIENTE,");
					sql.append("DT_NASCIMENTO, NUM_IDADE , FL_SEXO , FL_MENORQUINZE, FL_ALFABETIZADO, FL_FREQESCOLA, CD_OCUPACAO, FL_ALC,");
					sql.append(" FL_CHA, FL_DEF, FL_DIA, FL_DME, FL_EPI, FL_GES, FL_HAN, FL_HA, FL_MAL, FL_TBC ,FL_MAIOR, STATUS) ");
					sql.append(" VALUES(").append(cols[0]).append(",").append(cols[1]).append(",").append(cols[2]).append(",").append(cols[3]);
					sql.append(",").append(cols[4]).append(",").append(cols[5]).append(",'").append(cols[6]).append("','").append(cols[7]);
					sql.append("',").append(cols[8]).append(",'").append(cols[9]).append("','").append(cols[10]).append("','").append(cols[11]);
					sql.append("','").append(cols[12]).append("',").append(cols[13]).append(",'").append(cols[14]).append("','").append(cols[15]);
					sql.append("','").append(cols[16]).append("','").append(cols[17]).append("','").append(cols[18]).append("','").append(cols[19]);
					sql.append("','").append(cols[20]).append("','").append(cols[21]).append("','").append(cols[22]).append("','").append(cols[23]);
					sql.append("','").append(cols[24]).append("','").append(cols[25]).append("',").append(cols[26]).append(")");
						 
					driver.executeUpdate(sql.toString());
					pointerRs++;
					if( Math.ceil(((float)pointerRs/numRegistros)*100) % 5==0){
						ReplicationWin.progBarReplication.setValue(Convert.toInt(""+Math.round(Math.ceil(((float)pointerRs/numRegistros)*100))));				
					}	
				}
				ReplicationWin.progBarReplication.setValue(100);
				ultCdSincUsuario =Convert.toLong(cols[28]);
			}
		    driver.closeAll();
		} catch (XmlRpcException e) {
			throw new Exception("Erro Webserver:|"+e.getMessage());
		}catch(DriverException e){
			throw new Exception("Erro Litebase:|"+e.getMessage());
		}   
		return ultCdSincUsuario;
}
	
	public long getDownFichaTb(int cdAgente,String tpOperacao) throws Exception{
		String[] cols=null;
		long ultCdSincUsuario=0;
		try{
			SOAP s = new SOAP("getFichaTb",urlWebService);
			s.openTimeout=vTimeOut;
		    s.readTimeout=vTimeOut;
	       
	        s.setParam(cdAgente,"cdAgente");
	        s.setParam(tpOperacao,"tpOperacao");        
	        s.execute();      
	       
	        driver = LitebaseConnection.getInstance(CREATORID);  
	        
			
			StringBuffer sql = new StringBuffer(1024);
		    Object objRetorno = s.getAnswer();
	        if(objRetorno!=null){
	        	String [] retorno=null;
		        if(objRetorno instanceof String[]){
		        	retorno =(String[]) objRetorno;
		        }else{
		        	retorno =  new String[1];
		        	retorno[0] =(String) objRetorno;
		        }	
		        String status=null;
		        if("A".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_DELETEALL;
		        }else if("D".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_DELETED;
		        }else if("U".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_UPDATED;
		        }else if("I".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_INSERTED;
		        }
		        driver.executeUpdate("delete from fichatbtemp where STATUS="+status);
				for(int i=0;i<retorno.length;i++){
					cols=Convert.tokenizeString(retorno[i],'|');
					sql.setLength(0);
					sql.append("INSERT INTO fichatbtemp(CD_SEGMENTO, CD_AREA, CD_MICROAREA, NR_FAMILIA, CD_PACIENTE, CD_ANO, NUM_COMUNICANTES, NUM_COMUNICANTES5, DS_OBSERVACAO, STATUS )");
					sql.append(" VALUES(").append(cols[0]).append(",").append(cols[1]).append(",").append(cols[2]).append(",").append(cols[3]);
					sql.append(",").append(cols[4]).append(",").append(cols[5]).append(",").append(cols[6]).append(",").append(cols[7]);
					sql.append(",'").append(cols[8]).append("',").append(cols[9]).append(")");
						 
					driver.executeUpdate(sql.toString());
				}
				ultCdSincUsuario =Convert.toLong(cols[11]);
			}
	        driver.closeAll();
		} catch (XmlRpcException e) {
			throw new Exception("Erro Webserver:|"+e.getMessage());
		}catch(DriverException e){
			throw new Exception("Erro Litebase:|"+e.getMessage());
		}
		return ultCdSincUsuario;
	}
	
	public long getDownFichaTbAcomp(int cdAgente,String tpOperacao) throws Exception{
		String[] cols=null;
		long ultCdSincUsuario=0;
		try{
			SOAP s = new SOAP("getFichaTbAcomp",urlWebService);	
			s.openTimeout=vTimeOut;
		    s.readTimeout=vTimeOut;
	        s.setParam(cdAgente,"cdAgente");
	        s.setParam(tpOperacao,"tpOperacao");        
	        s.execute();      
	       
	        driver = LitebaseConnection.getInstance(CREATORID);  
	        
			
			StringBuffer sql = new StringBuffer(1024);
		    Object objRetorno = s.getAnswer();
	        if(objRetorno!=null){
	        	String [] retorno=null;
		        if(objRetorno instanceof String[]){
		        	retorno =(String[]) objRetorno;
		        }else{
		        	retorno =  new String[1];
		        	retorno[0] =(String) objRetorno;
		        }	
		        String status=null;
		        if("A".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_DELETEALL;
		        }else if("D".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_DELETED;
		        }else if("U".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_UPDATED;
		        }else if("I".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_INSERTED;
		        }
		        driver.executeUpdate("delete from fichatbacomptemp where STATUS="+status);
				for(int i=0;i<retorno.length;i++){
					cols=Convert.tokenizeString(retorno[i],'|');
					sql.setLength(0);
					sql.append("INSERT INTO fichatbacomptemp(cd_segmento ,  cd_area,  cd_microarea,  nr_familia,   cd_paciente, cd_ano ,  cd_mes ,");
				    sql.append("  dt_visita,  fl_medicacao,fl_reaindese,  dt_consulta,  fl_escarro,  num_examinados,  num_combcg ,STATUS)");
					sql.append(" VALUES(").append(cols[0]).append(",").append(cols[1]).append(",").append(cols[2]).append(",").append(cols[3]);
					sql.append(",").append(cols[4]).append(",").append(cols[5]).append(",").append(cols[6]).append(",'").append(cols[7]);
					sql.append("','").append(cols[8]).append("','").append(cols[9]).append("','").append(cols[10]).append("','").append(cols[11]);
					sql.append("',").append(cols[12]).append(",").append(cols[13]).append(",").append(cols[14]).append(")");
						 
					driver.executeUpdate(sql.toString());
				}
				ultCdSincUsuario =Convert.toLong(cols[16]);
			}
	        driver.closeAll();
		} catch (XmlRpcException e) {
			throw new Exception("Erro Webserver:|"+e.getMessage());
		}catch(DriverException e){
			throw new Exception("Erro Litebase:|"+e.getMessage());
		}
		return ultCdSincUsuario;
	}
	
	public long getDownFichaHan(int cdAgente,String tpOperacao) throws Exception{
		String[] cols=null;
		long ultCdSincUsuario=0;
		try{
			SOAP s = new SOAP("getFichaHan",urlWebService);
			s.openTimeout=vTimeOut;
		    s.readTimeout=vTimeOut;
	       
	        s.setParam(cdAgente,"cdAgente");
	        s.setParam(tpOperacao,"tpOperacao");        
	        s.execute();      
	       
	        driver = LitebaseConnection.getInstance(CREATORID);  
	        
			
			StringBuffer sql = new StringBuffer(1024);
		    Object objRetorno = s.getAnswer();
	        if(objRetorno!=null){
	        	String [] retorno=null;
		        if(objRetorno instanceof String[]){
		        	retorno =(String[]) objRetorno;
		        }else{
		        	retorno =  new String[1];
		        	retorno[0] =(String) objRetorno;
		        }	
		        String status=null;
		        if("A".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_DELETEALL;
		        }else if("D".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_DELETED;
		        }else if("U".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_UPDATED;
		        }else if("I".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_INSERTED;
		        }
		        driver.executeUpdate("delete from fichahantemp where STATUS="+status);
		        int numRegistros = retorno.length;
				int pointerRs=0;
				for(int i=0;i<retorno.length;i++){
					cols=Convert.tokenizeString(retorno[i],'|');
					sql.setLength(0);
					sql.append(" INSERT INTO fichahantemp(cd_segmento ,  cd_area,  cd_microarea,  nr_familia,   cd_paciente, cd_ano, ");
				    sql.append(" num_comunicantes,  ds_observacao,STATUS )");
					sql.append(" VALUES(").append(cols[0]).append(",").append(cols[1]).append(",").append(cols[2]).append(",").append(cols[3]);
					sql.append(",").append(cols[4]).append(",").append(cols[5]).append(",").append(cols[6]).append(",'").append(cols[7]);
					sql.append("',").append(cols[8]).append(")");				 
					driver.executeUpdate(sql.toString());
					pointerRs++;
					if( Math.ceil(((float)pointerRs/numRegistros)*100) % 5==0){
						ReplicationWin.progBarReplication.setValue(Convert.toInt(""+Math.round(Math.ceil(((float)pointerRs/numRegistros)*100))));				
					}
				}
				ultCdSincUsuario =Convert.toLong(cols[10]);
			}
	        driver.closeAll();
		} catch (XmlRpcException e) {
			throw new Exception("Erro Webserver:|"+e.getMessage());
		}catch(DriverException e){
			throw new Exception("Erro Litebase:|"+e.getMessage());
		}
		return ultCdSincUsuario;
	}
	
	public long getDownFichaHanAcomp(int cdAgente,String tpOperacao) throws Exception{
		String[] cols=null;
		long ultCdSincUsuario=0;
		try{
			SOAP s = new SOAP("getFichaHanAcomp",urlWebService);
			s.openTimeout=vTimeOut;
		    s.readTimeout=vTimeOut;
	        s.setParam(cdAgente,"cdAgente");
	        s.setParam(tpOperacao,"tpOperacao");        
	        s.execute();      
	       
	        driver = LitebaseConnection.getInstance(CREATORID);  
	        
			
			StringBuffer sql = new StringBuffer(1024);
		    Object objRetorno = s.getAnswer();
	        if(objRetorno!=null){
	        	String [] retorno=null;
		        if(objRetorno instanceof String[]){
		        	retorno =(String[]) objRetorno;
		        }else{
		        	retorno =  new String[1];
		        	retorno[0] =(String) objRetorno;
		        }
		        String status=null;
		        if("A".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_DELETEALL;
		        }else if("D".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_DELETED;
		        }else if("U".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_UPDATED;
		        }else if("I".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_INSERTED;
		        }
		        driver.executeUpdate("delete from fichahanacomptemp where STATUS="+status);
				for(int i=0;i<retorno.length;i++){
					cols=Convert.tokenizeString(retorno[i],'|');
					sql.setLength(0);
					sql.append(" INSERT INTO fichahanacomptemp(cd_segmento , cd_area, cd_microarea, nr_familia, cd_paciente, cd_ano, ");
				    sql.append(" cd_mes, dt_visita, fl_medicacao, dt_ultdose,  fl_autocuidados, dt_consulta , num_examinados, num_combcg,STATUS)");
					sql.append(" VALUES(").append(cols[0]).append(",").append(cols[1]).append(",").append(cols[2]).append(",").append(cols[3]);
					sql.append(",").append(cols[4]).append(",").append(cols[5]).append(",").append(cols[6]).append(",'").append(cols[7]);
					sql.append("','").append(cols[8]).append("','").append(cols[9]).append("','").append(cols[10]).append("','").append(cols[11]);
					sql.append("',").append(cols[12]).append(",").append(cols[13]).append(",").append(cols[14]).append(")");					 
					driver.executeUpdate(sql.toString());
				}	
				ultCdSincUsuario =Convert.toLong(cols[16]);
			}
	        driver.closeAll();
		} catch (XmlRpcException e) {
			throw new Exception("Erro Webserver:|"+e.getMessage());
		}catch(DriverException e){
			throw new Exception("Erro Litebase:|"+e.getMessage());
		}
		return ultCdSincUsuario;
	}
	
	public long getDownFichaHa(int cdAgente,String tpOperacao) throws Exception{
		String[] cols=null;
		long ultCdSincUsuario=0;
		try{
			SOAP s = new SOAP("getFichaHa",urlWebService);
			s.openTimeout=vTimeOut;
		    s.readTimeout=vTimeOut;
	        s.setParam(cdAgente,"cdAgente");
	        s.setParam(tpOperacao,"tpOperacao");        
	        s.execute();      
	       
	        driver = LitebaseConnection.getInstance(CREATORID);  
	        
			
			StringBuffer sql = new StringBuffer(1024);
			Object objRetorno = s.getAnswer();
	        if(objRetorno!=null){
	        	String [] retorno=null;
		        if(objRetorno instanceof String[]){
		        	retorno =(String[]) objRetorno;
		        }else{
		        	retorno =  new String[1];
		        	retorno[0] =(String) objRetorno;
		        }
		        String status=null;
		        if("A".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_DELETEALL;
		        }else if("D".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_DELETED;
		        }else if("U".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_UPDATED;
		        }else if("I".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_INSERTED;
		        }
		        driver.executeUpdate("delete from fichahatemp where STATUS="+status);
				for(int i=0;i<retorno.length;i++){
					cols=Convert.tokenizeString(retorno[i],'|');
					sql.setLength(0);
					sql.append(" INSERT INTO fichahatemp(cd_segmento ,  cd_area,  cd_microarea,  nr_familia,   cd_paciente, cd_ano, ");
				    sql.append(" fl_fumante, ds_observacao, STATUS )");
					sql.append(" VALUES(").append(cols[0]).append(",").append(cols[1]).append(",").append(cols[2]).append(",").append(cols[3]);
					sql.append(",").append(cols[4]).append(",").append(cols[5]).append(",'").append(cols[6]).append("','").append(cols[7]);
					sql.append("',").append(cols[8]).append(")");				 
					driver.executeUpdate(sql.toString());
				}		
				ultCdSincUsuario =Convert.toLong(cols[10]);
			}
	        driver.closeAll();
		} catch (XmlRpcException e) {
			throw new Exception("Erro Webserver:|"+e.getMessage());
		}catch(DriverException e){
			throw new Exception("Erro Litebase:|"+e.getMessage());
		}
		return ultCdSincUsuario;
	}
	
	public long getDownFichaHaAcomp(int cdAgente,String tpOperacao) throws Exception{
		String[] cols=null;
		long ultCdSincUsuario=0;
		try{
			SOAP s = new SOAP("getFichaHaAcomp",urlWebService);
			s.openTimeout=vTimeOut;
		    s.readTimeout=vTimeOut;
	        s.setParam(cdAgente,"cdAgente");
	        s.setParam(tpOperacao,"tpOperacao");        
	        s.execute();      
	       
	        driver = LitebaseConnection.getInstance(CREATORID);  
	        
			
			StringBuffer sql = new StringBuffer(1024);
			Object objRetorno = s.getAnswer();
	        if(objRetorno!=null){
	        	String [] retorno=null;
		        if(objRetorno instanceof String[]){
		        	retorno =(String[]) objRetorno;
		        }else{
		        	retorno =  new String[1];
		        	retorno[0] =(String) objRetorno;
		        }
		        String status=null;
		        if("A".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_DELETEALL;
		        }else if("D".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_DELETED;
		        }else if("U".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_UPDATED;
		        }else if("I".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_INSERTED;
		        }
		        driver.executeUpdate("delete from fichahaacomptemp where STATUS="+status);
				for(int i=0;i<retorno.length;i++){
					cols=Convert.tokenizeString(retorno[i],'|');
					sql.setLength(0);
					sql.append(" INSERT INTO fichahaacomptemp(cd_segmento , cd_area, cd_microarea, nr_familia, cd_paciente, cd_ano, ");
				    sql.append(" cd_mes, dt_visita, fl_dieta, fl_medicacao, fl_exercicio, ds_pressao, dt_ultconsulta, STATUS)");
					sql.append(" VALUES(").append(cols[0]).append(",").append(cols[1]).append(",").append(cols[2]).append(",").append(cols[3]);
					sql.append(",").append(cols[4]).append(",").append(cols[5]).append(",").append(cols[6]).append(",'").append(cols[7]);
					sql.append("','").append(cols[8]).append("','").append(cols[9]).append("','").append(cols[10]).append("','").append(cols[11]);
					sql.append("','").append(cols[12]).append("',").append(cols[13]).append(")");					 
					driver.executeUpdate(sql.toString());
				}	
				ultCdSincUsuario =Convert.toLong(cols[15]);
			}
	        driver.closeAll();
		} catch (XmlRpcException e) {
			throw new Exception("Erro Webserver:|"+e.getMessage());
		}catch(DriverException e){
			throw new Exception("Erro Litebase:|"+e.getMessage());
		}
		return ultCdSincUsuario;
	}
	
	public long getDownFichaDia(int cdAgente,String tpOperacao) throws Exception{
		String[] cols=null;
		long ultCdSincUsuario=0;
		try{
			SOAP s = new SOAP("getFichaDia",urlWebService);
			s.openTimeout=vTimeOut;
		    s.readTimeout=vTimeOut;
	        s.setParam(cdAgente,"cdAgente");
	        s.setParam(tpOperacao,"tpOperacao");        
	        s.execute();      
	       
	        driver = LitebaseConnection.getInstance(CREATORID);  
	        
			
			StringBuffer sql = new StringBuffer(1024);
			Object objRetorno = s.getAnswer();
	        if(objRetorno!=null){
	        	String [] retorno=null;
		        if(objRetorno instanceof String[]){
		        	retorno =(String[]) objRetorno;
		        }else{
		        	retorno =  new String[1];
		        	retorno[0] =(String) objRetorno;
		        }
		        String status=null;
		        if("A".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_DELETEALL;
		        }else if("D".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_DELETED;
		        }else if("U".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_UPDATED;
		        }else if("I".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_INSERTED;
		        }
		        driver.executeUpdate("delete from fichadiatemp where STATUS="+status);
				for(int i=0;i<retorno.length;i++){
					cols=Convert.tokenizeString(retorno[i],'|');
					sql.setLength(0);
					sql.append(" INSERT INTO fichadiatemp(cd_segmento ,  cd_area,  cd_microarea,  nr_familia,   cd_paciente, cd_ano, ");
				    sql.append(" ds_observacao, STATUS )");
					sql.append(" VALUES(").append(cols[0]).append(",").append(cols[1]).append(",").append(cols[2]).append(",").append(cols[3]);
					sql.append(",").append(cols[4]).append(",").append(cols[5]).append(",'").append(cols[6]).append("',").append(cols[7]);
					sql.append(")");				 
					driver.executeUpdate(sql.toString());
				}	
				ultCdSincUsuario =Convert.toLong(cols[9]);
			}
	        driver.closeAll();
		} catch (XmlRpcException e) {
			throw new Exception("Erro Webserver:|"+e.getMessage());
		}catch(DriverException e){
			throw new Exception("Erro Litebase:|"+e.getMessage());
		}
		return ultCdSincUsuario;
	}
	
	public long getDownFichaDiaAcomp(int cdAgente,String tpOperacao) throws Exception{
		String[] cols=null;
		long ultCdSincUsuario=0;
		try{
			SOAP s = new SOAP("getFichaDiaAcomp",urlWebService);
			s.openTimeout=vTimeOut;
		    s.readTimeout=vTimeOut;
	        s.setParam(cdAgente,"cdAgente");
	        s.setParam(tpOperacao,"tpOperacao");        
	        s.execute();      
	       
	        driver = LitebaseConnection.getInstance(CREATORID);  
	        
			
			StringBuffer sql = new StringBuffer(1024);
			Object objRetorno = s.getAnswer();
	        if(objRetorno!=null){
	        	String [] retorno=null;
		        if(objRetorno instanceof String[]){
		        	retorno =(String[]) objRetorno;
		        }else{
		        	retorno =  new String[1];
		        	retorno[0] =(String) objRetorno;
		        }
		        String status=null;
		        if("A".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_DELETEALL;
		        }else if("D".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_DELETED;
		        }else if("U".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_UPDATED;
		        }else if("I".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_INSERTED;
		        }
		        driver.executeUpdate("delete from fichadiaacomptemp where STATUS="+status);
				for(int i=0;i<retorno.length;i++){
					cols=Convert.tokenizeString(retorno[i],'|');
					sql.setLength(0);
					sql.append(" INSERT INTO fichadiaacomptemp(cd_segmento , cd_area, cd_microarea, nr_familia, cd_paciente, cd_ano, ");
				    sql.append(" cd_mes, dt_visita, fl_dieta, fl_exercicio, fl_insulina, fl_hipoglicemiante, dt_ultconsulta, STATUS)");
					sql.append(" VALUES(").append(cols[0]).append(",").append(cols[1]).append(",").append(cols[2]).append(",").append(cols[3]);
					sql.append(",").append(cols[4]).append(",").append(cols[5]).append(",").append(cols[6]).append(",'").append(cols[7]);
					sql.append("','").append(cols[8]).append("','").append(cols[9]).append("','").append(cols[10]).append("','").append(cols[11]);
					sql.append("','").append(cols[12]).append("',").append(cols[13]).append(")");					 
					driver.executeUpdate(sql.toString());
				}
				ultCdSincUsuario =Convert.toLong(cols[15]);
			}
	        driver.closeAll();
		} catch (XmlRpcException e) {
			throw new Exception("Erro Webserver:|"+e.getMessage());
		}catch(DriverException e){
			throw new Exception("Erro Litebase:|"+e.getMessage());
		}
		return ultCdSincUsuario;
	}
	
	public long getDownFichaGes(int cdAgente,String tpOperacao) throws Exception{
		String[] cols=null;
		long ultCdSincUsuario=0;
		try{
			SOAP s = new SOAP("getFichaGes",urlWebService);
			s.openTimeout=vTimeOut;
		    s.readTimeout=vTimeOut;
	        s.setParam(cdAgente,"cdAgente");
	        s.setParam(tpOperacao,"tpOperacao");        
	        s.execute();      
	       
	        driver = LitebaseConnection.getInstance(CREATORID);  
	        
			
			StringBuffer sql = new StringBuffer(1024);
			Object objRetorno = s.getAnswer();
	        if(objRetorno!=null){
	        	String [] retorno=null;
		        if(objRetorno instanceof String[]){
		        	retorno =(String[]) objRetorno;
		        }else{
		        	retorno =  new String[1];
		        	retorno[0] =(String) objRetorno;
		        }
		        String status=null;
		        if("A".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_DELETEALL;
		        }else if("D".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_DELETED;
		        }else if("U".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_UPDATED;
		        }else if("I".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_INSERTED;
		        }
		        driver.executeUpdate("delete from fichagestemp where STATUS="+status);
				for(int i=0;i<retorno.length;i++){
					cols=Convert.tokenizeString(retorno[i],'|');
					sql.setLength(0);
					sql.append(" INSERT INTO fichagestemp(cd_segmento ,  cd_area,  cd_microarea,  nr_familia,   cd_paciente, cd_ano, ");
				    sql.append(" dt_ultregra, dt_parto, dt_vacina1, dt_vacina2, dt_vacina3, dt_vacinar, fl_seisgesta, fl_natimorto, ");
				    sql.append(" fl_36anosmais, fl_menos20, fl_sangramento, fl_edema, fl_diabetes, fl_pressaoalta, dt_nascvivo, dt_nascmorto, ");
				    sql.append(" dt_aborto, dt_puerperio1, dt_puerperio2, ds_observacao, STATUS )");
					sql.append(" VALUES(").append(cols[0]).append(",").append(cols[1]).append(",").append(cols[2]).append(",").append(cols[3]);
					sql.append(",").append(cols[4]).append(",").append(cols[5]).append(",'").append(cols[6]).append("','").append(cols[7]);
					sql.append("','").append(cols[8]).append("','").append(cols[9]).append("','").append(cols[10]).append("','").append(cols[11]);
					sql.append("','").append(cols[12]).append("','").append(cols[13]).append("','").append(cols[14]).append("','").append(cols[15]);
					sql.append("','").append(cols[16]).append("','").append(cols[17]).append("','").append(cols[18]).append("','").append(cols[19]);
					sql.append("','").append(cols[20]).append("','").append(cols[21]).append("','").append(cols[22]).append("','").append(cols[23]);
					sql.append("','").append(cols[24]).append("','").append(cols[25]).append("',").append(cols[26]).append(")");				 
					driver.executeUpdate(sql.toString());
				}	
				ultCdSincUsuario =Convert.toLong(cols[28]);
			}
	        driver.closeAll();
		} catch (XmlRpcException e) {
			throw new Exception("Erro Webserver:|"+e.getMessage());
		}catch(DriverException e){
			throw new Exception("Erro Litebase:|"+e.getMessage());
		}
		return ultCdSincUsuario;
	}
	
	public long getDownFichaGesAcomp(int cdAgente,String tpOperacao) throws Exception{
		String[] cols=null;
		long ultCdSincUsuario=0;
		try{
			SOAP s = new SOAP("getFichaGesAcomp",urlWebService);
			s.openTimeout=vTimeOut;
		    s.readTimeout=vTimeOut;
	        s.setParam(cdAgente,"cdAgente");
	        s.setParam(tpOperacao,"tpOperacao");        
	        s.execute();      
	       
	        driver = LitebaseConnection.getInstance(CREATORID);  
	        
			
			StringBuffer sql = new StringBuffer(1024);
			Object objRetorno = s.getAnswer();
	        if(objRetorno!=null){
	        	String [] retorno=null;
		        if(objRetorno instanceof String[]){
		        	retorno =(String[]) objRetorno;
		        }else{
		        	retorno =  new String[1];
		        	retorno[0] =(String) objRetorno;
		        }	
		        String status=null;
		        if("A".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_DELETEALL;
		        }else if("D".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_DELETED;
		        }else if("U".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_UPDATED;
		        }else if("I".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_INSERTED;
		        }
		        driver.executeUpdate("delete from fichagesacomptemp where STATUS="+status);
				for(int i=0;i<retorno.length;i++){
					cols=Convert.tokenizeString(retorno[i],'|');
					sql.setLength(0);
					sql.append(" INSERT INTO fichagesacomptemp(cd_segmento , cd_area, cd_microarea, nr_familia, cd_paciente, cd_ano, ");
				    sql.append(" cd_mes, fl_estadonutri, dt_consprenatal, dt_visita,  STATUS )");
					sql.append(" VALUES(").append(cols[0]).append(",").append(cols[1]).append(",").append(cols[2]).append(",").append(cols[3]);
					sql.append(",").append(cols[4]).append(",").append(cols[5]).append(",").append(cols[6]).append(",'").append(cols[7]);
					sql.append("','").append(cols[8]).append("','").append(cols[9]).append("',").append(cols[10]).append(")");							 
					driver.executeUpdate(sql.toString());
				}
				ultCdSincUsuario =Convert.toLong(cols[12]);
			}
	        driver.closeAll();
		} catch (XmlRpcException e) {
			throw new Exception("Erro Webserver:|"+e.getMessage());
		}catch(DriverException e){
			throw new Exception("Erro Litebase:|"+e.getMessage());
		}
		return ultCdSincUsuario;
	}
	
	public long getDownFichaCri(int cdAgente,String tpOperacao) throws Exception{
		String[] cols=null;
		long ultCdSincUsuario=0;
		try{
			SOAP s = new SOAP("getFichaCri",urlWebService);
			s.openTimeout=vTimeOut;
		    s.readTimeout=vTimeOut;
	        s.setParam(cdAgente,"cdAgente");
	        s.setParam(tpOperacao,"tpOperacao");        
	        s.execute();      
	       
	        driver = LitebaseConnection.getInstance(CREATORID);  
	        
			
			StringBuffer sql = new StringBuffer(1024);
			Object objRetorno = s.getAnswer();
	        if(objRetorno!=null){
	        	String [] retorno=null;
		        if(objRetorno instanceof String[]){
		        	retorno =(String[]) objRetorno;
		        }else{
		        	retorno =  new String[1];
		        	retorno[0] =(String) objRetorno;
		        }
		        String status=null;
		        if("A".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_DELETEALL;
		        }else if("D".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_DELETED;
		        }else if("U".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_UPDATED;
		        }else if("I".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_INSERTED;
		        }
		        driver.executeUpdate("delete from fichacriancatemp where STATUS="+status);
				for(int i=0;i<retorno.length;i++){
					cols=Convert.tokenizeString(retorno[i],'|');
					sql.setLength(0);
					sql.append(" INSERT INTO fichacriancatemp(cd_segmento ,  cd_area,  cd_microarea,  nr_familia,   cd_paciente, cd_ano, ");
				    sql.append(" CD_AVALIACAOALIMEN, DS_TIPOALIMENTACAO, FL_IMUNIZADO, FL_COMPLETARESQ, FL_DIARREIA, FL_USARAMTRO, ");
				    sql.append(" FL_INTERNAMENTOS, FL_IRA, FL_CAXUMBA, FL_CATAPORA, FL_BAIXOPESONASC, FL_DEFICIENCIAS, STATUS )");
					sql.append(" VALUES(").append(cols[0]).append(",").append(cols[1]).append(",").append(cols[2]).append(",").append(cols[3]);
					sql.append(",").append(cols[4]).append(",").append(cols[5]).append(",").append(cols[6]).append(",'").append(cols[7]);
					sql.append("','").append(cols[8]).append("','").append(cols[9]).append("','").append(cols[10]).append("','").append(cols[11]);
					sql.append("','").append(cols[12]).append("','").append(cols[13]).append("','").append(cols[14]).append("','").append(cols[15]);
					sql.append("','").append(cols[16]).append("','").append(cols[17]).append("',").append(cols[18]).append(")");				 
					driver.executeUpdate(sql.toString());
				}	
				ultCdSincUsuario =Convert.toLong(cols[20]);
			}
	        driver.closeAll();
		} catch (XmlRpcException e) {
			throw new Exception("Erro Webserver:|"+e.getMessage());
		}catch(DriverException e){
			throw new Exception("Erro Litebase:|"+e.getMessage());
		}
		return ultCdSincUsuario;
	}
	
	public long getDownFichaCriAcomp(int cdAgente,String tpOperacao) throws Exception{
		String[] cols=null;
		long ultCdSincUsuario=0;
		try{
			SOAP s = new SOAP("getFichaCriAcomp",urlWebService);
			s.openTimeout=vTimeOut;
		    s.readTimeout=vTimeOut;
	        s.setParam(cdAgente,"cdAgente");
	        s.setParam(tpOperacao,"tpOperacao");        
	        s.execute();      
	       
	        driver = LitebaseConnection.getInstance(CREATORID);  
	        
			
			StringBuffer sql = new StringBuffer(1024);
			Object objRetorno = s.getAnswer();
	        if(objRetorno!=null){
	        	String [] retorno=null;
		        if(objRetorno instanceof String[]){
		        	retorno =(String[]) objRetorno;
		        }else{
		        	retorno =  new String[1];
		        	retorno[0] =(String) objRetorno;
		        }
		        String status=null;
		        if("A".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_DELETEALL;
		        }else if("D".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_DELETED;
		        }else if("U".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_UPDATED;
		        }else if("I".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_INSERTED;
		        }
		        driver.executeUpdate("delete from fichacriancaacomptemp where STATUS="+status);
				for(int i=0;i<retorno.length;i++){
					cols=Convert.tokenizeString(retorno[i],'|');
					sql.setLength(0);
					sql.append(" INSERT INTO fichacriancaacomptemp(cd_segmento , cd_area, cd_microarea, nr_familia, cd_paciente, cd_ano, ");
				    sql.append(" cd_mes, dt_quadronutri, num_peso, num_altura,  STATUS )");
					sql.append(" VALUES(").append(cols[0]).append(",").append(cols[1]).append(",").append(cols[2]).append(",").append(cols[3]);
					sql.append(",").append(cols[4]).append(",").append(cols[5]).append(",").append(cols[6]).append(",'").append(cols[7]);
					sql.append("',").append(cols[8]).append(",").append(cols[9]).append(",").append(cols[10]).append(")");							 
					driver.executeUpdate(sql.toString());
				}
				ultCdSincUsuario =Convert.toLong(cols[12]);
			}
	        driver.closeAll();
		} catch (XmlRpcException e) {
			throw new Exception("Erro Webserver:|"+e.getMessage());
		}catch(DriverException e){
			throw new Exception("Erro Litebase:|"+e.getMessage());
		}
		return ultCdSincUsuario;
	}
	
	public long getDownFichaIdoso(int cdAgente,String tpOperacao) throws Exception{
		String[] cols=null;
		long ultCdSincUsuario=0;
		try{
			SOAP s = new SOAP("getFichaIdoso",urlWebService);
			s.openTimeout=vTimeOut;
		    s.readTimeout=vTimeOut;
	        s.setParam(cdAgente,"cdAgente");
	        s.setParam(tpOperacao,"tpOperacao");        
	        s.execute();      
	       
	        driver = LitebaseConnection.getInstance(CREATORID);  
	        
			
			StringBuffer sql = new StringBuffer(1024);
			Object objRetorno = s.getAnswer();
	        if(objRetorno!=null){
	        	String [] retorno=null;
		        if(objRetorno instanceof String[]){
		        	retorno =(String[]) objRetorno;
		        }else{
		        	retorno =  new String[1];
		        	retorno[0] =(String) objRetorno;
		        }
		        String status=null;
		        if("A".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_DELETEALL;
		        }else if("D".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_DELETED;
		        }else if("U".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_UPDATED;
		        }else if("I".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_INSERTED;
		        }
		        driver.executeUpdate("delete from fichaidosotemp where STATUS="+status);
				for(int i=0;i<retorno.length;i++){
					cols=Convert.tokenizeString(retorno[i],'|');
					sql.setLength(0);
					sql.append(" INSERT INTO fichaidosotemp(cd_segmento ,  cd_area,  cd_microarea,  nr_familia,   cd_paciente, cd_ano, ");
				    sql.append(" fl_fumante, dt_dtp, dt_influenza, dt_pneumonococos, fl_hipertensao, fl_tuberculose, fl_alzheimer, fl_deffisica, ");
				    sql.append(" fl_hanseniase, fl_malparkson, fl_cancer, fl_acamado, fl_internamentos, fl_sequelasavc, fl_alcolatra, STATUS )");
					sql.append(" VALUES(").append(cols[0]).append(",").append(cols[1]).append(",").append(cols[2]).append(",").append(cols[3]);
					sql.append(",").append(cols[4]).append(",").append(cols[5]).append(",'").append(cols[6]).append("','").append(cols[7]);
					sql.append("','").append(cols[8]).append("','").append(cols[9]).append("','").append(cols[10]).append("','").append(cols[11]);
					sql.append("','").append(cols[12]).append("','").append(cols[13]).append("','").append(cols[14]).append("','").append(cols[15]);
					sql.append("','").append(cols[16]).append("','").append(cols[17]).append("','").append(cols[18]).append("','").append(cols[19]);
					sql.append("','").append(cols[20]).append("',").append(cols[21]).append(")");
					driver.executeUpdate(sql.toString());
				}
				ultCdSincUsuario =Convert.toLong(cols[23]);
			}
	        driver.closeAll();
		} catch (XmlRpcException e) {
			throw new Exception("Erro Webserver:|"+e.getMessage());
		}catch(DriverException e){
			throw new Exception("Erro Litebase:|"+e.getMessage());
		}
		return ultCdSincUsuario;
	}
	
	public long getDownFichaIdosoAcomp(int cdAgente,String tpOperacao) throws Exception{
		String[] cols=null;
		long ultCdSincUsuario=0;
		try{
			SOAP s = new SOAP("getFichaIdosoAcomp",urlWebService);
			s.openTimeout=vTimeOut;
		    s.readTimeout=vTimeOut;
	        s.setParam(cdAgente,"cdAgente");
	        s.setParam(tpOperacao,"tpOperacao");        
	        s.execute();      
	       
	        driver = LitebaseConnection.getInstance(CREATORID);  
	        
			
			StringBuffer sql = new StringBuffer(1024);
			Object objRetorno = s.getAnswer();
	        if(objRetorno!=null){
	        	String [] retorno=null;
		        if(objRetorno instanceof String[]){
		        	retorno =(String[]) objRetorno;
		        }else{
		        	retorno =  new String[1];
		        	retorno[0] =(String) objRetorno;
		        }
		        String status=null;
		        if("A".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_DELETEALL;
		        }else if("D".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_DELETED;
		        }else if("U".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_UPDATED;
		        }else if("I".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_INSERTED;
		        }
		        driver.executeUpdate("delete from fichaidosoacomptemp where STATUS="+status);
				for(int i=0;i<retorno.length;i++){
					cols=Convert.tokenizeString(retorno[i],'|');
					sql.setLength(0);
					sql.append(" INSERT INTO fichaidosoacomptemp(cd_segmento , cd_area, cd_microarea, nr_familia, cd_paciente, cd_ano, ");
				    sql.append(" cd_mes, fl_estadonutri, dt_visita, dt_acomppsf,  STATUS )");
					sql.append(" VALUES(").append(cols[0]).append(",").append(cols[1]).append(",").append(cols[2]).append(",").append(cols[3]);
					sql.append(",").append(cols[4]).append(",").append(cols[5]).append(",").append(cols[6]).append(",'").append(cols[7]);
					sql.append("','").append(cols[8]).append("','").append(cols[9]).append("',").append(cols[10]).append(")");							 
					driver.executeUpdate(sql.toString());
				}	
				ultCdSincUsuario =Convert.toLong(cols[12]);
			}
	        driver.closeAll();
		} catch (XmlRpcException e) {
			throw new Exception("Erro Webserver:|"+e.getMessage());
		}catch(DriverException e){
			throw new Exception("Erro Litebase:|"+e.getMessage());
		}
		return ultCdSincUsuario;
	}
	public long getDownAgente(int cdAgente,String tpOperacao) throws Exception{
		String[] cols=null;
		long ultCdSincUsuario=0;
		try{
			SOAP s = new SOAP("getAgente",urlWebService);
			s.openTimeout=vTimeOut;
		    s.readTimeout=vTimeOut;
	        s.setParam(cdAgente,"cdAgente");
	        s.setParam(tpOperacao,"tpOperacao");        
	        s.execute();      
	       
	        driver = LitebaseConnection.getInstance(CREATORID);  
	        
			
			StringBuffer sql = new StringBuffer(1024);
			Object objRetorno = s.getAnswer();
	        if(objRetorno!=null){
	        	String [] retorno=null;
		        if(objRetorno instanceof String[]){
		        	retorno =(String[]) objRetorno;
		        }else{
		        	retorno =  new String[1];
		        	retorno[0] =(String) objRetorno;
		        }
		        String status=null;
		        if("A".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_DELETEALL;
		        }else if("D".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_DELETED;
		        }else if("U".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_UPDATED;
		        }else if("I".equals(tpOperacao)){
		        	 status=""+BBPDBTemp.SSK_RECORD_INSERTED;
		        }
		        driver.executeUpdate("delete from agentetemp where STATUS="+status);
				for(int i=0;i<retorno.length;i++){
					cols=Convert.tokenizeString(retorno[i],'|');
					sql.setLength(0);
					sql.append(" INSERT INTO agentetemp(cd_agente , nm_agente,ds_login,ds_senha, cd_seg, cd_area, cd_microa,cd_ano,status )");
					sql.append(" VALUES(").append(cols[0]).append(",'").append(cols[1]).append("','").append(cols[2]).append("','").append(cols[3]);
					sql.append("','").append(cols[4]).append("','").append(cols[5]).append("','").append(cols[6]).append("',").append(cols[7]);
					sql.append(",").append(cols[8]).append(")");							 
					driver.executeUpdate(sql.toString());
				}	
				ultCdSincUsuario =Convert.toLong(cols[10]);
			}
	        driver.closeAll();
		} catch (XmlRpcException e) {
			throw new Exception("Erro Webserver:|"+e.getMessage());
		}catch(DriverException e){
			throw new Exception("Erro Litebase:|"+e.getMessage());
		}
		return ultCdSincUsuario;
	}
	
	public String[] getDownValidateAgente(int cdAgente) throws Exception{	
		 String[] objRetorno=null;
		try{
			SOAP s = new SOAP("validateAgente",urlWebService);
			
			s.openTimeout=vTimeOut;
		    s.readTimeout=vTimeOut;
	        s.setParam(cdAgente,"cdAgente");	             
	        s.execute();
	        objRetorno =(String[]) s.getAnswer();
		} catch (XmlRpcException e) {
			throw new Exception("Erro Webserver:|"+e.getMessage());
		}catch(DriverException e){
			throw new Exception("Erro Litebase:|"+e.getMessage());
		}
		return objRetorno;
	}
}
