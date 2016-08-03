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
import br.com.bibiano.siab.business.FichaHaNgc;
/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class ListaFichaHaWin extends Window {
	private static ListaFichaHaWin fichaHaWin;
	
	private Button btnPrev;
	private Button btnNew;
	private Button btnDel;
	private Grid gridFichaHa;
	public static String cmd;
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static ListaFichaHaWin criarInstancia()
	{
		if(fichaHaWin == null)
		{
			fichaHaWin = new ListaFichaHaWin();
		}
		return fichaHaWin;
	}
	
	public ListaFichaHaWin(){
		super("Lista Ficha HA",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(gridFichaHa = new BBGrid());
		gridFichaHa.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, TOP + 3*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 120*(int) ConstantesJanela.FATOR_Y);
		gridFichaHa.setStorage(new GridStorageSingleHashtable());
		gridFichaHa.setAllowSortRows(true);
		gridFichaHa.setAllowSortCols(true);
		gridFichaHa.setHighlightSelectedCell(true);
		gridFichaHa.setRowCount(9);
		gridFichaHa.setColCount(3);
		gridFichaHa.setFixedRowCount(1);
		gridFichaHa.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);
		gridFichaHa.setColSeparatorWidth(1);
		gridFichaHa.setRowSeparatorHeight(1);
		gridFichaHa.setDrawColSeparators(true);
		gridFichaHa.setDrawRowSeparators(true);
		gridFichaHa.setUpdating(false);
		gridFichaHa.setFullRowSelectionDisplay(true);
		gridFichaHa.setColWidth(0,0);
		gridFichaHa.setColWidth(1,42*(int)ConstantesJanela.FATOR_X);
		gridFichaHa.setColWidth(2, 97*(int)ConstantesJanela.FATOR_X);
		gridFichaHa.setCellText(0,1,"COD");
		gridFichaHa.setCellText(0,2,"NOME");

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
		     gridFichaHa.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridFichaHa.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		}
	}
	public void PopulaForm(){
		try {
			int numLinhas =gridFichaHa.getRowCount();			
			for(int i=1;i<numLinhas;i++){
				gridFichaHa.setRowStrings(i,new String[]{"","",""});
			}
			ResultSet rsFichaDia = FichaHaNgc.criarInstancia().ListaFichaAll();			
			int i=1;
			while (rsFichaDia.next()) {
				gridFichaHa.setCellText(i,0,""+rsFichaDia.getInt("NR_FAMILIA"));
				gridFichaHa.setCellText(i,1,""+rsFichaDia.getInt("CD_PACIENTE"));
				ResultSet rsFichaA = FichaANgc.criarInstancia().GetPaciente(rsFichaDia.getInt("NR_FAMILIA"),rsFichaDia.getInt("CD_PACIENTE"));
				if(rsFichaA.next())
					gridFichaHa.setCellText(i,2,rsFichaA.getString("NM_PACIENTE"));
				i++;
			}
			if(i>9){
				gridFichaHa.setRowCount(i);	
			}else{
				gridFichaHa.setRowCount(9);
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
					FichaHa1Win fichaHa1Win = FichaHa1Win.criarInstancia();		
					fichaHa1Win.LimpaForm();
					fichaHa1Win.PopulaForm();
					fichaHa1Win.popupBlockingModal();
	    			PopulaForm();
			    }else if (e.target == btnPrev){
			    	unpop();
			    }else if(e.target==btnDel){
			    	if(gridFichaHa.getUserSelectedRow()>=0 && !"".equals(gridFichaHa.getCellText(gridFichaHa.getUserSelectedRow(),0))){
			    		MessageBox msgBoxFinaliza = new MessageBox("Atenção", "Deseja Excluir a| Ficha Selecionada?",new String[]{"Sim","Não"});
				    	msgBoxFinaliza.popupBlockingModal();			    	
				    	if(msgBoxFinaliza.getPressedButtonIndex()==0){
					    	FichaHaNgc fichaHaNgc = FichaHaNgc.criarInstancia();
					    	fichaHaNgc.DeleteFichaHa(Convert.toLong(gridFichaHa.getCellText(gridFichaHa.getUserSelectedRow(),0)),Convert.toLong(gridFichaHa.getCellText(gridFichaHa.getUserSelectedRow(),1)));
					    	gridFichaHa.removeRow(gridFichaHa.getUserSelectedRow());
					    	gridFichaHa.setRowCount(gridFichaHa.getRowCount()+1);
					    	PopulaForm();
				    	}
			    	}else{
			    		MessageBox msgBoxFinaliza = new MessageBox("Atenção", "Selecione um Item do Grid",new String[]{"OK"});
				    	msgBoxFinaliza.popupBlockingModal();	
			    	}
			    }
    		}else if(e.type==GridEvent.CELL_DOUBLE_CLICKED){
    			if(!"".equals(gridFichaHa.getCellText(gridFichaHa.getUserSelectedRow(),0))){
	    			FichaHa1Win fichaHa1Win = FichaHa1Win.criarInstancia();
	    			fichaHa1Win.cdFamilia=Convert.toInt(gridFichaHa.getCellText(gridFichaHa.getUserSelectedRow(),0));
	    			fichaHa1Win.cdPaciente=Convert.toInt(gridFichaHa.getCellText(gridFichaHa.getUserSelectedRow(),1));
	    			this.cmd="U";
	    			fichaHa1Win.LimpaForm();
	    			fichaHa1Win.PopulaForm();
	    			fichaHa1Win.popupBlockingModal();
    			}
			}
    	}catch (Exception error) {
			MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+error.getMessage());
			msgBoxErro.popupBlockingModal();
			Vm.debug("Erro: "+error.getMessage());
		}   
	}  
}
