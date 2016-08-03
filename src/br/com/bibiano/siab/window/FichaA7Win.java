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
public class FichaA7Win extends Window {
	private static FichaA7Win fichaA7Win;
	
	private Label lblGrupoComunitario;
	private Grid gridGruposComunitarios;
	private Label lblGruposComunitariosOutros;
	private Edit edtgruposComunitariosOutros;
	private Label lblMeiosTransporteOutros;
	private Edit edtMeiosTransporteOutros;
	private Grid gridMeiosTransporte;
	private Label lblMeiosTransporte;
		
	private Button btnPrev;
	private Button btnNext;
	
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static FichaA7Win criarInstancia()
	{
		if(fichaA7Win == null)
		{
			fichaA7Win = new FichaA7Win();
		}
		return fichaA7Win;
	}
	
	public FichaA7Win(){
		
		super("Ficha A7",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		
		add(lblGrupoComunitario = new Label("Participa de Grupos Comunitários:"),LEFT + 3*(int) ConstantesJanela.FATOR_X,TOP  + 1*(int) ConstantesJanela.FATOR_Y);
		
		add(gridGruposComunitarios = new Grid());
		gridGruposComunitarios.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 1*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 42*(int) ConstantesJanela.FATOR_Y);
		gridGruposComunitarios.setStorage(new GridStorageSingleHashtable());
		gridGruposComunitarios.setAllowSortRows(false);
		gridGruposComunitarios.setAllowSortCols(false);

		gridGruposComunitarios.setHighlightSelectedCell(true);

		gridGruposComunitarios.setRowCount(3);
		gridGruposComunitarios.setColCount(2);
		
		gridGruposComunitarios.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);

		gridGruposComunitarios.setColSeparatorWidth(1);
		gridGruposComunitarios.setRowSeparatorHeight(1);
		gridGruposComunitarios.setDrawColSeparators(true);
		gridGruposComunitarios.setDrawRowSeparators(true);
		gridGruposComunitarios.setUpdating(false);
		gridGruposComunitarios.setFullRowSelectionDisplay(false);
		gridGruposComunitarios.lockColEditing(1);
		
		gridGruposComunitarios.setColCellType(0, new GridCellCheckBox().getClass());
		gridGruposComunitarios.setColCellType(1, new GridCellText().getClass());
		
		gridGruposComunitarios.setColWidth(0, 15*(int)ConstantesJanela.FATOR_X);
		gridGruposComunitarios.setColWidth(1, 136*(int)ConstantesJanela.FATOR_X);
		
		
		gridGruposComunitarios.setCellText(0,1,"COOPERATIVA");
		gridGruposComunitarios.setCellText(1,1,"GRUPO RELIGIOSO");
		gridGruposComunitarios.setCellText(2,1,"ASSOCIAÇÕES");
		gridGruposComunitarios.setHorizontalScrollVisible(false);
		gridGruposComunitarios.setVerticalScrollVisible(false);
		
		
		add(lblGruposComunitariosOutros = new Label("Outros:"),LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER);
		add(edtgruposComunitariosOutros = new Edit());
		edtgruposComunitariosOutros.setRect(AFTER + 1*(int) ConstantesJanela.FATOR_X,SAME, FILL - 3*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		add(lblMeiosTransporte = new Label("Meios de Transporte que mais Utiliza:"),LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER);				
		add(gridMeiosTransporte = new Grid());
		gridMeiosTransporte.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER,FILL - 3*(int) ConstantesJanela.FATOR_X, 34*(int) ConstantesJanela.FATOR_Y);
		gridMeiosTransporte.setStorage(new GridStorageSingleHashtable());
		gridMeiosTransporte.setAllowSortRows(false);
		gridMeiosTransporte.setAllowSortCols(false);
		gridMeiosTransporte.setHighlightSelectedCell(true);
		gridMeiosTransporte.setRowCount(4);
		gridMeiosTransporte.setColCount(2);
		
		gridMeiosTransporte.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);

		gridMeiosTransporte.setColSeparatorWidth(1);
		gridMeiosTransporte.setRowSeparatorHeight(1);
		gridMeiosTransporte.setDrawColSeparators(true);
		gridMeiosTransporte.setDrawRowSeparators(true);
		gridMeiosTransporte.setUpdating(false);
		gridMeiosTransporte.setFullRowSelectionDisplay(false);
		gridMeiosTransporte.lockColEditing(1);
		
		gridMeiosTransporte.setColCellType(0, new GridCellCheckBox().getClass());
		gridMeiosTransporte.setColCellType(1, new GridCellText().getClass());
		
		gridMeiosTransporte.setColWidth(0, 15*(int)ConstantesJanela.FATOR_X);
		gridMeiosTransporte.setColWidth(1, 124*(int)ConstantesJanela.FATOR_X);
		gridMeiosTransporte.setCellText(0,1,"ÔNIBUS");
		gridMeiosTransporte.setCellText(1,1,"CAMINHÃO");
		gridMeiosTransporte.setCellText(2,1,"CARRO");
		gridMeiosTransporte.setCellText(3,1,"CARROÇA");
		
		gridMeiosTransporte.setHorizontalScrollVisible(false);
		gridMeiosTransporte.setVerticalScrollVisible(true);
		
		add(lblMeiosTransporteOutros = new Label("Outros:"),LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER);
		add(edtMeiosTransporteOutros = new Edit());
		edtMeiosTransporteOutros.setRect(AFTER + 3*(int) ConstantesJanela.FATOR_X,SAME, FILL - 3*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		
		add(btnPrev = new Button("<<"));
		btnPrev.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		add(btnNext = new Button(">>"));
		btnNext.setRect(RIGHT - 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		if(Settings.isColor)
		{	//Setando as cores se o dispositivos suportar cores.
	    	Color BLUE =ConstantesJanela.BLUE;
		     setForeColor(BLUE);
		     lblGrupoComunitario.setForeColor(BLUE);
		     lblGruposComunitariosOutros.setForeColor(BLUE);
		     lblMeiosTransporteOutros.setForeColor(BLUE);
		     lblMeiosTransporte.setForeColor(BLUE);
		     btnNext.setForeColor(BLUE);
		     btnPrev.setForeColor(BLUE);
		     gridGruposComunitarios.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridGruposComunitarios.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		     gridMeiosTransporte.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridMeiosTransporte.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		}
	}
	
	protected void PopulaForm() {
		GridCellID cellID;
		GridCell cell;
		cellID = new GridCellID(FichaA2Win.cdGrupoComu-1, 0);
		cell = new GridCellCheckBox();
		((GridCellCheckBox) cell).setChecked(true);
		gridGruposComunitarios.setCell(cellID, cell);
		edtgruposComunitariosOutros.setText(FichaA2Win.dsGrupoComuEsp);		
		
		cellID = new GridCellID(FichaA2Win.cdMeiosTrans-1, 0);
		cell = new GridCellCheckBox();
		((GridCellCheckBox) cell).setChecked(true);
		gridMeiosTransporte.setCell(cellID, cell);
		edtMeiosTransporteOutros.setText(FichaA2Win.dsMeiosComuniEsp);
	}
	
	protected void LimpaForm() {
		GridCellID cellID;
		GridCell cell;
		int i=0;
		for( i=0;i<3;i++){	
			cellID = new GridCellID(i, 0);
			cell = new GridCellCheckBox();
			((GridCellCheckBox) cell).setChecked(false);
			gridGruposComunitarios.setCell(cellID, cell);			
		}
		edtgruposComunitariosOutros.setText("");
		for( i=0;i<4;i++){	
			cellID = new GridCellID(i, 0);
			cell = new GridCellCheckBox();
			((GridCellCheckBox) cell).setChecked(false);
			gridMeiosTransporte.setCell(cellID, cell);
		}
		edtMeiosTransporteOutros.setText("");
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
				   FichaA8Win fichaA8Win= FichaA8Win.criarInstancia();
				   fichaA8Win.LimpaForm();
				   if(ListaFamiliaWin.cmd.equals("U")){
					   fichaA8Win.PopulaForm();
				   }
				   fichaA8Win.popupBlockingModal();
				   if("F".equals(ListaFamiliaWin.cmd)){
					   unpop();   
				   }				   
			   }
		    }else if(e.type ==GridEvent.CELL_DOUBLE_CLICKED){
		    	if(e.target==gridGruposComunitarios){
		    		GridEvent ge = (GridEvent) e;
			    	if(ge.cellID.Col==0){
			    		int linha=ge.cellID.Row;
			    		for(int i=0;i<5;i++){		    			
			    			if(linha!=i){
			    				cellID = new GridCellID(i, 0);
					    		cell = new GridCellCheckBox();
					    		((GridCellCheckBox) cell).setChecked(false);
					    		gridGruposComunitarios.setCell(cellID, cell);   				
			    			}
			    		}
			    	}
		    	}else if(e.target==gridMeiosTransporte){
		    		GridEvent ge = (GridEvent) e;
			    	if(ge.cellID.Col==0){
			    		int linha=ge.cellID.Row;
			    		for(int i=0;i<5;i++){		    			
			    			if(linha!=i){
			    				cellID = new GridCellID(i, 0);
					    		cell = new GridCellCheckBox();
					    		((GridCellCheckBox) cell).setChecked(false);
					    		gridMeiosTransporte.setCell(cellID, cell);   				
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
		boolean isGruposComunitarios =false;
		int i=0;
    	for(i=0;i<5;i++){
    		 cellID = new GridCellID(i, 0);
	    	 cell = new GridCellCheckBox();
	    	 cell = gridGruposComunitarios.getCell(cellID);
	    	 isGruposComunitarios = ((GridCellCheckBox) cell).getChecked();
	    	if(isGruposComunitarios){
	    		break;
	    	}
 		}  
    	if(isGruposComunitarios){
    		FichaA2Win.cdGrupoComu=i+1;
    	}else
    	{
    		FichaA2Win.cdGrupoComu=0;
    	}
    	
    	FichaA2Win.dsGrupoComuEsp = edtgruposComunitariosOutros.getText();
    	
    	boolean isMeiosTransporte =false;		
    	for(i=0;i<5;i++){
    		 cellID = new GridCellID(i, 0);
	    	 cell = new GridCellCheckBox();
	    	 cell = gridMeiosTransporte.getCell(cellID);
	    	 isMeiosTransporte = ((GridCellCheckBox) cell).getChecked();
	    	if(isMeiosTransporte){
	    		break;
	    	}
 		} 
    	if(isMeiosTransporte){
    		FichaA2Win.cdMeiosTrans=i+1;	
    	}else{
    		FichaA2Win.cdMeiosTrans=0;
    	}
    	
    	FichaA2Win.dsMeiosTransEsp=edtMeiosTransporteOutros.getText();
    }
}
