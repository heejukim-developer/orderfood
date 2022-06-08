package food_project_0504;

public class PayDTO {
private int serial;
private String orderdate;
private int totalprice;

private OrderDTO orderDTO;
private MenuDTO menuDTO;

public PayDTO() {
	
}
public PayDTO(int serial, String orderdate, int totalprice) {
	
	this.serial = serial;
	this.orderdate = orderdate;
	this.totalprice = totalprice;
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

public int getTotalprice() {
	return totalprice;
}

public void setTotalprice(int totalprice) {
	this.totalprice = totalprice;
}
public OrderDTO getOrderDTO() {
	return orderDTO;
}
public void setOrderDTO(OrderDTO orderDTO) {
	this.orderDTO = orderDTO;
}
public MenuDTO getMenuDTO() {
	return menuDTO;
}
public void setMenuDTO(MenuDTO menuDTO) {
	this.menuDTO = menuDTO;
}


}//end class