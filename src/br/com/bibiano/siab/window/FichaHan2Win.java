package br.com.bibiano.siab.window;

import litebase.ResultSet;
import superwaba.ext.xplat.ui.MultiEdit;
import waba.fx.Color;
import waba.sys.Convert;
import waba.sys.Settings;
import waba.sys.Vm;
import waba.ui.Button;
import waba.ui.Calendar;
import waba.ui.ControlEvent;
import waba.ui.Event;
import waba.ui.InputDialog;
import waba.ui.Label;
import waba.ui.MessageBox;
import waba.ui.Window;
import waba.util.Date;
import waba.util.Hashtable;
import wextlib.ui.grid.BBGrid;
import wextlib.ui.grid.Grid;
import wextlib.ui.grid.GridEvent;
import wextlib.ui.grid.GridStorageSingleHashtable;
import br.com.bibiano.siab.business.AgenteNgc;
import br.com.bibiano.siab.business.FichaHanNgc;
import br.com.bibiano.siab.business.SIABBusiness;
import br.com.bibiano.siab.util.BBDate;

/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class FichaHan2Win extends Window {
	private static FichaHan2Win fichaHan2Win;
	
	private Label lblObservação;
	private MultiEdit edtObservacao;
	private Grid gridHan2;
	private Button btnPrev;
	private Button btnFinaliza;
	private Calendar calendar;
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static FichaHan2Win criarInstancia()
	{
		if(fichaHan2Win == null)
		{
			fichaHan2Win = new FichaHan2Win();
		}
		return fichaHan2Win;
	}
	
	public FichaHan2Win(){
		super("FICHAB - HAN2",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(gridHan2 = new BBGrid());
		gridHan2.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, TOP + (int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 85*(int) ConstantesJanela.FATOR_Y);
		gridHan2.setStorage(new GridStorageSingleHashtable());
		gridHan2.setAllowSortRows(false);
		gridHan2.setAllowSortCols(false);
		gridHan2.setAllowUserResizeRows(false);
		gridHan2.setVerticalScrollVisible(true);
		gridHan2.setHighlightSelectedCell(true);
		gridHan2.setRowCount(6);
		gridHan2.setColCount(13);
		gridHan2.setFixedRowCount(1);
		gridHan2.setFixedColCount(1);
		gridHan2.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);
		gridHan2.setColSeparatorWidth(1);
		gridHan2.setRowSeparatorHeight(1);
		gridHan2.setDrawColSeparators(true);
		gridHan2.setDrawRowSeparators(true);
		gridHan2.setUpdating(false);
		gridHan2.setFullRowSelectionDisplay(true);
		gridHan2.setColWidth(0,60*(int)ConstantesJanela.FATOR_X);
		gridHan2.setColWidth(1,42*(int)ConstantesJanela.FATOR_X);
		gridHan2.setColWidth(2,42*(int)ConstantesJanela.FATOR_X);
		gridHan2.setColWidth(3,42*(int)ConstantesJanela.FATOR_X);
		gridHan2.setColWidth(4,42*(int)ConstantesJanela.FATOR_X);
		gridHan2.setColWidth(5,42*(int)ConstantesJanela.FATOR_X);
		gridHan2.setColWidth(6,42*(int)ConstantesJanela.FATOR_X);
		gridHan2.setColWidth(7,42*(int)ConstantesJanela.FATOR_X);
		gridHan2.setColWidth(8,42*(int)ConstantesJanela.FATOR_X);
		gridHan2.setColWidth(9,42*(int)ConstantesJanela.FATOR_X);
		gridHan2.setColWidth(10,42*(int)ConstantesJanela.FATOR_X);
		gridHan2.setColWidth(11,42*(int)ConstantesJanela.FATOR_X);
		gridHan2.setColWidth(12,42*(int)ConstantesJanela.FATOR_X);
		gridHan2.setColWidth(13,42*(int)ConstantesJanela.FATOR_X);
		String [] tituloGrid = {"","JAN","FEV","MAR","ABR","MAI","JUN","JUL","AGO","SET","OUT","NOV","DEZ"};
		gridHan2.setRowStrings(0,tituloGrid);
		String[]Coluna1={"","Dt Últ Dose Supervisionada","Faz Auto-Cuidados","Dt Última Consulta","Comunicantes Examinados","Comunicantes que Recebem BCG"};
		gridHan2.setColStrings(0,Coluna1);

		add(lblObservação= new Label("Observação:"),LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+(int)ConstantesJanela.FATOR_Y);
		add(edtObservacao = new MultiEdit(1, 2));
		edtObservacao.setRect(LEFT +3*(int) ConstantesJanela.FATOR_X, AFTER+(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 30*(int) ConstantesJanela.FATOR_Y);
				
		add(btnPrev = new Button("<<"));
		btnPrev.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 2*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		add(btnFinaliza = new Button("Finalizar"));
		btnFinaliza.setRect(RIGHT - 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 2*(int) ConstantesJanela.FATOR_Y, 50*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		if(Settings.isColor)
		{	//Setando as cores se o dispositivos suportar cores.
	    	Color BLUE =ConstantesJanela.BLUE;
		     setForeColor(BLUE); 
		     btnFinaliza.setForeColor(BLUE);
		     btnPrev.setForeColor(BLUE);
		     lblObservação.setForeColor(BLUE);
		     gridHan2.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridHan2.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		}		
	}
	
    public void PopulaGrid(){
    	try{	   
    		if(ListaFichaHanWin.cmd.equals("U")){  
    			FichaHanNgc fichaHanNgc = FichaHanNgc.criarInstancia();
    	    	ResultSet rs = fichaHanNgc.getFichaHan(FichaHan1Win.cdFamilia,FichaHan1Win.cdPaciente);
    			ResultSet rsAcomp = fichaHanNgc.GetFichaHanAcomp(FichaHan1Win.cdFamilia,FichaHan1Win.cdPaciente);     			
    			if(rs.next()){    				
    				edtObservacao.setText(rs.getString("DS_OBSERVACAO"));				
    			} 
    			String [] arrayMes = {"","JAN","FEV","MAR","ABR","MAI","JUN","JUL","AGO","SET","OUT","NOV","DEZ"};
    			String items[]  = new String[6];
    			while(rsAcomp.next()){
    	    		int mes = rsAcomp.getInt("CD_MES");
    	    		items[0]=arrayMes[mes];
    	    		items[1] = rsAcomp.getString("DT_ULTDOSE");
    	    		items[2] = rsAcomp.getString("FL_AUTOCUIDADOS");
    	    		items[3] = rsAcomp.getString("DT_CONSULTA");
    	    		items[4] = rsAcomp.getString("NUM_EXAMINADOS");
    	    		items[5] = rsAcomp.getString("NUM_COMBCG");
    	    		gridHan2.setColStrings(mes,items);
    	    	}
    		} 
    	}catch (Exception e) {
    		MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+e.getMessage());
			msgBoxErro.popupBlockingModal();
			Vm.debug("Erro: "+e.getMessage());
		}
    }
    
    public void LimpaForm(){
		edtObservacao.setText("");
		String[]Linha1={"Dt Últ Dose Supervisionada","","","","","","","","","","","",""};
		String[]Linha2={"Faz Auto-Cuidados","","","","","","","","","","","",""};
		String[]Linha3={"Dt Última Consulta","","","","","","","","","","","",""};
		String[]Linha4={"Comunicantes Examinados","","","","","","","","","","","",""};
		String[]Linha5={"Comunicantes que Recebem BCG","","","","","","","","","","","",""};		
		gridHan2.setRowStrings(1,Linha1);
		gridHan2.setRowStrings(2,Linha2);
		gridHan2.setRowStrings(3,Linha3);
		gridHan2.setRowStrings(4,Linha4);
		gridHan2.setRowStrings(5,Linha5);
	}

    public void onEvent(Event e) {	
    	try{
		    if (e.type == ControlEvent.PRESSED) { 
			    if (e.target == btnFinaliza) {
			    	if(ValidaForm()){
			    		MessageBox msgBoxFinaliza = new MessageBox("Fim", "Deseja Finalizar a Ficha HAN?",new String[]{"Sim","Não"});
				    	msgBoxFinaliza.popupBlockingModal();			    	
				    	if(msgBoxFinaliza.getPressedButtonIndex()==0){
				    		FichaHanNgc fichaHanNgc=FichaHanNgc.criarInstancia();
				    		Hashtable voFichaHan = new Hashtable(10);
				    		AgenteNgc agenteNgc = AgenteNgc.criarInstancia();
				    		ResultSet rs = agenteNgc.getSegAreaMicroa();
				    		if(rs.next()){
				    			voFichaHan.put("CD_SEGMENTO",rs.getString("CD_SEG"));
				    			voFichaHan.put("CD_AREA",rs.getString("CD_AREA"));
				    			voFichaHan.put("CD_MICROAREA",rs.getString("CD_MICROA"));
				    		}		
				    		voFichaHan.put("NR_FAMILIA",Convert.toString(FichaHan1Win.cdFamilia));
				    		voFichaHan.put("CD_PACIENTE",Convert.toString(FichaHan1Win.cdPaciente));				    		
				    		voFichaHan.put("NUM_COMUNICANTES",Convert.toString(FichaHan1Win.numComunicantes));
				    		voFichaHan.put("DS_OBSERVACAO",edtObservacao.getText()); 
				    		voFichaHan.put("DT_VISITA",FichaHan1Win.dtVisita);
				    		voFichaHan.put("FL_MEDICACAO",FichaHan1Win.flMedicacao);
				    		int mes = SIABBusiness.getMes();
				    		voFichaHan.put("CD_MES",""+SIABBusiness.getMes());
				    		voFichaHan.put("CD_ANO",""+SIABBusiness.getAno());
				    		voFichaHan.put("DT_ULTDOSE",gridHan2.getCellText(1,mes));
				    		voFichaHan.put("FL_AUTOCUIDADOS",gridHan2.getCellText(2,mes));
				    		voFichaHan.put("DT_CONSULTA",gridHan2.getCellText(3,mes));
				    		voFichaHan.put("NUM_EXAMINADOS",gridHan2.getCellText(4,mes));
				    		voFichaHan.put("NUM_COMBCG",gridHan2.getCellText(5,mes));
				    		if("U".equals(ListaFichaHanWin.cmd)){
				    			fichaHanNgc.UpdateFichaHan(voFichaHan);
				    		}else{
				    			fichaHanNgc.InserteFichaHan(voFichaHan);
				    		}
				    		ListaFichaHanWin.cmd="F";
  						    unpop();
				    	}
			    	}
			    }else if (e.target == btnPrev){
			    	unpop();
			    }
		    }else if(e.type == GridEvent.CELL_DOUBLE_CLICKED){
		    	if(e.target==gridHan2){
		    		EntradaDataGrid();
		    	}
		    }
    	}catch (Exception error) {
			MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+error.getMessage());
			msgBoxErro.popupBlockingModal();
			Vm.debug("Erro: "+error.getMessage());
		}   
	}  

	   private void EntradaDataGrid(){
		   MessageBox msgGrid =null;
		   String[] pergunta = {"Dt Últ Dose Supervisionada","Faz Auto-Cuidados","Dt Última Consulta",
							    "Comunicantes Examinados","Comunicantes que Recebem BCG"};
		    if(gridHan2.getUserSelectedCol()>0){
		    	if(gridHan2.getUserSelectedCol()==(new BBDate()).getMonth()){
		    		InputDialog id;
				    	switch (gridHan2.getUserSelectedRow()) {
						case 1:	
							calendar=new Calendar();
							calendar.setSelectedDate(new Date(gridHan2.getCellText(gridHan2.getUserSelectedCellID())));
							calendar.popupBlockingModal();
							calendar.popupBlockingModal();
							/*popupModal(calendar);
				    		unpop();
							popupBlockingModal(calendar);*/
							gridHan2.setCellText(gridHan2.getUserSelectedCellID(),calendar.getSelectedDate()==null?"":calendar.getSelectedDate().toString());				
							
								 /*id = new InputDialog("Atenção",
					    	    		  		pergunta[gridHan2.getUserSelectedRow()-1],
					    	    		  		gridHan2.getCellText(gridHan2.getUserSelectedCellID()),new String[]{"Ok","Cancel"});
								id.setForeColor(ConstantesJanela.BLUE);
								id.popupBlockingModal();
								if (id.getPressedButtonIndex() == 0){
									gridHan2.setCellText(gridHan2.getUserSelectedCellID(),id.getValue());
					    	      }	*/	
								break;
						case 2:									
						    	msgGrid = new MessageBox("Atenção",pergunta[gridHan2.getUserSelectedRow()-1], new String[]{"Sim","Não","Canc"});			    	
						    	msgGrid.popupBlockingModal();
						    	msgGrid.setForeColor(ConstantesJanela.BLUE);
						    	if(msgGrid.getPressedButtonIndex() == 0){						    	
						    		gridHan2.setCellText(gridHan2.getUserSelectedCellID(),"S");
						    		repaint();
						    	}else if(msgGrid.getPressedButtonIndex() == 1){						    		  
						    		gridHan2.setCellText(gridHan2.getUserSelectedCellID(),"N");						    		   
						    	}
							    break;
						case 3:	
							calendar=new Calendar();
							calendar.setSelectedDate(new Date(gridHan2.getCellText(gridHan2.getUserSelectedCellID())));
							calendar.popupBlockingModal();
							calendar.popupBlockingModal();
							gridHan2.setCellText(gridHan2.getUserSelectedCellID(),calendar.getSelectedDate()==null?"":calendar.getSelectedDate().toString());				
							
							 /*id = new InputDialog("Atenção",
				    	    		  		pergunta[gridHan2.getUserSelectedRow()-1],
				    	    		  		gridHan2.getCellText(gridHan2.getUserSelectedCellID()),new String[]{"Ok","Cancel"});
							id.setForeColor(ConstantesJanela.BLUE);
							id.popupBlockingModal();
							if (id.getPressedButtonIndex() == 0){
								gridHan2.setCellText(gridHan2.getUserSelectedCellID(),id.getValue());
				    	      }	*/	
							break;	
						case 4:					
							 id = new InputDialog("Atenção",
				    	    		  		pergunta[gridHan2.getUserSelectedRow()-1],
				    	    		  		gridHan2.getCellText(gridHan2.getUserSelectedCellID()),new String[]{"Ok","Cancel"});
							id.setForeColor(ConstantesJanela.BLUE);
							id.popupBlockingModal();
							if (id.getPressedButtonIndex() == 0){
								gridHan2.setCellText(gridHan2.getUserSelectedCellID(),id.getValue());
				    	      }		
							break;
						case 5:					
							 id = new InputDialog("Atenção",
				    	    		  		pergunta[gridHan2.getUserSelectedRow()-1],
				    	    		  		gridHan2.getCellText(gridHan2.getUserSelectedCellID()),new String[]{"Ok","Cancel"});
							id.setForeColor(ConstantesJanela.BLUE);
							id.popupBlockingModal();
							if (id.getPressedButtonIndex() == 0){
								gridHan2.setCellText(gridHan2.getUserSelectedCellID(),id.getValue());
				    	      }		
							break;	
						}
		    	}else{
		    		msgGrid = new MessageBox("Atenção","Somente é Permitido Alterar| Dados do Mês Atual", new String[]{"OK"});			    	
			    	msgGrid.popupBlockingModal();
			    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	}   
	       }
	  }	 

	  private boolean ValidaForm(){
		  String[] pergunta = {"Preencha o Campo|Dt Últ Dose Supervisionada",
						       "Preencha o Campo|Faz Auto-Cuidados",
						       "Preencha o Campo|Dt Última Consulta",
						       "Preencha o Campo|Comunicantes Examinados",
						       "Preencha o Campo|Comunicantes que Recebem BCG"};
		  MessageBox msgGrid;
		  if(edtObservacao.getText().length()>100){
	    		msgGrid = new MessageBox("Atenção","O Campo Observação Ultrapassou|a Quantidade Máxima de|100 Caracteres", new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
	    	 }   
		    int mes = SIABBusiness.getMes();
		    if("".equals(gridHan2.getCellText(1,mes))){
		    	msgGrid = new MessageBox("Atenção",pergunta[0], new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
		    }
		    if("".equals(gridHan2.getCellText(2,mes))){
		    	msgGrid = new MessageBox("Atenção",pergunta[1], new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
		    }
		    if("".equals(gridHan2.getCellText(3,mes))){
		    	msgGrid = new MessageBox("Atenção",pergunta[2], new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
		    }
		    if("".equals(gridHan2.getCellText(4,mes))){
		    	msgGrid = new MessageBox("Atenção",pergunta[3], new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
		    }
		    if("".equals(gridHan2.getCellText(5,mes))){
		    	msgGrid = new MessageBox("Atenção",pergunta[4], new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
		    }
			return true;
	  }
}
