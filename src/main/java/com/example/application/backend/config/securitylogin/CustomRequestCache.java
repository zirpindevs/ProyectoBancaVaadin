package com.example.application.backend.config.securitylogin;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HttpSessionRequestCache that avoids saving internal framework requests.
 */
class CustomRequestCache extends HttpSessionRequestCache {
	/**
	 * {@inheritDoc}
	 *
	 * If the method is considered an internal request from the framework, we skip
	 * saving it.
	 *
	 * @see org.vaadin.paul.spring.app.security.SecurityUtils#isFrameworkInternalRequest(HttpServletRequest)
	 */
	@Override
	public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
		if (!com.example.application.backend.config.securitylogin.SecurityUtils.isFrameworkInternalRequest(request)) {
			super.saveRequest(request, response);
		}
	}

}