
package br.com.bibiano.siab.sync;

/**
 * Classe Sync
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */

import litebase.LitebaseConnection;
import litebase.PreparedStatement;
import litebase.ResultSet;
import litebase.ResultSetMetaData;
import waba.sys.Convert;
import waba.sys.Vm;
import br.com.bibiano.siab.business.SIABBusiness;
import br.com.bibiano.siab.window.ReplicationWin;


public class BBPDBPrincipal extends SIABBusiness{
	static LitebaseConnection driver ;
	
	public static BBPDBPrincipal criarInstancia()
	{
		driver = LitebaseConnection.getInstance(CREATORID);
		return new BBPDBPrincipal();
	}

	
	/**
	 * Metodo deleta todos os registros do sitema que foram passados pelo parametro rs na tabela informada
	 * @param rs
	 * @param nmTabela
	 * @throws Exception
	 */
	public void DeletedRecordsTabelaPrincipal(ResultSet rs,String nmTabela) throws Exception {
		
		ResultSetMetaData metaData =rs.getResultSetMetaData();
		String values="";
		String valuesTemp="";
		String sql="";
		int numCols=metaData.getColumnCount();		
		
		ResultSet rsDescriber = BBInfoDescriber.criarInstancia().getKeyTable(nmTabela);
		int numRegistros = rs.getRowCount();
		int pointerRs=0;		
		while(rs.next()){
			rsDescriber.beforeFirst();
			while(rsDescriber.next()){			  
				for(int i=1;i<numCols;i++){
					if(metaData.getColumnLabel(i).equalsIgnoreCase(rsDescriber.getString("NM_COLUNA"))){
						if(ResultSetMetaData.CHAR_TYPE==metaData.getColumnType(i)){
							valuesTemp = rsDescriber.getString("NM_COLUNA")+"='"+rs.getString(i)+"'";
						}else if(ResultSetMetaData.SHORT_TYPE ==metaData.getColumnType(i)){
							valuesTemp = rsDescriber.getString("NM_COLUNA")+"="+rs.getShort(i);
						}else if(ResultSetMetaData.INT_TYPE ==metaData.getColumnType(i)){
							valuesTemp = rsDescriber.getString("NM_COLUNA")+"="+rs.getInt(i);
						}else if(ResultSetMetaData.LONG_TYPE ==metaData.getColumnType(i)){
							valuesTemp = rsDescriber.getString("NM_COLUNA")+"="+rs.getLong(i);
						}else if(ResultSetMetaData.FLOAT_TYPE ==metaData.getColumnType(i)){
							valuesTemp = rsDescriber.getString("NM_COLUNA")+"="+rs.getFloat(i);
						}else if(ResultSetMetaData.DOUBLE_TYPE ==metaData.getColumnType(i)){
							valuesTemp = rsDescriber.getString("NM_COLUNA")+"="+rs.getDouble(i);
						}else if(ResultSetMetaData.CHAR_NOCASE_TYPE ==metaData.getColumnType(i)){
							valuesTemp = rsDescriber.getString("NM_COLUNA")+"='"+rs.getString(i)+"'";
						}
						if(i==1){
							values=valuesTemp;
						}else{
							values=values+" AND "+valuesTemp;
						}						
					}				
				}					
			}
			pointerRs++;
			sql="DELETE FROM "+nmTabela+" WHERE "+values;			
			driver.executeUpdate(sql);
			//Vm.debug(sql);
			if( Math.ceil(((float)pointerRs/numRegistros)*100) % 5==0){
				ReplicationWin.progBarReplication.setValue(Convert.toInt(""+Math.round(Math.ceil(((float)pointerRs/numRegistros)*100))));				
			}	
			
		}
	}
	
	/**
	 * Metodo insere todos os registros no sitema que foram passados pelo parametro rs na tabela informada
	 * @param rs
	 * @param nmTabela
	 * @throws Exception
	 */
	public void InsertRecordsTabelaPrincipal(ResultSet rs,String nmTable) throws Exception {
			
			ResultSetMetaData metaData =rs.getResultSetMetaData();
		/*	String values="";
			String valuesTemp="";
			String sql="";*/
			int numCols=metaData.getColumnCount();
			int i;
			int tpColuna;
			int tipocluna [] = new int[numCols];
			for(int w=1;w<numCols;w++){
				tipocluna[w-1] = metaData.getColumnType(w);
			}
			int numRegistros = rs.getRowCount();
			int pointerRs=0;	
			String  paramPst="";
			for(i=0;i<numCols;i++){
				if(i==0){
					paramPst="?";
				}else{
					paramPst+=",?";
				}
			}
			PreparedStatement ps = driver.prepareStatement("INSERT INTO "+nmTable+" VALUES("+paramPst+")");
			driver.setRowInc(nmTable,rs.getRowCount());
			while(rs.next()){
				for(i=1;i<numCols;i++){
					tpColuna = tipocluna[i-1];
					
					/*if(ResultSetMetaData.CHAR_TYPE==tpColuna){
						valuesTemp="'"+rs.getString(i)+"'";
					}else if(ResultSetMetaData.SHORT_TYPE ==tpColuna){
						valuesTemp=""+rs.getShort(i);
					}else if(ResultSetMetaData.INT_TYPE ==tpColuna){
						valuesTemp=""+rs.getInt(i);
					}else if(ResultSetMetaData.LONG_TYPE ==tpColuna){
						valuesTemp=""+rs.getLong(i);
					}else if(ResultSetMetaData.FLOAT_TYPE ==tpColuna){
						valuesTemp=""+rs.getFloat(i);
					}else if(ResultSetMetaData.DOUBLE_TYPE ==tpColuna){
						valuesTemp=""+rs.getDouble(i);
					}else if(ResultSetMetaData.CHAR_NOCASE_TYPE ==tpColuna){
						valuesTemp="'"+rs.getString(i)+"'";
					}
					if(i==1){
						values=valuesTemp;
					}else{
						values=values+","+valuesTemp;
					}*/
					if(ResultSetMetaData.CHAR_TYPE==tpColuna){
						ps.setString(i-1,rs.getString(i));
					}else if(ResultSetMetaData.SHORT_TYPE ==tpColuna){
						ps.setInt(i-1,rs.getShort(i));
					}else if(ResultSetMetaData.INT_TYPE ==tpColuna){
						ps.setInt(i-1,rs.getInt(i));
					}else if(ResultSetMetaData.LONG_TYPE ==tpColuna){
						ps.setLong(i-1,rs.getLong(i));
					}else if(ResultSetMetaData.FLOAT_TYPE ==tpColuna){
						ps.setDouble(i-1,rs.getFloat(i));
					}else if(ResultSetMetaData.DOUBLE_TYPE ==tpColuna){
						ps.setDouble(i-1,rs.getDouble(i));
					}else if(ResultSetMetaData.CHAR_NOCASE_TYPE ==tpColuna){
						ps.setString(i-1,rs.getString(i));
					}
				}
				
				ps.setInt(numCols-1,BBPDBTemp.SSK_RECORD_NULL);			
				ps.executeUpdate();
				/*sql=" INSERT INTO "+nmTable+" VALUES("+values+","+BBPDBTemp.SSK_RECORD_NULL+")";			
				driver.executeUpdate(sql);*/
				pointerRs++;
				if( Math.ceil(((float)pointerRs/numRegistros)*100) % 5==0){
					ReplicationWin.progBarReplication.setValue(Convert.toInt(""+Math.round(Math.ceil(((float)pointerRs/numRegistros)*100))));				
				}	
				//Vm.debug(sql);
			}
			driver.setRowInc(nmTable,-1);
		
	}
	
	/**
	 * Metodo altera todos os registros do sitema que foram passados pelo parametro rs na tabela informada
	 * @param rs
	 * @param nmTabela
	 * @throws Exception
	 */
	public void UpdateRecordsTabelaPrincipal(ResultSet rs,String nmTabela) throws Exception {
		
		ResultSetMetaData metaData =rs.getResultSetMetaData();
		String values="";
		String valuesTemp="";
		String sql="";
		int numCols=metaData.getColumnCount();		
		int numRegistros = rs.getRowCount()*2;
		int pointerRs=0;
		ResultSet rsDescriber = BBInfoDescriber.criarInstancia().getKeyTable(nmTabela);
		while(rs.next()){
			rsDescriber.beforeFirst();
			while(rsDescriber.next()){			  
				for(int i=1;i<numCols;i++){
					if(metaData.getColumnLabel(i).equalsIgnoreCase(rsDescriber.getString("NM_COLUNA"))){
						if(ResultSetMetaData.CHAR_TYPE==metaData.getColumnType(i)){
							valuesTemp = rsDescriber.getString("NM_COLUNA")+"='"+rs.getString(i)+"'";
						}else if(ResultSetMetaData.SHORT_TYPE ==metaData.getColumnType(i)){
							valuesTemp = rsDescriber.getString("NM_COLUNA")+"="+rs.getShort(i);
						}else if(ResultSetMetaData.INT_TYPE ==metaData.getColumnType(i)){
							valuesTemp = rsDescriber.getString("NM_COLUNA")+"="+rs.getInt(i);
						}else if(ResultSetMetaData.LONG_TYPE ==metaData.getColumnType(i)){
							valuesTemp = rsDescriber.getString("NM_COLUNA")+"="+rs.getLong(i);
						}else if(ResultSetMetaData.FLOAT_TYPE ==metaData.getColumnType(i)){
							valuesTemp = rsDescriber.getString("NM_COLUNA")+"="+rs.getFloat(i);
						}else if(ResultSetMetaData.DOUBLE_TYPE ==metaData.getColumnType(i)){
							valuesTemp = rsDescriber.getString("NM_COLUNA")+"="+rs.getDouble(i);
						}else if(ResultSetMetaData.CHAR_NOCASE_TYPE ==metaData.getColumnType(i)){
							valuesTemp = rsDescriber.getString("NM_COLUNA")+"='"+rs.getString(i)+"'";
						}
						if(i==1){
							values=valuesTemp;
						}else{
							values=values+" AND "+valuesTemp;
						}						
					}				
				}					
			}
			sql="DELETE FROM "+nmTabela+" WHERE "+values;			
			driver.executeUpdate(sql);
			pointerRs++;
			if( Math.ceil(((float)pointerRs/numRegistros)*100) % 5==0){
				ReplicationWin.progBarReplication.setValue(Convert.toInt(""+Math.round(Math.ceil(((float)pointerRs/numRegistros)*100))));				
			}			
		}
		
		int i;
		int tpColuna;
		int tipocluna [] = new int[numCols];
		for(int w=1;w<numCols;w++){
			tipocluna[w-1] = metaData.getColumnType(w);
		}
		rs.beforeFirst();
		while(rs.next()){
			for(i=1;i<numCols;i++){
				tpColuna = tipocluna[i-1];
				if(ResultSetMetaData.CHAR_TYPE==tpColuna){
					valuesTemp="'"+rs.getString(i)+"'";
				}else if(ResultSetMetaData.SHORT_TYPE ==tpColuna){
					valuesTemp=""+rs.getShort(i);
				}else if(ResultSetMetaData.INT_TYPE ==tpColuna){
					valuesTemp=""+rs.getInt(i);
				}else if(ResultSetMetaData.LONG_TYPE ==tpColuna){
					valuesTemp=""+rs.getLong(i);
				}else if(ResultSetMetaData.FLOAT_TYPE ==tpColuna){
					valuesTemp=""+rs.getFloat(i);
				}else if(ResultSetMetaData.DOUBLE_TYPE ==tpColuna){
					valuesTemp=""+rs.getDouble(i);
				}else if(ResultSetMetaData.CHAR_NOCASE_TYPE ==tpColuna){
					valuesTemp="'"+rs.getString(i)+"'";
				}
				if(i==1){
					values=valuesTemp;
				}else{
					values=values+","+valuesTemp;
				}
			}
			sql=" INSERT INTO "+nmTabela+" VALUES("+values+","+BBPDBTemp.SSK_RECORD_NULL+")";			
			driver.executeUpdate(sql);
			pointerRs++;
			if( Math.ceil(((float)pointerRs/numRegistros)*100) % 5==0){
				ReplicationWin.progBarReplication.setValue(Convert.toInt(""+Math.round(Math.ceil(((float)pointerRs/numRegistros)*100))));				
			}				
		}		
	}
	/*public void UpdateAllRecordsTabelaPrincipal(ResultSet rs,String nmTabela) throws Exception {
		PDBDriver driver = PDBDriver.getInstance(CREATORID);
		
		ResultSetMetaData metaData =rs.getResultSetMetaData();
		String values="";
		String valuesTemp="";
		String setValues="";
		String setValuesTemp="";
		String sql="";
		int numCols=metaData.getColumnCount();
		String nmColuna [] = new String[numCols+1];
		for(int w=1;w<=numCols;w++){
			nmColuna[w] = metaData.getColumnLabel(w);
		}
		int tipocluna [] = new int[numCols+1];
		for(int w=1;w<=numCols;w++){
			tipocluna[w] = metaData.getColumnType(w);
		}
		ResultSet rsDescriber = BBInfoDescriber.criarInstancia().getKeyTable(nmTabela);
		int ff=rsDescriber.getRowCount();
		while(rs.next()){
			rsDescriber.beforeFirst();
			values="";
			setValues="";
			while(rsDescriber.next()){									  
				for(int i=1;i<metaData.getColumnCount();i++){
					if(nmColuna[i].equalsIgnoreCase(rsDescriber.getString("NM_COLUNA"))){
						if(ResultSetMetaData.CHAR_TYPE==tipocluna[i]){
							valuesTemp = nmColuna[i]+"='"+rs.getString(i)+"'";							
						}else if(ResultSetMetaData.LONG_TYPE ==tipocluna[i]){
							valuesTemp = nmColuna[i]+"="+rs.getLong(i)+"L";
						}else if(ResultSetMetaData.INT_TYPE ==tipocluna[i]){
							valuesTemp = nmColuna[i]+"="+rs.getInt(i);
						}else if(ResultSetMetaData.SHORT_TYPE ==tipocluna[i]){
							valuesTemp = nmColuna[i]+"="+rs.getShort(i);
						}else if(ResultSetMetaData.FLOAT_TYPE ==tipocluna[i]){
							valuesTemp = nmColuna[i]+"="+rs.getFloat(i);
						}else if(ResultSetMetaData.DOUBLE_TYPE ==tipocluna[i]){
							valuesTemp = nmColuna[i]+"="+rs.getDouble(i);
						}else if(ResultSetMetaData.CHAR_NOCASE_TYPE ==tipocluna[i]){
							valuesTemp = nmColuna[i]+"='"+rs.getString(i)+"'";
						}
						if("".equals(values)){
							values=valuesTemp;
						}else{
							values=values+" AND "+valuesTemp;
						}
						break;
					}
				}
			}	
			boolean achou=false;									  
			for(int i=1;i<numCols;i++){
				rsDescriber.beforeFirst();				
				while(rsDescriber.next()){
					if(nmColuna[i].equalsIgnoreCase(rsDescriber.getString("NM_COLUNA"))){
						achou=true;
						break;
					}	
				}
				if(achou){
					achou=false;
					continue;
				}
				if(ResultSetMetaData.CHAR_TYPE==tipocluna[i]){
					setValuesTemp = nmColuna[i]+"='"+rs.getString(i)+"'";
				}else if(ResultSetMetaData.LONG_TYPE ==tipocluna[i]){
					setValuesTemp = nmColuna[i]+"="+rs.getLong(i);
				}else if(ResultSetMetaData.INT_TYPE ==tipocluna[i]){
					setValuesTemp = nmColuna[i]+"="+rs.getInt(i);
				}else if(ResultSetMetaData.SHORT_TYPE ==tipocluna[i]){
					setValuesTemp = nmColuna[i]+"="+rs.getShort(i);
				}else if(ResultSetMetaData.FLOAT_TYPE ==tipocluna[i]){
					setValuesTemp = nmColuna[i]+"="+rs.getFloat(i);
				}else if(ResultSetMetaData.DOUBLE_TYPE ==tipocluna[i]){
					setValuesTemp = nmColuna[i]+"="+rs.getDouble(i);
				}else if(ResultSetMetaData.CHAR_NOCASE_TYPE ==tipocluna[i]){
					setValuesTemp = nmColuna[i]+"='"+rs.getString(i)+"'";
				}
				if("".equals(setValues)){
					setValues=setValuesTemp;
				}else{
					setValues=setValues+", "+setValuesTemp;
				}
	      }
			sql="UPDATE "+nmTabela+" set "+setValues+" WHERE "+values;			
			driver.executeUpdate(sql);
			Vm.debug(sql);
		}
	}*/
	/**
	*  Métodos deleta todos os registros da tabela principal que estão com o status de deleção
	*  @param nmTabela	
	*  @throws Exception Erro ao ler os dados.
	*/
	public void DeleteAllRecordsDeleted(String nmTabela) throws Exception {
		 driver.executeUpdate(" DELETE FROM "+nmTabela+" WHERE  STATUS ="+BBPDBTemp.SSK_RECORD_DELETED);
		
	}
	
	/**
	*  Métodos deleta todos os registros da tabela principal
	*  @param nmTabela
	*  @throws Exception Erro ao ler os dados.
	*/
	public void DeleteAllRecords(String nmTabela) throws Exception {
		 driver.executeUpdate(" DELETE FROM "+nmTabela);
		
	}
	
	/**
	*  Métodos retorna o sequancial que servirá pra indicar se deve replicar os dados
	*  @throws Exception Erro ao ler os dados.
	*/
	public int getSequencial() throws Exception{
		ResultSet rs = driver.executeQuery(" SELECT ROWID, SEQUENCIAL FROM VALIDATION");
		if(rs.next()){
			return rs.getInt("SEQUENCIAL");	
		}else{
			return 0;
		}		
	}
	
	/**
	*  Métodos exclui o sequencial 
	*  @throws Exception Erro ao ler os dados.
	*/
	public void DeletaSequencial() throws Exception{
		driver.executeUpdate("DELETE FROM VALIDATION");
	}
	
	/**
	*  Métodos insere o sequencial
	*  @throws Exception Erro ao ler os dados.
	*/
	public void InserteSequencial(int seq) throws Exception{
		driver.executeUpdate("INSERT INTO VALIDATION VALUES("+seq+")");
	}
}