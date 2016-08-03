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
import waba.ui.Grid;
import waba.ui.GridEvent;
import waba.ui.Label;
import waba.ui.MessageBox;
import waba.ui.Radio;
import waba.ui.RadioGroup;
import waba.ui.Window;
import waba.util.Date;
import br.com.bibiano.siab.business.FichaANgc;
import br.com.bibiano.siab.business.FichaTbNgc;
import br.com.bibiano.siab.business.SIABBusiness;
import br.com.bibiano.siab.util.BBDate;
/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */

public class FichaTb1Win extends Window {	
	
	//atributos
	public static int cdFamilia;
	public static int cdPaciente;	
	protected static int numComunicantes;
	protected static int numComunicantes5;
	protected static String dsObservacao;
	protected static String dtVisita;
	protected static String flMedicacao;
	protected static String flReaIndese;
	protected static String dtConsulta;
	protected static String flEscarro;
	
	
	private Label lblFamilia;
	private ComboBox cbFamilia;
	private Label lblPaciente;
	private ComboBox cbPaciente;
	private Label lblEndereco;
	private Label lblSexo;
	private Label lblIdade;
	private Edit edtEndereco;
	private Edit edtIdade;
	private RadioGroup ragSexo;
	private Radio rdMasculino;
	private Radio rdFeminino;
	private Grid gridTb1;
	private Button btnPrev;
	private Button btnNext;
	private static long tmpselGrid;
	private static FichaTb1Win fichaTb1;
	private Calendar calendar;
	
	private int[] chPaciente;
	
	
	public static FichaTb1Win criarInstancia()
	{
		if(fichaTb1 == null)
		{
			fichaTb1 = new FichaTb1Win();
		}
		return fichaTb1;
	}
	
	public FichaTb1Win(){
		super("FICHAB - TB1",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(lblFamilia = new Label("Familia:"),LEFT + 3*(int) ConstantesJanela.FATOR_X,TOP  + 3*(int) ConstantesJanela.FATOR_Y);
		add(cbFamilia = new ComboBox());
		cbFamilia.setRect(AFTER +3*(int) ConstantesJanela.FATOR_X,SAME-1*(int) ConstantesJanela.FATOR_Y,FILL-3*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		add(lblPaciente = new Label("Paciente:"),LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 2*(int) ConstantesJanela.FATOR_Y);
		add(cbPaciente = new ComboBox());
		cbPaciente.setRect(AFTER +3*(int) ConstantesJanela.FATOR_X,SAME-1*(int) ConstantesJanela.FATOR_Y,FILL-3*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		add(lblEndereco = new Label("Endereço:"),LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+(int)ConstantesJanela.FATOR_Y);
		add(edtEndereco = new Edit());
		edtEndereco.setRect(AFTER +(int) ConstantesJanela.FATOR_X, SAME-3*(int) ConstantesJanela.FATOR_Y,FILL -5*(int) ConstantesJanela.FATOR_Y,15*(int) ConstantesJanela.FATOR_Y);
		edtEndereco.setEnabled(false);
		
		add(lblSexo = new Label("Sexo:"),LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+2*(int)ConstantesJanela.FATOR_Y);
		ragSexo = new RadioGroup();
		add(rdMasculino = new Radio("MASC",ragSexo),AFTER +(int) ConstantesJanela.FATOR_X,SAME+(int) ConstantesJanela.FATOR_Y);		
		add(rdFeminino = new Radio("FEM",ragSexo),AFTER +6*(int) ConstantesJanela.FATOR_X,SAME);		
		rdMasculino.setEnabled(false);
		rdFeminino.setEnabled(false);
		
		add(lblIdade = new Label("Idade:"),AFTER +7*(int) ConstantesJanela.FATOR_X, SAME+1*(int) ConstantesJanela.FATOR_Y);
		add(edtIdade = new Edit("99"));
		edtIdade.setMaxLength(3);
		edtIdade.setValidChars("1234567890");
		edtIdade.setEditable(false);
		edtIdade.setRect(AFTER +5*(int) ConstantesJanela.FATOR_X, SAME-3*(int) ConstantesJanela.FATOR_Y,20*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		String [] tituloGrid = {"","JAN","FEV","MAR","ABR","MAI","JUN","JUL","AGO","SET","OUT","NOV","DEZ"};
		int ww = fm.getTextWidth("xxxxx");
		int gridWidths[] = {90*(int) ConstantesJanela.FATOR_X, ww, ww, ww, ww, ww, ww, ww, ww, ww, ww, ww,ww};
		int gridAligns[] = { LEFT,LEFT,LEFT,LEFT,LEFT,LEFT,LEFT,LEFT,LEFT,LEFT,LEFT,LEFT,LEFT};
		
		add(gridTb1 = new Grid(tituloGrid,gridWidths,gridAligns,false));
		gridTb1.setRect(LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+1*(int)ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X,75*(int) ConstantesJanela.FATOR_Y);
				
		add(btnPrev = new Button("<<"));
		btnPrev.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		add(btnNext = new Button(">>"));
		btnNext.setRect(RIGHT - 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		if(Settings.isColor)
		{	//Setando as cores se o dispositivos suportar cores.
	    	Color BLUE =ConstantesJanela.BLUE;
		     setForeColor(BLUE);
		     lblFamilia.setForeColor(BLUE);
		     lblPaciente.setForeColor(BLUE);
		     lblEndereco.setForeColor(BLUE);
		     lblSexo.setForeColor(BLUE);
		     rdMasculino.setForeColor(BLUE);
		     rdFeminino.setForeColor(BLUE);
		     lblIdade.setForeColor(BLUE);
		     gridTb1.firstStripeColor = ConstantesJanela.BLUEGRID;
		     gridTb1.secondStripeColor = new Color(255,255,255);	
		     gridTb1.verticalLineStyle = Grid.VERT_LINE;
		     btnNext.setForeColor(BLUE);
		     btnPrev.setForeColor(BLUE);
		}
	}
	
	public void PopulaUpdate(){
		try{
			PopulaGrid();
			//ResultSet rs = FichaTbNgc.criarInstancia().ListaFichaTb(this.cdFamilia,this.cdPaciente);
			ResultSet rsAcomp = FichaTbNgc.criarInstancia().ListaFichaTbAcomp(this.cdFamilia,this.cdPaciente); 
			FichaANgc fichaANgc = FichaANgc.criarInstancia();			
			ResultSet rsFamilia = fichaANgc.GetFamiliaPaciente(this.cdFamilia);
			ResultSet rsPaciente = fichaANgc.GetPaciente(this.cdFamilia,this.cdPaciente);
			if(rsFamilia.next()){
					edtEndereco.setText(rsFamilia.getString("DS_ENDERECO"));
				if(rsPaciente.next()){
					edtIdade.setText(""+rsPaciente.getInt("NUM_IDADE"));
					if (rsPaciente.getString("FL_SEXO").equals("M")){
						ragSexo.setSelectedIndex(0);						
					}else{
						ragSexo.setSelectedIndex(1);
					}
				}				
			}
	    	String items[][]  = new String[5][13];
	    	String[]Coluna0 = {"Dt Visita","Toma Medicação","Reações Indesejáveis","Data última consulta","Exames de excarro"};
	    	while(rsAcomp.next()){
	    		int mes = rsAcomp.getInt("CD_MES");
	    		items[0][mes] = rsAcomp.getString("DT_VISITA");
	    		items[1][mes] = rsAcomp.getString("FL_MEDICACAO");
	    		items[2][mes] = rsAcomp.getString("FL_REAINDESE");
	    		items[3][mes] = rsAcomp.getString("DT_CONSULTA");
	    		items[4][mes] = rsAcomp.getString("FL_ESCARRO");
	    	}
	    	for (int i=0;i<5;i++){
	    		items[i][0] = Coluna0[i];	    	
	    	}	    	
	    	gridTb1.setItems(items);
	    	cbFamilia.setEnabled(false);
	    	cbPaciente.setEnabled(false);	    	
		}catch (Exception e) {
			MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+e.getMessage());
			msgBoxErro.popupBlockingModal();
			Vm.debug("Erro: "+e.getMessage());
		}    	
    }
	
    protected void PopulaGrid(){
    	String [] itens=null;
    	try{
    		    cbFamilia.setEnabled(true);
	    	    cbPaciente.setEnabled(true);
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
					if("U".equals(ListaFichaTbWin.cmd)){
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
					if("N".equals(ListaFichaTbWin.cmd)){
						cbFamilia.select(0);
					} else if("U".equals(ListaFichaTbWin.cmd)){
						cbFamilia.select(indiceFamilia);
					}
					ResultSet rsPaciente = fichaANgc.ListaPacienteAll(Convert.toInt((String) cbFamilia.getItemAt(cbFamilia.getSelectedIndex())));
					itens = new String[rsPaciente.getRowCount()];
					chPaciente= new int[rsPaciente.getRowCount()];
					i = 0;
					while(rsPaciente.next()){
						itens[i] = ""+rsPaciente.getString("NM_PACIENTE");
						chPaciente[i]=rsPaciente.getInt("CD_PACIENTE");
						if("U".equals(ListaFichaTbWin.cmd)){
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
						if("U".equals(ListaFichaTbWin.cmd)){							
								cbPaciente.select(indicePaciente);
						}else{
							cbPaciente.select(0);	
						}	
					}
				}
		    	String items[][]  = new String[5][13];
		    	String[]Coluna0 = {"Dt Visita","Toma Medicação","Reações Indesejáveis","Data última consulta","Exames de escarro"};
		    	for (int y=0;i<5;i++){
		    		items[y][0] = Coluna0[i];
		    		items[y][1] = "";
		    		items[y][2] = "";
		    		items[y][3] = ""; 		         
		    		items[y][4] = "";  		         
		    		items[y][5] = "";
		    		items[y][6] = "";
		    		items[y][7] = ""; 		         
		    		items[y][8] = "";
		    		items[y][9] = "";
		    		items[y][10] = "";
		    		items[y][11] = ""; 		         
		    		items[y][12] = "";
		    	}
		    	gridTb1.setItems(items);
    	}catch (Exception e) {
			Vm.debug("Erro: "+e.getMessage());
    		MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+e.getMessage());
			msgBoxErro.popupBlockingModal();
		}
    }

	   public void onEvent(Event e) {	
		   try{
				    if (e.type == ControlEvent.PRESSED) { 
					    if (e.target == btnNext) {
					    	if(ValidaForm()){
					    		PreencheAtributo();
						    	FichaTb2Win  fichaTb2 = FichaTb2Win.criarInstancia();
						    	if(!"U".equals(ListaFichaTbWin.cmd)){
						    		fichaTb2.LimpaDados();
						    	}
						    	fichaTb2.popupBlockingModal();
						    	if("F".equals(ListaFichaTbWin.cmd)){
						    		unpop();
						    	}
					    	}	
					    }else if (e.target == btnPrev){
					    	unpop();
					    }else if(e.target==cbFamilia){
				    		cbPaciente.removeAll();
				    		FichaANgc fichaANgc = FichaANgc.criarInstancia();
				    		int vFamilia=Convert.toInt((String) cbFamilia.getItemAt(cbFamilia.getSelectedIndex()));
				    		ResultSet rsPaciente = fichaANgc.ListaPacienteAll(vFamilia);
							String []itens = new String[rsPaciente.getRowCount()];
							chPaciente= new int[rsPaciente.getRowCount()];
							int i = 0;
							while(rsPaciente.next()){
								itens[i] = ""+rsPaciente.getString("NM_PACIENTE");
								chPaciente[i]=rsPaciente.getInt("CD_PACIENTE");
								i++;
								if(i==1){
									if(rsPaciente.getString("FL_SEXO").equals("M")){
										ragSexo.setSelectedIndex(0);
									}else{
										ragSexo.setSelectedIndex(1);
									}
									edtIdade.setText(""+rsPaciente.getInt("NUM_IDADE"));
								}
							}
							if(i>0){
								cbPaciente.add(itens);
								cbPaciente.select(0);								
							}
							ResultSet rsFamilia =fichaANgc.GetFamiliaPaciente(vFamilia);
							if(rsFamilia.next()){
				    			edtEndereco.setText(rsFamilia.getString("DS_ENDERECO"));
				    		}else{
				    			edtEndereco.setText("");
				    		}
				    	}else if(e.target==cbPaciente){
				    		FichaANgc fichaANgc = FichaANgc.criarInstancia();	
				    		int vFamilia=Convert.toInt((String) cbFamilia.getItemAt(cbFamilia.getSelectedIndex()));
				    		int vPaciente=chPaciente[cbPaciente.getSelectedIndex()];
				    		ResultSet rsPaciente =fichaANgc.GetPaciente(vFamilia,vPaciente);
				    		ResultSet rsFamilia =fichaANgc.GetFamiliaPaciente(vFamilia);
				    		if(rsPaciente.next()){
				    			if(rsPaciente.getString("FL_SEXO").equals("M")){
									ragSexo.setSelectedIndex(0);
								}else{
									ragSexo.setSelectedIndex(1);
								}
								edtIdade.setText(""+rsPaciente.getInt("NUM_IDADE"));
				    		}
				    		if(rsFamilia.next()){
				    			edtEndereco.setText(rsFamilia.getString("DS_ENDERECO"));
				    		}else{
				    			edtEndereco.setText("");
				    		}
				    	}
				    }else if(e.type == GridEvent.SELECTED_EVENT){
				    	 if(e.target == gridTb1){
				    		 if(DoubleClick()){
					    	      GridEvent ge = (GridEvent)e;
					    	      EntradaDataGrid(ge);
					    	 }
					    }
				    }
		    }catch (Exception error) {
				MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+error.getMessage());
				msgBoxErro.popupBlockingModal();
				Vm.debug("Erro: "+error.getMessage());
			}   
		    	
	   }  
	   
	   public static boolean DoubleClick(){
		   long lngTempo = BBDate.getCurrentTimeMillis(); 
		   if(lngTempo - tmpselGrid < 200){
			   tmpselGrid =BBDate.getCurrentTimeMillis();
			   return true; 
		   }		   
		   tmpselGrid =BBDate.getCurrentTimeMillis();
		   return false;
	   }
	   
	   private void EntradaDataGrid(GridEvent ge){
		   MessageBox msgGrid =null;
		   String[] pergunta = {"Entre com a data da| visita do ACS","Toma medicação diária? ",
				   				"Reações indesejáveis","Data da última| consulta(dd/mm/yy)",
				   				"Exame de escarro"};
		    if(ge.col>0){
		    	if(ge.col==(new BBDate()).getMonth()){			    	
				    	String oldLine[] = gridTb1.getItem(ge.row);
				    	String newLine[] = gridTb1.getItem(ge.row);
				    	switch (ge.row) {
						case 0:		
							calendar=new Calendar();
							calendar.setSelectedDate(new Date(oldLine[ge.col]));
							calendar.popupBlockingModal();
							calendar.popupBlockingModal();
							/*popupModal(calendar);
				    		unpop();
							popupBlockingModal(calendar);*/
							newLine[ge.col] = calendar.getSelectedDate()==null?"":calendar.getSelectedDate().toString();
			     	    	gridTb1.replace(newLine,ge.row);
			    	    	repaint();
						/*		InputDialog id = new InputDialog("Atenção",
					    	    		  		pergunta[ge.row],
					    	    		         oldLine[ge.col],new String[]{"Ok","Cancel"});
								id.setForeColor(ConstantesJanela.BLUE);
								id.popupBlockingModal();
								if (id.getPressedButtonIndex() == 0){		    	    	  
					    	    	  newLine[ge.col] = id.getValue();
					     	    	  gridTb1.replace(newLine,ge.row);
					    	    	  repaint();
					    	      }	*/	
								break;
						case 1:									
						    	msgGrid = new MessageBox("Atenção",pergunta[ge.row], new String[]{"Sim","Não","Não Houve Prescr","Canc"});			    	
						    	msgGrid.popupBlockingModal();
						    	msgGrid.setForeColor(ConstantesJanela.BLUE);
						    	if(msgGrid.getPressedButtonIndex() == 0){
						    		newLine[ge.col] = "S";
						    		gridTb1.replace(newLine,ge.row);
						    		repaint();
						    	}else if(msgGrid.getPressedButtonIndex() == 1){
						    		   newLine[ge.col] = "N";
							    	   gridTb1.replace(newLine,ge.row);
						    		   repaint();
						    	}else if(msgGrid.getPressedButtonIndex() == 2){
						    		   newLine[ge.col] = "X";
							    	   gridTb1.replace(newLine,ge.row);
						    		   repaint();
						    	}
							    break;
						case 2:
							msgGrid = new MessageBox("Atenção",pergunta[ge.row], new String[]{"Sim","Não","Cancelar"});			    	
					    	msgGrid.popupBlockingModal();
					    	msgGrid.setForeColor(ConstantesJanela.BLUE);
					    	if(msgGrid.getPressedButtonIndex() == 0){
					    		newLine[ge.col] = "X";
					    		gridTb1.replace(newLine,ge.row);
					    		repaint();
					    	}else if(msgGrid.getPressedButtonIndex() == 1){
					    		   newLine[ge.col] = "";
						    	   gridTb1.replace(newLine,ge.row);
					    		   repaint();
					    	}
							break;
						case 3:	
							calendar=new Calendar();
							calendar.setSelectedDate(new Date(oldLine[ge.col]));
							calendar.popupBlockingModal();
							calendar.popupBlockingModal();
							/*popupModal(calendar);
				    		unpop();
							popupBlockingModal(calendar);*/
							newLine[ge.col] = calendar.getSelectedDate()==null?"":calendar.getSelectedDate().toString();
			     	    	gridTb1.replace(newLine,ge.row);
			    	    	repaint();
							/*InputDialog id2 = new InputDialog("Atenção",
				    	    		  		pergunta[ge.row],
				    	    		         oldLine[ge.col],new String[]{"Ok","Cancel"});
							id2.setForeColor(ConstantesJanela.BLUE);
							id2.popupBlockingModal();					
							if (id2.getPressedButtonIndex() == 0){		    	    	  
				    	    	  newLine[ge.col] = id2.getValue();
				     	    	  gridTb1.replace(newLine,ge.row);
				    	    	  repaint();
				    	      }	*/	
							break;	
						case 4:
							msgGrid = new MessageBox("Atenção",pergunta[ge.row], new String[]{"Sim","Não","Cancelar"});			    	
					    	msgGrid.popupBlockingModal();
					    	msgGrid.setForeColor(ConstantesJanela.BLUE);
					    	if(msgGrid.getPressedButtonIndex() == 0){
					    		newLine[ge.col] = "S";
					    		gridTb1.replace(newLine,ge.row);
					    		repaint();
					    	}else if(msgGrid.getPressedButtonIndex() == 1){
					    		   newLine[ge.col] = "N";
						    	   gridTb1.replace(newLine,ge.row);
					    		   repaint();
					    	}
							break;	
						default:
							break;
						}	
		    	}else{
		    		msgGrid = new MessageBox("Atenção","Somente é Permitido Alterar| Dados do Mês Atual", new String[]{"OK"});			    	
			    	msgGrid.popupBlockingModal();
			    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	}   
	       }
	  }	 
	   
	  private void PreencheAtributo(){
		  this.cdFamilia = Convert.toInt((String) cbFamilia.getItemAt(cbFamilia.getSelectedIndex()));
		  this.cdPaciente = chPaciente[cbPaciente.getSelectedIndex()];
		  	
		    int mes = SIABBusiness.getMes();
			this.dtVisita=gridTb1.getItem(0)[mes];
			this.flMedicacao=gridTb1.getItem(1)[mes];
			this.flReaIndese=gridTb1.getItem(2)[mes];
			this.dtConsulta=gridTb1.getItem(3)[mes];
			this.flEscarro=gridTb1.getItem(4)[mes];
	  }
	  
	  public void LimpaDados(){
		 String items[][]  = new String[5][13];
	     String[]Coluna0 = {"Dt Visita","Toma Medicação","Reações Indesejáveis","Data última consulta","Exames de escarro"};
	     for (int i=0;i<5;i++){
	    		items[i][0] = Coluna0[i];
	    		items[i][1] = "";
	    		items[i][2] = "";
	    		items[i][3] = ""; 		         
	    		items[i][4] = "";  		         
	    		items[i][5] = "";
	    		items[i][6] = "";
	    		items[i][7] = ""; 		         
	    		items[i][8] = "";
	    		items[i][9] = "";
	    		items[i][10] = "";
	    		items[i][11] = ""; 		         
	    		items[i][12] = "";
	    }
	    gridTb1.setItems(items);
	  }
	  
	  private boolean ValidaForm(){
		  MessageBox msgGrid;
		  try{  if("N".equals(ListaFichaTbWin.cmd)){
				  	FichaTbNgc fichaTbNgc = FichaTbNgc.criarInstancia();
				  	if(fichaTbNgc.ExistsPacienteTb(Convert.toLong((String) cbFamilia.getItemAt(cbFamilia.getSelectedIndex())),chPaciente[cbPaciente.getSelectedIndex()])){
				  		msgGrid = new MessageBox("Atenção","Paciente Já Cadastrado", new String[]{"OK"});			    	
				    	msgGrid.popupBlockingModal();
				    	msgGrid.setForeColor(ConstantesJanela.BLUE);
				    	return false;
				  	}
		  		}
			    int mes = SIABBusiness.getMes();
			    if("".equals(gridTb1.getItem(0)[mes])){
			    	msgGrid = new MessageBox("Atenção","Preencha o Campo Dt Visita", new String[]{"OK"});			    	
			    	msgGrid.popupBlockingModal();
			    	msgGrid.setForeColor(ConstantesJanela.BLUE);
			    	return false;
			    }
			    if("".equals(gridTb1.getItem(1)[mes])){
			    	msgGrid = new MessageBox("Atenção","Preencha o Campo| Toma Medicação", new String[]{"OK"});			    	
			    	msgGrid.popupBlockingModal();
			    	msgGrid.setForeColor(ConstantesJanela.BLUE);
			    	return false;
			    }
				if("".equals(gridTb1.getItem(3)[mes])){
					msgGrid = new MessageBox("Atenção","Preencha o Campo| Data última consulta", new String[]{"OK"});			    	
			    	msgGrid.popupBlockingModal();
			    	msgGrid.setForeColor(ConstantesJanela.BLUE);
			    	return false;
				}
				if("".equals(gridTb1.getItem(4)[mes])){
					msgGrid = new MessageBox("Atenção","Preencha o Campo| Exames de escarro", new String[]{"OK"});			    	
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
