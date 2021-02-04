package memDBDAO;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fiveDBConn.FiveDBConn;
import memDBVO.MemDBVO;

public class MemDBDAO {
	
	private Connection con;
	PreparedStatement pstmt = null;

	ResultSet rs = null;
	
	public MemDBDAO() throws ClassNotFoundException, SQLException {
		con = new FiveDBConn().getConnection();
	}
	
	public ArrayList <MemDBVO> getAllInfo() throws SQLException{
		//객체 리스트 전체 정보 불러오기(수정)
		ArrayList <MemDBVO> tiarray = new ArrayList<MemDBVO>();
		String sql = "SELECT * FROM member";
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		while (rs.next()) {
			String id = rs.getString("id");
			String pw = rs.getString("pw");
			String name = rs.getString("name");
			int age = rs.getInt("age");
			double weig = rs.getDouble("weig");
			double heig = rs.getDouble("heig");
			String gen = rs.getString("gen");
			int bmr = rs.getInt("bmr");
			String bre = rs.getString("bre");
			String lun = rs.getString("lun");
			String din = rs.getString("din");
			//myList 1,2,3 필드 추가
			String myList1 = rs.getString("myList1");
			String myList2 = rs.getString("myList2");
			String myList3 = rs.getString("myList3");
			
			
			MemDBVO mem1 = new MemDBVO(id, pw, name, age, weig, heig, gen, bmr, bre, lun, din ,myList1,myList2,myList3);
			tiarray.add(mem1);
		}
		
		
		return tiarray;
	}
		
	public boolean signUp 
	//회원가입 메서드(수정)
		(String id, String pw, String name, int age, double weig, double heig, String gen) {
		String sql = "insert into member (id,pw,name,age,weig,heig,gen,bmr) "
						+ "VALUES (?,?,?,?,?,?,?,?)";
		
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			pstmt.setInt(4, age);
			pstmt.setDouble(5, Math.round(weig*10)/10.0);
								//소수점 첫째자리까지 반올림
			pstmt.setDouble(6, Math.round(heig*10)/10.0);
								//소수점 첫째자리까지 반올림
			pstmt.setString(7, gen); //male
			//bmr 계산식 남/여 따로 설정
			int pp= 0;
			if (gen.toUpperCase().equals("MALE")) {
				pp =(int)(Math.round(66.47+(13.75*weig)+(5*heig)-(6.76*age)));
				pstmt.setInt(8, pp);
				//남자일 경우 BMR
			}
			else {
				pstmt.setInt(8, (int)(Math.round(665.1 +(9.56*weig)+(1.85*heig)-(4.68*age))));
				//여자일 경우 BMR
			}
			//아침,점심,저녁 식단, 즐겨찾기 식단 1,2,3 "없음"으로 디폴트값 입력됨
			/*pstmt.setString(9, "없음");
			pstmt.setString(10, "없음");
			pstmt.setString(11, "없음");
			pstmt.setString(12, "없음");
			pstmt.setString(13, "없음");
			pstmt.setString(14, "없음");*/
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("update Exception");
			return false;
		}
		
		return true;
	}
	
	
	

	
	public boolean update_pw (String id, String npw) {//비밀번호 변경 메서드(완)
		//매개변수 1(기존 id, 새 비밀번호)
		String sql = "update member "
				+ "SET pw =? "
				+ "WHERE id = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, npw);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("update Exception");
			return false;
		}
		return true;
	}
	
	public boolean update_name (String id, String nName) {//이름 변경 메서드(완)
		//매개변수 1(기존 id, 새 이름)
		
		String sql = "update member "
				+ "SET name =? "
				//+ "SET pw =?" : 패스워드 수정 -> 이름 수정
				+ "WHERE id = ? ";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nName);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("update Exception");
			return false;
		}
		return true;
	}
	
	public boolean update_age (String id, int nage) {//나이 변경 메서드(완)
		//매개변수 1(기존 id, 새 나이)
		String sql = "update member "
				+ "SET age =? "
				//+ "SET pw =?": 패스워드 수정 -> 나이 수정
				+ "WHERE id = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, nage);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("update Exception");
			return false;
		}
		return true;
	}
	
	public boolean update_weig (String id, double nweig) {//몸무게 변경 메서드(완)
		//매개변수 1(기존 id, 새 몸무게)
		String sql = "update member "
				+ "SET weig =? " 
				//+ "SET pw =?": 패스워드 수정 -> 몸무게 수정
				+ "WHERE id = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setDouble(1, nweig);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("update Exception");
			return false;
		}
		return true;
	}
	
	public boolean update_heig (String id, double nheig) {//키 변경 메서드(완)
		//매개변수 1(기존 id, 새 키)
		String sql = "update member "
				+ "SET heig =? "
				//+ "SET pw =?": 패스워드 수정 -> 키 수정
				+ "WHERE id = ? ";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setDouble(1, nheig);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("update Exception");
			return false;
		}
		return true;
	}
	
	
	
	public boolean update_bre (String id, String nbre) {//아침식단 변경,추가 메서드(완)
		//매개변수 1(기존 id, 새 아침 식단)
		String sql = "update member "
				+ "SET bre = ? "
				+ "WHERE id = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nbre);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("update Exception");
			return false;
		}
		return true;
	}
	
	public boolean update_lun (String id, String nlau) {//점심식단 변경,추가 메서드(완)
		//매개변수 1(기존 id, 새 점심 식단)
		String sql = "update member "
				+ "SET lun =? "
				//+ "SET pw =?": 패스워드 수정 -> 점심 식단 수정
				+ "WHERE id = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nlau);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("update Exception");
			return false;
		}
		return true;
	}
	
	public boolean update_din (String id, String ndin) {//저녁식단 변경,추가 메서드(완)
		//매개변수 1(기존 id, 새 저녁식단)
		String sql = "update member "
				+ "SET din =? "
				//+ "SET pw =?": 패스워드 수정 -> 저녁 식단 수정
				+ "WHERE id = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ndin);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("update Exception");
			return false;
		}
		return true;
	}
	
	
	public boolean insertMylist (int num, String dList, String id) { 
		//즐겨찾기 식단 추가,변경 (디폴트값: "없음")
		//매개변수 (식단 번호, 식단 String("1,2,3,..."), 회원 id
		String sql = "update member "
				+ "SET myList"+num+" = ? "
				+ "WHERE id = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dList);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
		}catch (SQLException e) {
			System.out.println("update Exception");
			return false;
		}
		return true;
		
	}
	public boolean deleteMylist (int num, String id) {
		//즐겨찾기 식단 삭제: 성공시 "없음"입력됨
		//매개변수(식단 번호, 회원 id)
		String sql = "UPDATE member "
				+ "SET myList"+num+" = ? "
				+ "WHERE id = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, null);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
		}catch (SQLException e) {
			System.out.println("update Exception");
			return false;
		}
		return true;
	}
	
	public boolean update_gen (String id, String ngen) {

		String sql = "update member "

				+ "SET pw =? "

				+ "WHERE id = ?";

		try {

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, ngen);

			pstmt.setString(2, id);

			pstmt.executeUpdate();

		} catch (SQLException e) {

			System.out.println("update Exception");

			return false;

		}

		return true;

	}
	public int calcBmr (String gen, double heig, double weig, int age, String id) throws SQLException {

		String sql = "UPDATE member "

				+ "SET bmr = ? "

				+ "WHERE id = ?";

		int bmr;

		if (gen.toUpperCase().equals("MALE")) {

			bmr = (int)(Math.round(66.47+(13.75*(double)weig)+(5*(double)heig)-(6.76*age)));

		}

		else {

			bmr = (int)(Math.round(665.1 +(9.56*(double)weig)+(1.85*(double)heig)-(4.68*age)));

		}

		

		pstmt = con.prepareStatement(sql);

		pstmt.setInt(1, bmr);

		pstmt.setString(2, id);

		pstmt.executeUpdate();

		

		return bmr;

	}


}
