package food_project_0504;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PayDAO {

	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public static PayDAO paydao = new PayDAO();
	public PayDAO() {};
	
 //싱글톤패턴 getInstance()해서 하나의 객체로 여러번 쓴다 
	public static PayDAO getInstance() {
		return paydao;
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

 //payInsert(): 결산테이블에 넣기
	public void payInsert() {
		try {
			conn = init();
			String sql = "INSERT INTO pay(serial, orderdate, totalprice) "
					+ "SELECT o.serial, o.order_date, m.price * o.count "
					+ "FROM menu m, takeorder o " //오답노트 : pay테이블에 넣는것이기때문에 null인 상태의 pay테이블로 from하면 안된다 
					+ "WHERE o.code = m.code "
					+ "AND o.serial_number = (SELECT max(serial_number) FROM order_menu)";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate(); 
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				exit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}//end finally
	}//end payInsert()
	
	//paySelect(): 조건따라서 pay테이블 내용 가져오는 결산용 메소드
		public String[] paySelect(String startDate, String lastDate, String code) {
			String[] result = new String[3];
			
			try {
				conn = init();
				String sql = "SELECT m.name, sum(o.count) as quantity, sum(totalprice) "
						+ "FROM menu m, takeorder o, pay p "
						+ "WHERE o.serial = p.serial "
						+ "AND m.code = o.code "
						+ "AND p.orderdate >= TO_DATE(?,'YY/MM/DD') "
						+ "AND p.orderdate <= (TO_DATE(?,'YY/MM/DD') + 1) "
						+ "AND o.code = ? "
						+ "GROUP BY m.name "
						+ "ORDER BY m.name ";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, startDate);
				pstmt.setString(2, lastDate);	
				pstmt.setString(3, code);
				
				rs = pstmt.executeQuery();
				
				
				if(rs.next()) {
					result[0] = rs.getString("name");
					result[1] = Integer.toString(rs.getInt("quantity"));
					result[2] = Integer.toString(rs.getInt("sum(totalprice)"));
				}
				//오답노트: Integer.toString(a); int가 저장된 변수인 a의 값을 String으로 바꿔줌.
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
		}//end paySelect()
				
		//paySelectAll(): 조건따라서 pay테이블 내용 가져오는 결산 메소드
		public String[] paySelectAll(String startDate, String lastDate) {
			String[] result = new String[2];
			
			try {
				conn = init();
				String sql = "SELECT sum(o.count) as quantity, sum(totalprice) "
						+ "FROM menu m, takeorder o, pay p "
						+ "WHERE o.serial = p.serial "
						+ "AND m.code = o.code "
						+ "AND p.orderdate >= TO_DATE(?,'YY/MM/DD') "
						+ "AND p.orderdate <= (TO_DATE(?,'YY/MM/DD') + 1)";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, startDate);
				pstmt.setString(2, lastDate);	
				
				rs = pstmt.executeQuery();
				
				
				if(rs.next()) {
					result[0] = Integer.toString(rs.getInt("quantity"));
					result[1] = Integer.toString(rs.getInt("sum(totalprice)"));
				}
				//오답노트: Integer.toString(a); int가 저장된 변수인 a의 값을 String으로 바꿔줌.
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
		}//end paySelect()		
		
		//paySelect2(): pay테이블 제일마지막에 추가된 레코드의 주문번호와 총합가져오는 메소드
		public int[] paySelect2() {//원래는 dto로 해야하는데, 배열로도 되는지 궁금해서 만들어봄. 잘됨!
			
			int[] result = new int[2];
			try {
				conn = init();
				stmt = conn.createStatement();
				String sql = "SELECT serial, totalprice FROM pay "
							+ "WHERE serial = (SELECT max(serial) FROM takeorder)";
				rs = stmt.executeQuery(sql);
				if(rs.next()) {
					result[0] = rs.getInt("serial");
					result[1] = rs.getInt("totalprice");
				}
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
		}//end paySelect2()

	
}//end class
