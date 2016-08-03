package br.com.bibiano.siab.window;

import litebase.ResultSet;
import waba.fx.Color;
import waba.sys.Convert;
import waba.sys.Settings;
import waba.sys.Vm;
import waba.ui.Button;
import waba.ui.Calendar;
import waba.ui.ComboBox;
import waba.ui.ControlEvent;
import waba.ui.Edit;
import waba.ui.Event;
import waba.ui.Label;
import waba.ui.MessageBox;
import waba.ui.Radio;
import waba.ui.RadioGroup;
import waba.ui.Window;
import waba.util.Date;
import wextlib.ui.grid.BBGrid;
import wextlib.ui.grid.Grid;
import wextlib.ui.grid.GridEvent;
import wextlib.ui.grid.GridStorageSingleHashtable;
import br.com.bibiano.siab.business.FichaANgc;
import br.com.bibiano.siab.business.FichaDiaNgc;
import br.com.bibiano.siab.business.SIABBusiness;
import br.com.bibiano.siab.util.BBDate;
/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class FichaDia1Win extends Window {
	private static FichaDia1Win fichaDia1Win;
	
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
	private Label lblIdade;
	private Edit  edtIdade;
	private Grid gridFichaDia;
	
	private Button btnPrev;
	private Button btnNext;
	Calendar  calendar;
	
	/**
	 * Atributos FichaDia*/
	protected static int cdFamilia;
	protected static int cdPaciente;
	protected static String dsObservacao;
	protected static String dtVisita;
	protected static String flDieta;
	protected static String flExercicio;
	protected static String flInsulina;
	protected static String flHipoglicemiante;
	protected static String dtUltConsulta;
	
	private int[] chPaciente;
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static FichaDia1Win criarInstancia()
	{
		if(fichaDia1Win == null)
		{
			fichaDia1Win = new FichaDia1Win();
		}
		return fichaDia1Win;
	}
	
	public FichaDia1Win(){
		
		super("Ficha DIA1",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(lblFamilia = new Label("Familia:"),LEFT + 3*(int) ConstantesJanela.FATOR_X,TOP  + 1*(int) ConstantesJanela.FATOR_Y);
		add(cbFamilia = new ComboBox());
		cbFamilia.setRect(AFTER +3*(int) ConstantesJanela.FATOR_X,SAME-1*(int) ConstantesJanela.FATOR_Y,FILL-3*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
	
		add(lblPaciente = new Label("Paciente:"),LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 2*(int) ConstantesJanela.FATOR_Y);
		add(cbPaciente = new ComboBox());
		cbPaciente.setRect(AFTER +2*(int) ConstantesJanela.FATOR_X,SAME-1*(int) ConstantesJanela.FATOR_Y,FILL-3*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		add(lblEndereco = new Label("Endereço:"),LEFT + 3*(int) ConstantesJanela.FATOR_X,AFTER+ 2*(int) ConstantesJanela.FATOR_Y);
		add(edtEndereco = new Edit());
		edtEndereco.setRect(AFTER + 1*(int) ConstantesJanela.FATOR_X,SAME-1*(int) ConstantesJanela.FATOR_Y, FILL -3*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		edtEndereco.setEditable(false);	
		
		add(lblSexo = new Label("Sexo:"),LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+2*(int)ConstantesJanela.FATOR_Y);		
		ragSexo = new RadioGroup();
		add(rdMasculino = new Radio("MASC",ragSexo),AFTER +(int) ConstantesJanela.FATOR_X,SAME);
		add(rdFeminino = new Radio("FEM",ragSexo),AFTER +6*(int) ConstantesJanela.FATOR_X,SAME);
		rdMasculino.setEnabled(false);
		rdFeminino.setEnabled(false);
		
		add(lblIdade =  new Label("Idade:"),AFTER +3*(int) ConstantesJanela.FATOR_X, SAME+1*(int) ConstantesJanela.FATOR_Y);		
		add(edtIdade = new Edit("99"));
		edtIdade.setMaxLength(3);
		edtIdade.setRect(AFTER +2*(int) ConstantesJanela.FATOR_X, SAME-3*(int) ConstantesJanela.FATOR_Y,20*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		edtIdade.setEditable(false);
		
		add(gridFichaDia = new BBGrid());
		gridFichaDia.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER + 1*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 67*(int) ConstantesJanela.FATOR_Y);
		gridFichaDia.setStorage(new GridStorageSingleHashtable());
		gridFichaDia.setAllowSortRows(false);
		gridFichaDia.setAllowSortCols(false);
		gridFichaDia.setAllowUserResizeRows(false);
		gridFichaDia.setVerticalScrollVisible(false);
		gridFichaDia.setHighlightSelectedCell(true);
		gridFichaDia.setRowCount(4);
		gridFichaDia.setColCount(13);
		gridFichaDia.setFixedRowCount(1);
		gridFichaDia.setFixedColCount(1);
		gridFichaDia.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);
		gridFichaDia.setColSeparatorWidth(1);
		gridFichaDia.setRowSeparatorHeight(1);
		gridFichaDia.setDrawColSeparators(true);
		gridFichaDia.setDrawRowSeparators(true);
		gridFichaDia.setUpdating(false);
		gridFichaDia.setFullRowSelectionDisplay(true);
		gridFichaDia.setColWidth(0,60*(int)ConstantesJanela.FATOR_X);
		gridFichaDia.setColWidth(1,42*(int)ConstantesJanela.FATOR_X);
		gridFichaDia.setColWidth(2,42*(int)ConstantesJanela.FATOR_X);
		gridFichaDia.setColWidth(3,42*(int)ConstantesJanela.FATOR_X);
		gridFichaDia.setColWidth(4,42*(int)ConstantesJanela.FATOR_X);
		gridFichaDia.setColWidth(5,42*(int)ConstantesJanela.FATOR_X);
		gridFichaDia.setColWidth(6,42*(int)ConstantesJanela.FATOR_X);
		gridFichaDia.setColWidth(7,42*(int)ConstantesJanela.FATOR_X);
		gridFichaDia.setColWidth(8,42*(int)ConstantesJanela.FATOR_X);
		gridFichaDia.setColWidth(9,42*(int)ConstantesJanela.FATOR_X);
		gridFichaDia.setColWidth(10,42*(int)ConstantesJanela.FATOR_X);
		gridFichaDia.setColWidth(11,42*(int)ConstantesJanela.FATOR_X);
		gridFichaDia.setColWidth(12,42*(int)ConstantesJanela.FATOR_X);
		gridFichaDia.setColWidth(13,42*(int)ConstantesJanela.FATOR_X);
		String [] tituloGrid = {"","JAN","FEV","MAR","ABR","MAI","JUN","JUL","AGO","SET","OUT","NOV","DEZ"};
		gridFichaDia.setRowStrings(0,tituloGrid);
		String[]Coluna1={"","Dt Visita do ACS","Faz Dieta","Faz Exercícios Físicos",};
		gridFichaDia.setColStrings(0,Coluna1);
		
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
		     btnNext.setForeColor(BLUE);
		     btnPrev.setForeColor(BLUE);
		     gridFichaDia.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridFichaDia.setSelectedCellBackColor(ConstantesJanela.ORANGE);
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
				if("U".equals(ListaFichaDiaWin.cmd)){
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
				if("N".equals(ListaFichaDiaWin.cmd)){
					cbFamilia.setEnabled(true);
			    	cbPaciente.setEnabled(true);	
					cbFamilia.select(0);
				} else if("U".equals(ListaFichaDiaWin.cmd)){
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
					if("U".equals(ListaFichaDiaWin.cmd)){
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
					if("U".equals(ListaFichaDiaWin.cmd)){							
						cbPaciente.select(indicePaciente);
				    }else{
				    	cbPaciente.select(0);	
				    }					
				}
				
			}
			if("U".equals(ListaFichaDiaWin.cmd)){
				ResultSet rs = FichaANgc.criarInstancia().GetPaciente(this.cdFamilia,this.cdPaciente);
				ResultSet rs2 = FichaANgc.criarInstancia().GetFamiliaPaciente(this.cdFamilia);
				ResultSet rsAcomp = FichaDiaNgc.criarInstancia().GetFichaDiaAcomp(this.cdFamilia,this.cdPaciente); 
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
				}
		    	String items[]  = new String[4];
		    	String [] arrayMes = {"","JAN","FEV","MAR","ABR","MAI","JUN","JUL","AGO","SET","OUT","NOV","DEZ"};
		    	while(rsAcomp.next()){
		    		int mes = rsAcomp.getInt("CD_MES");
		    		items[0]=arrayMes[mes];
		    		items[1] = rsAcomp.getString("DT_VISITA");
		    		items[2] = rsAcomp.getString("FL_DIETA");
		    		items[3] = rsAcomp.getString("FL_EXERCICIO");
		    		gridFichaDia.setColStrings(mes,items);
		    	}
			}
		} catch (Exception e) {
			Vm.debug("Erro: "+e.getMessage());
    		MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+e.getMessage());
			msgBoxErro.popupBlockingModal();
		}
	}
	
	public void LimpaForm(){
		String[]Linha1={"Dt Visita do ACS","","","","","","","","","","","",""};
		String[]Linha2={"Faz Dieta","","","","","","","","","","","",""};
		String[]Linha3={"Faz Exercícios Físicos","","","","","","","","","","","",""};
		gridFichaDia.setRowStrings(1,Linha1);
		gridFichaDia.setRowStrings(2,Linha2);
		gridFichaDia.setRowStrings(3,Linha3);
	}

    public void onEvent(Event e) {
    	try{
		    if (e.type == ControlEvent.PRESSED) { 
		    	if(e.target==btnNext){
		    		if(ValidaForm()){
		    			PreencheAtributo();
		    		    FichaDia2Win fichaDia2Win = FichaDia2Win.criarInstancia();
		    		    if("N".equals(ListaFichaDiaWin.cmd)){
		    		    	fichaDia2Win.LimpaForm(); 
			    		 }else if("U".equals(ListaFichaDiaWin.cmd)){
			    			 fichaDia2Win.PopulaGrid();
			    		 } 
		    		    fichaDia2Win.popupBlockingModal();	
			    		 if("F".equals(ListaFichaDiaWin.cmd)){
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
		    }else if(e.type == GridEvent.CELL_DOUBLE_CLICKED){
		    	if(e.target==gridFichaDia){
		    		
		    		EntradaDataGrid();
		    	}
		    }		   
    	}catch (Exception error) {
			MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+error.getMessage());
			msgBoxErro.popupBlockingModal();
			Vm.debug("Erro: "+error.getMessage());
		}   
	}  

	   private void EntradaDataGrid(){
		   MessageBox msgGrid =null;
		   String[] pergunta = {"Dt Visita do ACS",
				   				"Faz Dieta","Faz Exercícios Físicos"};
		   
		    if(gridFichaDia.getUserSelectedCol()>0){		    	
		    	if(gridFichaDia.getUserSelectedCol()==(new BBDate()).getMonth()){
				    	switch (gridFichaDia.getUserSelectedRow()) {
						case 1:	
							calendar=new Calendar();
				    		calendar.setSelectedDate(new Date(gridFichaDia.getCellText(gridFichaDia.getUserSelectedCellID())));
				    		calendar.popupBlockingModal();
				    		calendar.popupBlockingModal();
				    		gridFichaDia.setCellText(gridFichaDia.getUserSelectedCellID(),calendar.getSelectedDate()==null?"":calendar.getSelectedDate().toString());
				    		//MessageBox msgBoxErro = new MessageBox("Data","data="+calendar.getSelectedDate());
				    		//msgBoxErro.popupBlockingModal();
						/*	InputDialog id = new InputDialog("Atenção",
				    	    		  		pergunta[gridFichaDia.getUserSelectedRow()-1],
				    	    		  		gridFichaDia.getCellText(gridFichaDia.getUserSelectedCellID()),new String[]{"Ok","Cancel"});
							id.setForeColor(ConstantesJanela.BLUE);
							id.popupBlockingModal();
							if (id.getPressedButtonIndex() == 0){
								gridFichaDia.setCellText(gridFichaDia.getUserSelectedCellID(),id.getValue());
				    	      }	*/	
							break;
						case 2:									
						    	msgGrid = new MessageBox("Atenção",pergunta[gridFichaDia.getUserSelectedRow()-1], new String[]{"Sim","Não","Canc"});			    	
						    	msgGrid.popupBlockingModal();
						    	msgGrid.setForeColor(ConstantesJanela.BLUE);
						    	if(msgGrid.getPressedButtonIndex() == 0){						    	
						    		gridFichaDia.setCellText(gridFichaDia.getUserSelectedCellID(),"S");
						    		repaint();
						    	}else if(msgGrid.getPressedButtonIndex() == 1){						    		  
						    		gridFichaDia.setCellText(gridFichaDia.getUserSelectedCellID(),"N");						    		   
						    	}
							    break;
						case 3:									
					    	msgGrid = new MessageBox("Atenção",pergunta[gridFichaDia.getUserSelectedRow()-1], new String[]{"Sim","Não","Canc"});			    	
					    	msgGrid.popupBlockingModal();
					    	msgGrid.setForeColor(ConstantesJanela.BLUE);
					    	if(msgGrid.getPressedButtonIndex() == 0){						    	
					    		gridFichaDia.setCellText(gridFichaDia.getUserSelectedCellID(),"S");
					    		repaint();
					    	}else if(msgGrid.getPressedButtonIndex() == 1){						    		  
					    		gridFichaDia.setCellText(gridFichaDia.getUserSelectedCellID(),"N");						    		   
					    	}
						    break;	    
						}	
		    	}else{
		    		msgGrid = new MessageBox("Atenção","Somente é Permitido Alterar| Dados do Mês Atual", new String[]{"OK"});			    	
			    	msgGrid.popupBlockingModal();
			    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	}
	       }
	  }	 
	   
    /**
     *  lê o form e carrrega os atributos 
     */
    private void PreencheAtributo(){
    	this.cdFamilia=Convert.toInt((String) cbFamilia.getItemAt(cbFamilia.getSelectedIndex()));
    	this.cdPaciente=chPaciente[cbPaciente.getSelectedIndex()];
    	int mes = SIABBusiness.getMes();			
    	this.dtVisita=gridFichaDia.getCellText(1,mes);
    	this.flDieta=gridFichaDia.getCellText(2,mes);
    	this.flExercicio=gridFichaDia.getCellText(3,mes);
    }
    
    /**
     * lê os pdbs e carrrega os atributos
     */
    protected void CarregaAtributoPdb(int cdFamilia){
		try{
    		FichaDiaNgc fichaDiaNgc= FichaDiaNgc.criarInstancia();
    		ResultSet rs =fichaDiaNgc.getFichaDia(cdFamilia,cdPaciente);
    		 if(rs.next()){ 
    			 this.cdFamilia=cdFamilia;
    		     this.cdPaciente=cdPaciente;
    		     this.dtVisita=rs.getString("DT_VISITA");
    		     this.flDieta=rs.getString("FL_DIETA");
    		     this.flExercicio=rs.getString("FL_EXERCICIO");
    		 }		 
    	} catch (Exception e) {
    		Vm.debug("Erro: "+e.getMessage());
    		MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+e.getMessage());
			msgBoxErro.popupBlockingModal();
		}
   }
    /**
     * Limpa os atributos
     */
    protected void LimpaAtributo(){
    	try{
			    this.cdFamilia=-1;
			    this.cdPaciente=-1;
			    this.dsObservacao="";
			    this.dtVisita="";
			    this.flDieta="";
			    this.flExercicio="";
			    this.flInsulina="";
			    this.flHipoglicemiante="";
			    this.dtUltConsulta="";
    	} catch (Exception e) {
    		Vm.debug("Erro: "+e.getMessage());
    		MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+e.getMessage());
			msgBoxErro.popupBlockingModal();
		}
   }
    
    private boolean ValidaForm(){
		  MessageBox msgGrid;
		  try{  
			if("N".equals(ListaFichaDiaWin.cmd)){
			  	FichaDiaNgc fichaDiaNgc = FichaDiaNgc.criarInstancia();
			  	if(fichaDiaNgc.ExistsPacienteDia(Convert.toLong((String) cbFamilia.getItemAt(cbFamilia.getSelectedIndex())),chPaciente[cbPaciente.getSelectedIndex()])){
			  		msgGrid = new MessageBox("Atenção","Paciente Já Cadastrado", new String[]{"OK"});			    	
				    	msgGrid.popupBlockingModal();
				    	msgGrid.setForeColor(ConstantesJanela.BLUE);
				    	return false;
				}
			}
		    int mes = SIABBusiness.getMes();
		    if("".equals(gridFichaDia.getCellText(1,mes))){
		    	msgGrid = new MessageBox("Atenção","Preencha o Campo Dt Visita", new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
		    }
		    if("".equals(gridFichaDia.getCellText(2,mes))){
		    	msgGrid = new MessageBox("Atenção","Preencha o Campo Faz Dieta", new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
		    }
		    if("".equals(gridFichaDia.getCellText(3,mes))){
		    	msgGrid = new MessageBox("Atenção","Preencha o Campo|Faz Exercícios Físicos", new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
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
