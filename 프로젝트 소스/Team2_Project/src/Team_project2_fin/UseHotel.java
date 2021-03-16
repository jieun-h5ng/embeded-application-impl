package pack_ver3;

import java.sql.*;
import java.util.*;



public class UseHotel { 
//	필드
	Scanner kb = new Scanner(System.in);
	ArrayList<SubInfo> reservation = new ArrayList<SubInfo>(); // 부가서비스내용,가격,총예약횟수
	String name = "고객";
	String line = "=========================="; 
	String service = ""; // rsvInfo매서드에서 쓰임 
	String sendSubName = "";
	int cnt; // 예약 횟수 카운트 변수
	int amount = 0; // 총 금액
	int choice; // 스캐너 값
	int price = 0; 

	void rsvInfo(SubInfo infoVo) {
		price = infoVo.getMoney(); //
		amount += price;
		cnt++;
		service = infoVo.getServiceName();
		reservation.add(infoVo);
	}

//  메뉴뷰어메소드
	public void menu() { // 상위메뉴창

		System.out.println("총지불금액 : " + amount + "  총예약건수 : " + cnt); // 카운트 세어서
		System.out.println(line); // "======"이거 뽑음
		System.out.println("이용하실 서비스를 선택해주세요.");
		System.out.println("[1]부대시설\n[2]룸서비스\n[3]미니바\n[4]예약확인\n[5]check-out");

	}


	public void facilities(List<SubInfo> list) throws NumberFormatException, SQLException {
		System.out.println(" 이용하실 시설을 선택 해주세요");
		System.out.println("[1]헬스:10000won\n[2]수영:35000won\n[3]스파:50000won\n");
		choice = kb.nextInt();
		SubInfo infoVo = new SubInfo(); // Info 클래스 객체생성
		if (choice == 1) {
			infoVo = list.get(0); // get0번은 sc1의 정보가 담긴 리스트
			rsvInfo(infoVo);
//			reservation.add(infoVo);
			System.out.println("**헬스가 예약되었습니다.\n**지불금액 : 10000원\n" + line);
			return;
		} else if (choice == 2) {
			infoVo = list.get(1);
			rsvInfo(infoVo);
			System.out.println("**수영이 예약되었습니다.\n**지불금액 : 35000원\n" + line);
			return;
		} else if (choice == 3) {
			infoVo = list.get(2);
			rsvInfo(infoVo);
			System.out.println("**스파가 예약되었습니다.\n**지불금액 : 50000원\n " + line);
			return;
		} else {
			System.out.println(" 번호를 잘 못 선택하셨습니다. 다시 선택해주세요.");
//					break;
			return;
		}

	}
	void roomService(List<SubInfo> list) throws SQLException {

		System.out.println("주문하실 메뉴를 선정해주세요.");
		System.out.println("[1]안심크림소스스테이크 :50000won\n[2]활전복구이:35000won\n[3]모둠치즈플레이트:50000won");
		choice = kb.nextInt();
		SubInfo infoVo = new SubInfo();
		if (choice == 1) {// rs 값이 3개씩 들어있으므로 그 3개에 해당하는 값과 사용자가 입력한 값이 맞으면 넣어준다.
			infoVo = list.get(0);
			rsvInfo(infoVo);
			System.out.println("**안심크림소스스테이크가 예약되었습니다.\n**지불금액 : 50000원\n" + line);
			return;
		} else if (choice == 2) {
			infoVo = list.get(1);
			rsvInfo(infoVo);
			System.out.println("**활전복구이가 예약되었습니다.\n**지불금액 : 35000원\n" + line);
			return;
		} else if (choice == 3) {
			infoVo = list.get(2);
			rsvInfo(infoVo);
			System.out.println("**모듬치즈플레이트가 예약되었습니다.\n**지불금액 : 50000원\n" + line);
			return;
		} else {
			System.out.println(" 번호를 잘 못 선택하셨습니다. 다시 선택해주세요.");
			return;
		}
	}

	void miniBar(List<SubInfo> list) throws SQLException {
		System.out.println(" 주문하실 메뉴를 선택해주세요.");
		System.out.println("[1]루이뢰더러 브륏 프리미어 와인 :250000won\n[2]고디바초콜렛:50000won\n[3]건크랜베리너트:30000won");
		choice = kb.nextInt();
		SubInfo infoVo = new SubInfo();
		if (choice == 1) {// rs 값이 3개씩 들어있으므로 그 3개에 해당하는 값과 사용자가 입력한 값이 맞으면 넣어준다.
			infoVo = list.get(0);
			rsvInfo(infoVo);
			System.out.println("**루이뢰더러_브륏_프리미어_와인이 예약되었습니다.\n**지불금액 : 250000원\n" + line);
			return;
		} else if (choice == 2) {
			infoVo = list.get(1);
			rsvInfo(infoVo);
			System.out.println("**고디바초콜렛이 예약되었습니다.\n**지불금액 : 50000원\n" + line);
			return;
		} else if (choice == 3) {
			infoVo = list.get(2);
			rsvInfo(infoVo);
			System.out.println("**건크랜베리너트가 예약되었습니다.\n**지불금액 : 30000원\n" + line);
			return;
		} else {
			System.out.println("번호를 잘 못 선택하셨습니다. 다시 선택해주세요.");
			return;
		}
	}

	void removeSvc() {
		if (reservation.isEmpty()) {
			System.out.println("예약된 서비스가 없습니다.");
			return;
		} else {
			String reserve = ""; // 아래에서
			String str = "[" + name + "님 예약 사항 입니다.]\n";
			for (SubInfo info : reservation) {
				str += info.getServiceName() + " (" + info.getMoney() + "원) \n"; // 디비에서 값을 뽑아 담은 info class의 부가서비스 이름 +
																					// 가격 가져옴
			}
			SubMenuViewer viewer = new SubMenuViewer();
			viewer.reservation = this.reservation;
			System.out.println(str); //
			System.out.println("[0]목록으로 돌아가기 [1]예약삭제");////
			int input = kb.nextInt();
			if (input == 1) {
				System.out.println("삭제할 예약사항을 입력해주세요. ");
				String del = kb.next();
				for (int i = 0; i < reservation.size(); i++) {
					SubInfo item = reservation.get(i);
					if (del.equals(item.getServiceName())) {
						reservation.remove(i);
						cnt--;
						amount = amount - item.getMoney();
					}
				} // end for
			}
			return;
		}
	}
	ArrayList<SubInfo> getUsed() {
	for (int i = 0; i < reservation.size(); i++) {
		SubInfo item = reservation.get(i);
		return reservation;
	}
	return reservation;
}
}


