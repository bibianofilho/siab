package br.com.bibiano.siab.window;



import litebase.ResultSet;
import waba.fx.Color;
import waba.sys.Convert;
import waba.sys.Settings;
import waba.sys.Vm;
import waba.ui.Button;
import waba.ui.ControlEvent;
import waba.ui.Event;
import waba.ui.Label;
import waba.ui.MessageBox;
import waba.ui.Window;
import waba.util.Hashtable;
import wextlib.ui.grid.Grid;
import wextlib.ui.grid.GridCell;
import wextlib.ui.grid.GridCellCheckBox;
import wextlib.ui.grid.GridCellID;
import wextlib.ui.grid.GridCellText;
import wextlib.ui.grid.GridStorageSingleHashtable;
import br.com.bibiano.siab.business.AgenteNgc;
import br.com.bibiano.siab.business.FichaANgc;
import br.com.bibiano.siab.util.BBDate;
/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class Paciente2Win extends Window {
	private static Paciente2Win paciente2Win;
	
	private Label lblDoencas;
	private Grid gridDoencas;

		
	private Button btnPrev;
	private Button btnNext;
	
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static Paciente2Win criarInstancia()
	{
		if(paciente2Win == null)
		{
			paciente2Win = new Paciente2Win();
		}
		return paciente2Win;
	}
	
	public Paciente2Win(){
		
		super("PACIENTE 2",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(lblDoencas = new Label("Doença ou condição referida:"),LEFT + 3*(int) ConstantesJanela.FATOR_X,TOP  + 1*(int) ConstantesJanela.FATOR_Y);
		add(gridDoencas = new Grid());
		gridDoencas.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 1*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 115*(int) ConstantesJanela.FATOR_Y);
		gridDoencas.setStorage(new GridStorageSingleHashtable());
		gridDoencas.setAllowSortRows(false);
		gridDoencas.setAllowSortCols(false);
		gridDoencas.setHighlightSelectedCell(true);
		gridDoencas.setRowCount(10);
		gridDoencas.setColCount(2);		
		gridDoencas.setAlternatedRowColorsEx(true, 
				new Color(0, 0, 0), new Color(255, 255, 255), 
				new Color(0, 0, 0), ConstantesJanela.BLUEGRID);
		gridDoencas.setColSeparatorWidth(1);
		gridDoencas.setRowSeparatorHeight(1);
		gridDoencas.setDrawColSeparators(true);
		gridDoencas.setDrawRowSeparators(true);
		gridDoencas.setUpdating(false);
		gridDoencas.setFullRowSelectionDisplay(false);
		gridDoencas.lockColEditing(1);		
		gridDoencas.setColCellType(0, new GridCellCheckBox().getClass());
		gridDoencas.setColCellType(1, new GridCellText().getClass());		
		gridDoencas.setColWidth(0, 15*(int)ConstantesJanela.FATOR_X);
		gridDoencas.setColWidth(1, 136*(int)ConstantesJanela.FATOR_X);
		gridDoencas.setCellText(0,1,"ALCOOLISMO");
		gridDoencas.setCellText(1,1,"CHAGAS");
		gridDoencas.setCellText(2,1,"DEFICIÊNCIA");
		gridDoencas.setCellText(3,1,"DIABETES");
		gridDoencas.setCellText(4,1,"EPLEPSIA");
		gridDoencas.setCellText(5,1,"GESTAÇÃO");
		gridDoencas.setCellText(6,1,"HIPERTENSÃO ARTERIAL");
		gridDoencas.setCellText(7,1,"TUBERCULOSE");
		gridDoencas.setCellText(8,1,"HANSENÍASE");
		gridDoencas.setCellText(9,1,"MALÁRIA");
		gridDoencas.setHorizontalScrollVisible(false);
		gridDoencas.setVerticalScrollVisible(true);
		
		add(btnPrev = new Button("<<"));
		btnPrev.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);		
		add(btnNext = new Button("OK"));
		btnNext.setRect(RIGHT - 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 35*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		if(Settings.isColor)
		{	//Setando as cores se o dispositivos suportar cores.
	    	Color BLUE =ConstantesJanela.BLUE;
		     setForeColor(BLUE);
		     lblDoencas.setForeColor(BLUE);		    
		     btnNext.setForeColor(BLUE);
		     btnPrev.setForeColor(BLUE);
		     gridDoencas.setSelectedCellForeColor(new Color(0, 0, 0));
		     gridDoencas.setSelectedCellBackColor(ConstantesJanela.ORANGE);
		    
		}
		
	}
	protected void PopulaForm() {
		GridCellID cellID;
		GridCell cell;	
		
		FichaANgc fichaANgc =  FichaANgc.criarInstancia();
		ResultSet rsPaciente = fichaANgc.GetPaciente(PacienteWin.cdFamilia,PacienteWin.cdPaciente);
		if(rsPaciente.next()){
			if("S".equalsIgnoreCase(rsPaciente.getString("FL_ALC"))){
				cellID = new GridCellID(0, 0);
				cell = new GridCellCheckBox();
				((GridCellCheckBox) cell).setChecked(true);
				gridDoencas.setCell(cellID, cell);	
			}
			if("S".equalsIgnoreCase(rsPaciente.getString("FL_CHA"))){
				cellID = new GridCellID(1, 0);
				cell = new GridCellCheckBox();
				((GridCellCheckBox) cell).setChecked(true);
				gridDoencas.setCell(cellID, cell);	
			}
			if("S".equalsIgnoreCase(rsPaciente.getString("FL_DEF"))){
				cellID = new GridCellID(2, 0);
				cell = new GridCellCheckBox();
				((GridCellCheckBox) cell).setChecked(true);
				gridDoencas.setCell(cellID, cell);	
			}
			if("S".equalsIgnoreCase(rsPaciente.getString("FL_DIA"))){
				cellID = new GridCellID(3, 0);
				cell = new GridCellCheckBox();
				((GridCellCheckBox) cell).setChecked(true);
				gridDoencas.setCell(cellID, cell);	
			}
			if("S".equals(rsPaciente.getString("FL_EPI"))){
				cellID = new GridCellID(4, 0);
				cell = new GridCellCheckBox();
				((GridCellCheckBox) cell).setChecked(true);
				gridDoencas.setCell(cellID, cell);	
			}
			if("S".equalsIgnoreCase(rsPaciente.getString("FL_GES"))){
				cellID = new GridCellID(5, 0);
				cell = new GridCellCheckBox();
				((GridCellCheckBox) cell).setChecked(true);
				gridDoencas.setCell(cellID, cell);	
			}
			if("S".equalsIgnoreCase(rsPaciente.getString("FL_HA"))){
				cellID = new GridCellID(6, 0);
				cell = new GridCellCheckBox();
				((GridCellCheckBox) cell).setChecked(true);
				gridDoencas.setCell(cellID, cell);	
			}
			if("S".equalsIgnoreCase(rsPaciente.getString("FL_TBC"))){
				cellID = new GridCellID(7, 0);
				cell = new GridCellCheckBox();
				((GridCellCheckBox) cell).setChecked(true);
				gridDoencas.setCell(cellID, cell);	
			}
			if("S".equalsIgnoreCase(rsPaciente.getString("FL_HAN"))){
				cellID = new GridCellID(8, 0);
				cell = new GridCellCheckBox();
				((GridCellCheckBox) cell).setChecked(true);
				gridDoencas.setCell(cellID, cell);	
			}
			if("S".equalsIgnoreCase(rsPaciente.getString("FL_MAL"))){
				cellID = new GridCellID(9, 0);
				cell = new GridCellCheckBox();
				((GridCellCheckBox) cell).setChecked(true);
				gridDoencas.setCell(cellID, cell);	
			}
			
		}
		
		
	}
	
	protected void LimpaForm() {
		GridCellID cellID;
		GridCell cell;
		int i=0;
		for( i=0;i<10;i++){			
			cellID = new GridCellID(i, 0);
			cell = new GridCellCheckBox();
			((GridCellCheckBox) cell).setChecked(false);
			gridDoencas.setCell(cellID, cell);
		}
	}

    public void onEvent(Event e) {	
    	GridCellID cellID;
		GridCell cell;
    	try{
		    if (e.type == ControlEvent.PRESSED) { 
			   if(e.target==btnPrev){
				   unpop();
			   }else if(e.target==btnNext){
				   MessageBox msgBoxFinaliza = new MessageBox("Fim", "Deseja Finalizar a Ficha Paciente?",new String[]{"Sim","Não"});
			    	msgBoxFinaliza.popupBlockingModal();			    	
			    	if(msgBoxFinaliza.getPressedButtonIndex()==0){
			    		 FichaANgc fichaANgc = new FichaANgc();
			    		 Hashtable pacienteVo = new Hashtable(22);
			    		 AgenteNgc agenteNgc = AgenteNgc.criarInstancia();
				    		ResultSet rs = agenteNgc.getSegAreaMicroa();
				    		if(rs.next()){
				    			pacienteVo.put("CD_SEGMENTO",rs.getString("CD_SEG"));
				    			pacienteVo.put("CD_AREA",rs.getString("CD_AREA"));
					    		pacienteVo.put("CD_MICROAREA",rs.getString("CD_MICROA"));
				    		}	
				    	 
			    		 pacienteVo.put("NR_FAMILIA",""+PacienteWin.cdFamilia);
					     pacienteVo.put("CD_PACIENTE",""+PacienteWin.cdPaciente); 
					     pacienteVo.put("NM_PACIENTE", PacienteWin.nmPaciente.toUpperCase()); 
					     pacienteVo.put("DT_NASCIMENTO",""+PacienteWin.dtNascimento); 
					     pacienteVo.put("NUM_IDADE",""+PacienteWin.numIdade);
					     pacienteVo.put("FL_SEXO",""+PacienteWin.flSexo);	
					     pacienteVo.put("FL_MENORQUINZE",""+PacienteWin.flMenorQuinze);	
					     pacienteVo.put("FL_ALFABETIZADO",""+PacienteWin.flAlfabetizado);	
						 pacienteVo.put("FL_FREQESCOLA",""+PacienteWin.flFreqEscola);
					     pacienteVo.put("CD_OCUPACAO",""+PacienteWin.cdOcupacao);
					     
					     //int idade = BBDate.diffBetweenDates(BBDate.valueOf(""+pacienteVo.get("DT_NASCIMENTO")),new BBDate(),BBDate.SSK_DIFF_YEARS);
					     int idade = Convert.toInt("" + pacienteVo.get("NUM_IDADE"));
					     if(idade>=15){
					    	 pacienteVo.put("FL_MAIOR","1");
					     }else{
					    	 pacienteVo.put("FL_MAIOR","0");
					     }
					    	
					    for(int i=0;i<10;i++){
					    		 cellID = new GridCellID(i, 0);
						    	 cell = new GridCellCheckBox();
						    	 cell = gridDoencas.getCell(cellID);
						    	 
						    	switch (i) {
									case 0:
										if(((GridCellCheckBox) cell).getChecked()){
											pacienteVo.put("FL_ALC","S");
										}else{
											pacienteVo.put("FL_ALC","N");
										}
									break;
									case 1:
										if(((GridCellCheckBox) cell).getChecked()){
											pacienteVo.put("FL_CHA","S");
										}else{
											pacienteVo.put("FL_CHA","N");
										}
									break;
									case 2:
										if(((GridCellCheckBox) cell).getChecked()){
											pacienteVo.put("FL_DEF","S");
										}else{
											pacienteVo.put("FL_DEF","N");
										}
									break;
									case 3:
										if(((GridCellCheckBox) cell).getChecked()){
											pacienteVo.put("FL_DIA","S");
										}else{
											pacienteVo.put("FL_DIA","N");
										}
									break;
									case 4:
										if(((GridCellCheckBox) cell).getChecked()){
											pacienteVo.put("FL_EPI","S");
										}else{
											pacienteVo.put("FL_EPI","N");
										}
									break;
									case 5:
										if(((GridCellCheckBox) cell).getChecked()){
											pacienteVo.put("FL_GES","S");
										}else{
											pacienteVo.put("FL_GES","N");
										}
									break;
									case 6:
										if(((GridCellCheckBox) cell).getChecked()){
											pacienteVo.put("FL_HA","S");
										}else{
											pacienteVo.put("FL_HA","N");
										}
									break;
									case 7:
										if(((GridCellCheckBox) cell).getChecked()){
											pacienteVo.put("FL_TBC","S");
										}else{
											pacienteVo.put("FL_TBC","N");
										}
									break;
									case 8:
										if(((GridCellCheckBox) cell).getChecked()){
											pacienteVo.put("FL_HAN","S");
										}else{
											pacienteVo.put("FL_HAN","N");
										}
									break;
									case 9:
										if(((GridCellCheckBox) cell).getChecked()){
											pacienteVo.put("FL_MAL","S");
										}else{
											pacienteVo.put("FL_MAL","N");
										}
									break;	
								}
						    	pacienteVo.put("FL_DME","N");
					 	}  
					     if("I".equals(PacienteWin.cmd)){
					    		fichaANgc.InsertePaciente(pacienteVo);	
					    	}else if("U".equals(PacienteWin.cmd)){
					    		fichaANgc.UpdatePaciente(pacienteVo);
					    	} 
					     PacienteWin.cmd="F";
						 unpop();
			    	}
			   }
		    }
    	}catch (Exception error) {
			MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+error.getMessage());
			msgBoxErro.popupBlockingModal();
			Vm.debug("Erro: "+error.getMessage());
		}   
	}  

    
   
}
