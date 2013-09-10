package tmp.apple;


public class AppleTest {

	public static void main(String[] args) throws Exception {
		ApplePusher pusher = new ApplePusher();
		pusher.init("d:/test.p12", "123", false);

		pusher.alert("1111", "hxwNewMessagesAlert.wav", null, "");
		
		pusher.shutdown();
	}

}
