package food_project_0504;


import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MenuDAO {

	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private static MenuDAO menudao = new MenuDAO();
	private MenuDAO() {}
	
	public static MenuDAO getInstance() {
		return menudao;
	}
	
	private Connection init() throws ClassNotFoundException,SQLException {
		Class.forName("oracle.jdbc.OracleDriver");
		String url ="jdbc:oracle:thin:@127.0.0.1:1521:xe";
		String username = "hr";
		String password = "a1234";
		return DriverManager.getConnection(url,username,password);
	}
		private void exit() throws SQLException {
			
			if(rs!=null) {
			  rs.close();
			}
			if(stmt!=null) {
				stmt.close();
			}
			if(conn!=null) {
				conn.close();
				}
			}

///// 관리자 case 2 :InputMenuTable() 메뉴 테이블에 추가 // 상세 내용 저장 
   public List<MenuDTO>InputMenuTable() throws SQLException{
		  List<MenuDTO>aList = new ArrayList<MenuDTO>();

		Scanner sc = new Scanner(System.in);
		String sql = "insert into menu"
				     +"(code,name,price)"
				     +"values"
				     +"(?,?,?)";	
		
		System.out.println("코드 등록: ");
		String code = sc.nextLine();
		
		System.out.println("음식명 등록: ");
		String name = sc.nextLine();
		
		System.out.println("가격 등록: ");
		int price = sc.nextInt();

		pstmt.setString(1, code);
		pstmt.setString(2, name);
		pstmt.setInt(3, price);
		
		int rowCount = pstmt.executeUpdate();
	    System.out.println(rowCount + "개의 음식이 추가되었습니다.");
	    return aList;
	    
	}// end InputMenuTable();

/////관리자메뉴 case 3 MenuAllProcess 현재 음식 보여주기 
  public List<MenuDTO>MenuAll() throws SQLException{
		  List<MenuDTO>aList = new ArrayList<MenuDTO>();
  try {	
	conn= init();
	stmt = conn.createStatement();
	String sql= "SELECT * FROM MENU";
	rs=stmt.executeQuery(sql); //sql 필수 필수 
	while(rs.next()) {
		MenuDTO dto = new MenuDTO();
		dto.setCode(rs.getString("code"));
		dto.setName(rs.getString("name"));
		dto.setPrice(rs.getInt("price"));
		aList.add(dto);
	} 
	   } catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				exit();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		} 
	
		return aList;
		
}//end MenuAll()

///// 관리자 case 3.5 :SoldOut()menu에 1.0추가  // 상세 내용 저장 
 public int SoldOut(String name) throws SQLException{
		
		int exe = -1;
		Scanner sc = new Scanner(System.in);
		String sql = "update menu "
		             +"set soldout = '1'"
		             +"where name = ?";        
//		왜 안됐지???
//		String sql = "update menu set soldout = '1' where name = ?";
		try {
			conn = init();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, name);
			
			exe = pstmt.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	    return exe;
	    }
  
}//end DAO 
