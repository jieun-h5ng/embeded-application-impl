package bit.hotel.corona;

import java.sql.*;

public class MemberInsert {
	 static {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
 public void insert(String strInsert) {
	//2. 오랔클 서버 접속 주소, 계정
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "esghotel";
		String pass = "esghotel";
		
		//3. 오라클서버접속객체 생성
		try {
			Connection con = DriverManager.getConnection(url, id, pass);
			Statement stmnt = con.createStatement();

	//실행
			stmnt.executeUpdate(strInsert);
			
			stmnt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

}
