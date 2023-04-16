package in.om.socket.config;

import org.springframework.context.ApplicationListener;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

public class SessionDisconnectEventListener implements ApplicationListener<SessionDisconnectEvent> {
	
	
	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {
		//log.info("Socket disconnected : "+ event.getSessionId());
	}
}
