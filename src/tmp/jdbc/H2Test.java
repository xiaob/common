package tmp.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.h2.jdbcx.JdbcDataSource;

public class H2Test {

	public static void main(String[] args) throws Exception {
//		DataSource ds = getDataSource("root", "123456");
//		Connection conn = getDataSource("root", "123456").getConnection();

//		test1(ds);
//		test2(conn);
//		test3(ds);
		
		Properties p = System.getProperties();
		p.list(System.out);
	}
	
	public static DataSource getDataSource(String user, String password){
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL("jdbc:h2:˜/test;MVCC=TRUE;MODE=MYSQL");
		dataSource.setUser(user);
		dataSource.setPassword(password);
		
		return dataSource;
	}
	
	public static void test1(DataSource dataSource) throws SQLException{
		final String sql = "insert into student(name, age) values('zhang', 22)";
		new QueryRunner(dataSource).update(sql);
	}
	
	public static void test2(Connection conn) throws SQLException{
		final String sql = "select * from student";
		ResultSet rs = conn.createStatement().executeQuery(sql);
		while(rs.next()){
			System.out.println(rs.getString("name") + ", " + rs.getInt("age"));
		}
	} 
	
	public static void test3(DataSource dataSource) throws SQLException{
		final String sql = "select * from student";
		List<Student> list = new QueryRunner(dataSource).query(sql, new BeanListHandler<Student>(Student.class));
		for(Student student : list){
			System.out.println(student.getName() + ", " + student.getAge());
		}
	}

}
