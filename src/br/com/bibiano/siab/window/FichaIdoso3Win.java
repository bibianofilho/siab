package br.com.bibiano.siab.window;


import litebase.ResultSet;
import waba.fx.Color;
import waba.sys.Convert;
import waba.sys.Settings;
import waba.sys.Vm;
import waba.ui.Button;
import waba.ui.Calendar;
import waba.ui.ControlEvent;
import waba.ui.Event;
import waba.ui.InputDialog;
import waba.ui.MessageBox;
import waba.ui.Window;
import waba.util.Date;
import waba.util.Hashtable;
import wextlib.ui.grid.BBGrid;
import wextlib.ui.grid.Grid;
import wextlib.ui.grid.GridEvent;
import wextlib.ui.grid.GridStorageSingleHashtable;
import br.com.bibiano.siab.business.AgenteNgc;
import br.com.bibiano.siab.business.FichaIdosoNgc;
import br.com.bibiano.siab.business.SIABBusiness;
import br.com.bibiano.siab.util.BBDate;

/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class FichaIdoso3Win extends Window {
	private static FichaIdoso3Win fichaIdoso3Win;

	private Grid gridIdoso3;
	private Button btnPrev;
	private Button btnNext;
	private Calendar calendar;
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static FichaIdoso3Win criarInstancia()
	{
		if(fichaIdoso3Win == null)
		{
			fichaIdoso3Win = new FichaIdoso3Win();
		}
		return fichaIdoso3Win;
	}
	public FichaIdoso3Win(){
		super("FICHA - IDOSO3",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(gridIdoso3 = new BBGrid());		
		gridIdoso3.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, TOP + 3*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 67*(int) ConstantesJanela.FATOR_Y);
		gridIdoso3.setStorage(new GridStorageSingleHashtable());
		gridIdoso3.setAllowSortRows(false);
		gridIdoso3.setAllowSortCols(false);
		gridIdoso3.setAllowUserResizeRows(false);
		gridIdoso3.setVerticalScrollVisible(false);
		gridIdoso3.setHighlightSelectedCell(true);
		gridIdoso3.setRowCount(4);
		gridIdoso3.setColCount(13);
		gridIdoso3.setFixedRowCount(1);
		gridIdoso3.setFixedColCount(1);		
		gridIdoso3.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);
		gridIdoso3.setColSeparatorWidth(1);
		gridIdoso3.setRowSeparatorHeight(1);
		gridIdoso3.setDrawColSeparators(true);
		gridIdoso3.setDrawRowSeparators(true);
		gridIdoso3.setUpdating(false);
		gridIdoso3.setFullRowSelectionDisplay(true);		
		gridIdoso3.setColWidth(0,60*(int)ConstantesJanela.FATOR_X);
		gridIdoso3.setColWidth(1,42*(int)ConstantesJanela.FATOR_X);
		gridIdoso3.setColWidth(2,42*(int)ConstantesJanela.FATOR_X);
		gridIdoso3.setColWidth(3,42*(int)ConstantesJanela.FATOR_X);
		gridIdoso3.setColWidth(4,42*(int)ConstantesJanela.FATOR_X);
		gridIdoso3.setColWidth(5,42*(int)ConstantesJanela.FATOR_X);
		gridIdoso3.setColWidth(6,42*(int)ConstantesJanela.FATOR_X);
		gridIdoso3.setColWidth(7,42*(int)ConstantesJanela.FATOR_X);
		gridIdoso3.setColWidth(8,42*(int)ConstantesJanela.FATOR_X);
		gridIdoso3.setColWidth(9,42*(int)ConstantesJanela.FATOR_X);
		gridIdoso3.setColWidth(10,42*(int)ConstantesJanela.FATOR_X);
		gridIdoso3.setColWidth(11,42*(int)ConstantesJanela.FATOR_X);
		gridIdoso3.setColWidth(12,42*(int)ConstantesJanela.FATOR_X);
		gridIdoso3.setColWidth(13,42*(int)ConstantesJanela.FATOR_X);		
		String [] tituloGrid = {"","JAN","FEV","MAR","ABR","MAI","JUN","JUL","AGO","SET","OUT","NOV","DEZ"};
		gridIdoso3.setRowStrings(0,tituloGrid);
		String[]Coluna1={"","Estado Nutricional","Data Visita ACS","Data Acomp PSF"};
		gridIdoso3.setColStrings(0,Coluna1);
		
		
		
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
		     gridIdoso3.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridIdoso3.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		}		
	}
	
    public void PopulaForm(){
    	try{
    		if("U".equals(ListaFichaIdosoWin.cmd)){  
    			FichaIdosoNgc fichaIdosoNgc = FichaIdosoNgc.criarInstancia();    	    	
    	    	ResultSet rsAcomp = fichaIdosoNgc.GetFichaIdosoAcomp(FichaIdoso1Win.cdFamilia,FichaIdoso1Win.cdPaciente);
    			String [] arrayMes = {"","JAN","FEV","MAR","ABR","MAI","JUN","JUL","AGO","SET","OUT","NOV","DEZ"};
    			String items[]  = new String[4];
    			while(rsAcomp.next()){
    	    		int mes = rsAcomp.getInt("CD_MES");
    	    		items[0]=arrayMes[mes];
    	    		items[1] = rsAcomp.getString("FL_ESTADONUTRI");
    	    		items[2] = rsAcomp.getString("DT_VISITA");
    	    		items[3] = rsAcomp.getString("DT_ACOMPPSF");    	    		
    	    		gridIdoso3.setColStrings(mes,items);
    	    	}
    		} 
    	}catch (Exception e) {
    		MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+e.getMessage());
			msgBoxErro.popupBlockingModal();
			Vm.debug("Erro: "+e.getMessage());
		}
    }
    
    public void LimpaForm(){    	    	
		String[]Linha1={"Estado Nutricional","","","","","","","","","","","",""};
		String[]Linha2={"Data Visita ACS","","","","","","","","","","","",""};
		String[]Linha3={"Data Acomp PSF","","","","","","","","","","","",""};		
		gridIdoso3.setRowStrings(1,Linha1);
		gridIdoso3.setRowStrings(2,Linha2);
		gridIdoso3.setRowStrings(3,Linha3);
	}

    public void onEvent(Event e) {	
    	try{
		    if (e.type == ControlEvent.PRESSED) { 
			    if (e.target == btnNext) {
			    	if(ValidaForm()){
			    		MessageBox msgBoxFinaliza = new MessageBox("Fim", "Deseja Finalizar a Ficha Idoso?",new String[]{"Sim","Não"});
				    	msgBoxFinaliza.popupBlockingModal();			    	
				    	if(msgBoxFinaliza.getPressedButtonIndex()==0){
				    		FichaIdosoNgc fichaIdosoNgc=FichaIdosoNgc.criarInstancia();
				    		Hashtable voFichaIdoso = new Hashtable(10);
				    		AgenteNgc agenteNgc = AgenteNgc.criarInstancia();
				    		ResultSet rs = agenteNgc.getSegAreaMicroa();
				    		if(rs.next()){
				    			voFichaIdoso.put("CD_SEGMENTO",rs.getString("CD_SEG"));
				    			voFichaIdoso.put("CD_AREA",rs.getString("CD_AREA"));
				    			voFichaIdoso.put("CD_MICROAREA",rs.getString("CD_MICROA"));
				    		}	
				    		voFichaIdoso.put("NR_FAMILIA",Convert.toString(FichaIdoso1Win.cdFamilia));
				    		voFichaIdoso.put("CD_PACIENTE",Convert.toString(FichaIdoso1Win.cdPaciente));	
				    		voFichaIdoso.put("FL_FUMANTE",FichaIdoso1Win.flFumante);
				    		voFichaIdoso.put("DT_DTP",FichaIdoso1Win.dtDtp);
				    		voFichaIdoso.put("DT_INFLUENZA",FichaIdoso1Win.dtInfluenza);
				    		voFichaIdoso.put("DT_PNEUMONOCOCOS",FichaIdoso1Win.dtPneumonococos);
				    		voFichaIdoso.put("FL_HIPERTENSAO",FichaIdoso1Win.flhipertensao);
				    		voFichaIdoso.put("FL_TUBERCULOSE",FichaIdoso1Win.flTuberculose);
				    		voFichaIdoso.put("FL_ALZHEIMER",FichaIdoso1Win.flAlzheimer);
				    		voFichaIdoso.put("FL_DEFFISICA",FichaIdoso1Win.flDefFisica);
				    		voFichaIdoso.put("FL_HANSENIASE",FichaIdoso1Win.flHanseniase);
				    		voFichaIdoso.put("FL_MALPARKSON",FichaIdoso1Win.flMalParkson);
				    		voFichaIdoso.put("FL_CANCER",FichaIdoso1Win.flCancer);
				    		voFichaIdoso.put("FL_ACAMADO",FichaIdoso1Win.flAcamado);
				    		voFichaIdoso.put("FL_INTERNAMENTOS",FichaIdoso1Win.flInternamentos);
				    		voFichaIdoso.put("FL_SEQUELASAVC",FichaIdoso1Win.flSequelasAvc);
				    		voFichaIdoso.put("FL_ALCOLATRA",FichaIdoso1Win.flAlcoolatra);
				    		int mes = SIABBusiness.getMes();
				    		voFichaIdoso.put("CD_MES",""+SIABBusiness.getMes());
				    		voFichaIdoso.put("CD_ANO",""+SIABBusiness.getAno());
				    		voFichaIdoso.put("FL_ESTADONUTRI",gridIdoso3.getCellText(1,mes));
				    		voFichaIdoso.put("DT_VISITA",gridIdoso3.getCellText(2,mes));
				    		voFichaIdoso.put("DT_ACOMPPSF",gridIdoso3.getCellText(3,mes));				    						    		
				    		if("U".equals(ListaFichaIdosoWin.cmd)){
				    			fichaIdosoNgc.UpdateFichaIdoso(voFichaIdoso);
				    		}else{
				    			fichaIdosoNgc.InserteFichaIdoso(voFichaIdoso);
				    		}
				    		ListaFichaIdosoWin.cmd="F";
  						    unpop();
				    	}
			    	}
			    }else if (e.target == btnPrev){
			    	unpop();
			    }
		    }else if(e.type == GridEvent.CELL_DOUBLE_CLICKED){
		    	if(e.target==gridIdoso3){
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
		 String[]pergunta={"Estado Nutricional","Data Visita ACS","Data Acomp PSF"};
		   
	     if(gridIdoso3.getUserSelectedCol()>0){	    	
		    	if(gridIdoso3.getUserSelectedCol()==(new BBDate()).getMonth()){
				    	switch (gridIdoso3.getUserSelectedRow()) {
				    	case 1:									
					    	msgGrid = new MessageBox("Atenção",pergunta[gridIdoso3.getUserSelectedRow()-1], new String[]{"Nutrido","Desnutrido","Canc"});			    	
					    	msgGrid.popupBlockingModal();
					    	msgGrid.setForeColor(ConstantesJanela.BLUE);
					    	if(msgGrid.getPressedButtonIndex() == 0){						    	
					    		gridIdoso3.setCellText(gridIdoso3.getUserSelectedCellID(),"N");
					    		repaint();
					    	}else if(msgGrid.getPressedButtonIndex() == 1){						    		  
					    		gridIdoso3.setCellText(gridIdoso3.getUserSelectedCellID(),"D");						    		   
					    	}
						    break;					    
				    	case 2:	
				    		calendar=new Calendar();
				    		calendar.setSelectedDate(new Date(gridIdoso3.getCellText(gridIdoso3.getUserSelectedCellID())));
				    		calendar.popupBlockingModal();
				    		calendar.popupBlockingModal();
				    		/*popupModal(calendar);
				    		unpop();
				    		popupBlockingModal(calendar);*/
				    		gridIdoso3.setCellText(gridIdoso3.getUserSelectedCellID(),calendar.getSelectedDate()==null?"":calendar.getSelectedDate().toString());
				    		/* id = new InputDialog("Atenção",
							  		pergunta[gridIdoso3.getUserSelectedRow()-1],
							  		gridIdoso3.getCellText(gridIdoso3.getUserSelectedCellID()),new String[]{"Ok","Cancel"});
							id.setForeColor(ConstantesJanela.BLUE);
							id.popupBlockingModal();
							if (id.getPressedButtonIndex() == 0){
								gridIdoso3.setCellText(gridIdoso3.getUserSelectedCellID(),id.getValue());
							  }	*/
							break;
						case 3:	
							calendar=new Calendar();
				    		calendar.setSelectedDate(new Date(gridIdoso3.getCellText(gridIdoso3.getUserSelectedCellID())));		    		
				    		/*popupModal(calendar);
				    		unpop();
				    		popupBlockingModal(calendar);*/
				    		calendar.popupBlockingModal();
				    		calendar.popupBlockingModal();
				    		gridIdoso3.setCellText(gridIdoso3.getUserSelectedCellID(),calendar.getSelectedDate()==null?"":calendar.getSelectedDate().toString());
							 /*id = new InputDialog("Atenção", pergunta[gridIdoso3.getUserSelectedRow()-1],
				    	    		  		gridIdoso3.getCellText(gridIdoso3.getUserSelectedCellID()),new String[]{"Ok","Cancel"});
							id.setForeColor(ConstantesJanela.BLUE);
							id.popupBlockingModal();
							if (id.getPressedButtonIndex() == 0){
								gridIdoso3.setCellText(gridIdoso3.getUserSelectedCellID(),id.getValue());
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
		  String[]pergunta={"Preencha o Campo|Estado Nutricional","Preencha o Campo|Data Visita ACS",
				  			"Preencha o Campo|Data Acomp PSF"};
		  MessageBox msgGrid;
		     
		    int mes = SIABBusiness.getMes();
		    if("".equals(gridIdoso3.getCellText(1,mes))){
		    	msgGrid = new MessageBox("Atenção",pergunta[0], new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
		    }
		    if("".equals(gridIdoso3.getCellText(2,mes))){
		    	msgGrid = new MessageBox("Atenção",pergunta[1], new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
		    }
		    if("".equals(gridIdoso3.getCellText(3,mes))){
		    	msgGrid = new MessageBox("Atenção",pergunta[2], new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
		    }		   		    
			return true;
	  }
}
