package food_project_0504;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import java0426_jdbc.part01.DepartmentsDTO;

public class OrderDAO {
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private static OrderDAO orderdao = new OrderDAO();
	private OrderDAO() {}
	
	public static OrderDAO getInstance() {
		return orderdao;
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

/////사용자메뉴 case 1 : SearchMenu()
	 public List<MenuDTO>SearchMenu(String data) throws SQLException{
		      List<MenuDTO>aList = new ArrayList<MenuDTO>();
	 try {
		
		String sql = "SELECT * FROM MENU WHERE NAME LIKE ?";	
		conn =init();
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,"%"+data+"%");
	    rs=pstmt.executeQuery();
		
			while(rs.next()) {
				MenuDTO dto = new MenuDTO();
				dto.setCode(rs.getString("code"));
				dto.setName(rs.getString("name"));
				dto.setPrice(rs.getInt("price"));
				dto.setSoldout(rs.getString("soldout"));
			
					if(dto.getSoldout()== "1")
						System.out.println("품절입니다");
						aList.add(dto);
			}//end while
			
		} catch (SQLException e) {
				e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
				try {
					exit();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
	 
		 return aList;
		 
	}//SearchMenu();
	
/////사용자메뉴 case 1.5 : MatchSoldout() 품절매칭
		public int MatchSoldout( String name ) throws SQLException{
			int exe=-1;
			
			String  sql="select soldout";
				  sql += " from menu ";
			      sql += " where name = ?";
			      sql += " and soldout = '1'";
			try {
				conn = init();
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, name);
				exe = pstmt.executeUpdate();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
			return exe;
			
		}//end MatchSoldout();
		
///// userMenu case 1.5 주문하기: InputOrderTable() 메뉴 테이블에 추가 // 상세 내용 저장 
	public int InputOrderTable( HashMap<String,Object> map ) throws SQLException{
		List<OrderDTO>aList = new ArrayList<OrderDTO>();
			int exe= -1;
			
			String sql = "insert into takeorder"
					     +"(serial,orderdate,code,count) "
					     +"values"
					     +"(?,sysdate,?,?)";
			
			try {
				conn = init();
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1,Integer.parseInt(String.valueOf(map.get("serial"))));
				pstmt.setString(2,(String.valueOf(map.get("code"))));
				pstmt.setInt(3,Integer.parseInt(String.valueOf(map.get(("count")))));
				exe = pstmt.executeUpdate();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
			return exe;
		    
	}//end InputOrderTable();
	
	
/////userMenu case 2 주문확인 OrderMenu() //주문번호 넣으면 조인해서 음식도 나와야함 
	public List<OrderDTO> OrderMenu(){
	List<OrderDTO>aList = new ArrayList<OrderDTO>();
			try {	
				conn= init();
				stmt = conn.createStatement();
	
				String  sql="select o.serial,m.code,m.name,m.price,o.count,o.orderdate";
						sql += " from menu m,takeorder o";
						sql += " where m.code = o.code";
	
				rs=stmt.executeQuery(sql);
				while(rs.next()) {
					OrderDTO dto = new OrderDTO();
					dto.setSerial(rs.getInt("serial"));
					dto.setOrderdate(rs.getString("orderdate"));
					dto.setCode(rs.getString("code"));
					dto.setCount(rs.getInt("count"));
					//menu의 이름 가격 조인하기 
					MenuDTO menuDTO = new MenuDTO();
					menuDTO.setName(rs.getString("name"));
					menuDTO.setPrice(rs.getInt("price"));
					dto.setMenuDTO(menuDTO);	
					
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
	
	}//end orderMenu();

	
/////userMenu case3 주문취소 : ShowCancle() 
	//하나만 지우는것이기 때문에 hash안써도 되고 delete로 해야한다 
	public int ShowCancle( int serial ) throws SQLException{
			int exe= -1;
			//자식인 pay테이블부터 지워야 한다 
			String sql = "delete from pay where serial=?";
			try {
				conn = init();
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, serial);
				exe = pstmt.executeUpdate();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			// 그 다음 takeorder를 지워야한다 
			sql = "delete from takeorder where serial=?";
			try {
				conn = init();
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, serial);
				exe = pstmt.executeUpdate();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
			return exe;
	}//end ShowCancle();

/////pay에서 쓰는 메소드인데 
/////codeChk: 주문한 코드가 메뉴에 있으면 true를 반환하는 메소드
	public boolean codeChk(String data) {
		boolean result = false; //오답노트: 이거 할당안하면 오류나옴!!!
		try {
			conn = init();
			
			String sql = "SELECT * FROM menu WHERE code = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, data);
			rs = pstmt.executeQuery();
			result = rs.next();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				exit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result; 
	}//end codeChk();
	
}//end class();
