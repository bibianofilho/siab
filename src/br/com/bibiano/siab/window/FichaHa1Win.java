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
import br.com.bibiano.siab.business.FichaHaNgc;
import br.com.bibiano.siab.business.FichaHanNgc;
import br.com.bibiano.siab.business.SIABBusiness;
import br.com.bibiano.siab.util.BBDate;
/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class FichaHa1Win extends Window {
	private static FichaHa1Win fichaDia1Win;
	
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
	private Grid gridFichaHa;
	
	private Button btnPrev;
	private Button btnNext;
	
	private Calendar calendar;
	
	/**
	 * Atributos FichaDia*/
	protected static int cdFamilia;
	protected static int cdPaciente;
	protected static String flFumante;
	protected static String dsObservacao;
	protected static String dtVisita;
	protected static String flDieta;
	protected static String flMedicacao;
	protected static String flexercicio;
	protected static String dsPressao;
	protected static String dtUltConsulta;
	
	
	private int[] chPaciente;
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static FichaHa1Win criarInstancia()
	{
		if(fichaDia1Win == null)
		{
			fichaDia1Win = new FichaHa1Win();
		}
		return fichaDia1Win;
	}
	
	public FichaHa1Win(){
		super("Ficha HA1",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(lblFamilia = new Label("Familia:"),LEFT + 3*(int) ConstantesJanela.FATOR_X,TOP  + 2*(int) ConstantesJanela.FATOR_Y);
		add(cbFamilia = new ComboBox());
		cbFamilia.setRect(AFTER +3*(int) ConstantesJanela.FATOR_X,SAME-1*(int) ConstantesJanela.FATOR_Y,FILL-3*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
	
		add(lblPaciente = new Label("Paciente:"),LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 2*(int) ConstantesJanela.FATOR_Y);
		add(cbPaciente = new ComboBox());
		cbPaciente.setRect(AFTER +3*(int) ConstantesJanela.FATOR_X,SAME-1*(int) ConstantesJanela.FATOR_Y,FILL-3*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		add(lblEndereco = new Label("Endereço:"),LEFT + 3*(int) ConstantesJanela.FATOR_X,AFTER+ 2*(int) ConstantesJanela.FATOR_Y);
		add(edtEndereco = new Edit());
		edtEndereco.setRect(AFTER + 1*(int) ConstantesJanela.FATOR_X,SAME-3*(int) ConstantesJanela.FATOR_Y, FILL -3*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		edtEndereco.setEnabled(false);
		
		add(lblSexo = new Label("Sexo:"),LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+5*(int)ConstantesJanela.FATOR_Y);		
		ragSexo = new RadioGroup();
		add(rdMasculino = new Radio("MASC",ragSexo),AFTER +(int) ConstantesJanela.FATOR_X,SAME-1*(int) ConstantesJanela.FATOR_Y);
		add(rdFeminino = new Radio("FEM",ragSexo),AFTER +6*(int) ConstantesJanela.FATOR_X,SAME);
		rdMasculino.setEnabled(false);
		rdFeminino.setEnabled(false);
		
		add(lblIdade =  new Label("Idade:"),AFTER +3*(int) ConstantesJanela.FATOR_X, SAME+1*(int) ConstantesJanela.FATOR_Y);		
		add(edtIdade = new Edit("99"));
		edtIdade.setMaxLength(3);
		edtIdade.setRect(AFTER +2*(int) ConstantesJanela.FATOR_X, SAME-4*(int) ConstantesJanela.FATOR_Y,20*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		edtIdade.setEnabled(false);
		
		add(lblFumante = new Label("Fumante:"),LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+4*(int)ConstantesJanela.FATOR_Y);
		ragFumante = new RadioGroup();
		add(rdFumanteSim = new Radio("Sim",ragFumante),AFTER +3*(int) ConstantesJanela.FATOR_X,SAME-3*(int) ConstantesJanela.FATOR_Y);
		add(rdFumanteNao = new Radio("Não",ragFumante),AFTER +6*(int) ConstantesJanela.FATOR_X,SAME);
		
		add(gridFichaHa = new BBGrid());
		gridFichaHa.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER + 1*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 53*(int) ConstantesJanela.FATOR_Y);
		gridFichaHa.setStorage(new GridStorageSingleHashtable());
		gridFichaHa.setAllowSortRows(false);
		gridFichaHa.setAllowSortCols(false);
		gridFichaHa.setAllowUserResizeRows(false);
		gridFichaHa.setVerticalScrollVisible(false);
		gridFichaHa.setHighlightSelectedCell(true);
		gridFichaHa.setRowCount(3);
		gridFichaHa.setColCount(13);
		gridFichaHa.setFixedRowCount(1);
		gridFichaHa.setFixedColCount(1);
		gridFichaHa.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);
		gridFichaHa.setColSeparatorWidth(1);
		gridFichaHa.setRowSeparatorHeight(1);
		gridFichaHa.setDrawColSeparators(true);
		gridFichaHa.setDrawRowSeparators(true);
		gridFichaHa.setUpdating(false);
		gridFichaHa.setFullRowSelectionDisplay(true);
		gridFichaHa.setColWidth(0,60*(int)ConstantesJanela.FATOR_X);
		gridFichaHa.setColWidth(1,42*(int)ConstantesJanela.FATOR_X);
		gridFichaHa.setColWidth(2,42*(int)ConstantesJanela.FATOR_X);
		gridFichaHa.setColWidth(3,42*(int)ConstantesJanela.FATOR_X);
		gridFichaHa.setColWidth(4,42*(int)ConstantesJanela.FATOR_X);
		gridFichaHa.setColWidth(5,42*(int)ConstantesJanela.FATOR_X);
		gridFichaHa.setColWidth(6,42*(int)ConstantesJanela.FATOR_X);
		gridFichaHa.setColWidth(7,42*(int)ConstantesJanela.FATOR_X);
		gridFichaHa.setColWidth(8,42*(int)ConstantesJanela.FATOR_X);
		gridFichaHa.setColWidth(9,42*(int)ConstantesJanela.FATOR_X);
		gridFichaHa.setColWidth(10,42*(int)ConstantesJanela.FATOR_X);
		gridFichaHa.setColWidth(11,42*(int)ConstantesJanela.FATOR_X);
		gridFichaHa.setColWidth(12,42*(int)ConstantesJanela.FATOR_X);
		gridFichaHa.setColWidth(13,42*(int)ConstantesJanela.FATOR_X);
		String [] tituloGrid = {"","JAN","FEV","MAR","ABR","MAI","JUN","JUL","AGO","SET","OUT","NOV","DEZ"};
		gridFichaHa.setRowStrings(0,tituloGrid);
		String[]Coluna1={"","Dt Visita do ACS","Faz Dieta"};
		gridFichaHa.setColStrings(0,Coluna1);
		
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
		     btnNext.setForeColor(BLUE);
		     btnPrev.setForeColor(BLUE);
		     gridFichaHa.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridFichaHa.setSelectedCellBackColor(ConstantesJanela.ORANGE);
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
				if("U".equals(ListaFichaHaWin.cmd)){
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
				if("N".equals(ListaFichaHaWin.cmd)){
					cbFamilia.setEnabled(true);
			    	cbPaciente.setEnabled(true);	
					cbFamilia.select(0);
				} else if("U".equals(ListaFichaHaWin.cmd)){
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
					if("U".equals(ListaFichaHaWin.cmd)){
						if(rsPaciente.getInt("CD_PACIENTE")==this.cdPaciente){
							indicePaciente=i;
						}
					}
					if(i==0){						
						edtIdade.setText(""+rsPaciente.getInt("NUM_IDADE"));
						if(rsPaciente.getString("FL_SEXO").equals("M")){
							ragSexo.setSelectedIndex(0);
						}else{
							ragSexo.setSelectedIndex(1);
						}	
					}
					i++;
				}
				if(i>0){
					cbPaciente.add(itens);
					if("U".equals(ListaFichaHaWin.cmd)){							
						cbPaciente.select(indicePaciente);
				    }else{
				    	cbPaciente.select(0);	
				    }	
									
				}
				
			}
			if("U".equals(ListaFichaHaWin.cmd)){
				ResultSet rs = FichaANgc.criarInstancia().GetPaciente(this.cdFamilia,this.cdPaciente);
				ResultSet rs2 = FichaANgc.criarInstancia().GetFamiliaPaciente(this.cdFamilia);
				ResultSet rsHa = FichaHaNgc.criarInstancia().getFichaHa(this.cdFamilia,this.cdPaciente);
				ResultSet rsAcomp = FichaHaNgc.criarInstancia().GetFichaHaAcomp(this.cdFamilia,this.cdPaciente); 
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
					if (rsHa.getString("FL_FUMANTE").equals("S")){
						ragFumante.setSelectedIndex(0);						
					}else{
						ragFumante.setSelectedIndex(1);
					}
					
				}
		    	String items[]  = new String[3];
		    	String [] arrayMes = {"","JAN","FEV","MAR","ABR","MAI","JUN","JUL","AGO","SET","OUT","NOV","DEZ"};
		    	while(rsAcomp.next()){
		    		int mes = rsAcomp.getInt("CD_MES");
		    		items[0]=arrayMes[mes];
		    		items[1] = rsAcomp.getString("DT_VISITA");
		    		items[2] = rsAcomp.getString("FL_DIETA");
		    		gridFichaHa.setColStrings(mes,items);
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
		gridFichaHa.setRowStrings(1,Linha1);
		gridFichaHa.setRowStrings(2,Linha2);
	}

    public void onEvent(Event e) {
    	try{
		    if (e.type == ControlEvent.PRESSED) { 
		    	if(e.target==btnNext){
		    		if(ValidaForm()){
		    			PreencheAtributo();
		    		    FichaHa2Win fichaHa2Win = FichaHa2Win.criarInstancia();
		    		    if("N".equals(ListaFichaHaWin.cmd)){
			    			 fichaHa2Win.LimpaForm(); 
			    		 }else if("U".equals(ListaFichaHaWin.cmd)){
			    			 fichaHa2Win.PopulaGrid();
			    		 } 
		    		    fichaHa2Win.popupBlockingModal();	
			    		 if("F".equals(ListaFichaHaWin.cmd)){
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
		    	if(e.target==gridFichaHa){
		    		EntradaDataGrid();
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
    	int mes = SIABBusiness.getMes();			
    	this.dtVisita=gridFichaHa.getCellText(1,mes);
    	this.flDieta=gridFichaHa.getCellText(2,mes);
    }
    
    /**
     * lê os pdbs e carrrega os atributos
     */
    protected void CarregaAtributoPdb(int cdFamilia,int cdPaciente){
    	try{
    		FichaHanNgc fichaHanNgc= FichaHanNgc.criarInstancia();
    		ResultSet rs =fichaHanNgc.getFichaHan(cdFamilia,cdPaciente);
    		 if(rs.next()){ 
    			 this.cdFamilia=cdFamilia;
    		     this.cdPaciente=cdPaciente;
    		     this.flFumante=rs.getString("FL_FUMANTE");
    		     this.dtVisita=rs.getString("DT_VISITA");
    		     this.flDieta=rs.getString("FL_DIETA");
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
			    this.flFumante="";
			    this.dsObservacao="";			    
			    this.dtVisita="";			    
			    this.flMedicacao="";			    
			    this.flexercicio="";
			    this.dsPressao="";
			    this.dtUltConsulta="";
    	} catch (Exception e) {
    		Vm.debug("Erro: "+e.getMessage());
    		MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+e.getMessage());
			msgBoxErro.popupBlockingModal();
		}
   }
    
    private boolean ValidaForm(){
		  MessageBox msgGrid;
		  try{		if("N".equals(ListaFichaHaWin.cmd)){
					    FichaHaNgc fichaHaNgc = FichaHaNgc.criarInstancia();
					  	if(fichaHaNgc.ExistsPacienteHa(Convert.toLong((String) cbFamilia.getItemAt(cbFamilia.getSelectedIndex())),chPaciente[cbPaciente.getSelectedIndex()])){
					  		msgGrid = new MessageBox("Atenção","Paciente Já Cadastrado", new String[]{"OK"});			    	
					    	msgGrid.popupBlockingModal();
					    	msgGrid.setForeColor(ConstantesJanela.BLUE);
					    	return false;
					  	}
		  			}
				    int mes = SIABBusiness.getMes();
				    if("".equals(gridFichaHa.getCellText(1,mes))){
				    	msgGrid = new MessageBox("Atenção","Preencha o Campo Dt Visita", new String[]{"OK"});			    	
				    	msgGrid.popupBlockingModal();
				    	msgGrid.setForeColor(ConstantesJanela.BLUE);
				    	return false;
				    }
				    if("".equals(gridFichaHa.getCellText(2,mes))){
				    	msgGrid = new MessageBox("Atenção","Preencha o Campo Faz Dieta", new String[]{"OK"});			    	
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

    private void EntradaDataGrid(){
		MessageBox msgGrid =null;
		String[] pergunta = {"Dt Visita do ACS","Faz Dieta"};
		if(gridFichaHa.getUserSelectedCol()>0){
			if(gridFichaHa.getUserSelectedCol()==(new BBDate()).getMonth()){
				switch (gridFichaHa.getUserSelectedRow()) {
				case 1:	
					calendar=new Calendar();
		    		calendar.setSelectedDate(new Date(gridFichaHa.getCellText(gridFichaHa.getUserSelectedCellID())));
		    		calendar.popupBlockingModal();
		    		calendar.popupBlockingModal();
		    		gridFichaHa.setCellText(gridFichaHa.getUserSelectedCellID(),calendar.getSelectedDate()==null?"":calendar.getSelectedDate().toString());
						/*InputDialog id = new InputDialog("Atenção",
						  		pergunta[gridFichaHa.getUserSelectedRow()-1],
						  		gridFichaHa.getCellText(gridFichaHa.getUserSelectedCellID()),new String[]{"Ok","Cancel"});
						id.setForeColor(ConstantesJanela.BLUE);
						id.popupBlockingModal();
						if (id.getPressedButtonIndex() == 0){
							gridFichaHa.setCellText(gridFichaHa.getUserSelectedCellID(),id.getValue());
						  }	*/	
				break;
				case 2:									
				    	msgGrid = new MessageBox("Atenção",pergunta[gridFichaHa.getUserSelectedRow()-1], new String[]{"Sim","Não","Canc"});			    	
				    	msgGrid.popupBlockingModal();
				    	msgGrid.setForeColor(ConstantesJanela.BLUE);
				    	if(msgGrid.getPressedButtonIndex() == 0){						    	
				    		gridFichaHa.setCellText(gridFichaHa.getUserSelectedCellID(),"S");
				    		repaint();
				    	}else if(msgGrid.getPressedButtonIndex() == 1){						    		  
				    		gridFichaHa.setCellText(gridFichaHa.getUserSelectedCellID(),"N");						    		   
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
}
