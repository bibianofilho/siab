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
import br.com.bibiano.siab.business.FichaIdosoNgc;

/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class FichaIdoso2Win extends Window {
	private static FichaIdoso2Win fichaIdoso2Win;
	
	private Label lblCondRisco;	
	private Grid gridCondRisco;
	private Button btnPrev;
	private Button btnNext;
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static FichaIdoso2Win criarInstancia()
	{
		if(fichaIdoso2Win == null)
		{
			fichaIdoso2Win = new FichaIdoso2Win();
		}
		return fichaIdoso2Win;
	}
	public FichaIdoso2Win(){
		super("FICHAB - IDOSO2",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(lblCondRisco= new Label("Condição de Risco:"),LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+(int)ConstantesJanela.FATOR_Y);
		add(gridCondRisco = new BBGrid());
		gridCondRisco.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 1*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 115*(int) ConstantesJanela.FATOR_Y);
		gridCondRisco.setStorage(new GridStorageSingleHashtable());
		gridCondRisco.setAllowSortRows(true);
		gridCondRisco.setAllowSortCols(true);
		gridCondRisco.setHighlightSelectedCell(true);
		gridCondRisco.setRowCount(11);
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
		gridCondRisco.setColWidth(1, 125*(int)ConstantesJanela.FATOR_X);
		gridCondRisco.setCellText(0,1,"HIPERTENSÃO");
		gridCondRisco.setCellText(1,1,"TUBERCULOSE");
		gridCondRisco.setCellText(2,1,"ALZHEIMER");
		gridCondRisco.setCellText(3,1,"DEF. FÍSICA");
		gridCondRisco.setCellText(4,1,"HANSENÍASE");
		gridCondRisco.setCellText(5,1,"MAL DE PARKSON");
		gridCondRisco.setCellText(6,1,"CÂNCER");
		gridCondRisco.setCellText(7,1,"ACAMADO");
		gridCondRisco.setCellText(8,1,"INTERNAMENTOS");
		gridCondRisco.setCellText(9,1,"SEQUELAS DE AVC");
		gridCondRisco.setCellText(10,1,"ALCOOLATRA");
		gridCondRisco.setHorizontalScrollVisible(false);
		
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
    		if("U".equals(ListaFichaIdosoWin.cmd)){  
    			FichaIdosoNgc fichaIdosoNgc = FichaIdosoNgc.criarInstancia();
    	    	ResultSet rs = fichaIdosoNgc.getFichaIdoso(FichaIdoso1Win.cdFamilia,FichaIdoso1Win.cdPaciente);    			
    			if(rs.next()){    
    				cell = new GridCellCheckBox();
    	    		if("S".equals(rs.getString("FL_HIPERTENSAO"))){    	    			
        	    		((GridCellCheckBox) cell).setChecked(true);        	    		
    	    		}else{
    	    			((GridCellCheckBox) cell).setChecked(false);
    	    		}
    	    		gridCondRisco.setCell(new GridCellID(0, 0), cell);
    	    		
    	    		cell = new GridCellCheckBox();
    	    		if("S".equals(rs.getString("FL_TUBERCULOSE"))){    	    			
        	    		((GridCellCheckBox) cell).setChecked(true);        	    		
    	    		}else{
    	    			((GridCellCheckBox) cell).setChecked(false);
    	    		}
    	    		gridCondRisco.setCell(new GridCellID(1, 0), cell);
    	    		
    	    		cell = new GridCellCheckBox();
    	    		if("S".equals(rs.getString("FL_ALZHEIMER"))){    	    			
        	    		((GridCellCheckBox) cell).setChecked(true);        	    		
    	    		}else{
    	    			((GridCellCheckBox) cell).setChecked(false);
    	    		}
    	    		gridCondRisco.setCell(new GridCellID(2, 0), cell);    
    	    		
    	    		cell = new GridCellCheckBox();
    	    		if("S".equals(rs.getString("FL_DEFFISICA"))){    	    			
        	    		((GridCellCheckBox) cell).setChecked(true);        	    		
    	    		}else{
    	    			((GridCellCheckBox) cell).setChecked(false);
    	    		}
    	    		gridCondRisco.setCell(new GridCellID(3, 0), cell);  
    	    		
    	    		cell = new GridCellCheckBox();
    	    		if("S".equals(rs.getString("FL_HANSENIASE"))){    	    			
        	    		((GridCellCheckBox) cell).setChecked(true);        	    		
    	    		}else{
    	    			((GridCellCheckBox) cell).setChecked(false);
    	    		}
    	    		gridCondRisco.setCell(new GridCellID(4, 0), cell);  
    	    		
    	    		cell = new GridCellCheckBox();
    	    		if("S".equals(rs.getString("FL_MALPARKSON"))){    	    			
        	    		((GridCellCheckBox) cell).setChecked(true);        	    		
    	    		}else{
    	    			((GridCellCheckBox) cell).setChecked(false);
    	    		}
    	    		gridCondRisco.setCell(new GridCellID(5, 0), cell);  
    	    		
    	    		cell = new GridCellCheckBox();
    	    		if("S".equals(rs.getString("FL_CANCER"))){    	    			
        	    		((GridCellCheckBox) cell).setChecked(true);        	    		
    	    		}else{
    	    			((GridCellCheckBox) cell).setChecked(false);
    	    		}
    	    		gridCondRisco.setCell(new GridCellID(6, 0), cell); 
    	    		
    	    		cell = new GridCellCheckBox();
    	    		if("S".equals(rs.getString("FL_ACAMADO"))){    	    			
        	    		((GridCellCheckBox) cell).setChecked(true);        	    		
    	    		}else{
    	    			((GridCellCheckBox) cell).setChecked(false);
    	    		}
    	    		gridCondRisco.setCell(new GridCellID(7, 0), cell);
    	    		
    	    		cell = new GridCellCheckBox();
    	    		if("S".equals(rs.getString("FL_INTERNAMENTOS"))){    	    			
        	    		((GridCellCheckBox) cell).setChecked(true);        	    		
    	    		}else{
    	    			((GridCellCheckBox) cell).setChecked(false);
    	    		}
    	    		gridCondRisco.setCell(new GridCellID(8, 0), cell);
    	    		
    	    		cell = new GridCellCheckBox();
    	    		if("S".equals(rs.getString("FL_SEQUELASAVC"))){    	    			
        	    		((GridCellCheckBox) cell).setChecked(true);
        	    		
    	    		}else{
    	    			((GridCellCheckBox) cell).setChecked(false);
    	    		}
    	    		gridCondRisco.setCell(new GridCellID(9, 0), cell);
    	    		
    	    		cell = new GridCellCheckBox();
    	    		if("S".equals(rs.getString("FL_ALCOLATRA"))){    	    			
        	    		((GridCellCheckBox) cell).setChecked(true);        	    		
    	    		}else{
    	    			((GridCellCheckBox) cell).setChecked(false);
    	    		}
    	    		gridCondRisco.setCell(new GridCellID(10, 0), cell);
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
    	for(int i=0;i<11;i++){
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
		    		    FichaIdoso3Win fichaIdoso3Win = FichaIdoso3Win.criarInstancia();
		    		    if("N".equals(ListaFichaIdosoWin.cmd)){
		    		    	fichaIdoso3Win.LimpaForm(); 
			    		 }else if("U".equals(ListaFichaIdosoWin.cmd)){
			    			 fichaIdoso3Win.PopulaForm();
			    		 } 
		    		    fichaIdoso3Win.popupBlockingModal();	
			    		 if("F".equals(ListaFichaIdosoWin.cmd)){
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
			FichaIdoso1Win.flhipertensao="S";
		}else{
			FichaIdoso1Win.flhipertensao="N";
		}  
		cell = new GridCellCheckBox();
		cell = gridCondRisco.getCell(new GridCellID(1, 0));
		if(((GridCellCheckBox) cell).getChecked()){
			FichaIdoso1Win.flTuberculose="S";
		}else{
			FichaIdoso1Win.flTuberculose="N";
		} 
		cell = new GridCellCheckBox();
		cell = gridCondRisco.getCell(new GridCellID(2, 0));
		if(((GridCellCheckBox) cell).getChecked()){
			FichaIdoso1Win.flAlzheimer="S";
		}else{
			FichaIdoso1Win.flAlzheimer="N";
		}
		cell = new GridCellCheckBox();
		cell = gridCondRisco.getCell(new GridCellID(3, 0));
		if(((GridCellCheckBox) cell).getChecked()){
			FichaIdoso1Win.flDefFisica="S";
		}else{
			FichaIdoso1Win.flDefFisica="N";
		}
		cell = new GridCellCheckBox();
		cell = gridCondRisco.getCell(new GridCellID(4, 0));
		if(((GridCellCheckBox) cell).getChecked()){
			FichaIdoso1Win.flHanseniase="S";
		}else{
			FichaIdoso1Win.flHanseniase="N";
		}
		cell = new GridCellCheckBox();
		cell = gridCondRisco.getCell(new GridCellID(5, 0));
		if(((GridCellCheckBox) cell).getChecked()){
			FichaIdoso1Win.flMalParkson="S";
		}else{
			FichaIdoso1Win.flMalParkson="N";
		}
		cell = new GridCellCheckBox();
		cell = gridCondRisco.getCell(new GridCellID(6, 0));
		if(((GridCellCheckBox) cell).getChecked()){
			FichaIdoso1Win.flCancer="S";
		}else{
			FichaIdoso1Win.flCancer="N";
		}
		cell = new GridCellCheckBox();
		cell = gridCondRisco.getCell(new GridCellID(7, 0));
		if(((GridCellCheckBox) cell).getChecked()){
			FichaIdoso1Win.flAcamado="S";
		}else{
			FichaIdoso1Win.flAcamado="N";
		}
		cell = new GridCellCheckBox();
		cell = gridCondRisco.getCell(new GridCellID(8, 0));
		if(((GridCellCheckBox) cell).getChecked()){
			FichaIdoso1Win.flInternamentos="S";
		}else{
			FichaIdoso1Win.flInternamentos="N";
		}
		cell = new GridCellCheckBox();
		cell = gridCondRisco.getCell(new GridCellID(9, 0));
		if(((GridCellCheckBox) cell).getChecked()){
			FichaIdoso1Win.flSequelasAvc="S";
		}else{
			FichaIdoso1Win.flSequelasAvc="N";
		}
		cell = new GridCellCheckBox();
		cell = gridCondRisco.getCell(new GridCellID(10, 0));
		if(((GridCellCheckBox) cell).getChecked()){
			FichaIdoso1Win.flAlcoolatra="S";
		}else{
			FichaIdoso1Win.flAlcoolatra="N";
		}
    }
    
	private void EntraDataGrid(){
		 
	}

	  private boolean ValidaForm(){
		 return true;
	  }
}
