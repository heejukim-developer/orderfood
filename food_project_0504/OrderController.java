package food_project_0504;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class OrderController {
	OrderDAO orderDAO;
	public OrderController() {
		orderDAO = OrderDAO.getInstance();
	}
	
/////user case 1 : SearchMenuProcess() 	이름검색하면 출력할수 있도록 
	public List<MenuDTO> SearchMenuProcess(String data) throws SQLException{
		
		return orderDAO.SearchMenu(data); 
		}
///// user case 1.5 :MatchSoldoutProcess() 품절매칭
	public void  MatchSoldoutProcess() throws SQLException{	 
		Scanner sc = new Scanner(System.in);
		System.out.println("주문할 음식을 입력하세요 ");
		String name = sc.nextLine(); 
		int exe = orderDAO.MatchSoldout(name); 	
		if (exe == 1) {
			System.out.println("품절입니다 다른메뉴를 선택해주세요");
			System.out.println("주문할 음식을 입력하세요 ");
		}else 
		SearchMenuProcess(name);
			
	}
//// user case 1.7: inputOrderTableProcess() 주문했을때 행 추가
	public void  InputOrderTableProcess() throws SQLException{	
		Scanner sc = new Scanner(System.in);
		HashMap <String,Object>hashmap = new HashMap<String,Object>();
		System.out.println("고객님의 주문번호를 입력해주세요:");
		int serial = sc.nextInt(); 

		sc.nextLine();
		System.out.println("음식코드를 입력해주세요:");
		String code = sc.nextLine(); 
	
		System.out.println("개수를 입력하세요:");
		int count = sc.nextInt(); 
		hashmap.put("serial", serial);
		hashmap.put("code", code);
		hashmap.put("count", count);

		int exe = orderDAO.InputOrderTable(hashmap); 	
		if (exe == 1)
			System.out.println("주문이 완료되었습니다");
		else 
			System.out.println("주문 실패했습니다 ");
	}

//////user case 2: OrderMenuAllProcess() 주문 확인 
	public List<OrderDTO> OrderMenuAllProcess() throws SQLException{	 
		return orderDAO.OrderMenu(); 	
	}	
//////user case 3: ShowCancleProcess() 주문취소
	public void  ShowCancleProcess() throws SQLException{	 
		Scanner sc = new Scanner(System.in);
		System.out.println("취소하실 주문번호를 입력해주세요:");
		int serial = sc.nextInt(); 
		
		int exe = orderDAO.ShowCancle(serial); 	
		if (exe == 1)
			System.out.println("주문이 취소되었습니다");
		else 
			System.out.println("주문내역이 없습니다");
	}
	
	
}//end class 