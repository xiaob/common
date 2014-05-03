package tmp.jdbc;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DbUtilsTest {

	public static void main(String[] args) throws SQLException {
		String sql = "select dept_no as deptNo, dept_name as deptName from departments limit 10";
//		int money = queryScalar(sql, Integer.class);
//		System.out.println(money);
		List<Dept> list = queryList(sql, Dept.class);
		for(Dept dept : list){
			System.out.println(dept);
		}
	}
	
	private static MysqlDataSource dataSource;
	static{
		dataSource = new MysqlDataSource();
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/employees?useUnicode=true&characterEncoding=utf-8");
		dataSource.setUser("root");
		dataSource.setPassword("");
	}
	public static <T> T queryBean(String sql, Class<T> clazz, Object... params) throws SQLException{
		return new QueryRunner(dataSource).query(sql, new BeanHandler<T>(clazz), params);
	}
	
	public static <T> List<T> queryList(String sql, Class<T> clazz, Object... params) throws SQLException{
		return new QueryRunner(dataSource).query(sql, new BeanListHandler<T>(clazz), params);
	}
	
	public static <T> T queryScalar(String sql, Class<T> clazz, Object... params)throws SQLException{
		return new QueryRunner(dataSource).query(sql, new ScalarHandler<T>(), params);
	}

}
