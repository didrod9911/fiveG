package fiveDBConn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FiveDBConn {
	private Connection con;
	
	public Connection getConnection(){//���Ӱ�ü�� ���⸸������ call�ϰڴ�.
		return con;
	}
	public FiveDBConn()//������ 
			throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:"+
		"1521:xe","hr","hr");
	}

}
