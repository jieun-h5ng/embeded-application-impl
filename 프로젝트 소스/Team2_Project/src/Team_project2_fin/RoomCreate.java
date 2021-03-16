package bit.hotel.corona;

import java.sql.*;

public class RoomCreate {

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void search(String strCreate) {

		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "esghotel";
		String pass = "esghotel";

		// 3. 오라클서버접속객체 생성
		try {
			Connection con = DriverManager.getConnection(url, id, pass);
			Statement stmnt = con.createStatement();

			// 실행
			int cnt = stmnt.executeUpdate(strCreate);

			stmnt.close();
			con.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
}
