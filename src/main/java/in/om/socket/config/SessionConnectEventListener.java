package in.om.socket.config;

import in.om.security.JwtUtil;
import in.om.security.TokenDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectEvent;


public class SessionConnectEventListener implements ApplicationListener<SessionConnectEvent> {
	
	@Autowired SocketSessionRegistry webAgentSessionRegistry;
	@Autowired private JwtUtil tokenProvider;

	@Override
	public void onApplicationEvent(SessionConnectEvent event) {
		StompHeaderAccessor header = StompHeaderAccessor.wrap(event.getMessage());
		// login get from browser
		String token = header.getNativeHeader("token").get(0);
		TokenDetails tokenDetails = tokenProvider.getTokenDetails(token);
		String sessionId = header.getSessionId();
		webAgentSessionRegistry.registerSessionId(token, tokenDetails.getUserName(), sessionId);
	}
}
