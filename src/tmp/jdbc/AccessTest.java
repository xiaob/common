package tmp.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccessTest {

	public static void main(String[] args)throws Exception {
		String accessFile = "D:/workspace/yuan2/yuan-common/src/resources/test.accdb";
		Connection conn = getConnection(accessFile, null, null);

		test1(conn);
		test2(conn);
		
		conn.close();
	}
	
	public static Connection getConnection(String accessFile, String user, String password) throws SQLException{
		return DriverManager.getConnection("jdbc:odbc:Driver={MicroSoft Access Driver (*.mdb, *.accdb)};DBQ="+accessFile, user, password);
	}
	
	public static void test1(Connection conn) throws SQLException{
		final String sql = "insert into student(name, age) values('yuan', 100)";
		conn.createStatement().executeUpdate(sql);
	}
	
	public static void test2(Connection conn) throws SQLException{
		final String sql = "select * from student";
		ResultSet rs = conn.createStatement().executeQuery(sql);
		while(rs.next()){
			System.out.println(rs.getString("name") + ", " + rs.getInt("age"));
		}
	}
	
}
