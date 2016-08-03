package br.com.bibiano.siab.sync;
import br.com.bibiano.siab.business.SIABBusiness;
import litebase.LitebaseConnection;
import litebase.ResultSet;
import waba.sys.Settings;
import waba.sys.Vm;

/**
 * Classe Sync
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class GeraTabela extends SIABBusiness {
	static LitebaseConnection driver;
	
	public static GeraTabela criarInstancia()
	{
		
		return new GeraTabela();
	}

		
	public  int GeneratePdb(boolean tmp) {
		String vTmp="";
		if(tmp){
			 vTmp="temp";	
		}else{
			 vTmp="";
		}	
		driver = LitebaseConnection.getInstance(CREATORID);
		if(!driver.exists("FICHATBACOMP"+vTmp)){
			driver.execute("CREATE TABLE FICHATBACOMP"+vTmp+" (CD_SEGMENTO int, CD_AREA int, CD_MICROAREA int,NR_FAMILIA int,CD_PACIENTE int, CD_MES int,CD_ANO int,DT_VISITA char(20)," +
			"FL_MEDICACAO char(2), FL_REAINDESE char(2), DT_CONSULTA char(20), FL_ESCARRO char(2), NUM_EXAMINADOS int, NUM_COMBCG int,STATUS int)");
			if(!tmp)
				driver.execute("CREATE INDEX idx_tbacomp ON FICHATBACOMP(NR_FAMILIA)");
		}
		if(!driver.exists("FICHATB"+vTmp)){
			driver.execute("CREATE TABLE FICHATB"+vTmp+" (CD_SEGMENTO int, CD_AREA int, CD_MICROAREA int,NR_FAMILIA int,CD_PACIENTE int,CD_ANO int, NUM_COMUNICANTES int, NUM_COMUNICANTES5 int, DS_OBSERVACAO char(100),STATUS int)");
			if(!tmp)
				driver.execute("CREATE INDEX idx_tb ON FICHATB(NR_FAMILIA)");
		}

		if(!driver.exists("FICHACADFAM"+vTmp)){
			driver.execute("CREATE TABLE FICHACADFAM"+vTmp+" (CD_SEGMENTO int, CD_AREA int, CD_MICROAREA int,NR_FAMILIA int,CD_ANO int, DS_ENDERECO char(60),"+
			" NUM_ENDERECO int, DS_BAIRRO char(25), DS_CEP char(15), CD_MUNICIPIO  int, DT_CADASTRO char(12), " +
			" CD_UF char(2), CD_TPCASA int, DS_TPCASAESP char(30), NUM_COMODOS int , FL_ENERGIA char(1), CD_DESTLIXO int, CD_TRATAAGUA int," +
			" CD_ABASTEAGUA int, CD_FEZESURINA int,  CD_DOENCAPROCU int, DS_DOENCAPROCUESP char(20),CD_GRUPOCOMU int, DS_GRUPOCOMUESP char(20)," +
			" CD_MEIOSCOMUNI int, DS_MEIOSCOMUNIESP char(20), CD_MEIOSTRANS int, DS_MEIOSTRANSESP char(20), FL_POSSUIPLAN char(1), NUM_PESPLAN int," +
			" NM_PLANO char(20), DS_OBSERVACAO char(100),STATUS int  )");
			if(!tmp)
				driver.execute("CREATE INDEX idx_cadfam ON FICHACADFAM(NR_FAMILIA)");
		}
		if(!driver.exists("PACFAMILIA"+vTmp)){
			driver.execute("CREATE TABLE PACFAMILIA"+vTmp+" (CD_SEGMENTO int, CD_AREA int, CD_MICROAREA int,NR_FAMILIA int, CD_PACIENTE int,CD_ANO int, NM_PACIENTE CHAR(30)," +
					" DT_NASCIMENTO char(10), NUM_IDADE int, FL_SEXO  char(1), FL_MENORQUINZE char(1), FL_ALFABETIZADO char(1),"+
					" FL_FREQESCOLA char(1), CD_OCUPACAO int,FL_ALC char(1),FL_CHA char(1),FL_DEF char(1)," +
					" FL_DIA char(1), FL_DME char(1), FL_EPI char(1), FL_GES char(1), FL_HAN char(1), FL_HA char(1), FL_MAL char(1),"+
					" FL_TBC char(1),FL_MAIOR char(1),STATUS int )");
			if(!tmp)
				driver.execute("CREATE INDEX idx_pacfamilia ON PACFAMILIA(NR_FAMILIA)");
		}
		
		if(!driver.exists("FICHAHAN"+vTmp)){
			driver.execute("CREATE TABLE FICHAHAN"+vTmp+" (CD_SEGMENTO int, CD_AREA int, CD_MICROAREA int,NR_FAMILIA int,CD_PACIENTE int,CD_ANO int, NUM_COMUNICANTES int, DS_OBSERVACAO char(100),STATUS int)");
			if(!tmp)
				driver.execute("CREATE INDEX idx_han ON FICHAHAN(NR_FAMILIA)");
		}
		if(!driver.exists("FICHAHANACOMP"+vTmp)){
			driver.execute("CREATE TABLE FICHAHANACOMP"+vTmp+" (CD_SEGMENTO int, CD_AREA int, CD_MICROAREA int,NR_FAMILIA int,CD_PACIENTE int, CD_MES int,CD_ANO int,DT_VISITA char(20)," +
			"FL_MEDICACAO char(2), DT_ULTDOSE char(10),FL_AUTOCUIDADOS char(1), DT_CONSULTA char(10), NUM_EXAMINADOS int, NUM_COMBCG int,STATUS int)");
			if(!tmp)
				driver.execute("CREATE INDEX idx_hanacomp ON FICHAHANACOMP(NR_FAMILIA)");
		}
		
		if(!driver.exists("FICHAHA"+vTmp)){
			driver.execute("CREATE TABLE FICHAHA"+vTmp+" (CD_SEGMENTO int, CD_AREA int, CD_MICROAREA int,NR_FAMILIA int,CD_PACIENTE int,CD_ANO int, FL_FUMANTE char(1), DS_OBSERVACAO char(100),STATUS int)");
			if(!tmp)
				driver.execute("CREATE INDEX idx_ha ON FICHAHA(NR_FAMILIA)");
		}
		if(!driver.exists("FICHAHAACOMP"+vTmp)){
			driver.execute("CREATE TABLE FICHAHAACOMP"+vTmp+" (CD_SEGMENTO int, CD_AREA int, CD_MICROAREA int,NR_FAMILIA int,CD_PACIENTE int, CD_MES int,CD_ANO int, DT_VISITA char(20)," +
			"FL_DIETA char(1), FL_MEDICACAO char(1),FL_EXERCICIO char(1), DS_PRESSAO char(10), DT_ULTCONSULTA char(10),STATUS int)");
			if(!tmp)
				driver.execute("CREATE INDEX idx_haacomp ON FICHAHAACOMP(NR_FAMILIA)");
		}
		
		if(!driver.exists("FICHADIA"+vTmp)){
			driver.execute("CREATE TABLE FICHADIA"+vTmp+" (CD_SEGMENTO int, CD_AREA int, CD_MICROAREA int,NR_FAMILIA int,CD_PACIENTE int,CD_ANO int, DS_OBSERVACAO char(100),STATUS int)");
			if(!tmp)
				driver.execute("CREATE INDEX idx_dia ON FICHADIA(NR_FAMILIA)");
		}
		if(!driver.exists("FICHADIAACOMP"+vTmp)){
			driver.execute("CREATE TABLE FICHADIAACOMP"+vTmp+" (CD_SEGMENTO int, CD_AREA int, CD_MICROAREA int,NR_FAMILIA int,CD_PACIENTE int, CD_MES int, CD_ANO int,DT_VISITA char(20)," +
			"FL_DIETA char(1),FL_EXERCICIO char(1), FL_INSULINA char(1),FL_HIPOGLICEMIANTE char(1), DT_ULTCONSULTA char(10),STATUS int)");
			if(!tmp)
				driver.execute("CREATE INDEX idx_diaacomp ON FICHADIAACOMP(NR_FAMILIA)");
		}
		
		if(!driver.exists("FICHAGES"+vTmp)){
			driver.execute("CREATE TABLE FICHAGES"+vTmp+" (CD_SEGMENTO int, CD_AREA int, CD_MICROAREA int,NR_FAMILIA int,CD_PACIENTE int,CD_ANO int, DT_ULTREGRA char(10)," +
					       " DT_PARTO char(10), DT_VACINA1 char(10),DT_VACINA2 char(10),DT_VACINA3 char(10),DT_VACINAR char(10), FL_SEISGESTA char(1),"+
						   " FL_NATIMORTO char(1),FL_36ANOSMAIS char(1), FL_MENOS20 char(1), FL_SANGRAMENTO char(1),FL_EDEMA char(1),FL_DIABETES char(1),"+
						   " FL_PRESSAOALTA char(1),DT_NASCVIVO char(10),DT_NASCMORTO char(10),DT_ABORTO char(10),DT_PUERPERIO1 char(10),DT_PUERPERIO2 char(10),DS_OBSERVACAO char(100),STATUS int)");
			if(!tmp)
				driver.execute("CREATE INDEX idx_ges ON FICHAGES(NR_FAMILIA)");
		}
		if(!driver.exists("FICHAGESACOMP"+vTmp)){
			driver.execute("CREATE TABLE FICHAGESACOMP"+vTmp+" (CD_SEGMENTO int, CD_AREA int, CD_MICROAREA int,NR_FAMILIA int,CD_PACIENTE int, CD_MES int,CD_ANO int,FL_ESTADONUTRI char(1), DT_CONSPRENATAL char(10), DT_VISITA char(10),STATUS int)");
			if(!tmp)
				driver.execute("CREATE INDEX idx_gesacomp ON FICHAGESACOMP(NR_FAMILIA)");
		}
		
		if(!driver.exists("FICHAIDOSO"+vTmp)){
			driver.execute("CREATE TABLE FICHAIDOSO"+vTmp+" (CD_SEGMENTO int, CD_AREA int, CD_MICROAREA int,NR_FAMILIA int,CD_PACIENTE int,CD_ANO int, FL_FUMANTE char(10),DT_DTP char(10)," +
					   " DT_INFLUENZA char(10),DT_PNEUMONOCOCOS char(10),FL_HIPERTENSAO char(1),FL_TUBERCULOSE char(1), FL_ALZHEIMER char(1),"+
					   " FL_DEFFISICA char(1),FL_HANSENIASE char(1), FL_MALPARKSON char(1), FL_CANCER char(1),FL_ACAMADO char(1),FL_INTERNAMENTOS char(1),"+
					   " FL_SEQUELASAVC char(1),FL_ALCOLATRA char(1),STATUS int)");
			if(!tmp)
				driver.execute("CREATE INDEX idx_idoso ON FICHAIDOSO(NR_FAMILIA)");
		}
		if(!driver.exists("FICHAIDOSOACOMP"+vTmp)){
			driver.execute("CREATE TABLE FICHAIDOSOACOMP"+vTmp+" (CD_SEGMENTO int, CD_AREA int, CD_MICROAREA int,NR_FAMILIA int,CD_PACIENTE int, CD_MES int,CD_ANO int,FL_ESTADONUTRI char(1), DT_VISITA char(10), DT_ACOMPPSF char(10),STATUS int)");
			if(!tmp)
				driver.execute("CREATE INDEX idx_idosoacomp ON FICHAIDOSOACOMP(NR_FAMILIA)");
		}
	    
		if(!driver.exists("FICHACRIANCA"+vTmp)){
			driver.execute("CREATE TABLE FICHACRIANCA"+vTmp+" (CD_SEGMENTO int, CD_AREA int, CD_MICROAREA int,NR_FAMILIA int,CD_PACIENTE int,CD_ANO int, CD_AVALIACAOALIMEN int,DS_TIPOALIMENTACAO char(50)," +
					   " FL_IMUNIZADO char(1),FL_COMPLETARESQ char(1),FL_DIARREIA char(1),FL_USARAMTRO char(1), FL_INTERNAMENTOS char(1),"+
					   " FL_IRA char(1),FL_CAXUMBA char(1), FL_CATAPORA char(1), FL_BAIXOPESONASC char(1),FL_DEFICIENCIAS char(1),STATUS int)");
			if(!tmp)
				driver.execute("CREATE INDEX idx_crianca ON FICHACRIANCA(NR_FAMILIA)");
		}
		if(!driver.exists("FICHACRIANCAACOMP"+vTmp)){
			driver.execute("CREATE TABLE FICHACRIANCAACOMP"+vTmp+" (CD_SEGMENTO int, CD_AREA int, CD_MICROAREA int,NR_FAMILIA int,CD_PACIENTE int, CD_MES int,CD_ANO int,DT_QUADRONUTRI char(10), NUM_PESO float, NUM_ALTURA float,STATUS int)");
			if(!tmp)
				driver.execute("CREATE INDEX idx_criancaacomp ON FICHACRIANCAACOMP(NR_FAMILIA)");
		}
	    
		if(!driver.exists("AGENTE"+vTmp)){
			driver.execute("CREATE TABLE AGENTE"+vTmp+" (CD_AGENTE long,NM_AGENTE char(30), DS_LOGIN char(10), DS_SENHA char(10), CD_SEG char(2), CD_AREA char(3), CD_MICROA char(2), CD_ANO int,STATUS int)");
		}
	    
		if(!driver.exists("VALIDATION"+vTmp)){
		    driver.execute("CREATE TABLE VALIDATION"+vTmp+" (SEQUENCIAL int)");			
		}
	   
		
		if(!driver.exists("OCUPACAO"+vTmp)){			
			driver.execute("CREATE TABLE OCUPACAO"+vTmp+" (CD_OCUPACAO int, DS_OCUPACAO char(60),STATUS int)");
			if(!tmp)
				driver.execute("CREATE INDEX idx_ocupacao ON OCUPACAO(CD_OCUPACAO)");
		}
		
		
		driver.closeAll();
		Vm.debug("Pdbs Gerado com Sucesso!");
		return 0;
	}

}
