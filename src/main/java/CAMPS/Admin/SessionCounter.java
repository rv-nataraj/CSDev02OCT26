package CAMPS.Admin;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class SessionCounter implements HttpSessionListener, HttpSessionAttributeListener {

	private static final AtomicInteger activeSessions = new AtomicInteger(0);
	private static final Set<String> studentSessions = ConcurrentHashMap.newKeySet();
	private static final Set<String> staffSessions = ConcurrentHashMap.newKeySet();

	// --- Count session creation & destruction ---
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		activeSessions.incrementAndGet();
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		activeSessions.decrementAndGet();

		HttpSession session = se.getSession();
		Object ssId = session.getAttribute("ss_id");
		Object uType = session.getAttribute("uType");

		if (ssId != null && uType != null) {
			unregisterUser(ssId.toString(), uType.toString());
		}
	}

	// --- Track attributes being set ---
	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		if ("ss_id".equals(event.getName()) || "uType".equals(event.getName())) {
			registerUser(event.getSession());
		}
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		if ("ss_id".equals(event.getName()) || "uType".equals(event.getName())) {
			registerUser(event.getSession());
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		if ("ss_id".equals(event.getName())) {
			Object ssId = event.getValue(); // old value
			Object uType = event.getSession().getAttribute("uType");
			if (ssId != null && uType != null) {
				unregisterUser(ssId.toString(), uType.toString());
			}
		} else if ("uType".equals(event.getName())) {
			Object uType = event.getValue(); // old value
			Object ssId = event.getSession().getAttribute("ss_id");
			if (ssId != null && uType != null) {
				unregisterUser(ssId.toString(), uType.toString());
			}
		}
	}

	// --- Helper methods ---
	private static void registerUser(HttpSession session) {
		Object ssId = session.getAttribute("ss_id");
		Object uType = session.getAttribute("uType");

		if (ssId != null && uType != null) {
			if ("student".equalsIgnoreCase(uType.toString())) {
				studentSessions.add(ssId.toString());
			} else if ("staff".equalsIgnoreCase(uType.toString())) {
				staffSessions.add(ssId.toString());
			}
		}
	}

	private static void unregisterUser(String ssId, String uType) {
		if ("student".equalsIgnoreCase(uType)) {
			studentSessions.remove(ssId);
		} else if ("staff".equalsIgnoreCase(uType)) {
			staffSessions.remove(ssId);
		}
	}

	// --- Public API ---
	public static int getActiveSessions() {
		return activeSessions.get();
	}

	public static Set<String> getActiveStudentSessions() {
		return studentSessions;
	}

	public static Set<String> getActiveStaffSessions() {
		return staffSessions;
	}

	public static int getLoggedInCount() {
		return studentSessions.size() + staffSessions.size();
	}
}
