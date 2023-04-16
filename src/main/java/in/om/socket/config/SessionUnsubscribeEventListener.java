package in.om.socket.config;

import in.om.security.JwtUtil;
import in.om.security.TokenDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;


public class SessionUnsubscribeEventListener implements ApplicationListener<SessionUnsubscribeEvent> {
	
	@Autowired SocketSessionRegistry webAgentSessionRegistry;
	@Autowired private JwtUtil tokenProvider;
	
	@Override
	public void onApplicationEvent(SessionUnsubscribeEvent event) {
		StompHeaderAccessor header = StompHeaderAccessor.wrap(event.getMessage());
		String token = header.getFirstNativeHeader("id");
		if(token != null){
			TokenDetails tokenDetails = tokenProvider.getTokenDetails(token);
			webAgentSessionRegistry.unregisterSessionId(token, tokenDetails.getUserName(), header.getSessionId());
		}
	}
}
