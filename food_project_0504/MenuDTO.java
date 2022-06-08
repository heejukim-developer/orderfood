package food_project_0504;

//메뉴 데이터 저장하는
public class MenuDTO {

	private String code;
	private String name;
	private int price;
	private String soldout;
	
	public MenuDTO() {}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	public String getSoldout() {
		return soldout;
	}

	public void setSoldout(String soldout) {
		this.soldout = soldout;
	}

	@Override
	public String toString() {
		
		return String.format("%s %s %d",
				code,name,price);
	}

}//end class
