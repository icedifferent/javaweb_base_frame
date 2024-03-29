
package com.pouruan.web.service.security.impl;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.CompositeLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;



/**
 * @description 重写 ConcurrentSessionFilter->doFilter(),否则强制用户退出的时候会陷入死循环
 * @author Ice
 *
 */
public class ConcurrentSessionFilter extends GenericFilterBean {

	// ~ Instance fields
	// ================================================================================================

	private final SessionRegistry sessionRegistry;
	private String expiredUrl;
	private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();
	private LogoutHandler handlers = new CompositeLogoutHandler(new SecurityContextLogoutHandler());
	private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

	// ~ Methods
	// ========================================================================================================

	public ConcurrentSessionFilter(SessionRegistry sessionRegistry) {
		Assert.notNull(sessionRegistry, "SessionRegistry required");
		this.sessionRegistry = sessionRegistry;
		this.sessionInformationExpiredStrategy = new ResponseBodySessionInformationExpiredStrategy();
	}

	/**
	 * Creates a new instance
	 *
	 * @param sessionRegistry the SessionRegistry to use
	 * @param expiredUrl the URL to redirect to
	 * @deprecated use {@link #ConcurrentSessionFilter(SessionRegistry, SessionInformationExpiredStrategy)} with {@link SimpleRedirectSessionInformationExpiredStrategy} instead.
	 */
	@Deprecated
	public ConcurrentSessionFilter(SessionRegistry sessionRegistry, String expiredUrl) {
		Assert.notNull(sessionRegistry, "SessionRegistry required");
		Assert.isTrue(expiredUrl == null || UrlUtils.isValidRedirectUrl(expiredUrl),
				expiredUrl + " isn't a valid redirect URL");
		this.expiredUrl = expiredUrl;
		this.sessionRegistry = sessionRegistry;
		this.sessionInformationExpiredStrategy = new SessionInformationExpiredStrategy() {

			@Override
			public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
				HttpServletRequest request = event.getRequest();
				HttpServletResponse response = event.getResponse();
				SessionInformation info = event.getSessionInformation();
				redirectStrategy.sendRedirect(request, response, determineExpiredUrl(request, info));
			}

		};
	}

	public ConcurrentSessionFilter(SessionRegistry sessionRegistry, SessionInformationExpiredStrategy sessionInformationExpiredStrategy) {
		Assert.notNull(sessionRegistry, "sessionRegistry required");
		Assert.notNull(sessionInformationExpiredStrategy, "sessionInformationExpiredStrategy cannot be null");
		this.sessionRegistry = sessionRegistry;
		this.sessionInformationExpiredStrategy = sessionInformationExpiredStrategy;
	}

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(sessionRegistry, "SessionRegistry required");
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		HttpSession  session= request.getSession(false);
		//System.out.println(session.getId());
		//System.out.println(request.getRequestURI() );
		if (session != null) {
			System.out.println("session!=null"+session.getId());
			SessionInformation info = sessionRegistry.getSessionInformation(session
					.getId());
			if (info != null) {
				if (info.isExpired()) {
					//System.out.println(info.getSessionId()+"过期");
					// Expired - abort processing
					if (logger.isDebugEnabled()) {
						logger.debug("Requested session ID "
								+ request.getRequestedSessionId() + " has expired.");
					}
					doLogout(request, response);//只清楚remember-cookie，不清除浏览器的sessionid
					//System.out.println("cookie清理完毕");
					//必须删除内存中的session,否则陷入死循环(虽然响应浏览器删除所有session和cookie，
					//但PersistentTokenBasedRememberMeServices的logout方法没有执行session.invalidate();，所以要执行session.invalidate();
					session.invalidate();
					sessionRegistry.removeSessionInformation(info.getSessionId());
					this.sessionInformationExpiredStrategy.onExpiredSessionDetected(new SessionInformationExpiredEvent(info, request, response));
					return;
				}else {
					// Non-expired - update last request date/time
					sessionRegistry.refreshLastRequest(info.getSessionId());
				}
			}else{
				System.out.println("但sessionRegistry=null");
			}
		}

		chain.doFilter(request, response);
	}

	/**
	 * Determine the URL for expiration
	 * @param request the HttpServletRequest
	 * @param info the {@link SessionInformation}
	 * @return the URL for expiration
	 * @deprecated Use {@link #ConcurrentSessionFilter(SessionRegistry, SessionInformationExpiredStrategy)} instead.
	 */
	protected String determineExpiredUrl(HttpServletRequest request,
			SessionInformation info) {
		return expiredUrl;
	}

	private void doLogout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		this.handlers.logout(request, response, auth);
	}

	public void setLogoutHandlers(LogoutHandler[] handlers) {
		this.handlers = new CompositeLogoutHandler(handlers);
	}

	/**
	 * Sets the {@link RedirectStrategy} used with {@link #ConcurrentSessionFilter(SessionRegistry, String)}
	 * @param redirectStrategy the {@link RedirectStrategy} to use
	 * @deprecated use {@link #ConcurrentSessionFilter(SessionRegistry, SessionInformationExpiredStrategy)} instead.
	 */
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	/**
	 * A {@link SessionInformationExpiredStrategy} that writes an error message to the response body.
	 * @author Rob Winch
	 * @since 4.2
	 */
	private static final class ResponseBodySessionInformationExpiredStrategy
			implements SessionInformationExpiredStrategy {
		@Override
		public void onExpiredSessionDetected(SessionInformationExpiredEvent event)
				throws IOException, ServletException {
			HttpServletResponse response = event.getResponse();
			response.getWriter().print(
					"This session has been expired (possibly due to multiple concurrent "
							+ "logins being attempted as the same user).");
			response.flushBuffer();
		}
	}
}
