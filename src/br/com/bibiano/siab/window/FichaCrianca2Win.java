package br.com.bibiano.siab.window;


import litebase.ResultSet;
import waba.fx.Color;
import waba.sys.Settings;
import waba.sys.Vm;
import waba.ui.Button;
import waba.ui.ControlEvent;
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
import wextlib.ui.grid.GridStorageSingleHashtable;
import br.com.bibiano.siab.business.FichaCriancaNgc;

/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class FichaCrianca2Win extends Window {
	private static FichaCrianca2Win fichaCrianca2Win;
	
	private Label lblCondRisco;	
	private Grid gridCondRisco;
	private Button btnPrev;
	private Button btnNext;
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static FichaCrianca2Win criarInstancia()
	{
		if(fichaCrianca2Win == null)
		{
			fichaCrianca2Win = new FichaCrianca2Win();
		}
		return fichaCrianca2Win;
	}
	public FichaCrianca2Win(){
		super("FICHAB - CRIANÇA2",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(lblCondRisco= new Label("Condição de Risco:"),LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+5*(int)ConstantesJanela.FATOR_Y);
		add(gridCondRisco = new BBGrid());
		gridCondRisco.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 4*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 108*(int) ConstantesJanela.FATOR_Y);
		gridCondRisco.setStorage(new GridStorageSingleHashtable());
		gridCondRisco.setAllowSortRows(true);
		gridCondRisco.setAllowSortCols(true);
		gridCondRisco.setHighlightSelectedCell(true);
		gridCondRisco.setRowCount(8);
		gridCondRisco.setColCount(2);
		gridCondRisco.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);
		gridCondRisco.setColSeparatorWidth(1);
		gridCondRisco.setRowSeparatorHeight(1);
		gridCondRisco.setDrawColSeparators(true);
		gridCondRisco.setDrawRowSeparators(true);
		gridCondRisco.setUpdating(false);
		gridCondRisco.setFullRowSelectionDisplay(true);
		gridCondRisco.setColCellType(0, new GridCellCheckBox().getClass());
		gridCondRisco.setColCellType(1, new GridCellText().getClass());
		gridCondRisco.setColWidth(0, 15*(int)ConstantesJanela.FATOR_X);
		gridCondRisco.setColWidth(1, 135*(int)ConstantesJanela.FATOR_X);
		gridCondRisco.setCellText(0,1,"DIARREIA");
		gridCondRisco.setCellText(1,1,"USARAM TRO");
		gridCondRisco.setCellText(2,1,"INTERNAMENTOS");
		gridCondRisco.setCellText(3,1,"IRA");
		gridCondRisco.setCellText(4,1,"CAXUMBA");
		gridCondRisco.setCellText(5,1,"CATAPORA");
		gridCondRisco.setCellText(6,1,"BAIXO PESO AO NASCER");
		gridCondRisco.setCellText(7,1,"DEFICIÊNCIAS");		
		gridCondRisco.setHorizontalScrollVisible(false);
		gridCondRisco.setVerticalScrollVisible(false);
		
		
		
		
		add(btnPrev = new Button("<<"));
		btnPrev.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 1*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		add(btnNext = new Button(">>"));
		btnNext.setRect(RIGHT - 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 1*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		if(Settings.isColor)
		{	//Setando as cores se o dispositivos suportar cores.
	    	Color BLUE =ConstantesJanela.BLUE;
		     setForeColor(BLUE); 
		     btnNext.setForeColor(BLUE);
		     btnPrev.setForeColor(BLUE);
		     lblCondRisco.setForeColor(BLUE);
		     gridCondRisco.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridCondRisco.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		}		
	}
	
    public void PopulaForm(){
    	GridCell cell;
    	try{
    		if("U".equals(ListaFichaCriancaWin.cmd)){  
    			FichaCriancaNgc fichaCriancaNgc = FichaCriancaNgc.criarInstancia();
    	    	ResultSet rs = fichaCriancaNgc.getFichaCrianca(FichaCrianca1Win.cdFamilia,FichaCrianca1Win.cdPaciente);    			
    			if(rs.next()){    
    				cell = new GridCellCheckBox();
    	    		if("S".equals(rs.getString("FL_DIARREIA"))){    	    			
        	    		((GridCellCheckBox) cell).setChecked(true);        	    		
    	    		}else{
    	    			((GridCellCheckBox) cell).setChecked(false);
    	    		}
    	    		gridCondRisco.setCell(new GridCellID(0, 0), cell);
    	    		
    	    		cell = new GridCellCheckBox();
    	    		if("S".equals(rs.getString("FL_USARAMTRO"))){    	    			
        	    		((GridCellCheckBox) cell).setChecked(true);        	    		
    	    		}else{
    	    			((GridCellCheckBox) cell).setChecked(false);
    	    		}
    	    		gridCondRisco.setCell(new GridCellID(1, 0), cell);
    	    		
    	    		cell = new GridCellCheckBox();
    	    		if("S".equals(rs.getString("FL_INTERNAMENTOS"))){    	    			
        	    		((GridCellCheckBox) cell).setChecked(true);        	    		
    	    		}else{
    	    			((GridCellCheckBox) cell).setChecked(false);
    	    		}
    	    		gridCondRisco.setCell(new GridCellID(2, 0), cell);    
    	    		
    	    		cell = new GridCellCheckBox();
    	    		if("S".equals(rs.getString("FL_IRA"))){    	    			
        	    		((GridCellCheckBox) cell).setChecked(true);        	    		
    	    		}else{
    	    			((GridCellCheckBox) cell).setChecked(false);
    	    		}
    	    		gridCondRisco.setCell(new GridCellID(3, 0), cell);  
    	    		
    	    		cell = new GridCellCheckBox();
    	    		if("S".equals(rs.getString("FL_CAXUMBA"))){    	    			
        	    		((GridCellCheckBox) cell).setChecked(true);        	    		
    	    		}else{
    	    			((GridCellCheckBox) cell).setChecked(false);
    	    		}
    	    		gridCondRisco.setCell(new GridCellID(4, 0), cell);  
    	    		
    	    		cell = new GridCellCheckBox();
    	    		if("S".equals(rs.getString("FL_CATAPORA"))){    	    			
        	    		((GridCellCheckBox) cell).setChecked(true);        	    		
    	    		}else{
    	    			((GridCellCheckBox) cell).setChecked(false);
    	    		}
    	    		gridCondRisco.setCell(new GridCellID(5, 0), cell);  
    	    		
    	    		cell = new GridCellCheckBox();
    	    		if("S".equals(rs.getString("FL_BAIXOPESONASC"))){    	    			
        	    		((GridCellCheckBox) cell).setChecked(true);        	    		
    	    		}else{
    	    			((GridCellCheckBox) cell).setChecked(false);
    	    		}
    	    		gridCondRisco.setCell(new GridCellID(6, 0), cell); 
    	    		
    	    		cell = new GridCellCheckBox();
    	    		if("S".equals(rs.getString("FL_DEFICIENCIAS"))){    	    			
        	    		((GridCellCheckBox) cell).setChecked(true);        	    		
    	    		}else{
    	    			((GridCellCheckBox) cell).setChecked(false);
    	    		}
    	    		gridCondRisco.setCell(new GridCellID(7, 0), cell);
    	    	}
    		} 
    	}catch (Exception e) {
    		MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+e.getMessage());
			msgBoxErro.popupBlockingModal();
			Vm.debug("Erro: "+e.getMessage());
		}
    }
    
    public void LimpaForm(){
    	GridCell cell;
    	for(int i=0;i<8;i++){
    		cell = new GridCellCheckBox();
    		((GridCellCheckBox) cell).setChecked(false);
    		gridCondRisco.setCell(new GridCellID(i, 0), cell);
    	}
	}

    public void onEvent(Event e) {	
    	try{
		    if (e.type == ControlEvent.PRESSED) { 
			    if (e.target == btnNext) {
			    	if(ValidaForm()){
		    			PreencheAtributo();
		    		    FichaCrianca3Win fichaCrianca3Win = FichaCrianca3Win.criarInstancia();
		    		    if("N".equals(ListaFichaCriancaWin.cmd)){
		    		    	fichaCrianca3Win.LimpaForm(); 
			    		 }else if("U".equals(ListaFichaCriancaWin.cmd)){
			    			 fichaCrianca3Win.PopulaForm();
			    		 } 
		    		    fichaCrianca3Win.popupBlockingModal();	
			    		 if("F".equals(ListaFichaCriancaWin.cmd)){
							   unpop();   
						 }
		    		} 
			    }else if (e.target == btnPrev){
			    	unpop();
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
		GridCell cell;	 
		cell = new GridCellCheckBox();
		cell = gridCondRisco.getCell(new GridCellID(0, 0));
		if(((GridCellCheckBox) cell).getChecked()){
			FichaCrianca1Win.flDiarreia="S";
		}else{
			FichaCrianca1Win.flDiarreia="N";
		}  
		cell = new GridCellCheckBox();
		cell = gridCondRisco.getCell(new GridCellID(1, 0));
		if(((GridCellCheckBox) cell).getChecked()){
			FichaCrianca1Win.flUsaramTro="S";
		}else{
			FichaCrianca1Win.flUsaramTro="N";
		} 
		cell = new GridCellCheckBox();
		cell = gridCondRisco.getCell(new GridCellID(2, 0));
		if(((GridCellCheckBox) cell).getChecked()){
			FichaCrianca1Win.flInternamentos="S";
		}else{
			FichaCrianca1Win.flInternamentos="N";
		}
		cell = new GridCellCheckBox();
		cell = gridCondRisco.getCell(new GridCellID(3, 0));
		if(((GridCellCheckBox) cell).getChecked()){
			FichaCrianca1Win.flIra="S";
		}else{
			FichaCrianca1Win.flIra="N";
		}
		cell = new GridCellCheckBox();		
		cell = new GridCellCheckBox();
		cell = gridCondRisco.getCell(new GridCellID(4, 0));
		if(((GridCellCheckBox) cell).getChecked()){
			FichaCrianca1Win.flCaxumba="S";
		}else{
			FichaCrianca1Win.flCaxumba="N";
		}
		cell = new GridCellCheckBox();
		cell = gridCondRisco.getCell(new GridCellID(5, 0));
		if(((GridCellCheckBox) cell).getChecked()){
			FichaCrianca1Win.flCatapora="S";
		}else{
			FichaCrianca1Win.flCatapora="N";
		}
		cell = new GridCellCheckBox();
		cell = gridCondRisco.getCell(new GridCellID(6, 0));
		if(((GridCellCheckBox) cell).getChecked()){
			FichaCrianca1Win.flBaixoPeso="S";
		}else{
			FichaCrianca1Win.flBaixoPeso="N";
		}
		cell = new GridCellCheckBox();
		cell = gridCondRisco.getCell(new GridCellID(7, 0));
		if(((GridCellCheckBox) cell).getChecked()){
			FichaCrianca1Win.flDeficiencias="S";
		}else{
			FichaCrianca1Win.flDeficiencias="N";
		}
    }
    
	private void EntraDataGrid(){
		 
	}

	  private boolean ValidaForm(){
		 return true;
	  }
}
