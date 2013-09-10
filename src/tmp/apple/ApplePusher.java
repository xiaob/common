package tmp.apple;

import javapns.communication.exceptions.KeystoreException;
import javapns.devices.exceptions.InvalidDeviceTokenFormatException;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.Payload;
import javapns.notification.PayloadPerDevice;
import javapns.notification.PushNotificationPayload;
import javapns.notification.transmission.NotificationProgressListener;
import javapns.notification.transmission.NotificationThread;
import javapns.notification.transmission.NotificationThreads;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplePusher {

	private static final Logger LOG = LoggerFactory.getLogger(ApplePusher.class);
	
	private NotificationThreads work;
		
	public void init(String keyStore, String password, boolean production) throws KeystoreException{
		work = new NotificationThreads(new AppleNotificationServerBasicImpl(keyStore, password, production), Runtime.getRuntime().availableProcessors());
		// 对线程的监听
		work.setListener(new DefaultNotificationProgressListener());
		// 启动线程
		work.start();
	}
	
	public void push(Payload payload, String token) throws InvalidDeviceTokenFormatException{
		if(work != null){
			work.add(new PayloadPerDevice(payload, token));
		}else {
			LOG.warn("ApplePusher is not init !");
		}
	}
	
	public void alert(String alert, String sound, Integer badge, String token) throws InvalidDeviceTokenFormatException, JSONException{
		PushNotificationPayload payload = new PushNotificationPayload();
		if(alert != null){
			payload.addAlert(alert);
		}
		// 声音
		if(sound != null){
			payload.addSound(sound);
		}
		// 图标小红圈的数值
		if(badge != null){
			payload.addBadge(badge);
		}
		push(payload, token);
	}
	
	public void shutdown(){
		if(work != null){
			work.clearPushedNotifications();
		}
	}
	
}

class DefaultNotificationProgressListener implements NotificationProgressListener{
	
	private static final Logger LOG = LoggerFactory.getLogger(DefaultNotificationProgressListener.class);
	
	public void eventThreadStarted(NotificationThread notificationThread) {
		LOG.debug("   [EVENT]: thread #" + notificationThread.getThreadNumber()
				+ " started with " + " devices beginning at message id #"
				+ notificationThread.getFirstMessageIdentifier());
	}

	public void eventThreadFinished(NotificationThread thread) {
		LOG.debug("[EVENT]: thread #{} finished: pushed messages #{} to {} toward devices",
				new Object[] { thread.getThreadNumber(), thread.getFirstMessageIdentifier(),
						thread.getLastMessageIdentifier() });
	}

	public void eventConnectionRestarted(NotificationThread thread) {
		LOG.debug(
				"[EVENT]: connection restarted in thread #{} because it reached {} notifications per connection",
				new Object[] { thread.getThreadNumber(),
						thread.getMaxNotificationsPerConnection() });
	}

	public void eventAllThreadsStarted(NotificationThreads notificationThreads) {
		LOG.debug("[EVENT]: all threads started:{}", notificationThreads.getThreads().size());
	}

	public void eventAllThreadsFinished(NotificationThreads notificationThreads) {
		LOG.debug("[EVENT]: all threads finished:{}", notificationThreads.getThreads().size());
	}

	public void eventCriticalException(NotificationThread notificationThread,
			Exception exception) {
		LOG.error("[EVENT]: critical exception occurred: ", exception);
	}
}
