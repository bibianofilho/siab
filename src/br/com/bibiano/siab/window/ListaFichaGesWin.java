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
import br.com.bibiano.siab.business.FichaGesNgc;
/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class ListaFichaGesWin extends Window {
	private static ListaFichaGesWin listaFichaGesWin;
	
	private Button btnPrev;
	private Button btnNew;
	private Button btnDel;
	private Grid gridFichaGes;
	public static String cmd;
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static ListaFichaGesWin criarInstancia()
	{
		if(listaFichaGesWin == null)
		{
			listaFichaGesWin = new ListaFichaGesWin();
		}
		return listaFichaGesWin;
	}
	
	public ListaFichaGesWin(){
		super("Lista Ficha GES",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(gridFichaGes = new BBGrid());
		gridFichaGes.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, TOP + 3*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 120*(int) ConstantesJanela.FATOR_Y);
		gridFichaGes.setStorage(new GridStorageSingleHashtable());
		gridFichaGes.setAllowSortRows(true);
		gridFichaGes.setAllowSortCols(true);
		gridFichaGes.setHighlightSelectedCell(true);
		gridFichaGes.setRowCount(9);
		gridFichaGes.setColCount(3);
		gridFichaGes.setFixedRowCount(1);
		gridFichaGes.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);
		gridFichaGes.setColSeparatorWidth(1);
		gridFichaGes.setRowSeparatorHeight(1);
		gridFichaGes.setDrawColSeparators(true);
		gridFichaGes.setDrawRowSeparators(true);
		gridFichaGes.setUpdating(false);
		gridFichaGes.setFullRowSelectionDisplay(true);
		gridFichaGes.setColWidth(0,0);
		gridFichaGes.setColWidth(1,42*(int)ConstantesJanela.FATOR_X);
		gridFichaGes.setColWidth(2, 97*(int)ConstantesJanela.FATOR_X);
		gridFichaGes.setCellText(0,1,"COD");
		gridFichaGes.setCellText(0,2,"NOME");

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
		     gridFichaGes.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridFichaGes.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		}
	}
	
	
	public void PopulaForm(){
		try {
			int numLinhas =gridFichaGes.getRowCount();			
			for(int i=1;i<numLinhas;i++){
				gridFichaGes.setRowStrings(i,new String[]{"","",""});
			}
			ResultSet rsFichaGes = FichaGesNgc.criarInstancia().ListaFichaAll();
			int i=1;
			while (rsFichaGes.next()) {
				gridFichaGes.setCellText(i,0,""+rsFichaGes.getInt("NR_FAMILIA"));
				gridFichaGes.setCellText(i,1,""+rsFichaGes.getInt("CD_PACIENTE"));
				ResultSet rsFichaA = FichaANgc.criarInstancia().GetPaciente(rsFichaGes.getInt("NR_FAMILIA"),rsFichaGes.getInt("CD_PACIENTE"));
				if(rsFichaA.next())
					gridFichaGes.setCellText(i,2,rsFichaA.getString("NM_PACIENTE"));
				i++;
			}
			if(i>9){
				gridFichaGes.setRowCount(i);	
			}else{
				gridFichaGes.setRowCount(9);
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
					FichaGes1Win fichaGes1Win = FichaGes1Win.criarInstancia();		
					fichaGes1Win.PopulaForm();
					fichaGes1Win.LimpaForm();
					fichaGes1Win.popupBlockingModal();
	    			PopulaForm();
			    }else if (e.target == btnPrev){
			    	unpop();
			    }else if(e.target==btnDel){
			    	if(gridFichaGes.getUserSelectedRow()>=0 & !"".equals(gridFichaGes.getCellText(gridFichaGes.getUserSelectedRow(),0))){
			    		MessageBox msgBoxFinaliza = new MessageBox("Atenção", "Deseja Excluir a| Ficha Selecionada?",new String[]{"Sim","Não"});
				    	msgBoxFinaliza.popupBlockingModal();			    	
				    	if(msgBoxFinaliza.getPressedButtonIndex()==0){
					    	FichaGesNgc fichaGesNgc = FichaGesNgc.criarInstancia();
					    	fichaGesNgc.DeleteFichaGes(Convert.toInt(gridFichaGes.getCellText(gridFichaGes.getUserSelectedRow(),0)),Convert.toInt(gridFichaGes.getCellText(gridFichaGes.getUserSelectedRow(),1)));
					    	gridFichaGes.removeRow(gridFichaGes.getUserSelectedRow());
					    	gridFichaGes.setRowCount(gridFichaGes.getRowCount()+1);
					    	PopulaForm();
				    	}
			    	}else{
			    		MessageBox msgBoxFinaliza = new MessageBox("Atenção", "Selecione um Item do Grid",new String[]{"OK"});
				    	msgBoxFinaliza.popupBlockingModal();	
			    	}
			    }
    		}else if(e.type==GridEvent.CELL_DOUBLE_CLICKED){
    			if(!"".equals(gridFichaGes.getCellText(gridFichaGes.getUserSelectedRow(),0))){
	    			FichaGes1Win fichaGes1Win = FichaGes1Win.criarInstancia();
	    			fichaGes1Win.cdFamilia=Convert.toInt(gridFichaGes.getCellText(gridFichaGes.getUserSelectedRow(),0));
	    			fichaGes1Win.cdPaciente=Convert.toInt(gridFichaGes.getCellText(gridFichaGes.getUserSelectedRow(),1));
	    			this.cmd="U";	    			
	    			fichaGes1Win.LimpaForm();
	    			fichaGes1Win.PopulaForm();
	    			fichaGes1Win.popupBlockingModal();
    			}
			}
    	}catch (Exception error) {
			MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+error.getMessage());
			msgBoxErro.popupBlockingModal();
			Vm.debug("Erro: "+error.getMessage());
		}   
	}
}
