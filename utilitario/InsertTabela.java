import litebase.LitebaseConnection;
import litebase.ResultSet;
import waba.sys.Settings;
import waba.sys.Vm;

public class InsertTabela {

		
	public static void main(String[] args) {
		
		    	waba.applet.JavaBridge.setNonGUIApp( );
		    	Settings.dataPath = "F:\\SIAB_MOBILE3\\SIAB\\data";
		    	 //Settings.dataPath = "F:\\SIAB\\dataTeste";
		
		String CREATORID = "SIAB";
		LitebaseConnection driver = LitebaseConnection.getInstance(CREATORID);	
		
		driver.executeUpdate("INSERT INTO OCUPACAO (CD_OCUPACAO,DS_OCUPACAO) VALUES(1,'Analista')");
		
		driver.executeUpdate("DELETE FROM AGENTE");
		driver.executeUpdate("INSERT INTO AGENTE VALUES(1,'MANOEL BIBIANO','1','1','01','001','01')");
		
		
		int sequencial=0;		
		ResultSet rs = driver.executeQuery(" SELECT SEQUENCIAL  FROM VALIDATIONTEMP");
		if(rs.next()){
			sequencial	=rs.getInt("SEQUENCIAL")+1;
		}else sequencial= 1;	
		driver.executeUpdate("DELETE FROM VALIDATIONTEMP");
		driver.executeUpdate("INSERT INTO VALIDATIONTEMP VALUES("+sequencial+")");
		//driver.executeUpdate("INSERT INTO VALIDATIONTEMP VALUES(8)");
		
		
		
		driver.executeUpdate("DELETE FROM FICHACRIANCATEMP");
		
	/*	driver.executeUpdate(" INSERT INTO FICHACRIANCATEMP (CD_FAMILIA,CD_PACIENTE, CD_AVALIACAOALIMEN,DS_TIPOALIMENTACAO," +
						     " FL_IMUNIZADO,FL_COMPLETARESQ,FL_DIARREIA,FL_USARAMTRO, FL_INTERNAMENTOS,FL_IRA,FL_CAXUMBA, FL_CATAPORA,"+
						     " FL_BAIXOPESONASC,FL_DEFICIENCIAS,STATUS)" +
				   		     " VALUES(1,1,1,'teste'," +
				   		     " 'S','S','S','S','S','S','S','S'," +
				   		     " 'S','S',3)");
		
	    driver.executeUpdate(" INSERT INTO FICHACRIANCATEMP (CD_FAMILIA,CD_PACIENTE, CD_AVALIACAOALIMEN,DS_TIPOALIMENTACAO," +
						     " FL_IMUNIZADO,FL_COMPLETARESQ,FL_DIARREIA,FL_USARAMTRO, FL_INTERNAMENTOS,FL_IRA,FL_CAXUMBA, FL_CATAPORA,"+
						     " FL_BAIXOPESONASC,FL_DEFICIENCIAS,STATUS)" +
			    		     " VALUES(1,1,1,'teste'," +
			    		     " 'S','S','S','S','S','S','S','S'," +
			    		     " 'S','S',1)");
	    
	    driver.executeUpdate(" INSERT INTO FICHACRIANCATEMP (CD_FAMILIA,CD_PACIENTE, CD_AVALIACAOALIMEN,DS_TIPOALIMENTACAO," +
						     " FL_IMUNIZADO,FL_COMPLETARESQ,FL_DIARREIA,FL_USARAMTRO, FL_INTERNAMENTOS,FL_IRA,FL_CAXUMBA, FL_CATAPORA,"+
						     " FL_BAIXOPESONASC,FL_DEFICIENCIAS,STATUS)" +
				   		     " VALUES(1,1,1,'testeaaaaaaaaaa'," +
				   		     " 'S','S','S','S','S','S','S','S'," +
				   		     " 'S','S',2)");*/
		driver.executeUpdate("DELETE FROM FICHACADFAM");
	    driver.executeUpdate("DELETE FROM FICHACADFAMTEMP");
	   /* driver.executeUpdate(" INSERT INTO FICHACADFAMTEMP( CD_FAMILIA, DS_ENDERECO, NUM_ENDERECO, DS_BAIRRO, DS_CEP, CD_MUNICIPIO, CD_SEGMENTO, CD_AREA, CD_MICROAREA," +
							 " DT_CADASTRO, CD_UF, CD_TPCASA,DS_TPCASAESP ,NUM_COMODOS,FL_ENERGIA ,CD_DESTLIXO,CD_TRATAAGUA,CD_ABASTEAGUA, CD_FEZESURINA,"+
							 "CD_DOENCAPROCU,DS_DOENCAPROCUESP, CD_GRUPOCOMU,DS_GRUPOCOMUESP, CD_MEIOSCOMUNI, DS_MEIOSCOMUNIESP,CD_MEIOSTRANS,DS_MEIOSTRANSESP,"+
							 "FL_POSSUIPLAN,NUM_PESPLAN,NM_PLANO ,DS_OBSERVACAO, STATUS)"+
			                 " VALUES(1,'Rua Fulano de Tal',200,'Praia de Iracema','"+
			                 "11111',225255,222222,363333,"+
			                 "4444444,'01/06/2006','CE',1,'"+
			                 "asasass',1,'S',1," +
			                 "1,1,1,1,'"+
			                 "aaaa',1,'ssss',"+
			                 "1,'gggg',1,'"+
			                 "1','S',1,'UNIMED','OBS TESTE',1)");
	    
	    driver.executeUpdate(" INSERT INTO FICHACADFAMTEMP( CD_FAMILIA, DS_ENDERECO, NUM_ENDERECO, DS_BAIRRO, DS_CEP, CD_MUNICIPIO, CD_SEGMENTO, CD_AREA, CD_MICROAREA," +
				 " DT_CADASTRO, CD_UF, CD_TPCASA,DS_TPCASAESP ,NUM_COMODOS,FL_ENERGIA ,CD_DESTLIXO,CD_TRATAAGUA,CD_ABASTEAGUA, CD_FEZESURINA,"+
				 "CD_DOENCAPROCU,DS_DOENCAPROCUESP, CD_GRUPOCOMU,DS_GRUPOCOMUESP, CD_MEIOSCOMUNI, DS_MEIOSCOMUNIESP,CD_MEIOSTRANS,DS_MEIOSTRANSESP,"+
				 "FL_POSSUIPLAN,NUM_PESPLAN,NM_PLANO ,DS_OBSERVACAO, STATUS)"+
                " VALUES(2,'Rua Fulano de Tal2',200,'Praia de Iracema','"+
                "11111',225255,222222,363333,"+
                "4444444,'01/06/2006','CE',1,'"+
                "asasass',1,'S',1," +
                "1,1,1,1,'"+
                "aaaa',1,'ssss',"+
                "1,'gggg',1,'"+
                "1','S',1,'UNIMED','OBS TESTE',1)");*/
	    
	    for(int i=1;i<100;i++){
		    driver.executeUpdate(" INSERT INTO FICHACADFAMTEMP( CD_FAMILIA, DS_ENDERECO, NUM_ENDERECO, DS_BAIRRO, DS_CEP, CD_MUNICIPIO, CD_SEGMENTO, CD_AREA, CD_MICROAREA," +
								 " DT_CADASTRO, CD_UF, CD_TPCASA,DS_TPCASAESP ,NUM_COMODOS,FL_ENERGIA ,CD_DESTLIXO,CD_TRATAAGUA,CD_ABASTEAGUA, CD_FEZESURINA,"+
								 " CD_DOENCAPROCU,DS_DOENCAPROCUESP, CD_GRUPOCOMU,DS_GRUPOCOMUESP, CD_MEIOSCOMUNI, DS_MEIOSCOMUNIESP,CD_MEIOSTRANS,DS_MEIOSTRANSESP,"+
								 " FL_POSSUIPLAN,NUM_PESPLAN,NM_PLANO ,DS_OBSERVACAO, STATUS)"+
				                 " VALUES("+i+",'Rua Fulano de Tal',200,'Praia de Iracema','"+
				                 " 11111',225255,222222,363333,"+
				                 " 4444444,'01/06/2006','CE',1,'"+
				                 " asasass',1,'S',1," +
				                 " 1,1,1,1,'"+
				                 " aaaa',1,'ssss',"+
				                 " 1,'gggg',1,'"+
				                 " 1','S',1,'UNIMED','OBS TESTE',1)");
	    }
	    
	    /*for(int i=1;i<90;i++){
		    driver.executeUpdate(" INSERT INTO FICHACADFAMTEMP( CD_FAMILIA, DS_ENDERECO, NUM_ENDERECO, DS_BAIRRO, DS_CEP, CD_MUNICIPIO, CD_SEGMENTO, CD_AREA, CD_MICROAREA," +
								 " DT_CADASTRO, CD_UF, CD_TPCASA,DS_TPCASAESP ,NUM_COMODOS,FL_ENERGIA ,CD_DESTLIXO,CD_TRATAAGUA,CD_ABASTEAGUA, CD_FEZESURINA,"+
								 " CD_DOENCAPROCU,DS_DOENCAPROCUESP, CD_GRUPOCOMU,DS_GRUPOCOMUESP, CD_MEIOSCOMUNI, DS_MEIOSCOMUNIESP,CD_MEIOSTRANS,DS_MEIOSTRANSESP,"+
								 " FL_POSSUIPLAN,NUM_PESPLAN,NM_PLANO ,DS_OBSERVACAO, STATUS)"+
				                 " VALUES("+i+",'1Rua Fulano de Tal"+i+"',200,'Praia de Iracema','"+
				                 " 11111',225255,222222,363333,"+
				                 " 4444444,'01/06/2006','CE',1,'"+
				                 " asasass',1,'S',1," +
				                 " 1,1,1,1,'"+
				                 " aaaa',1,'ssss',"+
				                 " 1,'gggg',1,'"+
				                 " 1','S',1,'UNIMED','OBS TESTE',2)");
	    }*/
	    
	    driver.executeUpdate("DELETE FROM FICHAHATEMP");
	    for(int i=1;i<2;i++){
		    driver.executeUpdate(" INSERT INTO FICHAHATEMP( CD_FAMILIA, CD_PACIENTE, FL_FUMANTE, DS_OBSERVACAO, STATUS)"+
				                 " VALUES("+i+",1,'S','Praia de Iracema',1)");
	    }
		Vm.debug("Pdbs Gerado com Sucesso!");
	}

}
