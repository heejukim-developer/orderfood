package food_project_0504;

//주문 테이블 데이터 저장 
public class OrderDTO {
	private int serial;
	private String orderdate;
	private String code;
	private int count;
	private MenuDTO menuDTO; //menu dto가져오기 조인하기 

public OrderDTO() {
	
}

public int getSerial() {
	return serial;
}

public void setSerial(int serial) {
	this.serial = serial;
}

public String getOrderdate() {
	return orderdate;
}

public void setOrderdate(String orderdate) {
	this.orderdate = orderdate;
}

public String getCode() {
	return code;
}

public void setCode(String code) {
	this.code = code;
}

public int getCount() {
	return count;
}

public void setCount(int count) {
	this.count = count;
}

@Override
public String toString() {
	
	return String.format("%d %s %s %d",
			serial,orderdate,code,count);
}

///menu랑 조인하기 
public MenuDTO getMenuDTO() {
	return menuDTO;
}
public void setMenuDTO(MenuDTO menuDTO) {
	this.menuDTO = menuDTO;
}

}//end class()