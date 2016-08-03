package br.com.bibiano.siab.window;

import litebase.ResultSet;
import waba.fx.Color;
import waba.sys.Convert;
import waba.sys.Settings;
import waba.sys.Vm;
import waba.ui.Button;
import waba.ui.ControlEvent;
import waba.ui.Event;
import waba.ui.MessageBox;
import waba.ui.Window;
import wextlib.ui.grid.BBGrid;
import wextlib.ui.grid.Grid;
import wextlib.ui.grid.GridEvent;
import wextlib.ui.grid.GridStorageSingleHashtable;
import br.com.bibiano.siab.business.FichaANgc;
import br.com.bibiano.siab.business.FichaHanNgc;
/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class ListaFichaHanWin extends Window {
	private static ListaFichaHanWin fichaHanWin;
	
	private Button btnPrev;
	private Button btnNew;
	private Button btnDel;
	private Grid gridFichaHan;
	public static String cmd;
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static ListaFichaHanWin criarInstancia()
	{
		if(fichaHanWin == null)
		{
			fichaHanWin = new ListaFichaHanWin();
		}
		return fichaHanWin;
	}
	
	public ListaFichaHanWin(){
		super("Lista Ficha HAN",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(gridFichaHan = new BBGrid());
		gridFichaHan.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, TOP + 3*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 120*(int) ConstantesJanela.FATOR_Y);
		gridFichaHan.setStorage(new GridStorageSingleHashtable());
		gridFichaHan.setAllowSortRows(true);
		gridFichaHan.setAllowSortCols(true);
		gridFichaHan.setHighlightSelectedCell(true);
		gridFichaHan.setRowCount(9);
		gridFichaHan.setColCount(3);
		gridFichaHan.setFixedRowCount(1);
		gridFichaHan.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);
		gridFichaHan.setColSeparatorWidth(1);
		gridFichaHan.setRowSeparatorHeight(1);
		gridFichaHan.setDrawColSeparators(true);
		gridFichaHan.setDrawRowSeparators(true);
		gridFichaHan.setUpdating(false);
		gridFichaHan.setFullRowSelectionDisplay(true);
		gridFichaHan.setColWidth(0,0);
		gridFichaHan.setColWidth(1,42*(int)ConstantesJanela.FATOR_X);
		gridFichaHan.setColWidth(2, 97*(int)ConstantesJanela.FATOR_X);
		gridFichaHan.setCellText(0,1,"COD");
		gridFichaHan.setCellText(0,2,"NOME");

		add(btnPrev = new Button("<<"));
		btnPrev.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		add(btnNew = new Button("Novo"));
		btnNew.setRect(RIGHT - 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		add(btnDel= new Button("Excluir"));
		btnDel.setRect(BEFORE - 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 40*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		if(Settings.isColor)
		{	//Setando as cores se o dispositivos suportar cores.
	    	Color BLUE =ConstantesJanela.BLUE;	    	
		     setForeColor(BLUE);
		     btnPrev.setForeColor(BLUE);
		     btnNew.setForeColor(BLUE);
		     btnDel.setForeColor(BLUE);
		     gridFichaHan.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridFichaHan.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		}		
	}
	
	public void PopulaForm(){
		try {
			int numLinhas =gridFichaHan.getRowCount();			
			for(int i=1;i<numLinhas;i++){
				gridFichaHan.setRowStrings(i,new String[]{"","",""});
			}
			ResultSet rsFichaHan = FichaHanNgc.criarInstancia().ListaFichaAll();
			int i=1;
			while (rsFichaHan.next()) {
				gridFichaHan.setCellText(i,0,""+rsFichaHan.getInt("NR_FAMILIA"));
				gridFichaHan.setCellText(i,1,""+rsFichaHan.getInt("CD_PACIENTE"));
				ResultSet rsFichaA = FichaANgc.criarInstancia().GetPaciente(rsFichaHan.getInt("NR_FAMILIA"),rsFichaHan.getInt("CD_PACIENTE"));
				if(rsFichaA.next())
					gridFichaHan.setCellText(i,2,rsFichaA.getString("NM_PACIENTE"));				
				i++;
			}
			if(i>9){
				gridFichaHan.setRowCount(i);	
			}else{
				gridFichaHan.setRowCount(9);
			}
		} catch (Exception e) {
			Vm.debug("Erro: "+e.getMessage());
			MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+e.getMessage());
			msgBoxErro.popupBlockingModal();
		}
	}

    public void onEvent(Event e) {	
    	try{
    		if (e.type==ControlEvent.PRESSED) {			
				if (e.target == btnNew) {
					this.cmd="N";
					FichaHan1Win fichaHan1Win = FichaHan1Win.criarInstancia();		
					fichaHan1Win.PopulaForm();
					fichaHan1Win.LimpaForm();
					fichaHan1Win.popupBlockingModal();
	    			PopulaForm();
			    }else if (e.target == btnPrev){
			    	unpop();
			    }else if(e.target==btnDel){
			    	if(gridFichaHan.getUserSelectedRow()>=0 & !"".equals(gridFichaHan.getCellText(gridFichaHan.getUserSelectedRow(),0))){
			    		MessageBox msgBoxFinaliza = new MessageBox("Atenção", "Deseja Excluir a| Ficha Selecionada?",new String[]{"Sim","Não"});
				    	msgBoxFinaliza.popupBlockingModal();			    	
				    	if(msgBoxFinaliza.getPressedButtonIndex()==0){
					    	FichaHanNgc fichaHanNgc = FichaHanNgc.criarInstancia();
					    	fichaHanNgc.DeleteFichaHan(Convert.toInt(gridFichaHan.getCellText(gridFichaHan.getUserSelectedRow(),0)),Convert.toInt(gridFichaHan.getCellText(gridFichaHan.getUserSelectedRow(),1)));
					    	gridFichaHan.removeRow(gridFichaHan.getUserSelectedRow());
					    	gridFichaHan.setRowCount(gridFichaHan.getRowCount()+1);
					    	PopulaForm();
				    	}
			    	}else{
			    		MessageBox msgBoxFinaliza = new MessageBox("Atenção", "Selecione um Item do Grid",new String[]{"OK"});
				    	msgBoxFinaliza.popupBlockingModal();	
			    	}
			    }
    		}else if(e.type==GridEvent.CELL_DOUBLE_CLICKED){
    			if(!"".equals(gridFichaHan.getCellText(gridFichaHan.getUserSelectedRow(),0))){
    				FichaHan1Win fichaHan1Win = FichaHan1Win.criarInstancia();
        		    fichaHan1Win.cdFamilia=Convert.toInt(gridFichaHan.getCellText(gridFichaHan.getUserSelectedRow(),0));
        		    fichaHan1Win.cdPaciente=Convert.toInt(gridFichaHan.getCellText(gridFichaHan.getUserSelectedRow(),1));
        			this.cmd="U";
        			fichaHan1Win.LimpaForm();
        			fichaHan1Win.PopulaForm();
        			fichaHan1Win.popupBlockingModal();
    			}
			}
    	}catch (Exception error) {
			MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+error.getMessage());
			msgBoxErro.popupBlockingModal();
			Vm.debug("Erro: "+error.getMessage());
		}   
	}  
}
