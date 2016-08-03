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
import waba.ui.Radio;
import waba.ui.RadioGroup;
import waba.ui.Window;
import br.com.bibiano.siab.business.FichaANgc;
import br.com.bibiano.siab.business.FichaIdosoNgc;
/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class FichaIdoso1Win extends Window {
	private static FichaIdoso1Win fichaIdoso1Win;
	
	private Label lblFamilia;
	private ComboBox cbFamilia;
	private Label lblPaciente;
	private ComboBox cbPaciente;
	private Label lblEndereco;
	private Edit edtEndereco;
	private Label lblSexo;
	private RadioGroup ragSexo;
	private Radio rdMasculino;
	private Radio rdFeminino;	
	private Label lblFumante;
	private RadioGroup ragFumante;
	private Radio rdFumanteSim;
	private Radio rdFumanteNao;	
	private Label lblIdade;
	private Edit  edtIdade;
	private Label lblDtDtp;
	private Edit edtDtDtp;
	private Label lblDtInfluenza;
	private Edit edtDtInfluenza;
	private Label lblDtPneumonococos;
	private Edit edtDtPneumonococos;
	
	
	private Button btnPrev;
	private Button btnNext;
	
	/**
	 * Atributos FichaDia*/
	protected static int cdFamilia;
	protected static int cdPaciente;
	protected static String flFumante;
	protected static String dtDtp;
	protected static String dtInfluenza;
	protected static String dtPneumonococos;
	
	protected static String flhipertensao;
	protected static String flTuberculose;
	protected static String flAlzheimer;
	protected static String flDefFisica;
	protected static String flHanseniase;
	protected static String flMalParkson;
	protected static String flCancer;
	protected static String flAcamado;
	protected static String flInternamentos;
	protected static String flSequelasAvc;
	protected static String flAlcoolatra;
	
	private int[] chPaciente;
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static FichaIdoso1Win criarInstancia()
	{
		if(fichaIdoso1Win == null)
		{
			fichaIdoso1Win = new FichaIdoso1Win();
		}
		return fichaIdoso1Win;
	}
	
	public FichaIdoso1Win(){
		super("Ficha IDOSO1",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(lblFamilia = new Label("Familia:"),LEFT + 3*(int) ConstantesJanela.FATOR_X,TOP  + 3*(int) ConstantesJanela.FATOR_Y);
		add(cbFamilia = new ComboBox());
		cbFamilia.setRect(AFTER +3*(int) ConstantesJanela.FATOR_X,SAME-1*(int) ConstantesJanela.FATOR_Y,FILL-3*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
	
		add(lblPaciente = new Label("Paciente:"),LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 2*(int) ConstantesJanela.FATOR_Y);
		add(cbPaciente = new ComboBox());
		cbPaciente.setRect(AFTER +3*(int) ConstantesJanela.FATOR_X,SAME-1*(int) ConstantesJanela.FATOR_Y,FILL-3*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		add(lblEndereco = new Label("Endereço:"),LEFT + 3*(int) ConstantesJanela.FATOR_X,AFTER+ 3*(int) ConstantesJanela.FATOR_Y);
		add(edtEndereco = new Edit());
		edtEndereco.setRect(AFTER + 1*(int) ConstantesJanela.FATOR_X,SAME-3*(int) ConstantesJanela.FATOR_Y, FILL -3*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		edtEndereco.setEnabled(false);
		
		add(lblSexo = new Label("Sexo:"),LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+3*(int)ConstantesJanela.FATOR_Y);		
		ragSexo = new RadioGroup();
		add(rdMasculino = new Radio("MASC",ragSexo),AFTER +(int) ConstantesJanela.FATOR_X,SAME);
		add(rdFeminino = new Radio("FEM",ragSexo),AFTER +6*(int) ConstantesJanela.FATOR_X,SAME);
		rdMasculino.setEnabled(false);
		rdFeminino.setEnabled(false);
		
		add(lblIdade =  new Label("Idade:"),AFTER +3*(int) ConstantesJanela.FATOR_X, SAME);				
		add(edtIdade = new Edit("99"));
		edtIdade.setMaxLength(3);
		edtIdade.setRect(AFTER +2*(int) ConstantesJanela.FATOR_X, SAME-2*(int) ConstantesJanela.FATOR_Y,20*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		edtIdade.setEnabled(false);
		
		add(lblFumante = new Label("Fumante:"),LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+2*(int)ConstantesJanela.FATOR_Y);
		ragFumante = new RadioGroup();
		add(rdFumanteSim = new Radio("Sim",ragFumante),AFTER +3*(int) ConstantesJanela.FATOR_X,SAME);
		add(rdFumanteNao = new Radio("Não",ragFumante),AFTER +6*(int) ConstantesJanela.FATOR_X,SAME);
		
		add(lblDtDtp= new Label("Data DTP:"),LEFT + 3*(int) ConstantesJanela.FATOR_X,AFTER+3*(int)ConstantesJanela.FATOR_Y);
		add(edtDtDtp = new Edit("99/99/9999"));
		edtDtDtp.setMaxLength(10);
		edtDtDtp.setKeyboard(Edit.KBD_CALENDAR);
		edtDtDtp.setEditable(false);
		edtDtDtp.setRect(AFTER +2*(int) ConstantesJanela.FATOR_X, SAME-2*(int) ConstantesJanela.FATOR_Y,55*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		add(lblDtInfluenza= new Label("DT Influenza:"),LEFT + 3*(int) ConstantesJanela.FATOR_X,AFTER+3*(int)ConstantesJanela.FATOR_Y);
		add(edtDtInfluenza = new Edit("99/99/9999"));
		edtDtInfluenza.setMaxLength(10);
		edtDtInfluenza.setKeyboard(Edit.KBD_CALENDAR);
		edtDtInfluenza.setEditable(false);
		edtDtInfluenza.setRect(AFTER +2*(int) ConstantesJanela.FATOR_X, SAME-2*(int) ConstantesJanela.FATOR_Y,55*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		add(lblDtPneumonococos= new Label("DT Pneumonococos:"),LEFT + 3*(int) ConstantesJanela.FATOR_X,AFTER+3*(int)ConstantesJanela.FATOR_Y);
		add(edtDtPneumonococos = new Edit("99/99/9999"));
		edtDtPneumonococos.setMaxLength(10);
		edtDtPneumonococos.setKeyboard(Edit.KBD_CALENDAR);
		edtDtPneumonococos.setEditable(false);
		edtDtPneumonococos.setRect(AFTER +2*(int) ConstantesJanela.FATOR_X, SAME-2*(int) ConstantesJanela.FATOR_Y,55*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		
		add(btnPrev = new Button("<<"));
		btnPrev.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 1*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		add(btnNext = new Button(">>"));
		btnNext.setRect(RIGHT - 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 1*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		if(Settings.isColor)
		{	//Setando as cores se o dispositivos suportar cores.
	    	Color BLUE =ConstantesJanela.BLUE;
		     setForeColor(BLUE);
		     lblFamilia.setForeColor(BLUE);
		     lblPaciente.setForeColor(BLUE);
		     lblEndereco.setForeColor(BLUE);
		     lblSexo.setForeColor(BLUE);
		     lblIdade.setForeColor(BLUE);
		     lblFumante.setForeColor(BLUE);
		     lblDtDtp.setForeColor(BLUE);
		     lblDtInfluenza.setForeColor(BLUE);
		     lblDtPneumonococos.setForeColor(BLUE);
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
				if("U".equals(ListaFichaIdosoWin.cmd)){
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
				if("N".equals(ListaFichaIdosoWin.cmd)){
					cbFamilia.setEnabled(true);
			    	cbPaciente.setEnabled(true);	
					cbFamilia.select(0);
				} else if("U".equals(ListaFichaIdosoWin.cmd)){
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
					if("U".equals(ListaFichaIdosoWin.cmd)){
						if(rsPaciente.getInt("CD_PACIENTE")==this.cdPaciente){
							indicePaciente=i;
						}
					}
					if(i==0){					
						if(rsPaciente.getString("FL_SEXO").equals("M")){
							ragSexo.setSelectedIndex(0);
						}else{
							ragSexo.setSelectedIndex(1);
						}
						edtIdade.setText(""+rsPaciente.getInt("NUM_IDADE"));
					}
					i++;
				}
				if(i>0){
					cbPaciente.add(itens);
					if("U".equals(ListaFichaIdosoWin.cmd)){							
						cbPaciente.select(indicePaciente);
				    }else{
				    	cbPaciente.select(0);	
				    }
				}
			}
			if("U".equals(ListaFichaIdosoWin.cmd)){
				ResultSet rs = FichaANgc.criarInstancia().GetPaciente(this.cdFamilia,this.cdPaciente);
				ResultSet rs2 = FichaANgc.criarInstancia().GetFamiliaPaciente(this.cdFamilia);
				ResultSet rsIdoso = FichaIdosoNgc.criarInstancia().getFichaIdoso(this.cdFamilia,this.cdPaciente);				 
				if(rs.next()){		
					if(rs2.next()){
						edtEndereco.setText(rs2.getString("DS_ENDERECO"));	
					}
					edtIdade.setText(""+rs.getInt("NUM_IDADE"));
					if (rs.getString("FL_SEXO").equals("M")){
						ragSexo.setSelectedIndex(0);						
					}else{
						ragSexo.setSelectedIndex(1);
					}
					if(rsIdoso.next()){
						if ("S".equals(rsIdoso.getString("FL_FUMANTE"))){
							ragFumante.setSelectedIndex(0);						
						}else{
							ragFumante.setSelectedIndex(1);
						}		
						edtDtDtp.setText(rsIdoso.getString("DT_DTP"));
						edtDtInfluenza.setText(rsIdoso.getString("DT_INFLUENZA"));
						edtDtPneumonococos.setText(rsIdoso.getString("DT_PNEUMONOCOCOS"));
					}
				}
			}
		} catch (Exception e) {
			Vm.debug("Erro: "+e.getMessage());
    		MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+e.getMessage());
			msgBoxErro.popupBlockingModal();
		}		
	}
	
	public void LimpaForm(){
		rdFumanteSim.setChecked(false);
		rdFumanteNao.setChecked(false);
		edtDtDtp.setText("");
		edtDtInfluenza.setText("");
		edtDtPneumonococos.setText("");
	}

    public void onEvent(Event e) {
    	try{
		    if (e.type == ControlEvent.PRESSED) { 
		    	if(e.target==btnNext){
		    		if(ValidaForm()){
		    			PreencheAtributo();
		    		    FichaIdoso2Win fichaIdoso2Win = FichaIdoso2Win.criarInstancia();
		    		    if("N".equals(ListaFichaIdosoWin.cmd)){
		    		    	fichaIdoso2Win.LimpaForm(); 
			    		 }else if("U".equals(ListaFichaIdosoWin.cmd)){
			    			 fichaIdoso2Win.PopulaForm();
			    		 } 
		    		    fichaIdoso2Win.popupBlockingModal();	
			    		 if("F".equals(ListaFichaIdosoWin.cmd)){
							   unpop();   
						 }
		    		} 
		    	}else if(e.target==btnPrev){
		    		unpop();
		    	}else if(e.target==cbFamilia){
		    		cbPaciente.removeAll();
		    		FichaANgc fichaANgc = FichaANgc.criarInstancia();
		    		int vFamilia =Convert.toInt((String) cbFamilia.getItemAt(cbFamilia.getSelectedIndex()));
		    		ResultSet rsPaciente = fichaANgc.ListaPacienteAll(vFamilia);
					String []itens = new String[rsPaciente.getRowCount()];
					chPaciente= new int[rsPaciente.getRowCount()];
					int i = 0;
					while(rsPaciente.next()){
						itens[i] = ""+rsPaciente.getString("NM_PACIENTE");
						chPaciente[i]=rsPaciente.getInt("CD_PACIENTE");
						i++;
					}
					if(i>0){
						cbPaciente.add(itens);
						cbPaciente.select(0);
						if(rsPaciente.getString("FL_SEXO").equals("M")){
							ragSexo.setSelectedIndex(0);
						}else{
							ragSexo.setSelectedIndex(1);
						}
						edtIdade.setText(""+rsPaciente.getInt("NUM_IDADE"));
					}
					ResultSet rsFamilia =fichaANgc.GetFamiliaPaciente(vFamilia);
					if(rsFamilia.next()){
		    			edtEndereco.setText(rsFamilia.getString("DS_ENDERECO"));
		    		}else{
		    			edtEndereco.setText("");
		    		}
		    	}else if(e.target==cbPaciente){
		    		FichaANgc fichaANgc = FichaANgc.criarInstancia();	
		    		ResultSet rsPaciente =fichaANgc.GetPaciente(Convert.toInt((String) cbFamilia.getItemAt(cbFamilia.getSelectedIndex())),chPaciente[cbPaciente.getSelectedIndex()]);
		    		if(rsPaciente.next()){
		    			if(rsPaciente.getString("FL_SEXO").equals("M")){
							ragSexo.setSelectedIndex(0);
						}else{
							ragSexo.setSelectedIndex(1);
						}
						edtIdade.setText(""+rsPaciente.getInt("NUM_IDADE"));
		    		}
		    	}
		    }else if(e.type == PenEvent.PEN_UP){
		    	 if(e.target==edtDtDtp){				    	
		    		 edtDtDtp.popupKCC();
				 }else if(e.target==edtDtInfluenza){				    	
					 edtDtInfluenza.popupKCC();
				 }else if(e.target==edtDtPneumonococos){				    	
					 edtDtPneumonococos.popupKCC();
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
    	if(ragFumante.getSelectedIndex() ==0){
			  this.flFumante="S";  
		  }else{
			  this.flFumante="N";
		}	
    	this.dtDtp = edtDtDtp.getText();
    	this.dtInfluenza = edtDtInfluenza.getText();
    	this.dtPneumonococos = edtDtPneumonococos.getText();
    }
    
   
    
    private boolean ValidaForm(){
		  MessageBox msgGrid;
		  try{		if("N".equals(ListaFichaIdosoWin.cmd)){
					    FichaIdosoNgc fichaIdosoNgc = FichaIdosoNgc.criarInstancia();
					  	if(fichaIdosoNgc.ExistsPacienteIdoso(Convert.toInt((String) cbFamilia.getItemAt(cbFamilia.getSelectedIndex())),chPaciente[cbPaciente.getSelectedIndex()])){
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
