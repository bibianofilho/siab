import litebase.LitebaseConnection;
import waba.sys.Settings;
import waba.sys.Vm;

public class GeraTabela {

		
	public static void main(String[] args) {
		
		    	waba.applet.JavaBridge.setNonGUIApp( );
		    	 //Settings.dataPath = "C:\\bibiano\\workspace\\SIAB\\createpdb";
		    	 Settings.dataPath = "F:\\SIAB_MOBILE3\\SIAB\\createpdb";
		
		String CREATORID = "SIAB";
		LitebaseConnection driver = LitebaseConnection.getInstance(CREATORID);
		driver.execute("CREATE TABLE FICHATBACOMP (CD_FAMILIA long,CD_PACIENTE long, CD_MES int,CD_ANO int,DT_VISITA char(20)," +
		"FL_MEDICACAO char(2), FL_REAINDESE char(2), DT_CONSULTA char(2), FL_ESCARRO char(2), NUM_EXAMINADOS int, NUM_COMBCG int,STATUS int)");
/*		driver.executeUpdate(" INSERT INTO FICHATBACOMP (CD_PACIENTE, CD_MES,DT_VISITA,FL_MEDICACAO, FL_REAINDESE, DT_CONSULTA, FL_ESCARRO, NUM_EXAMINADOS, NUM_COMBCG)" +
		"VALUES(1000,1,'12','S','X','18/01','N',2,2)");*/		
		driver.execute("CREATE TABLE FICHATB (CD_FAMILIA long,CD_PACIENTE long,CD_ANO int, NUM_COMUNICANTES int, NUM_COMUNICANTES5 int, DS_OBSERVACAO char(100),STATUS int)");
/*		driver.executeUpdate(" INSERT INTO FICHATB (CD_PACIENTE,NM_PACIENTE,DS_ENDERECO,CD_SEXO,NUM_IDADE,NUM_COMUNICANTES,NUM_COMUNICANTES5, DS_OBSERVACAO)" +
	    "             VALUES(1000,'ANA MARIA','RUI BARBOSA', 'F',25,02,0,'Teste Observação')");
*/
		driver.execute("CREATE TABLE FICHACADFAM (CD_FAMILIA long,CD_ANO int, DS_ENDERECO char(60), NUM_ENDERECO int," +
		" DS_BAIRRO char(25), DS_CEP char(15), CD_MUNICIPIO  int, CD_SEGMENTO int, CD_AREA int, CD_MICROAREA int, DT_CADASTRO char(12), " +
		" CD_UF char(2), CD_TPCASA int, DS_TPCASAESP char(30), NUM_COMODOS int , FL_ENERGIA char(1), CD_DESTLIXO int, CD_TRATAAGUA int," +
		" CD_ABASTEAGUA int, CD_FEZESURINA int,  CD_DOENCAPROCU int, DS_DOENCAPROCUESP char(20),CD_GRUPOCOMU int, DS_GRUPOCOMUESP char(20)," +
		" CD_MEIOSCOMUNI int, DS_MEIOSCOMUNIESP char(20), CD_MEIOSTRANS int, DS_MEIOSTRANSESP char(20), FL_POSSUIPLAN char(1), NUM_PESPLAN int," +
		" NM_PLANO char(20), DS_OBSERVACAO char(100),STATUS int  )");		
		driver.execute("CREATE TABLE PACFAMILIA (CD_FAMILIA long, CD_PACIENTE long,CD_ANO int, NM_PACIENTE CHAR(30)," +
				" DT_NASCIMENTO char(10), NUM_IDADE int, FL_SEXO  char(1), FL_MENORQUINZE char(1), FL_ALFABETIZADO char(1),"+
				" FL_FREQESCOLA char(1), CD_OCUPACAO int,FL_ALC char(1),FL_CHA char(1),FL_DEF char(1)," +
				" FL_DIA char(1), FL_DME char(1), FL_EPI char(1), FL_GES char(1), FL_HAN char(1), FL_HA char(1), FL_MAL char(1),"+
				" FL_TBC char(1),STATUS int )");
		
		driver.execute("CREATE TABLE FICHAHAN (CD_FAMILIA long,CD_PACIENTE long,CD_ANO int, NUM_COMUNICANTES int, DS_OBSERVACAO char(100),STATUS int)");		
		driver.execute("CREATE TABLE FICHAHANACOMP (CD_FAMILIA long,CD_PACIENTE long, CD_MES int,CD_ANO int,DT_VISITA char(20)," +
		"FL_MEDICACAO char(2), DT_ULTDOSE char(10),FL_AUTOCUIDADOS char(1), DT_CONSULTA char(10), NUM_EXAMINADOS int, NUM_COMBCG int,STATUS int)");
		
		driver.execute("CREATE TABLE FICHAHA (CD_FAMILIA long,CD_PACIENTE long ,CD_ANO int, FL_FUMANTE char(1), DS_OBSERVACAO char(100),STATUS int)");		
		driver.execute("CREATE TABLE FICHAHAACOMP (CD_FAMILIA long,CD_PACIENTE long, CD_MES int,CD_ANO int, DT_VISITA char(20)," +
		"FL_DIETA char(1), FL_MEDICACAO char(1),FL_EXERCICIO char(1), DS_PRESSAO char(10), DT_ULTCONSULTA char(10),STATUS int)");
		
		driver.execute("CREATE TABLE FICHADIA (CD_FAMILIA long,CD_PACIENTE long,CD_ANO int, DS_OBSERVACAO char(100),STATUS int)");		
		driver.execute("CREATE TABLE FICHADIAACOMP (CD_FAMILIA long,CD_PACIENTE long, CD_MES int, CD_ANO int,DT_VISITA char(20)," +
		"FL_DIETA char(1),FL_EXERCICIO char(1), FL_INSULINA char(1),FL_HIPOGLICEMIANTE char(1), DT_ULTCONSULTA char(10),STATUS int)");
		
		driver.execute("CREATE TABLE FICHAGES (CD_FAMILIA long,CD_PACIENTE long,CD_ANO int, DT_ULTREGRA char(10),DT_PARTO char(10)," +
					   " DT_VACINA1 char(10),DT_VACINA2 char(10),DT_VACINA3 char(10),DT_VACINAR char(10), FL_SEISGESTA char(1),"+
					   " FL_NATIMORTO char(1),FL_36ANOSMAIS char(1), FL_MENOS20 char(1), FL_SANGRAMENTO char(1),FL_EDEMA char(1),FL_DIABETES char(1),"+
					   " FL_PRESSAOALTA char(1),DT_NASCVIVO char(10),DT_NASCMORTO char(10),DT_ABORTO char(10),DT_PUERPERIO1 char(10),DT_PUERPERIO2 char(10),DS_OBSERVACAO char(100),STATUS int)");		
		driver.execute("CREATE TABLE FICHAGESACOMP (CD_FAMILIA long,CD_PACIENTE long, CD_MES int,CD_ANO int,FL_ESTADONUTRI char(1), DT_CONSPRENATAL char(10), DT_VISITA char(10),STATUS int)");
		
		
		driver.execute("CREATE TABLE FICHAIDOSO (CD_FAMILIA long,CD_PACIENTE long,CD_ANO int, FL_FUMANTE char(10),DT_DTP char(10)," +
				   " DT_INFLUENZA char(10),DT_PNEUMONOCOCOS char(10),FL_HIPERTENSAO char(1),FL_TUBERCULOSE char(1), FL_ALZHEIMER char(1),"+
				   " FL_DEFFISICA char(1),FL_HANSENIASE char(1), FL_MALPARKSON char(1), FL_CANCER char(1),FL_ACAMADO char(1),FL_INTERNAMENTOS char(1),"+
				   " FL_SEQUELASAVC char(1),FL_ALCOLATRA char(1),STATUS int)");	
	    driver.execute("CREATE TABLE FICHAIDOSOACOMP (CD_FAMILIA long,CD_PACIENTE long, CD_MES int,CD_ANO int,FL_ESTADONUTRI char(1), DT_VISITA char(10), DT_ACOMPPSF char(10),STATUS int)");
	    
	    
	    driver.execute("CREATE TABLE FICHACRIANCA (CD_FAMILIA long,CD_PACIENTE long,CD_ANO int, CD_AVALIACAOALIMEN int,DS_TIPOALIMENTACAO char(50)," +
				   " FL_IMUNIZADO char(1),FL_COMPLETARESQ char(1),FL_DIARREIA char(1),FL_USARAMTRO char(1), FL_INTERNAMENTOS char(1),"+
				   " FL_IRA char(1),FL_CAXUMBA char(1), FL_CATAPORA char(1), FL_BAIXOPESONASC char(1),FL_DEFICIENCIAS char(1),STATUS int)");
	    driver.execute("CREATE TABLE FICHACRIANCAACOMP (CD_FAMILIA long,CD_PACIENTE long, CD_MES int,CD_ANO int,DT_QUADRONUTRI char(10), NUM_PESO float, NUM_ALTURA float,STATUS int)");
	    
	    driver.execute("CREATE TABLE AGENTE (CD_AGENTE long,NM_AGENTE char(30), DS_LOGIN char(10), DS_SENHA char(10), CD_SEG char(2), CD_AREA char(3), CD_MICROA char(2))");
	    
	    driver.execute("CREATE TABLE VALIDATION (SEQUENCIAL int)");
	    
	    driver.execute("CREATE TABLE OCUPACAO (CD_OCUPACAO int, DS_OCUPACAO char(60),STATUS int)");
		
		Vm.debug("Pdbs Gerado com Sucesso!");
	}

}
