package bit.hotel.corona;

public class GuestInfo {
	String rsvNum;
	String name;
	int membership;
	String roomNum;
	String checkIn;
	String checkOut;
	String cardInfo;
	int cardMoney;


	public void guestInfo(String rsvNum, String name, int membership, String roomNum, String checkIn, String checkOut, String cardInfo) {
		this.rsvNum = rsvNum;
		this.name = name;
		this.membership = membership;
		this.roomNum = roomNum;
//		this.checkIn = Integer.parseInt(checkIn);
//		this.checkOut = Integer.parseInt(checkOut);
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.cardInfo = cardInfo;
		cardMoney = ((int) Math.random() * 1000) * 10000;
		
		System.out.println("★값이 제대로 전달 되었는지 확인하기 위한 출력입니다."+rsvNum+name+membership+roomNum+checkIn+checkOut+cardInfo+cardMoney);	// 출력용
		System.out.println("");
//		System.out.println("★parseInt test : "+checkIn+checkOut);
	}
}
