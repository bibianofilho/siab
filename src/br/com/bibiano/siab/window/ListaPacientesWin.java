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
import wextlib.ui.grid.GridCellID;
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
public class ListaPacientesWin extends Window {
	private static ListaPacientesWin listaPacientesWin;
	public static int cdFamilia;
 
	private Button btnPrev;
	private Button btnNext;
	private Button btnNew;
	private Button btnDel;
	private Grid gridPaciente;
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static ListaPacientesWin criarInstancia()
	{
		if(listaPacientesWin == null)
		{
			listaPacientesWin = new ListaPacientesWin();
		}
		return listaPacientesWin;
	}
	
	public ListaPacientesWin(){
		super("Membros Família",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(gridPaciente = new BBGrid());
		gridPaciente.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, TOP + 3*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 120*(int) ConstantesJanela.FATOR_Y);
		gridPaciente.setStorage(new GridStorageSingleHashtable());
		gridPaciente.setAllowSortRows(true);
		gridPaciente.setAllowSortCols(true);
		gridPaciente.setHighlightSelectedCell(true);
		gridPaciente.setRowCount(9);
		gridPaciente.setColCount(2);
		gridPaciente.setFixedRowCount(1);
		gridPaciente.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);
		gridPaciente.setColSeparatorWidth(1);
		gridPaciente.setRowSeparatorHeight(1);
		gridPaciente.setDrawColSeparators(true);
		gridPaciente.setDrawRowSeparators(true);
		gridPaciente.setUpdating(false);
		gridPaciente.setFullRowSelectionDisplay(true);
		gridPaciente.setColWidth(0,42*(int)ConstantesJanela.FATOR_X);
		gridPaciente.setColWidth(1, 97*(int)ConstantesJanela.FATOR_X);
		gridPaciente.setCellText(0,0,"COD");
		gridPaciente.setCellText(0,1,"NOME");

		add(btnPrev = new Button("<<"));
		btnPrev.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		add(btnNext = new Button(">>"));
		btnNext.setRect(RIGHT - 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y,30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		add(btnNew = new Button("Novo"));
		btnNew.setRect(BEFORE - 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		add(btnDel= new Button("Excluir"));
		btnDel.setRect(BEFORE - 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 40*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		//btnDel.setVisible(false);
		if(Settings.isColor)
		{	//Setando as cores se o dispositivos suportar cores.
	    	Color BLUE =ConstantesJanela.BLUE;
		     setForeColor(BLUE);
		     btnPrev.setForeColor(BLUE);
		     btnNew.setForeColor(BLUE);
		     btnDel.setForeColor(BLUE);
		     btnNext.setForeColor(BLUE);		     
		     gridPaciente.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridPaciente.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		}
	}
	
	public void LimpaForm(){
		/*gridPaciente.setRowCount(1);
		gridPaciente.setRowCount(9);*/
		int numLinhas =gridPaciente.getRowCount();			
		  for(int i=1;i<numLinhas;i++){
				gridPaciente.setRowStrings(i,new String[]{"",""});
		  }
	}
	
	public void PopulaGrid(int nrFamilia){
		try {		
			  this.cdFamilia=nrFamilia;
			  
			  int numLinhas =gridPaciente.getRowCount();			
			  for(int i=1;i<numLinhas;i++){
					gridPaciente.setRowStrings(i,new String[]{"",""});
			  }
			  ResultSet rs = FichaANgc.criarInstancia().ListaPacienteAll(nrFamilia);
			  int i=1;
			  while(rs.next()){
				  gridPaciente.setCellText(i,0,""+rs.getInt("CD_PACIENTE"));
				  gridPaciente.setCellText(i,1, rs.getString("NM_PACIENTE"));
				  i++;
			  }
			  if(i>9){
				  gridPaciente.setRowCount(i);	
			  }else{
				  gridPaciente.setRowCount(9);
			  }
			  gridPaciente.repaintNow();
		} catch (Exception e) {
			Vm.debug("Erro: "+e.getMessage());
			MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+e.getMessage());
			msgBoxErro.popupBlockingModal();			
		}
	}

    public void onEvent(Event e) {	
    	try{
		    if (e.type == ControlEvent.PRESSED) { 
			    if(e.target == btnPrev) {
			    	if("I".equals(ListaFamiliaWin.cmd)){
			    		if(!"".equals(gridPaciente.getCellText(1,0).trim())){
				    		MessageBox msgBox = new MessageBox("Atenção","Os pacientes serão excluídos",new String[]{"OK", "Cancelar"});
							msgBox.popupBlockingModal();
							if(msgBox.getPressedButtonIndex()==0){
								FichaANgc  fichaANgc = FichaANgc.criarInstancia();
								fichaANgc.DeleteFamilia(cdFamilia);
								unpop();
							}
				    	}else{
				    		unpop();
				    	}
			    	}else{
			    		unpop();
			    	}
			    	
			    }else if(e.target==btnNew){
			    	if(gridPaciente.getRowCount()>9){
			    		gridPaciente.setRowCount(gridPaciente.getRowCount()+1);	
			    	}			    	
					PacienteWin pacienteWin = PacienteWin.criarInstancia();
					pacienteWin.LimpaForm();
					pacienteWin.cmd="I";
					pacienteWin.PopulaForm(-1,-1);
					pacienteWin.popupBlockingModal();
	    			PopulaGrid(cdFamilia);
				}else if(e.target==btnNext){
					for (int i=1; i<gridPaciente.getRowCount(); i++){
						GridCellID cellID = new GridCellID(i, 1);
				    	if("".equals(gridPaciente.getCellText(cellID.Row,0).trim())){
				    		MessageBox msgBoxErro = new MessageBox("Atenção","Nenhum Paciente Cadastrado");
							msgBoxErro.popupBlockingModal();
							return;
				    	}else{
				    		break;
				    	}
					}
					FichaA2Win fichaA2Win = FichaA2Win.criarInstancia(); 
					fichaA2Win.LimpaAtributo();					
					fichaA2Win.LimpaForm();
					fichaA2Win.cdFamilia = this.cdFamilia;
					if(ListaFamiliaWin.cmd.equals("U")){
						fichaA2Win.CarregaAtributoPdb(cdFamilia);
						fichaA2Win.PopulaForm();
					}					
					fichaA2Win.popupBlockingModal();
					if("F".equals(ListaFamiliaWin.cmd)){
						   unpop();   
					   }
				}else if(e.target==btnDel){		
					if(gridPaciente.getUserSelectedRow()>=0 && !"".equals(gridPaciente.getCellText(gridPaciente.getUserSelectedRow(),0))){
						MessageBox msgBoxFinaliza = new MessageBox("Fim", "Deseja Excluir o Paciente?",new String[]{"Sim","Não"});
				    	msgBoxFinaliza.popupBlockingModal();			    	
				    	if(msgBoxFinaliza.getPressedButtonIndex()==0){
							GridCellID cellID = gridPaciente.getUserSelectedCellID();
					    	FichaANgc fichaANgc = FichaANgc.criarInstancia();
					    	fichaANgc.DeletePaciente(cdFamilia,Convert.toInt(gridPaciente.getCellText(cellID.Row,0)));
					    	gridPaciente.removeRow(cellID.Row);
					    	gridPaciente.setRowCount(gridPaciente.getRowCount()+1);
					    	PopulaGrid(cdFamilia);
					    	gridPaciente.repaint();
				    	} 	
					}else{
			    		MessageBox msgBoxFinaliza = new MessageBox("Atenção", "Selecione um Item do Grid",new String[]{"OK"});
				    	msgBoxFinaliza.popupBlockingModal();	
			    	}	
			    }
		    }else if(e.type==GridEvent.CELL_DOUBLE_CLICKED){
		    	if(!"".equals(gridPaciente.getCellText(gridPaciente.getUserSelectedRow(),0))){
	    			PacienteWin pacienteWin = PacienteWin.criarInstancia();
	    			GridCellID cellID = gridPaciente.getUserSelectedCellID();
	    			pacienteWin.LimpaForm();
	    			pacienteWin.cmd="U";
	    			pacienteWin.PopulaForm(cdFamilia,Convert.toInt(gridPaciente.getCellText(cellID.Row,0)));
	    			pacienteWin.popupBlockingModal();
	    			PopulaGrid(cdFamilia);
		    	}
			}
    	}catch (Exception error) {
			MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+error.getMessage());
			msgBoxErro.popupBlockingModal();
			Vm.debug("Erro: "+error.getMessage());
		}   
	}
}
