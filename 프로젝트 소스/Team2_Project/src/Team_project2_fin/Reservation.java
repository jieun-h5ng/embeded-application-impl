package bit.hotel.corona;

import java.sql.*;
import java.util.Scanner;

public class Reservation {
	static Scanner sc=new Scanner(System.in);
	static String rn;
	static String name;
	static int membership;
	static String roomNum;
	static String checkIn;
	static String checkOut;
	
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void checkReservation() {
		System.out.println("예약번호를 입력해 주십시오.");
		
		String url="jdbc:oracle:thin:@localhost:1521:xe";
		String id="esghotel";
		String pass="esghotel";
		
		String sql="SELECT gno, gname, gmship, rsvno, rno, rsvcheckin, rsvcheckout FROM reservation NATURAL JOIN guest WHERE rsvno=";
		
		rn=sc.next();
		
		try {
			Connection con=DriverManager.getConnection(url, id, pass);
			PreparedStatement pstmt=con.prepareStatement(sql+"'"+rn+"'");
			ResultSet rs=pstmt.executeQuery();
			
			while(rs.next()) {
				name=rs.getString("gname");
				membership=rs.getInt("gmship");
				roomNum=rs.getString("rno");
				checkIn=rs.getString("rsvcheckin");
				checkOut=rs.getString("rsvcheckout");
				
				System.out.println("\n예약하신 정보는 다음과 같습니다 : \n");
//				System.out.printf("%s %d %s %s %s", name, membership, roomNum, checkIn, checkOut);	//출력
				System.out.println("성함 : "+name);
				System.out.println("멤버십 : "+membership);
				System.out.println("객실 번호 : "+roomNum);
				System.out.println("체크인 일자 : "+checkIn);
				System.out.println("체크아웃 일자 : "+checkOut);
				System.out.println("");
			}
			
			rs.close();
			pstmt.close();
			con.close();
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		System.out.println("카드번호를 입력해주세요.");
		String cardNum=sc.next();
		System.out.println("\n입력하신 카드 번호는 다음과 같습니다 : \n"+cardNum+"\n");
		
		GuestInfo gi=new GuestInfo();
		gi.guestInfo(rn, name, membership, roomNum, checkIn, checkOut, cardNum);
		return;
		
	}
	public static void main(String[] args) {
		
	}
}