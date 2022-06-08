package food_project_0504;
//메인 메뉴 클래스 AbstractMenu의 자식 
//Menu인터페이스를 구현하는 추상클래스인 AbstractMenu를 상속받는다 

import java.sql.SQLException;

class MainMenu extends AbstractMenu{
	
	private static final MainMenu instance = new MainMenu(null); //자기자신의 객체 생성 
    private static final String MAIN_MENU_TEXT = //기본문구 
    		"1.사용자메뉴\n"+
            "2.관리자메뉴\n"+
    		"q.종료\n\n"+
            "선택하세요: ";
    
//외부에 노출되지 않는 생성자 오로지 클래스 변수 초기화를 위해 단한번 생성
private MainMenu(Menu prevMenu){
	super(MAIN_MENU_TEXT, prevMenu); //부모생성자 호출 
}

//메뉴 객체 반환 
public static MainMenu getInstance() {
	return instance;            
}

//메뉴 인터페이스의 next메소드 재정의한것 

public Menu next() throws SQLException {
	AdminController aController = new AdminController();
	AdminDAO adminDAO;
	
switch(scanner.nextLine()) {
	
	//사용자메뉴로 이동하는 기능 추가 
	case "1":
		 UserMenu userMenu = UserMenu.getInstance(); //1번 누르면 사용자 객체 가져온다 
		 userMenu.setPrevMenu(this); //메인메뉴를 이전메뉴로 반환 
		 return userMenu; //사용자 객체 반환 
	
	//관리자메뉴로 이동하는 기능 추가 
	case "2":
		//비밀번호가 테이블에 있는지 확인 
		 aController.ListPasswordProcess();
		 AdminMenu adminMenu = AdminMenu.getInstance(); //비밀번호 통과되면 관리자 객체 가져온다 
		 adminMenu.setPrevMenu(this); //메인메뉴를 이전메뉴로 반환 
		 return adminMenu; //관리자 메뉴 객체를 반환 
	
	
	case "q" : return prevMenu; //q 입력시 5행의 prevMenu null을 반환 그래서 종료 
	default: return this; //그외 입력은 메인 메뉴 this로 돌아감 
	}//end switch

}//end menu

  //관리자 메뉴 진입전 비밀번호를 인증, db에서 가져오기전 표면적으로 수행한 것 
	//private boolean checkAdminPassword() {
	//	System.out.println("관리자번호를 입력하세요:");
	//    return "a1234".equals(scanner.nextLine()); 
	//}//checkAdminPassword()


}//end class()