
import br.com.bibiano.siab.business.LoginNgc;
import br.com.bibiano.siab.sync.BBPDBPrincipal;
import br.com.bibiano.siab.sync.BBPDBTemp;
import br.com.bibiano.siab.sync.GeraTabela;
import br.com.bibiano.siab.sync.ReplicationPdb;
import br.com.bibiano.siab.sync.SyncDownManager;
import br.com.bibiano.siab.window.ConstantesJanela;
import br.com.bibiano.siab.window.ListaFichaTbWin;
import br.com.bibiano.siab.window.MenuWin;
import br.com.bibiano.siab.window.ReplicationWin;
import waba.fx.Color;
import waba.fx.Image;
import waba.sys.Settings;
import waba.sys.Time;
import waba.sys.Vm;
import waba.ui.Button;
import waba.ui.ControlEvent;
import waba.ui.Edit;
import waba.ui.Event;
import waba.ui.Label;
import waba.ui.MainWindow;
import waba.ui.MessageBox;

/**
 * Classe MainWindow
 * 
 * @version 1.0 28/11/2005
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */

  public class SIAB extends MainWindow { 
	  	  
	  private Label lblLogin;
	  private Label lblSenha;
	  private Button btnOk;
	  private Button btnSair;
	  private Button btnReplicar;
	  private Button btnLogo;
	  private Button btnSincronizar;
	   
	  Edit edtLogin;
	  Edit edtSenha;
	 
	  
	   public SIAB() { 
		    super("SIAB",TAB_ONLY_BORDER);  
		    
		    String cumprimento;
			Time tmr = new Time();
			if (tmr.hour >= 5 && tmr.hour < 12) {
				cumprimento = "Bom dia ";
			} else if (tmr.hour >= 12 && tmr.hour < 18) {
				cumprimento = "Boa tarde ";
			} else { //qualquer hora entre as 18 e as 4 da manha.
				cumprimento = "Boa noite ";
			}			
			setTitle("SIAB - "+cumprimento);
			
		    if (Settings.platform.equals("PalmOS") || !Settings.onDevice) {
	            Settings.setUIStyle(Settings.PalmOS);
	        }
		    
		    if ( "Java".equals( Settings.platform ) ) {
		    	//waba.applet.JavaBridge.setNonGUIApp( );
		    	Settings.dataPath = "G:\\SIAB_MOBILE3\\SIAB\\data";
		    	// Settings.dataPath = "C:\\bibiano\\workspace\\SIAB\\data";
			}
		    
		    Settings.dateSeparator = '/'; //necessário para a sfc-mobile.
			Settings.dateFormat = Settings.DATE_DMY; //necessário para a sfc-mobile.
			Settings.timeSeparator = ':';  //necessário para a sfc-mobile.
			Settings.is24Hour = true; //necessário para a sfc-mobile.
			
			Vm.interceptSystemKeys(Vm.SK_ALL | Vm.SK_LAUNCH);
		    
		    add(btnLogo =  new Button(new Image("images/logoSiab2.bmp")));
		    btnLogo.setRect(CENTER, TOP + 5*(int) ConstantesJanela.FATOR_Y, 90*(int) ConstantesJanela.FATOR_X, 58*(int) ConstantesJanela.FATOR_Y);
		    btnLogo.onlyShowImage();
		    
		    add(lblLogin =  new Label("Login :"));
		    lblLogin.setRect(LEFT +10*(int) ConstantesJanela.FATOR_X, AFTER+10*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, 10*(int) ConstantesJanela.FATOR_Y);
		    
		    add(lblSenha =  new Label("Senha :"));
		    lblSenha.setRect(LEFT +10*(int) ConstantesJanela.FATOR_X, AFTER+20*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, 10*(int) ConstantesJanela.FATOR_Y);
		    
		    
		   // setForeColor( new Color( 44, 0, 139 ) );
		   // setBackColor( new Color(255, 190, 70));
		    
			add(edtLogin = new Edit());
			add(edtSenha = new Edit());
			//edtLogin.setText("1");
			//edtSenha.setText("1");
			edtLogin.setRect(AFTER +6*(int) ConstantesJanela.FATOR_X, SAME-5*(int) ConstantesJanela.FATOR_Y, 100*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y, lblLogin);
			edtSenha.setRect(AFTER +5*(int) ConstantesJanela.FATOR_X, SAME-5*(int) ConstantesJanela.FATOR_Y, 100*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y,lblSenha);
			edtLogin.setValidChars("1234567890");
		    
		    add(btnOk = new Button("OK"));		    
		    btnOk.setRect(LEFT + 65*(int) ConstantesJanela.FATOR_X,BOTTOM  - 10*(int) ConstantesJanela.FATOR_Y, 40*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);		    	  
		    add(btnSair = new Button("Sair"));		    
		    btnSair.setRect(LEFT + 110*(int) ConstantesJanela.FATOR_X,BOTTOM  - 10*(int) ConstantesJanela.FATOR_Y, 40*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		    
		    add(btnReplicar = new Button("Sincronizar"));		    
		    btnReplicar.setRect(LEFT + 5*(int) ConstantesJanela.FATOR_X,BOTTOM  - 10*(int) ConstantesJanela.FATOR_Y, 55*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		    btnReplicar.setVisible(true);
	   }
	 
	   public void onStart() {
		    Vm.debug("Iniciando Execução da Aplicação");	
		    if (Settings.isColor) {
				
				setForeColor( ConstantesJanela.BLUE );
			}
		    
		    if(Settings.isColor)
			{	//Setando as cores se o dispositivos suportar cores.
		    	Color BLUE =ConstantesJanela.BLUE;
			     setForeColor(BLUE);
			     btnReplicar.setForeColor(BLUE);
			     btnOk.setForeColor(BLUE);
			     btnSair.setForeColor(BLUE);
			     lblLogin.setForeColor(BLUE);
			     lblSenha.setForeColor(BLUE);
			}
	   } 
	 
	   public void onExit() { 
		    Vm.debug("Terminando a Execução da Aplicação");
	   }	   
	   
	   public void onEvent(Event e) {		   
		   try{
			    if (e.type == ControlEvent.PRESSED) { 
				    if (e.target == btnOk) {
				    	
				    	/*if(loginNgc.ValidaSenha(edtLogin.getText(),edtSenha.getText())){
				    		BBPDBTemp bbPdbTemp = BBPDBTemp.criarInstancia();
							BBPDBPrincipal bbPdbPrincipal= BBPDBPrincipal.criarInstancia();
					    	if(bbPdbTemp.getSequencial()!=bbPdbPrincipal.getSequencial()){
					    		ReplicationWin replicationWin = ReplicationWin.criarInstancia();	
					    		replicationWin.popupModal();
					    		replicationWin.populaform();
					    	}	
				    		MenuWin menuWin = MenuWin.criarInstancia();
				    		menuWin.popupBlockingModal();
					    	exit(0);
				    	}else{
				    		MessageBox msgBoxErro = new MessageBox("Atenção","Login ou Senha Inválida");
							msgBoxErro.popupBlockingModal();
				    	}*/
				    	/*MessageBox msg = null;
				    	GeraTabela geraTabela = GeraTabela.criarInstancia();
				    	(new MessageBox("Erro","Qtd PacFamilia "+geraTabela.GeneratePdb(true))).popupBlockingModal();
				    	geraTabela.GeneratePdb(false);
				    	SyncDownManager downManager = SyncDownManager.criarInstancia();
				    	downManager.limpaTabelas();
				    	msg=new MessageBox("Erro","Limpou todas as tabelas ");
				    	msg.setUnpopDelay(3000);
				    	msg.popupModal();				    	
				    	downManager.creatDescriber();
				    	msg=(new MessageBox("Erro","Criou Describer",null));
				    	msg.popupModal(); 	
				    	
				    	downManager.getDownOcupacao(16,"I");
				    	msg=(new MessageBox("Erro","getDownOcupacao",null));
				    	msg.popupModal();				    	
				    	//downManager.getDownFichaCadFam(16,"A");
				    	downManager.getDownFichaCadFam(16,"I");
				    	msg=(new MessageBox("Erro","getDownFichaCadFam",null));
				    	msg.popupModal();				    	
				    	downManager.getDownPacFamilia(16,"I");
				    	msg=(new MessageBox("Erro","getDownPacFamilia",null));	
				    	msg.popupModal();				    	
				    	downManager.getDownFichaTb(16,"I");
				    	msg=(new MessageBox("Erro","getDownFichaTb",null));
				    	msg.popupModal();				    	
				    	downManager.getDownFichaTbAcomp(16,"I");
				    	msg=(new MessageBox("Erro","getDownFichaTbAcomp",null));
				    	msg.popupModal();				    	
				    	downManager.getDownFichaHan(16,"I");
				    	msg=(new MessageBox("Erro","getDownFichaHan",null));
				    	msg.popupModal();
				    	downManager.getDownFichaHanAcomp(16,"I");
				    	msg=(new MessageBox("Erro","getDownFichaHanAcomp",null));
				    	msg.popupModal();
				    	downManager.getDownFichaHa(16,"I");
				    	msg=(new MessageBox("Erro","getDownFichaHa",null));
				    	msg.popupModal();
				    	downManager.getDownFichaHaAcomp(16,"I");
				    	msg=(new MessageBox("Erro","getDownFichaHaAcomp",null));
				    	msg.popupModal();
				    	downManager.getDownFichaDia(16,"I");
				    	msg=(new MessageBox("Erro","getDownFichaDia",null));
				    	msg.popupModal();
				    	downManager.getDownFichaDiaAcomp(16,"I");
				    	msg=(new MessageBox("Erro","getDownFichaDiaAcomp",null));
				    	msg.popupModal();
				    	downManager.getDownFichaGes(16,"I");
				    	msg=(new MessageBox("Erro","getDownFichaGes",null));
				    	msg.popupModal();
				    	downManager.getDownFichaGesAcomp(16,"I");
				    	msg=(new MessageBox("Erro","getDownFichaGesAcomp",null));
				    	msg.popupModal();
				    	downManager.getDownFichaCri(16,"I");
				    	msg=(new MessageBox("Erro","getDownFichaCri",null));
				    	msg.popupModal();
				    	downManager.getDownFichaCriAcomp(16,"I");
				    	msg=(new MessageBox("Erro","getDownFichaCriAcomp",null));
				    	msg.popupModal();
				    	downManager.getDownFichaIdoso(16,"I");
				    	msg=(new MessageBox("Erro","getDownFichaIdoso",null));
				    	msg.popupModal();
				    	downManager.getDownFichaIdosoAcomp(16,"I");
				    	msg=(new MessageBox("Erro","getDownFichaIdosoAcomp",null));
				    	msg.popupModal();*/
				    	
				    	LoginNgc loginNgc = LoginNgc.criarInstancia();
				    	if(loginNgc.ValidaSenha(edtLogin.getText(),edtSenha.getText())){
				    		if(loginNgc.getAnoUltSinc()!=loginNgc.getAno()){
				    			MessageBox msgBoxErro = new MessageBox("Atenção","Favor Realizar um Novo Sincronismo");
								msgBoxErro.popupBlockingModal();
								return;
				    		}
					    	MenuWin menuWin = MenuWin.criarInstancia();
				    		menuWin.popupBlockingModal();
				    		exit(0);
				    	} else{
				    		MessageBox msgBoxErro = new MessageBox("Atenção","Login ou Senha Inválida");
							msgBoxErro.popupBlockingModal();
				    	}
				    }else if (e.target == btnSair) {					    	
				    	exit(0);
				    }else if(e.target ==btnReplicar){				    	
						
				    	ReplicationWin replicationWin = ReplicationWin.criarInstancia();				    	
				    	replicationWin.popupBlockingModal();				    
			    		replicationWin.unpop();
			    		exit(0);
				    	/*BBPDBTemp bbPdbTemp = BBPDBTemp.criarInstancia();
						BBPDBPrincipal bbPdbPrincipal= BBPDBPrincipal.criarInstancia();
						MessageBox msgBoxErro = new MessageBox("Erro",bbPdbTemp.getSequencial()+" >"+bbPdbPrincipal.getSequencial());
						msgBoxErro.popupBlockingModal();
				    	if(bbPdbTemp.getSequencial()!=bbPdbPrincipal.getSequencial()){
				    		ReplicationWin replicationWin = ReplicationWin.criarInstancia();				    		
				    		replicationWin.popupModal();
				    		replicationWin.populaform();
				    	}		*/		    	
				    }
			    }
	     } catch (Exception erro) {
			Vm.debug("Erro: "+erro.getMessage());
			MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+erro.getMessage());
			msgBoxErro.popupBlockingModal();
		}
	 }  
	   
}