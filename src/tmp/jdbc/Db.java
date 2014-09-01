package tmp.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;

import com.alibaba.druid.pool.DruidDataSource;

public class Db {

	/**
	 * 获取access 连接对象
	 * @param accessFile
	 * @param user
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public static Connection accessConnection(String accessFile, String user, String password) throws SQLException{
		return DriverManager.getConnection("jdbc:odbc:Driver={MicroSoft Access Driver (*.mdb, *.accdb)};DBQ="+accessFile, user, password);
	}
	
	/**
	 * 获取mysql连接
	 * @param host
	 * @param port
	 * @param db
	 * @param user
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public static Connection mysqlConnection(String host, int port, String db, String user, String password) throws SQLException{
		String url="jdbc:mysql://{0}:{1}/{2}?useUnicode=true&characterEncoding=utf-8&autoReconnect=true";
		
		return DriverManager.getConnection(MessageFormat.format(url, host, String.valueOf(port), db), user, password);
	}

	
	/**
	 * 获取数据源
	 * @param url
	 * @param user
	 * @param password
	 * @param minActive
	 * @param maxActive
	 * @return
	 * @throws SQLException
	 */
	public static DruidDataSource dataSource(String url, String user, String password, int minActive, int maxActive) throws SQLException{
		DruidDataSource ds = new DruidDataSource();
		ds.setUrl(url);
		ds.setUsername(user);
		ds.setPassword(password);
		ds.setMaxActive(maxActive);
		ds.setValidationQuery("select 1");
		ds.setTestWhileIdle(true);
		
		ds.setInitialSize(minActive);
		ds.init();
		
		return ds;
	}
}
