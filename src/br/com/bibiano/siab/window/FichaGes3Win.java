package br.com.bibiano.siab.window;

import litebase.ResultSet;
import superwaba.ext.xplat.ui.MultiEdit;
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
import waba.ui.PenEvent;
import waba.ui.Window;
import waba.util.Hashtable;
import br.com.bibiano.siab.business.AgenteNgc;
import br.com.bibiano.siab.business.FichaGesNgc;
import br.com.bibiano.siab.business.SIABBusiness;
/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class FichaGes3Win extends Window {
	private static FichaGes3Win fichaGes3Win;

	private Label lblResGestacao;
	private Label lblNv;
	private Edit edtNv;
	private Label lblNm;
	private Edit edtNm;
	private Label lblAb;
	private Edit edtAb;	
	private Label lblDtConsPuerperio;	
	private Label lblObservacao;
	private Label lblDtPuerperio1;
	private Label lblDtPuerperio2;
	private Edit edtDtPuerperio1;
	private Edit edtDtPuerperio2;		
	private MultiEdit edtObservacao;
	
	private Button btnPrev;
	private Button btnFinalizar;
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static FichaGes3Win criarInstancia()
	{
		if(fichaGes3Win == null)
		{
			fichaGes3Win = new FichaGes3Win();
		}
		return fichaGes3Win;
	}
	
	public FichaGes3Win(){
		
		super("Ficha GES3",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(lblResGestacao = new Label("Resultado da Gestação Atual:"),LEFT + 3*(int) ConstantesJanela.FATOR_X,AFTER+ 2*(int) ConstantesJanela.FATOR_Y);
		add(lblNv = new Label("NV:"),LEFT + 3*(int) ConstantesJanela.FATOR_X,AFTER+ 2*(int) ConstantesJanela.FATOR_Y);
		add(edtNv = new Edit("99/99/9999"));
		edtNv.setMaxLength(10);
		edtNv.setKeyboard(Edit.KBD_CALENDAR);
		edtNv.setEditable(false);
		edtNv.setRect(AFTER +2*(int) ConstantesJanela.FATOR_X, SAME-(int) ConstantesJanela.FATOR_Y,55*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		add(lblNm = new Label("NM:"),AFTER + 3*(int) ConstantesJanela.FATOR_X,SAME+ (int) ConstantesJanela.FATOR_Y);
		add(edtNm = new Edit("99/99/9999"));
		edtNm.setMaxLength(10);
		edtNm.setKeyboard(Edit.KBD_CALENDAR);
		edtNm.setEditable(false);
		edtNm.setRect(AFTER +2*(int) ConstantesJanela.FATOR_X, SAME-(int) ConstantesJanela.FATOR_Y,55*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		add(lblAb = new Label("AB:"),LEFT + 3*(int) ConstantesJanela.FATOR_X,AFTER+ 2*(int) ConstantesJanela.FATOR_Y);
		add(edtAb = new Edit("99/99/9999"));
		edtAb.setMaxLength(10);
		edtAb.setKeyboard(Edit.KBD_CALENDAR);
		edtAb.setEditable(false);
		edtAb.setRect(AFTER +3*(int) ConstantesJanela.FATOR_X, SAME-(int) ConstantesJanela.FATOR_Y,55*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
			
		add(lblDtConsPuerperio = new Label("Data da Consulta de Puerpério:"),LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+2*(int)ConstantesJanela.FATOR_Y);		
		add(lblDtPuerperio1 = new Label("1:"),LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+3*(int)ConstantesJanela.FATOR_Y);
		add(edtDtPuerperio1 = new Edit("99/99/9999"));
		edtDtPuerperio1.setMaxLength(10);
		edtDtPuerperio1.setKeyboard(Edit.KBD_CALENDAR);
		edtDtPuerperio1.setEditable(false);
		edtDtPuerperio1.setRect(AFTER +2*(int) ConstantesJanela.FATOR_X, SAME-(int) ConstantesJanela.FATOR_Y,55*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		add(lblDtPuerperio2 = new Label("2:"),AFTER+3*(int)ConstantesJanela.FATOR_X, SAME+(int)ConstantesJanela.FATOR_Y);
		add(edtDtPuerperio2 = new Edit("99/99/9999"));
		edtDtPuerperio2.setMaxLength(10);
		edtDtPuerperio2.setKeyboard(Edit.KBD_CALENDAR);
		edtDtPuerperio2.setEditable(false);
		edtDtPuerperio2.setRect(AFTER +2*(int) ConstantesJanela.FATOR_X, SAME-2*(int) ConstantesJanela.FATOR_Y,55*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		add(lblObservacao = new Label("Observação:"),LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+2*(int)ConstantesJanela.FATOR_Y);
		edtObservacao = new MultiEdit(1, 3);
		add(edtObservacao);
		edtObservacao.setRect(LEFT +3*(int) ConstantesJanela.FATOR_X, AFTER+2*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 40*(int) ConstantesJanela.FATOR_Y);
		
		add(btnPrev = new Button("<<"));
		btnPrev.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 1*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		add(btnFinalizar = new Button("Finalizar"));
		btnFinalizar.setRect(RIGHT - 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 1*(int) ConstantesJanela.FATOR_Y, 50*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		if(Settings.isColor)
		{	//Setando as cores se o dispositivos suportar cores.
	    	Color BLUE =ConstantesJanela.BLUE;
		     setForeColor(BLUE);
		     lblResGestacao.setForeColor(BLUE);
		     lblDtConsPuerperio.setForeColor(BLUE);
		     lblObservacao.setForeColor(BLUE);
		     lblNv.setForeColor(BLUE);
		     lblNm.setForeColor(BLUE);
		     lblAb.setForeColor(BLUE);
		     lblDtPuerperio1.setForeColor(BLUE);
		     lblDtPuerperio2.setForeColor(BLUE);
		     btnFinalizar.setForeColor(BLUE);
		     btnPrev.setForeColor(BLUE);
		}
	}
	
	protected void PopulaForm() {
		try {
			if(ListaFichaGesWin.cmd.equals("U")){  
    			FichaGesNgc fichaGesNgc = FichaGesNgc.criarInstancia();
    	    	ResultSet rs = fichaGesNgc.getFichaGes(FichaGes1Win.cdFamilia,FichaGes1Win.cdPaciente);    			     			
    			if(rs.next()){  
    				edtNv.setText(rs.getString("DT_NASCVIVO"));
    				edtNm.setText(rs.getString("DT_NASCMORTO"));
    				edtAb.setText(rs.getString("DT_ABORTO"));
    				edtDtPuerperio1.setText(rs.getString("DT_PUERPERIO1"));
    				edtDtPuerperio2.setText(rs.getString("DT_PUERPERIO2"));
    				edtObservacao.setText(rs.getString("DS_OBSERVACAO"));
    			}
    		} 
		} catch (Exception e) {
			Vm.debug("Erro: "+e.getMessage());
    		MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+e.getMessage());
			msgBoxErro.popupBlockingModal();
		}		
	}
	
	 public void LimpaForm(){    	
	    	edtNv.setText("");
	    	edtNm.setText("");
	    	edtAb.setText("");
	    	edtDtPuerperio1.setText("");
	    	edtDtPuerperio2.setText("");
	    	edtObservacao.setText("");
	}

    public void onEvent(Event e) {	
    	try{
		    if (e.type == ControlEvent.PRESSED) { 
		    	if(e.target==btnPrev){
		    		unpop();
		    	}else if(e.target==btnFinalizar){
		    		if(ValidaForm()){
			    		MessageBox msgBoxFinaliza = new MessageBox("Fim", "Deseja Finalizar a Ficha GES?",new String[]{"Sim","Não"});
				    	msgBoxFinaliza.popupBlockingModal();			    	
				    	if(msgBoxFinaliza.getPressedButtonIndex()==0){
				    		FichaGesNgc fichaGesNgc=FichaGesNgc.criarInstancia();
				    		Hashtable voFichaGes = new Hashtable(10);
				    		AgenteNgc agenteNgc = AgenteNgc.criarInstancia();
				    		ResultSet rs = agenteNgc.getSegAreaMicroa();
				    		if(rs.next()){
				    			voFichaGes.put("CD_SEGMENTO",rs.getString("CD_SEG"));
				    			voFichaGes.put("CD_AREA",rs.getString("CD_AREA"));
					    		voFichaGes.put("CD_MICROAREA",rs.getString("CD_MICROA"));
				    		}	
				    		
				    		voFichaGes.put("NR_FAMILIA",Convert.toString(FichaGes1Win.cdFamilia));
				    		voFichaGes.put("CD_PACIENTE",Convert.toString(FichaGes1Win.cdPaciente));	
				    		voFichaGes.put("FL_ESTADONUTRI",FichaGes1Win.flEstadonutri); 
				    		voFichaGes.put("DT_CONSPRENATAL",FichaGes1Win.dtConsPreNatal);
				    		voFichaGes.put("DT_VISITA",FichaGes1Win.dtVisita);
				    		voFichaGes.put("CD_ANO",FichaGes1Win.cdAno);
				    		voFichaGes.put("DS_OBSERVACAO",edtObservacao.getText()); 
				    		
				    		voFichaGes.put("DT_ULTREGRA",FichaGes1Win.dtUltRegra); 
				    		voFichaGes.put("DT_PARTO",FichaGes1Win.dtParto); 
				    		voFichaGes.put("DT_VACINA1",FichaGes1Win.dtVacina1);
				    		voFichaGes.put("DT_VACINA2",FichaGes1Win.dtVacina2);
				    		voFichaGes.put("DT_VACINA3",FichaGes1Win.dtVacina3);
				    		voFichaGes.put("DT_VACINAR",FichaGes1Win.dtVacinaR);
				    		voFichaGes.put("FL_SEISGESTA",FichaGes1Win.flSeisGesta);
				    		voFichaGes.put("FL_NATIMORTO",FichaGes1Win.flNatiMorto);
				    		voFichaGes.put("FL_36ANOSMAIS",FichaGes1Win.fl36AnosMais);
				    		voFichaGes.put("FL_MENOS20",FichaGes1Win.flMenos20);
				    		voFichaGes.put("FL_SANGRAMENTO",FichaGes1Win.flSangramento);
				    		voFichaGes.put("FL_EDEMA",FichaGes1Win.flEdema);
				    		voFichaGes.put("FL_DIABETES",FichaGes1Win.flDiabetes);
				    		voFichaGes.put("FL_PRESSAOALTA",FichaGes1Win.flPressaoAlta);
				    		voFichaGes.put("DT_NASCVIVO",edtNv.getText());
				    		voFichaGes.put("DT_NASCMORTO",edtNm.getText());
				    		voFichaGes.put("DT_ABORTO",edtAb.getText());
				    		voFichaGes.put("DT_PUERPERIO1",edtDtPuerperio1.getText());
				    		voFichaGes.put("DT_PUERPERIO2",edtDtPuerperio2.getText());
				    		voFichaGes.put("CD_MES",""+SIABBusiness.getMes());
				    		if("U".equals(ListaFichaGesWin.cmd)){
				    			fichaGesNgc.UpdateFichaGes(voFichaGes);
				    		}else{
				    			fichaGesNgc.InserteFichaGes(voFichaGes);
				    		}
				    		ListaFichaGesWin.cmd="F";
  						    unpop();
				    	}
			    	}		    		
		    	}
		    }else if(e.type == PenEvent.PEN_UP){
		    	 if(e.target==edtNv){				    	
		    		 edtNv.popupKCC();
				 }else if(e.target==edtNm){				    	
					 edtNm.popupKCC();
				 }else if(e.target==edtAb){				    	
					 edtAb.popupKCC();
				 }else if(e.target==edtDtPuerperio1){				    	
					 edtDtPuerperio1.popupKCC();
				 }else if(e.target==edtDtPuerperio2){				    	
					 edtDtPuerperio2.popupKCC();
				 }
			}				   
    	}catch (Exception error) {
			MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+error.getMessage());
			msgBoxErro.popupBlockingModal();
			Vm.debug("Erro: "+error.getMessage());
		}   
	}  
   
    private boolean ValidaForm(){	
    	 MessageBox msgGrid;
    	 if(edtObservacao.getText().length()>100){
    		msgGrid = new MessageBox("Atenção","O Campo Observação Ultrapassou|a Quantidade Máxima de|100 Caracteres", new String[]{"OK"});			    	
	    	msgGrid.popupBlockingModal();
	    	msgGrid.setForeColor(ConstantesJanela.BLUE);
	    	return false;
    	 }    	
	 	 return true;
	}

}
