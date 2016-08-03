package br.com.bibiano.siab.window;

import litebase.ResultSet;
import waba.fx.Color;
import waba.sys.Convert;
import waba.sys.Settings;
import waba.sys.Vm;
import waba.ui.Button;
import waba.ui.Check;
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
import br.com.bibiano.siab.util.BBDate;
/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class PacienteWin extends Window {
	private static PacienteWin pacienteWin;
	private Label lblNome;
	private Edit  edtNome;
	private Label lblDtNasc;
	private Edit  edtDtNasc;
	private Label lblIdade;
	private Edit  edtIdade;
	private Label lblSexo;
	private RadioGroup ragSexo;
	private Radio rdMasculino;
	private Radio rdFeminino;
	private Label lblAlfabetizado;
	private Label lblfreqEscola;
	private RadioGroup ragAlfabetizado;
	private Radio rdAlfabetSim;
	private Radio rdAlfabetnao;
	private Label lblOcupacao;
	//private Edit edtOcupacao;
	private ComboBox cbOcupacao;	
	private Label lbl15Mais;
	private Check chk15Mais;
	private Button btnCancel;
	private Button btnNext;
	public static String cmd; 
	//private long cdPaciente;
	
	private int[] chOcupacao;
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	
	
	/**
	 * Atributos Ficha  Paciente*/
	protected static int cdFamilia;
	protected static int cdPaciente;
	protected static String nmPaciente;
	protected static String dtNascimento;
	protected static int numIdade;
	protected static String flSexo;
	protected static String flMenorQuinze;
	protected static String flAlfabetizado;
	protected static String flFreqEscola;
	protected static int cdOcupacao;
	
	public static PacienteWin criarInstancia()
	{
		if(pacienteWin == null)
		{
			pacienteWin = new PacienteWin();
		}
		return pacienteWin;
	}
	
	public PacienteWin(){
		super("Cadastro Paciente",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(lblNome = new Label("Nome:"));
		lblNome.setRect(LEFT+5*(int)ConstantesJanela.FATOR_X, TOP+5*(int)ConstantesJanela.FATOR_Y,28*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
	
		add(edtNome =  new Edit());
		edtNome.setRect(AFTER +(int) ConstantesJanela.FATOR_X, SAME-3*(int) ConstantesJanela.FATOR_Y, FILL -5*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		add(lblDtNasc =  new Label("Dt. Nasc.:"));
		lblDtNasc.setRect(LEFT+5*(int)ConstantesJanela.FATOR_X, AFTER+7*(int)ConstantesJanela.FATOR_Y,40*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		add(edtDtNasc =  new Edit("99/99/9999"));
		edtDtNasc.setMode(Edit.DATE);
		edtDtNasc.setKeyboard(Edit.KBD_NONE);
		edtDtNasc.setMaxLength(10);
		//edtDtNasc.setEditable(false);
		edtDtNasc.setRect(AFTER +(int) ConstantesJanela.FATOR_X, SAME-3*(int) ConstantesJanela.FATOR_Y,55*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
	
		add(lblIdade =  new Label("Idade:"));
		lblIdade.setRect(AFTER +3*(int) ConstantesJanela.FATOR_X, SAME+2*(int) ConstantesJanela.FATOR_Y,25*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		add(edtIdade = new Edit("99"));
		edtIdade.setMaxLength(3);
		edtIdade.setValidChars("1234567890");
		edtIdade.setEditable(false);
		edtIdade.setRect(AFTER +2*(int) ConstantesJanela.FATOR_X, SAME-4*(int) ConstantesJanela.FATOR_Y,20*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		add(lblSexo = new Label("Sexo:"));
		lblSexo.setRect(LEFT+5*(int)ConstantesJanela.FATOR_X, AFTER+7*(int)ConstantesJanela.FATOR_Y,25*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		ragSexo = new RadioGroup();
		add(rdMasculino = new Radio("MASC",ragSexo),AFTER +(int) ConstantesJanela.FATOR_X,SAME+2*(int) ConstantesJanela.FATOR_Y);
		add(rdFeminino = new Radio("FEM",ragSexo),AFTER +6*(int) ConstantesJanela.FATOR_X,SAME);
		
		add(lbl15Mais = new Label("Com 15 anos ou mais:"),LEFT+5*(int)ConstantesJanela.FATOR_X, AFTER+6*(int)ConstantesJanela.FATOR_Y);
		add(chk15Mais = new Check("Sim"),AFTER+2*(int)ConstantesJanela.FATOR_X,SAME+(int)ConstantesJanela.FATOR_Y);
		chk15Mais.setEnabled(false);
		
		add(lblAlfabetizado = new Label("Alfabetizado:"),LEFT+5*(int)ConstantesJanela.FATOR_X,AFTER+7*(int)ConstantesJanela.FATOR_Y);
		ragAlfabetizado = new RadioGroup();
		add(rdAlfabetSim = new Radio("Sim",ragAlfabetizado),AFTER +(int) ConstantesJanela.FATOR_X,SAME-1*(int) ConstantesJanela.FATOR_Y);
		add(rdAlfabetnao = new Radio("Não",ragAlfabetizado),AFTER +6*(int) ConstantesJanela.FATOR_X,SAME);
		lblAlfabetizado.setVisible(false);
		
		add(lblfreqEscola = new Label("Freq. Escola:"),LEFT+5*(int)ConstantesJanela.FATOR_X,AFTER+7*(int)ConstantesJanela.FATOR_Y,chk15Mais);

		add(lblOcupacao = new Label("Ocupação:"),LEFT+5*(int)ConstantesJanela.FATOR_X, AFTER+6*(int)ConstantesJanela.FATOR_Y);
		add(cbOcupacao = new ComboBox());
		//edtOcupacao.setRect(AFTER +(int) ConstantesJanela.FATOR_X, SAME-5*(int) ConstantesJanela.FATOR_Y, FILL -5*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		cbOcupacao.setRect(LEFT+5*(int) ConstantesJanela.FATOR_X,AFTER+5*(int) ConstantesJanela.FATOR_Y,FILL-3*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		
		add(btnCancel = new Button("<<"));
		btnCancel.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		add(btnNext = new Button(">>"));
		btnNext.setRect(RIGHT - 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		if(Settings.isColor)
		{	//Setando as cores se o dispositivos suportar cores.
	    	Color BLUE =ConstantesJanela.BLUE;
		     setForeColor(BLUE);
		     lblNome.setForeColor(BLUE);
		     lblDtNasc.setForeColor(BLUE);
		     lblIdade.setForeColor(BLUE);
		     lblSexo.setForeColor(BLUE);
		     lbl15Mais.setForeColor(BLUE);
		     lblAlfabetizado.setForeColor(BLUE);
		     lblOcupacao.setForeColor(BLUE);		     
		     lblfreqEscola.setForeColor(BLUE);		    
		     btnCancel.setForeColor(BLUE);
		     btnNext.setForeColor(BLUE);
		}
	}
	
	public void PopulaForm(int cdFamilia,int cdPaciente){
		FichaANgc fichaANgc = FichaANgc.criarInstancia();
		ResultSet rs =fichaANgc.GetPaciente(cdFamilia,cdPaciente);
		ResultSet rsOcupacao = fichaANgc.GetOcupacaoAll();
		
		
		String [] itens = new String[rsOcupacao.getRowCount()];
		chOcupacao= new int[rsOcupacao.getRowCount()];
		int i = 0;
		while (rsOcupacao.next()){
			chOcupacao[i]= rsOcupacao.getInt("CD_OCUPACAO");
			itens[i] = ""+rsOcupacao.getString("DS_OCUPACAO");
			i++;				
		}
		if(i>0)
		cbOcupacao.add(itens);
		
		if(rs.next()){
			edtNome.setText(rs.getString("NM_PACIENTE"));
			edtDtNasc.setText(rs.getString("DT_NASCIMENTO"));
			//int idade = BBDate.diffBetweenDates(BBDate.valueOf(rs.getString("DT_NASCIMENTO")),new BBDate(),BBDate.SSK_DIFF_YEARS);
			int idade =BBDate.calculaIdade(rs.getString("DT_NASCIMENTO"));
			edtIdade.setText(""+idade);
			if(rs.getString("FL_SEXO").equals("M")){
				ragSexo.setSelectedIndex(0);
			}else{
				ragSexo.setSelectedIndex(1);
			}
			if(rs.getString("FL_MENORQUINZE").equals("S")){
				chk15Mais.setChecked(false);
				lblAlfabetizado.setVisible(false);
	    		lblfreqEscola.setVisible(true);
			}else{
				chk15Mais.setChecked(true);
				lblAlfabetizado.setVisible(true);
	    		lblfreqEscola.setVisible(false);
			}
			if(rs.getString("FL_ALFABETIZADO").equals("S")){
				ragAlfabetizado.setSelectedIndex(0);
			}else{
				ragAlfabetizado.setSelectedIndex(1);
			}
			for(int b=0;b<chOcupacao.length;b++){
				if(chOcupacao[b]==rs.getInt("CD_OCUPACAO")){
					cbOcupacao.select(b);
				}
			}			
			for(int f=0; f<chOcupacao.length; f++){
				if(rs.getInt("CD_OCUPACAO")==chOcupacao[f]){
					cbOcupacao.select(f);
					break;
				}
			}
			this.cdPaciente =cdPaciente;
		}
		/*if("U".equals(this.cmd)){
			edtDtNasc.setEnabled(false);			
		}else{
			edtDtNasc.setEnabled(true);
		}*/
	}
	
	protected void LimpaForm(){
		edtNome.setText("");
		edtDtNasc.setText("");
		edtIdade.setText("");		
		ragSexo.setSelectedIndex(0);
		chk15Mais.setChecked(false);
		ragAlfabetizado.setSelectedIndex(0);
		cbOcupacao.removeAll();		
	}

    public void onEvent(Event e) {	
    	try{
		    if (e.type == ControlEvent.PRESSED) { 
			    if(e.target == btnNext) {
			    	if(ValidaForm()){
			    			PreencheAtributo();
			    			Paciente2Win paciente2Win = Paciente2Win.criarInstancia();			    			
			    			 if("I".equals(this.cmd)){
			    				  paciente2Win.LimpaForm(); 
					    	 }else if("U".equals(this.cmd)){
					    		  paciente2Win.LimpaForm();
					    		  paciente2Win.PopulaForm();
					    	 } 
			    			 paciente2Win.popupBlockingModal();
					    	 if("F".equals(this.cmd)){
								  unpop();   
							 }
					    	
					    	/*Hashtable pacienteVo = new Hashtable(10);
					    	pacienteVo.put("CD_FAMILIA",""+ListaPacientesWin.cdFamilia);
					    	if(cmd.equals("I")){
					    		pacienteVo.put("CD_PACIENTE",""+FichaANgc.criarInstancia().getCdPacienteNew(ListaPacientesWin.cdFamilia)); 
					    	}else{
					    		pacienteVo.put("CD_PACIENTE",""+cdPaciente);
					    	}
					    	pacienteVo.put("NM_PACIENTE", edtNome.getText().toUpperCase()); 
					    	pacienteVo.put("DT_NASCIMENTO",edtDtNasc.getText()); 
					    	pacienteVo.put("NUM_IDADE",""+edtIdade.getText());
					    	if(ragSexo.getSelectedIndex()==0){
					    		pacienteVo.put("FL_SEXO","M");	
					    	}else{
					    		pacienteVo.put("FL_SEXO","F");
					    	}
					    	if(chk15Mais.getChecked()){
					    		pacienteVo.put("FL_MENORQUINZE","N");	
					    		if(ragAlfabetizado.getSelectedIndex()==0){
						    		pacienteVo.put("FL_ALFABETIZADO","S");	
						    	}else{
						    		pacienteVo.put("FL_ALFABETIZADO","N");
						    	}	
					    		pacienteVo.put("FL_FREQESCOLA","");
					    	}else{
					    		pacienteVo.put("FL_MENORQUINZE","S");	
					    		if(ragAlfabetizado.getSelectedIndex()==0){
						    		pacienteVo.put("FL_FREQESCOLA","S");	
						    	}else{
						    		pacienteVo.put("FL_FREQESCOLA","N");
						    	}	
					    		pacienteVo.put("FL_ALFABETIZADO","");
					    	}	*/		    	
					    	//pacienteVo.put("DS_OCUPACAO",edtOcupacao.getText());
					    	/*pacienteVo.put("CD_OCUPACAO",chOcupacao[cbOcupacao.getSelectedIndex()]);
					    	pacienteVo.put("CD_DOENCA",edtDoenca.getText());*/
					    	
					    	
					    	/*FichaANgc fichaANgc = new FichaANgc();
					    	if(cmd.equals("I")){
					    		fichaANgc.InsertePaciente(pacienteVo);	
					    	}else if(cmd.equals("U")){
					    		fichaANgc.UpdatePaciente(pacienteVo);
					    	}
					    	unpop();*/
			    	}	
			    }else if(e.target==btnCancel){
			    	unpop();
			    }else if(e.target==chk15Mais){
			    	if(chk15Mais.getChecked()){
			    		lblAlfabetizado.setVisible(true);
			    		lblfreqEscola.setVisible(false);
			    	}else{
			    		lblAlfabetizado.setVisible(false);
			    		lblfreqEscola.setVisible(true);
			    	}
			    }
		    }else if(e.type == PenEvent.PEN_UP){
		    	 if(e.target==edtDtNasc){
			    	//edtDtNasc.popupKCC();				    	
			    }if(e.target==edtIdade){
			    	if(!"".equals(edtDtNasc.getText())){
			    		//int idade = BBDate.diffBetweenDates(BBDate.valueOf(edtDtNasc.getText()),new BBDate(),BBDate.SSK_DIFF_YEARS);
			    		int idade = BBDate.calculaIdade(edtDtNasc.getText());
						edtIdade.setText(""+idade);
						chk15Mais.setEnabled(true);
						if(idade>=15){
							chk15Mais.setChecked(true);
						}else{
							chk15Mais.setChecked(false);
						}						
						if(chk15Mais.getChecked()){
				    		lblAlfabetizado.setVisible(true);
				    		lblfreqEscola.setVisible(false);
				    	}else{
				    		lblAlfabetizado.setVisible(false);
				    		lblfreqEscola.setVisible(true);
				    	}
						chk15Mais.setEnabled(false);
			    	}	
			    }
		    }else if (e.type == ControlEvent.FOCUS_OUT) { 
					    if(e.target == edtDtNasc) {
					    	if(!edtDtNasc.getText().equals("")){
					    		if(!BBDate.isDataValida(edtDtNasc.getText())){					    			
					    		//	(new MessageBox("Erro","Data Inválida (DD/MM/AAAA) ")).popupBlockingModal();					    			
					    			return;
					    		}
					    		
					    		//int idade = BBDate.diffBetweenDates(BBDate.valueOf(edtDtNasc.getText()),new BBDate(),BBDate.SSK_DIFF_YEARS);
					    		int idade = BBDate.calculaIdade(edtDtNasc.getText());
								//edtIdade.setText(""+rs.getInt("NUM_IDADE"));
								edtIdade.setText(""+idade);
								chk15Mais.setEnabled(true);
								if(idade>=15){
									chk15Mais.setChecked(true);
								}else{
									chk15Mais.setChecked(false);
								}
								if(chk15Mais.getChecked()){
						    		lblAlfabetizado.setVisible(true);
						    		lblfreqEscola.setVisible(false);
						    	}else{
						    		lblAlfabetizado.setVisible(false);
						    		lblfreqEscola.setVisible(true);
						    	}
								chk15Mais.setEnabled(false);
					    	}			    	
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
     * @throws Exception 
     */
    private void PreencheAtributo() throws Exception{
    	this.cdFamilia=ListaPacientesWin.cdFamilia;    	
    	if(cmd.equals("I")){
    		this.cdPaciente=FichaANgc.criarInstancia().getCdPacienteNew(ListaPacientesWin.cdFamilia); 
    	}else{
    		this.cdPaciente=cdPaciente;
    	}
    	
    	this.nmPaciente=edtNome.getText();
    	this.dtNascimento=edtDtNasc.getText();
    	this.numIdade=Convert.toInt(edtIdade.getText());
    	
    	if(ragSexo.getSelectedIndex()==0){
    		this.flSexo="M";
    	}else
    		this.flSexo="F";
    	if(chk15Mais.getChecked()){
    		this.flMenorQuinze = "N";	
    		if(ragAlfabetizado.getSelectedIndex()==0){
    			this.flAlfabetizado="S";	
	    	}else{
	    		this.flAlfabetizado = "N";
	    	}	
    		this.flFreqEscola ="";
    	}else{
    		this.flMenorQuinze="S";	
    		if(ragAlfabetizado.getSelectedIndex()==0){
    			this.flFreqEscola = "S";	
	    	}else{
	    		this.flFreqEscola = "N";
	    	}	
    		this.flAlfabetizado="";
    	}
    	this.cdOcupacao  = chOcupacao[cbOcupacao.getSelectedIndex()];    	   	
    }
    
    private boolean ValidaForm(){
		  MessageBox msgGrid;
		  try{  
			    if("".equals(edtNome.getText())){
			    	msgGrid = new MessageBox("Atenção","Preencha o Campo Nome", new String[]{"OK"});			    	
			    	msgGrid.popupBlockingModal();
			    	msgGrid.setForeColor(ConstantesJanela.BLUE);
			    	return false;
			    }
			    if("".equals(edtDtNasc.getText())){
			    	msgGrid = new MessageBox("Atenção","Preencha o Campo| Data Nascimento", new String[]{"OK"});			    	
			    	msgGrid.popupBlockingModal();
			    	msgGrid.setForeColor(ConstantesJanela.BLUE);
			    	return false;
			    }
				if("".equals(edtIdade.getText())){
					msgGrid = new MessageBox("Atenção","Preencha o Campo| Idade", new String[]{"OK"});			    	
			    	msgGrid.popupBlockingModal();
			    	msgGrid.setForeColor(ConstantesJanela.BLUE);
			    	return false;
				}	
				if(cbOcupacao.getSelectedIndex()==-1){
					msgGrid = new MessageBox("Atenção","Selecione o Campo| Ocupação", new String[]{"OK"});			    	
			    	msgGrid.popupBlockingModal();
			    	msgGrid.setForeColor(ConstantesJanela.BLUE);
			    	return false;
				}
				if(!BBDate.isDataValida(edtDtNasc.getText())){					    			
	    			(new MessageBox("Erro","Data Inválida (DD/MM/AAAA) ")).popupBlockingModal();					    			
	    			return false;
	    		}
		  }catch (Exception error) {
				MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+error.getMessage());
				msgBoxErro.popupBlockingModal();
				Vm.debug("Erro: "+error.getMessage());	
		 }   
		  return true;
	  }  
}
