package foodDB;



public class FoodDBVO {//»ý¼ºÀÚ and getset
	private int num;
	private String name;
	private String gram;
	private float kcal;
	public FoodDBVO() {}
	public FoodDBVO(int num,String name, String gram, float kcal) {
		super();
		this.num = num;
		this.name = name;
		this.gram = gram;
		this.kcal = kcal;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGram() {
		return gram;
	}
	public void setGram(String gram) {
		this.gram = gram;
	}
	public float getKcal() {
		return kcal;
	}
	public void setKcal(float kcal) {
		this.kcal = kcal;
	}
	

	
}
