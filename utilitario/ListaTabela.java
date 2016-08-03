
import litebase.LitebaseConnection;
import litebase.ResultSet;
import litebase.ResultSetMetaData;
import waba.sys.Settings;
import waba.sys.Vm;

public class ListaTabela {

		
	public static void main(String[] args) {
		
		    	waba.applet.JavaBridge.setNonGUIApp( );
		    	// Settings.dataPath = "C:\\bibiano\\workspace\\SIAB\\data";
//		    	/ Settings.dataPath = "C:\\bibiano\\workspace\\SIAB\\dataTeste";
		    	 Settings.dataPath = "F:\\SIAB_MOBILE2\\SIAB\\dataTeste";
		    	 //Settings.dataPath =  "C:\\TEMP\\criou2\\20060529\\1\\carga";
		
		String CREATORID = "SIAB";		
		LitebaseConnection driver = LitebaseConnection.getInstance(CREATORID);
		String[] nmTabela={"FICHACADFAM","PACFAMILIA","FICHACRIANCA","FICHACRIANCAACOMP","FICHATB",
				"FICHATBACOMP","FICHAHA","FICHAHAACOMP","FICHADIA","FICHADIAACOMP","FICHAGES","FICHAGESACOMP",
				"FICHAHAN","FICHAHANACOMP","FICHAIDOSO","FICHAIDOSOACOMP","FICHACADFAMTEMP","PACFAMILIATEMP","FICHACRIANCATEMP",
				"FICHACRIANCAACOMPTEMP","FICHATBTEMP","FICHATBACOMPTEMP","FICHAHATEMP","FICHAHAACOMPTEMP","FICHADIATEMP",
				"FICHADIAACOMPTEMP","FICHAGESTEMP","FICHAGESACOMPTEMP","FICHAHANTEMP","FICHAHANACOMPTEMP","FICHAIDOSOTEMP",
				"FICHAIDOSOACOMPTEMP","VALIDATION","VALIDATIONTEMP","AGENTE"};
		
		for(int y=0;y<nmTabela.length;y++){
			if(driver.exists(nmTabela[y])){
				ResultSet rs = driver.executeQuery(" SELECT * FROM  "+nmTabela[y]);
				ResultSetMetaData metaData =rs.getResultSetMetaData();
				String values="";
				String valuesTemp="";		
				int numCols=metaData.getColumnCount();		
				while(rs.next()){
					for(int i=1;i<=numCols;i++){
						if(ResultSetMetaData.CHAR_TYPE==metaData.getColumnType(i)){
							valuesTemp=metaData.getColumnLabel(i)+"='"+rs.getString(i)+"'";
						}else if(ResultSetMetaData.SHORT_TYPE ==metaData.getColumnType(i)){
							valuesTemp=metaData.getColumnLabel(i)+"="+rs.getShort(i);
						}else if(ResultSetMetaData.INT_TYPE ==metaData.getColumnType(i)){
							valuesTemp=metaData.getColumnLabel(i)+"="+rs.getInt(i);
						}else if(ResultSetMetaData.LONG_TYPE ==metaData.getColumnType(i)){
							valuesTemp=metaData.getColumnLabel(i)+"="+rs.getLong(i);
						}else if(ResultSetMetaData.FLOAT_TYPE ==metaData.getColumnType(i)){
							valuesTemp=metaData.getColumnLabel(i)+"="+rs.getFloat(i);
						}else if(ResultSetMetaData.DOUBLE_TYPE ==metaData.getColumnType(i)){
							valuesTemp=metaData.getColumnLabel(i)+"="+rs.getDouble(i);
						}else if(ResultSetMetaData.CHAR_NOCASE_TYPE ==metaData.getColumnType(i)){
							valuesTemp=metaData.getColumnLabel(i)+"='"+rs.getString(i)+"'";
						}
						if(i==1){
							values=valuesTemp;
						}else{
							values=values+","+valuesTemp;
						}
					}			
					Vm.debug(nmTabela[y]+" => "+values);
				}
			}
			Vm.debug("Pdbs lidos com Sucesso!");
		}
	}
}
