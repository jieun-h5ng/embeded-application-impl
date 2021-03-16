package bit.hotel.corona;

import java.util.Scanner;

public class Welcome {
	public void checkInRun() {
		Scanner sc=new Scanner(System.in);
		int select = 0;
		boolean isRun = true;
		GuestInfo guest = new GuestInfo();
		
		while(isRun){
			System.out.println("안녕하세요, 호텔 코로나입니다.\n예약하셨습니까?\n1. 네\t\t2. 아니오\t\t3. 나가기\n답변을 선택해 주십시오. :");
			select = sc.nextInt();
			switch(select) {
				case 1: 
//					searchGuestInfo();
					Reservation.checkReservation();
						break;
				case 2: 
					WriteCard info = new WriteCard();

					info.checkCapacity();
					while (info.isAvailable) { // 방 있을 때
						info.searchInfo();
						if (info.isMembership == false) { // 멤버십 고객 아닌경우 이메일 받고 멤버십광고
							info.getInfo();
						}
						info.setPayment();
						guest = info.setInfo(); //guest객체 받기
					} isRun =false;
						break;
				default: 
					System.out.println("잘 못 선택하셨습니다. 다시 입력해주세요.\n");
					break;
				case 3:
					System.out.println("안녕히 가십시오.");
					isRun=false;
			}
		}//While 끝. 여기서 정연씨가 UserHotel user = new UserHotel(); 이런식으로 불러와서 실행시키시면될거같아요
	}
}
