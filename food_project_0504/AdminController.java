package food_project_0504;

// 사용자의 요청(request)을 어떻게 처리할지 결정하는 파트
//즉, Controller에 정의 된 기준대로 요청을 처리

import java.sql.SQLException;
import java.util.Scanner;

public class AdminController {
	static AdminDAO adminDAO;
	
//외부에서 호출 시 new를 사용하지 않고 getInstance를 사용하여 객체를 생성하거나 반환받음.
//싱글톤 하나의 인스턴스를 가지고 공유해서 쓴다	
public AdminController() {
	AdminDAO.getInstance();  
}

//관리자메뉴 case 1: 비밀번호 리스트

public void ListPasswordProcess() throws SQLException{	 
	adminDAO = AdminDAO.getInstance();
	while(true) {
	String password=null; 

	System.out.println("비밀번호를 입력하세요: ");
	Scanner sc = new Scanner(System.in);
	password = sc.nextLine();
	if(adminDAO.ListPassword(password)) 
		System.out.println("로그인 성공했습니다");

	else if(!(adminDAO.ListPassword(password)))
		System.out.println("없는 비밀번호 입니다");
		break;
	}
	return;
}//end ListPasswordProcess()

//관리자메뉴 case 1: 비밀번호 변경한것 (admin테이블에 들어감) 
	public void  ChangePasswordProcess() throws SQLException{	 
		Scanner sc = new Scanner(System.in);
		System.out.println("변경하실 새 비밀번호를 입력하세요:");
		String password = sc.nextLine(); 
		
		int exe = adminDAO.ChangePassword(password); 	
		if (exe == 1) {
			System.out.println("비밀번호가 재설정 되었습니다");
			System.out.println("다시 로그인하세요");
		}else 
			System.out.println("비밀번호 재설정 실패했습니다");
		
	}//end ChangePasswordProcess()
	
}//end class
