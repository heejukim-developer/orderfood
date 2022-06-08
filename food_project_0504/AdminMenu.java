package food_project_0504;
//관리자메뉴

import java.sql.SQLException;
import java.util.List;

//AbstractMenu의 자식 
public class AdminMenu extends AbstractMenu{
	
private static final AdminMenu instance = new AdminMenu(null); //자기자신의 객체 생성 
private static final String ADMIN_MENU_TEXT =
			"1.비밀번호 변경\n"+
			"2.음식설정\n"+
			"3.품절설정\n" +
			"4.결산설정\n"+
			"5.처음으로\n\n"+
			"선택하세요:";

private AdminMenu(Menu prevMenu) {
	super(ADMIN_MENU_TEXT, prevMenu); // 부모 생성자 호출 
}
public static AdminMenu getInstance() {
	return instance; 				//메뉴 객체를 반환 
}
//menu인터페이스의 next메소드 재정의 
public Menu next() throws SQLException {
	MenuController mController = new MenuController();
	AdminController aController = new AdminController();
	PayController pController = new PayController();
	
 switch (scanner.nextLine()) {
	
	case "1" : //비밀번호 변경 
		
		//aController.ListPasswordProcess();//비밀번호 리스트 확인한 프로세스 넣기 
		aController.ChangePasswordProcess();
		return this; 	
		
	case "2": //음식 설정 
		mController.InputMenuTableProcess();
		return this;  
		
	case "3" : // 현재 음식 테이블 보여주기 
		List<MenuDTO> aList = mController.MenuAllProcess();
		for(MenuDTO dto : aList) 
			System.out.printf("%s %s %d원\n",
					dto.getCode(),
					dto.getName(),
					dto.getPrice());
		mController.SoldOutProcess();//품절 문구 넣기 
		return this;
		
	case "4" ://결산 설정 
		pController.showPayProcess();
		return this;
	
	case "5":
		return prevMenu;
	default:
		return this;
	}//end switch
 
}//end Menu next()

public void password() {
	System.out.print("비밀번호를 입력하세요:");
	}
}//end class()
