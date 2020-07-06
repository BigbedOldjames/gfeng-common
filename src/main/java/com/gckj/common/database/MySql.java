package com.gckj.common.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


public class MySql { 
	public static String dbIp;
	public static String Driver;
	public static String SqlUrl;
	public static String DBName;
	public static String DBUser;
	public static String DBPS;
	public static String mysqlPath = "";
	public static String versionName = "system_db_version";
	private static Connection con = null;
	private Statement stmt = null;
	public static String GBK = "GB2312";
	
	public static String tableName = null;
	
	public static void setUrl(String ip,String port,String dbName){
		MySql.DBName = dbName;
		MySql.dbIp = ip;
		MySql.SqlUrl = "jdbc:mysql://"+ip+":"+port+"/"+dbName+"?characterEncoding=GBK";
	}
	
	public void init(){
		try {
			if (con != null) {
				con.close();
				con = null;
			}
			if (con == null) {
				Class.forName(Driver);
				con=DriverManager.getConnection(SqlUrl,DBUser,DBPS);
			}
		}catch(Exception e){
			System.out.println("取得连接错误:");
			e.printStackTrace();
		}
	}
	
	public MySql(){
		
	}
	
//	public static void main(String[] args) {
//		MySql ms = new MySql();
//
//		String sql = "select * from "+tableName;
//
//		List ho = ms.getResult(sql);
//
//		System.out.println(ho.size());
//	}
	
	/*
	 *取得连接 
	 * 
	 * */
	public Connection getConnection(){
		
//		try{
//			Class.forName(Driver);
//			con=DriverManager.getConnection(SqlUrl,DBName,DBPS);
//			stmt = con.createStatement();
//		}catch(Exception e){
//			System.out.println("取得连接错误:");
//			e.printStackTrace();
//		}
		return con;
	}
	
	/**
	 * 查询数据
	 * */
	public List<Map<String, Object>> getResult(String sql){
		stmt = null;
		ResultSet rs = null;
		List list = new ArrayList();
		try{
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				Hashtable table = new Hashtable();
				ResultSetMetaData rsmd = rs.getMetaData();//取得结果集信息
				int columnCount = rsmd.getColumnCount()+1;
				for (int i = 1; i < columnCount; i++) {
					
					switch (rsmd.getColumnType(i)) {
					case java.sql.Types.LONGVARCHAR:
						table.put(rsmd.getColumnLabel(i), rs.getString(i));
						break;
					case java.sql.Types.VARCHAR:
						table.put(rsmd.getColumnLabel(i), rs.getString(i));
						break;
					case java.sql.Types.DATE:
						table.put(rsmd.getColumnLabel(i), getDate(rs, rsmd.getColumnName(i)));
						break;
					case java.sql.Types.DOUBLE:
						table.put(rsmd.getColumnLabel(i), rs.getDouble(i));
						break;
					case java.sql.Types.INTEGER:
						table.put(rsmd.getColumnLabel(i), rs.getInt(i));
						break;
					case java.sql.Types.FLOAT:
						table.put(rsmd.getColumnLabel(i), rs.getFloat(i));
						break;
					case java.sql.Types.DECIMAL:
						table.put(rsmd.getColumnLabel(i), rs.getInt(i));
						break;
					case java.sql.Types.BIGINT:
						table.put(rsmd.getColumnLabel(i), rs.getLong(i));
						break;
					case java.sql.Types.TIMESTAMP:
						table.put(rsmd.getColumnLabel(i), getDate(rs, rsmd.getColumnName(i)));
						break;
					case java.sql.Types.NUMERIC:
						table.put(rsmd.getColumnLabel(i), rs.getDouble(i));
						break;
					default:
						System.out.println(rsmd.getColumnLabel(i)+"未知类型"+rsmd.getColumnType(i));
					}
				}
				
				list.add(table);
			}
			
			
			Cnclose();
			
			return list;
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(this.getClass().getMethods()+"执行SQL错误: "+sql+" :"+e.getStackTrace());
		}
		return list;
	}
	
	/**
	 * update数据
	 * 
	 * */
	
	public int executeUpdate(String sql){
		int i = -1;
		stmt = null;
		try{
			stmt = con.createStatement();
			if (createDB.runState == 1) {
				createDB.printSql(sql);
				if (sql.indexOf("use") == 0) {
					i = stmt.executeUpdate(sql);
				}
			}else {
				i = stmt.executeUpdate(sql);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			Cnclose();
		}
		return i;
	}
	
	public int save(String sql){
		int i = -1;
		stmt = null;
		try{
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
			ResultSet resultset = stmt.getGeneratedKeys();
            if (resultset != null && resultset.next()) {
            	i = (int)resultset.getLong(1);//返回插入的主键id
            }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			Cnclose();
		}
		return i;
	}
	
	public void Cnclose(){
		try{
			if(this.stmt != null)
				this.stmt.close();
			this.stmt = null;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static Date getDate(ResultSet rs, String colName)throws SQLException {
		Timestamp timeStamp = rs.getTimestamp(colName);
		if (timeStamp != null)
			return new Date(timeStamp.getTime());
		
		return null;
	}

}
