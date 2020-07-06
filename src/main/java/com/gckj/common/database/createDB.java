package com.gckj.common.database;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;



public class createDB {
	MySql session;
	public static String tempVersion = null;
	public static int runState = 0;
	public static String sqlPath = System.getProperty("user.dir")+"/doc/sql/";
	
	public createDB() {
		session = new MySql();
		session.init();
	}
	
	private static StringBuffer getFileBuffer(String fileName) {
		StringBuffer sBuffer = new StringBuffer();
		return sBuffer;
	}
	
//	private static void  setSql(String path) throws Exception{
//		File file = new File(path);
//		
//		File [] fs = file.listFiles();
//		
//		//如果下面有子目录则循环子目录
//		for (int i = 0; i < fs.length; i++) {
//			if (!fs[i].isHidden() && fs[i].isDirectory()) {
//				setSql(fs[i].getPath());
//			}
//		}
//		
//		String[] farrya = file.list(new FilenameFilter(){
//
//			@Override
//			public boolean accept(File dir, String name) {
//				// TODO Auto-generated method stub
//				
//				String [] as = name.split("-");
//				
//				if (as.length != 3 && name.endsWith("sql")) {
//					return true;
//				}else {
//					return false;
//				}
//			}
//			
//		});
//		
//		Vector<String> vector = new Vector();
//		
//		Hashtable hb = new Hashtable();
//		
//		int [] it = new int[farrya.length];
//		
//		//排序
//		for (int i = 0; i < farrya.length; i++) {
//			
//			try {
//				it[i] = Integer.parseInt(farrya[i].substring(0,2));
//			} catch (NumberFormatException e) {
//				// TODO Auto-generated catch block
//				//e.printStackTrace();
//				vector.add(farrya[i]);
//				continue;
//			}
//			hb.put(it[i], farrya[i]);
//		}
//		
//		Arrays.sort(it);
//
//		for (int i = 0; i < it.length; i++) {
//			int j = it[i];
//			if (hb.get(it[i]) == null) {
//				continue;
//			}
//			
//			System.out.println("导入: "+path+"\\"+hb.get(it[i]));
//			
//			importSql(path,hb.get(it[i]).toString());
//			
//		}
//		
//		for (int i = 0; i < vector.size(); i++) {
//			importSql(path,vector.get(i));
//		}
//		
//	}
	
	public boolean checkArrayById(Integer[] vs,int id) {
		boolean bl = false;
		for (int i = 0; i < vs.length; i++) {
			if (vs[i] == id) {
				bl = true;
				break;
			}
		}
		return bl;
	}
	
	/**mybatis导入
	 * @param path
	 * @param fileName
	 * @throws Exception
	 */
	/*
	public  void importSql(String path,String fileName) throws Exception{
		
		if (runState == 1) {
			printSql(new File(path+"\\"+fileName));
			return;
		}
		
		System.out.println("导入: "+path+"\\"+fileName);
		
		ScriptRunner runner = new ScriptRunner(session.getConnection());
		runner.setAutoCommit(true);
		runner.setFullLineDelimiter(true);
		runner.setSendFullScript(true);
		
//		runner.setStopOnError(false);
		
		try {
			runner.runScript(new InputStreamReader(new FileInputStream(path+"\\"+fileName),"UTF-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(fileName);
			e.printStackTrace();
			throw new Exception(fileName+"导入失败");
		}
		
//		runner.closeConnection();
		
		//conn.close();
		
	}
	
	*/
	
	/**读取导入sql的第一行
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static String getFileByNumber(String path,String fileName) {
		String text = null;
		try {
			BufferedReader in = new BufferedReader(new FileReader(path+"\\"+fileName));
			
			text = in.readLine();
			
			in.close();
		} catch (Exception e) {
			System.out.println(fileName+"读取错误");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (text == null) {
			text = "";
		}
		return text;
	}
	
	/**调用mysqldump命令导入脚本
	 * @param path
	 * @param fileName
	 */
	public void importByMysqlDump(String path,String fileName) {
		
		String tempPath;
		
		File temDir = null;
		
		if (!MySql.mysqlPath.equals("")) {
			tempPath = "mysql";
			temDir = new File(MySql.mysqlPath);
		}else {
			tempPath = "mysql";
		}
		
//		String  [] cmd = {"cmd","/c",tempPath,"-u",MySql.DBUser,"-p",MySql.DBPS,MySql.DBName,"<","\""+path+"\\"+fileName+"\""};
		
//		String cmd = tempPath+" -u"+MySql.DBUser+" -p"+MySql.DBPS+" "+MySql.DBName+" < "+path+"\\"+fileName;
//		System.out.println(cmd);
		
		
		
		int exitCode = 1;
		try {
			Process process = Runtime.getRuntime().exec("cmd /c "+tempPath+" -h"+MySql.dbIp+" -u"+MySql.DBUser+" -p"+MySql.DBPS+" "+MySql.DBName+" < \""+path+fileName+"\"",null,temDir);
//			Process process = Runtime.getRuntime().exec(cmd);
			
			exitCode = process.waitFor();
			
//			process.getInputStream();
			
			InputStream inputStream = process.getInputStream();
			try {
	            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

	            String line;
	            while ((line = reader.readLine()) != null) {
	                System.out.println(line);
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {

	            try {
	                inputStream.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (exitCode == 0) {
            System.out.println(fileName+"导入成功");
        } else {
            System.err.println(fileName+"导入失败");
        }
		
	}
	
	
	public void importByAnt(String path,String fileName) throws Exception{
		SQLExec sqlExec = new SQLExec();
		
		sqlExec.setDriver(MySql.Driver);

        sqlExec.setUrl(MySql.SqlUrl);

        sqlExec.setUserid(MySql.DBUser);

        sqlExec.setPassword(MySql.DBPS);
        
        sqlExec.setAutocommit(false);
        sqlExec.setKeepformat(false);
        
		sqlExec.setEncoding("utf-8");
		
//		DelimiterType dt = new DelimiterType();
//		
//		dt.setValue(SQLExec.DelimiterType.NORMAL); 
//		sqlExec.setDelimiterType(dt);
		
//		sqlExec.setDelimiter("\\");
		sqlExec.setSrc(new File(path+"\\"+fileName));
		sqlExec.setPrint(true); //设置是否输出
		//输出到文件 sql.out 中；不设置该属性，默认输出到控制台
		// sqlExec.setOutput(new File("d:/script/sql.out"));
		sqlExec.setProject(new Project()); 
		
		try {
			sqlExec.setEncoding("utf-8");
			sqlExec.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(fileName);
			e.printStackTrace();
			throw new Exception("导入失败");
		}
	}
	
	//ant导入
	public  void importSql(String path,String fileName) throws Exception{
		
		if (runState == 1) {
			printSql(new File(path+"\\"+fileName));
			return;
		}
		
		System.out.println("导入: "+path+"\\"+fileName);
		
		//取出文件第一行
		if (getFileByNumber(path,fileName).equals("/*mysql=true*/")) {
			importByMysqlDump(path, fileName);
		}else {
			importByAnt(path,fileName);
		}
		
	}
	
	private static void printSql(File sqlFile)throws Exception {
		
		BufferedReader bis = new BufferedReader(new InputStreamReader(new FileInputStream(sqlFile),"utf-8"));
		
		int ib = 0;
		
		char [] c = new char[512];
		
		while ((ib = bis.read(c)) != -1) {
			System.out.print(new String(c,0,ib));
		}
		System.out.println("\n");
		bis.close();
	}
	
	public static void printSql(String sql) {
		System.out.println(sql+";");
	}
	
	public void run() throws Exception{
		run(0);
	}
	
	/**
	 * @param args
	 */
	public void run(int ib) throws Exception{
		// TODO Auto-generated method stub
		createDB.runState = ib;
		//检查是否存在数据库
		String sql = "SELECT SCHEMA_NAME FROM SCHEMATA where SCHEMA_NAME='"+MySql.DBName+"'";
		
		session.executeUpdate("use information_schema");
		
		List os = session.getResult(sql);
		if (os.size() == 0) {
			sql = "CREATE DATABASE "+MySql.DBName+" DEFAULT CHARACTER SET utf8";
			session.executeUpdate(sql);
		}
		
		session.executeUpdate("use "+MySql.DBName);
		
		String path = sqlPath;
		
		//获取数据库版本
		Integer [] vs = getDbVersion();
		
		if (vs == null) {
			//数据库版本从1开始
			vs = new Integer[]{0};
		}
		
//		String versionString = "";
		
		Vector<Integer> versionV = new Vector<Integer>();
		
		//获取所有符合条件的sql文件
		
		File file = new File(path);
		
		File[] files = file.listFiles(new FilenameFilter(){

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				
//				String [] as = name.split("-");
//				
//				if (as.length == 3 && name.endsWith("sql")) {
//					return true;
//				}else {
//					return false;
//				}
				
				return name.endsWith("sql");
			}
			
		});
		
		//排序
		
		Hashtable hb = new Hashtable();
		
		Hashtable<Integer,File> hbFile = new Hashtable();
		
		if (files == null) {
			System.out.println("没有要导入的sql文件");
			System.exit(0);
		}
		
		int [] it = new int[files.length];
		
		//排序
		for (int i = 0; i < files.length; i++) {
			String [] as = files[i].getName().split("-");
			try {
				it[i] = Integer.parseInt(as[0]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				continue;
			}
			hb.put(it[i], files[i].getName());
			hbFile.put(it[i], files[i]);
		}
		
		Arrays.sort(it);
		
		
		//与数据库版本比较是否需要执行
		//导入100以后的
//		for (int i = 0; i < it.length; i++) {
//			
//			
//			
//			if (!checkArrayById(vs,it[i])) {
//				
//				String fileName = hbFile.get(it[i]).getName();
//				
//				if (Integer.parseInt(fileName.split("-")[0]) <100) {//只导入大于100的
//					continue;
//				}
//				
//				//不在数组里,则导入该sql
//				importSql(path, hb.get(it[i]).toString());
//				
//				//修改文件名称
//				
//				if (Integer.parseInt(fileName.split("-")[0]) > 100) {
//					
//					if (fileName.indexOf("true") < 0) {
//						File tFile = new File(hbFile.get(it[i]).getParent()+"/"+fileName.substring(0,fileName.lastIndexOf("."))+",true.sql");
////						System.out.println(tFile);
//						hbFile.get(it[i]).renameTo(tFile);
//					}
//					
//				}
//				
//			}
//			if (it[i] > 100) {//小于100的是系统保留表,每次都需要重新导入
//				if (!versionV.contains(it[i])) {
//					versionV.add(it[i]);
//				}
////				versionString = versionString + it[i] + "@";
//			}
//		}
		
		for (int i = 0; i < it.length; i++) {
			
			if (!checkArrayById(vs,it[i])) {
				
				String fileName = hbFile.get(it[i]).getName();
				
				//不在数组里,则导入该sql
				importSql(path, hb.get(it[i]).toString());
				
				//修改文件名称
				
					
				if (fileName.indexOf("true") < 0) {
					File tFile = new File(hbFile.get(it[i]).getParent()+"/"+fileName.substring(0,fileName.lastIndexOf("."))+",true.sql");
//						System.out.println(tFile);
					hbFile.get(it[i]).renameTo(tFile);
				}
				
			}
			
			if (!versionV.contains(it[i])) {
				versionV.add(it[i]);
			}
		}
		
		
		//更改数据库版本
		if (versionV.size() > 0) {
			
			String versionString = "";
			
			Integer [] ica = versionV.toArray(new Integer[0]);
			
			Arrays.sort(ica);
			
			for (int i = 0; i < ica.length; i++) {
				versionString = versionString + ica[i] + "@";
			}
			
			sql = "select * from "+MySql.versionName;
			
			List objects = session.getResult(sql);
			
			if (objects.size() > 0) {
				sql = "update "+MySql.versionName + " set vs='"+versionString+"'";
			}else {
				sql = "insert into "+MySql.versionName+" (vs) values('"+versionString+"')";
			}
			
			session.executeUpdate(sql);
		}
		
		System.out.println("完成");
	}
	
	public Integer [] getDbVersion(){
		
		String sql = "SHOW TABLES LIKE '"+MySql.versionName+"'";
		List isDb = session.getResult(sql);
		
		Integer [] version = null;
		
		if (isDb.size() == 0) {
			//创建表
			sql = "CREATE TABLE "+MySql.versionName+" (`vs`  text)";
			
			session.executeUpdate(sql);
			
		}else {
			//取得版本
			
			sql = "select * from "+MySql.versionName;
			
			List objects = session.getResult(sql);
			
			String tv = createDB.tempVersion;
			
			if (objects.size() > 0 || tv != null) {
				
				if (tv == null) {
					tv = ((Hashtable)objects.get(0)).get("vs").toString();
				}
				
				if (tv.indexOf("@") > 0) {
					
					String [] as = tv.split("@");
					
					version = new Integer[as.length];
					for (int i = 0; i < as.length; i++) {
						version[i] = new Integer(as[i]);
					}
					
//					version = Arrays.asList().toArray(new Integer[0]);
				}else {
					
					if (tv.equals("")) {
						version = null;
					}else {
						version = new Integer[]{Integer.parseInt(tv)};
					}
				}
			}
		}
		
		return version;
	}
	
	public void showUpdateSql(String version) throws Exception{
		tempVersion = version;
		run(1);
	}

}
