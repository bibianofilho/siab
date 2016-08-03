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
import br.com.bibiano.siab.business.FichaCriancaNgc;
/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class ListaFichaCriancaWin extends Window {
	private static ListaFichaCriancaWin fichaCriancaWin;
	
	private Button btnPrev;
	private Button btnNew;
	private Button btnDel;
	private Grid gridFichaCrianca;
	public static String cmd;
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static ListaFichaCriancaWin criarInstancia()
	{
		if(fichaCriancaWin == null)
		{
			fichaCriancaWin = new ListaFichaCriancaWin();
		}
		return fichaCriancaWin;
	}
	
	public ListaFichaCriancaWin(){
		super("Lista Ficha CRIANÇA",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(gridFichaCrianca = new BBGrid());
		gridFichaCrianca.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, TOP + 3*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 120*(int) ConstantesJanela.FATOR_Y);
		gridFichaCrianca.setStorage(new GridStorageSingleHashtable());
		gridFichaCrianca.setAllowSortRows(true);
		gridFichaCrianca.setAllowSortCols(true);
		gridFichaCrianca.setHighlightSelectedCell(true);
		gridFichaCrianca.setRowCount(9);
		gridFichaCrianca.setColCount(3);
		gridFichaCrianca.setFixedRowCount(1);
		gridFichaCrianca.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);
		gridFichaCrianca.setColSeparatorWidth(1);
		gridFichaCrianca.setRowSeparatorHeight(1);
		gridFichaCrianca.setDrawColSeparators(true);
		gridFichaCrianca.setDrawRowSeparators(true);
		gridFichaCrianca.setUpdating(false);
		gridFichaCrianca.setFullRowSelectionDisplay(true);
		gridFichaCrianca.setColWidth(0,0);
		gridFichaCrianca.setColWidth(1,42*(int)ConstantesJanela.FATOR_X);
		gridFichaCrianca.setColWidth(2, 97*(int)ConstantesJanela.FATOR_X);
		gridFichaCrianca.setCellText(0,1,"COD");
		gridFichaCrianca.setCellText(0,2,"NOME");

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
		     gridFichaCrianca.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridFichaCrianca.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		}
	}
	public void PopulaForm(){
		try {
			
			int numLinhas =gridFichaCrianca.getRowCount();			
			for(int i=1;i<numLinhas;i++){
				gridFichaCrianca.setRowStrings(i,new String[]{"","",""});
			}
			ResultSet rsFichaCrianca = FichaCriancaNgc.criarInstancia().ListaFichaAll();			
			int i=1;
			while (rsFichaCrianca.next()) {
				gridFichaCrianca.setCellText(i,0,""+rsFichaCrianca.getInt("NR_FAMILIA"));
				gridFichaCrianca.setCellText(i,1,""+rsFichaCrianca.getInt("CD_PACIENTE"));
				ResultSet rsFichaA = FichaANgc.criarInstancia().GetPaciente(rsFichaCrianca.getInt("NR_FAMILIA"),rsFichaCrianca.getInt("CD_PACIENTE"));
				gridFichaCrianca.setCellText(i,2,rsFichaA.getString("NM_PACIENTE"));
				i++;
			}
			if(i>9){
				gridFichaCrianca.setRowCount(i);	
			}else{
				gridFichaCrianca.setRowCount(9);
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
					FichaCrianca1Win fichaCrianca1Win = FichaCrianca1Win.criarInstancia();		
					fichaCrianca1Win.LimpaForm();
					fichaCrianca1Win.PopulaForm();
					fichaCrianca1Win.popupBlockingModal();
	    			PopulaForm();
			    }else if (e.target == btnPrev){
			    	unpop();
			    }else if(e.target==btnDel){
			    	if(gridFichaCrianca.getUserSelectedRow()>=0 & !"".equals(gridFichaCrianca.getCellText(gridFichaCrianca.getUserSelectedRow(),0))){
			    			MessageBox msgBoxFinaliza = new MessageBox("Atenção", "Deseja Excluir a| Ficha Selecionada?",new String[]{"Sim","Não"});
					    	msgBoxFinaliza.popupBlockingModal();			    	
					    	if(msgBoxFinaliza.getPressedButtonIndex()==0){
						    	FichaCriancaNgc fichaCriancaNgc = FichaCriancaNgc.criarInstancia();
						    	fichaCriancaNgc.DeleteFichaCrianca(Convert.toInt(gridFichaCrianca.getCellText(gridFichaCrianca.getUserSelectedRow(),0)),Convert.toInt(gridFichaCrianca.getCellText(gridFichaCrianca.getUserSelectedRow(),1)));
						    	gridFichaCrianca.removeRow(gridFichaCrianca.getUserSelectedRow());
						    	gridFichaCrianca.setRowCount(gridFichaCrianca.getRowCount()+1);
						    	PopulaForm();
					    	}
			    	}else{
			    		MessageBox msgBoxFinaliza = new MessageBox("Atenção", "Selecione um Item do Grid",new String[]{"OK"});
				    	msgBoxFinaliza.popupBlockingModal();	
			    	}
			    }
    		}else if(e.type==GridEvent.CELL_DOUBLE_CLICKED){
    			if(!"".equals(gridFichaCrianca.getCellText(gridFichaCrianca.getUserSelectedRow(),0))){
	    			FichaCrianca1Win fichaCrianca1Win = FichaCrianca1Win.criarInstancia();
	    			fichaCrianca1Win.cdFamilia=Convert.toInt(gridFichaCrianca.getCellText(gridFichaCrianca.getUserSelectedRow(),0));
	    			fichaCrianca1Win.cdPaciente=Convert.toInt(gridFichaCrianca.getCellText(gridFichaCrianca.getUserSelectedRow(),1));
	    			this.cmd="U";
	    			fichaCrianca1Win.LimpaForm();
	    			fichaCrianca1Win.PopulaForm();
	    			fichaCrianca1Win.popupBlockingModal();
    			}
			}
    	}catch (Exception error) {
			MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+error.getMessage());
			msgBoxErro.popupBlockingModal();
			Vm.debug("Erro: "+error.getMessage());
		}   
	}  
}
