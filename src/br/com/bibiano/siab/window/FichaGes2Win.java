package br.com.bibiano.siab.window;

import litebase.ResultSet;
import waba.fx.Color;
import waba.sys.Settings;
import waba.sys.Vm;
import waba.ui.Button;
import waba.ui.Calendar;
import waba.ui.ControlEvent;
import waba.ui.Event;
import waba.ui.InputDialog;
import waba.ui.MessageBox;
import waba.ui.Window;
import waba.util.Date;
import wextlib.ui.grid.BBGrid;
import wextlib.ui.grid.Grid;
import wextlib.ui.grid.GridCell;
import wextlib.ui.grid.GridCellCheckBox;
import wextlib.ui.grid.GridCellID;
import wextlib.ui.grid.GridCellText;
import wextlib.ui.grid.GridEvent;
import wextlib.ui.grid.GridStorageSingleHashtable;
import br.com.bibiano.siab.business.FichaGesNgc;
import br.com.bibiano.siab.business.SIABBusiness;

/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class FichaGes2Win extends Window {
	private static FichaGes2Win fichaGes2Win;
	
	private Grid gridGes1;
	private Grid gridGes2;
	private Button btnPrev;
	private Button btnNext;
	private Calendar calendar;
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static FichaGes2Win criarInstancia()
	{
		if(fichaGes2Win == null)
		{
			fichaGes2Win = new FichaGes2Win();
		}
		return fichaGes2Win;
	}
	
	public FichaGes2Win(){
		super("FICHAB - GES2",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(gridGes1 = new BBGrid());
		gridGes1.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, TOP + (int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 67*(int) ConstantesJanela.FATOR_Y);
		gridGes1.setStorage(new GridStorageSingleHashtable());
		gridGes1.setAllowSortRows(false);
		gridGes1.setAllowSortCols(false);
		gridGes1.setAllowUserResizeRows(false);
		gridGes1.setVerticalScrollVisible(false);
		gridGes1.setHighlightSelectedCell(true);
		gridGes1.setRowCount(5);
		gridGes1.setColCount(10);
		gridGes1.setFixedRowCount(1);
		gridGes1.setFixedColCount(1);
		gridGes1.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);

		gridGes1.setColSeparatorWidth(1);
		gridGes1.setRowSeparatorHeight(1);
		gridGes1.setDrawColSeparators(true);
		gridGes1.setDrawRowSeparators(true);
		gridGes1.setUpdating(false);
		gridGes1.setFullRowSelectionDisplay(true);
		gridGes1.setColWidth(0,60*(int)ConstantesJanela.FATOR_X);
		gridGes1.setColWidth(1,42*(int)ConstantesJanela.FATOR_X);
		gridGes1.setColWidth(2,42*(int)ConstantesJanela.FATOR_X);
		gridGes1.setColWidth(3,42*(int)ConstantesJanela.FATOR_X);
		gridGes1.setColWidth(4,42*(int)ConstantesJanela.FATOR_X);
		gridGes1.setColWidth(5,42*(int)ConstantesJanela.FATOR_X);
		gridGes1.setColWidth(6,42*(int)ConstantesJanela.FATOR_X);
		gridGes1.setColWidth(7,42*(int)ConstantesJanela.FATOR_X);
		gridGes1.setColWidth(8,42*(int)ConstantesJanela.FATOR_X);
		gridGes1.setColWidth(9,42*(int)ConstantesJanela.FATOR_X);
		gridGes1.setRowHeight(4,0);
		String [] tituloGrid = {"","1","2","3","4","5","6","7","8","9"};
		gridGes1.setRowStrings(0,tituloGrid);
		String[]Coluna1={"","Estado Nutricional","Dt Consulta de Pré-Natal","Dt Visita do ACS"};
		gridGes1.setColStrings(0,Coluna1);

		add(gridGes2 = new Grid());
		gridGes2.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER + 2*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 59*(int) ConstantesJanela.FATOR_Y);
		gridGes2.setStorage(new GridStorageSingleHashtable());
		gridGes2.setAllowSortRows(false);
		gridGes2.setAllowSortCols(false);
		gridGes2.setAllowUserResizeRows(false);
		gridGes2.setHorizontalScrollVisible(false);
		gridGes2.setHighlightSelectedCell(true);
		gridGes2.setRowCount(8);
		gridGes2.setColCount(2);
		gridGes2.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);
		gridGes2.setColSeparatorWidth(1);
		gridGes2.setRowSeparatorHeight(1);
		gridGes2.setDrawColSeparators(true);
		gridGes2.setDrawRowSeparators(true);
		gridGes2.setUpdating(false);
		gridGes2.setFullRowSelectionDisplay(true);
		gridGes2.setColWidth(0, 15*(int)ConstantesJanela.FATOR_X);
		gridGes2.setColWidth(1, 123*(int)ConstantesJanela.FATOR_X);
		gridGes2.setColCellType(0, new GridCellCheckBox().getClass());
		gridGes2.setColCellType(1, new GridCellText().getClass());
		String[]ColunaGrid2={"6 ou Mais Gestações","Natimorto/Aborto","36 Anos e Mais","Menos de 20 Anos",
							 "Sangramento","Edema","Diabetes","Pressão Alta" };
		gridGes2.setColStrings(1,ColunaGrid2);
		
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
		     gridGes1.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridGes1.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		     gridGes2.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridGes2.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		}		
	}
	
	//	TODO VERIFICAR O problema do grid não deixar alterar meses anteriores

    protected void PopulaForm(){
    	GridCell cell;
    	try{
    		if(ListaFichaGesWin.cmd.equals("U")){  
    			FichaGesNgc fichaHaNgc = FichaGesNgc.criarInstancia();
    	    	ResultSet rs = fichaHaNgc.getFichaGes(FichaGes1Win.cdFamilia,FichaGes1Win.cdPaciente);
    			ResultSet rsAcomp = fichaHaNgc.GetFichaGesAcomp(FichaGes1Win.cdFamilia,FichaGes1Win.cdPaciente);     			
    			if(rs.next()){  
    				cell = new GridCellCheckBox();
    				if("S".equals(rs.getString("FL_SEISGESTA"))){
    					((GridCellCheckBox) cell).setChecked(true);	
    				}else{
    					((GridCellCheckBox) cell).setChecked(false);
    				}
    				gridGes2.setCell(new GridCellID(0, 0), cell);
    		   	    cell= new GridCellCheckBox();
    				if("S".equals(rs.getString("FL_NATIMORTO"))){
    					((GridCellCheckBox) cell).setChecked(true);	
    				}else{
    					((GridCellCheckBox) cell).setChecked(false);
    				}
    				gridGes2.setCell(new GridCellID(1, 0), cell);
    				
    		   	    cell= new GridCellCheckBox();
    				if("S".equals(rs.getString("FL_36ANOSMAIS"))){
    					((GridCellCheckBox) cell).setChecked(true);	
    				}else{
    					((GridCellCheckBox) cell).setChecked(false);
    				}
    				gridGes2.setCell(new GridCellID(2, 0), cell);
    				
    		   	    cell= new GridCellCheckBox();
    				if("S".equals(rs.getString("FL_MENOS20"))){
    					((GridCellCheckBox) cell).setChecked(true);	
    				}else{
    					((GridCellCheckBox) cell).setChecked(false);
    				}
    				gridGes2.setCell(new GridCellID(3, 0), cell);
    				
    		   	    cell= new GridCellCheckBox();
    				if("S".equals(rs.getString("FL_SANGRAMENTO"))){
    					((GridCellCheckBox) cell).setChecked(true);	
    				}else{
    					((GridCellCheckBox) cell).setChecked(false);
    				}
    				gridGes2.setCell(new GridCellID(4, 0), cell);
    				
    		   	    cell= new GridCellCheckBox();
    				if("S".equals(rs.getString("FL_EDEMA"))){
    					((GridCellCheckBox) cell).setChecked(true);	
    				}else{
    					((GridCellCheckBox) cell).setChecked(false);
    				}    				
    				gridGes2.setCell(new GridCellID(5, 0), cell);
    				
    		   	    cell= new GridCellCheckBox();
    				if("S".equals(rs.getString("FL_DIABETES"))){
    					((GridCellCheckBox) cell).setChecked(true);	
    				}else{
    					((GridCellCheckBox) cell).setChecked(false);
    				}
    				gridGes2.setCell(new GridCellID(6, 0), cell);
    				
    		   	    cell= new GridCellCheckBox();
    				if("S".equals(rs.getString("FL_PRESSAOALTA"))){
    					((GridCellCheckBox) cell).setChecked(true);	
    				}else{
    					((GridCellCheckBox) cell).setChecked(false);
    				}
    				gridGes2.setCell(new GridCellID(7, 0), cell);
    				
    			} 
    			String [] arrayMes = {"","1","2","3","4","5","6","7","8","9"};
    			String items[]  = new String[5];
    			while(rsAcomp.next()){
    	    		int mes = rsAcomp.getInt("CD_MES");
    	    		items[0]=arrayMes[mes];
    	    		items[1] = rsAcomp.getString("FL_ESTADONUTRI");
    	    		items[2] = rsAcomp.getString("DT_CONSPRENATAL");
    	    		items[3] = rsAcomp.getString("DT_VISITA");
    	    		items[4] = rsAcomp.getString("CD_ANO");    
    	    		gridGes1.setColStrings(mes,items);
    	    	}
    		} 
    	}catch (Exception e) {
    		MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+e.getMessage());
			msgBoxErro.popupBlockingModal();
			Vm.debug("Erro: "+e.getMessage());
		}
    }
    
    public void LimpaForm(){    	
    	String[]Linha1={"Estado Nutricional","","","","","","","","","","","",""};
		String[]Linha2={"Dt Consulta de Pré-Natal","","","","","","","","","","","",""};
		String[]Linha3={"Dt Visita do ACS","","","","","","","","","","","",""};
		String[]Linha4={"","","","","","","","","","","","",""};	
		gridGes1.setRowStrings(1,Linha1);
		gridGes1.setRowStrings(2,Linha2);
		gridGes1.setRowStrings(3,Linha3);
		gridGes1.setRowStrings(4,Linha4);
	}

    public void onEvent(Event e) {	
    	try{
		    if (e.type == ControlEvent.PRESSED) { 
			    if (e.target == btnNext) {
					if(ValidaForm()){
		    			PreencheAtributo();
		    		    FichaGes3Win fichaGes3Win = FichaGes3Win.criarInstancia();
		    		    if("N".equals(ListaFichaGesWin.cmd)){
		    		    	fichaGes3Win.LimpaForm(); 
			    		 }else if("U".equals(ListaFichaGesWin.cmd)){
			    			 fichaGes3Win.PopulaForm();
			    		 } 
		    		    fichaGes3Win.popupBlockingModal();	
			    		 if("F".equals(ListaFichaGesWin.cmd)){
							   unpop();   
						 }
		    		} 
			    }else if (e.target == btnPrev){
			    	unpop();
			    }
		    }else if(e.type == GridEvent.CELL_DOUBLE_CLICKED){
		    	if(e.target==gridGes1){
		    		EntraDataGrid();
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
    	//int mes = SIABBusiness.getMes();    	
    	Vm.copyArray(gridGes1.getRowStrings(1),1,FichaGes1Win.flEstadonutri, 0, 9) ;
    	Vm.copyArray(gridGes1.getRowStrings(2),1,FichaGes1Win.dtConsPreNatal ,0,9);
    	Vm.copyArray(gridGes1.getRowStrings(3),1,FichaGes1Win.dtVisita,0,9);
    	Vm.copyArray(gridGes1.getRowStrings(4),1,FichaGes1Win.cdAno,0,9);
    	
		GridCell cell= new GridCellCheckBox();
   	    cell = gridGes2.getCell( new GridCellID(0, 0));   	 
   	    if(((GridCellCheckBox) cell).getChecked()){
   	    	FichaGes1Win.flSeisGesta="S";
    	}else{
    		FichaGes1Win.flSeisGesta="N";
    	}
   	    cell = gridGes2.getCell( new GridCellID(1, 0));   
   	    cell= new GridCellCheckBox();
	    if(((GridCellCheckBox) cell).getChecked()){
	    	FichaGes1Win.flNatiMorto="S";
	 	}else{
	 		FichaGes1Win.flNatiMorto="N";
	 	}
   	    cell= new GridCellCheckBox();
	    cell = gridGes2.getCell( new GridCellID(2, 0));   	 
	    if(((GridCellCheckBox) cell).getChecked()){
	    	FichaGes1Win.fl36AnosMais="S";
	 	}else{
	 		FichaGes1Win.fl36AnosMais="N";
	 	}	
   	    cell= new GridCellCheckBox();
	    cell = gridGes2.getCell( new GridCellID(3, 0));   	 
	    if(((GridCellCheckBox) cell).getChecked()){
	    	FichaGes1Win.flMenos20="S";
	 	}else{
	 		FichaGes1Win.flMenos20="N";
	 	}
   	    cell= new GridCellCheckBox();
	    cell = gridGes2.getCell( new GridCellID(4, 0));   	 
	    if(((GridCellCheckBox) cell).getChecked()){
	    	FichaGes1Win.flSangramento="S";
	 	}else{
	 		FichaGes1Win.flSangramento="N";
	 	}	
   	    cell= new GridCellCheckBox();
	    cell = gridGes2.getCell( new GridCellID(5, 0));   	 
	    if(((GridCellCheckBox) cell).getChecked()){
	    	FichaGes1Win.flEdema="S";
	 	}else{
	 		FichaGes1Win.flEdema="N";
	 	}	
   	    cell= new GridCellCheckBox();
	    cell = gridGes2.getCell( new GridCellID(6, 0));   	 
	    if(((GridCellCheckBox) cell).getChecked()){
	    	FichaGes1Win.flDiabetes="S";
	 	}else{
	 		FichaGes1Win.flDiabetes="N";
	 	}	
   	    cell= new GridCellCheckBox();
	    cell = gridGes2.getCell( new GridCellID(7, 0));   	 
	    if(((GridCellCheckBox) cell).getChecked()){
	    	FichaGes1Win.flPressaoAlta="S";
	 	}else{
	 		FichaGes1Win.flPressaoAlta="N";
	 	}
    }
	
    private void EntraDataGrid(){
		 MessageBox msgGrid =null;
		 InputDialog id =null;		 
		 String[]pergunta={"Estado Nutricional","Dt Consulta de Pré-Natal","Dt Visita do ACS"};  
	     if(gridGes1.getUserSelectedCol()>0){
				    	if(gridGes1.getCellText(gridGes1.getUserSelectedCellID()).equals("")){
				    		gridGes1.setCellText(4,gridGes1.getUserSelectedCellID().Col,""+ SIABBusiness.getAno());
				    	}
				    	switch (gridGes1.getUserSelectedRow()) {
				    	case 1:
				    		
					    	msgGrid = new MessageBox("Atenção",pergunta[gridGes1.getUserSelectedRow()-1], new String[]{"Nutrida","Desnutrida","Canc"});			    	
					    	msgGrid.popupBlockingModal();
					    	msgGrid.setForeColor(ConstantesJanela.BLUE);
					    	if(msgGrid.getPressedButtonIndex() == 0){						    	
					    		gridGes1.setCellText(gridGes1.getUserSelectedCellID(),"N");
					    		repaint();
					    	}else if(msgGrid.getPressedButtonIndex() == 1){						    		  
					    		gridGes1.setCellText(gridGes1.getUserSelectedCellID(),"D");						    		   
					    	}
						    break;					    
				    	case 2:	
				    		calendar=new Calendar();
				    		calendar.setSelectedDate(new Date(gridGes1.getCellText(gridGes1.getUserSelectedCellID())));				    		
				    		calendar.popupBlockingModal();
				    		calendar.popupBlockingModal();
				    		if(!calendar.canceled)
				    			gridGes1.setCellText(gridGes1.getUserSelectedCellID(),calendar.getSelectedDate()==null?"":calendar.getSelectedDate().toString());
				    		/*id = new InputDialog("Atenção", pergunta[gridGes1.getUserSelectedRow()-1],
									 gridGes1.getCellText(gridGes1.getUserSelectedCellID()),new String[]{"Ok","Cancel"});
							id.setForeColor(ConstantesJanela.BLUE);
							id.popupBlockingModal();							
							if (id.getPressedButtonIndex() == 0){
								gridGes1.setCellText(gridGes1.getUserSelectedCellID(),id.getValue());
				    	      }	*/	
							break;					
						case 3:		
							calendar=new Calendar();
				    		calendar.setSelectedDate(new Date(gridGes1.getCellText(gridGes1.getUserSelectedCellID())));		    		
				    		calendar.popupBlockingModal();
				    		calendar.popupBlockingModal();
				    		if(!calendar.canceled)
				    			gridGes1.setCellText(gridGes1.getUserSelectedCellID(),calendar.getSelectedDate()==null?"":calendar.getSelectedDate().toString());
							 /*id = new InputDialog("Atenção", pergunta[gridGes1.getUserSelectedRow()-1],
									 gridGes1.getCellText(gridGes1.getUserSelectedCellID()),new String[]{"Ok","Cancel"});
							id.setForeColor(ConstantesJanela.BLUE);
							id.popupBlockingModal();
							if (id.getPressedButtonIndex() == 0){
								gridGes1.setCellText(gridGes1.getUserSelectedCellID(),id.getValue());
				    	      }		*/
							break;						
						}	
		    	/*}else{
		    		msgGrid = new MessageBox("Atenção","Somente é Permitido Alterar| Dados do Mês Atual", new String[]{"OK"});			    	
			    	msgGrid.popupBlockingModal();
			    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	}*/
	     } 	
	}
    
    private boolean ValidaForm(){
		  /*MessageBox msgGrid;
		    int mes = SIABBusiness.getMes();
		    if("".equals(gridGes1.getCellText(1,mes))){
		    	msgGrid = new MessageBox("Atenção","Preencha o Campo|Estado Nutricional", new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
		    }
		    if("".equals(gridGes1.getCellText(2,mes))){
		    	msgGrid = new MessageBox("Atenção","Preencha o Campo|Dt Consulta de Pré-Natal", new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
		    }
		    if("".equals(gridGes1.getCellText(3,mes))){
		    	msgGrid = new MessageBox("Atenção","Preencha o Campo|Dt Visita do ACS", new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
		    }*/
			return true;
	  }
}
