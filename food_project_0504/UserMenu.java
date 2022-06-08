package food_project_0504;
//사용자 메뉴 

import java.util.List;

//AbstractMenu의 자식 
public class UserMenu extends AbstractMenu{

	private static final UserMenu instance = new UserMenu(null);
	private static final String ADMIN_MENU_TEXT =
			"1.주문시작\n"+
			"2.주문확인\n"+
			"3.주문취소\n"+
			"q.이전으로\n\n"+
			"선택하세요:";
	
	private UserMenu(Menu prevMenu) {
		super(ADMIN_MENU_TEXT, prevMenu); // 부모 생성자 호출 
	}
	public static UserMenu getInstance() {
		return instance; 				//메뉴 객체를 반환 
	}
	
	public Menu next() throws Exception {
		MenuController dController = new MenuController();
		OrderController oController = new OrderController();
		
		switch(scanner.nextLine()){
//1.주문시작
		case "1" :			
			//전체 음식판 출력 
			List<MenuDTO> aList = dController.MenuAllProcess();
			for(MenuDTO dto : aList) 
				System.out.printf("%s %s %d\n",
						dto.getCode(),
						dto.getName(),
						dto.getPrice());

			//품절인지 아닌지 거르기 
			oController.MatchSoldoutProcess();
			//주문하기 : 입력하기 
			oController.SearchMenuProcess(scanner.nextLine());
			//주문하기 : 주문 테이블에 입력한거 추가하기  
			oController.InputOrderTableProcess();
		return this;
//2.주문확인 		
		case "2":
			//전체 주문판 출력 (menu랑 takeorder랑 조인해서 menu의 name price 가져옴 )
			List<OrderDTO>aList2 = oController.OrderMenuAllProcess();
			for(OrderDTO dto : aList2) 
				System.out.printf("주문번호 %d %s %d원 %d개 %s\n",
						dto.getSerial(),
						dto.getMenuDTO().getName(), //order dto에 getMenuDTO()저장 
						dto.getMenuDTO().getPrice(),
						dto.getCount(),
						dto.getCode());
			return this;
//3.주문취소
		case "3":
			oController.ShowCancleProcess();
			return this;
//q.뒤로가기		
		case "q" : 
			return prevMenu; 
		default: return this; 
		}//end switch
		
	}//end Menu next()

}//end class()
