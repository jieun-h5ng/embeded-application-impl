package bit.hotel.corona;

import java.sql.*;

//1) Oracle Server에 연결
//2) emp 테이블의 데이터를 읽어오자

public class MemberSelect {
// 오라클에 접속하기 위한 클래스를 메모리에 로딩
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	// WriteCard에서 읽어갈 정보
	static String gno;
	static String membership = "";
	static String name = "";
	static int mn = 0;

	public static void search(String strSelect) {
//2. 오랔클 서버 접속 주소, 계정
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "esghotel";
		String pass = "esghotel";
// 3.오라클 서버에 접속하기 위한 객체 생성
		try {
			Connection con = DriverManager.getConnection(url, id, pass);
// 4.SQL문을 실행하기 위한 객체 생성
			Statement statemt = con.createStatement();
// 5.sql문 - 여기선 매개변수로 받음

// 6. SQL문 서버로 전송 후 결과 받아오기
			ResultSet rs = statemt.executeQuery(strSelect);
			// select 문만 결과값 출력이기 때문에 executeQuery 함수 이용
			// ResultSet에 결과값 저장
// 7.결과 출력

			while (rs.next()) { // rs는 내부에 커서가 있어서 next() 호출할때마다
								// 다음행으로 커서를 옮긴다. 더이상 읽을행없으면 false반환
				System.out.println("=========고객번호 " + rs.getString(1) + "=========");
				System.out.println("이름: " + rs.getString(2));
				System.out.println("연락처: " + rs.getString(3));
				System.out.println("이메일: " + rs.getString(6));
				if (rs.getInt(4)==1) {//다시 int로 형변환
					membership = "Gold Class";
					mn = 1; 
				} else if (rs.getInt(4) == 2) {
					membership = "Silver Class";
					mn = 2;
				} else {
					membership = "Bronze Class";
					mn = 3;
				}
				System.out.println("멤버십 등급: " + membership);
				name = rs.getString(2);
				gno = rs.getString(1);
			}
			System.out.println();
			rs.close();
			statemt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
