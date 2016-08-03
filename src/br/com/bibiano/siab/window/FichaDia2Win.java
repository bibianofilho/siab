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
import br.com.bibiano.siab.business.FichaDiaNgc;
import br.com.bibiano.siab.business.SIABBusiness;
import br.com.bibiano.siab.util.BBDate;
/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class FichaDia2Win extends Window {
	private static FichaDia2Win fichaDia2Win; 
	
	private Label lblObservação;
	private MultiEdit edtObservacao;
	private Grid gridDia2;
	private Button btnPrev;
	private Button btnNext;

	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static FichaDia2Win criarInstancia()
	{
		if(fichaDia2Win == null)
		{
			fichaDia2Win = new FichaDia2Win();
		}
		return fichaDia2Win;
	}
	
	public FichaDia2Win(){
		super("FICHAB - DIA2",TAB_ONLY_BORDER);  
		makeUnmovable();

		add(gridDia2 = new BBGrid());
		gridDia2.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, TOP + 1*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 67*(int) ConstantesJanela.FATOR_Y);
		gridDia2.setStorage(new GridStorageSingleHashtable());
		gridDia2.setAllowSortRows(false);
		gridDia2.setAllowSortCols(false);
		gridDia2.setAllowUserResizeRows(false);
		gridDia2.setVerticalScrollVisible(false);
		gridDia2.setHighlightSelectedCell(true);
		gridDia2.setRowCount(4);
		gridDia2.setColCount(13);
		gridDia2.setFixedRowCount(1);
		gridDia2.setFixedColCount(1);
		gridDia2.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);
		gridDia2.setColSeparatorWidth(1);
		gridDia2.setRowSeparatorHeight(1);
		gridDia2.setDrawColSeparators(true);
		gridDia2.setDrawRowSeparators(true);
		gridDia2.setUpdating(false);
		gridDia2.setFullRowSelectionDisplay(true);
		gridDia2.setColWidth(0,60*(int)ConstantesJanela.FATOR_X);
		gridDia2.setColWidth(1,42*(int)ConstantesJanela.FATOR_X);
		gridDia2.setColWidth(2,42*(int)ConstantesJanela.FATOR_X);
		gridDia2.setColWidth(3,42*(int)ConstantesJanela.FATOR_X);
		gridDia2.setColWidth(4,42*(int)ConstantesJanela.FATOR_X);
		gridDia2.setColWidth(5,42*(int)ConstantesJanela.FATOR_X);
		gridDia2.setColWidth(6,42*(int)ConstantesJanela.FATOR_X);
		gridDia2.setColWidth(7,42*(int)ConstantesJanela.FATOR_X);
		gridDia2.setColWidth(8,42*(int)ConstantesJanela.FATOR_X);
		gridDia2.setColWidth(9,42*(int)ConstantesJanela.FATOR_X);
		gridDia2.setColWidth(10,42*(int)ConstantesJanela.FATOR_X);
		gridDia2.setColWidth(11,42*(int)ConstantesJanela.FATOR_X);
		gridDia2.setColWidth(12,42*(int)ConstantesJanela.FATOR_X);
		gridDia2.setColWidth(13,42*(int)ConstantesJanela.FATOR_X);
		String [] tituloGrid = {"","JAN","FEV","MAR","ABR","MAI","JUN","JUL","AGO","SET","OUT","NOV","DEZ"};
		gridDia2.setRowStrings(0,tituloGrid);
		String[]Coluna1={"","Usa Insulina","Toma Hopoglicemiante Oral","Dt Última Consulta"};
		gridDia2.setColStrings(0,Coluna1);
		
		add(lblObservação= new Label("Obervação:"));
		lblObservação.setRect(LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+2*(int)ConstantesJanela.FATOR_Y,100*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		add(edtObservacao = new MultiEdit(1, 3));
		edtObservacao.setRect(LEFT +3*(int) ConstantesJanela.FATOR_X, AFTER+1*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 42*(int) ConstantesJanela.FATOR_Y);
				
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
		     gridDia2.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridDia2.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		}		
	}
	
	public void PopulaGrid(){
    	try{
    		if(ListaFichaDiaWin.cmd.equals("U")){  
    			FichaDiaNgc fichaDiaNgc = FichaDiaNgc.criarInstancia();
    	    	ResultSet rs = fichaDiaNgc.getFichaDia(FichaDia1Win.cdFamilia,FichaDia1Win.cdPaciente);
    			ResultSet rsAcomp = fichaDiaNgc.GetFichaDiaAcomp(FichaDia1Win.cdFamilia,FichaDia1Win.cdPaciente);     			
    			if(rs.next()){    				
    				edtObservacao.setText(rs.getString("DS_OBSERVACAO"));				
    			} 
    			String [] arrayMes = {"","JAN","FEV","MAR","ABR","MAI","JUN","JUL","AGO","SET","OUT","NOV","DEZ"};
    			String items[]  = new String[4];
    			while(rsAcomp.next()){
    	    		int mes = rsAcomp.getInt("CD_MES");
    	    		items[0]=arrayMes[mes];
    	    		items[1] = rsAcomp.getString("FL_INSULINA");
    	    		items[2] = rsAcomp.getString("FL_HIPOGLICEMIANTE");
    	    		items[3] = rsAcomp.getString("DT_ULTCONSULTA");
    	    		gridDia2.setColStrings(mes,items);
    	    	}
    		} 
    	}catch (Exception e) {
    		MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+e.getMessage());
			msgBoxErro.popupBlockingModal();
			Vm.debug("Erro: "+e.getMessage());
		}
	}

    public void onEvent(Event e) {	
    	try{
		    if (e.type == ControlEvent.PRESSED) { 
			    if (e.target == btnNext) {
			    	if(ValidaForm()){
			    		MessageBox msgBoxFinaliza = new MessageBox("Fim", "Deseja Finalizar a Ficha DIA?",new String[]{"Sim","Não"});
				    	msgBoxFinaliza.popupBlockingModal();			    	
				    	if(msgBoxFinaliza.getPressedButtonIndex()==0){
				    		FichaDiaNgc fichaDiaNgc=FichaDiaNgc.criarInstancia();
				    		Hashtable voFichaDia = new Hashtable(10);
				    		AgenteNgc agenteNgc = AgenteNgc.criarInstancia();
				    		ResultSet rs = agenteNgc.getSegAreaMicroa();
				    		if(rs.next()){
				    			voFichaDia.put("CD_SEGMENTO",rs.getString("CD_SEG"));
				    			voFichaDia.put("CD_AREA",rs.getString("CD_AREA"));
					    		voFichaDia.put("CD_MICROAREA",rs.getString("CD_MICROA"));
				    		}	
				    		voFichaDia.put("NR_FAMILIA",Convert.toString(FichaDia1Win.cdFamilia));
				    		voFichaDia.put("CD_PACIENTE",Convert.toString(FichaDia1Win.cdPaciente));	
				    		voFichaDia.put("DS_OBSERVACAO",edtObservacao.getText()); 
				    		voFichaDia.put("DT_VISITA",FichaDia1Win.dtVisita);
				    		voFichaDia.put("FL_DIETA",FichaDia1Win.flDieta);
				    		voFichaDia.put("FL_EXERCICIO",FichaDia1Win.flExercicio);
				    		int mes = SIABBusiness.getMes();
				    		voFichaDia.put("CD_MES",""+SIABBusiness.getMes());
				    		voFichaDia.put("CD_ANO",""+SIABBusiness.getAno());
				    		voFichaDia.put("FL_INSULINA",gridDia2.getCellText(1,mes));
				    		voFichaDia.put("FL_HIPOGLICEMIANTE",gridDia2.getCellText(2,mes));
				    		voFichaDia.put("DT_ULTCONSULTA",gridDia2.getCellText(3,mes));				    		
				    		if("U".equals(ListaFichaDiaWin.cmd)){
				    			fichaDiaNgc.UpdateFichaDia(voFichaDia);
				    		}else{
				    			fichaDiaNgc.InserteFichaDia(voFichaDia);
				    		}
				    		ListaFichaDiaWin.cmd="F";
  						    unpop();
				    	}
			    	}
			    }else if (e.target == btnPrev){
			    	unpop();
			    }
		    }else if(e.type == GridEvent.CELL_DOUBLE_CLICKED){
		    	if(e.target == gridDia2){
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
		   InputDialog id =null;
		   String[] pergunta = {"Usa Insulina","Toma Hopoglicemiante Oral","Dt Última Consulta"};
		    if(gridDia2.getUserSelectedCol()>0){
		    	if(gridDia2.getUserSelectedCol()==(new BBDate()).getMonth()){
				    	switch (gridDia2.getUserSelectedRow()) {
						case 1:	
							msgGrid = new MessageBox("Atenção",pergunta[gridDia2.getUserSelectedRow()-1], new String[]{"Sim","Não","Canc"});			    	
					    	msgGrid.popupBlockingModal();
					    	msgGrid.setForeColor(ConstantesJanela.BLUE);
					    	if(msgGrid.getPressedButtonIndex() == 0){						    	
					    		gridDia2.setCellText(gridDia2.getUserSelectedCellID(),"S");
					    		repaint();
					    	}else if(msgGrid.getPressedButtonIndex() == 1){						    		  
					    		gridDia2.setCellText(gridDia2.getUserSelectedCellID(),"N");						    		   
					    	}
						    break;	 
							 
						case 2:									
							msgGrid = new MessageBox("Atenção",pergunta[gridDia2.getUserSelectedRow()-1], new String[]{"Sim","Não","Canc"});			    	
					    	msgGrid.popupBlockingModal();
					    	msgGrid.setForeColor(ConstantesJanela.BLUE);
					    	if(msgGrid.getPressedButtonIndex() == 0){						    	
					    		gridDia2.setCellText(gridDia2.getUserSelectedCellID(),"S");
					    		repaint();
					    	}else if(msgGrid.getPressedButtonIndex() == 1){						    		  
					    		gridDia2.setCellText(gridDia2.getUserSelectedCellID(),"N");						    		   
					    	}
						    break;	  
						case 3:		
							Calendar calendar=new Calendar();
				    		calendar.setSelectedDate(new Date(gridDia2.getCellText(gridDia2.getUserSelectedCellID())));
				    		calendar.popupBlockingModal();
				    		calendar.popupBlockingModal();
				    		/*popupModal(calendar);
				    		unpop();
				    		popupBlockingModal(calendar);*/
				    		gridDia2.setCellText(gridDia2.getUserSelectedCellID(),calendar.getSelectedDate()==null?"":calendar.getSelectedDate().toString());
							/*id = new InputDialog("Atenção",
    	    		  		pergunta[gridDia2.getUserSelectedRow()-1],
    	    		  		gridDia2.getCellText(gridDia2.getUserSelectedCellID()),new String[]{"Ok","Cancel"});
							id.setForeColor(ConstantesJanela.BLUE);
							id.popupBlockingModal();
							if (id.getPressedButtonIndex() == 0){
								gridDia2.setCellText(gridDia2.getUserSelectedCellID(),id.getValue());
				    	      }		*/
							break;   
						}	
		    	}else{
		    		msgGrid = new MessageBox("Atenção","Somente é Permitido Alterar| Dados do Mês Atual", new String[]{"OK"});			    	
			    	msgGrid.popupBlockingModal();
			    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	}   
	       }
	  }	 
    
    public void LimpaForm(){
    	edtObservacao.setText("");
		String[]Linha1={"Usa Insulina","","","","","","","","","","","",""};
		String[]Linha2={"Toma Hopoglicemiante Oral","","","","","","","","","","","",""};
		String[]Linha3={"Dt Última Consulta","","","","","","","","","","","",""};
		gridDia2.setRowStrings(1,Linha1);
		gridDia2.setRowStrings(2,Linha2);
		gridDia2.setRowStrings(3,Linha3);
	}
    
    private boolean ValidaForm(){
		  String[]pergunta={"Preencha o Campo|Usa Insulina","Preencha o Campo|Toma Hopoglicemiante Oral",
				  			"Preencha o Campo|Dt Última Consulta"};		 
		  MessageBox msgGrid;
		    if(edtObservacao.getText().length()>100){
	    		msgGrid = new MessageBox("Atenção","O Campo Observação Ultrapassou|a Quantidade Máxima de|100 Caracteres", new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
	    	}   
		    int mes = SIABBusiness.getMes();
		    if("".equals(gridDia2.getCellText(1,mes))){
		    	msgGrid = new MessageBox("Atenção",pergunta[0], new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
		    }
		    if("".equals(gridDia2.getCellText(2,mes))){
		    	msgGrid = new MessageBox("Atenção",pergunta[1], new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
		    }
		    if("".equals(gridDia2.getCellText(3,mes))){
		    	msgGrid = new MessageBox("Atenção",pergunta[2], new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
		    } 
			return true;
	  }
}
