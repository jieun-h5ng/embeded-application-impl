package bit.hotel.corona;

import java.sql.*;
import java.util.Scanner;

public class WriteCard {
//모두 DB 예약테이블에 들어갈것. JDBC 인서트도 해야함 1) 방배정 2) 멤버십가입
	String rn; // 예약번호
	String date1, date2;
	// 날짜 어케 받지
	int personnel; // 투숙인원
	String roomNum = "";

	boolean isAvailable = false; // 빈방있음여부
	boolean isMembership = false;
// Guest 객체에 들어갈것
	private String gno;
	private int membership; // 멤버십등급
	private String name;
	private String phoneNum;
	private String eMail = "";
	int selMembership = 0;// 등급 : 1(gold) 2(silver) 3(Bronze) 0(none)
	String cardNum;

	Scanner sc = new Scanner(System.in);
//쿼리문들 (짧은건 나중에 없애도 될듯)

	public void checkCapacity() {

		// 날짜랑 인원부터 체크 - 값저장
		// 빈방있는경우 "예약하시겠습니까?" -> searchInfo 로 이름 연락처 받기
		// 1. 예약하기 / 2. 다른날짜 인원 선택
		while (true) {
			int checkIn, checkOut;
			System.out.print("체크인 날짜를 입력하세요(YYYYMMDD): ");
			checkIn = sc.nextInt();

			System.out.print("체크아웃 날짜를 입력하세요(YYYYMMDD: ");
			checkOut = sc.nextInt();

			date1 = Integer.toString(checkIn);
			date2 = Integer.toString(checkOut);

			if (checkOut < checkIn) {
				System.out.println("-----------------------------");
				System.out.println("체크아웃이 체크인 날짜보다 이릅니다.");
				System.out.println("-----------------------------");
			} else {
				while (true) {
					System.out.print("투숙 인원을 입력하세요(0.뒤로가기): ");
					personnel = sc.nextInt();
					sc.nextLine(); // Enter값 날리기
					if (personnel == 0)
						break;
					else if (personnel >= 5) {
						System.out.println("--------------------------------------------------");
						System.out.println("코로나 감염증 예방에대한 정부지침으로 5인이상 투숙은 불가합니다");
						System.out.println("--------------------------------------------------");
					} else {// 체크인,아웃,인원수에 맞춰 예약가능한 방 조회

						// view 테이블 생성(미리해놓음)하고 조회한다.
						RoomSelect roomSel = new RoomSelect();
//객실 0개일경우 처리
//						roomInfo.search(strRoom);
						roomSel.search(" SELECT DISTINCT *  FROM forrsvtotal\r\n" + " WHERE 수용인원 >=" + personnel + "   \r\n"
								+ " AND 객실번호 NOT IN (SELECT 객실번호\r\n" + " FROM reservation  \r\n" + " WHERE (RSVCHECKIN <= '"
								+ checkOut + "'\r\n" + " AND RSVCHECKOUT >= '" + checkIn + "')) ");

						if (roomSel.cnt == 0) {
							System.out.println("선택하신 날짜에 방이 없습니다.");
							break;
						}
						System.out.print("묵으실 방을 선택하세요:");
						roomNum = sc.nextLine();
						// 방번호 선택한거로 INSERT 하면될듯
						System.out.print(checkIn + "~" + checkOut + " 날짜에 " + roomNum + "호실 " + personnel
								+ "명 예약을 진행하시겠습니까? (Y/N)");
						String answer = sc.nextLine();
						if (answer.equals("Y") || answer.equals("y")) {
							isAvailable = true;

							return;
						} else {
							System.out.println("-------------------");
							System.out.println("첫화면으로 돌아갑니다");
							System.out.println("-------------------");
							break;
						}
					}
				}
			}
		}

	}

	public void searchInfo() {
		System.out.print("예약자 이름을 입력하세요: ");
		name = sc.nextLine();
		System.out.print("연락처를 입력해주세요: ");
		phoneNum = sc.nextLine();

		String strGuest = " SELECT * FROM guest WHERE gname ='" + name + "' AND gpnum ='" + phoneNum + "' ";

		// 이름 연락처로 멤버십인지 확인하는 if 문
		MemberSelect memberInfo = new MemberSelect();
		memberInfo.search(strGuest);
		if (memberInfo.mn != 0) {
			System.out.println(memberInfo.membership + " " + memberInfo.name + " 고객님 재방문을 환영합니다.");
			isMembership = true;
			membership = memberInfo.mn;
			gno = memberInfo.gno;
		}

		// 멤버십 고객인경우 "Gold Class 홍지은 고객님 재방문을 환영합니다." > setPayment
		// 아닌경우 > setInfo

	}

	public void getInfo() {
		// 나머지 고객정보 입력받기
		// 1.이름 연락처 이메일(null허용)
		System.out.print("바우처를 받으실 이메일을 입력하세요: ");
		eMail = sc.nextLine();
		System.out.println("코로나호텔 멤버십에 가입하시겠습니까?");
		System.out.println("-----------------------------------");
		System.out.println("Gold Class: 부대시설 이용이 평생ㅋㅋ 무료"); // 회의때 바꿀것
		System.out.println("Silver Class: 월1회 룸서비스 5만원 이용권");
		System.out.println("Bronze Class: 월1회 미니바 3만원이용권");
		System.out.println("-----------------------------------");
		System.out.println("1. Gold 가입  2.Silver 가입 3. Bronze 가입 0. 멤버십혜택없이 이용하기");

		selMembership = sc.nextInt();
		sc.nextLine();

		// 멤버십가입의 경우 db 변경후 return
	}

	public void setPayment() {
		// 2. 결제할 카드 정보 생성
		System.out.print("결제하실 카드 번호를 입력해주세요:");
		cardNum = sc.nextLine();
		System.out.println("------------------");
		System.out.println("예약이 완료되었습니다!");
		System.out.println("------------------");

		if (selMembership != 0) {

			String memInsert = "INSERT INTO ESGHOTEL.GUEST (GNO,GNAME,GPNUM,GMSHIP,GPAY,EMAIL) \r\n"
					+ "VALUES (gno.NEXTVAL,'" + name + "','" + phoneNum + "'," + selMembership + ",'" + cardNum + "','"
					+ eMail + "')";

			MemberInsert newMember = new MemberInsert();
			newMember.insert(memInsert);
		}

		isAvailable = false;
	}

	public GuestInfo setInfo() {

		// DB 에 예약정보 생성하기
		String strInsert = "INSERT INTO ESGHOTEL.RESERVATION (RSVNO, RSVCHECKIN,RSVCHECKOUT,RSVPERSON,GNO,RNO) "
				+ " VALUES (RSVNO.nextval, to_DATE('" + date1 + "','yyyymmdd'),to_DATE('" + date2 + "','yyyymmdd'),"
				+ personnel + ",'" + gno + "','" + roomNum + "') ";

		RsvInsert rsvInfo = new RsvInsert();
		rsvInfo.insert(strInsert); // 오류 해결하고나면, Member Insert랑 합쳐보기

//또 데이터 읽어오는 작업 = 고객번호만 받아오기
		RsvSelect getRsvInfo = new RsvSelect();
		rn = getRsvInfo.rn;

		GuestInfo gi = new GuestInfo();
		gi.guestInfo(rn, name, membership, roomNum, date1, date2, cardNum);
		System.out.println("고객객체 생성 확인용: " + gi.rsvNum + " " + gi.name + " " + gi.membership + " " + gi.roomNum + " "
				+ gi.checkIn + " " + gi.checkOut + " " + gi.cardInfo + " " + gi.cardMoney);
		return gi;

	}
}