package br.com.bibiano.siab.sync;

import litebase.ResultSet;
import waba.sys.Vm;
import br.com.bibiano.siab.util.BBDate;
import br.com.bibiano.siab.window.ReplicationWin;

/**
 * Classe Sync
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class ReplicationPdb {
	public static ReplicationPdb criarInstancia()
	{
		return new ReplicationPdb();
	}
	
	
	public void ControllerReplication(String nmTabela) throws Exception{
		
		
		BBPDBTemp bbPdbTemp = BBPDBTemp.criarInstancia();
		BBPDBPrincipal bbPdbPrincipal= BBPDBPrincipal.criarInstancia();
			
			
		
	   			
	   			
	   			ResultSet rsTemp;
	   			//deleta todos os registros da tabela principal
	   			ReplicationWin.lblAcao.setText("Delete All");
	   			ReplicationWin.lblAcao.repaintNow();	
	   			rsTemp= bbPdbTemp.getDeletedAllRecords(nmTabela);
	   			ReplicationWin.progBarReplication.setValue(0);
	   			if(rsTemp.getRowCount()>0){	   				
	   				bbPdbPrincipal.DeleteAllRecords(nmTabela);
	   			}
	   			bbPdbTemp.DeleteAllRecordsDeletedAll(nmTabela);
	   		    ReplicationWin.progBarReplication.setValue(100);
	   		    
	   		    //deleta registro da tabela principal
	   		    ReplicationWin.lblAcao.setText("Delete");
	   			ReplicationWin.lblAcao.repaintNow();
	   			rsTemp= bbPdbTemp.getDeletedRecords(nmTabela);
	   			ReplicationWin.progBarReplication.setValue(0);
	   			if(rsTemp.getRowCount()>0){	   				
	   				bbPdbPrincipal.DeletedRecordsTabelaPrincipal(rsTemp,nmTabela);
	   			}
	   		    bbPdbTemp.DeleteAllRecordsDeleted(nmTabela);
	   		    ReplicationWin.progBarReplication.setValue(100);
	   		 
	   			//insere os registro na tabela principal
	   		    ReplicationWin.lblAcao.setText("Inserindo");
	   			ReplicationWin.lblAcao.repaintNow();
	   			rsTemp= bbPdbTemp.getInsertedRecords(nmTabela);  
	   			ReplicationWin.progBarReplication.setValue(0);
	   			if(rsTemp.getRowCount()>0){	   				
	   				bbPdbPrincipal.InsertRecordsTabelaPrincipal(rsTemp,nmTabela);
	   			}
	   	   		bbPdbTemp.DeleteAllRecordsInserted(nmTabela);
	   	   		ReplicationWin.progBarReplication.setValue(100);
	   	   	
	   			//Altera os registro na tabela principal
		   	   	ReplicationWin.lblAcao.setText("Atualizando");
	   			ReplicationWin.lblAcao.repaintNow();
	   			rsTemp= bbPdbTemp.getUpdatedRecords(nmTabela);
	   			ReplicationWin.progBarReplication.setValue(0);
	   			if(rsTemp.getRowCount()>0){
	   				bbPdbPrincipal.UpdateRecordsTabelaPrincipal(rsTemp,nmTabela);
	   			}
	   			bbPdbTemp.DeleteAllRecordsUpdated(nmTabela);
	   			ReplicationWin.progBarReplication.setValue(100);
	   			ReplicationWin.lblAcao.setText("");
	   			ReplicationWin.lblAcao.repaintNow();
	   			//deleta todos os registros da tabela principal que estão com status de deleção
	   			bbPdbPrincipal.DeleteAllRecordsDeleted(nmTabela);
	   	
		//bbPdbPrincipal.DeletaSequencial();
		
   		
	}
	
	/*public static void main(String[] args) {
		try {
			waba.applet.JavaBridge.setNonGUIApp();
			Settings.dataPath = "C:\\bibiano\\workspace\\SIAB\\dataTeste";
			//Settings.dataPath = "F:\\SIAB\\dataTeste";
			ReplicationPdb replicationPdb = criarInstancia();
			replicationPdb.ControllerReplication();		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
