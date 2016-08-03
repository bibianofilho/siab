package br.com.bibiano.siab.window;


import litebase.ResultSet;
import waba.fx.Color;
import waba.sys.Convert;
import waba.sys.Settings;
import waba.sys.Vm;
import waba.ui.Button;
import waba.ui.Calendar;
import waba.ui.ControlEvent;
import waba.ui.Edit;
import waba.ui.Event;
import waba.ui.InputDialog;
import waba.ui.Label;
import waba.ui.MessageBox;
import waba.ui.Window;
import waba.util.Date;
import waba.util.Hashtable;
import wextlib.ui.grid.BBGrid;
import wextlib.ui.grid.Grid;
import wextlib.ui.grid.GridCell;
import wextlib.ui.grid.GridCellCheckBox;
import wextlib.ui.grid.GridCellID;
import wextlib.ui.grid.GridCellText;
import wextlib.ui.grid.GridEvent;
import wextlib.ui.grid.GridStorageSingleHashtable;
import br.com.bibiano.siab.business.AgenteNgc;
import br.com.bibiano.siab.business.FichaCriancaNgc;
import br.com.bibiano.siab.business.SIABBusiness;
import br.com.bibiano.siab.util.BBDate;

/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class FichaCrianca3Win extends Window {
	private static FichaCrianca3Win fichaCrianca3Win;

	private Grid gridQuadroNutricional;
	private Label lblAvalAlimentar;	
	private Grid gridAvalAlimentar;
	private Label lblOutroTpAlimentacao;
	private Edit edtOutroTpAlimentacao;
	private Button btnPrev;
	private Button btnNext;
	private Calendar calendar;
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static FichaCrianca3Win criarInstancia()
	{
		if(fichaCrianca3Win == null)
		{
			fichaCrianca3Win = new FichaCrianca3Win();
		}
		return fichaCrianca3Win;
	}
	public FichaCrianca3Win(){
		super("FICHA - CRIANÇA3",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(gridQuadroNutricional = new BBGrid());		
		gridQuadroNutricional.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, TOP + 1*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 67*(int) ConstantesJanela.FATOR_Y);
		gridQuadroNutricional.setStorage(new GridStorageSingleHashtable());
		gridQuadroNutricional.setAllowSortRows(false);
		gridQuadroNutricional.setAllowSortCols(false);
		gridQuadroNutricional.setAllowUserResizeRows(false);
		gridQuadroNutricional.setVerticalScrollVisible(false);
		gridQuadroNutricional.setHighlightSelectedCell(true);
		gridQuadroNutricional.setRowCount(4);
		gridQuadroNutricional.setColCount(13);
		gridQuadroNutricional.setFixedRowCount(1);
		gridQuadroNutricional.setFixedColCount(1);		
		gridQuadroNutricional.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);
		gridQuadroNutricional.setColSeparatorWidth(1);
		gridQuadroNutricional.setRowSeparatorHeight(1);
		gridQuadroNutricional.setDrawColSeparators(true);
		gridQuadroNutricional.setDrawRowSeparators(true);
		gridQuadroNutricional.setUpdating(false);
		gridQuadroNutricional.setFullRowSelectionDisplay(true);		
		gridQuadroNutricional.setColWidth(0,40*(int)ConstantesJanela.FATOR_X);
		gridQuadroNutricional.setColWidth(1,42*(int)ConstantesJanela.FATOR_X);
		gridQuadroNutricional.setColWidth(2,42*(int)ConstantesJanela.FATOR_X);
		gridQuadroNutricional.setColWidth(3,42*(int)ConstantesJanela.FATOR_X);
		gridQuadroNutricional.setColWidth(4,42*(int)ConstantesJanela.FATOR_X);
		gridQuadroNutricional.setColWidth(5,42*(int)ConstantesJanela.FATOR_X);
		gridQuadroNutricional.setColWidth(6,42*(int)ConstantesJanela.FATOR_X);
		gridQuadroNutricional.setColWidth(7,42*(int)ConstantesJanela.FATOR_X);
		gridQuadroNutricional.setColWidth(8,42*(int)ConstantesJanela.FATOR_X);
		gridQuadroNutricional.setColWidth(9,42*(int)ConstantesJanela.FATOR_X);
		gridQuadroNutricional.setColWidth(10,42*(int)ConstantesJanela.FATOR_X);
		gridQuadroNutricional.setColWidth(11,42*(int)ConstantesJanela.FATOR_X);
		gridQuadroNutricional.setColWidth(12,42*(int)ConstantesJanela.FATOR_X);
		gridQuadroNutricional.setColWidth(13,42*(int)ConstantesJanela.FATOR_X);		
		String [] tituloGrid = {"","JAN","FEV","MAR","ABR","MAI","JUN","JUL","AGO","SET","OUT","NOV","DEZ"};
		gridQuadroNutricional.setRowStrings(0,tituloGrid);
		String[]Coluna1={"","Data","Peso","Altura"};
		gridQuadroNutricional.setColStrings(0,Coluna1);
		
		//add(lblAvalAlimentar= new Label("Avaliação Alimentar:"),LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+(int)ConstantesJanela.FATOR_Y);
		add(gridAvalAlimentar = new BBGrid());
		gridAvalAlimentar.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 1*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 30*(int) ConstantesJanela.FATOR_Y);
		gridAvalAlimentar.setStorage(new GridStorageSingleHashtable());
		gridAvalAlimentar.setAllowSortRows(true);
		gridAvalAlimentar.setAllowSortCols(true);
		gridAvalAlimentar.setHighlightSelectedCell(true);
		gridAvalAlimentar.setRowCount(2);
		gridAvalAlimentar.setColCount(2);
		gridAvalAlimentar.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);
		gridAvalAlimentar.setColSeparatorWidth(1);
		gridAvalAlimentar.setRowSeparatorHeight(1);
		gridAvalAlimentar.setDrawColSeparators(true);
		gridAvalAlimentar.setDrawRowSeparators(true);
		gridAvalAlimentar.setUpdating(false);
		gridAvalAlimentar.setFullRowSelectionDisplay(true);
		gridAvalAlimentar.setColCellType(0, new GridCellCheckBox().getClass());
		gridAvalAlimentar.setColCellType(1, new GridCellText().getClass());
		gridAvalAlimentar.setColWidth(0, 15*(int)ConstantesJanela.FATOR_X);
		gridAvalAlimentar.setColWidth(1, 135*(int)ConstantesJanela.FATOR_X);
		gridAvalAlimentar.setCellText(0,1,"ALEITAMENTO EXEC.");
		gridAvalAlimentar.setCellText(1,1,"ALEIT. MATER. MISTO");					
		gridAvalAlimentar.setHorizontalScrollVisible(false);
		gridAvalAlimentar.setVerticalScrollVisible(false);
		add(lblOutroTpAlimentacao= new Label("Outro Tipo Alimentação:"),LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+(int)ConstantesJanela.FATOR_Y);
		add(edtOutroTpAlimentacao= new Edit());
		edtOutroTpAlimentacao.setRect(LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+(int)ConstantesJanela.FATOR_Y,FILL-3*(int)ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y );
		edtOutroTpAlimentacao.setMaxLength(30);
		add(btnPrev = new Button("<<"));
		btnPrev.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 1*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		add(btnNext = new Button("Finalizar"));
		btnNext.setRect(RIGHT - 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 1*(int) ConstantesJanela.FATOR_Y, 50*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		if(Settings.isColor)
		{	//Setando as cores se o dispositivos suportar cores.
	    	Color BLUE =ConstantesJanela.BLUE;
		     setForeColor(BLUE); 
		     btnNext.setForeColor(BLUE);
		     btnPrev.setForeColor(BLUE);	
		     lblOutroTpAlimentacao.setForeColor(BLUE);
		     gridAvalAlimentar.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridAvalAlimentar.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		     gridQuadroNutricional.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridQuadroNutricional.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		}		
	}
	
    public void PopulaForm(){
    	try{
    		if("U".equals(ListaFichaCriancaWin.cmd)){  
    			FichaCriancaNgc fichaCriancaNgc = FichaCriancaNgc.criarInstancia();    	    	
    			ResultSet rs = fichaCriancaNgc.getFichaCrianca(FichaCrianca1Win.cdFamilia,FichaCrianca1Win.cdPaciente);
    	    	ResultSet rsAcomp = fichaCriancaNgc.GetFichaCriancaAcomp(FichaCrianca1Win.cdFamilia,FichaCrianca1Win.cdPaciente);
    			String [] arrayMes = {"","JAN","FEV","MAR","ABR","MAI","JUN","JUL","AGO","SET","OUT","NOV","DEZ"};
    			String items[]  = new String[4];
    			while(rsAcomp.next()){
    	    		int mes = rsAcomp.getInt("CD_MES");
    	    		items[0]=arrayMes[mes];
    	    		items[1] = rsAcomp.getString("DT_QUADRONUTRI");
    	    		items[2] = rsAcomp.getString("NUM_PESO");
    	    		items[3] = rsAcomp.getString("NUM_ALTURA");    	    		
    	    		gridQuadroNutricional.setColStrings(mes,items);
    	    	}
    			GridCell cell ;
    			if(rs.next()){
    				cell = new GridCellCheckBox();
    				int tpAlimentacao=rs.getInt("CD_AVALIACAOALIMEN");
    				if(tpAlimentacao==1){    	    			
        	    		((GridCellCheckBox) cell).setChecked(true);  
        	    		gridAvalAlimentar.setCell(new GridCellID(0, 0), cell);
        	    		cell = new GridCellCheckBox();
        	    		((GridCellCheckBox) cell).setChecked(false);  
        	    		gridAvalAlimentar.setCell(new GridCellID(1, 0), cell);
    	    		}else if(tpAlimentacao==2){
    	    			((GridCellCheckBox) cell).setChecked(false);  
        	    		gridAvalAlimentar.setCell(new GridCellID(0, 0), cell);
        	    		cell = new GridCellCheckBox();
        	    		((GridCellCheckBox) cell).setChecked(true);  
        	    		gridAvalAlimentar.setCell(new GridCellID(1, 0), cell);
    	    		}
    	    		
    			}
    		} 
    	}catch (Exception e) {
    		MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+e.getMessage());
			msgBoxErro.popupBlockingModal();
			Vm.debug("Erro: "+e.getMessage());
		}
    }
    
    public void LimpaForm(){
		String[]Linha1={"Data","","","","","","","","","","","",""};
		String[]Linha2={"Peso","","","","","","","","","","","",""};
		String[]Linha3={"Altura","","","","","","","","","","","",""};		
		gridQuadroNutricional.setRowStrings(1,Linha1);
		gridQuadroNutricional.setRowStrings(2,Linha2);
		gridQuadroNutricional.setRowStrings(3,Linha3);
		edtOutroTpAlimentacao.setText("");
		
		GridCell cell ;
		cell = new GridCellCheckBox();
		((GridCellCheckBox) cell).setChecked(false);  
	    gridAvalAlimentar.setCell(new GridCellID(0, 0), cell);
	    cell = new GridCellCheckBox();
		((GridCellCheckBox) cell).setChecked(false);  
		gridAvalAlimentar.setCell(new GridCellID(1, 0), cell);
	    
	}

    public void onEvent(Event e) {	
    	try{
		    if (e.type == ControlEvent.PRESSED) { 
			    if (e.target == btnNext) {
			    	if(ValidaForm()){
			    		MessageBox msgBoxFinaliza = new MessageBox("Fim", "Deseja Finalizar a Ficha Criança?",new String[]{"Sim","Não"});
				    	msgBoxFinaliza.popupBlockingModal();			    	
				    	if(msgBoxFinaliza.getPressedButtonIndex()==0){
				    		FichaCriancaNgc fichaCriancaNgc=FichaCriancaNgc.criarInstancia();
				    		Hashtable voFichaCrianca = new Hashtable(10);
				    		AgenteNgc agenteNgc = AgenteNgc.criarInstancia();
				    		ResultSet rs = agenteNgc.getSegAreaMicroa();
				    		if(rs.next()){
				    			voFichaCrianca.put("CD_SEGMENTO",rs.getString("CD_SEG"));
					    		voFichaCrianca.put("CD_AREA",rs.getString("CD_AREA"));
					    		voFichaCrianca.put("CD_MICROAREA",rs.getString("CD_MICROA"));
				    		}	
				    		
				    		
				    		voFichaCrianca.put("NR_FAMILIA",Convert.toString(FichaCrianca1Win.cdFamilia));
				    		voFichaCrianca.put("CD_PACIENTE",Convert.toString(FichaCrianca1Win.cdPaciente));
				    		GridCell cell;
				    		GridCell cell2;	
				    		cell = new GridCellCheckBox();
				    		cell = gridAvalAlimentar.getCell(new GridCellID(0, 0));
				    		cell2 = new GridCellCheckBox();
				    		cell2 = gridAvalAlimentar.getCell(new GridCellID(1, 0));
				    		if(((GridCellCheckBox) cell).getChecked()){
				    			voFichaCrianca.put("CD_AVALIACAOALIMEN","1");
				    		}else if(((GridCellCheckBox) cell2).getChecked()){
				    			voFichaCrianca.put("CD_AVALIACAOALIMEN","2");
				    		}
				    		
				    		voFichaCrianca.put("DS_TIPOALIMENTACAO",edtOutroTpAlimentacao.getText());
				    		voFichaCrianca.put("FL_IMUNIZADO",FichaCrianca1Win.flImunizado);
				    		voFichaCrianca.put("FL_COMPLETARESQ",FichaCrianca1Win.flCompletarEsq);
				    		voFichaCrianca.put("FL_DIARREIA",FichaCrianca1Win.flDiarreia);
				    		voFichaCrianca.put("FL_USARAMTRO",FichaCrianca1Win.flUsaramTro);
				    		voFichaCrianca.put("FL_INTERNAMENTOS",FichaCrianca1Win.flInternamentos);
				    		voFichaCrianca.put("FL_IRA",FichaCrianca1Win.flIra);
				    		voFichaCrianca.put("FL_CAXUMBA",FichaCrianca1Win.flCaxumba);
				    		voFichaCrianca.put("FL_CATAPORA",FichaCrianca1Win.flCatapora);
				    		voFichaCrianca.put("FL_BAIXOPESONASC",FichaCrianca1Win.flBaixoPeso);
				    		voFichaCrianca.put("FL_DEFICIENCIAS",FichaCrianca1Win.flDeficiencias);
				    		
				    		
				    		int mes = SIABBusiness.getMes();
				    		voFichaCrianca.put("CD_MES",""+SIABBusiness.getMes());
				    		voFichaCrianca.put("CD_ANO",""+SIABBusiness.getAno());
				    		voFichaCrianca.put("DT_QUADRONUTRI",gridQuadroNutricional.getCellText(1,mes));
				    		voFichaCrianca.put("NUM_PESO",gridQuadroNutricional.getCellText(2,mes));
				    		voFichaCrianca.put("NUM_ALTURA",gridQuadroNutricional.getCellText(3,mes));
				    		if("U".equals(ListaFichaCriancaWin.cmd)){
				    			fichaCriancaNgc.UpdateFichaCrianca(voFichaCrianca);
				    		}else{
				    			fichaCriancaNgc.InserteFichaCrianca(voFichaCrianca);
				    		}
				    		ListaFichaCriancaWin.cmd="F";
  						    unpop();
				    	}
			    	}
			    }else if (e.target == btnPrev){
			    	unpop();
			    }
		    }else if(e.type == GridEvent.CELL_DOUBLE_CLICKED){
		    	
		    	if(e.target==gridQuadroNutricional){
		    		EntraDataGrid();
		    	}
		    	GridCell cell;
		    	GridEvent ge = (GridEvent) e;
		    	if(e.target==gridAvalAlimentar){
		    		if(ge.cellID.Col==0){
			    		int linha=ge.cellID.Row;		    		
			    		for(int i=0;i<2;i++){		    			
			    			if(linha!=i){			    				
					    		cell = new GridCellCheckBox();
					    		((GridCellCheckBox) cell).setChecked(false);
					    		gridAvalAlimentar.setCell(new GridCellID(i, 0), cell);   				
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
		   
	private void EntraDataGrid(){
		 MessageBox msgGrid =null;
		 InputDialog id =null;
		 String[]pergunta={"Data","Peso","Altura"};
		   
	     if(gridQuadroNutricional.getUserSelectedCol()>0){	    	
		    	if(gridQuadroNutricional.getUserSelectedCol()==(new BBDate()).getMonth()){
				    	switch (gridQuadroNutricional.getUserSelectedRow()) {
				    	case 1:	
				    		calendar=new Calendar();
				    		calendar.setSelectedDate(new Date(gridQuadroNutricional.getCellText(gridQuadroNutricional.getUserSelectedCellID())));
				    		calendar.popupBlockingModal();
				    		calendar.popupBlockingModal();
				    		/*popupModal(calendar);
				    		unpop();
				    		popupBlockingModal(calendar);*/
				    		gridQuadroNutricional.setCellText(gridQuadroNutricional.getUserSelectedCellID(),calendar.getSelectedDate()==null?"":calendar.getSelectedDate().toString());
				    		/*id = new InputDialog("Atenção",
							  		pergunta[gridQuadroNutricional.getUserSelectedRow()-1],
							  		gridQuadroNutricional.getCellText(gridQuadroNutricional.getUserSelectedCellID()),new String[]{"Ok","Cancel"});
				    		id.setForeColor(ConstantesJanela.BLUE);
							id.popupBlockingModal();							
							if (id.getPressedButtonIndex() == 0){
								gridQuadroNutricional.setCellText(gridQuadroNutricional.getUserSelectedCellID(),id.getValue());
							  }	*/
							break;				    
				    	case 2:									
				    		 id = new InputDialog("Atenção",
							  		pergunta[gridQuadroNutricional.getUserSelectedRow()-1],
							  		gridQuadroNutricional.getCellText(gridQuadroNutricional.getUserSelectedCellID()),new String[]{"Ok","Cancel"});
							id.setForeColor(ConstantesJanela.BLUE);
							id.popupBlockingModal();
							if (id.getPressedButtonIndex() == 0){
								gridQuadroNutricional.setCellText(gridQuadroNutricional.getUserSelectedCellID(),id.getValue());
							  }	
							break;
						case 3:		
							 id = new InputDialog("Atenção", pergunta[gridQuadroNutricional.getUserSelectedRow()-1],
				    	    		  		gridQuadroNutricional.getCellText(gridQuadroNutricional.getUserSelectedCellID()),new String[]{"Ok","Cancel"});
							id.setForeColor(ConstantesJanela.BLUE);
							id.popupBlockingModal();
							if (id.getPressedButtonIndex() == 0){
								gridQuadroNutricional.setCellText(gridQuadroNutricional.getUserSelectedCellID(),id.getValue());
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

	  private boolean ValidaForm(){		 
		  String[]pergunta={"Preencha o Campo|Data","Preencha o Campo|Peso",
				  			"Preencha o Campo|Altura"};
		  MessageBox msgGrid;
		     
		    int mes = SIABBusiness.getMes();
		    if("".equals(gridQuadroNutricional.getCellText(1,mes))){
		    	msgGrid = new MessageBox("Atenção",pergunta[0], new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
		    }
		    if("".equals(gridQuadroNutricional.getCellText(2,mes))){
		    	msgGrid = new MessageBox("Atenção",pergunta[1], new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
		    }
		    if("".equals(gridQuadroNutricional.getCellText(3,mes))){
		    	msgGrid = new MessageBox("Atenção",pergunta[2], new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
		    }		   		    
			return true;
	  }
}
