package food_project_0504;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class MenuController {
	MenuDAO menuDAO;
	Scanner sc = new Scanner(System.in);
	
	public MenuController() {
		menuDAO = MenuDAO.getInstance();
	}

	
// 관리자메뉴 case 2: inputMenuTableProcess() 음식 설정 추가하기  // 자세한건 DAO에 있다 
	public List<MenuDTO> InputMenuTableProcess() throws SQLException{	 
		return menuDAO.InputMenuTable(); 	
	}
	
// 관리자메뉴 case 3: MenuAllProcess() 음식 현재 상황 재고조회 // 자세한건 DAO에 있다 	
	public List<MenuDTO> MenuAllProcess() throws SQLException{	 
		return menuDAO.MenuAll(); 	
	}
// 관리자메뉴 case 3.5: SoldOutProcess() 품절문구 띄우기  // 자세한건 DAO에 있다 	

	public void  SoldOutProcess() throws SQLException{	 
		Scanner sc = new Scanner(System.in);
		System.out.println("품절 음식을 선택해주세요:");
		String name = sc.nextLine(); 
		
		int exe = menuDAO.SoldOut(name); 	
		if (exe == 1)
			System.out.println("품절 설정 완료했습니다");
		else 
			System.out.println("품절 설정 실패했습니다");
		
	}//end SoldOutProcess()

}//end class()