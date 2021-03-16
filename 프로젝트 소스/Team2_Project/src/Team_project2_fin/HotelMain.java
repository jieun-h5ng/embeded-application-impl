package pack_ver3;

public class HotelMain {
	public static void main(String[] args) {
		Welcome chkIn = new Welcome();
		chkIn.checkInRun();		
		
		SubMenuViewer smv =new SubMenuViewer();
		smv.service(1);
	}
	
}

