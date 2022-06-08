package food_project_0504;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PayController {

	PayDAO payDAO;
	MenuDAO menuDAO;
	OrderDAO orderDAO;
	Scanner sc = new Scanner(System.in);
	List<PayDTO> payList = new ArrayList<PayDTO>();
	
	public PayController() {
		payDAO = PayDAO.getInstance();
	}

	//손님 결제내용 기록 
	public void userPayProcess() {
		payDAO.payInsert();
	}

	public void userOrderShow() {
		int[] result = payDAO.paySelect2();
		
		System.out.println("주문번호: " + result[0]); 
		System.out.println("주문금액: " + result[1]);
	
	}
	
	//입력조건이 맞으면 true를 반환하는 메소드
	public boolean chkInputDate(String date) {
		
		char[] hey = date.toCharArray();

		int sum = 0;
		for(char a : hey) {
			if(Character.isDigit(a) == false) {
				sum += 1;
			}	
		}
		
		if(date.length() == 8 && sum == 0) { //8자리이면서 각 자리가 모두 숫자인 경우 true
			return true;
		}else {
			return false;
		}
		
	}//end chkInputDate()
	
	//showPayProcess()
	public void showPayProcess() throws SQLException {
		Scanner sc = new Scanner(System.in);
		System.out.println("기간을 입력해주세요 ex.20220311");
		String startDate ="";
		
		while(true) {
			
			System.out.print("시작일 입력: ");
			startDate = sc.nextLine();
			
			if(chkInputDate(startDate) == false) {
				System.out.println("잘못된 날짜 형식입니다. 다시 입력해주세요.");
			}else {
				
				break;
			}
		}
		String lastDate = "";
		
		while(true) { //종료일 확인
			System.out.print("종료일 입력: ");
			lastDate = sc.nextLine();
			if(Integer.parseInt(startDate) > Integer.parseInt(lastDate)) {
				System.out.println("종료일은 시작일과 같거나 시작일 이후만 가능합니다.");
			}else {
				break;
			}
		}//end while
		
		MenuController mController = new MenuController();
		List<MenuDTO> aList2 = mController.MenuAllProcess();
		for(MenuDTO dto : aList2) 
			System.out.printf("%s %s\n",
					dto.getCode(),
					dto.getName()
					);
		
		while(true) { //메뉴코드 확인
			System.out.print("메뉴코드를 입력해주세요(전체 메뉴는 Z를 입력해주세요): ");
			String code = sc.nextLine(); //코드 입력받기 
			
			//codeChk: 주문한 코드가 메뉴에 있으면 true를 반환하는 메소드
			if(OrderDAO.getInstance().codeChk(code)==false && code.equals("Z") == false){
				System.out.println("없는 메뉴입니다. 다시 선택해주세요\n");
			}else {
			
				String[] result;
				if(code.equals("Z") == false) {
					result = payDAO.paySelect(startDate, lastDate, code);
					if(result[0] == null) {
						System.out.printf("해당메뉴는 %s부터 %s까지 판매기록이 없습니다.\n\n"
								, startDate, lastDate);
					}else {
					String startPrint = startDate.substring(0, 4) + "년 " + startDate.substring(4, 6) + "월 " + startDate.substring(6, 8) + "일";
					String lastPrint = lastDate.substring(0, 4) + "년 " + lastDate.substring(4, 6) + "월 " + lastDate.substring(6, 8) + "일";
					System.out.printf("%s메뉴는 %s부터 %s까지 %s개 판매되었습니다. \n매출총합은 %s원입니다.\n\n"
							, result[0], startPrint, lastPrint, result[1], result[2]);
					}
				}else{
					result = payDAO.paySelectAll(startDate, lastDate);
					String startPrint = startDate.substring(0, 4) + "년 " + startDate.substring(4, 6) + "월 " + startDate.substring(6, 8) + "일";
					String lastPrint = lastDate.substring(0, 4) + "년 " + lastDate.substring(4, 6) + "월 " + lastDate.substring(6, 8) + "일";
					System.out.printf("전체메뉴는 %s부터 %s까지 %s개 판매되었습니다. \n매출총합은 %s원입니다.\n\n"
							, startPrint, lastPrint, result[0], result[1]);
				}
				break;
			}
		}//end while
		
	}//end showPayProcess()
	
}//end class()
