package fiveDBConn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FiveDBConn {
	private Connection con;
	
	public Connection getConnection(){//접속객체를 여기만들어놓고 call하겠다.
		return con;
	}
	public FiveDBConn()//생성자 
			throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:"+
		"1521:xe","hr","hr");
	}

}
