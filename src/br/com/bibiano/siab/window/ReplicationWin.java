package br.com.bibiano.siab.window;

import litebase.LitebaseConnection;
import litebase.ResultSet;
import superwaba.ext.xplat.html.ui.Reset;
import superwaba.ext.xplat.ui.MultiEdit;
import br.com.bibiano.siab.business.AgenteNgc;
import br.com.bibiano.siab.business.SIABBusiness;
import br.com.bibiano.siab.sync.BBPDBPrincipal;
import br.com.bibiano.siab.sync.BBPDBTemp;
import br.com.bibiano.siab.sync.GeraTabela;
import br.com.bibiano.siab.sync.ReplicationPdb;
import br.com.bibiano.siab.sync.SyncDownManager;
import br.com.bibiano.siab.sync.SyncUpManager;
import br.com.bibiano.siab.util.BBDate;
import waba.fx.Color;
import waba.sys.Convert;
import waba.sys.Settings;
import waba.sys.Vm;
import waba.ui.Button;
import waba.ui.ControlEvent;
import waba.ui.Edit;
import waba.ui.Event;
import waba.ui.Label;
import waba.ui.MessageBox;
import waba.ui.ProgressBar;
import waba.ui.Window;
/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class ReplicationWin extends Window {
	private static ReplicationWin replicationWin;
	
	private Label lblTabela;
	public static Label lblNmTabela;
	public static Label lblStatus;
	public static Label lblAcao;
	private MultiEdit edtObservacao;
	private Button btnSair;
	private Button btnSincronizar;
	public static ProgressBar progBarReplication;
	private Label lblLogin;
	private Edit edtLogin;
	private Label lblSenha;
	private Edit edtSenha;
	private Label lblIp;
	private Edit edtIp;
	private boolean isCreatDescribe;
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static ReplicationWin criarInstancia()
	{
		if(replicationWin == null)
		{
			replicationWin = new ReplicationWin();
		}
		return replicationWin;
	}
	
	public ReplicationWin(){
		super("Sincronizar Dados - SIAB",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(lblIp =  new Label("Ip:"),LEFT + 4*(int) ConstantesJanela.FATOR_X, TOP + 5*(int) ConstantesJanela.FATOR_Y);
		add(edtIp =  new Edit());
		edtIp.setRect(AFTER + 2*(int) ConstantesJanela.FATOR_X,SAME-5*(int) ConstantesJanela.FATOR_Y,90*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		//edtIp.setText("183.245.5.136");
		//edtIp.setText("200.253.233.10");
		edtIp.setText("192.168.0.108");
		//edtIp.setText("183.245.4.47");
		
		add(lblLogin =  new Label("Login:"),LEFT + 4*(int) ConstantesJanela.FATOR_X, AFTER+5*(int) ConstantesJanela.FATOR_Y);
		add(edtLogin =  new Edit());
		edtLogin.setRect(AFTER + 2*(int) ConstantesJanela.FATOR_X,SAME-5*(int) ConstantesJanela.FATOR_Y,50*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		edtLogin.setValidChars("1234567890");
		//edtLogin.setText("104");
		
		add(lblSenha =  new Label("Senha :"));
	    lblSenha.setRect(LEFT +4*(int) ConstantesJanela.FATOR_X, AFTER+5*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, 10*(int) ConstantesJanela.FATOR_Y);
	    add(edtSenha = new Edit());
	    edtSenha.setRect(AFTER +5*(int) ConstantesJanela.FATOR_X, SAME-5*(int) ConstantesJanela.FATOR_Y, 50*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y,lblSenha);
	    //edtSenha.setText("1");
	    
	    add(btnSincronizar = new Button("Sincronizar"));
	    btnSincronizar.setRect(AFTER + 8*(int) ConstantesJanela.FATOR_X,SAME+2*(int) ConstantesJanela.FATOR_Y , 50*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
	    
		add(lblTabela =  new Label("Tabela:"),LEFT + 4*(int) ConstantesJanela.FATOR_X, AFTER + 5*(int) ConstantesJanela.FATOR_Y);
		add(lblNmTabela =  new Label(""),AFTER + 2*(int) ConstantesJanela.FATOR_X,SAME);
		add(lblAcao =  new Label("----------------------------"),LEFT + 4*(int) ConstantesJanela.FATOR_X,AFTER  + 5*(int) ConstantesJanela.FATOR_Y);
		
		add(progBarReplication = new ProgressBar());
		progBarReplication.min=0;
		progBarReplication.max=100;		
		progBarReplication.setRect(LEFT + 4*(int) ConstantesJanela.FATOR_X,AFTER  + 5*(int) ConstantesJanela.FATOR_Y, FILL - 3*(int) ConstantesJanela.FATOR_X, 15*(int) ConstantesJanela.FATOR_Y);
		add(lblStatus =  new Label(""),AFTER + 2*(int) ConstantesJanela.FATOR_X,SAME);
		
		edtObservacao = new MultiEdit(1, 2);
		add(edtObservacao);
		edtObservacao.setRect(LEFT +3*(int) ConstantesJanela.FATOR_X, AFTER+10*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 30*(int) ConstantesJanela.FATOR_Y);
		
		
		add(btnSair = new Button("SAIR"));
		btnSair.setRect(LEFT + 7*(int) ConstantesJanela.FATOR_X,BOTTOM  - 5*(int) ConstantesJanela.FATOR_Y, 65*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		if(Settings.isColor)
		{	//Setando as cores se o dispositivos suportar cores.
	    	Color BLUE =ConstantesJanela.BLUE;
		     setForeColor(BLUE);
		     btnSair.setForeColor(BLUE);
		     lblTabela.setForeColor(BLUE);
		     lblLogin.setForeColor(BLUE);
		     lblSenha.setForeColor(BLUE);
		     lblIp.setForeColor(BLUE);
		}
	}
	
	public void populaform() throws Exception{
		//try {
				
				SyncDownManager downManager = SyncDownManager.criarInstancia();
				downManager.setIp(edtIp.getText());				
				if(!isCreatDescribe){
					downManager.creatDescriber();
		    		isCreatDescribe=true;
				}
		    	
		    	long inicio=BBDate.getCurrentTimeMillis();
		    	
		    	upSync(Convert.toInt(edtLogin.getText()));
		    	
		    	downSync(Convert.toInt(edtLogin.getText()));
			   	long fim = BBDate.getCurrentTimeMillis();   		
				 
		    	MessageBox msgBoxErro = new MessageBox("Atenção","Sincronismo Realizado com Sucesso|Tempo: "+(fim-inicio));
				msgBoxErro.popupBlockingModal();
				lblStatus.setText("Sucesso!");
		/*} catch (Exception error) {
			edtObservacao.setText("Erro: "+error.getMessage());
			edtObservacao.repaintNow();
			
			lblStatus.setText("Fallha = "+error.getMessage());
			MessageBox msgBoxErro = new MessageBox("Alerta","Erro: "+error.getMessage());
			msgBoxErro.popupBlockingModal();	
			throw new Exception(error);
		}*/
	}
	
	public void upSync(int cdAgente) throws Exception{	
		
		
			SyncUpManager upManager= new SyncUpManager();
			upManager.setIp(edtIp.getText());
			upManager.setDelFichaAcomp(cdAgente,"fichatbacomp");
			upManager.setDelFicha(cdAgente,"fichatb");
			upManager.setDelFichaAcomp(cdAgente,"fichahanacomp");
			upManager.setDelFicha(cdAgente,"fichahan");
			upManager.setDelFichaAcomp(cdAgente,"fichahaacomp");
			upManager.setDelFicha(cdAgente,"fichaha");
			upManager.setDelFichaAcomp(cdAgente,"fichadiaacomp");
			upManager.setDelFicha(cdAgente,"fichadia");
			upManager.setDelFichaAcomp(cdAgente,"fichagesacomp");
			upManager.setDelFicha(cdAgente,"fichages");
			upManager.setDelFichaAcomp(cdAgente,"fichaidosoacomp");
			upManager.setDelFicha(cdAgente,"fichaidoso");			
			upManager.setDelFichaAcomp(cdAgente,"fichacriancaacomp");
			upManager.setDelFicha(cdAgente,"fichacrianca");	
			
			upManager.UpdateFicha(cdAgente,"FICHACADFAM");
			upManager.UpdateFicha(cdAgente,"PACFAMILIA");
			upManager.UpdateFicha(cdAgente,"FICHATB");
			upManager.UpdateFicha(cdAgente,"FICHATBACOMP");
			upManager.UpdateFicha(cdAgente,"FICHAHAN");
			upManager.UpdateFicha(cdAgente,"FICHAHANACOMP");
			upManager.UpdateFicha(cdAgente,"FICHAHA");
			upManager.UpdateFicha(cdAgente,"FICHAHAACOMP");
			upManager.UpdateFicha(cdAgente,"FICHADIA");
			upManager.UpdateFicha(cdAgente,"FICHADIAACOMP");
			upManager.UpdateFicha(cdAgente,"FICHAGES");
			upManager.UpdateFicha(cdAgente,"FICHAGESACOMP");
			upManager.UpdateFicha(cdAgente,"FICHAIDOSO");
			upManager.UpdateFicha(cdAgente,"FICHAIDOSOACOMP");
			upManager.UpdateFicha(cdAgente,"FICHACRIANCA");
			upManager.UpdateFicha(cdAgente,"FICHACRIANCAACOMP");
			
			upManager.InsertFicha(cdAgente,"FICHACADFAM");
			upManager.InsertFicha(cdAgente,"PACFAMILIA");
			upManager.InsertFicha(cdAgente,"FICHATB");
			upManager.InsertFicha(cdAgente,"FICHATBACOMP");
			upManager.InsertFicha(cdAgente,"FICHAHAN");
			upManager.InsertFicha(cdAgente,"FICHAHANACOMP");
			upManager.InsertFicha(cdAgente,"FICHAHA");
			upManager.InsertFicha(cdAgente,"FICHAHAACOMP");
			upManager.InsertFicha(cdAgente,"FICHADIA");
			upManager.InsertFicha(cdAgente,"FICHADIAACOMP");
			upManager.InsertFicha(cdAgente,"FICHAGES");
			upManager.InsertFicha(cdAgente,"FICHAGESACOMP");
			upManager.InsertFicha(cdAgente,"FICHAIDOSO");
			upManager.InsertFicha(cdAgente,"FICHAIDOSOACOMP");
			upManager.InsertFicha(cdAgente,"FICHACRIANCA");
			upManager.InsertFicha(cdAgente,"FICHACRIANCAACOMP");
			
	}
	
	public void downSync(int cdAgente) throws Exception{
		
				SyncDownManager downManager = SyncDownManager.criarInstancia();
				downManager.setIp(edtIp.getText());
				SyncUpManager upManager= new SyncUpManager();
				upManager.setIp(edtIp.getText());
		    	String[] tpOperacao = {"A","D","U","I"};
		    			    			    	
		    	
		    	for(int i =0;i<tpOperacao.length;i++){
		    		lblAcao.setText("getDownOcupacao: "+tpOperacao[i]);
			    	lblAcao.repaintNow();	
			    	long cdSincronismo=downManager.getDownOcupacao(cdAgente,tpOperacao[i]);
		    		upManager.setRegistroSincronizado(cdAgente,"OCUPACAO",tpOperacao[i],cdSincronismo);	    	
		    	}
		    	lblAcao.setText("getDownFichaCadFam");
		    	lblAcao.repaintNow();	
		    	for(int i =0;i<tpOperacao.length;i++){
		    		progBarReplication.setValue(0);
		    		upManager.setRegistroSincronizado(cdAgente,"FICHACADFAM",tpOperacao[i],downManager.getDownFichaCadFam(cdAgente,tpOperacao[i]));
		    		progBarReplication.setValue(100);
		    	}
		    	
		    	lblAcao.setText("getDownPacFamilia");
		    	lblAcao.repaintNow();
		    	for(int i =0;i<tpOperacao.length;i++){
		    		progBarReplication.setValue(0);
		    	   	upManager.setRegistroSincronizado(cdAgente,"PACFAMILIA",tpOperacao[i],downManager.getDownPacFamilia(cdAgente,tpOperacao[i]));
		    	   	progBarReplication.setValue(100);
		    	}
		    	   	
		    	lblAcao.setText("getDownFichaTb");
		    	lblAcao.repaintNow();	   
		    	for(int i =0;i<tpOperacao.length;i++){
		    		progBarReplication.setValue(0);
			    	upManager.setRegistroSincronizado(cdAgente,"FICHATB",tpOperacao[i],downManager.getDownFichaTb(cdAgente,tpOperacao[i]));
			    	progBarReplication.setValue(100);
		    	}
		    	
		    	lblAcao.setText("getDownFichaTbAcomp");
		    	lblAcao.repaintNow();
		    	for(int i =0;i<tpOperacao.length;i++){
		    		progBarReplication.setValue(0);
		    		upManager.setRegistroSincronizado(cdAgente,"FICHATBACOMP",tpOperacao[i],downManager.getDownFichaTbAcomp(cdAgente,tpOperacao[i]));
		    		progBarReplication.setValue(100);
		    	}
		    	
		    	lblAcao.setText("getDownFichaHan");
		    	lblAcao.repaintNow();
		    	for(int i =0;i<tpOperacao.length;i++){
		    		progBarReplication.setValue(0);
		    		upManager.setRegistroSincronizado(cdAgente,"FICHAHAN",tpOperacao[i],downManager.getDownFichaHan(cdAgente,tpOperacao[i]));
		    		progBarReplication.setValue(100);
		    	}
		    	
		    	lblAcao.setText("getDownFichaHanAcomp");
		    	lblAcao.repaintNow();
		    	for(int i =0;i<tpOperacao.length;i++){
		    		progBarReplication.setValue(0);
		    		upManager.setRegistroSincronizado(cdAgente,"FICHAHANACOMP",tpOperacao[i],downManager.getDownFichaHanAcomp(cdAgente,tpOperacao[i]));
		    		progBarReplication.setValue(100);
		    	}
		    	
		    	lblAcao.setText("getDownFichaHa");
		    	lblAcao.repaintNow();
		    	for(int i =0;i<tpOperacao.length;i++){
		    		progBarReplication.setValue(0);
		    		upManager.setRegistroSincronizado(cdAgente,"FICHAHA",tpOperacao[i],downManager.getDownFichaHa(cdAgente,tpOperacao[i]));
		    		progBarReplication.setValue(100);
		    	}
		    	
		    	lblAcao.setText("getDownFichaHaAcomp");
		    	lblAcao.repaintNow();
		    	for(int i =0;i<tpOperacao.length;i++){
		    		progBarReplication.setValue(0);
		    		upManager.setRegistroSincronizado(cdAgente,"FICHAHAACOMP",tpOperacao[i],downManager.getDownFichaHaAcomp(cdAgente,tpOperacao[i]));
		    		progBarReplication.setValue(100);
		    	}
		    	
		    	lblAcao.setText("getDownFichaDia");
		    	lblAcao.repaintNow();	
		    	for(int i =0;i<tpOperacao.length;i++){
		    		progBarReplication.setValue(0);   	
		    		upManager.setRegistroSincronizado(cdAgente,"FICHADIA",tpOperacao[i],downManager.getDownFichaDia(cdAgente,tpOperacao[i]));
		    		progBarReplication.setValue(100);
		    	}	
		    	
		    	lblAcao.setText("getDownFichaDiaAcomp");
		    	lblAcao.repaintNow();	  
		    	for(int i =0;i<tpOperacao.length;i++){
		    		progBarReplication.setValue(0);
		    		upManager.setRegistroSincronizado(cdAgente,"FICHADIAACOMP",tpOperacao[i],downManager.getDownFichaDiaAcomp(cdAgente,tpOperacao[i]));
		    		progBarReplication.setValue(100);
		    	}
		    	
		    	lblAcao.setText("getDownFichaGes");
		    	lblAcao.repaintNow();	
		    	for(int i =0;i<tpOperacao.length;i++){
		    		progBarReplication.setValue(0);
		    		upManager.setRegistroSincronizado(cdAgente,"FICHAGES",tpOperacao[i],downManager.getDownFichaGes(cdAgente,tpOperacao[i]));
		    		progBarReplication.setValue(100);
		    	}
		    	
		    	lblAcao.setText("getDownFichaGesAcomp");
		    	lblAcao.repaintNow();
		    	for(int i =0;i<tpOperacao.length;i++){
		    		progBarReplication.setValue(0);
		    	  	upManager.setRegistroSincronizado(cdAgente,"FICHAGESACOMP",tpOperacao[i],downManager.getDownFichaGesAcomp(cdAgente,tpOperacao[i]));	
		    	  	progBarReplication.setValue(100);
		    	}  	
		    	
		    	lblAcao.setText("getDownFichaCri");
		    	lblAcao.repaintNow();	
		    	for(int i =0;i<tpOperacao.length;i++){
		    		progBarReplication.setValue(0);
		    	   	upManager.setRegistroSincronizado(cdAgente,"FICHACRIANCA",tpOperacao[i],downManager.getDownFichaCri(cdAgente,tpOperacao[i]));
		    	   	progBarReplication.setValue(100);
		    	}   	
		    	
		    	lblAcao.setText("getDownFichaCriAcomp");
		    	lblAcao.repaintNow();
		    	for(int i =0;i<tpOperacao.length;i++){
		    		progBarReplication.setValue(0);
		    		upManager.setRegistroSincronizado(cdAgente,"FICHACRIANCAACOMP",tpOperacao[i],downManager.getDownFichaCriAcomp(cdAgente,tpOperacao[i]));
		    		progBarReplication.setValue(100);
		    	}
		    	
		    	lblAcao.setText("getDownFichaIdoso");
		    	lblAcao.repaintNow();	   
		    	for(int i =0;i<tpOperacao.length;i++){
		    		progBarReplication.setValue(0);	    	
		    		upManager.setRegistroSincronizado(cdAgente,"FICHAIDOSO",tpOperacao[i],downManager.getDownFichaIdoso(cdAgente,tpOperacao[i]));
		    		progBarReplication.setValue(100);
		    	}	
		    	
		    	lblAcao.setText("getDownFichaIdosoAcomp");
		    	lblAcao.repaintNow();	
		    	for(int i =0;i<tpOperacao.length;i++){
		    		progBarReplication.setValue(0);
		    	  	upManager.setRegistroSincronizado(cdAgente,"FICHAIDOSOACOMP",tpOperacao[i],downManager.getDownFichaIdosoAcomp(cdAgente,tpOperacao[i]));
		    	  	progBarReplication.setValue(100);
		    	}  	
		    	
		    	lblAcao.setText("getAgente");
		    	lblAcao.repaintNow();	
		    	for(int i =0;i<tpOperacao.length;i++){
		    		progBarReplication.setValue(0);
		    	   	upManager.setRegistroSincronizado(cdAgente,"AGENTE",tpOperacao[i],downManager.getDownAgente(cdAgente,tpOperacao[i]));
		    	   	progBarReplication.setValue(100);
		    	}   	
		    	
				
				ReplicationPdb replicationPdb = ReplicationPdb.criarInstancia();
				BBPDBTemp bbPdbTemp = BBPDBTemp.criarInstancia();
				BBPDBPrincipal bbPdbPrincipal= BBPDBPrincipal.criarInstancia();
				
				String[] nomesTabelas = bbPdbTemp.getNameTables();
				int countTables = nomesTabelas.length;   		
			   	for (int indexTable = 0; indexTable < countTables; indexTable++) {
			   		lblNmTabela.setText(nomesTabelas[indexTable]);
		   			lblNmTabela.repaintNow();
			   		replicationPdb.ControllerReplication(nomesTabelas[indexTable]);
			   	}
			   	bbPdbPrincipal.InserteSequencial(bbPdbTemp.getSequencial());
			   	downManager.limpaTabelas(true); 
		
	}

    public void onEvent(Event e) {	
    	try{
		    if (e.type == ControlEvent.PRESSED) { 
			    if(e.target==btnSair){
			    	unpop();
			    }else  if(e.target==btnSincronizar){
			    	GeraTabela geraTabela = GeraTabela.criarInstancia();
					geraTabela.GeneratePdb(true);
					geraTabela.GeneratePdb(false);
					edtObservacao.setText("");
					lblAcao.setText("");
					lblNmTabela.setText("");
					
			    	String vLogin=edtLogin.getText().trim();
			    	String vSenha=edtSenha.getText().trim();
			    	if(vLogin.length()==0 || vSenha.length()==0){
			    		MessageBox msgBoxErro = new MessageBox("Atenção","Login e Senha Requerido");
						msgBoxErro.popupBlockingModal();
						return;
			    	}
			    	SyncDownManager downManager= new SyncDownManager();
			    	downManager.setIp(edtIp.getText());
			    	String[] objRetorno = downManager.getDownValidateAgente(Convert.toInt(vLogin));
			    	LitebaseConnection driver = LitebaseConnection.getInstance(SIABBusiness.CREATORID);  
			        ResultSet rs= driver.executeQuery("select * from agente");
			        if(objRetorno==null){
			        	MessageBox msgBoxErro = new MessageBox("Atenção","Login Inválido");
						msgBoxErro.popupBlockingModal();
						return;
			        }else if( !vLogin.equals(objRetorno[2]) ||
			        		!vSenha.equals(objRetorno[3])){
			        	MessageBox msgBoxErro = new MessageBox("Atenção","Login ou Senha Inválida");
						msgBoxErro.popupBlockingModal();	
						return;
					}
					if(rs.next()){
						if(rs.getString("CD_SEG").equals(objRetorno[4]) && rs.getString("CD_AREA").equals(objRetorno[5]) &&
						   rs.getString("CD_MICROA").equals(objRetorno[6]) ){
							populaform();
							MenuWin menuWin = MenuWin.criarInstancia();
				    		menuWin.popupBlockingModal();
				    		unpop();
						}else{
							MessageBox msgBoxErro = new MessageBox("Atenção","Este Palm Está com Dados |de Outro Agente de Saúde|" +
									"Seq="+rs.getString("CD_SEG")+" Area="+rs.getString("CD_AREA")+" MicroA="+rs.getString("CD_MICROA")+"|");
							msgBoxErro.popupBlockingModal();
							return;
						}
					}else{
						populaform();
						MenuWin menuWin = MenuWin.criarInstancia();
			    		menuWin.popupBlockingModal();
			    		unpop();
					}
			    }
		    }
    	}catch (Exception error) {
    		edtObservacao.setText(error.getMessage());
			/*MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+error.getMessage());
			msgBoxErro.popupBlockingModal();*/			
		}   
	}  
}
