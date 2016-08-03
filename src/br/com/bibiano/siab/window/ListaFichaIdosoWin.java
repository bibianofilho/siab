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
import br.com.bibiano.siab.business.FichaIdosoNgc;
/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class ListaFichaIdosoWin extends Window {
	private static ListaFichaIdosoWin fichaIdosoWin;
	
	private Button btnPrev;
	private Button btnNew;
	private Button btnDel;
	private Grid gridFichaIdoso;
	public static String cmd;
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static ListaFichaIdosoWin criarInstancia()
	{
		if(fichaIdosoWin == null)
		{
			fichaIdosoWin = new ListaFichaIdosoWin();
		}
		return fichaIdosoWin;
	}
	
	public ListaFichaIdosoWin(){
		super("Lista Ficha IDOSO",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(gridFichaIdoso = new BBGrid());
		gridFichaIdoso.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, TOP + 3*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 120*(int) ConstantesJanela.FATOR_Y);
		gridFichaIdoso.setStorage(new GridStorageSingleHashtable());
		gridFichaIdoso.setAllowSortRows(true);
		gridFichaIdoso.setAllowSortCols(true);
		gridFichaIdoso.setHighlightSelectedCell(true);
		gridFichaIdoso.setRowCount(9);
		gridFichaIdoso.setColCount(3);
		gridFichaIdoso.setFixedRowCount(1);
		gridFichaIdoso.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);
		gridFichaIdoso.setColSeparatorWidth(1);
		gridFichaIdoso.setRowSeparatorHeight(1);
		gridFichaIdoso.setDrawColSeparators(true);
		gridFichaIdoso.setDrawRowSeparators(true);
		gridFichaIdoso.setUpdating(false);
		gridFichaIdoso.setFullRowSelectionDisplay(true);
		gridFichaIdoso.setColWidth(0,0);
		gridFichaIdoso.setColWidth(1,42*(int)ConstantesJanela.FATOR_X);
		gridFichaIdoso.setColWidth(2, 97*(int)ConstantesJanela.FATOR_X);
		gridFichaIdoso.setCellText(0,1,"COD");
		gridFichaIdoso.setCellText(0,2,"NOME");

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
		     gridFichaIdoso.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridFichaIdoso.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		}
	}
	public void PopulaForm(){
		try {
			int numLinhas =gridFichaIdoso.getRowCount();			
			for(int i=1;i<numLinhas;i++){
				gridFichaIdoso.setRowStrings(i,new String[]{"","",""});
			}
			ResultSet rsFichaIdoso = FichaIdosoNgc.criarInstancia().ListaFichaAll();			
			int i=1;
			while (rsFichaIdoso.next()) {
				gridFichaIdoso.setCellText(i,0,""+rsFichaIdoso.getInt("NR_FAMILIA"));
				gridFichaIdoso.setCellText(i,1,""+rsFichaIdoso.getInt("CD_PACIENTE"));
				ResultSet rsFichaA = FichaANgc.criarInstancia().GetPaciente(rsFichaIdoso.getInt("NR_FAMILIA"),rsFichaIdoso.getInt("CD_PACIENTE"));
				gridFichaIdoso.setCellText(i,2,rsFichaA.getString("NM_PACIENTE"));
				i++;
			}
			if(i>9){
				gridFichaIdoso.setRowCount(i);	
			}else{
				gridFichaIdoso.setRowCount(9);
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
					FichaIdoso1Win fichaIdoso1Win = FichaIdoso1Win.criarInstancia();		
					fichaIdoso1Win.LimpaForm();
					fichaIdoso1Win.PopulaForm();
					fichaIdoso1Win.popupBlockingModal();
	    			PopulaForm();
			    }else if (e.target == btnPrev){
			    	unpop();
			    }else if(e.target==btnDel){
			    	if(gridFichaIdoso.getUserSelectedRow()>=0 & !"".equals(gridFichaIdoso.getCellText(gridFichaIdoso.getUserSelectedRow(),0))){
			    			MessageBox msgBoxFinaliza = new MessageBox("Atenção", "Deseja Excluir a| Ficha Selecionada?",new String[]{"Sim","Não"});
					    	msgBoxFinaliza.popupBlockingModal();			    	
					    	if(msgBoxFinaliza.getPressedButtonIndex()==0){
						    	FichaIdosoNgc fichaIdosoNgc = FichaIdosoNgc.criarInstancia();
						    	fichaIdosoNgc.DeleteFichaIdoso(Convert.toInt(gridFichaIdoso.getCellText(gridFichaIdoso.getUserSelectedRow(),0)),Convert.toInt(gridFichaIdoso.getCellText(gridFichaIdoso.getUserSelectedRow(),1)));
						    	gridFichaIdoso.removeRow(gridFichaIdoso.getUserSelectedRow());
						    	gridFichaIdoso.setRowCount(gridFichaIdoso.getRowCount()+1);
						    	PopulaForm();
					    	}
			    	}else{
			    		MessageBox msgBoxFinaliza = new MessageBox("Atenção", "Selecione um Item do Grid",new String[]{"OK"});
				    	msgBoxFinaliza.popupBlockingModal();	
			    	}
			    }
    		}else if(e.type==GridEvent.CELL_DOUBLE_CLICKED){
    			if(!"".equals(gridFichaIdoso.getCellText(gridFichaIdoso.getUserSelectedRow(),0))){
	    			FichaIdoso1Win fichaIdoso1Win = FichaIdoso1Win.criarInstancia();
	    			fichaIdoso1Win.cdFamilia=Convert.toInt(gridFichaIdoso.getCellText(gridFichaIdoso.getUserSelectedRow(),0));
	    			fichaIdoso1Win.cdPaciente=Convert.toInt(gridFichaIdoso.getCellText(gridFichaIdoso.getUserSelectedRow(),1));
	    			this.cmd="U";
	    			fichaIdoso1Win.LimpaForm();
	    			fichaIdoso1Win.PopulaForm();
	    			fichaIdoso1Win.popupBlockingModal();
    			}
			}
    	}catch (Exception error) {
			MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+error.getMessage());
			msgBoxErro.popupBlockingModal();
			Vm.debug("Erro: "+error.getMessage());
		}   
	}  
}
