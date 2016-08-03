package br.com.bibiano.siab.window;


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
import waba.ui.Window;

/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */

  public class AboutWin extends Window { 
	  	  
	  private Label lblVersao;
	  private Label lblSuporte;
	  private Label lblCopy;
	  private Label lblUltAtualizacao;
	  private Label lblLicenca;
	  private Button btnVoltar;
	  private Button btnLogo;  
	  private static AboutWin aboutWin;
	 
	  
	   public AboutWin() { 
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
			setTitle("About SIAB MOBILE - "+cumprimento);
		    
		    add(btnLogo =  new Button(new Image("images/logoSiab2.bmp")));
		    btnLogo.setRect(CENTER, TOP + 5*(int) ConstantesJanela.FATOR_Y, 70*(int) ConstantesJanela.FATOR_X, 40*(int) ConstantesJanela.FATOR_Y);
		    btnLogo.onlyShowImage();
		    
		    add(lblVersao =  new Label("Versão 1.2 "),LEFT +5*(int) ConstantesJanela.FATOR_X, AFTER+1*(int) ConstantesJanela.FATOR_Y);
		    
		    add(lblLicenca =  new Label("Licenciado a Secr. de Saúde de Tauá"),LEFT +5*(int) ConstantesJanela.FATOR_X, AFTER+5*(int) ConstantesJanela.FATOR_Y);
		    
		    add(lblSuporte =  new Label("Contato: bibianofilho@gmail.com"),LEFT +5*(int) ConstantesJanela.FATOR_X, AFTER+5*(int) ConstantesJanela.FATOR_Y);
		    
		    add(lblCopy =  new Label("All Rights Reserved"),LEFT +5*(int) ConstantesJanela.FATOR_X, AFTER+5*(int) ConstantesJanela.FATOR_Y);
		    
		    add(lblUltAtualizacao =  new Label("Última Atualização: 21/11/2007"),LEFT +5*(int) ConstantesJanela.FATOR_X, AFTER+5*(int) ConstantesJanela.FATOR_Y);
		    
		    
		    
		    
		    
		    add(btnVoltar = new Button("OK"));		    
		    btnVoltar.setRect(LEFT + 65*(int) ConstantesJanela.FATOR_X,BOTTOM  - 7*(int) ConstantesJanela.FATOR_Y, 40*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		    
		    if(Settings.isColor)
			{	//Setando as cores se o dispositivos suportar cores.
		    	Color BLUE =ConstantesJanela.BLUE;
			     setForeColor(BLUE);			    
			     btnVoltar.setForeColor(BLUE);			    
			}
		   
	   }
	   
	   public static AboutWin criarInstancia()
		{
			if(aboutWin == null)
			{
				aboutWin = new AboutWin();
			}
			return aboutWin;
		}  
	   
	   public void onEvent(Event e) {		   
		   try{
			    if (e.type == ControlEvent.PRESSED) { 
				    if (e.target == btnVoltar) {
				    	unpop();
				    }
			    }
	     } catch (Exception erro) {
			Vm.debug("Erro: "+erro.getMessage());
			MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+erro.getMessage());
			msgBoxErro.popupBlockingModal();
		}
	 }  
	   
}