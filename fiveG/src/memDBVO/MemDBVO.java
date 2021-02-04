package memDBVO;

import java.sql.Array;

public class MemDBVO {
	
	private String id;
	private String pw;
	private String name;
	private int age;
	private double weig;
	private double heig;
	private String gen;
	private String bre;
	private String lun;
	private String din;
	private int bmr;
	
	//나만의 식단 필드 3개 추가
	private String myList1;
	private String myList2;
	private String myList3;
	



	public String getMyList1() {
		return myList1;
	}

	public void setMyList1(String myList1) {
		this.myList1 = myList1;
	}

	public String getMyList2() {
		return myList2;
	}

	public void setMyList2(String myList2) {
		this.myList2 = myList2;
	}

	public String getMyList3() {
		return myList3;
	}

	public void setMyList3(String myList3) {
		this.myList3 = myList3;
	}

	public String getBre() {
		return bre;
	}

	public void setBre(String bre) {
		this.bre = bre;
	}

	public String getLun() {
		return lun;
	}

	public void setLun(String lun) {
		this.lun = lun;
	}

	public String getDin() {
		return din;
	}

	public void setDin(String din) {
		this.din = din;
	}

	public MemDBVO() {
	}

	public MemDBVO(String id, String pw, String name, int age, double weig,
			double heig, String gen, int bmr, String bre, String lun, String din, String myList1, String myList2, String myList3) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.age = age;
		this.weig = weig;
		this.heig = heig;
		this.gen = gen;
		this.bmr = bmr;
		this.bre = bre;
		this.lun = lun;
		this.din = din;
		this.myList1 = myList1;
		this.myList2 = myList2;
		this.myList3 = myList3;
		//식단 즐겨찾기 생성자 추가
		
	}
	

	
	
	public int getBmr() {
		return bmr;
	}

	public void setBmr(int bmr) {
		this.bmr = bmr;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;//테스트용
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public double getWeig() {
		return weig;
	}
	public void setWeig(double weig) {
		this.weig = weig;
	}
	public double getHeig() {
		return heig;
	}
	public void setHeig(double heig) {
		this.heig = heig;
	}
	public String getGen() {
		return gen;
	}
	public void setGen(String gen) {
		this.gen = gen;
	}
	
	


}
