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
		//��ü ����Ʈ ��ü ���� �ҷ�����(����)
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
			//myList 1,2,3 �ʵ� �߰�
			String myList1 = rs.getString("myList1");
			String myList2 = rs.getString("myList2");
			String myList3 = rs.getString("myList3");
			
			
			MemDBVO mem1 = new MemDBVO(id, pw, name, age, weig, heig, gen, bmr, bre, lun, din ,myList1,myList2,myList3);
			tiarray.add(mem1);
		}
		
		
		return tiarray;
	}
		
	public boolean signUp 
	//ȸ������ �޼���(����)
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
								//�Ҽ��� ù°�ڸ����� �ݿø�
			pstmt.setDouble(6, Math.round(heig*10)/10.0);
								//�Ҽ��� ù°�ڸ����� �ݿø�
			pstmt.setString(7, gen); //male
			//bmr ���� ��/�� ���� ����
			int pp= 0;
			if (gen.toUpperCase().equals("MALE")) {
				pp =(int)(Math.round(66.47+(13.75*weig)+(5*heig)-(6.76*age)));
				pstmt.setInt(8, pp);
				//������ ��� BMR
			}
			else {
				pstmt.setInt(8, (int)(Math.round(665.1 +(9.56*weig)+(1.85*heig)-(4.68*age))));
				//������ ��� BMR
			}
			//��ħ,����,���� �Ĵ�, ���ã�� �Ĵ� 1,2,3 "����"���� ����Ʈ�� �Էµ�
			/*pstmt.setString(9, "����");
			pstmt.setString(10, "����");
			pstmt.setString(11, "����");
			pstmt.setString(12, "����");
			pstmt.setString(13, "����");
			pstmt.setString(14, "����");*/
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("update Exception");
			return false;
		}
		
		return true;
	}
	
	
	

	
	public boolean update_pw (String id, String npw) {//��й�ȣ ���� �޼���(��)
		//�Ű����� 1(���� id, �� ��й�ȣ)
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
	
	public boolean update_name (String id, String nName) {//�̸� ���� �޼���(��)
		//�Ű����� 1(���� id, �� �̸�)
		
		String sql = "update member "
				+ "SET name =? "
				//+ "SET pw =?" : �н����� ���� -> �̸� ����
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
	
	public boolean update_age (String id, int nage) {//���� ���� �޼���(��)
		//�Ű����� 1(���� id, �� ����)
		String sql = "update member "
				+ "SET age =? "
				//+ "SET pw =?": �н����� ���� -> ���� ����
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
	
	public boolean update_weig (String id, double nweig) {//������ ���� �޼���(��)
		//�Ű����� 1(���� id, �� ������)
		String sql = "update member "
				+ "SET weig =? " 
				//+ "SET pw =?": �н����� ���� -> ������ ����
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
	
	public boolean update_heig (String id, double nheig) {//Ű ���� �޼���(��)
		//�Ű����� 1(���� id, �� Ű)
		String sql = "update member "
				+ "SET heig =? "
				//+ "SET pw =?": �н����� ���� -> Ű ����
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
	
	
	
	public boolean update_bre (String id, String nbre) {//��ħ�Ĵ� ����,�߰� �޼���(��)
		//�Ű����� 1(���� id, �� ��ħ �Ĵ�)
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
	
	public boolean update_lun (String id, String nlau) {//���ɽĴ� ����,�߰� �޼���(��)
		//�Ű����� 1(���� id, �� ���� �Ĵ�)
		String sql = "update member "
				+ "SET lun =? "
				//+ "SET pw =?": �н����� ���� -> ���� �Ĵ� ����
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
	
	public boolean update_din (String id, String ndin) {//����Ĵ� ����,�߰� �޼���(��)
		//�Ű����� 1(���� id, �� ����Ĵ�)
		String sql = "update member "
				+ "SET din =? "
				//+ "SET pw =?": �н����� ���� -> ���� �Ĵ� ����
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
		//���ã�� �Ĵ� �߰�,���� (����Ʈ��: "����")
		//�Ű����� (�Ĵ� ��ȣ, �Ĵ� String("1,2,3,..."), ȸ�� id
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
		//���ã�� �Ĵ� ����: ������ "����"�Էµ�
		//�Ű�����(�Ĵ� ��ȣ, ȸ�� id)
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
