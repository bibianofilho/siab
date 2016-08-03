package br.com.bibiano.siab.window;

import litebase.ResultSet;
import br.com.bibiano.siab.business.AgenteNgc;
import br.com.bibiano.siab.business.FichaANgc;
import waba.fx.Color;
import waba.sys.Convert;
import waba.sys.Settings;
import waba.sys.Vm;
import waba.ui.Button;
import waba.ui.ControlEvent;
import waba.ui.Edit;
import waba.ui.Event;
import waba.ui.PenEvent;
import waba.ui.Label;
import waba.ui.MessageBox;
import waba.ui.Window;
/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class FichaA2Win extends Window {
	private static FichaA2Win fichaA2Win;
	
	private Label lblEndereco;
	private Edit edtEndereco;	
	private Label lblNumEndereco;
	private Edit edtNumEndereco;
	private Label lblBairro;
	private Edit edtBairro;
	private Label lblMunicipio;
	private Edit edtMunicipio;
	private Label lblUf;
	private Edit edtUf;
	private Label lblSegmento;
	private Edit edtSegmento;
	private Label lblMicroarea;
	private Edit edtMicroarea;
	private Label lblCep;
	private Edit edtCep;
	private Label lblData;
	private Edit edtData;
	private Label lblArea;
	private Edit edtArea;
	
	private Button btnPrev;
	private Button btnNext;
	
	/**
	 * Atributos FichaA*/
	protected static long cdFamilia;
	protected static String dsEndereco;
	protected static int numEndereco;
	protected static String dsBairro;
	protected static String dsCep;
	protected static int cdMunicipio;
	protected static int cdSegmento;
	protected static int cdArea;
	protected static int cdMicroarea;
	protected static String dtCadastro;
	protected static String cdUf;
	protected static int cdTpCasa;
	protected static String dsTpCasaEsp;
	protected static int numComodos;
	protected static String flEnergia;
	protected static int cdDestLixo;
	protected static int cdTrataAgua;
	protected static int cdAbasteAgua;
	protected static int cdFezesUrina;
	protected static int cdDoencaProcura;
	protected static String dsDoencaProcuraEsp;
	protected static int cdGrupoComu;
	protected static String dsGrupoComuEsp;
	protected static int cdMeiosComuni;
	protected static String dsMeiosComuniEsp;
	protected static int cdMeiosTrans;
	protected static String dsMeiosTransEsp;
	protected static String flPossuiPlano;
	protected static int numPesPlano;
	protected static String nmPlano;
	protected static String dsObservacao;
	
	/**
	 * Método que cria uma instância da classe, se já não tiver sido criada.	 
	 */
	public static FichaA2Win criarInstancia()
	{
		if(fichaA2Win == null)
		{
			fichaA2Win = new FichaA2Win();
		}
		return fichaA2Win;
	}
	
	public FichaA2Win(){		
		super("Ficha A2",TAB_ONLY_BORDER);  
		makeUnmovable();
		
		add(lblEndereco = new Label("Endereço:"),LEFT + 3*(int) ConstantesJanela.FATOR_X,TOP  + 3*(int) ConstantesJanela.FATOR_Y);
		add(edtEndereco = new Edit());
		edtEndereco.setRect(AFTER + 3*(int) ConstantesJanela.FATOR_X,SAME, FILL -3*(int) ConstantesJanela.FATOR_X,15*(int) ConstantesJanela.FATOR_Y);
		
		add(lblNumEndereco = new Label("Num:"),LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 3*(int) ConstantesJanela.FATOR_Y);
		add(edtNumEndereco = new Edit("99999"),AFTER + 3*(int) ConstantesJanela.FATOR_X, SAME-(int) ConstantesJanela.FATOR_Y);
		edtNumEndereco.setValidChars("1234567890");
		
		add(lblBairro= new Label("Bairro:"),LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 3*(int) ConstantesJanela.FATOR_Y);
		add(edtBairro = new Edit());
		edtBairro.setRect(AFTER + 3*(int) ConstantesJanela.FATOR_X, SAME-(int) ConstantesJanela.FATOR_Y, FILL-3*(int) ConstantesJanela.FATOR_Y,15*(int) ConstantesJanela.FATOR_Y);
		
		add(lblMunicipio= new Label("Município:"),LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 3*(int) ConstantesJanela.FATOR_Y);
		add(edtMunicipio = new Edit("999999999"),AFTER + 3*(int) ConstantesJanela.FATOR_X, SAME);
		edtMunicipio.setValidChars("1234567890");
		edtMunicipio.setMaxLength(10);
		edtMunicipio.setEditable(false);
		
		add(lblUf= new Label("UF:"),AFTER + 3*(int) ConstantesJanela.FATOR_X, SAME);
		add(edtUf = new Edit("@@"),AFTER + 3*(int) ConstantesJanela.FATOR_X, SAME);
		edtUf.setMaxLength(2);
		edtUf.setText("CE");
		edtUf.setEditable(false);
		
		add(lblSegmento= new Label("Segmento:"),LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 3*(int) ConstantesJanela.FATOR_Y);
		add(edtSegmento= new Edit("99"),AFTER + 3*(int) ConstantesJanela.FATOR_X, SAME);
		edtSegmento.setMaxLength(2);
		edtSegmento.setValidChars("1234567890");
		edtSegmento.setEditable(false);
		add(lblArea= new Label("Área:"),AFTER + 3*(int) ConstantesJanela.FATOR_X, SAME+ (int) ConstantesJanela.FATOR_Y);
		add(edtArea= new Edit("999"),AFTER + 3*(int) ConstantesJanela.FATOR_X, SAME);		
		edtArea.setMaxLength(3);
		edtArea.setValidChars("1234567890");
		edtArea.setEditable(false);
		
		add(lblMicroarea= new Label("MicroÁrea:"),LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 3*(int) ConstantesJanela.FATOR_Y);
		add(edtMicroarea= new Edit("99"),AFTER + 3*(int) ConstantesJanela.FATOR_X, SAME);
		edtMicroarea.setMaxLength(2);
		edtMicroarea.setValidChars("1234567890");
		edtMicroarea.setEditable(false);
		
		add(lblCep= new Label("CEP:"),LEFT + 3*(int) ConstantesJanela.FATOR_X, AFTER+ 3*(int) ConstantesJanela.FATOR_Y);
		add(edtCep= new Edit("99999999"),AFTER + 3*(int) ConstantesJanela.FATOR_X, SAME);
		edtCep.setMaxLength(9);
		edtCep.setValidChars("1234567890");
		
		add(lblData= new Label("Data:"),AFTER + 3*(int) ConstantesJanela.FATOR_X, SAME);
		add(edtData= new Edit("99/99/9999"),AFTER + 3*(int) ConstantesJanela.FATOR_X, SAME);
		edtData.setMaxLength(10);
		edtData.setKeyboard(Edit.KBD_CALENDAR);
		edtData.setEditable(false);
		edtData.setMode(Edit.DATE);				
		
		
		add(btnPrev = new Button("<<"));
		btnPrev.setRect(LEFT + 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		add(btnNext = new Button(">>"));
		btnNext.setRect(RIGHT - 3*(int) ConstantesJanela.FATOR_X,BOTTOM  - 3*(int) ConstantesJanela.FATOR_Y, 30*(int) ConstantesJanela.FATOR_X, ConstantesJanela.ALTURA_BOTAO);
		
		if(Settings.isColor)
		{	//Setando as cores se o dispositivos suportar cores.
	    	Color BLUE =ConstantesJanela.BLUE;
		     setForeColor(BLUE);
		   lblEndereco.setForeColor(BLUE);
		   lblNumEndereco.setForeColor(BLUE);
		   lblBairro.setForeColor(BLUE);
		   lblMunicipio.setForeColor(BLUE);
		   lblUf.setForeColor(BLUE);
		   lblSegmento.setForeColor(BLUE);
		   lblArea.setForeColor(BLUE);
		   lblMicroarea.setForeColor(BLUE);
		   lblCep.setForeColor(BLUE);
		   lblData.setForeColor(BLUE);
		   btnNext.setForeColor(BLUE);
		   btnPrev.setForeColor(BLUE);
		}
	}
	
	protected void PopulaForm() {
		edtEndereco.setText(dsEndereco);
		edtNumEndereco.setText(""+numEndereco);
		edtBairro.setText(dsBairro);
		edtMunicipio.setText(""+cdMunicipio);
		edtUf.setText(cdUf);
		edtSegmento.setText(""+cdSegmento);
		edtArea.setText(""+cdArea);
		edtMicroarea.setText(""+cdMicroarea);
		edtCep.setText(dsCep);
		edtData.setText(dtCadastro);
	}
	protected void LimpaForm() throws Exception {
		
		AgenteNgc agenteNgc = AgenteNgc.criarInstancia();
		ResultSet rs = agenteNgc.getSegAreaMicroa();
		if(rs.next()){
			edtSegmento.setText(rs.getString("CD_SEG"));
			edtArea.setText(rs.getString("CD_AREA"));
			edtMicroarea.setText(rs.getString("CD_MICROA"));
			edtMunicipio.setText("13302");
		}else{
			edtSegmento.setText("");
			edtArea.setText("");
			edtMicroarea.setText("");
			edtMunicipio.setText("");
		}
		edtEndereco.setText("");
		edtNumEndereco.setText("");
		edtBairro.setText("");
		
		edtUf.setText("CE");		
		edtCep.setText("");
		edtData.setText("");
	}
	

    public void onEvent(Event e) {
    	try{
		    if (e.type == ControlEvent.PRESSED) { 
		    	if(e.target==btnNext){
		    		if(ValidaForm()){
			    		PreencheAtributo();
			    		 FichaA3Win fichaA3Win = FichaA3Win.criarInstancia();
			    		 fichaA3Win.LimpaForm();
			    		 if(ListaFamiliaWin.cmd.equals("U")){
			    			 fichaA3Win.PopulaForm();
			    		 }			    		 
			    		 fichaA3Win.popupBlockingModal();
			    		 if("F".equals(ListaFamiliaWin.cmd)){
							   unpop();   
						   }
		    		}
		    	}else if(e.target==btnPrev){
		    		unpop();
		    	}			    
		    }else if(e.type == PenEvent.PEN_UP){
		    	 if(e.target==edtData){				    	
		    		 edtData.popupKCC();
				 }
			}			   
    	}catch (Exception error) {
			MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+error.getMessage());
			msgBoxErro.popupBlockingModal();
			Vm.debug("Erro: "+error.getMessage());
		}   
	}  
   
    /**
     *  lê o form e carrrega os atributos 
     */
    private void PreencheAtributo(){
    	 this.cdFamilia = cdFamilia;
    	 this.dsEndereco = edtEndereco.getText().toUpperCase();
    	 this.numEndereco = Convert.toInt(edtNumEndereco.getText());
    	 this.dsBairro = edtBairro.getText();
    	 this.dsCep = edtCep.getText();
    	 this.cdMunicipio= Convert.toInt(edtMunicipio.getText());
    	 this.cdSegmento= Convert.toInt(edtSegmento.getText());
    	 this.cdArea=Convert.toInt(edtArea.getText());
    	 this.cdMicroarea= Convert.toInt(edtMicroarea.getText());
    	 this.dtCadastro= edtData.getText();
    	 this.cdUf= edtUf.getText().toUpperCase();
    }
    
    /**
     * lê os pdbs e carrrega os atributos
     */
    protected void CarregaAtributoPdb(int cdFamilia){
    	try{
		     FichaANgc fichaANgc = FichaANgc.criarInstancia();
		     ResultSet rs = fichaANgc.ListaFamilia(cdFamilia);
		    if(rs.next()){ 
			    this.cdFamilia=cdFamilia;
			    this.dsEndereco=rs.getString("DS_ENDERECO");
			    this.numEndereco=rs.getInt("NUM_ENDERECO");
			    this.dsBairro=rs.getString("DS_BAIRRO");
			    this.dsCep=rs.getString("DS_CEP");
			    this.cdMunicipio=13302;
			    this.cdSegmento=rs.getInt("CD_SEGMENTO");
			    this.cdArea=rs.getInt("CD_AREA");
			    this.cdMicroarea=rs.getInt("CD_MICROAREA");
			    this.dtCadastro=rs.getString("DT_CADASTRO");
			    //this.cdUf=rs.getString("CD_UF");
			    this.cdUf="CE";
			    this.cdTpCasa=rs.getInt("CD_TPCASA");
			    this.dsTpCasaEsp=rs.getString("DS_TPCASAESP");
			    this.numComodos=rs.getInt("NUM_COMODOS");
			    this.flEnergia=rs.getString("FL_ENERGIA");
			    this.cdDestLixo=rs.getInt("CD_DESTLIXO");
			    this.cdTrataAgua=rs.getInt("CD_TRATAAGUA");
			    this.cdAbasteAgua=rs.getInt("CD_ABASTEAGUA");
			    this.cdFezesUrina=rs.getInt("CD_FEZESURINA");
			    this.cdDoencaProcura=rs.getInt("CD_DOENCAPROCU");
			    this.dsDoencaProcuraEsp=rs.getString("DS_DOENCAPROCUESP");
			    this.cdGrupoComu=rs.getInt("CD_GRUPOCOMU");
			    this.dsGrupoComuEsp=rs.getString("DS_GRUPOCOMUESP");
			    this.cdMeiosComuni=rs.getInt("CD_MEIOSCOMUNI");
			    this.dsMeiosComuniEsp=rs.getString("DS_MEIOSCOMUNIESP");
			    this.cdMeiosTrans=rs.getInt("CD_MEIOSTRANS");
			    this.dsMeiosTransEsp=rs.getString("DS_MEIOSTRANSESP");
			    this.flPossuiPlano=rs.getString("FL_POSSUIPLAN");
			    this.numPesPlano=rs.getInt("NUM_PESPLAN");
			    this.nmPlano=rs.getString("NM_PLANO");
			    this.dsObservacao=rs.getString("DS_OBSERVACAO");
		    }
    	} catch (Exception e) {
    		Vm.debug("Erro: "+e.getMessage());
    		MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+e.getMessage());
			msgBoxErro.popupBlockingModal();
		}
   }
    /**
     * Limpa os atributos
     */
    protected void LimpaAtributo(){
    	try{   
			    this.dsEndereco="";
			    this.numEndereco=-1;
			    this.dsBairro="";
			    this.dsCep="";
			    this.cdMunicipio=-1;
			    this.cdSegmento=-1;
			    this.cdArea=-1;
			    this.cdMicroarea=-1;
			    this.dtCadastro="";
			    this.cdUf="";
			    this.cdTpCasa=-1;
			    this.dsTpCasaEsp="";
			    this.numComodos=-1;
			    this.flEnergia="";
			    this.cdDestLixo=-1;
			    this.cdTrataAgua=-1;
			    this.cdAbasteAgua=-1;
			    this.cdFezesUrina=-1;
			    this.cdDoencaProcura=-1;
			    this.dsDoencaProcuraEsp="";
			    this.cdGrupoComu=-1;
			    this.dsGrupoComuEsp="";
			    this.cdMeiosComuni=-1;
			    this.dsMeiosComuniEsp="";
			    this.cdMeiosTrans=-1;
			    this.dsMeiosTransEsp="";
			    this.flPossuiPlano="";
			    this.numPesPlano=-1;
			    this.nmPlano="";
			    this.dsObservacao="";
    	} catch (Exception e) {
    		Vm.debug("Erro: "+e.getMessage());
    		MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+e.getMessage());
			msgBoxErro.popupBlockingModal();
		}
   }

    private boolean ValidaForm(){
		  MessageBox msgGrid;
		  try{  
			    if("".equals(edtEndereco.getText())){
			    	msgGrid = new MessageBox("Atenção","Preencha o Campo Endereço", new String[]{"OK"});			    	
			    	msgGrid.popupBlockingModal();
			    	msgGrid.setForeColor(ConstantesJanela.BLUE);
			    	return false;
			    }
			    if("".equals(edtNumEndereco.getText())){
			    	msgGrid = new MessageBox("Atenção","Preencha o Campo Número", new String[]{"OK"});			    	
			    	msgGrid.popupBlockingModal();
			    	msgGrid.setForeColor(ConstantesJanela.BLUE);
			    	return false;
			    }
			    if("".equals(edtBairro.getText())){
			    	msgGrid = new MessageBox("Atenção","Preencha o Campo Bairro", new String[]{"OK"});			    	
			    	msgGrid.popupBlockingModal();
			    	msgGrid.setForeColor(ConstantesJanela.BLUE);
			    	return false;
			    }
			    if("".equals(edtMunicipio.getText())){
			    	msgGrid = new MessageBox("Atenção","Preencha o Campo Município", new String[]{"OK"});			    	
			    	msgGrid.popupBlockingModal();
			    	msgGrid.setForeColor(ConstantesJanela.BLUE);
			    	return false;
			    }
			    if("".equals(edtUf.getText())){
			    	msgGrid = new MessageBox("Atenção","Preencha o Campo UF", new String[]{"OK"});			    	
			    	msgGrid.popupBlockingModal();
			    	msgGrid.setForeColor(ConstantesJanela.BLUE);
			    	return false;
			    }
			    if("".equals(edtSegmento.getText())){
			    	msgGrid = new MessageBox("Atenção","Preencha o Campo Segmento", new String[]{"OK"});			    	
			    	msgGrid.popupBlockingModal();
			    	msgGrid.setForeColor(ConstantesJanela.BLUE);
			    	return false;
			    }
			    if("".equals(edtArea.getText())){
			    	msgGrid = new MessageBox("Atenção","Preencha o Campo Área", new String[]{"OK"});			    	
			    	msgGrid.popupBlockingModal();
			    	msgGrid.setForeColor(ConstantesJanela.BLUE);
			    	return false;
			    }
			    if("".equals(edtMicroarea.getText())){
			    	msgGrid = new MessageBox("Atenção","Preencha o Campo Micro Área", new String[]{"OK"});			    	
			    	msgGrid.popupBlockingModal();
			    	msgGrid.setForeColor(ConstantesJanela.BLUE);
			    	return false;
			    }
			    if("".equals(edtData.getText())){
			    	msgGrid = new MessageBox("Atenção","Preencha o Campo Data", new String[]{"OK"});			    	
			    	msgGrid.popupBlockingModal();
			    	msgGrid.setForeColor(ConstantesJanela.BLUE);
			    	return false;
			    }
				if("".equals(edtCep.getText())){
					msgGrid = new MessageBox("Atenção","Preencha o Campo CEP", new String[]{"OK"});			    	
			    	msgGrid.popupBlockingModal();
			    	msgGrid.setForeColor(ConstantesJanela.BLUE);
			    	return false;
				}				
		  }catch (Exception error) {
				MessageBox msgBoxErro = new MessageBox("Erro","Erro: "+error.getMessage());
				msgBoxErro.popupBlockingModal();
				Vm.debug("Erro: "+error.getMessage());
		 }   
		  return true;
	  }  
}
