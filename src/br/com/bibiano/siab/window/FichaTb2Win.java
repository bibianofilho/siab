package br.com.bibiano.siab.window;


import litebase.ResultSet;
import superwaba.ext.xplat.ui.MultiEdit;
import waba.fx.Color;
import waba.sys.Convert;
import waba.sys.Settings;
import waba.sys.Vm;
import waba.ui.Button;
import waba.ui.ControlEvent;
import waba.ui.Edit;
import waba.ui.Event;
import waba.ui.Grid;
import waba.ui.GridEvent;
import waba.ui.InputDialog;
import waba.ui.Label;
import waba.ui.MessageBox;
import waba.ui.Window;
import waba.util.Hashtable;
import br.com.bibiano.siab.business.AgenteNgc;
import br.com.bibiano.siab.business.FichaTbNgc;
import br.com.bibiano.siab.business.SIABBusiness;
import br.com.bibiano.siab.util.BBDate;

/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class FichaTb2Win extends Window {
	
	private Label lblNComuni;
	private Label lblcomu5;
	private Label lblObservação;
	private Edit edtNComuni;
	private Edit edtcomu5;	
	private Grid gridTb2;
	private Button btnPrev;
	private Button btnNext;
	private MultiEdit edtObservacao;
	private static FichaTb2Win fichaTb2;
	
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static FichaTb2Win criarInstancia()
	{
		if(fichaTb2 == null)
		{
			fichaTb2 = new FichaTb2Win();
		}
		return fichaTb2;
	}
	public FichaTb2Win(){
		super("FICHAB - TB2",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		String [] tituloGrid = {"","JAN","FEV","MAR","ABR","MAI","JUN","JUL","AGO","SET","OUT","NOV","DEZ"};
		int ww = fm.getTextWidth("xxxxx");
		int gridWidths[] = {90*(int)ConstantesJanela.FATOR_X, ww, ww, ww, ww, ww, ww, ww, ww, ww, ww, ww,ww};
		int gridAligns[] = { LEFT,LEFT,LEFT,LEFT,LEFT,LEFT,LEFT,LEFT,LEFT,LEFT,LEFT,LEFT,LEFT};
		gridTb2 = new Grid(tituloGrid,gridWidths,gridAligns,false);
		add(gridTb2);
		gridTb2.setRect(LEFT+3*(int)ConstantesJanela.FATOR_X, TOP+5*(int)ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X,35*(int) ConstantesJanela.FATOR_Y);
		
		lblNComuni = new Label("Nºde Comunicantes:");
		add(lblNComuni);
		lblNComuni.setRect(LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+3*(int)ConstantesJanela.FATOR_Y,90*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		edtNComuni = new Edit();
		add(edtNComuni);
		edtNComuni.setRect(AFTER +2*(int) ConstantesJanela.FATOR_X, SAME-3*(int) ConstantesJanela.FATOR_Y, 20*(int) ConstantesJanela.FATOR_Y,15*(int) ConstantesJanela.FATOR_Y);
		edtNComuni.setMaxLength(2);
		edtNComuni.setValidChars("1234567890");
		
		lblcomu5 = new Label("Comunicantes < 5 anos:");
		add(lblcomu5);
		lblcomu5.setRect(LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+3*(int)ConstantesJanela.FATOR_Y,100*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		edtcomu5 = new Edit();
		add(edtcomu5);
		edtcomu5.setRect(AFTER +2*(int) ConstantesJanela.FATOR_X, SAME-3*(int) ConstantesJanela.FATOR_Y,20*(int) ConstantesJanela.FATOR_Y,15*(int) ConstantesJanela.FATOR_Y);
		edtcomu5.setMaxLength(2);
		edtcomu5.setValidChars("1234567890");
		
		lblObservação= new Label("Observação:");
		add(lblObservação);
		lblObservação.setRect(LEFT+3*(int)ConstantesJanela.FATOR_X, AFTER+3*(int)ConstantesJanela.FATOR_Y,100*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		edtObservacao = new MultiEdit(1, 3);
		add(edtObservacao);
		edtObservacao.setRect(LEFT +3*(int) ConstantesJanela.FATOR_X, AFTER+2*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 40*(int) ConstantesJanela.FATOR_Y);
		
		btnPrev = new Button("<<");
		add(btnPrev);
		btnPrev.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		btnNext = new Button("Finalizar");
		add(btnNext);
		btnNext.setRect(RIGHT - 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 50*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		
		
		if(Settings.isColor)
		{	//Setando as cores se o dispositivos suportar cores.
	    	Color BLUE =ConstantesJanela.BLUE;
		     setForeColor(BLUE);
		     lblNComuni.setForeColor(BLUE);
		     lblcomu5.setForeColor(BLUE);
		     gridTb2.firstStripeColor = ConstantesJanela.BLUEGRID;
		     gridTb2.secondStripeColor = new Color(255,255,255);	
		     gridTb2.verticalLineStyle = Grid.VERT_LINE;
		     btnNext.setForeColor(BLUE);
		     btnPrev.setForeColor(BLUE);
		     lblObservação.setForeColor(BLUE);
		}
		PopulaGrid();
	}
	
    private void PopulaGrid(){
    	try{
    		String items[][]  = new String[2][13];	   
    		if("U".equals(ListaFichaTbWin.cmd)){    			
    	    	ResultSet rs = FichaTbNgc.criarInstancia().ListaFichaTb(FichaTb1Win.cdFamilia,FichaTb1Win.cdPaciente);
    			ResultSet rsAcomp = FichaTbNgc.criarInstancia().ListaFichaTbAcomp(FichaTb1Win.cdFamilia,FichaTb1Win.cdPaciente); 
    			
    			if(rs.next()){
    				edtNComuni.setText(rs.getString("NUM_COMUNICANTES"));
    				edtcomu5.setText(rs.getString("NUM_COMUNICANTES5"));
    				edtObservacao.setText(rs.getString("DS_OBSERVACAO"));				
    			} 	
    	    	while(rsAcomp.next()){
    	    		int mes = rsAcomp.getInt("CD_MES");
    	    		items[0][mes] = rsAcomp.getString("NUM_EXAMINADOS");
    	    		items[1][mes] = rsAcomp.getString("NUM_COMBCG");
    	    	}
    		}   
	    	String[]Coluna0 = {"Comunicantes examinados","< 5 anos com BCG"};
	    	for (int i=0;i<2;i++){
	    		items[i][0] = Coluna0[i];	    
	    	}
	    	gridTb2.setItems(items);
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
				    	MessageBox msgBoxFinaliza = new MessageBox("Fim", "Deseja Finalizar a Ficha TB?",new String[]{"Sim","Não"});
				    	msgBoxFinaliza.popupBlockingModal();			    	
				    	if(msgBoxFinaliza.getPressedButtonIndex()==0){
				    		PreencheAtributo();
				    		FichaTbNgc fichaTbNgc =FichaTbNgc.criarInstancia();	
				    		Hashtable voFichaTb = new Hashtable(10);
				    		AgenteNgc agenteNgc = AgenteNgc.criarInstancia();
				    		ResultSet rs = agenteNgc.getSegAreaMicroa();
				    		if(rs.next()){
				    			voFichaTb.put("CD_SEGMENTO",rs.getString("CD_SEG"));
				    			voFichaTb.put("CD_AREA",rs.getString("CD_AREA"));
				    			voFichaTb.put("CD_MICROAREA",rs.getString("CD_MICROA"));
				    		}	
				    		voFichaTb.put("NR_FAMILIA",Convert.toString(FichaTb1Win.cdFamilia));
				    		voFichaTb.put("CD_PACIENTE",Convert.toString(FichaTb1Win.cdPaciente));				    		
				    		voFichaTb.put("NUM_COMUNICANTES",Convert.toString(FichaTb1Win.numComunicantes));
				    		voFichaTb.put("NUM_COMUNICANTES5",Convert.toString(FichaTb1Win.numComunicantes5));
				    		voFichaTb.put("DS_OBSERVACAO",FichaTb1Win.dsObservacao);
				    		voFichaTb.put("CD_MES",Convert.toString(SIABBusiness.getMes()));
				    		voFichaTb.put("CD_ANO",Convert.toString(SIABBusiness.getAno()));
				    		voFichaTb.put("DT_VISITA",FichaTb1Win.dtVisita);
				    		voFichaTb.put("FL_MEDICACAO",FichaTb1Win.flMedicacao);
				    		voFichaTb.put("FL_REAINDESE",FichaTb1Win.flReaIndese);
				    		voFichaTb.put("DT_CONSULTA",FichaTb1Win.dtConsulta);
				    		voFichaTb.put("FL_ESCARRO",FichaTb1Win.flEscarro);
				    		int mes = SIABBusiness.getMes();						
				    		voFichaTb.put("NUM_EXAMINADOS",gridTb2.getItem(0)[mes]);
				    		voFichaTb.put("NUM_COMBCG",gridTb2.getItem(1)[mes]);
				    		if("U".equals(ListaFichaTbWin.cmd)){
				    			fichaTbNgc.UpdateFichaTb(voFichaTb);
				    		}else{
				    			fichaTbNgc.InserteFichaTb(voFichaTb);
				    		}
				    		ListaFichaTbWin.cmd="F";
				    		unpop();			    		
				    	}
			    	}
			    }else if (e.target == btnPrev){
			    	unpop();
			    }
		    }else if(e.type == GridEvent.SELECTED_EVENT){
		    	if(e.target == gridTb2){
		    		 if(FichaTb1Win.DoubleClick()){
			    	      GridEvent ge = (GridEvent)e;
			    	      EntraDataGrid(ge);
			    	 }
		    	}	 
		    }
    	}catch (Exception error) {
			MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+error.getMessage());
			msgBoxErro.popupBlockingModal();
			Vm.debug("Erro: "+error.getMessage());
		}   
	}  
		    
	private void EntraDataGrid(GridEvent ge){
		
	   MessageBox msgGrid =null;
	   String[] pergunta = {"Comunicantes examinados","< 5 anos com BCG"};
	    if(ge.col>0){
	    	if(ge.col==(new BBDate()).getMonth()){
			    	String oldLine[] = gridTb2.getItem(ge.row);
			    	String newLine[] = gridTb2.getItem(ge.row);
			    	switch (ge.row) {
			    		case 0:
						case 1:					
								InputDialog id = new InputDialog("Atenção",
					    	    		  		pergunta[ge.row],
					    	    		         oldLine[ge.col],new String[]{"Ok","Cancel"});
								id.setForeColor(ConstantesJanela.BLUE);
								id.popupBlockingModal();								
								if (id.getPressedButtonIndex() == 0){		    	    	  
					    	    	  newLine[ge.col] = id.getValue();
					     	    	  gridTb2.replace(newLine,ge.row);
					    	    	  repaint();
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
    
	 private void PreencheAtributo(){
		 FichaTb1Win.numComunicantes = Convert.toInt(edtNComuni.getText());
		 FichaTb1Win.numComunicantes5 = Convert.toInt(edtcomu5.getText());
		 FichaTb1Win.dsObservacao = edtObservacao.getText();
	  }
	 
	 public void LimpaDados(){
		 edtcomu5.setText("");
		 edtNComuni.setText("");
		 edtObservacao.setText("");		 
		 String items[][]  = new String[2][13];	
		 String[]Coluna0 = {"Comunicantes examinados","< 5 anos com BCG"};
	    	for (int i=0;i<2;i++){
	    		items[i][0] = Coluna0[i];
	    		items[i][1] = "";
	    		items[i][2] = "";
	    		items[i][3] = ""; 		         
	    		items[i][4] = "";  		         
	    		items[i][5] = "";
	    		items[i][6] = "";
	    		items[i][7] = ""; 		         
	    		items[i][8] = "";
	    		items[i][9] = "";
	    		items[i][10] = "";
	    		items[i][11] = ""; 		         
	    		items[i][12] = "";
	    	}
	    	gridTb2.setItems(items);
	 }
	 
	 private boolean ValidaForm(){
		  MessageBox msgGrid;
		    int mes = SIABBusiness.getMes();
		    if(edtObservacao.getText().length()>100){
	    		msgGrid = new MessageBox("Atenção","O Campo Observação Ultrapassou|a Quantidade Máxima de|100 Caracteres", new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
	    	 }   
		    if("".equals(gridTb2.getItem(0)[mes])){
		    	msgGrid = new MessageBox("Atenção","Preencha o Campo| Comunicantes examinados", new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
		    }
		    if("".equals(gridTb2.getItem(1)[mes])){
		    	msgGrid = new MessageBox("Atenção","Preencha o Campo| < 5 anos com BCG", new String[]{"OK"});			    	
		    	msgGrid.popupBlockingModal();
		    	msgGrid.setForeColor(ConstantesJanela.BLUE);
		    	return false;
		    }
			return true;
	  }
}
