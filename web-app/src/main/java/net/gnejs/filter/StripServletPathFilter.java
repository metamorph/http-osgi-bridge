package net.gnejs.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * Filter that, if the request matches, removes the context and servlet path from the request.
 * It's a hack to allow the Felix servlet-bridge to route requests to OSGi-registered servlets using the
 * `servlet-local` path.
 * <p/>
 * Request: /foo/bar/zip -> /zip
 * Request: /foo/bar/xyz/foo.txt -> /xyz/foo.txt
 * <p/>
 * It's not a fool-proof solution since the target servlet that this filter wraps can be mapped
 * to any type of wildcard mapping supported by the servlet spec.
 */
public class StripServletPathFilter implements Filter {
	@Override public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// Return a null servletPath, that's it.
		chain.doFilter(createRequest(request), response);
	}

	private ServletRequest createRequest(ServletRequest request) {
		return new HttpServletRequestWrapper((HttpServletRequest) request) {
			@Override public String getServletPath() {
				return null;
			}
		};
	}

	@Override public void destroy() {

	}
}
