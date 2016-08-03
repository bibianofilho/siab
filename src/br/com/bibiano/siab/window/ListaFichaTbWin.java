package br.com.bibiano.siab.window;

import litebase.ResultSet;
import waba.fx.Color;
import waba.sys.Convert;
import waba.sys.Settings;
import waba.sys.Vm;
import waba.ui.Button;
import waba.ui.ControlEvent;
import waba.ui.Event;
import waba.ui.Grid;
import waba.ui.GridEvent;
import waba.ui.MessageBox;
import waba.ui.Window;
import br.com.bibiano.siab.business.FichaANgc;
import br.com.bibiano.siab.business.FichaTbNgc;
/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class ListaFichaTbWin extends Window {
	private static ListaFichaTbWin listaFichaTbWin;
	
	private Grid gridTb1;
	private Button btnPrev;
	private Button btnNew;
	private Button btnDel;
	
	
	public static String cmd;
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static ListaFichaTbWin criarInstancia()
	{
		if(listaFichaTbWin == null)
		{
			listaFichaTbWin = new ListaFichaTbWin();
		}
		return listaFichaTbWin;
	}
	
	public ListaFichaTbWin(){
		super("Lista Ficha TB",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		String [] tituloGrid = {"COD","NOME","ENDEREÇO"};		
		int gridWidths[] = {30*(int) ConstantesJanela.FATOR_X, 90*(int) ConstantesJanela.FATOR_X, 100*(int) ConstantesJanela.FATOR_X,0};
		int gridAligns[] = { LEFT,LEFT,LEFT,LEFT,LEFT,LEFT,LEFT,LEFT,LEFT,LEFT,LEFT,LEFT,LEFT,LEFT};
		add(gridTb1 = new Grid(tituloGrid,gridWidths,gridAligns,false));
		gridTb1.setRect(LEFT+3*(int)ConstantesJanela.FATOR_X, TOP+5*(int)ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X,125*(int) ConstantesJanela.FATOR_Y);
				
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
		     gridTb1.firstStripeColor = ConstantesJanela.BLUEGRID;
		     gridTb1.secondStripeColor = new Color(255,255,255);		     
		     gridTb1.verticalLineStyle = Grid.VERT_LINE;
		     btnNew.setForeColor(BLUE);
		     btnPrev.setForeColor(BLUE);
		     btnDel.setForeColor(BLUE);		     
		}
	}
	
    public void PopulaForm(){
    	try{
    		int numLinhas =gridTb1.size();	
    		String itemsLimpa[][]  = new String[numLinhas][4];
			for(int j=1;j<numLinhas;j++){
				itemsLimpa[j]=new String[]{"","","",""};				
			}
			gridTb1.setItems(itemsLimpa);;
    		
    		int i =0;
	    	ResultSet rs = FichaTbNgc.criarInstancia().ListaFichaAll();
	    	String items[][]  = new String[rs.getRowCount()][4];
	    	while(rs.next()){	    		
	    		items[i][0] = ""+rs.getInt("CD_PACIENTE");
	    		ResultSet rsFichaA = FichaANgc.criarInstancia().GetPaciente(rs.getInt("NR_FAMILIA"),rs.getInt("CD_PACIENTE"));
	    		ResultSet rsFamilia = FichaANgc.criarInstancia().GetFamiliaPaciente(rs.getInt("NR_FAMILIA"));
	    		if(rsFichaA.next()){
	    			items[i][1] =rsFichaA.getString("NM_PACIENTE");
	    		}	    
	    		if(rsFamilia.next()){
	    			items[i][2] = ""+rsFamilia.getString("DS_ENDERECO");
	    		}
	    		items[i][3] = ""+rs.getInt("NR_FAMILIA");
	    		i++;
	    	}
	    	gridTb1.setItems(items);	    	
    	} catch (Exception e) {
    		MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+e.getMessage());
			msgBoxErro.popupBlockingModal();
			Vm.debug("Erro: "+e.getMessage());
		}
    }

	   public void onEvent(Event e) {
		   try{
			    if (e.type == ControlEvent.PRESSED) { 
				    if (e.target == btnNew) {
				    	FichaTb1Win  fichaTb1 = FichaTb1Win.criarInstancia();
				    	this.cmd = "N";
				    	fichaTb1.PopulaGrid();
				    	fichaTb1.LimpaDados();
				    	fichaTb1.popupBlockingModal();
				    	PopulaForm();
				    }else if (e.target == btnPrev){
				    	unpop();
				    }else if(e.target==btnDel){
				    	long cdFamilia=Convert.toLong(gridTb1.getItem(gridTb1.getSelectedLine())[3]);
				    	long cdPaciente=Convert.toLong(gridTb1.getItem(gridTb1.getSelectedLine())[0]);
				    	FichaTbNgc fichaTbNgc = new FichaTbNgc();
				    	fichaTbNgc.DeleteFichaTb(cdFamilia,cdPaciente);
				    	PopulaForm();
				    }
			    }else if(e.type == GridEvent.SELECTED_EVENT){
			    	 if(e.target == gridTb1){
			    		 if(FichaTb1Win.DoubleClick()){
			    			 GridEvent ge = (GridEvent)e;
			    			 if((gridTb1.getItem(ge.row)!=null)){
					    	      FichaTb1Win  fichaTb1 = FichaTb1Win.criarInstancia();	
					    	      this.cmd = "U";
					    	      fichaTb1.cdFamilia=Convert.toInt(gridTb1.getItem(ge.row)[3]);
					    	      fichaTb1.cdPaciente =Convert.toInt(gridTb1.getItem(ge.row)[0]);
					    	      fichaTb1.PopulaUpdate();
					    	      fichaTb1.popupBlockingModal();
							      PopulaForm();
			    			 }
				    	 }
				    }
			    }
		 }catch (Exception erro) {
			   MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+erro.getMessage());
				msgBoxErro.popupBlockingModal();
				Vm.debug("Erro: "+erro.getMessage());
		}   	
	}  
}
