package br.com.bibiano.siab.window;

import superwaba.ext.xplat.ui.MultiEdit;
import br.com.bibiano.siab.business.FichaANgc;
import waba.fx.Color;
import waba.sys.Convert;
import waba.sys.Settings;
import waba.sys.Vm;
import waba.ui.Button;
import waba.ui.Check;
import waba.ui.ControlEvent;
import waba.ui.Edit;
import waba.ui.Event;
import waba.ui.Label;
import waba.ui.MessageBox;
import waba.ui.Window;
import waba.util.Hashtable;

/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class FichaA8Win extends Window {
	private static FichaA8Win fichaA8Win;
	
	private Label lblPossuiPlano;
	private Check chkPossuiPlan;	
	private Label lblNumPessoasPlano;
	private Edit edtNumPessoasPlano;
	private Label lblObservacao;
	private Label lblNomePlano;
	private Edit edtNomePlano;
	private MultiEdit edtObservacao;
	
	private Button btnPrev;
	private Button btnFinalizar;
	
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static FichaA8Win criarInstancia()
	{
		if(fichaA8Win == null)
		{
			fichaA8Win = new FichaA8Win();
		}
		return fichaA8Win;
	}
	
	public FichaA8Win(){
		
		super("Ficha A8",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		
		add(lblPossuiPlano = new Label("Alguém Possui Plano de saúde:"),LEFT + 3*(int) ConstantesJanela.FATOR_X,TOP  + 3*(int) ConstantesJanela.FATOR_Y);
		add(chkPossuiPlan = new Check("Sim"),AFTER + 3*(int) ConstantesJanela.FATOR_X, SAME);
		
		add(lblNumPessoasPlano = new Label("Número de Pessoas com Plano:"),LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 3*(int) ConstantesJanela.FATOR_Y);
		add(edtNumPessoasPlano = new Edit("99"),AFTER + 3*(int) ConstantesJanela.FATOR_X,SAME);		
		edtNumPessoasPlano.setMaxLength(2);
		edtNumPessoasPlano.setValidChars("1234567890");
		
		add(lblNomePlano = new Label("Nome do Plano de Saúde:"),LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 3*(int) ConstantesJanela.FATOR_Y);
		add(edtNomePlano = new Edit());
		edtNomePlano.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X,AFTER+ 3*(int) ConstantesJanela.FATOR_Y, FILL - 3*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);	
		
		add(lblObservacao = new Label("Observações:"),LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 3*(int) ConstantesJanela.FATOR_Y);
		add(edtObservacao = new MultiEdit(1, 3));
		edtObservacao.setRect(LEFT +3*(int) ConstantesJanela.FATOR_X, AFTER+3*(int) ConstantesJanela.FATOR_Y,FILL - 3*(int) ConstantesJanela.FATOR_X, 40*(int) ConstantesJanela.FATOR_Y);
		
		add(btnPrev = new Button("<<"));
		btnPrev.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		add(btnFinalizar = new Button("Finalizar"));
		btnFinalizar.setRect(RIGHT - 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 45*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		if(Settings.isColor)
		{	//Setando as cores se o dispositivos suportar cores.
	    	Color BLUE =ConstantesJanela.BLUE;
		     setForeColor(BLUE);
		     lblPossuiPlano.setForeColor(BLUE);
		     lblNomePlano.setForeColor(BLUE);
		     lblNumPessoasPlano.setForeColor(BLUE);
		     lblObservacao.setForeColor(BLUE);
		     lblNumPessoasPlano.setForeColor(BLUE);
		     btnFinalizar.setForeColor(BLUE);
		     btnPrev.setForeColor(BLUE);
		}
		
	}
	
	protected void PopulaForm(){
		if(FichaA2Win.flPossuiPlano.equals("S")){
			chkPossuiPlan.setChecked(true);
		}else{
			chkPossuiPlan.setChecked(false);
		}
		edtNumPessoasPlano.setText(""+FichaA2Win.numPesPlano);
		edtNomePlano.setText(FichaA2Win.nmPlano);
		edtObservacao.setText(FichaA2Win.dsObservacao);
	}
	protected void LimpaForm(){		
		chkPossuiPlan.setChecked(false);		
		edtNumPessoasPlano.setText("");
		edtNomePlano.setText("");
		edtObservacao.setText("");
	}

    public void onEvent(Event e) {
    	try{
		    if (e.type == ControlEvent.PRESSED) { 
			   if(e.target==btnPrev){
				   unpop();
			   }else if(e.target==btnFinalizar){
				   MessageBox msgBoxFinaliza = new MessageBox("Fim", "Deseja Finalizar a Ficha A?",new String[]{"Sim","Não"});
			    	msgBoxFinaliza.popupBlockingModal();			    	
			    	if(msgBoxFinaliza.getPressedButtonIndex()==0){
						   PreencheAtributo();
						   Hashtable voFichaA = new Hashtable(10);
						   voFichaA.put("NR_FAMILIA",""+FichaA2Win.cdFamilia);
						   voFichaA.put("DS_ENDERECO",FichaA2Win.dsEndereco);
						   voFichaA.put("NUM_ENDERECO",""+FichaA2Win.numEndereco);
						   voFichaA.put("DS_BAIRRO",FichaA2Win.dsBairro);				   
						   voFichaA.put("DS_CEP",""+FichaA2Win.dsCep);
						   voFichaA.put("CD_MUNICIPIO",""+FichaA2Win.cdMunicipio);
						   voFichaA.put("CD_SEGMENTO",""+FichaA2Win.cdSegmento);
						   voFichaA.put("CD_AREA",""+FichaA2Win.cdArea);
						   voFichaA.put("CD_MICROAREA",""+FichaA2Win.cdMicroarea);
						   voFichaA.put("DT_CADASTRO",FichaA2Win.dtCadastro);
						   voFichaA.put("CD_UF",""+FichaA2Win.cdUf);
						   voFichaA.put("CD_TPCASA",""+FichaA2Win.cdTpCasa);
						   voFichaA.put("DS_TPCASAESP",""+FichaA2Win.dsTpCasaEsp);
						   voFichaA.put("NUM_COMODOS",""+FichaA2Win.numComodos);
						   voFichaA.put("FL_ENERGIA",""+FichaA2Win.flEnergia);
						   voFichaA.put("CD_DESTLIXO",""+FichaA2Win.cdDestLixo);
						   voFichaA.put("CD_TRATAAGUA",""+FichaA2Win.cdTrataAgua);
						   voFichaA.put("CD_ABASTEAGUA",""+FichaA2Win.cdAbasteAgua);
						   voFichaA.put("CD_FEZESURINA",""+FichaA2Win.cdFezesUrina);
						   voFichaA.put("CD_DOENCAPROCU",""+FichaA2Win.cdDoencaProcura);
						   voFichaA.put("DS_DOENCAPROCUESP",""+FichaA2Win.dsDoencaProcuraEsp);
						   voFichaA.put("CD_GRUPOCOMU",""+FichaA2Win.cdGrupoComu);
						   voFichaA.put("DS_GRUPOCOMUESP",""+FichaA2Win.dsGrupoComuEsp);
						   voFichaA.put("CD_MEIOSCOMUNI",""+FichaA2Win.cdMeiosComuni);
						   voFichaA.put("DS_MEIOSCOMUNIESP",""+FichaA2Win.dsMeiosComuniEsp);
						   voFichaA.put("CD_MEIOSTRANS",""+FichaA2Win.cdMeiosTrans);
						   voFichaA.put("DS_MEIOSTRANSESP",""+FichaA2Win.dsMeiosTransEsp);
						   voFichaA.put("FL_POSSUIPLAN",""+FichaA2Win.flPossuiPlano);
						   voFichaA.put("NUM_PESPLAN",""+FichaA2Win.numPesPlano);
						   voFichaA.put("NM_PLANO",""+FichaA2Win.nmPlano);
						   voFichaA.put("DS_OBSERVACAO",""+FichaA2Win.dsObservacao);
						   FichaANgc fichaANgc =  FichaANgc.criarInstancia();
						   if(ListaFamiliaWin.cmd.equals("I")){
							   fichaANgc.InserteFichaA(voFichaA);   
						   }else if(ListaFamiliaWin.cmd.equals("U")){
							   fichaANgc.UpdateFichaA(voFichaA);
						   }
						   ListaFamiliaWin.cmd="F";
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
    
    private void PreencheAtributo(){   
    	 if(chkPossuiPlan.getChecked()){
    		 FichaA2Win.flPossuiPlano="S"; 
    	 }else{
    		 FichaA2Win.flPossuiPlano="N";
    	 } 
    	 FichaA2Win.numPesPlano= Convert.toInt(edtNumPessoasPlano.getText());
    	 FichaA2Win.nmPlano= edtNomePlano.getText();
    	 FichaA2Win.dsObservacao= edtObservacao.getText();
    }

}
