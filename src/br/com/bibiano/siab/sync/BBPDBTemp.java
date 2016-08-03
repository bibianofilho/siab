package br.com.bibiano.siab.sync;


import litebase.LitebaseConnection;
import litebase.ResultSet;
import br.com.bibiano.siab.business.SIABBusiness;

/**
 * Classe Sync
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */

public class BBPDBTemp extends SIABBusiness{

	public final static int SSK_RECORD_NULL =  0; //flag de vazio
	public final static int SSK_RECORD_INSERTED = 1 ; //flag de registro a inserir.
	public final static int SSK_RECORD_UPDATED=  2 ;//flag de registro a alterar.
	public final static int SSK_RECORD_DELETED=  3 ; //flag de registro a excluir.
	public final static int SSK_DELETEALL =  4 ; //flag de exclusão total.
	public final static String SufixoTemp="TEMP";
	static LitebaseConnection driver ;

	public static BBPDBTemp criarInstancia()
	{
		driver = LitebaseConnection.getInstance(CREATORID);
		return new BBPDBTemp();
	}

	
	public String[] getNameTables(){
		return new String[]{"FICHACADFAM","PACFAMILIA","FICHACRIANCA","FICHACRIANCAACOMP","FICHATB",
				"FICHATBACOMP","FICHAHA","FICHAHAACOMP","FICHADIA","FICHADIAACOMP","FICHAGES","FICHAGESACOMP",
				"FICHAHAN","FICHAHANACOMP","FICHAIDOSO","FICHAIDOSOACOMP","OCUPACAO","AGENTE"};
	}
	
	/**
	*  Métodos deleta todos os registros da tabela temporária que estão com o status de deleção	
	*  @throws Exception Erro ao ler os dados.
	*/
	public void DeleteAllRecordsDeleted(String nmTabela) throws Exception {
		 driver.executeUpdate(" DELETE FROM "+nmTabela+BBPDBTemp.SufixoTemp+" WHERE  STATUS ="+SSK_RECORD_DELETED);
		
	}
	
	/**
	*  Métodos deleta todos os registros da tabela temporária que estão com o status de inserção	
	*  @throws Exception Erro ao ler os dados.
	*/
	public void DeleteAllRecordsInserted(String nmTabela) throws Exception {
	 driver.executeUpdate(" DELETE FROM "+nmTabela+BBPDBTemp.SufixoTemp+" WHERE  STATUS ="+SSK_RECORD_INSERTED);
		
	}
	
	/**
	*  Métodos deleta todos os registros da tabela temporária que estão com o status de updade	
	*  @throws Exception Erro ao ler os dados.
	*/
	public void DeleteAllRecordsUpdated(String nmTabela) throws Exception {
		 driver.executeUpdate(" DELETE FROM "+nmTabela+BBPDBTemp.SufixoTemp+" WHERE  STATUS ="+SSK_RECORD_UPDATED);
		
	}
	
	/**
	*  Métodos deleta todos os registros da tabela temporária que estão com o status de delete all	
	*  @throws Exception Erro ao ler os dados.
	*/
	public void DeleteAllRecordsDeletedAll(String nmTabela) throws Exception {
		 driver.executeUpdate(" DELETE FROM "+nmTabela+BBPDBTemp.SufixoTemp+" WHERE  STATUS ="+SSK_DELETEALL);
		
	}

	/**
	*  Métodos retorna se a tabela deverá excluir toda
	*  no PDB do sistema SSK_DELETEALL.
	*  @return boolean true: deletar todos os registros.
	*  @throws Exception Erro ao ler os dados.
	*/
	public ResultSet getDeletedAllRecords(String nmTabela) throws Exception {
		//Vm.debug(Settings.dataPath);
		ResultSet rs = driver.executeQuery(" SELECT * FROM "+nmTabela+BBPDBTemp.SufixoTemp+" WHERE  STATUS ="+SSK_DELETEALL);
		//ResultSet rs = driver.executeQuery(" SELECT * FROM "+nmTabela+BBPDBTemp.SufixoTemp);
		return rs;
	}

	/**
	*  Método que retorna os regitros a serem inseridos
	*  no PDB do sistema.	
	*  @return ResultSet registros a serem inseridos.
	*  @throws Exception Erro ao buscar registros ou ler os dados.
    */
	public ResultSet getInsertedRecords(String nmTabela) throws Exception {
		ResultSet rs = driver.executeQuery(" SELECT * FROM "+nmTabela+BBPDBTemp.SufixoTemp+" WHERE  STATUS ="+SSK_RECORD_INSERTED);
		return rs;
	}

	/**
	*  Método que retorna um ResultSet com os regitros a serem alterados
	*  no PDB do sistema.	
	*  @return ResultSet registros a serem alterados.
	*  @throws Exception Erro ao buscar registros.
	*/
	public ResultSet getUpdatedRecords(String nmTabela) throws Exception {
		ResultSet rs = driver.executeQuery(" SELECT * FROM "+nmTabela+BBPDBTemp.SufixoTemp+" WHERE  STATUS ="+SSK_RECORD_UPDATED);
		return rs;
	}

	/**
	*  Método que retorna um ResultSet com os regitros a serem apagados
	*  no PDB do sistema.
	*  @return ResultSet registros a serem deletados.
	*  @throws Exception Erro ao buscar registros.
	*/
	public ResultSet getDeletedRecords(String nmTabela) throws Exception {
		ResultSet rs = driver.executeQuery(" SELECT * FROM "+nmTabela+BBPDBTemp.SufixoTemp+" WHERE  STATUS ="+SSK_RECORD_DELETED);
		return rs;
	}
	
	
	public int getSequencial() throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT ROWID,SEQUENCIAL FROM VALIDATION"+BBPDBTemp.SufixoTemp);
		if(rs.next()){
			return rs.getInt("SEQUENCIAL");	
		}else{
			return 0;
		}	
	}
}