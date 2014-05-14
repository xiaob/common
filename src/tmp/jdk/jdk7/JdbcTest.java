package tmp.jdk.jdk7;

import java.sql.SQLException;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

public class JdbcTest {

	public static void main(String[] args) throws SQLException {
		JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet();

		// 创建一个 JdbcRowSet 对象，配置数据库连接属性
		rowSet.setUrl("jdbc:mysql://127.0.0.1:3306/unicorn?useUnicode=true&characterEncoding=utf-8&autoReconnect=true");
		rowSet.setUsername("root");
		rowSet.setPassword("123456");

		rowSet.setCommand("select * from account");
		rowSet.execute();
		
		while(rowSet.next()){
			System.out.println(rowSet.getLong("account_id") + ", " + rowSet.getString("username"));
		}
		rowSet.close();
	}

}
