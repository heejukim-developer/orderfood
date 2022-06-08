package food_project_0504;
//시작 종료 페이지 
//메뉴 전환을 반복하여 전체 프로그램을 동작 시킨다 
public class MainApp {
											
	public static void main(String[] args) throws Exception {

		System.out.println("프로그램을 시작합니다");
		Menu menu = MainMenu.getInstance();
		
		//메뉴가 없을때 까지 반복 
		while (menu != null) {
			menu.print();
			menu = menu.next();
		}
		System.out.println("프로그램이 종료됩니다");

	}//end main
	
}//end class

//메뉴 인터페이스 
interface Menu{
	void print();  
	Menu next() throws Exception;
}