package br.com.bibiano.siab.window;

import litebase.ResultSet;
import waba.fx.Color;
import waba.sys.Convert;
import waba.sys.Settings;
import waba.sys.Vm;
import waba.ui.Button;
import waba.ui.ComboBox;
import waba.ui.ControlEvent;
import waba.ui.Edit;
import waba.ui.Event;
import waba.ui.Label;
import waba.ui.MessageBox;
import waba.ui.PenEvent;
import waba.ui.Window;
import br.com.bibiano.siab.business.FichaANgc;
import br.com.bibiano.siab.business.FichaGesNgc;
/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class FichaGes1Win extends Window {
	private static FichaGes1Win fichaGes1Win;
	
	private Label lblFamilia;
	private ComboBox cbFamilia;
	private Label lblPaciente;
	private ComboBox cbPaciente;
	private Label lblEndereco;
	private Edit edtEndereco;
	private Label lblDtUltRegra;
	private Edit edtDtUltRegra;
	
	private Label lblDtVacina;
	private Label lblDtVacina1;
	private Label lblDtVacina2;
	private Label lblDtVacina3;
	private Label lblDtVacinaR;
	private Edit edtDtVacina1;
	private Edit edtDtVacina2;
	private Edit edtDtVacina3;
	private Edit edtDtVacinaR;
	
	private Label lblDtProvParto;
	private Edit  edtDtProvParto;	
	
	private Button btnPrev;
	private Button btnNext;
	
	/**
	 * Atributos FichaDia*/
	protected static int cdFamilia;
	protected static int cdPaciente;
	protected static String dtUltRegra;
	protected static String dtParto;
	protected static String dtVacina1;
	protected static String dtVacina2;
	protected static String dtVacina3;
	protected static String dtVacinaR;
	protected static String[] flEstadonutri = new String[9];
	protected static String[] dtConsPreNatal= new String[9];
	protected static String[] dtVisita= new String[9];
	protected static String[] cdAno= new String[9];
	
	protected static String flSeisGesta;
	protected static String flNatiMorto;
	protected static String fl36AnosMais;
	protected static String flMenos20;
	protected static String flSangramento;
	protected static String flEdema;
	protected static String flDiabetes;
	protected static String flPressaoAlta;
	
	private int[] chPaciente;
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static FichaGes1Win criarInstancia()
	{
		if(fichaGes1Win == null)
		{
			fichaGes1Win = new FichaGes1Win();
		}
		return fichaGes1Win;
	}
	
	public FichaGes1Win(){
		super("Ficha GES1",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(lblFamilia = new Label("Familia:"),LEFT + 3*(int) ConstantesJanela.FATOR_X,TOP  + 3*(int) ConstantesJanela.FATOR_Y);
		add(cbFamilia = new ComboBox());
		cbFamilia.setRect(AFTER +3*(int) ConstantesJanela.FATOR_X,SAME-1*(int) ConstantesJanela.FATOR_Y,FILL-3*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
	
		add(lblPaciente = new Label("Paciente:"),LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 2*(int) ConstantesJanela.FATOR_Y);
		add(cbPaciente = new ComboBox());
		cbPaciente.setRect(AFTER +3*(int) ConstantesJanela.FATOR_X,SAME-1*(int) ConstantesJanela.FATOR_Y,FILL-3*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		add(lblEndereco = new Label("Endereço:"),LEFT + 3*(int) ConstantesJanela.FATOR_X,AFTER+ 3*(int) ConstantesJanela.FATOR_Y);
		add(edtEndereco = new Edit());
		edtEndereco.setRect(AFTER + 3*(int) ConstantesJanela.FATOR_X,SAME-3*(int) ConstantesJanela.FATOR_Y, FILL -3*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		
		add(lblDtUltRegra = new Label("Data Última Regra:"),LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+2*(int)ConstantesJanela.FATOR_Y);		
		add(edtDtUltRegra = new Edit("99"));
		edtDtUltRegra.setMaxLength(10);
		edtDtUltRegra.setKeyboard(Edit.KBD_CALENDAR);
		edtDtUltRegra.setEditable(false);
		edtDtUltRegra.setRect(AFTER +3*(int) ConstantesJanela.FATOR_X, SAME-2*(int) ConstantesJanela.FATOR_Y,55*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		add(lblDtProvParto =  new Label("Dt Provável Parto:"),LEFT +3*(int) ConstantesJanela.FATOR_X, AFTER+3*(int) ConstantesJanela.FATOR_Y);
		add(edtDtProvParto = new Edit("99/99/9999"));
		edtDtProvParto.setMaxLength(10);
		edtDtProvParto.setKeyboard(Edit.KBD_CALENDAR);
		edtDtProvParto.setEditable(false);
		edtDtProvParto.setRect(AFTER +5*(int) ConstantesJanela.FATOR_X, SAME-2*(int) ConstantesJanela.FATOR_Y,55*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		add(lblDtVacina = new Label("Data da Vacina:"),LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+2*(int)ConstantesJanela.FATOR_Y);
		add(lblDtVacina1 = new Label("1:"),LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+3*(int)ConstantesJanela.FATOR_Y);
		add(edtDtVacina1 = new Edit("99/99/9999"));
		edtDtVacina1.setMode(Edit.DATE);
		edtDtVacina1.setMaxLength(10);
		edtDtVacina1.setKeyboard(Edit.KBD_CALENDAR);	
		edtDtVacina1.setEditable(false);
		edtDtVacina1.setRect(AFTER +2*(int) ConstantesJanela.FATOR_X, SAME-(int) ConstantesJanela.FATOR_Y,55*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		
		add(lblDtVacina2 = new Label("2:"),AFTER+3*(int)ConstantesJanela.FATOR_X, SAME+(int)ConstantesJanela.FATOR_Y);
		add(edtDtVacina2 = new Edit("99/99/9999"));
		edtDtVacina2.setMaxLength(10);
		edtDtVacina2.setKeyboard(Edit.KBD_CALENDAR);
		edtDtVacina2.setEditable(false);
		edtDtVacina2.setRect(AFTER +2*(int) ConstantesJanela.FATOR_X, SAME-2*(int) ConstantesJanela.FATOR_Y,55*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		add(lblDtVacina3 = new Label("3:"),LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+3*(int)ConstantesJanela.FATOR_Y);
		add(edtDtVacina3 = new Edit("99/99/9999"));
		edtDtVacina3.setMaxLength(10);
		edtDtVacina3.setKeyboard(Edit.KBD_CALENDAR);
		edtDtVacina3.setEditable(false);
		edtDtVacina3.setRect(AFTER +2*(int) ConstantesJanela.FATOR_X, SAME-(int) ConstantesJanela.FATOR_Y,55*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		add(lblDtVacinaR = new Label("R:"),AFTER+3*(int)ConstantesJanela.FATOR_X, SAME+(int)ConstantesJanela.FATOR_Y);
		add(edtDtVacinaR = new Edit("99/99/9999"));
		edtDtVacinaR.setMaxLength(10);
		edtDtVacinaR.setKeyboard(Edit.KBD_CALENDAR);
		edtDtVacinaR.setEditable(false);
		edtDtVacinaR.setRect(AFTER +2*(int) ConstantesJanela.FATOR_X, SAME-(int) ConstantesJanela.FATOR_Y,55*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		add(btnPrev = new Button("<<"));
		btnPrev.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 1*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		add(btnNext = new Button(">>"));
		btnNext.setRect(RIGHT - 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 1*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		edtEndereco.setEditable(false);
		if(Settings.isColor)
		{	//Setando as cores se o dispositivos suportar cores.
	    	Color BLUE =ConstantesJanela.BLUE;
		     setForeColor(BLUE);
		     lblFamilia.setForeColor(BLUE);
		     lblPaciente.setForeColor(BLUE);
		     lblEndereco.setForeColor(BLUE);
		     lblDtUltRegra.setForeColor(BLUE);
		     lblDtVacina.setForeColor(BLUE);
		     lblDtProvParto.setForeColor(BLUE);
		     lblDtVacina1.setForeColor(BLUE);
		     lblDtVacina2.setForeColor(BLUE);
		     lblDtVacina3.setForeColor(BLUE);
		     lblDtVacinaR.setForeColor(BLUE);
		     btnNext.setForeColor(BLUE);
		     btnPrev.setForeColor(BLUE);
		}
	}
	
	protected void PopulaForm() {
		String [] itens=null;
		try {
				cbFamilia.removeAll();
				cbPaciente.removeAll();
				FichaANgc fichaANgc = FichaANgc.criarInstancia();			
				ResultSet rsFamilia = fichaANgc.ListaFamiliasAll();
				itens = new String[rsFamilia.getRowCount()];
				int i = 0;
				int indiceFamilia=0;
				int indicePaciente=0;
				while(rsFamilia.next()){
					itens[i] = ""+rsFamilia.getInt("NR_FAMILIA");
					if("U".equals(ListaFichaGesWin.cmd)){
						if(rsFamilia.getInt("NR_FAMILIA")==this.cdFamilia){
							indiceFamilia=i;
						}
					}
					i++;
					if(i==1){
						edtEndereco.setText(rsFamilia.getString("DS_ENDERECO"));						
					}
				}
				if(i>0){
					cbFamilia.add(itens);
					if("N".equals(ListaFichaGesWin.cmd)){
						cbFamilia.setEnabled(true);
				    	cbPaciente.setEnabled(true);	
						cbFamilia.select(0);
					} else if("U".equals(ListaFichaGesWin.cmd)){
						cbFamilia.setEnabled(false);
				    	cbPaciente.setEnabled(false);	
						cbFamilia.select(indiceFamilia);
					}					
					ResultSet rsPaciente = fichaANgc.ListaPacienteAll(Convert.toInt((String) cbFamilia.getItemAt(cbFamilia.getSelectedIndex())));
					itens = new String[rsPaciente.getRowCount()];
					chPaciente= new int[rsPaciente.getRowCount()];
					i = 0;
					while(rsPaciente.next()){
						itens[i] = ""+rsPaciente.getString("NM_PACIENTE");
						chPaciente[i]=rsPaciente.getInt("CD_PACIENTE");
						if("U".equals(ListaFichaGesWin.cmd)){
							if(rsPaciente.getInt("CD_PACIENTE")==this.cdPaciente){
								indicePaciente=i;
							}
						}
						i++;
					}
					if(i>0){
						cbPaciente.add(itens);
						if("U".equals(ListaFichaGesWin.cmd)){							
							cbPaciente.select(indicePaciente);
					    }else{
					    	cbPaciente.select(0);	
					    }
					}
				}
				if("U".equals(ListaFichaGesWin.cmd)){
					ResultSet rs = FichaANgc.criarInstancia().GetPaciente(this.cdFamilia,this.cdPaciente);
					ResultSet rs2 = FichaANgc.criarInstancia().GetFamiliaPaciente(this.cdFamilia);
					ResultSet rsGes = FichaGesNgc.criarInstancia().getFichaGes(this.cdFamilia,this.cdPaciente);
					if(rs.next()){		
						if(rs2.next()){
							edtEndereco.setText(rs2.getString("DS_ENDERECO"));	
						}
					}
					if(rsGes.next()){
						edtDtUltRegra.setText(rsGes.getString("DT_ULTREGRA"));
						edtDtProvParto.setText(rsGes.getString("DT_PARTO"));
						edtDtVacina1.setText(rsGes.getString("DT_VACINA1"));
						edtDtVacina2.setText(rsGes.getString("DT_VACINA2"));
						edtDtVacina3.setText(rsGes.getString("DT_VACINA3"));
						edtDtVacinaR.setText(rsGes.getString("DT_VACINAR"));
					}
				}			
		} catch (Exception e) {
			Vm.debug("Erro: "+e.getMessage());
    		MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+e.getMessage());
			msgBoxErro.popupBlockingModal();
		}		
	}
	
	public void LimpaForm(){
		edtDtUltRegra.setText("");
		edtDtProvParto.setText("");
		edtDtVacina1.setText("");
		edtDtVacina2.setText("");
		edtDtVacina3.setText("");
		edtDtVacinaR.setText("");
	}

    public void onEvent(Event e) {
    	try{
		    if (e.type == ControlEvent.PRESSED) { 
		    	if(e.target==btnNext){
			    		if(ValidaForm()){
			    			PreencheAtributo();
			    		    FichaGes2Win fichaGes2Win = FichaGes2Win.criarInstancia();
			    		    if("N".equals(ListaFichaGesWin.cmd)){
			    		    	fichaGes2Win.LimpaForm(); 
				    		 }else if("U".equals(ListaFichaGesWin.cmd)){
				    			 fichaGes2Win.LimpaForm(); 
				    			 fichaGes2Win.PopulaForm();
				    		 } 
			    		    fichaGes2Win.popupBlockingModal();	
				    		 if("F".equals(ListaFichaGesWin.cmd)){
								   unpop();   
							 }
			    		} 
		    	}else if(e.target==btnPrev){
		    		unpop();
		    	}else if(e.target==cbFamilia){
		    		cbPaciente.removeAll();
		    		FichaANgc fichaANgc = FichaANgc.criarInstancia();	
		    		ResultSet rsPaciente = fichaANgc.ListaPacienteAll(Convert.toInt((String) cbFamilia.getItemAt(cbFamilia.getSelectedIndex())));
		    		ResultSet rsFamilia = fichaANgc.GetFamiliaPaciente(Convert.toInt((String) cbFamilia.getItemAt(cbFamilia.getSelectedIndex())));
					String []itens = new String[rsPaciente.getRowCount()];
					chPaciente= new int[rsPaciente.getRowCount()];
					int i = 0;
					if(rsFamilia.next()){
						edtEndereco.setText(rsFamilia.getString("DS_ENDERECO"));
					}
					while(rsPaciente.next()){
						itens[i] = ""+rsPaciente.getString("NM_PACIENTE");
						chPaciente[i]=rsPaciente.getInt("CD_PACIENTE");
						i++;
					}
					if(i>0){
						cbPaciente.add(itens);
						cbPaciente.select(0);
					}					
		    	}else if(e.target==cbPaciente){
		    		
		    	}			    
		    }else if(e.type == PenEvent.PEN_UP){
		    	 if(e.target==edtDtVacina1){				    	
		    		 edtDtVacina1.popupKCC();
				 }else if(e.target==edtDtVacina2){				    	
		    		 edtDtVacina2.popupKCC();
				 }else if(e.target==edtDtVacina3){				    	
		    		 edtDtVacina3.popupKCC();
				 }else if(e.target==edtDtVacinaR){				    	
		    		 edtDtVacinaR.popupKCC();
				 }else if(e.target==edtDtUltRegra){				    	
					 edtDtUltRegra.popupKCC();
				 }else if(e.target==edtDtProvParto){				    	
					 edtDtProvParto.popupKCC();
				 }
			}		   
    	}catch (Exception error) {
			MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+error.getMessage());
			msgBoxErro.popupBlockingModal();
			Vm.debug("Erro: "+error.getMessage());
		}   
	}  
   
    /**
     *  lê o form e carrrega os atributos 
     */
    private void PreencheAtributo(){
    	this.cdFamilia=Convert.toInt((String) cbFamilia.getItemAt(cbFamilia.getSelectedIndex()));
    	this.cdPaciente=chPaciente[cbPaciente.getSelectedIndex()];
    	this.dtUltRegra = edtDtUltRegra.getText();
    	this.dtParto = edtDtProvParto.getText();
    	this.dtVacina1 = edtDtVacina1.getText();
    	this.dtVacina2 = edtDtVacina2.getText();
    	this.dtVacina3 = edtDtVacina3.getText();
    	this.dtVacinaR = edtDtVacinaR.getText();
    }    
    
    private boolean ValidaForm(){	
    	 MessageBox msgGrid;
    	 try{	if("N".equals(ListaFichaGesWin.cmd)){
	    		    FichaGesNgc fichaGesNgc = FichaGesNgc.criarInstancia();
				  	if(fichaGesNgc.ExistsPacienteGes(Convert.toLong((String) cbFamilia.getItemAt(cbFamilia.getSelectedIndex())),chPaciente[cbPaciente.getSelectedIndex()])){
				  		msgGrid = new MessageBox("Atenção","Paciente Já Cadastrado", new String[]{"OK"});			    	
				    	msgGrid.popupBlockingModal();
				    	msgGrid.setForeColor(ConstantesJanela.BLUE);
				    	return false;
				  	}
    	 		}
    	 }catch (Exception error) {
				MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+error.getMessage());
				msgBoxErro.popupBlockingModal();
				Vm.debug("Erro: "+error.getMessage());
		 }   
	     return true;
	}

}
