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
/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class ListaFamiliaWin extends Window {
	private static ListaFamiliaWin familiaWin;

	private Button btnPrev;
	private Button btnNew;
	private Button btnDel;
	private Grid gridFamilia;
	public static String cmd;
	
	protected static int cdSegmento;
	protected static int cdArea;
	protected static int cdMicroArea;
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static ListaFamiliaWin criarInstancia()
	{
		if(familiaWin == null)
		{
			familiaWin = new ListaFamiliaWin();
		}
		return familiaWin;
	}
	
	public ListaFamiliaWin(){
		super("Lista de Famílias",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(gridFamilia = new BBGrid());
		gridFamilia.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, TOP + 3*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 120*(int) ConstantesJanela.FATOR_Y);
		gridFamilia.setStorage(new GridStorageSingleHashtable());
		gridFamilia.setAllowSortRows(true);
		gridFamilia.setAllowSortCols(true);
		gridFamilia.setHighlightSelectedCell(true);
		gridFamilia.setRowCount(9);
		gridFamilia.setColCount(2);
		gridFamilia.setFixedRowCount(1);
		gridFamilia.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);
		gridFamilia.setColSeparatorWidth(1);
		gridFamilia.setRowSeparatorHeight(1);
		gridFamilia.setDrawColSeparators(true);
		gridFamilia.setDrawRowSeparators(true);
		gridFamilia.setUpdating(false);
		gridFamilia.setFullRowSelectionDisplay(true);
		gridFamilia.setColWidth(0,42*(int)ConstantesJanela.FATOR_X);
		gridFamilia.setColWidth(1, 97*(int)ConstantesJanela.FATOR_X);
		gridFamilia.setCellText(0,0,"COD");
		gridFamilia.setCellText(0,1,"ENDEREÇO");

		add(btnPrev = new Button("<<"));
		btnPrev.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		add(btnNew = new Button("Novo"));
		btnNew.setRect(RIGHT - 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		add(btnDel= new Button("Excluir"));
		btnDel.setRect(BEFORE - 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 40*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		btnDel.setVisible(false);
		
		if(Settings.isColor)
		{	//Setando as cores se o dispositivos suportar cores.
	    	Color BLUE =ConstantesJanela.BLUE;
		     setForeColor(BLUE);
		     btnPrev.setForeColor(BLUE);
		     btnNew.setForeColor(BLUE);
		     btnDel.setForeColor(BLUE);
		     gridFamilia.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridFamilia.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		}
		//TODO PRENCHER CHAVE
		/*this.cdSegmento=1;
		this.cdArea=1;
		this.cdMicroArea=1;*/
	}
	public void PopulaForm(){
		try {
			ResultSet rsFamilia = FichaANgc.criarInstancia().ListaFamiliasAll();
			int i=1;
			while (rsFamilia.next()) {
				gridFamilia.setCellText(i,0,""+rsFamilia.getInt("NR_FAMILIA"));
				gridFamilia.setCellText(i,1,rsFamilia.getString("DS_ENDERECO"));
				i++;
			}
			if(i>9){
				gridFamilia.setRowCount(i);	
			}else{
				gridFamilia.setRowCount(9);
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
					/*MessageBox msgBoxErro = new MessageBox("Atenção","Não é permitido Adicionar|Família na versão de Demonstração");
					msgBoxErro.popupBlockingModal();
					return;*/
					this.cmd="I";
					ListaPacientesWin listaPacientesWin = ListaPacientesWin.criarInstancia();
					FichaANgc fichaANgc = FichaANgc.criarInstancia();
					listaPacientesWin.cdFamilia = fichaANgc.getCdFamiliaNew();
					listaPacientesWin.LimpaForm();
					listaPacientesWin.popupBlockingModal();
	    			PopulaForm();
			    }else if (e.target == btnPrev){
			    	unpop();
			    }else if(e.target==btnDel){
			    	if(gridFamilia.getUserSelectedRow()>=0 & !"".equals(gridFamilia.getCellText(gridFamilia.getUserSelectedRow(),0))){
			    		MessageBox msgBoxFinaliza = new MessageBox("Atenção", "Deseja Excluir a| Ficha Selecionada?",new String[]{"Sim","Não"});
				    	msgBoxFinaliza.popupBlockingModal();	
				    	if(msgBoxFinaliza.getPressedButtonIndex()==0){
				    		FichaANgc fichaANgc = FichaANgc.criarInstancia();			    	
					    	fichaANgc.DeleteFamilia(Convert.toInt(gridFamilia.getCellText(gridFamilia.getUserSelectedCellID().Row,0)));
					    	gridFamilia.removeRow(gridFamilia.getUserSelectedCellID().Row);
					    	gridFamilia.setRowCount(gridFamilia.getRowCount()+1);
				    	} 	
			    	}else{
			    		MessageBox msgBoxFinaliza = new MessageBox("Atenção", "Selecione um Item do Grid",new String[]{"OK"});
				    	msgBoxFinaliza.popupBlockingModal();	
			    	}			    	
			    }
    		}else if(e.type==GridEvent.CELL_DOUBLE_CLICKED){
    			if(!"".equals(gridFamilia.getCellText(gridFamilia.getUserSelectedRow(),0))){
	    			GridEvent ge = (GridEvent) e;   			
	    			ListaPacientesWin listaPacientesWin = ListaPacientesWin.criarInstancia();
	    			listaPacientesWin.PopulaGrid(Convert.toInt(gridFamilia.getCellText(ge.cellID.Row,0)));
	    			this.cmd="U";
	    			listaPacientesWin.popupBlockingModal();
	    			PopulaForm();
    			}
			}
    	}catch (Exception error) {
			MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+error.getMessage());
			msgBoxErro.popupBlockingModal();
			Vm.debug("Erro: "+error.getMessage());
		}   
	}  
}
