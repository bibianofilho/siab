package br.com.bibiano.siab.window;

import waba.fx.Color;
import waba.sys.Settings;
import waba.sys.Vm;
import waba.ui.Button;
import waba.ui.ControlEvent;
import waba.ui.Edit;
import waba.ui.Event;
import waba.ui.Label;
import waba.ui.MessageBox;
import waba.ui.Window;
import wextlib.ui.grid.Grid;
import wextlib.ui.grid.GridCell;
import wextlib.ui.grid.GridCellCheckBox;
import wextlib.ui.grid.GridCellID;
import wextlib.ui.grid.GridCellText;
import wextlib.ui.grid.GridEvent;
import wextlib.ui.grid.GridStorageSingleHashtable;
/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class FichaA5Win extends Window {
	private static FichaA5Win fichaA5Win;
	
	private Label lblAbastecimentoAgua;
	private Grid gridAbastecimentoAgua;
	private Label lblAbstecimentoAguaOutros;
	private Edit edtAbstecimentoAguaOutros;
	private Grid gridDestinoFezes;
	private Label lblDestinoFezes;
		
	private Button btnPrev;
	private Button btnNext;
	
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static FichaA5Win criarInstancia()
	{
		if(fichaA5Win == null)
		{
			fichaA5Win = new FichaA5Win();
		}
		return fichaA5Win;
	}
	
	public FichaA5Win(){
		
		super("Ficha A5",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(lblAbastecimentoAgua = new Label("Abastecimento de Água:"),LEFT + 3*(int) ConstantesJanela.FATOR_X,TOP  + 3*(int) ConstantesJanela.FATOR_Y);
		add(gridAbastecimentoAgua = new Grid());
		gridAbastecimentoAgua.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 3*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 29*(int) ConstantesJanela.FATOR_Y);
		gridAbastecimentoAgua.setStorage(new GridStorageSingleHashtable());
		gridAbastecimentoAgua.setAllowSortRows(false);
		gridAbastecimentoAgua.setAllowSortCols(false);
		gridAbastecimentoAgua.setHighlightSelectedCell(true);
		gridAbastecimentoAgua.setRowCount(2);
		gridAbastecimentoAgua.setColCount(2);		
		gridAbastecimentoAgua.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);
		gridAbastecimentoAgua.setColSeparatorWidth(1);
		gridAbastecimentoAgua.setRowSeparatorHeight(1);
		gridAbastecimentoAgua.setDrawColSeparators(true);
		gridAbastecimentoAgua.setDrawRowSeparators(true);
		gridAbastecimentoAgua.setUpdating(false);
		gridAbastecimentoAgua.setFullRowSelectionDisplay(false);
		gridAbastecimentoAgua.lockColEditing(1);
		gridAbastecimentoAgua.setColCellType(0, new GridCellCheckBox().getClass());
		gridAbastecimentoAgua.setColCellType(1, new GridCellText().getClass());		
		gridAbastecimentoAgua.setColWidth(0, 15*(int)ConstantesJanela.FATOR_X);
		gridAbastecimentoAgua.setColWidth(1, 136*(int)ConstantesJanela.FATOR_X);
		gridAbastecimentoAgua.setCellText(0,1,"REDE PÚBLICA");
		gridAbastecimentoAgua.setCellText(1,1,"POÇO OU NASCENTE");		
		gridAbastecimentoAgua.setHorizontalScrollVisible(false);
		gridAbastecimentoAgua.setVerticalScrollVisible(false);
		
		add(lblAbstecimentoAguaOutros = new Label("Outros:"),LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 3*(int) ConstantesJanela.FATOR_Y);
		add(edtAbstecimentoAguaOutros = new Edit());
		edtAbstecimentoAguaOutros.setRect(AFTER + 3*(int) ConstantesJanela.FATOR_X,SAME, FILL - 3*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		add(lblDestinoFezes = new Label("Destino de Fezes e Urina:"),LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 3*(int) ConstantesJanela.FATOR_Y);
		add(gridDestinoFezes = new Grid());
		gridDestinoFezes.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 3*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 42*(int) ConstantesJanela.FATOR_Y);
		gridDestinoFezes.setStorage(new GridStorageSingleHashtable());
		gridDestinoFezes.setAllowSortRows(false);
		gridDestinoFezes.setAllowSortCols(false);
		gridDestinoFezes.setHighlightSelectedCell(true);
		gridDestinoFezes.setRowCount(3);
		gridDestinoFezes.setColCount(2);		
		gridDestinoFezes.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0),ConstantesJanela.BLUEGRID);
		gridDestinoFezes.setColSeparatorWidth(1);
		gridDestinoFezes.setRowSeparatorHeight(1);
		gridDestinoFezes.setDrawColSeparators(true);
		gridDestinoFezes.setDrawRowSeparators(true);
		gridDestinoFezes.setUpdating(false);
		gridDestinoFezes.setFullRowSelectionDisplay(false);
		gridDestinoFezes.lockColEditing(1);		
		gridDestinoFezes.setColCellType(0, new GridCellCheckBox().getClass());
		gridDestinoFezes.setColCellType(1, new GridCellText().getClass());		
		gridDestinoFezes.setColWidth(0, 15*(int)ConstantesJanela.FATOR_X);
		gridDestinoFezes.setColWidth(1, 136*(int)ConstantesJanela.FATOR_X);
		gridDestinoFezes.setCellText(0,1,"SISTEMA DE ESGOTO-REDE GERAL)");
		gridDestinoFezes.setCellText(1,1,"FOSSA");
		gridDestinoFezes.setCellText(2,1,"CÉU ABERTO");		
		gridDestinoFezes.setHorizontalScrollVisible(false);
		gridDestinoFezes.setVerticalScrollVisible(false);
		
		add(btnPrev = new Button("<<"));
		btnPrev.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		add(btnNext = new Button(">>"));
		btnNext.setRect(RIGHT - 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		if(Settings.isColor)
		{	//Setando as cores se o dispositivos suportar cores.
	    	Color BLUE =ConstantesJanela.BLUE;
		     setForeColor(BLUE);
		   lblAbastecimentoAgua.setForeColor(BLUE);
		   lblAbstecimentoAguaOutros.setForeColor(BLUE);
		   lblDestinoFezes.setForeColor(BLUE);
		   btnNext.setForeColor(BLUE);
		   btnPrev.setForeColor(BLUE);	
		   gridAbastecimentoAgua.setSelectedCellForeColor(new Color(0, 0, 0));
		   gridAbastecimentoAgua.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		   gridDestinoFezes.setSelectedCellForeColor(new Color(0, 0, 0));
		   gridDestinoFezes.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		}
		
	}
		
	protected void PopulaForm() {
		GridCellID cellID;
		GridCell cell;
		cellID = new GridCellID(FichaA2Win.cdAbasteAgua-1, 0);
		cell = new GridCellCheckBox();
		((GridCellCheckBox) cell).setChecked(true);
		gridAbastecimentoAgua.setCell(cellID, cell);
		
		//edtAbstecimentoAguaOutros.setText(FichaA2Win.ds)
		cellID = new GridCellID(FichaA2Win.cdFezesUrina-1, 0);
		cell = new GridCellCheckBox();
		((GridCellCheckBox) cell).setChecked(true);
		gridDestinoFezes.setCell(cellID, cell);
	}
	
	protected void LimpaForm() {
		GridCellID cellID;
		GridCell cell;
		int i=0;
		for( i=0;i<2;i++){	
			cellID = new GridCellID(i, 0);
			cell = new GridCellCheckBox();
			((GridCellCheckBox) cell).setChecked(false);
			gridAbastecimentoAgua.setCell(cellID, cell);
		}
		for( i=0;i<3;i++){	
			cellID = new GridCellID(i, 0);
			cell = new GridCellCheckBox();
			((GridCellCheckBox) cell).setChecked(false);
			gridDestinoFezes.setCell(cellID, cell);
		}
	}

    public void onEvent(Event e) {	
    	GridCellID cellID;
		GridCell cell;
    	try{
		    if (e.type == ControlEvent.PRESSED) { 
			   if(e.target==btnPrev){
				   unpop();
			   }else if(e.target==btnNext){
				   PreencheAtributo();
				   FichaA6Win fichaA6Win = FichaA6Win.criarInstancia();
				   fichaA6Win.LimpaForm();
				   if(ListaFamiliaWin.cmd.equals("U")){
					   fichaA6Win.PopulaForm();
				   }
				   fichaA6Win.popupBlockingModal();
				   if("F".equals(ListaFamiliaWin.cmd)){
					   unpop();   
				   }
			   }
		    }else if(e.type ==GridEvent.CELL_DOUBLE_CLICKED){
		    	if(e.target==gridAbastecimentoAgua){
		    		GridEvent ge = (GridEvent) e;
			    	if(ge.cellID.Col==0){
			    		int linha=ge.cellID.Row;
			    		for(int i=0;i<5;i++){		    			
			    			if(linha!=i){
			    				cellID = new GridCellID(i, 0);
					    		cell = new GridCellCheckBox();
					    		((GridCellCheckBox) cell).setChecked(false);
					    		gridAbastecimentoAgua.setCell(cellID, cell);   				
			    			}
			    		}
			    	}
		    	}else if(e.target==gridDestinoFezes){
		    		GridEvent ge = (GridEvent) e;
			    	if(ge.cellID.Col==0){
			    		int linha=ge.cellID.Row;
			    		for(int i=0;i<5;i++){		    			
			    			if(linha!=i){
			    				cellID = new GridCellID(i, 0);
					    		cell = new GridCellCheckBox();
					    		((GridCellCheckBox) cell).setChecked(false);
					    		gridDestinoFezes.setCell(cellID, cell);   				
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
    
    private void PreencheAtributo(){    	
    	GridCellID cellID;
		GridCell cell;
		boolean booAbstecimentoAgua =false;
		int i=0;
    	for(i=0;i<5;i++){
    		 cellID = new GridCellID(i, 0);
	    	 cell = new GridCellCheckBox();
	    	 cell = gridAbastecimentoAgua.getCell(cellID);
	    	 booAbstecimentoAgua = ((GridCellCheckBox) cell).getChecked();
	    	if(booAbstecimentoAgua){
	    		break;
	    	}
 		} 
    	if(booAbstecimentoAgua){
    		FichaA2Win.cdAbasteAgua=i+1;	
    	}else{
    		FichaA2Win.cdAbasteAgua=0;
    	}
    	
    	 
    	boolean booFezesUrina =false;
    	for(i=0;i<5;i++){
   		 cellID = new GridCellID(i, 0);
	    	 cell = new GridCellCheckBox();
	    	 cell = gridAbastecimentoAgua.getCell(cellID);
	    	 booFezesUrina = ((GridCellCheckBox) cell).getChecked();
	    	if(booFezesUrina){
	    		break;
	    	}
		}  
    	if(booFezesUrina){
    		FichaA2Win.cdFezesUrina=i+1;	
    	}else{
    		FichaA2Win.cdFezesUrina=0;
    	}
    	    		
    }
}
