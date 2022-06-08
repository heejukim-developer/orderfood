package food_project_0504;

//DAO(Data Access Object)는 데이터베이스의 data에 접근하기 위한 객체 
//직접 DB에 접근하여 data를 삽입, 삭제, 조회 등 조작할 수 있는 기능을 수행한다.
//MVC 패턴의 Model에서 이와 같은 일을 수행한다.

import java.sql.Connection;
import java.sql.DriverManager; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException;
import java.sql.Statement; 

//<Statement와 PreparedStatement>
//기존 Statement를 이용해서 데이터를 넣어줄려면 값을 직접 작성해야 함
//하지만, PrepareStatement는 메서드와, "?" 이것을 이용해서 데이터를 전달할 수 있다.
//PrepareStatement 클래스를 통해 가독성과, 효율성을 둘다 챙길 수 있는 좋은 방법을 사용 할수있다 .

//executeQuery(): 조회문(select, show 등)을 실행할 목적으로 사용한다.
//executeUpdate(): 조회문(select, show 등)을 제외한 create, drop, insert, delete, update 등등 문을 처리할 때 사용한다.

public class AdminDAO {

	private Connection conn; //sql 연결을 위한 Connection 객체 생성
	private Statement stmt; //Query 작업을 실행하기 위한 객체.질의 수행
	private PreparedStatement pstmt; //인자값으로 전달이 가능하다.
	private ResultSet rs; //질의 결과가 있다면, ResultSet 객체를 생성하여 결과 저장
	
	private static AdminDAO admindao = new AdminDAO();
	private AdminDAO() {}
	
	public static AdminDAO getInstance() {
		return admindao;
	}
	
	private Connection init() throws ClassNotFoundException,SQLException {
		//1.드라이버 로딩
		Class.forName("oracle.jdbc.OracleDriver");
		//2.연결하기
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
		
//관리자 case 1 ListPassword 비밀번호 리스트 
		public boolean ListPassword( String password ) throws SQLException{
			boolean chk = false;//불리언 기본값 false로 줘야함 
			AdminDTO dto = new AdminDTO();	

			String sql="select password";
			  sql += " from admin ";
		      sql += " where password = ?";
			try {
				conn = init();
				
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1,password); //set타입은 작성한 SQL문에 있는 ? 라는 값을 바꿔주는 역할이다. 
				//executeQuery(): 조회문(select, show 등)을 실행할 목적으로 사용한다.
				
				rs = pstmt.executeQuery();
				//ResultSet : 쿼리문을 처리한후 rs 반환 , 조회된 목록들의 저장된 객체를 반환
				while(rs.next()) {
					dto.setPassword(rs.getString("password"));//getstring("컬럼명")
					if (password.equals(dto.getPassword())) {
						chk=true;
						break;
					}else if(!(password.equals(dto.getPassword())))
						chk=false;
						break;
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}return chk;

	}//end ListPassword();
		
////관리자 case 1  ChangePasswordProcess에서 비밀번호 입력하면 admin테이블에 들어감 
		public int ChangePassword( String password ) throws SQLException{
			int exe= -1;
			String sql = "insert into admin"
						        +"(password)"
						        +"values"
						         +"(?)";
			try {
				conn = init();
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, password);
						
				//executeUpdate() : 조회문(select, show 등)을 제외한 create, drop, insert, delete, update 등등 문을 처리할 때 사용한다
				exe = pstmt.executeUpdate();  //int값 반환
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}return exe;

		}//end  ChangePassword()

			
}//end class()

