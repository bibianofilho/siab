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
import waba.ui.Radio;
import waba.ui.RadioGroup;
import waba.ui.Window;
import wextlib.ui.grid.BBGrid;
import wextlib.ui.grid.GridCell;
import wextlib.ui.grid.GridCellCheckBox;
import wextlib.ui.grid.GridCellID;
import wextlib.ui.grid.GridCellText;
import wextlib.ui.grid.GridEvent;
import wextlib.ui.grid.GridStorageSingleHashtable;
import br.com.bibiano.siab.business.FichaANgc;
import br.com.bibiano.siab.business.FichaCriancaNgc;
/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class FichaCrianca1Win extends Window {
	private static FichaCrianca1Win fichaCrianca1Win;
	
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
	private Label lblSituVacinal;
	private BBGrid gridSituVacinal;
	
	
	
	private Button btnPrev;
	private Button btnNext;
	
	/**
	 * Atributos FichaDia*/
	protected static int cdFamilia;
	protected static int cdPaciente;
	
	protected static String flImunizado;
	protected static String flCompletarEsq;
	protected static String dtPneumonococos;
	
	protected static String flDiarreia;
	protected static String flUsaramTro;
	protected static String flInternamentos;
	protected static String flIra;
	protected static String flCaxumba;
	protected static String flCatapora;
	protected static String flBaixoPeso;
	protected static String flDeficiencias;	
	
	private int[] chPaciente;
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static FichaCrianca1Win criarInstancia()
	{
		if(fichaCrianca1Win == null)
		{
			fichaCrianca1Win = new FichaCrianca1Win();
		}
		return fichaCrianca1Win;
	}
	
	public FichaCrianca1Win(){
		super("Ficha CRIANÇA1",TAB_ONLY_BORDER);  
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
		
		add(lblSexo = new Label("Sexo:"),LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+4*(int)ConstantesJanela.FATOR_Y);
		
		ragSexo = new RadioGroup();
		add(rdMasculino = new Radio("MASC",ragSexo),AFTER +(int) ConstantesJanela.FATOR_X,SAME);
		add(rdFeminino = new Radio("FEM",ragSexo),AFTER +6*(int) ConstantesJanela.FATOR_X,SAME);
		rdMasculino.setEnabled(false);
		rdFeminino.setEnabled(false);
		
		add(lblIdade =  new Label("Idade:"),AFTER +4*(int) ConstantesJanela.FATOR_X, SAME);			
		add(edtIdade = new Edit("99"));
		edtIdade.setMaxLength(3);
		edtIdade.setRect(AFTER +2*(int) ConstantesJanela.FATOR_X, SAME- 2*(int) ConstantesJanela.FATOR_Y,20*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		edtIdade.setEnabled(false);
		
		add(lblSituVacinal= new Label("Situação Vacinal:"),LEFT + 3*(int) ConstantesJanela.FATOR_X,AFTER+2*(int)ConstantesJanela.FATOR_Y);
		add(gridSituVacinal = new BBGrid());
		gridSituVacinal.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 2*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 30*(int) ConstantesJanela.FATOR_Y);
		gridSituVacinal.setStorage(new GridStorageSingleHashtable());
		gridSituVacinal.setAllowSortRows(true);
		gridSituVacinal.setAllowSortCols(true);
		gridSituVacinal.setHighlightSelectedCell(true);
		gridSituVacinal.setRowCount(2);
		gridSituVacinal.setColCount(2);		
		gridSituVacinal.setVerticalScrollVisible(false);
		gridSituVacinal.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);
		gridSituVacinal.setColSeparatorWidth(1);
		gridSituVacinal.setRowSeparatorHeight(1);
		gridSituVacinal.setDrawColSeparators(true);
		gridSituVacinal.setDrawRowSeparators(true);
		gridSituVacinal.setUpdating(false);
		gridSituVacinal.setFullRowSelectionDisplay(true);
		gridSituVacinal.setColCellType(0, new GridCellCheckBox().getClass());
		gridSituVacinal.setColCellType(1, new GridCellText().getClass());
		gridSituVacinal.setColWidth(0, 15*(int)ConstantesJanela.FATOR_X);
		gridSituVacinal.setColWidth(1, 135*(int)ConstantesJanela.FATOR_X);
		gridSituVacinal.setCellText(0,1,"IMUNIZADO");
		gridSituVacinal.setCellText(1,1,"FALTA COMPLETAR O ESQUEMA");		
		gridSituVacinal.setHorizontalScrollVisible(false);
		
		
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
		     lblSituVacinal.setForeColor(BLUE);		     
		     btnNext.setForeColor(BLUE);
		     btnPrev.setForeColor(BLUE);
		     gridSituVacinal.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridSituVacinal.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		    
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
				if("U".equals(ListaFichaCriancaWin.cmd)){
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
				if("N".equals(ListaFichaCriancaWin.cmd)){
					cbFamilia.setEnabled(true);
			    	cbPaciente.setEnabled(true);	
					cbFamilia.select(0);
				} else if("U".equals(ListaFichaCriancaWin.cmd)){
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
					if("U".equals(ListaFichaCriancaWin.cmd)){
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
					if("U".equals(ListaFichaCriancaWin.cmd)){							
						cbPaciente.select(indicePaciente);
				    }else{
				    	cbPaciente.select(0);	
				    }	
				}
			}
			if("U".equals(ListaFichaCriancaWin.cmd)){
				ResultSet rs = FichaANgc.criarInstancia().GetPaciente(this.cdFamilia,this.cdPaciente);
				ResultSet rs2 = FichaANgc.criarInstancia().GetFamiliaPaciente(this.cdFamilia);
				ResultSet rsCrianca = FichaCriancaNgc.criarInstancia().getFichaCrianca(this.cdFamilia,this.cdPaciente);				 
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
					GridCell cell;
					if(rsCrianca.next()){	
						cell = new GridCellCheckBox();
	    	    		if("S".equals(rsCrianca.getString("FL_IMUNIZADO"))){    	    			
	        	    		((GridCellCheckBox) cell).setChecked(true);        	    		
	    	    		}else{
	    	    			((GridCellCheckBox) cell).setChecked(false);
	    	    		}
	    	    		gridSituVacinal.setCell(new GridCellID(0, 0), cell);			
	    	    		cell = new GridCellCheckBox();
	    	    		if("S".equals(rsCrianca.getString("FL_COMPLETARESQ"))){    	    			
	        	    		((GridCellCheckBox) cell).setChecked(true);        	    		
	    	    		}else{
	    	    			((GridCellCheckBox) cell).setChecked(false);
	    	    		}
	    	    		gridSituVacinal.setCell(new GridCellID(1, 0), cell);
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
		
		GridCell cell;
    	for(int i=0;i<2;i++){
    		cell = new GridCellCheckBox();
    		((GridCellCheckBox) cell).setChecked(false);
    		gridSituVacinal.setCell(new GridCellID(i, 0), cell);
    	}
	}

    public void onEvent(Event e) {
    	try{
		    if (e.type == ControlEvent.PRESSED) { 
		    	if(e.target==btnNext){
		    		if(ValidaForm()){
		    			PreencheAtributo();
		    		    FichaCrianca2Win fichaCrianca2Win = FichaCrianca2Win.criarInstancia();
		    		    if("N".equals(ListaFichaCriancaWin.cmd)){
		    		    	fichaCrianca2Win.LimpaForm(); 
			    		 }else if("U".equals(ListaFichaCriancaWin.cmd)){
			    			fichaCrianca2Win.PopulaForm();
			    		 } 
		    		    fichaCrianca2Win.popupBlockingModal();	
			    		 if("F".equals(ListaFichaCriancaWin.cmd)){
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
		    }else if(e.type ==GridEvent.CELL_DOUBLE_CLICKED){
		    	GridEvent ge = (GridEvent) e;
		    	GridCell cell;
		    	if(e.target==gridSituVacinal){
		    		if(ge.cellID.Col==0){
			    		int linha=ge.cellID.Row;		    		
			    		for(int i=0;i<2;i++){		    			
			    			if(linha!=i){			    				
					    		cell = new GridCellCheckBox();
					    		((GridCellCheckBox) cell).setChecked(false);
					    		gridSituVacinal.setCell(new GridCellID(i, 0), cell);   				
			    			}
			    		}
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
     */
    private void PreencheAtributo(){
    	this.cdFamilia=Convert.toInt((String) cbFamilia.getItemAt(cbFamilia.getSelectedIndex()));
    	this.cdPaciente=chPaciente[cbPaciente.getSelectedIndex()];
    	GridCell cell;	 
		cell = new GridCellCheckBox();
		cell = gridSituVacinal.getCell(new GridCellID(0, 0));
		if(((GridCellCheckBox) cell).getChecked()){
			this.flImunizado="S";
		}else{
			this.flImunizado="N";
		}
		cell = new GridCellCheckBox();
		cell = gridSituVacinal.getCell(new GridCellID(1, 0));
		if(((GridCellCheckBox) cell).getChecked()){
			this.flCompletarEsq="S";
		}else{
			this.flCompletarEsq="N";
		}    	
    }
    
   
    
    private boolean ValidaForm(){
		  MessageBox msgGrid;
		  try{		if("N".equals(ListaFichaCriancaWin.cmd)){
					    FichaCriancaNgc fichaCriancaNgc = FichaCriancaNgc.criarInstancia();
					  	if(fichaCriancaNgc.ExistsPacienteCrianca(Convert.toInt((String) cbFamilia.getItemAt(cbFamilia.getSelectedIndex())),chPaciente[cbPaciente.getSelectedIndex()])){
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

    private void EntradaDataGrid(){
		
    }	 
}
