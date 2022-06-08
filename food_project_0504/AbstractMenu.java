package food_project_0504;

import java.util.Scanner;
//메인메뉴와 관리자메뉴의 부모 클래스가 됨 
//main app에 있는 menu 인터페이스 구현 

abstract class AbstractMenu implements Menu {
protected String menuText; //기본문구 
protected Menu prevMenu; //이전메뉴 
protected static final Scanner scanner = new Scanner(System.in);

public AbstractMenu(String menuText, Menu prevMenu) {
	this.menuText = menuText;
	this.prevMenu = prevMenu;
}

//메뉴인터페이스의 print()메소드 
public void print() {
	System.out.println("\n" + menuText); //메뉴출력 
}
//세터메소드 메뉴를 종료한후 돌아갈 이전메뉴를 설정 
public void setPrevMenu(Menu prevMenu) {
	this.prevMenu = prevMenu;
}

}//end class()
