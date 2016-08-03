package br.com.bibiano.siab.window;


import waba.fx.Color;
import waba.sys.Settings;
import waba.sys.Vm;
import waba.ui.Button;
import waba.ui.ControlEvent;
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
public class FichaA40Win extends Window {
	private static FichaA40Win fichaA4Win;
	
	private Label lbllixo;
	private Grid gridLixo;
	private Grid gridTrataAgua;
	private Label lblTrataAgua;
		
	private Button btnPrev;
	private Button btnNext;
	
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static FichaA40Win criarInstancia()
	{
		if(fichaA4Win == null)
		{
			fichaA4Win = new FichaA40Win();
		}
		return fichaA4Win;
	}
	
	public FichaA40Win(){
		
		super("Ficha A4",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(lbllixo = new Label("Destino do Lixo:"),LEFT + 3*(int) ConstantesJanela.FATOR_X,TOP  + 3*(int) ConstantesJanela.FATOR_Y);
		add(gridLixo = new Grid());
		gridLixo.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 1*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 42*(int) ConstantesJanela.FATOR_Y);
		gridLixo.setStorage(new GridStorageSingleHashtable());
		gridLixo.setAllowSortRows(false);
		gridLixo.setAllowSortCols(false);
		gridLixo.setHighlightSelectedCell(true);
		gridLixo.setRowCount(3);
		gridLixo.setColCount(2);		
		gridLixo.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);
		gridLixo.setColSeparatorWidth(1);
		gridLixo.setRowSeparatorHeight(1);
		gridLixo.setDrawColSeparators(true);
		gridLixo.setDrawRowSeparators(true);
		gridLixo.setUpdating(false);
		gridLixo.setFullRowSelectionDisplay(false);
		gridLixo.lockColEditing(1);		
		gridLixo.setColCellType(0, new GridCellCheckBox().getClass());
		gridLixo.setColCellType(1, new GridCellText().getClass());		
		gridLixo.setColWidth(0, 15*(int)ConstantesJanela.FATOR_X);
		gridLixo.setColWidth(1, 136*(int)ConstantesJanela.FATOR_X);
		gridLixo.setCellText(0,1,"COLETADO");
		gridLixo.setCellText(1,1,"QUEIMADO/ENTERRADO");
		gridLixo.setCellText(2,1,"CÉU ABERTO");
		gridLixo.setHorizontalScrollVisible(false);
		gridLixo.setVerticalScrollVisible(false);
		
		add(lblTrataAgua = new Label("Tratamento de Água no Domicílio:"),LEFT + 2*(int) ConstantesJanela.FATOR_X, AFTER+ 3*(int) ConstantesJanela.FATOR_Y);
		add(gridTrataAgua = new Grid());
		gridTrataAgua.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 1*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 55*(int) ConstantesJanela.FATOR_Y);
		gridTrataAgua.setStorage(new GridStorageSingleHashtable());
		gridTrataAgua.setAllowSortRows(false);
		gridTrataAgua.setAllowSortCols(false);
		gridTrataAgua.setHighlightSelectedCell(true);
		gridTrataAgua.setRowCount(4);
		gridTrataAgua.setColCount(2);		
		gridTrataAgua.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);
		gridTrataAgua.setColSeparatorWidth(1);
		gridTrataAgua.setRowSeparatorHeight(1);
		gridTrataAgua.setDrawColSeparators(true);
		gridTrataAgua.setDrawRowSeparators(true);
		gridTrataAgua.setUpdating(false);
		gridTrataAgua.setFullRowSelectionDisplay(false);
		gridTrataAgua.lockColEditing(1);
		gridTrataAgua.setColCellType(0, new GridCellCheckBox().getClass());
		gridTrataAgua.setColCellType(1, new GridCellText().getClass());		
		gridTrataAgua.setColWidth(0, 15*(int)ConstantesJanela.FATOR_X);
		gridTrataAgua.setColWidth(1, 136*(int)ConstantesJanela.FATOR_X);
		gridTrataAgua.setCellText(0,1,"FILTRAÇÃO");
		gridTrataAgua.setCellText(1,1,"FERVURA");
		gridTrataAgua.setCellText(2,1,"CLORAÇÃO");
		gridTrataAgua.setCellText(3,1,"SEM TRATAMENTO");
		gridTrataAgua.setHorizontalScrollVisible(false);
		gridTrataAgua.setVerticalScrollVisible(false);
		
		
		add(btnPrev = new Button("<<"));
		btnPrev.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);		
		add(btnNext = new Button(">>"));
		btnNext.setRect(RIGHT - 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		if(Settings.isColor)
		{	//Setando as cores se o dispositivos suportar cores.
	    	Color BLUE =ConstantesJanela.BLUE;
		     setForeColor(BLUE);
		     lbllixo.setForeColor(BLUE);
		     lblTrataAgua.setForeColor(BLUE);
		     btnNext.setForeColor(BLUE);
		     btnPrev.setForeColor(BLUE);
		     gridLixo.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridLixo.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		     gridTrataAgua.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridTrataAgua.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		   
		}
		
	}
	protected void PopulaForm() {
		GridCellID cellID;
		GridCell cell;
		cellID = new GridCellID(FichaA2Win.cdDestLixo-1, 0);
		cell = new GridCellCheckBox();
		((GridCellCheckBox) cell).setChecked(true);
		gridLixo.setCell(cellID, cell);		
		
		cellID = new GridCellID(FichaA2Win.cdTrataAgua-1, 0);
		cell = new GridCellCheckBox();
		((GridCellCheckBox) cell).setChecked(true);
		gridTrataAgua.setCell(cellID, cell);
	}
	
	protected void LimpaForm() {
		GridCellID cellID;
		GridCell cell;
		int i=0;
		for( i=0;i<3;i++){			
			cellID = new GridCellID(i, 0);
			cell = new GridCellCheckBox();
			((GridCellCheckBox) cell).setChecked(false);
			gridLixo.setCell(cellID, cell);
		}
		for(i=0;i<4;i++){	
			cellID = new GridCellID(i, 0);
			cell = new GridCellCheckBox();
			((GridCellCheckBox) cell).setChecked(false);
			gridTrataAgua.setCell(cellID, cell);
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
				   FichaA5Win fichaA5Win = FichaA5Win.criarInstancia();
				   fichaA5Win.LimpaForm();
				   if(ListaFamiliaWin.cmd.equals("U")){
					   fichaA5Win.PopulaForm();
				   }
				   fichaA5Win.popupBlockingModal();				   
				   if("F".equals(ListaFamiliaWin.cmd)){
					   unpop();   
				   }
			   }
		    }else if(e.type ==GridEvent.CELL_DOUBLE_CLICKED){
		    	if(e.target==gridLixo){
		    		GridEvent ge = (GridEvent) e;
			    	if(ge.cellID.Col==0){
			    		int linha=ge.cellID.Row;
			    		for(int i=0;i<5;i++){		    			
			    			if(linha!=i){
			    				cellID = new GridCellID(i, 0);
					    		cell = new GridCellCheckBox();
					    		((GridCellCheckBox) cell).setChecked(false);
					    		gridLixo.setCell(cellID, cell);   				
			    			}
			    		}
			    	}
		    	}else if(e.target==gridTrataAgua){
		    		GridEvent ge = (GridEvent) e;
			    	if(ge.cellID.Col==0){
			    		int linha=ge.cellID.Row;
			    		for(int i=0;i<5;i++){		    			
			    			if(linha!=i){
			    				cellID = new GridCellID(i, 0);
					    		cell = new GridCellCheckBox();
					    		((GridCellCheckBox) cell).setChecked(false);
					    		gridTrataAgua.setCell(cellID, cell);   				
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
		boolean booTpCasa =false;
		int i=0;
    	for(i=0;i<5;i++){
    		 cellID = new GridCellID(i, 0);
	    	 cell = new GridCellCheckBox();
	    	 cell = gridLixo.getCell(cellID);
	    	booTpCasa = ((GridCellCheckBox) cell).getChecked();
	    	if(booTpCasa){
	    		break;
	    	}
 		}
    	if(booTpCasa){
    		FichaA2Win.cdDestLixo=i+1;	
    	}else{
    		FichaA2Win.cdDestLixo=0;
    	}
    		
    	
    	 
    	for(i=0;i<5;i++){
    		 cellID = new GridCellID(i, 0);
	    	 cell = new GridCellCheckBox();
	    	 cell = gridTrataAgua.getCell(cellID);
	    	booTpCasa = ((GridCellCheckBox) cell).getChecked();
	    	if(booTpCasa){
	    		break;
	    	}
 		} 
    	if(booTpCasa){
    		FichaA2Win.cdTrataAgua =i+1;
    	}
    	else{
    		FichaA2Win.cdTrataAgua =0;
    	}
    	
    }
}
