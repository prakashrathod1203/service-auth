package in.om.socket.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.util.Assert;

public class SocketSessionRegistry {
	private final ConcurrentMap<String, Map<String, String>> tenantConcurrentMap = new ConcurrentHashMap<String, Map<String, String>>();
	private final Object lock = new Object();

	public SocketSessionRegistry() {}

	
	public ConcurrentMap<String, Map<String, String>> getAllSessionIds() {
		return this.tenantConcurrentMap;
	}
	
	/**
	 * Get All session by tenant & user
	 * @param tenant
	 * @param user
	 * @return
	 */
	public Map<String, String> getSessionIds(String user) {
		Map<String, String> userSession = this.tenantConcurrentMap.get(user);
		if(userSession == null){
			return Collections.emptyMap();
		}
		return userSession;
	}

	/**
	 * Register new session
	 * @param token
	 * @param userName
	 * @param sessionId
	 */
	public void registerSessionId(String token, String userName, String sessionId) {
		Assert.notNull(token, "Token must not be null");
		Assert.notNull(userName, "User name must not be null");
		Assert.notNull(sessionId, "Session Id must not be null");
		Object var3 = this.lock;
		Map<String, String> userSession = this.tenantConcurrentMap.get(userName);
		synchronized (this.lock) {
			if (userSession == null) {
				userSession = new HashMap<String, String>();
			}
			userSession.put(token, sessionId);
			this.tenantConcurrentMap.put(userName, userSession);
		}
	}

	/**
	 * Remove registered session 
	 * @param token
	 * @param userName
	 * @param sessionId
	 */
	public void unregisterSessionId(String token, String userName, String sessionId) {
		Assert.notNull(token, "Token must not be null");
		Assert.notNull(userName, "User name must not be null");
		Assert.notNull(sessionId, "Session Id must not be null");
		Object var3 = this.lock;
		synchronized (this.lock) {
			Map<String, String> sessionMap = this.tenantConcurrentMap.get(userName);
			if (sessionMap != null && sessionMap.remove(token, sessionId) && sessionMap.isEmpty()) {
				this.tenantConcurrentMap.remove(userName);
			}
		}
	}
}
