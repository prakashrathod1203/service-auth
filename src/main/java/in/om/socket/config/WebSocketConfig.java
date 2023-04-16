package in.om.socket.config;

import in.om.utility.ApplicationConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	@Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(ApplicationConstants.SOCKET_BROKER); //notification
        config.setApplicationDestinationPrefixes(ApplicationConstants.SOCKET_PREFIX); //app
        config.setUserDestinationPrefix(ApplicationConstants.SOCKET_USER_DEST_PREFIX); //dgs
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
    	registry.addEndpoint(ApplicationConstants.SOCKET_ENDPOINT).setAllowedOrigins("*").withSockJS();
    }
    
    @Bean
    public SocketSessionRegistry socketSessionRegistry(){
        return new SocketSessionRegistry();
    }
    
    @Bean
    public SessionConnectEventListener sessionConnectEventListener(){
        return new SessionConnectEventListener();
    }
    
    @Bean
    public SessionDisconnectEventListener sessionDisconnectEventListener(){
        return new SessionDisconnectEventListener();
    }
    
    @Bean
    public SessionUnsubscribeEventListener sessionUnscribeEventListener(){
        return new SessionUnsubscribeEventListener();
    }
    
}
