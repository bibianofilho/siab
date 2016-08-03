import litebase.LitebaseConnection;

import waba.sys.Settings;
import waba.sys.Vm;

public class CreateDescriber {

		
	public static void main(String[] args) {
		
		    	waba.applet.JavaBridge.setNonGUIApp( );
		    	 //Settings.dataPath = "C:\\bibiano\\workspace\\SIAB\\datateste";
		    	 Settings.dataPath = "F:\\SIAB_MOBILE3\\SIAB\\datateste";
		
		String CREATORID = "SIAB";
		LitebaseConnection driver = LitebaseConnection.getInstance(CREATORID);
		String sql="";
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
							   "FL_TBC"};
		keyCol= new String[]{"S","S","S","S","S","S","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N"};
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
	}
	
	
	
	
	
	
	
	
	
	

}
