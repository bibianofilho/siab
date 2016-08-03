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
import br.com.bibiano.siab.business.FichaHaNgc;
import br.com.bibiano.siab.business.SIABBusiness;
import br.com.bibiano.siab.util.BBDate;

/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class FichaHa2Win extends Window {
	private static FichaHa2Win fichaDia2Win;
	
	private Label lblObservação;
	private MultiEdit edtObservacao;
	private Grid gridHa2;
	private Button btnPrev;
	private Button btnNext;
	private Calendar calendar;
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static FichaHa2Win criarInstancia()
	{
		if(fichaDia2Win == null)
		{
			fichaDia2Win = new FichaHa2Win();
		}
		return fichaDia2Win;
	}
	public FichaHa2Win(){
		super("FICHAB - HA2",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(gridHa2 = new BBGrid());		
		gridHa2.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, TOP + 1*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 80*(int) ConstantesJanela.FATOR_Y);
		gridHa2.setStorage(new GridStorageSingleHashtable());
		gridHa2.setAllowSortRows(false);
		gridHa2.setAllowSortCols(false);
		gridHa2.setAllowUserResizeRows(false);
		gridHa2.setVerticalScrollVisible(false);
		gridHa2.setHighlightSelectedCell(true);
		gridHa2.setRowCount(5);
		gridHa2.setColCount(13);
		gridHa2.setFixedRowCount(1);
		gridHa2.setFixedColCount(1);		
		gridHa2.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);
		gridHa2.setColSeparatorWidth(1);
		gridHa2.setRowSeparatorHeight(1);
		gridHa2.setDrawColSeparators(true);
		gridHa2.setDrawRowSeparators(true);
		gridHa2.setUpdating(false);
		gridHa2.setFullRowSelectionDisplay(true);		
		gridHa2.setColWidth(0,60*(int)ConstantesJanela.FATOR_X);
		gridHa2.setColWidth(1,42*(int)ConstantesJanela.FATOR_X);
		gridHa2.setColWidth(2,42*(int)ConstantesJanela.FATOR_X);
		gridHa2.setColWidth(3,42*(int)ConstantesJanela.FATOR_X);
		gridHa2.setColWidth(4,42*(int)ConstantesJanela.FATOR_X);
		gridHa2.setColWidth(5,42*(int)ConstantesJanela.FATOR_X);
		gridHa2.setColWidth(6,42*(int)ConstantesJanela.FATOR_X);
		gridHa2.setColWidth(7,42*(int)ConstantesJanela.FATOR_X);
		gridHa2.setColWidth(8,42*(int)ConstantesJanela.FATOR_X);
		gridHa2.setColWidth(9,42*(int)ConstantesJanela.FATOR_X);
		gridHa2.setColWidth(10,42*(int)ConstantesJanela.FATOR_X);
		gridHa2.setColWidth(11,42*(int)ConstantesJanela.FATOR_X);
		gridHa2.setColWidth(12,42*(int)ConstantesJanela.FATOR_X);
		gridHa2.setColWidth(13,42*(int)ConstantesJanela.FATOR_X);		
		String [] tituloGrid = {"","JAN","FEV","MAR","ABR","MAI","JUN","JUL","AGO","SET","OUT","NOV","DEZ"};
		gridHa2.setRowStrings(0,tituloGrid);
		String[]Coluna1={"","Toma Medicação","Faz Exercícios Físicos","Pressão Arterial","Dt Última Consulta"};
		gridHa2.setColStrings(0,Coluna1);
		
		add(lblObservação= new Label("Observação:"),LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+(int)ConstantesJanela.FATOR_Y);
		add(edtObservacao = new MultiEdit(1, 3));
		edtObservacao.setRect(LEFT +3*(int) ConstantesJanela.FATOR_X, AFTER+1*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 36*(int) ConstantesJanela.FATOR_Y);
		
		add(btnPrev = new Button("<<"));
		btnPrev.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 1*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		add(btnNext = new Button("Finalizar"));
		btnNext.setRect(RIGHT - 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 1*(int) ConstantesJanela.FATOR_Y, 50*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		if(Settings.isColor)
		{	//Setando as cores se o dispositivos suportar cores.
	    	Color BLUE =ConstantesJanela.BLUE;
		     setForeColor(BLUE); 
		     btnNext.setForeColor(BLUE);
		     btnPrev.setForeColor(BLUE);
		     lblObservação.setForeColor(BLUE);
		     gridHa2.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridHa2.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		}		
	}
	
    public void PopulaGrid(){
    	try{
    		if(ListaFichaHaWin.cmd.equals("U")){  
    			FichaHaNgc fichaHaNgc = FichaHaNgc.criarInstancia();
    	    	ResultSet rs = fichaHaNgc.getFichaHa(FichaHa1Win.cdFamilia,FichaHa1Win.cdPaciente);
    			ResultSet rsAcomp = fichaHaNgc.GetFichaHaAcomp(FichaHa1Win.cdFamilia,FichaHa1Win.cdPaciente);     			
    			if(rs.next()){    				
    				edtObservacao.setText(rs.getString("DS_OBSERVACAO"));				
    			} 
    			String [] arrayMes = {"","JAN","FEV","MAR","ABR","MAI","JUN","JUL","AGO","SET","OUT","NOV","DEZ"};
    			String items[]  = new String[6];
    			while(rsAcomp.next()){
    	    		int mes = rsAcomp.getInt("CD_MES");
    	    		items[0]=arrayMes[mes];
    	    		items[1] = rsAcomp.getString("FL_MEDICACAO");
    	    		items[2] = rsAcomp.getString("FL_EXERCICIO");
    	    		items[3] = rsAcomp.getString("DS_PRESSAO");
    	    		items[4] = rsAcomp.getString("DT_ULTCONSULTA");
    	    		gridHa2.setColStrings(mes,items);
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
		String[]Linha1={"Toma Medicação","","","","","","","","","","","",""};
		String[]Linha2={"Faz Exercícios Físicos","","","","","","","","","","","",""};
		String[]Linha3={"Pressão Arterial","","","","","","","","","","","",""};
		String[]Linha4={"Dt Última Consulta","","","","","","","","","","","",""};
		gridHa2.setRowStrings(1,Linha1);
		gridHa2.setRowStrings(2,Linha2);
		gridHa2.setRowStrings(3,Linha3);
		gridHa2.setRowStrings(4,Linha4);
	}

    public void onEvent(Event e) {	
    	try{
		    if (e.type == ControlEvent.PRESSED) { 
			    if (e.target == btnNext) {
			    	if(ValidaForm()){
			    		MessageBox msgBoxFinaliza = new MessageBox("Fim", "Deseja Finalizar a Ficha HA?",new String[]{"Sim","Não"});
				    	msgBoxFinaliza.popupBlockingModal();			    	
				    	if(msgBoxFinaliza.getPressedButtonIndex()==0){
				    		FichaHaNgc fichaHaNgc=FichaHaNgc.criarInstancia();
				    		Hashtable voFichaHa = new Hashtable(10);
				    		AgenteNgc agenteNgc = AgenteNgc.criarInstancia();
				    		ResultSet rs = agenteNgc.getSegAreaMicroa();
				    		if(rs.next()){
				    			voFichaHa.put("CD_SEGMENTO",rs.getString("CD_SEG"));
				    			voFichaHa.put("CD_AREA",rs.getString("CD_AREA"));
				    			voFichaHa.put("CD_MICROAREA",rs.getString("CD_MICROA"));
				    		}	
				    		voFichaHa.put("NR_FAMILIA",Convert.toString(FichaHa1Win.cdFamilia));
				    		voFichaHa.put("CD_PACIENTE",Convert.toString(FichaHa1Win.cdPaciente));	
				    		voFichaHa.put("FL_FUMANTE",FichaHa1Win.flFumante); 
				    		voFichaHa.put("DS_OBSERVACAO",edtObservacao.getText()); 
				    		voFichaHa.put("DT_VISITA",FichaHa1Win.dtVisita);
				    		voFichaHa.put("FL_DIETA",FichaHa1Win.flDieta);
				    		int mes = SIABBusiness.getMes();
				    		voFichaHa.put("CD_MES",""+SIABBusiness.getMes());
				    		voFichaHa.put("CD_ANO",""+SIABBusiness.getAno());
				    		voFichaHa.put("FL_MEDICACAO",gridHa2.getCellText(1,mes));
				    		voFichaHa.put("FL_EXERCICIO",gridHa2.getCellText(2,mes));
				    		voFichaHa.put("DS_PRESSAO",gridHa2.getCellText(3,mes));
				    		voFichaHa.put("DT_ULTCONSULTA",gridHa2.getCellText(4,mes));				    		
				    		if("U".equals(ListaFichaHaWin.cmd)){
				    			fichaHaNgc.UpdateFichaHa(voFichaHa);
				    		}else{
				    			fichaHaNgc.InserteFichaHa(voFichaHa);
				    		}
				    		ListaFichaHaWin.cmd="F";
  						    unpop();
				    	}
			    	}
			    }else if (e.target == btnPrev){
			    	unpop();
			    }
		    }else if(e.type == GridEvent.CELL_DOUBLE_CLICKED){
		    	if(e.target==gridHa2){
		    		EntraDataGrid();
		    	}
		    }
    	}catch (Exception error) {
			MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+error.getMessage());
			msgBoxErro.popupBlockingModal();
			Vm.debug("Erro: "+error.getMessage());
		}   
	}  
		   
	private void EntraDataGrid(){
		 MessageBox msgGrid =null;
		 InputDialog id =null;
		 String[]pergunta={"Toma Medicação","Faz Exercícios Físicos","Pressão Arterial","Dt Última Consulta"};
		   
	     if(gridHa2.getUserSelectedCol()>0){	    	
		    	if(gridHa2.getUserSelectedCol()==(new BBDate()).getMonth()){
				    	switch (gridHa2.getUserSelectedRow()) {
				    	case 1:									
					    	msgGrid = new MessageBox("Atenção",pergunta[gridHa2.getUserSelectedRow()-1], new String[]{"Sim","Não","Canc"});			    	
					    	msgGrid.popupBlockingModal();
					    	msgGrid.setForeColor(ConstantesJanela.BLUE);
					    	if(msgGrid.getPressedButtonIndex() == 0){						    	
					    		gridHa2.setCellText(gridHa2.getUserSelectedCellID(),"S");
					    		repaint();
					    	}else if(msgGrid.getPressedButtonIndex() == 1){						    		  
					    		gridHa2.setCellText(gridHa2.getUserSelectedCellID(),"N");						    		   
					    	}
						    break;					    
				    	case 2:									
					    	msgGrid = new MessageBox("Atenção",pergunta[gridHa2.getUserSelectedRow()-1], new String[]{"Sim","Não","Canc"});			    	
					    	msgGrid.popupBlockingModal();
					    	msgGrid.setForeColor(ConstantesJanela.BLUE);
					    	if(msgGrid.getPressedButtonIndex() == 0){						    	
					    		gridHa2.setCellText(gridHa2.getUserSelectedCellID(),"S");
					    		repaint();
					    	}else if(msgGrid.getPressedButtonIndex() == 1){						    		  
					    		gridHa2.setCellText(gridHa2.getUserSelectedCellID(),"N");						    		   
					    	}
						    break;					
						case 3:		
							 id = new InputDialog("Atenção", pergunta[gridHa2.getUserSelectedRow()-1],
				    	    		  		gridHa2.getCellText(gridHa2.getUserSelectedCellID()),new String[]{"Ok","Cancel"});
							id.setForeColor(ConstantesJanela.BLUE);
							id.popupBlockingModal();
							if (id.getPressedButtonIndex() == 0){
								gridHa2.setCellText(gridHa2.getUserSelectedCellID(),id.getValue());
				    	      }		
							break;
						case 4:								
							calendar=new Calendar();
				    		calendar.setSelectedDate(new Date(gridHa2.getCellText(gridHa2.getUserSelectedCellID())));
				    		calendar.popupBlockingModal();
				    		calendar.popupBlockingModal();
				    		/*popupModal(calendar);
				    		unpop();
				    		popupBlockingModal(calendar);*/
				    		gridHa2.setCellText(gridHa2.getUserSelectedCellID(),calendar.getSelectedDate()==null?"":calendar.getSelectedDate().toString());
							 /*id = new InputDialog("Atenção", pergunta[gridHa2.getUserSelectedRow()-1],
		    		  		 gridHa2.getCellText(gridHa2.getUserSelectedCellID()),new String[]{"Ok","Cancel"});
							 id.setForeColor(ConstantesJanela.BLUE);
							 id.popupBlockingModal();
							 if (id.getPressedButtonIndex() == 0){
								gridHa2.setCellText(gridHa2.getUserSelectedCellID(),id.getValue());
				    	      }	*/	
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
		  String[]pergunta={"Preencha o Campo|Toma Medicação","Preencha o Campo|Faz Exercícios Físicos",
				  			"Preencha o Campo|Pressão Arterial","Preencha o Campo|Dt Última Consulta"};
		 
		  MessageBox msgGrid;
		    if(edtObservacao.getText().length()>100){
	    		msgGrid = new MessageBox("Atenção","O Campo Observação Ultrapassou|a Quantidade Máxima de|100 Caracteres", new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
	    	}   
		    int mes = SIABBusiness.getMes();
		    if("".equals(gridHa2.getCellText(1,mes))){
		    	msgGrid = new MessageBox("Atenção",pergunta[0], new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
		    }
		    if("".equals(gridHa2.getCellText(2,mes))){
		    	msgGrid = new MessageBox("Atenção",pergunta[1], new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
		    }
		    if("".equals(gridHa2.getCellText(3,mes))){
		    	msgGrid = new MessageBox("Atenção",pergunta[2], new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
		    }
		    if("".equals(gridHa2.getCellText(4,mes))){
		    	msgGrid = new MessageBox("Atenção",pergunta[3], new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
		    }		    
			return true;
	  }
}
