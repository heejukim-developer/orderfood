package food_project_0504;

//DTO(Data Transfer Object)는 계층 간 데이터 교환을 하기 위해 사용하는 객체로, 
//DTO는 로직을 가지지 않는 순수한 데이터 객체(getter & setter 만 가진 클래스)

public class AdminDTO {
private String password;

public AdminDTO(){}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}


}//end class()
