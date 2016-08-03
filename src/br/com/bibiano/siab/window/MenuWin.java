package br.com.bibiano.siab.window;

import waba.fx.Color;
import waba.sys.Settings;
import waba.sys.Vm;
import waba.ui.Button;
import waba.ui.ControlEvent;
import waba.ui.Event;
import waba.ui.MessageBox;
import waba.ui.Window;
/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class MenuWin extends Window {
	private static MenuWin menuWin;
	
	private Button btnFamilia;
	private Button btnTb;
	private Button btnHan;
	private Button btnHa;
	private Button btnDia;
	private Button btnGes;
	private Button btnIdoso;
	private Button btnCrianca;
	private Button btnSair;
	private Button btnContato;
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static MenuWin criarInstancia()
	{
		if(menuWin == null)
		{
			menuWin = new MenuWin();
		}
		return menuWin;
	}
	
	public MenuWin(){
		super("Menu - SIAB MOBILE",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(btnFamilia = new Button("CAD FAMÍLIA"));
		btnFamilia.setRect(LEFT + 7*(int) ConstantesJanela.FATOR_X,TOP  + 10*(int) ConstantesJanela.FATOR_Y, 65*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
			
		add(btnTb = new Button("FICHA TB"));
		btnTb.setRect(AFTER + 10*(int) ConstantesJanela.FATOR_X,SAME , 65*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		add(btnHan = new Button("FICHA HAN"));
		btnHan.setRect(LEFT + 7*(int) ConstantesJanela.FATOR_X,AFTER  + 15*(int) ConstantesJanela.FATOR_Y, 65*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
				
		add(btnHa = new Button("FICHA HA"));
		btnHa.setRect(AFTER + 10*(int) ConstantesJanela.FATOR_X,SAME , 65*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
				
		add(btnDia = new Button("FICHA DIA"));
		btnDia.setRect(LEFT + 7*(int) ConstantesJanela.FATOR_X,AFTER  + 15*(int) ConstantesJanela.FATOR_Y, 65*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
				
		add(btnGes = new Button("FICHA GES"));
		btnGes.setRect(AFTER +10*(int) ConstantesJanela.FATOR_X,SAME , 65*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		add(btnIdoso = new Button("FICHA IDOSO"));
		btnIdoso.setRect(LEFT + 7*(int) ConstantesJanela.FATOR_X,AFTER  + 15*(int) ConstantesJanela.FATOR_Y, 65*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		add(btnCrianca = new Button("FICHA CRIANCA"));
		btnCrianca.setRect(AFTER +10*(int) ConstantesJanela.FATOR_X,SAME, 65*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		add(btnSair = new Button("SAIR"));
		btnSair.setRect(LEFT + 7*(int) ConstantesJanela.FATOR_X,AFTER  + 15*(int) ConstantesJanela.FATOR_Y, 65*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		add(btnContato = new Button("ABOUT"));
		btnContato.setRect(AFTER + 10*(int) ConstantesJanela.FATOR_X,SAME, 65*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		if(Settings.isColor)
		{	//Setando as cores se o dispositivos suportar cores.
	    	Color BLUE =ConstantesJanela.BLUE;
		     setForeColor(BLUE);
		     btnFamilia.setForeColor(BLUE);
		     btnTb.setForeColor(BLUE);
		     btnHan.setForeColor(BLUE);
		     btnDia.setForeColor(BLUE);
		     btnGes.setForeColor(BLUE);
		     btnHa.setForeColor(BLUE);
		     btnSair.setForeColor(BLUE);
		     btnIdoso.setForeColor(BLUE);
		     btnCrianca.setForeColor(BLUE);
		     btnContato.setForeColor(BLUE);
		}
	}
	

    public void onEvent(Event e) {	
    	try{
		    if (e.type == ControlEvent.PRESSED) { 
			    if(e.target == btnTb) {
			    	ListaFichaTbWin fichaTb   = new ListaFichaTbWin();
			    	fichaTb.PopulaForm();
			    	fichaTb.popupBlockingModal();
			    }else if(e.target == btnDia){
			    	ListaFichaDiaWin listaFichaDiaWin = ListaFichaDiaWin.criarInstancia();
			    	listaFichaDiaWin.PopulaForm();
			    	listaFichaDiaWin.popupBlockingModal();
			    }else if(e.target == btnFamilia){
			    	ListaFamiliaWin listaCadFamiliaWin = ListaFamiliaWin.criarInstancia();
			    	listaCadFamiliaWin.PopulaForm();
			    	listaCadFamiliaWin.popupBlockingModal();		    	
			    }else if(e.target == btnHa){
			    	ListaFichaHaWin listaFichaHaWin = ListaFichaHaWin.criarInstancia();
			    	listaFichaHaWin.PopulaForm();
			    	listaFichaHaWin.popupBlockingModal();
			    }else if(e.target == btnHan){
			    	ListaFichaHanWin fichaHanWin = ListaFichaHanWin.criarInstancia();
			    	fichaHanWin.PopulaForm();
			    	fichaHanWin.popupBlockingModal();
			    }else if(e.target == btnGes){
			    	ListaFichaGesWin fichaGesWin = ListaFichaGesWin.criarInstancia();
			    	fichaGesWin.PopulaForm();
			    	fichaGesWin.popupBlockingModal();
			    } else if(e.target==btnSair){
			    	unpop();
			    } else if(e.target==btnIdoso){
			    	ListaFichaIdosoWin listaFichaIdosoWin = ListaFichaIdosoWin.criarInstancia();
			    	listaFichaIdosoWin.PopulaForm();
			    	listaFichaIdosoWin.popupBlockingModal();
			    } else if(e.target==btnCrianca){
			    	ListaFichaCriancaWin listaFichaCriancaWin = ListaFichaCriancaWin.criarInstancia();
			    	listaFichaCriancaWin.PopulaForm();
			    	listaFichaCriancaWin.popupBlockingModal();
			    }else if(e.target == btnContato){
			    	AboutWin aboutWin =  AboutWin.criarInstancia();
			    	aboutWin.popupBlockingModal();
			    }
		    }
    	}catch (Exception error) {
			MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+error.getMessage());
			msgBoxErro.popupBlockingModal();
			Vm.debug("Erro: "+error.getMessage());
		}   
	}  
}
