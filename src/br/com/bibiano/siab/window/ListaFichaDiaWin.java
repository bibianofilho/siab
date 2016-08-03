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
import br.com.bibiano.siab.business.FichaDiaNgc;
/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class ListaFichaDiaWin extends Window {
	private static ListaFichaDiaWin listaFichaDiaWin;
	
	private Button btnPrev;
	private Button btnNew;
	private Button btnDel;
	private Grid gridFichaDia;
	public static String cmd;
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static ListaFichaDiaWin criarInstancia()
	{
		if(listaFichaDiaWin == null)
		{
			listaFichaDiaWin = new ListaFichaDiaWin();
		}
		return listaFichaDiaWin;
	}
	
	public ListaFichaDiaWin(){
		super("Lista Ficha DIA",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(gridFichaDia = new BBGrid());
		gridFichaDia.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, TOP + 3*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 120*(int) ConstantesJanela.FATOR_Y);
		gridFichaDia.setStorage(new GridStorageSingleHashtable());
		gridFichaDia.setAllowSortRows(true);
		gridFichaDia.setAllowSortCols(true);
		gridFichaDia.setHighlightSelectedCell(true);
		gridFichaDia.setRowCount(9);
		gridFichaDia.setColCount(3);
		gridFichaDia.setFixedRowCount(1);
		gridFichaDia.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);
		gridFichaDia.setColSeparatorWidth(1);
		gridFichaDia.setRowSeparatorHeight(1);
		gridFichaDia.setDrawColSeparators(true);
		gridFichaDia.setDrawRowSeparators(true);
		gridFichaDia.setUpdating(false);
		gridFichaDia.setFullRowSelectionDisplay(true);
		gridFichaDia.setColWidth(0,0);
		gridFichaDia.setColWidth(1,42*(int)ConstantesJanela.FATOR_X);
		//gridFichaDia.setColWidth(2, 100*(int)ConstantesJanela.FATOR_X);
		gridFichaDia.setColWidth(2, 97*(int)ConstantesJanela.FATOR_X);
		gridFichaDia.setCellText(0,1,"COD");
		gridFichaDia.setCellText(0,2,"NOME");

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
		     gridFichaDia.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridFichaDia.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		}
	}
	
	public void PopulaForm(){
		try {
			int numLinhas =gridFichaDia.getRowCount();			
			for(int i=1;i<numLinhas;i++){
				gridFichaDia.setRowStrings(i,new String[]{"","",""});
			}
			ResultSet rsFichaDia = FichaDiaNgc.criarInstancia().ListaFichaAll();
			int i=1;
			while (rsFichaDia.next()) {
				gridFichaDia.setCellText(i,0,""+rsFichaDia.getInt("NR_FAMILIA"));
				gridFichaDia.setCellText(i,1,""+rsFichaDia.getInt("CD_PACIENTE"));
				ResultSet rsFichaA = FichaANgc.criarInstancia().GetPaciente(rsFichaDia.getInt("NR_FAMILIA"),rsFichaDia.getInt("CD_PACIENTE"));
				if(rsFichaA.next())
					gridFichaDia.setCellText(i,2,rsFichaA.getString("NM_PACIENTE"));
				i++;
			}
			if(i>9){
				gridFichaDia.setRowCount(i);	
			}else{
				gridFichaDia.setRowCount(9);
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
					FichaDia1Win fichaDia1Win = FichaDia1Win.criarInstancia();		
					fichaDia1Win.LimpaForm();
					fichaDia1Win.PopulaForm();
					fichaDia1Win.popupBlockingModal();
	    			PopulaForm();
			    }else if (e.target == btnPrev){
			    	unpop();
			    }else if(e.target==btnDel){
			    	if(gridFichaDia.getUserSelectedRow()>=0 & !"".equals(gridFichaDia.getCellText(gridFichaDia.getUserSelectedRow(),0))){
			    		MessageBox msgBoxFinaliza = new MessageBox("Atenção", "Deseja Excluir a| Ficha Selecionada?",new String[]{"Sim","Não"});
				    	msgBoxFinaliza.popupBlockingModal();			    	
				    	if(msgBoxFinaliza.getPressedButtonIndex()==0){
					    	FichaDiaNgc fichaDiaNgc = FichaDiaNgc.criarInstancia();
					    	fichaDiaNgc.DeleteFichaDia(Convert.toInt(gridFichaDia.getCellText(gridFichaDia.getUserSelectedRow(),0)),Convert.toInt(gridFichaDia.getCellText(gridFichaDia.getUserSelectedRow(),1)));
					    	gridFichaDia.removeRow(gridFichaDia.getUserSelectedRow());
					    	gridFichaDia.setRowCount(gridFichaDia.getRowCount()+1);
					    	PopulaForm();
				    	}
			    	}else{
			    		MessageBox msgBoxFinaliza = new MessageBox("Atenção", "Selecione um Item do Grid",new String[]{"OK"});
				    	msgBoxFinaliza.popupBlockingModal();	
			    	}
			    }
    		}else if(e.type==GridEvent.CELL_DOUBLE_CLICKED){
    			if(!"".equals(gridFichaDia.getCellText(gridFichaDia.getUserSelectedRow(),0))){
	    			FichaDia1Win fichaDia1Win = FichaDia1Win.criarInstancia();
	    			fichaDia1Win.cdFamilia=Convert.toInt(gridFichaDia.getCellText(gridFichaDia.getUserSelectedRow(),0));
	    			fichaDia1Win.cdPaciente=Convert.toInt(gridFichaDia.getCellText(gridFichaDia.getUserSelectedRow(),1));
	    			this.cmd="U";
	    			fichaDia1Win.PopulaForm();
	    			fichaDia1Win.popupBlockingModal();
    			}
			}
    	}catch (Exception error) {
			MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+error.getMessage());
			msgBoxErro.popupBlockingModal();
			Vm.debug("Erro: "+error.getMessage());
		}   
	}  
}
