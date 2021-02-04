package foodDB;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fiveDBConn.FiveDBConn;


public class FoodDBDAO {
	private Connection con;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public FoodDBDAO() throws ClassNotFoundException, SQLException{
		con= new FiveDBConn().getConnection();
	}
	public ArrayList<FoodDBVO> foodSelect() throws SQLException {//음식전체검색
		ArrayList<FoodDBVO> array = new ArrayList<FoodDBVO>();
		String sql = "Select * from fooddata";
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		while(rs.next()){
			int num = rs.getInt("num");
			String name = rs.getString("name");
			String gram = rs.getString("gram");
			float kcal = rs.getFloat("kcal");
			FoodDBVO fvo = new FoodDBVO(num,name,gram,kcal);
			
			array.add(fvo); 
		}
		return array;
		
	}
}
