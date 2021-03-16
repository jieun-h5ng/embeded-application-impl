package pack_ver3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SubMenuViewer {
	ArrayList<SubInfo> reservation = new ArrayList<SubInfo>();
	Scanner kb = new Scanner(System.in);
	UseHotel useHotel = new UseHotel();
	String subinfo ;
	int choice; // 스캐너 값
//	서비스메뉴메소드(메뉴뷰어메소드 호출)
	public void service(int mem) {
		// 2. url, id, pass
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "esghotel";
		String pass = "esghotel";

		// 1. 오라클 접속에 필요한 클래스를 메모리로 로딩한다
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 3. 클래스 변수 선언
			Connection con = null; // 오라클 접속에 필요
			PreparedStatement preStmt = null; // SQL 문 실행에 필요
			ResultSet rs = null; // SELECT문 결과값 저장에 필요
			try {
				// 4. 오라클 접속
				con = DriverManager.getConnection(url, id, pass);
				// 7. 결과 검사
				String serviceList = "SELECT * FROM EXTRASERVICE  "; // 부가서비스 테이블 정보 출력
				String whereStr = ""; // 조건 뽑아 스트링으로 담음
				while (true) {
					useHotel.menu(); // 매뉴메서드 호출
					choice = kb.nextInt();
					List<SubInfo> list = new ArrayList(); // info클래스 타입의 어레이리스트 생성.
					SubInfo serviceInfo = null; // info클래스의 변수선언 <서비스이름 , 가격 ,횟수 정보 담김>
					switch (choice) {
					case 1:
						whereStr = "WHERE ENO like 'SC%'"; // serviceList + where절 입력하는 변수에 조건을 스트링 값으로
																			// 넣어줌 sc 부대시설만 추출
						preStmt = con.prepareStatement(serviceList + whereStr); // (select * from) + where
						rs = preStmt.executeQuery(); // 실행쿼리에 넣음 , 그럼 sc 1 ,sc2 ,sc3 의 데이터만 뽑힘
						while (rs.next()) { // rs객체의 다음값을 while문으로 뽑아냄
							serviceInfo = new SubInfo(); // 객체 생성해서 새로운 값을받을 수 있도록 , case안에다가 객체 생성하지 않으면 , 값이 덮어씌어짐. .
							serviceInfo.setServiceName(rs.getString(2)); // getString(2) = 2는 이름을 가리킴
							serviceInfo.setMoney(Integer.parseInt(rs.getString(3))); // getString(3) = 3은 가격을 가리킴
							list.add(serviceInfo); // 쿼리 결과만큼 while 문으로 돈다. 그후 다시 와일문돌면서 객체값이 초기화
						}
						useHotel.facilities(list); // 부대시설메서드 , while문으로 돌리면서 rs 값을 저장했던 list를 부대시설 매변에 넣음.
						break;
					case 2:
						whereStr = "WHERE ENO like 'RM%'"; // rm부대시설만 뽑음
						preStmt = con.prepareStatement(serviceList + whereStr);
						rs = preStmt.executeQuery();
						while (rs.next()) {
							serviceInfo = new SubInfo();
							serviceInfo.setServiceName(rs.getString(2));
							serviceInfo.setMoney(Integer.parseInt(rs.getString(3)));
							list.add(serviceInfo);
						}
						useHotel.roomService(list); // 룸서비스 매서드
						break;
					case 3:
						whereStr = "WHERE ENO like 'MN%'";// mn 부대시설만 뽑음
						preStmt = con.prepareStatement(serviceList + whereStr);
						rs = preStmt.executeQuery();
						while (rs.next()) {
							serviceInfo = new SubInfo();
							serviceInfo.setServiceName(rs.getString(2));
							serviceInfo.setMoney(Integer.parseInt(rs.getString(3)));
							list.add(serviceInfo);
						}
						useHotel.miniBar(list); // 미니바 매서드
						break;
					case 4:
						useHotel.removeSvc();
						break;
					case 5:
						CheckoutInfo chkout = new CheckoutInfo();
						chkout.checkOut(useHotel.getUsed(), useHotel.cnt, useHotel.amount);
						
						break;
					default:
						System.out.println(" 번호를 잘 못 선택하셨습니다. 다시 선택해주세요.");
						continue;
					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {

				try {
					// 8. 열린 객체 모두 닫기
					if (rs != null)
						rs.close();
					if (preStmt != null)
						preStmt.close();
					if (con != null)
						con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
class CheckoutInfo{
	ArrayList<SubInfo> sendSubName;
	Scanner kb =new Scanner(System.in);
	int cnt;
	int amont;
	
	public void checkOut( ArrayList<SubInfo> getUsedList ,int cnt ,int amount ) {
		System.out.println( "부대시설 내역입니다.");
		for (int i=0; i < getUsedList.size(); i++) {
			System.out.println("**** " + getUsedList.get(i).getServiceName());
		}
		System.out.println("총지불금액 : "+amount+"  총예약건수 : " + cnt);
		int enter = kb.nextInt();
		this.sendSubName = getUsedList;
		this.cnt = cnt;
		this.amont = amount;
		
	}
}
	


