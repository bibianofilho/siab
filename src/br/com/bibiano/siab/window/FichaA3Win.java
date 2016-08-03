package br.com.bibiano.siab.window;


import waba.fx.Color;
import waba.sys.Convert;
import waba.sys.Settings;
import waba.sys.Vm;
import waba.ui.Button;
import waba.ui.Check;
import waba.ui.ControlEvent;
import waba.ui.Edit;
import waba.ui.Event;
import waba.ui.Label;
import waba.ui.MessageBox;
import waba.ui.Window;
import wextlib.ui.grid.BBGrid;
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
public class FichaA3Win extends Window {
	private static FichaA3Win fichaA3Win;
	
	private Label lblTpCasa;
	private Grid gridTpCasa;
	private Label lblTpCasaEsp;
	private Edit edtTpCasaEsp;
	private Label lblNumComodos;
	private Edit edtNumComodos;
	private Label lblEnergia;
	private Check chkEnergia;
	private Button btnPrev;
	private Button btnNext;
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static FichaA3Win criarInstancia()
	{
		if(fichaA3Win == null)
		{
			fichaA3Win = new FichaA3Win();
		}
		return fichaA3Win;
	}
	
	public FichaA3Win(){		
		super("Ficha A3",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		
		add(lblTpCasa = new Label("Tipo de Casa:"));
		lblTpCasa.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X,TOP  + 3*(int) ConstantesJanela.FATOR_Y, 65*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		add(gridTpCasa = new BBGrid());
		gridTpCasa.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 1*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 50*(int) ConstantesJanela.FATOR_Y);
		gridTpCasa.setStorage(new GridStorageSingleHashtable());
		gridTpCasa.setAllowSortRows(true);
		gridTpCasa.setAllowSortCols(true);
		gridTpCasa.setHighlightSelectedCell(true);
		gridTpCasa.setRowCount(5);
		gridTpCasa.setColCount(2);
		gridTpCasa.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);
		gridTpCasa.setColSeparatorWidth(1);
		gridTpCasa.setRowSeparatorHeight(1);
		gridTpCasa.setDrawColSeparators(true);
		gridTpCasa.setDrawRowSeparators(true);
		gridTpCasa.setUpdating(false);
		gridTpCasa.setFullRowSelectionDisplay(true);
		gridTpCasa.setColCellType(0, new GridCellCheckBox().getClass());
		gridTpCasa.setColCellType(1, new GridCellText().getClass());
		gridTpCasa.setColWidth(0, 15*(int)ConstantesJanela.FATOR_X);
		gridTpCasa.setColWidth(1, 125*(int)ConstantesJanela.FATOR_X);
		gridTpCasa.setCellText(0,1,"TIJOLO/ADOBE");
		gridTpCasa.setCellText(1,1,"TAIPA REVERTIDA");
		gridTpCasa.setCellText(2,1,"TAIPA NÃO REVERTIDA");
		gridTpCasa.setCellText(3,1,"MADEIRA");
		gridTpCasa.setCellText(4,1,"MATERIAL APROVEITADO");
		gridTpCasa.setHorizontalScrollVisible(false);
		
		
		add(lblTpCasaEsp = new Label("Outro - Especificar:"),LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 3*(int) ConstantesJanela.FATOR_Y);
		add(edtTpCasaEsp = new Edit());
		edtTpCasaEsp.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+(int) ConstantesJanela.FATOR_Y, FILL -3*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		add(lblNumComodos= new Label("Nº de comodos/peças:"),LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 3*(int) ConstantesJanela.FATOR_Y);
		add(edtNumComodos = new Edit("99"),AFTER + 3*(int) ConstantesJanela.FATOR_X, SAME-(int) ConstantesJanela.FATOR_Y);
		edtNumComodos.setMaxLength(2);
		edtNumComodos.setValidChars("1234567890");
		
		add(lblEnergia= new Label("Energia Elétrica:"),LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 2*(int) ConstantesJanela.FATOR_Y);
		add(chkEnergia = new Check("Sim"),AFTER + 3*(int) ConstantesJanela.FATOR_X, SAME);		
		
		add(btnPrev = new Button("<<"));
		btnPrev.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);		
		add(btnNext = new Button(">>"));
		btnNext.setRect(RIGHT - 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		if(Settings.isColor)
		{	//Setando as cores se o dispositivos suportar cores.
	    	  Color BLUE =ConstantesJanela.BLUE;
		      setForeColor(BLUE);
			  lblTpCasa.setForeColor(BLUE);
			  lblTpCasaEsp.setForeColor(BLUE);
			  lblNumComodos.setForeColor(BLUE);
			  lblEnergia.setForeColor(BLUE);
			  btnNext.setForeColor(BLUE);
			  btnPrev.setForeColor(BLUE); 
			  gridTpCasa.setSelectedCellForeColor(new Color(0, 0, 0));
			  gridTpCasa.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		}
	}
	protected void PopulaForm() {
		GridCellID cellID;
		GridCell cell;
		cellID = new GridCellID(FichaA2Win.cdTpCasa-1, 0);
		cell = new GridCellCheckBox();
		((GridCellCheckBox) cell).setChecked(true);
		gridTpCasa.setCell(cellID, cell);
		edtTpCasaEsp.setText(FichaA2Win.dsTpCasaEsp);
		edtNumComodos.setText(""+FichaA2Win.numComodos);
		if(FichaA2Win.flEnergia.equals("S"))
			chkEnergia.setChecked(true);
		else
			chkEnergia.setChecked(false);
	}
	
	protected void LimpaForm() {
		GridCellID cellID;
		GridCell cell;
		for (int i=0;i<5;i++){
			cellID = new GridCellID(i, 0);
			cell = new GridCellCheckBox();
			((GridCellCheckBox) cell).setChecked(false);
			gridTpCasa.setCell(cellID, cell);
		}
		edtTpCasaEsp.setText("");
		edtNumComodos.setText("");		
		chkEnergia.setChecked(false);
	}

    public void onEvent(Event e) {	
    	GridCellID cellID;
		GridCell cell;
    	try{
		    if (e.type == ControlEvent.PRESSED) { 
		    	if(e.target==btnNext){
		    		PreencheAtributo();
		    		
		    		 FichaA40Win fichaA4Win = FichaA40Win.criarInstancia();
		    		 fichaA4Win.LimpaForm();
		    		 if(ListaFamiliaWin.cmd.equals("U")){
		    			 fichaA4Win.PopulaForm();
		    		 }
		    		 fichaA4Win.popupBlockingModal();
		    		 if("F".equals(ListaFamiliaWin.cmd)){
						   unpop();   
					   }
		    	}else if(e.target==btnPrev){
		    		unpop();
		    	}
		    }else if(e.type ==GridEvent.CELL_DOUBLE_CLICKED){
		    	GridEvent ge = (GridEvent) e;
		    	if(ge.cellID.Col==0){
		    		int linha=ge.cellID.Row;		    		
		    		for(int i=0;i<5;i++){		    			
		    			if(linha!=i){
		    				cellID = new GridCellID(i, 0);
				    		cell = new GridCellCheckBox();
				    		((GridCellCheckBox) cell).setChecked(false);
				    		gridTpCasa.setCell(cellID, cell);   				
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
	    	 cell = gridTpCasa.getCell(cellID);
	    	booTpCasa = ((GridCellCheckBox) cell).getChecked();
	    	if(booTpCasa){
	    		break;
	    	}
 		}  
    	if(booTpCasa){
    		FichaA2Win.cdTpCasa= i+1;	
    	}else{
    		FichaA2Win.cdTpCasa= 0;
    	}
    	
    	FichaA2Win.dsTpCasaEsp = edtTpCasaEsp.getText();
    	FichaA2Win.numComodos = Convert.toInt(edtNumComodos.getText());
    	if(chkEnergia.getChecked()){
    		FichaA2Win.flEnergia="S";
    	}else{
    		FichaA2Win.flEnergia="N";
    	}
   }

}
