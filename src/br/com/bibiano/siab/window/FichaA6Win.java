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
public class FichaA6Win extends Window {
	private static FichaA6Win fichaA6Win;
	
	private Label lblDoencaProcurar;
	private Grid gridDoencaProcurar;
	private Label lblDoencaProcurarOutros;
	private Edit edtDoencaProcurarOutros;
	private Label lblComunicacoesOutros;
	private Edit edtComunicacoesOutros;
	private Grid gridMeiosComunicacoes;
	private Label lblMeiosComunicacoes;		
	private Button btnPrev;
	private Button btnNext;
	
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static FichaA6Win criarInstancia()
	{
		if(fichaA6Win == null)
		{
			fichaA6Win = new FichaA6Win();
		}
		return fichaA6Win;
	}
	
	public FichaA6Win(){
		
		super("Ficha A6",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		
		add(lblDoencaProcurar = new Label("Em caso de Doença Procurar:"),LEFT + 3*(int) ConstantesJanela.FATOR_X,TOP  + 3*(int) ConstantesJanela.FATOR_Y);		
		add(gridDoencaProcurar = new Grid());
		gridDoencaProcurar.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 1*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 38*(int) ConstantesJanela.FATOR_Y);
		gridDoencaProcurar.setStorage(new GridStorageSingleHashtable());
		gridDoencaProcurar.setAllowSortRows(false);
		gridDoencaProcurar.setAllowSortCols(false);
		gridDoencaProcurar.setHighlightSelectedCell(true);
		gridDoencaProcurar.setRowCount(4);
		gridDoencaProcurar.setColCount(2);		
		gridDoencaProcurar.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0),ConstantesJanela.BLUEGRID);
		gridDoencaProcurar.setColSeparatorWidth(1);
		gridDoencaProcurar.setRowSeparatorHeight(1);
		gridDoencaProcurar.setDrawColSeparators(true);
		gridDoencaProcurar.setDrawRowSeparators(true);
		gridDoencaProcurar.setUpdating(false);
		gridDoencaProcurar.setFullRowSelectionDisplay(false);
		gridDoencaProcurar.lockColEditing(1);		
		gridDoencaProcurar.setColCellType(0, new GridCellCheckBox().getClass());
		gridDoencaProcurar.setColCellType(1, new GridCellText().getClass());		
		gridDoencaProcurar.setColWidth(0, 15*(int)ConstantesJanela.FATOR_X);
		gridDoencaProcurar.setColWidth(1, 125*(int)ConstantesJanela.FATOR_X);
		gridDoencaProcurar.setCellText(0,1,"HOSPITAL");
		gridDoencaProcurar.setCellText(1,1,"UNIDADE DE SAÚDE");
		gridDoencaProcurar.setCellText(2,1,"BENZEDEIRA");
		gridDoencaProcurar.setCellText(3,1,"FARMÁCIA");		
		gridDoencaProcurar.setHorizontalScrollVisible(false);
		
		
		add(lblDoencaProcurarOutros = new Label("Outros:"),LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 3*(int) ConstantesJanela.FATOR_Y);
		add(edtDoencaProcurarOutros = new Edit());
		edtDoencaProcurarOutros.setRect(AFTER + 3*(int) ConstantesJanela.FATOR_X,SAME, FILL - 3*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		add(lblMeiosComunicacoes = new Label("Meios de Comunicação mais Utilizado:"),LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 1*(int) ConstantesJanela.FATOR_Y);				
		add(gridMeiosComunicacoes = new Grid());
		gridMeiosComunicacoes.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 1*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 29*(int) ConstantesJanela.FATOR_Y);
		gridMeiosComunicacoes.setStorage(new GridStorageSingleHashtable());
		gridMeiosComunicacoes.setAllowSortRows(false);
		gridMeiosComunicacoes.setAllowSortCols(false);
		gridMeiosComunicacoes.setHighlightSelectedCell(true);
		gridMeiosComunicacoes.setRowCount(2);
		gridMeiosComunicacoes.setColCount(2);		
		gridMeiosComunicacoes.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);
		gridMeiosComunicacoes.setColSeparatorWidth(1);
		gridMeiosComunicacoes.setRowSeparatorHeight(1);
		gridMeiosComunicacoes.setDrawColSeparators(true);
		gridMeiosComunicacoes.setDrawRowSeparators(true);
		gridMeiosComunicacoes.setUpdating(false);
		gridMeiosComunicacoes.setFullRowSelectionDisplay(false);
		gridMeiosComunicacoes.lockColEditing(1);		
		gridMeiosComunicacoes.setColCellType(0, new GridCellCheckBox().getClass());
		gridMeiosComunicacoes.setColCellType(1, new GridCellText().getClass());		
		gridMeiosComunicacoes.setColWidth(0, 15*(int)ConstantesJanela.FATOR_X);
		gridMeiosComunicacoes.setColWidth(1, 136*(int)ConstantesJanela.FATOR_X);
		gridMeiosComunicacoes.setCellText(0,1,"RÁDIO");
		gridMeiosComunicacoes.setCellText(1,1,"TELEVISÃO");		
		gridMeiosComunicacoes.setHorizontalScrollVisible(false);
		gridMeiosComunicacoes.setVerticalScrollVisible(false);
		
		add(lblComunicacoesOutros = new Label("Outros:"),LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 3*(int) ConstantesJanela.FATOR_Y);
		add(edtComunicacoesOutros = new Edit());
		edtComunicacoesOutros.setRect(AFTER + 3*(int) ConstantesJanela.FATOR_X,SAME-2*(int) ConstantesJanela.FATOR_Y, FILL - 3*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		add(btnPrev = new Button("<<"));
		btnPrev.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		add(btnNext = new Button(">>"));
		btnNext.setRect(RIGHT - 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		if(Settings.isColor)
		{	//Setando as cores se o dispositivos suportar cores.
	    	Color BLUE =ConstantesJanela.BLUE;
		     setForeColor(BLUE);
		     lblDoencaProcurar.setForeColor(BLUE);
		     lblDoencaProcurarOutros.setForeColor(BLUE);
		     lblMeiosComunicacoes.setForeColor(BLUE);
		     lblComunicacoesOutros.setForeColor(BLUE);
		     btnNext.setForeColor(BLUE);
		     btnPrev.setForeColor(BLUE);
		     gridDoencaProcurar.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridDoencaProcurar.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		     gridMeiosComunicacoes.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridMeiosComunicacoes.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		}
		
	}
	protected void PopulaForm() {
		GridCellID cellID;
		GridCell cell;
		cellID = new GridCellID(FichaA2Win.cdDoencaProcura-1, 0);
		cell = new GridCellCheckBox();
		((GridCellCheckBox) cell).setChecked(true);
		gridDoencaProcurar.setCell(cellID, cell);
		edtDoencaProcurarOutros.setText(FichaA2Win.dsDoencaProcuraEsp);
		cellID = new GridCellID(FichaA2Win.cdMeiosComuni-1, 0);
		cell = new GridCellCheckBox();
		((GridCellCheckBox) cell).setChecked(true);
		gridMeiosComunicacoes.setCell(cellID, cell);
		edtComunicacoesOutros.setText(FichaA2Win.dsMeiosComuniEsp);
		
	}
	
	protected void LimpaForm() {
		GridCellID cellID;
		GridCell cell;
		int i=0;
		for( i=0;i<4;i++){	
			cellID = new GridCellID(i, 0);
			cell = new GridCellCheckBox();
			((GridCellCheckBox) cell).setChecked(false);
			gridDoencaProcurar.setCell(cellID, cell);
		}		
		edtDoencaProcurarOutros.setText("");
		for( i=0;i<2;i++){	
			cellID = new GridCellID(i, 0);
			cell = new GridCellCheckBox();
			((GridCellCheckBox) cell).setChecked(false);
			gridMeiosComunicacoes.setCell(cellID, cell);
		}
		edtComunicacoesOutros.setText("");
		
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
				   FichaA7Win fichaA7Win = FichaA7Win.criarInstancia();
				   fichaA7Win.LimpaForm();
				   if(ListaFamiliaWin.cmd.equals("U")){
					   fichaA7Win.PopulaForm();
				   }
				   fichaA7Win.popupBlockingModal();
				   if("F".equals(ListaFamiliaWin.cmd)){
					   unpop();   
				   }
			   }
		    }else if(e.type ==GridEvent.CELL_DOUBLE_CLICKED){
		    	if(e.target==gridDoencaProcurar){
		    		GridEvent ge = (GridEvent) e;
			    	if(ge.cellID.Col==0){
			    		int linha=ge.cellID.Row;
			    		for(int i=0;i<5;i++){		    			
			    			if(linha!=i){
			    				cellID = new GridCellID(i, 0);
					    		cell = new GridCellCheckBox();
					    		((GridCellCheckBox) cell).setChecked(false);
					    		gridDoencaProcurar.setCell(cellID, cell);   				
			    			}
			    		}
			    	}
		    	}else if(e.target==gridMeiosComunicacoes){
		    		GridEvent ge = (GridEvent) e;
			    	if(ge.cellID.Col==0){
			    		int linha=ge.cellID.Row;
			    		for(int i=0;i<5;i++){		    			
			    			if(linha!=i){
			    				cellID = new GridCellID(i, 0);
					    		cell = new GridCellCheckBox();
					    		((GridCellCheckBox) cell).setChecked(false);
					    		gridMeiosComunicacoes.setCell(cellID, cell);   				
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
		boolean booDoencaProcurar =false;
		int i=0;
    	for(i=0;i<5;i++){
    		 cellID = new GridCellID(i, 0);
	    	 cell = new GridCellCheckBox();
	    	 cell = gridDoencaProcurar.getCell(cellID);
	    	 booDoencaProcurar = ((GridCellCheckBox) cell).getChecked();
	    	if(booDoencaProcurar){
	    		break;
	    	}
 		} 
    	if(booDoencaProcurar){
    		FichaA2Win.cdDoencaProcura=i+1;	
    	}else{
    		FichaA2Win.cdDoencaProcura=0;
    	}
    	
    	FichaA2Win.dsDoencaProcuraEsp = edtDoencaProcurarOutros.getText();
    	
    	boolean booMeiosComunicacoes =false;
		
    	for(i=0;i<5;i++){
    		 cellID = new GridCellID(i, 0);
	    	 cell = new GridCellCheckBox();
	    	 cell = gridMeiosComunicacoes.getCell(cellID);
	    	 booMeiosComunicacoes = ((GridCellCheckBox) cell).getChecked();
	    	if(booMeiosComunicacoes){
	    		break;
	    	}
 		}  
    	if(booMeiosComunicacoes){
    		FichaA2Win.cdMeiosComuni=i+1;
    	}else{
    		FichaA2Win.cdMeiosComuni=0;
    	}
    	
    	FichaA2Win.dsMeiosComuniEsp=edtComunicacoesOutros.getText();	
    }

}
